package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.model.*;
import gov.nih.nci.ncicb.cadsr.dao.*;

import gov.nih.nci.ncicb.cadsr.spring.*;

import java.util.*;

public class UMLPersister implements Persister {

  private ElementsLists elements = null;

  private String contextName;

  private AdminComponentDAO adminComponentDAO;
  private DataElementDAO dataElementDAO;
  private ContextDAO contextDAO;
  private DataElementConceptDAO dataElementConceptDAO;
  private ValueDomainDAO valueDomainDAO; 
  private ConceptualDomainDAO conceptualDomainDAO;
  private PropertyDAO propertyDAO;
  private ObjectClassDAO objectClassDAO;
  private ClassificationSchemeDAO classificationSchemeDAO;
  private ContentObjectDAO contentObjectDAO;


  public UMLPersister(ElementsLists list) {
    this.elements = list;

    ApplicationContextFactory.init("applicationContext.xml");


    System.out.println("Loading ContextDAO bean");
    contextDAO = (ContextDAO) ApplicationContextFactory.getApplicationContext().getBean("contextDAO");


    System.out.println("Loading DataElementDAO bean");
    dataElementDAO = (DataElementDAO) ApplicationContextFactory.getApplicationContext().getBean("dataElementDAO");

    System.out.println("Loading AdminComponentDAO bean");
    adminComponentDAO = (AdminComponentDAO) ApplicationContextFactory.getApplicationContext().getBean("adminComponentDAO");

    System.out.println("Loading DataElementConceptDAO bean");
    dataElementConceptDAO = (DataElementConceptDAO) ApplicationContextFactory.getApplicationContext().getBean("dataElementConceptDAO");

    System.out.println("Loading CDDAO bean");
    conceptualDomainDAO = (ConceptualDomainDAO) ApplicationContextFactory.getApplicationContext().getBean("conceptualDomainDAO");

    System.out.println("Loading VDDAO bean");
    valueDomainDAO = (ValueDomainDAO) ApplicationContextFactory.getApplicationContext().getBean("valueDomainDAO");

    System.out.println("Loading PropertyDAO bean");
    propertyDAO = (PropertyDAO) ApplicationContextFactory.getApplicationContext().getBean("propertyDAO");

    System.out.println("Loading ObjectClassDAO bean");
    objectClassDAO = (ObjectClassDAO) ApplicationContextFactory.getApplicationContext().getBean("objectClassDAO");

    System.out.println("Loading CSDAO bean");
    classificationSchemeDAO = (ClassificationSchemeDAO) ApplicationContextFactory.getApplicationContext().getBean("classificationSchemeDAO");

    System.out.println("Loading ContentObjectDAO bean");
    contentObjectDAO = (ContentObjectDAO) ApplicationContextFactory.getApplicationContext().getBean("contentObjectDAO");


  }

  public void persist() {
    // persist OC
    List ocs = (List)elements.getElements(ObjectClass.class);
    for(int i=0; i<ocs.size(); i++) {
      ObjectClass oc = (ObjectClass)ocs.get(0);
      
      // Does this OC exist?
      

    }


  }

  public void setContextName(String contextName) {
    this.contextName = contextName;
  }
  
}
