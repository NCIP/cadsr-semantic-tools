package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.dao.*;

import gov.nih.nci.ncicb.cadsr.spring.*;

import java.util.*;

import org.apache.log4j.Logger;

public class UMLPersister implements Persister {
  private static Logger logger = Logger.getLogger(UMLPersister.class.getName());
  private static AdminComponentDAO      adminComponentDAO;
  private static DataElementDAO         dataElementDAO;
  private static ContextDAO             contextDAO;
  private static DataElementConceptDAO  dataElementConceptDAO;
  private static ValueDomainDAO         valueDomainDAO; 
  private static ConceptualDomainDAO    conceptualDomainDAO;
  private static PropertyDAO            propertyDAO;
  private static ObjectClassDAO         objectClassDAO;
  private static ObjectClassRelationshipDAO         objectClassRelationshipDAO;
  private static ClassificationSchemeDAO     classificationSchemeDAO;
  private static ClassificationSchemeItemDAO classificationSchemeItemDAO;
  private static LoaderDAO loaderDAO;
  private static ConceptDAO conceptDAO;

  static {
    ApplicationContextFactory.init("applicationContext.xml");

//     logger.debug("Loading ContextDAO bean");
    contextDAO = (ContextDAO) ApplicationContextFactory.getApplicationContext().getBean("contextDAO");

//     logger.debug("Loading DataElementDAO bean");
    dataElementDAO = (DataElementDAO) ApplicationContextFactory.getApplicationContext().getBean("dataElementDAO");

//     logger.debug("Loading AdminComponentDAO bean");
    adminComponentDAO = (AdminComponentDAO) ApplicationContextFactory.getApplicationContext().getBean("adminComponentDAO");

//     logger.debug("Loading DataElementConceptDAO bean");
    dataElementConceptDAO = (DataElementConceptDAO) ApplicationContextFactory.getApplicationContext().getBean("dataElementConceptDAO");

//     logger.debug("Loading CDDAO bean");
    conceptualDomainDAO = (ConceptualDomainDAO) ApplicationContextFactory.getApplicationContext().getBean("conceptualDomainDAO");

//     logger.debug("Loading VDDAO bean");
    valueDomainDAO = (ValueDomainDAO) ApplicationContextFactory.getApplicationContext().getBean("valueDomainDAO");

//     logger.debug("Loading PropertyDAO bean");
    propertyDAO = (PropertyDAO) ApplicationContextFactory.getApplicationContext().getBean("propertyDAO");

//     logger.debug("Loading ObjectClassDAO bean");
    objectClassDAO = (ObjectClassDAO) ApplicationContextFactory.getApplicationContext().getBean("objectClassDAO");

//     logger.debug("Loading ObjectClassRelationshipDAO bean");
    objectClassRelationshipDAO = (ObjectClassRelationshipDAO) ApplicationContextFactory.getApplicationContext().getBean("objectClassRelationshipDAO");

//     logger.debug("Loading CSDAO bean");
    classificationSchemeDAO = (ClassificationSchemeDAO) ApplicationContextFactory.getApplicationContext().getBean("classificationSchemeDAO");

//     logger.debug("Loading CSIDAO bean");
    classificationSchemeItemDAO = (ClassificationSchemeItemDAO) ApplicationContextFactory.getApplicationContext().getBean("classificationSchemeItemDAO");

//     logger.debug("Loading LoaderDAO bean");
    loaderDAO = (LoaderDAO) ApplicationContextFactory.getApplicationContext().getBean("loaderDAO");

//     logger.debug("Loading ConceptDAO bean");
    conceptDAO = (ConceptDAO) ApplicationContextFactory.getApplicationContext().getBean("conceptDAO");

  }


  private ElementsLists elements = null;
  private Map params = new HashMap();
  
  private String projectName, projectVersion, workflowStatus;
  private Float version;
  private Context context;
  private ConceptualDomain conceptualDomain;

  private ClassificationSchemeItem domainCsi;
  private ClassificationScheme projectCs;
  private ClassSchemeClassSchemeItem projectCsCsi;

  // default Audit holds username
  private Audit audit;

  private Map valueDomains = new HashMap();



  private Map packageCsCsis = new HashMap();

  private static final String CSI_PACKAGE_TYPE = "UML_PACKAGE";

  public UMLPersister(ElementsLists list) {

    this.elements = list;

  }

  public void setParameter(String key, Object value) {
    params.put(key, value);
  }

  public void persist() throws PersisterException {

    initParams();

    initClassifications();

    persistPackages();

    persistProperties();
    
    persistObjectClasses();

    persistDecs();

    persistDes();

    persistOcRecs();
  }

  private void persistPackages() throws PersisterException {
    ClassificationSchemeItem pkg = DomainObjectFactory.newClassificationSchemeItem();
    List packages = (List)elements.getElements(pkg.getClass());
    if(packages != null)
      for(ListIterator it = packages.listIterator(); it.hasNext();) {

	pkg = (ClassificationSchemeItem)it.next();
	pkg.setAudit(audit);
	pkg.setType(CSI_PACKAGE_TYPE);
	
	// See if it already exist in DB
	List l = classificationSchemeItemDAO.find(pkg);
	if(l.size() == 0) { // not in DB, create it.
	  pkg.setId(classificationSchemeItemDAO.create(pkg));
	} else {
	  pkg = (ClassificationSchemeItem)l.get(0);
	}
	
	// link package CSI to project CS.
	List csCsis = projectCs.getCsCsis();
	boolean found = false;
	ClassSchemeClassSchemeItem packageCsCsi = null;
	for(ListIterator it2 = csCsis.listIterator(); it2.hasNext(); ) 
	  {
	    ClassSchemeClassSchemeItem csCsi = (ClassSchemeClassSchemeItem)it2.next();
	    if(csCsi.getCsi().getType().equals(CSI_PACKAGE_TYPE) && csCsi.getCsi().getName().equals(pkg.getName())) {
	      {
		packageCsCsi = csCsi;
		found = true;
	      }
	    }
	  }
	if(!found) {
	  logger.info("Package " + pkg.getName() + " was not linked to Project CS -- linking it now.");
	  packageCsCsi = DomainObjectFactory.newClassSchemeClassSchemeItem();
	  packageCsCsi.setCs(projectCs);
	  packageCsCsi.setCsi(pkg);
	  packageCsCsi.setLabel(pkg.getName());
	  packageCsCsi.setAudit(audit);
	  
	  classificationSchemeDAO.addClassificationSchemeItem(projectCs, packageCsCsi);
	  logger.info("Added Package CS_CSI");
	}
	
	// Put CS_CSI in cache so OCs can use it
	packageCsCsis.put(pkg.getName(), packageCsCsi);

      }
  }

  private void persistOcRecs() throws PersisterException {

    ObjectClassRelationship ocr = DomainObjectFactory.newObjectClassRelationship();
    List ocrs = (List)elements.getElements(ocr.getClass());

    if(ocrs != null)
      for(ListIterator it=ocrs.listIterator(); it.hasNext(); ) {
	ocr = (ObjectClassRelationship)it.next();
	ocr.setContext(context);
	ocr.setAudit(audit);
	ocr.setVersion(version);
	ocr.setWorkflowStatus(workflowStatus);

	String definition = null;
	if(ocr.getSourceCardinality().indexOf("*") != -1)
	  definition = "Many-to-";
	else
	  definition = "One-to-";
	if(ocr.getTargetCardinality().indexOf("*") != -1)
	  definition += "many";
	else
	  definition += "one";

	ocr.setPreferredDefinition(definition);
	
	// !!! TODO This requirement is changing. Business Case will require a Role name for all associations.
	if((ocr.getLongName() == null) || (ocr.getLongName().length() == 0)) {
	  logger.debug("No Role name for association. Generating one");
	  String longName = ocr.getSource().getLongName();
	  if(ocr.getSourceRole() != null)
	    longName += "." + ocr.getSourceRole();
	  longName += "(" + ocr.getSourceCardinality() 
	    + ")::"
	    + ocr.getTarget().getLongName();
	  if(ocr.getTargetRole() != null)
	    longName += "." + ocr.getTargetRole();
	  longName += "(" + ocr.getSourceCardinality() + ")";
	  
	  ocr.setLongName(longName);
	}

	List ocs = elements.getElements(DomainObjectFactory.newObjectClass().getClass());
	for(int j=0; j<ocs.size(); j++) {
	  ObjectClass o = (ObjectClass)ocs.get(j);
	  if(o.getLongName().equals(ocr.getSource().getLongName())) {
	    ocr.setSource(o);
	  } else if(o.getLongName().equals(ocr.getTarget().getLongName()))
	    ocr.setTarget(o);
	}

	logAc(ocr);
	logger.info("-- Source Role: " + ocr.getSourceRole());
	logger.info("-- Source Cardinality: " + ocr.getSourceCardinality());
	logger.info("-- Target Role: " + ocr.getTargetRole());
	logger.info("-- Target Cardinality: " + ocr.getTargetCardinality());
	logger.info("-- Direction: " + ocr.getDirection());
	logger.info("-- Type: " + ocr.getType());

	// check if association already exists
	ObjectClassRelationship ocr2 = DomainObjectFactory.newObjectClassRelationship();
	ocr2.setSource(ocr.getSource());
	ocr2.setSourceRole(ocr.getSourceRole());
	ocr2.setTarget(ocr.getTarget());
	ocr2.setTargetRole(ocr.getTargetRole());

	List eager = new ArrayList();
	eager.add(EagerConstants.AC_CS_CSI);
	List l = objectClassRelationshipDAO.find(ocr2, eager);
	boolean found = false;
	if(l.size() > 0) {
	  for(Iterator it2 = l.iterator(); it2.hasNext(); ) {
	    ocr2 = (ObjectClassRelationship)it2.next();
	    List acCsCsis = (List)ocr2.getAcCsCsis();
	    for(Iterator it3 = acCsCsis.iterator(); it3.hasNext(); ) {
	      AdminComponentClassSchemeClassSchemeItem acCsCsi = (AdminComponentClassSchemeClassSchemeItem)it3.next();
	      if(acCsCsi.getCsCsi().getCs().getLongName().equals(projectCs.getLongName()));
		found = true;
	    }
	  }
	}
	
	if(found) {
	  logger.info("Association already existed.");
	} else {
	  ocr.setId(objectClassRelationshipDAO.create(ocr));
	  addProjectCs(ocr);
	  logger.info("Created Association");
	}

	// !!! TODO also add package name

      }      

  }

  private void persistDes() throws PersisterException {
    DataElement de = DomainObjectFactory.newDataElement();
    List des = (List)elements.getElements(de.getClass());

    logger.debug("des...");
    
    if(des != null)
      for(ListIterator it=des.listIterator(); it.hasNext(); ) {
	try {
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

	    de.setVersion(version);
	    de.setWorkflowStatus(workflowStatus);

	    de.setAudit(audit);
	    logger.debug("Creating DE: " + de.getLongName());
	    de.setId(dataElementDAO.create(de));
	    logger.info("Created DataElement:  ");
	  } else {
	    de = (DataElement)l.get(0);
	    logger.info("DataElement Existed: ");
	  }	

	  logAc(de);
	  logger.info("-- Value Domain (Preferred_Name): " + de.getValueDomain().getPreferredName());
	  
	  addProjectCs(de);
	  it.set(de);
	} catch (PersisterException e){
	  logger.error("Could not persist DE: " + de.getLongName());
	  logger.error(e.getMessage());
	} // end of try-catch
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
	  prop.setAudit(audit);
	  
	  prop.setId(propertyDAO.create(prop));
	  logger.info("Created Property: ");
	} else {
	  prop = (Property)l.get(0);
	  logger.info("Property Existed: ");
	}	

	logAc(prop);
	
	addProjectCs(prop);
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
	eager.add(EagerConstants.AC_CS_CSI);
	List l = objectClassDAO.find(oc, eager);

	boolean packageFound = false;
	if(l.size() == 0) {
	  // !!!!! TODO
	  oc.setPreferredDefinition(oc.getLongName());
	  oc.setPreferredName(oc.getLongName());

	  oc.setVersion(new Float(1.0f));
	  oc.setWorkflowStatus(workflowStatus);
	  oc.setAudit(audit);

	  oc.setId(objectClassDAO.create(oc));
	  logger.info("Created Object Class: ");
	} else {
	  oc = (ObjectClass)l.get(0);
	  logger.info("Object Class Existed: ");
	  
	  List packages = oc.getAcCsCsis(); 
	  if(packages != null)
	    for(Iterator it2 = packages.iterator(); it2.hasNext() && !packageFound; ) {
	      AdminComponentClassSchemeClassSchemeItem acCsCsi = (AdminComponentClassSchemeClassSchemeItem)it2.next();
	      ClassSchemeClassSchemeItem csCsi = acCsCsi.getCsCsi();
	      if(csCsi.getCsi().getType().equals(CSI_PACKAGE_TYPE) && csCsi.getCsi().getName().equals(packageName)) {
		packageFound = true;
	      }
	    }
	}	

	logAc(oc);

	addProjectCs(oc);
	it.set(oc);

	// add CSI to hold package name
	// !!!! TODO
	if(!packageFound) {
	  // see if we have the package in cache
	  ClassSchemeClassSchemeItem packageCsCsi = (ClassSchemeClassSchemeItem)packageCsCsis.get(packageName);
	  
	  if(packageCsCsi != null) {
	    List ll = new ArrayList();
	    ll.add(packageCsCsi);
	    adminComponentDAO.addClassSchemeClassSchemeItems(oc, ll);
	    logger.info("Added CS package: " + packageName);
	  } else { 
	    // PersistPackages should have taken care of it. 
	    // We should not be here.
	    logger.error("Missing Package: " + packageName);
	  }
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

	  dec.setVersion(version);
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

	  dec.setAudit(audit);
	  dec.setId(dataElementConceptDAO.create(dec));
	  logger.info("Created DataElementConcept: ");
	} else {
	  dec = (DataElementConcept)l.get(0);
	  logger.info("DataElementConcept Existed: ");
	}	

	logAc(dec);
	logger.info("-- Object Class (long_name): " + dec.getObjectClass().getLongName());
	logger.info("-- Property (long_name): " + dec.getProperty().getLongName());

	addProjectCs(dec);
	it.set(dec);

	// add designation to hold package name
	// !!!! TODO
	
      }

  }


  private void addProjectCs(AdminComponent ac) throws PersisterException {
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
    audit = DomainObjectFactory.newAudit();
    audit.setCreatedBy((String)params.get("username"));

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

    version = new Float(loaderDefault.getVersion().toString());
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

      projectCs.setAudit(audit);
      projectCs.setId(classificationSchemeDAO.create(projectCs));
      logger.info("Added Project CS: ");
      logAc(projectCs);
      logger.info("-- Type: " + projectCs.getType());
      
      projectCsCsi = DomainObjectFactory.newClassSchemeClassSchemeItem();
      projectCsCsi.setCs(projectCs);
      projectCsCsi.setCsi(domainCsi);
      projectCsCsi.setLabel(projectName);
      projectCsCsi.setAudit(audit);
      
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
	projectCsCsi.setAudit(audit);

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
      List l = valueDomainDAO.find(vd);
      if(l.size() == 0) {
	throw new PersisterException("Value Domain " + vd.getPreferredName() + " does not exist.");
      }
      result = (ValueDomain)l.get(0);
      valueDomains.put(result.getPreferredName(), result);
    }

    return result;

  }

  
}
