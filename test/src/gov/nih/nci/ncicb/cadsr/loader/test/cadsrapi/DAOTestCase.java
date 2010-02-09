package gov.nih.nci.ncicb.cadsr.loader.test.cadsrapi;

import gov.nih.nci.ncicb.cadsr.dao.AdminComponentDAO;
import gov.nih.nci.ncicb.cadsr.dao.ClassificationSchemeDAO;
import gov.nih.nci.ncicb.cadsr.dao.ClassificationSchemeItemDAO;
import gov.nih.nci.ncicb.cadsr.dao.ConceptDAO;
import gov.nih.nci.ncicb.cadsr.dao.ConceptualDomainDAO;
import gov.nih.nci.ncicb.cadsr.dao.ContextDAO;
import gov.nih.nci.ncicb.cadsr.dao.DataElementConceptDAO;
import gov.nih.nci.ncicb.cadsr.dao.DataElementDAO;
import gov.nih.nci.ncicb.cadsr.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.dao.ObjectClassDAO;
import gov.nih.nci.ncicb.cadsr.dao.ObjectClassRelationshipDAO;
import gov.nih.nci.ncicb.cadsr.dao.PropertyDAO;
import gov.nih.nci.ncicb.cadsr.dao.ValueDomainDAO;
import gov.nih.nci.ncicb.cadsr.domain.Context;
import gov.nih.nci.ncicb.cadsr.spring.ApplicationContextFactory;
import junit.framework.TestCase;


public class DAOTestCase extends TestCase
{

  static {
    String[] files = {"spring-datasources.xml","applicationContext.xml"};
    ApplicationContextFactory.init(files);
  }
  
  protected static AdminComponentDAO adminComponentDAO;
  protected static DataElementDAO dataElementDAO;
  protected static ContextDAO contextDAO;
  protected static DataElementConceptDAO dataElementConceptDAO;
  protected static ValueDomainDAO valueDomainDAO; 
  protected static ConceptualDomainDAO conceptualDomainDAO;
  protected static PropertyDAO propertyDAO;
  protected static ConceptDAO conceptDAO;
  protected static ObjectClassDAO objectClassDAO;
  protected static ClassificationSchemeDAO classificationSchemeDAO;
  protected static ClassificationSchemeItemDAO classificationSchemeItemDAO;
  protected static ObjectClassRelationshipDAO objectClassRelationshipDAO;
  protected static FormDAO formDAO;
  

  static {
  
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

    System.out.println("Loading ConceptDAO bean");
    conceptDAO = (ConceptDAO) ApplicationContextFactory.getApplicationContext().getBean("conceptDAO");

    System.out.println("Loading ObjectClassDAO bean");
    objectClassDAO = (ObjectClassDAO) ApplicationContextFactory.getApplicationContext().getBean("objectClassDAO");

    System.out.println("Loading CSDAO bean");
    classificationSchemeDAO = (ClassificationSchemeDAO) ApplicationContextFactory.getApplicationContext().getBean("classificationSchemeDAO");

    System.out.println("Loading ContentObjectDAO bean");
    formDAO = (FormDAO) ApplicationContextFactory.getApplicationContext().getBean("formDAO");
  
    System.out.println("Loading ObjectClassRelationshipDAO bean");
    objectClassRelationshipDAO = (ObjectClassRelationshipDAO) ApplicationContextFactory.getApplicationContext().getBean("objectClassRelationshipDAO");

    System.out.println("Loading ClassificationSchemeItemDAO bean");
    classificationSchemeItemDAO = (ClassificationSchemeItemDAO) ApplicationContextFactory.getApplicationContext().getBean("classificationSchemeItemDAO");
  }

  public DAOTestCase() {

  }

  public DAOTestCase(String name) {
    super(name);
  }

  protected void setUp() throws Exception
  {
    


  }

  protected Context getContext(String name) {
    
    return contextDAO.findByName(name);

  }



}