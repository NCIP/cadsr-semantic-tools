package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.domain.*;


import org.apache.log4j.Logger;

public class LogUtil {

  public static void logAc(AdminComponent ac, Logger logger) {
    logger.info("-- ID: " + ac.getId());
    logger.info("-- Long_Name: " + ac.getLongName());
    logger.info("-- Preferred_Name: " + ac.getPreferredName());
    logger.info("-- Version: " + ac.getVersion());
    logger.info("-- Workflow Status: " + ac.getWorkflowStatus());
    logger.info("-- Preferred_Definition: " + ac.getPreferredDefinition());
  }

}