package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.dao.*;

import gov.nih.nci.ncicb.cadsr.spring.*;

import java.util.*;

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

  private HashMap params = new HashMap();

  

  public UMLPersister(ElementsLists list) {
    this.elements = list;

    ApplicationContextFactory.init("applicationContext.xml");


    System.out.println("Loading ContextDAO bean");
    contextDAO = (ContextDAO) ApplicationContextFactory.getApplicationContext().getBean("contextDAO");


//     System.out.println("Loading DataElementDAO bean");
//     dataElementDAO = (DataElementDAO) ApplicationContextFactory.getApplicationContext().getBean("dataElementDAO");

//     System.out.println("Loading AdminComponentDAO bean");
//     adminComponentDAO = (AdminComponentDAO) ApplicationContextFactory.getApplicationContext().getBean("adminComponentDAO");

//     System.out.println("Loading DataElementConceptDAO bean");
//     dataElementConceptDAO = (DataElementConceptDAO) ApplicationContextFactory.getApplicationContext().getBean("dataElementConceptDAO");

//     System.out.println("Loading CDDAO bean");
//     conceptualDomainDAO = (ConceptualDomainDAO) ApplicationContextFactory.getApplicationContext().getBean("conceptualDomainDAO");

    System.out.println("Loading VDDAO bean");
    valueDomainDAO = (ValueDomainDAO) ApplicationContextFactory.getApplicationContext().getBean("valueDomainDAO");

    System.out.println("Loading PropertyDAO bean");
    propertyDAO = (PropertyDAO) ApplicationContextFactory.getApplicationContext().getBean("propertyDAO");

    System.out.println("Loading ObjectClassDAO bean");
    objectClassDAO = (ObjectClassDAO) ApplicationContextFactory.getApplicationContext().getBean("objectClassDAO");

    System.out.println("Loading CSDAO bean");
    classificationSchemeDAO = (ClassificationSchemeDAO) ApplicationContextFactory.getApplicationContext().getBean("classificationSchemeDAO");

  }

  public void setParameter(String key, Object value) {
    params.put(key, value);
  }

  public void persist() throws PersisterException {

    
    String cName = (String)params.get("contextName");
    if(cName == null)
      throw new PersisterException("Context Name not Set.");

    Context context = contextDAO.findByName(cName);
    if(context == null)
      throw new PersisterException("Context: " + cName + " not found.");


    String projectName = (String)params.get("projectName");
    String version = (String)params.get("version");
    if(projectName == null || version == null)
      throw new PersisterException("Project Name / Version not Set.");


//     String workflowStatus = (String)params.get("workflowStatus");
//     if(workflowStatus == null)
//       throw new PersisterException("WorkflowStatus not Set.");


    
    ClassificationScheme domainCS = DomainObjectFactory.newClassificationScheme();
    domainCS.setLongName("essai-UML");
    ArrayList eager = new ArrayList();
    eager.add(EagerConstants.CS_CSI);
    List result = classificationSchemeDAO.find(domainCS, eager);

    if(result.size() == 0)
      throw new PersisterException("Classification Scheme: " + domainCS.getLongName() + " does not exist on DB.");

    domainCS = (ClassificationScheme)result.get(0);
    
    
    List csCsis = domainCS.getCsCsis();
    boolean found = false;
    for(int i=0; i<csCsis.size(); i++) {
      ClassSchemeClassSchemeItem csCsi = (ClassSchemeClassSchemeItem)csCsis.get(i);
      if(csCsi.getCsi().getName().equals(projectName))
	found = true;
    }    
    if(!found) { // need to add projectName CSI
      ClassificationSchemeItem csi = DomainObjectFactory.newClassificationSchemeItem();
      csi.setName(projectName);
      csi.setType("TEST");

      ClassSchemeClassSchemeItem csCsi = DomainObjectFactory.newClassSchemeClassSchemeItem();
      csCsi.setCs(domainCS);
      csCsi.setCsi(csi);
      csCsi.setLabel(projectName);

      ArrayList list = new ArrayList();
      list.add(csCsi);
      classificationSchemeDAO.addClassificationSchemeItems(domainCS, list);

      System.out.println("Added CSI to domainCS");
    }
    



    found = false;
    for(int i=0; i<csCsis.size(); i++) {      
      ClassSchemeClassSchemeItem csCsi = (ClassSchemeClassSchemeItem)csCsis.get(i);
      if(csCsi.getCsi().getName().equals(version))
	found = true;
    }
    if(!found) { // need to add version CSI
      ClassificationSchemeItem csi = DomainObjectFactory.newClassificationSchemeItem();
      csi.setName(projectName + "_" + version);
      csi.setType("TEST");

      ClassSchemeClassSchemeItem csCsi = DomainObjectFactory.newClassSchemeClassSchemeItem();
      csCsi.setCs(domainCS);
      csCsi.setCsi(csi);
      csCsi.setLabel(version);
      
      ArrayList list = new ArrayList();
      list.add(csCsi);
      classificationSchemeDAO.addClassificationSchemeItems(domainCS, list);
    }
    
    
    
    



    // persist OC
//     List ocs = (List)elements.getElements(ObjectClass.class);
//     if(ocs != null)
//       for(int i=0; i<ocs.size(); i++) {
// 	ObjectClass oc = (ObjectClass)ocs.get(0);
    

// 	System.out.println("Found Object Class: " + oc.getPreferredName());
// 	// Does this OC exist?
	
	
//       }
    

    DataElement o = DomainObjectFactory.newDataElement();

    List des = (List)elements.getElements(o.getClass());
    if(des != null) 
      for(int i=0; i<des.size(); i++) {
	DataElement de = (DataElement)des.get(i);
	DataElementConcept dec = de.getDataElementConcept();
	System.out.println("Class: " + dec.getObjectClass().getPreferredName());
	System.out.println(" -- Attribute: " + dec.getProperty().getPreferredName() + ":" + de.getValueDomain().getPreferredName());

	List vds = valueDomainDAO.find(de.getValueDomain());
	System.out.println("vds -- SIZE: " + vds.size());
	

      }

  }

  
}
