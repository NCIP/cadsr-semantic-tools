package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.dao.*;

import gov.nih.nci.ncicb.cadsr.spring.*;

import java.util.*;

import org.apache.log4j.Logger;

public class UMLPersister implements Persister {

  private ElementsLists elements = null;

  private AdminComponentDAO adminComponentDAO;
  private DataElementDAO dataElementDAO;
  private ContextDAO contextDAO;
  private DataElementConceptDAO dataElementConceptDAO;
  private ValueDomainDAO valueDomainDAO; 
  private ConceptualDomainDAO conceptualDomainDAO;
  private PropertyDAO propertyDAO;
  private ObjectClassDAO objectClassDAO;
  private ClassificationSchemeDAO classificationSchemeDAO;
  private ClassificationSchemeItemDAO classificationSchemeItemDAO;

  private LoaderDAO loaderDAO;

  private HashMap params = new HashMap();
  
  private String projectName, projectVersion, version, workflowStatus;
  private Context context;
  private ConceptualDomain conceptualDomain;

  private ClassificationSchemeItem domainCsi;
  private ClassificationScheme projectCs;
  private ClassSchemeClassSchemeItem projectCsCsi;

  private HashMap valueDomains = new HashMap();

  private Logger logger = Logger.getLogger(UMLPersister.class.getName());


  private static final String DESIG_TYPE = "UML_PACKAGE";

  public UMLPersister(ElementsLists list) {

    this.elements = list;

    ApplicationContextFactory.init("applicationContext.xml");

    logger.debug("Loading ContextDAO bean");
    contextDAO = (ContextDAO) ApplicationContextFactory.getApplicationContext().getBean("contextDAO");


    logger.debug("Loading DataElementDAO bean");
    dataElementDAO = (DataElementDAO) ApplicationContextFactory.getApplicationContext().getBean("dataElementDAO");

    logger.debug("Loading AdminComponentDAO bean");
    adminComponentDAO = (AdminComponentDAO) ApplicationContextFactory.getApplicationContext().getBean("adminComponentDAO");

    logger.debug("Loading DataElementConceptDAO bean");
    dataElementConceptDAO = (DataElementConceptDAO) ApplicationContextFactory.getApplicationContext().getBean("dataElementConceptDAO");

    logger.debug("Loading CDDAO bean");
    conceptualDomainDAO = (ConceptualDomainDAO) ApplicationContextFactory.getApplicationContext().getBean("conceptualDomainDAO");

    logger.debug("Loading VDDAO bean");
    valueDomainDAO = (ValueDomainDAO) ApplicationContextFactory.getApplicationContext().getBean("valueDomainDAO");

    logger.debug("Loading PropertyDAO bean");
    propertyDAO = (PropertyDAO) ApplicationContextFactory.getApplicationContext().getBean("propertyDAO");

    logger.debug("Loading ObjectClassDAO bean");
    objectClassDAO = (ObjectClassDAO) ApplicationContextFactory.getApplicationContext().getBean("objectClassDAO");

    logger.debug("Loading CSDAO bean");
    classificationSchemeDAO = (ClassificationSchemeDAO) ApplicationContextFactory.getApplicationContext().getBean("classificationSchemeDAO");

    logger.debug("Loading CSIDAO bean");
    classificationSchemeItemDAO = (ClassificationSchemeItemDAO) ApplicationContextFactory.getApplicationContext().getBean("classificationSchemeItemDAO");

    logger.debug("Loading LoaderDAO bean");
    loaderDAO = (LoaderDAO) ApplicationContextFactory.getApplicationContext().getBean("loaderDAO");

  }

  public void setParameter(String key, Object value) {
    params.put(key, value);
  }

  public void persist() throws PersisterException {

    initParams();

    initClassifications();

    persistProperties();
    
    persistObjectClasses();

    persistDecs();

    persistDes();

  }


  public void persistDes() throws PersisterException {
    DataElement de = DomainObjectFactory.newDataElement();
    List des = (List)elements.getElements(de.getClass());
    
    if(des != null)
      for(ListIterator it=des.listIterator(); it.hasNext(); ) {
	de = (DataElement)it.next();
	
	de.setContext(context);

	int ind = de.getLongName().lastIndexOf(".");
	if(ind > 0)
	  de.setLongName(de.getLongName().substring(ind+1));

	List decs = elements.getElements(DomainObjectFactory.newDataElementConcept().getClass());
	for(ListIterator lit=decs.listIterator(); lit.hasNext();) {
	  DataElementConcept o = (DataElementConcept)lit.next();
	  if(o.getLongName().equals(de.getDataElementConcept().getLongName()))
	    de.setDataElementConcept(o);
	}

	de.setValueDomain(lookupValueDomain(de.getValueDomain()));
	List l = dataElementDAO.find(de);
	if(l.size() == 0) {
	  de.setPreferredDefinition(de.getLongName());
	  de.setPreferredName(de.getLongName());
	  // !!!!! TODO -- following will pass constraints
	  if(de.getPreferredName().length() > 30)
	    de.setPreferredName(de.getPreferredName().substring(0, 29));

	  de.setVersion(new Float(version));
	  
	  de.setWorkflowStatus(workflowStatus);

	  de.setId(dataElementDAO.create(de));
	  logger.info("Created DataElement:  ");
	} else {
	  de = (DataElement)l.get(0);
	  logger.info("DataElement Existed: ");
	}	

	logAc(de);
	logger.info("-- Value Domain (Preferred_Name): " + de.getValueDomain().getPreferredName());
	
	addClassificationSchemes(de);
	it.set(de);

      }
  }

  // !!!! TODO 
  // EVS CONCEPT CODE.
  private void persistProperties() throws PersisterException {

    Property prop = DomainObjectFactory.newProperty();
    List props = (List)elements.getElements(prop.getClass());

    if(props != null)
      for(ListIterator it=props.listIterator(); it.hasNext();) {
	prop = (Property)it.next();

	prop.setContext(context);
	
	// does this property exist?
	List l = propertyDAO.find(prop);
	if(l.size() == 0) {
	  // !!!!! TODO
	  prop.setPreferredDefinition(prop.getLongName());
	  prop.setPreferredName(prop.getLongName());
	  
	  prop.setVersion(new Float(1.0f));
	  
	  prop.setWorkflowStatus(workflowStatus);
	  prop.setId(propertyDAO.create(prop));
	  logger.info("Created Property: ");
	} else {
	  prop = (Property)l.get(0);
	  logger.info("Property Existed: ");
	}	

	logAc(prop);
	
	addClassificationSchemes(prop);
	it.set(prop);
      }

  }

  
  private void persistObjectClasses() throws PersisterException {

    ObjectClass oc = DomainObjectFactory.newObjectClass();
    List ocs = (List)elements.getElements(oc.getClass());

    logger.debug("ocs...");
    
    String packageName = null;

    if(ocs != null)
      for(ListIterator it=ocs.listIterator(); it.hasNext(); ) {
	oc = (ObjectClass)it.next();
	oc.setContext(context);

	String className = oc.getLongName();
	int ind = className.lastIndexOf(".");
	packageName = className.substring(0, ind);
	className = className.substring(ind + 1);
	
	oc.setLongName(className);
	// does this oc exist?
	List eager = new ArrayList();
	eager.add(EagerConstants.DESIGNATIONS);
	List l = objectClassDAO.find(oc, eager);

	boolean desigFound = false;
	if(l.size() == 0) {
	  // !!!!! TODO
	  oc.setPreferredDefinition(oc.getLongName());
	  oc.setPreferredName(oc.getLongName());

	  oc.setVersion(new Float(1.0f));

	  oc.setWorkflowStatus(workflowStatus);
	  oc.setId(objectClassDAO.create(oc));
	  logger.info("Created Object Class: ");
	} else {
	  oc = (ObjectClass)l.get(0);
	  logger.info("Object Class Existed: ");
	  
	  List designations = oc.getDesignations();
	  if(designations != null) 
	    for(Iterator it2 = designations.iterator(); it2.hasNext(); ) {
	      Designation d = (Designation)it2.next();
	      if(d.getType().equals(DESIG_TYPE) && d.getName().equals(packageName)) {
		desigFound = true;
		logger.debug("Found desig: " + d.getType() + " " + d.getName());
	      }
	    }
	  
	}	

	logAc(oc);

	addClassificationSchemes(oc);
	it.set(oc);

	// add designation to hold package name
	// !!!! TODO
	if(!desigFound) {
	  List des = new ArrayList();
	  Designation desig = DomainObjectFactory.newDesignation();
	  desig.setContext(context);
	  desig.setName(packageName);
	  desig.setType(DESIG_TYPE);
	  des.add(desig);
	  adminComponentDAO.addDesignations(oc, des);
	  logger.info("Added Designation: ");
	  logger.info("-- ID: " + desig.getId());
	  logger.info("-- Type: " + desig.getType());
	  logger.info("-- Name: " + desig.getName());
	} else {
	  logger.debug("Designation was found.");
	}
      }

  }

  private void persistDecs() throws PersisterException {
    DataElementConcept dec = DomainObjectFactory.newDataElementConcept();
    List decs = (List)elements.getElements(dec.getClass());
    
    logger.debug("decs: " + decs.size());

    if(decs != null)
      for(ListIterator it=decs.listIterator(); it.hasNext(); ) {
	dec = (DataElementConcept)it.next();
	
	dec.setContext(context);
	dec.setConceptualDomain(conceptualDomain);

	int ind = dec.getLongName().lastIndexOf(".");
	if(ind > 0)
	  dec.setLongName(dec.getLongName().substring(ind+1));

	logger.debug("dec name: " + dec.getLongName());

	// does this dec exist?
	List l = dataElementConceptDAO.find(dec);
	if(l.size() == 0) {
	  // !!!!! TODO
	  dec.setPreferredDefinition(dec.getLongName());
	  dec.setPreferredName(dec.getLongName());

	  dec.setVersion(new Float(version));
	  dec.setWorkflowStatus(workflowStatus);

	  List ocs = elements.getElements(DomainObjectFactory.newObjectClass().getClass());
	  for(int j=0; j<ocs.size(); j++) {
	    ObjectClass o = (ObjectClass)ocs.get(j);
	    if(o.getLongName().equals(dec.getObjectClass().getLongName()))
	      dec.setObjectClass(o);
	  }

	  List props = elements.getElements(DomainObjectFactory.newProperty().getClass());
	  for(int j=0; j<props.size(); j++) {
	    Property o = (Property)props.get(j);
	    if(o.getLongName().equals(dec.getProperty().getLongName()))
	      dec.setProperty(o);
	  }

	  
	  dec.setId(dataElementConceptDAO.create(dec));
	  logger.info("Created DataElementConcept: ");
	} else {
	  dec = (DataElementConcept)l.get(0);
	  logger.info("DataElementConcept Existed: ");
	}	

	logAc(dec);
	logger.info("-- Object Class (long_name): " + dec.getObjectClass().getLongName());
	logger.info("-- Property (long_name): " + dec.getProperty().getLongName());

	addClassificationSchemes(dec);
	it.set(dec);

	// add designation to hold package name
	// !!!! TODO
	
      }

  }


  private void addClassificationSchemes(AdminComponent ac) throws PersisterException {

    // Add Classification Schemes
    List l = adminComponentDAO.getClassSchemeClassSchemeItems(ac);
    
    // is projectCs linked?
    boolean found = false;

    for(ListIterator it = l.listIterator(); it.hasNext();) {
      ClassSchemeClassSchemeItem csCsi = (ClassSchemeClassSchemeItem)it.next();

      if(csCsi.getCs().getLongName().equals(projectCs.getLongName()))
	if(csCsi.getCsi().getName().equals(domainCsi.getName()))
	  found = true;
      
     
    } 
    List csCsis = new ArrayList();
    if(!found) {
      logger.info("Project Classification was not found. Attaching it now.");
      csCsis.add(projectCsCsi);
      adminComponentDAO.addClassSchemeClassSchemeItems(ac, csCsis);
    }
    
  }
  
  private void initParams() throws PersisterException {

    projectName = (String)params.get("projectName");
    // !!!! TODO Use version Too
    LoaderDefault loaderDefault = loaderDAO.findByName(projectName);

    if(loaderDefault == null)
      throw new PersisterException("Defaults not found. Please create a profile first.");
    
    String cName = loaderDefault.getContextName();
    if(cName == null)
      throw new PersisterException("Context Name not Set.");

    context = contextDAO.findByName(cName);
    if(context == null)
      throw new PersisterException("Context: " + cName + " not found.");

    version = loaderDefault.getVersion().toString();
    projectVersion = loaderDefault.getProjectVersion().toString();

    workflowStatus = loaderDefault.getWorkflowStatus();
    if(workflowStatus == null)
      throw new PersisterException("WorkflowStatus not Set.");
    
    conceptualDomain = DomainObjectFactory.newConceptualDomain();
    conceptualDomain.setPreferredName(loaderDefault.getCdName());
    
    Context cdContext = contextDAO.findByName(loaderDefault.getCdContextName());
    if(cdContext == null)
      throw new PersisterException("CD Context not found.");

    conceptualDomain.setContext(cdContext);

    try {
      conceptualDomain = (ConceptualDomain)conceptualDomainDAO.find(conceptualDomain).get(0);
    } catch (NullPointerException e){
      throw new PersisterException("CD: " + conceptualDomain.getPreferredName() + " not found.");
    } 
    

  }
  
  private void initClassifications() throws PersisterException {

    domainCsi = DomainObjectFactory.newClassificationSchemeItem();

    // !!!! TODO
    domainCsi.setName("Essai-Domain Model");

    // !!!! TODO
    domainCsi.setType("TEST");

    List result = classificationSchemeItemDAO.find(domainCsi);
    if(result.size() == 0)
      throw new PersisterException("Classification Scheme Item: " + domainCsi.getName() + " does not exist on DB.");
    domainCsi = (ClassificationSchemeItem)result.get(0);

    projectCs = DomainObjectFactory.newClassificationScheme();
    projectCs.setLongName(projectName);
    projectCs.setVersion(new Float(projectVersion));
    projectCs.setContext(context);
    ArrayList eager = new ArrayList();
    eager.add(EagerConstants.CS_CSI);
    result = classificationSchemeDAO.find(projectCs, eager);
    
    if(result.size() == 0) { // need to add projectName CS
      projectCs.setPreferredName(projectName);
      projectCs.setWorkflowStatus(workflowStatus);
      // !!! TODO
      projectCs.setPreferredDefinition("Un essai de CS. Nom du projet.");
      // !!! TODO
      projectCs.setType("TEST");
      projectCs.setLabelType(ClassificationScheme.LABEL_TYPE_ALPHA);

      projectCs.setId(classificationSchemeDAO.create(projectCs));
      logger.info("Addedd Project CS: ");
      logAc(projectCs);
      logger.info("-- Type: " + projectCs.getType());
      
      projectCsCsi = DomainObjectFactory.newClassSchemeClassSchemeItem();
      projectCsCsi.setCs(projectCs);
      projectCsCsi.setCsi(domainCsi);
      projectCsCsi.setLabel(projectName);
      
      classificationSchemeDAO.addClassificationSchemeItem(projectCs, projectCsCsi);
      logger.info("Added Project CS_CSI: ");
      logger.info("-- Label: " + projectCsCsi.getLabel());
    } else { // is domainCsi linked?
      logger.info("Project CS existed");
      projectCs = (ClassificationScheme)result.get(0);
      List csCsis = projectCs.getCsCsis();
      boolean found = false;
      for(ListIterator it = csCsis.listIterator(); it.hasNext(); ) 
	{
	  ClassSchemeClassSchemeItem csCsi = (ClassSchemeClassSchemeItem)it.next();
	  if(csCsi.getCsi().getName().equals(domainCsi.getName())) 
	    {
	      projectCsCsi = csCsi;
	      found = true;
	    }
	}
      if(!found) {
	logger.info("Project CS was not linked to Domain CSI -- linking it now.");
	projectCsCsi = DomainObjectFactory.newClassSchemeClassSchemeItem();
	projectCsCsi.setCs(projectCs);
	projectCsCsi.setCsi(domainCsi);
	projectCsCsi.setLabel(projectName);

	classificationSchemeDAO.addClassificationSchemeItem(projectCs, projectCsCsi);
      }

    }
  }
  
  private void logAc(AdminComponent ac) {
    logger.info("-- ID: " + ac.getId());
    logger.info("-- Long_Name: " + ac.getLongName());
    logger.info("-- Preferred_Name: " + ac.getPreferredName());
    logger.info("-- Version: " + ac.getVersion());
    logger.info("-- Workflow Status: " + ac.getWorkflowStatus());
    logger.info("-- Preferred_Definition: " + ac.getPreferredDefinition());
    
  }
  
  private ValueDomain lookupValueDomain(ValueDomain vd) throws PersisterException {
    ValueDomain result = (ValueDomain)valueDomains.get(vd.getPreferredName());

    if(result == null) { // not in cache -- go to db
      result = (ValueDomain)valueDomainDAO.find(vd).get(0);
      valueDomains.put(result.getPreferredName(), result);
    }

    return result;

  }

  
}
