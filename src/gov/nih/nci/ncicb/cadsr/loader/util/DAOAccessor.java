package gov.nih.nci.ncicb.cadsr.loader.util;

import gov.nih.nci.ncicb.cadsr.spring.ApplicationContextFactory;
import gov.nih.nci.ncicb.cadsr.dao.*;

public class DAOAccessor {

  private static AdminComponentDAO adminComponentDAO;
  private static DataElementDAO dataElementDAO;
  private static DataElementConceptDAO dataElementConceptDAO;
  private static ValueDomainDAO valueDomainDAO;
  private static PropertyDAO propertyDAO;
  private static ObjectClassDAO objectClassDAO;
  private static ObjectClassRelationshipDAO objectClassRelationshipDAO;
  private static ClassificationSchemeDAO classificationSchemeDAO;
  private static ClassificationSchemeItemDAO classificationSchemeItemDAO;
  private static ConceptDAO conceptDAO;
  private static LoaderDAO loaderDAO;
  private static ContextDAO contextDAO;
  private static ConceptualDomainDAO conceptualDomainDAO;

  static {
    ApplicationContextFactory.init("applicationContext.xml");

    //     logger.debug("Loading DataElementDAO bean");
    dataElementDAO = (DataElementDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("dataElementDAO");

    //     logger.debug("Loading AdminComponentDAO bean");
    adminComponentDAO = (AdminComponentDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("adminComponentDAO");

    //     logger.debug("Loading DataElementConceptDAO bean");
    dataElementConceptDAO = (DataElementConceptDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("dataElementConceptDAO");


    //     logger.debug("Loading VDDAO bean");
    valueDomainDAO = (ValueDomainDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("valueDomainDAO");

    //     logger.debug("Loading PropertyDAO bean");
    propertyDAO = (PropertyDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("propertyDAO");

    //     logger.debug("Loading ObjectClassDAO bean");
    objectClassDAO = (ObjectClassDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("objectClassDAO");

    //     logger.debug("Loading ObjectClassRelationshipDAO bean");
    objectClassRelationshipDAO = (ObjectClassRelationshipDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("objectClassRelationshipDAO");

    //     logger.debug("Loading CSDAO bean");
    classificationSchemeDAO = (ClassificationSchemeDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("classificationSchemeDAO");

    //     logger.debug("Loading CSIDAO bean");
    classificationSchemeItemDAO = (ClassificationSchemeItemDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("classificationSchemeItemDAO");


    //     logger.debug("Loading ConceptDAO bean");
    conceptDAO = (ConceptDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("conceptDAO");


    contextDAO = (ContextDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("contextDAO");

    //     logger.debug("Loading CDDAO bean");
    conceptualDomainDAO = (ConceptualDomainDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("conceptualDomainDAO");

    //     logger.debug("Loading LoaderDAO bean");
    loaderDAO = (LoaderDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("loaderDAO");

  }

  public static AdminComponentDAO getAdminComponentDAO() {
    return adminComponentDAO;
  }

  public static DataElementDAO getDataElementDAO() {
    return dataElementDAO;
  }

  public static DataElementConceptDAO getDataElementConceptDAO() {
    return dataElementConceptDAO;
  }

  public static ValueDomainDAO getValueDomainDAO() {
    return valueDomainDAO;
  }

  public static PropertyDAO getPropertyDAO() {
    return propertyDAO;
  }

  public static ObjectClassDAO getObjectClassDAO() {
    return objectClassDAO;
  }

  public static ObjectClassRelationshipDAO getObjectClassRelationshipDAO() {
    return objectClassRelationshipDAO;
  }

  public static ClassificationSchemeDAO getClassificationSchemeDAO() {
    return classificationSchemeDAO;
  }

  public static ClassificationSchemeItemDAO getClassificationSchemeItemDAO() {
    return classificationSchemeItemDAO;
  }

  public static ConceptDAO getConceptDAO() {
    return conceptDAO;
  }

  public static LoaderDAO getLoaderDAO() {
    return loaderDAO;
  }

  public static ContextDAO getContextDAO() {
    return contextDAO;
  }

  public static ConceptualDomainDAO getConceptualDomainDAO() {
    return conceptualDomainDAO;
  }


}