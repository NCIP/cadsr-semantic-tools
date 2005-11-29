/*
 * Copyright 2000-2003 Oracle, Inc. This software was developed in conjunction with the National Cancer Institute, and so to the extent government employees are co-authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105.
 *
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the disclaimer of Article 3, below. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 2. The end-user documentation included with the redistribution, if any, must include the following acknowledgment:
 *
 * "This product includes software developed by Oracle, Inc. and the National Cancer Institute."
 *
 * If no such end-user documentation is to be included, this acknowledgment shall appear in the software itself, wherever such third-party acknowledgments normally appear.
 *
 * 3. The names "The National Cancer Institute", "NCI" and "Oracle" must not be used to endorse or promote products derived from this software.
 *
 * 4. This license does not authorize the incorporation of this software into any proprietary programs. This license does not authorize the recipient to use any trademarks owned by either NCI or Oracle, Inc.
 *
 * 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, ORACLE, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 */
package gov.nih.nci.ncicb.cadsr.loader.util;

import gov.nih.nci.ncicb.cadsr.spring.ApplicationContextFactory;
import gov.nih.nci.ncicb.cadsr.dao.*;

import org.apache.log4j.Logger;


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
  private static ClassSchemeClassSchemeItemDAO classSchemeClassSchemeItemDAO;
  private static ConceptDAO conceptDAO;
  private static LoaderDAO loaderDAO;
  private static ContextDAO contextDAO;
  private static ConceptualDomainDAO conceptualDomainDAO;

  private static Logger logger = Logger.getLogger(DAOAccessor.class.getName());

  static {
    ApplicationContextFactory.init("applicationContext.xml");

    logger.info("Loading DataElementDAO bean");
    dataElementDAO = (DataElementDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("dataElementDAO");

    logger.info("Loading AdminComponentDAO bean");
    adminComponentDAO = (AdminComponentDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("adminComponentDAO");

    logger.info("Loading DataElementConceptDAO bean");
    dataElementConceptDAO = (DataElementConceptDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("dataElementConceptDAO");


    logger.info("Loading VDDAO bean");
    valueDomainDAO = (ValueDomainDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("valueDomainDAO");

    logger.info("Loading PropertyDAO bean");
    propertyDAO = (PropertyDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("propertyDAO");

    logger.info("Loading ObjectClassDAO bean");
    objectClassDAO = (ObjectClassDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("objectClassDAO");

    logger.info("Loading ObjectClassRelationshipDAO bean");
    objectClassRelationshipDAO = (ObjectClassRelationshipDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("objectClassRelationshipDAO");

    logger.info("Loading CSDAO bean");
    classificationSchemeDAO = (ClassificationSchemeDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("classificationSchemeDAO");

    logger.info("Loading CSIDAO bean");
    classificationSchemeItemDAO = (ClassificationSchemeItemDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("classificationSchemeItemDAO");

    logger.info("Loading CSIDAO bean");
    classSchemeClassSchemeItemDAO = (ClassSchemeClassSchemeItemDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("classSchemeClassSchemeItemDAO");

    logger.info("Loading ConceptDAO bean");
    conceptDAO = (ConceptDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("conceptDAO");

    logger.info("Loading ContextDAO bean");
    contextDAO = (ContextDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("contextDAO");

    logger.info("Loading CDDAO bean");
    conceptualDomainDAO = (ConceptualDomainDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("conceptualDomainDAO");

    logger.info("Loading LoaderDAO bean");
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

  public static ClassSchemeClassSchemeItemDAO getClassSchemeClassSchemeItemDAO() {
    return classSchemeClassSchemeItemDAO;
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