package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.domain.*;

import org.apache.log4j.Logger;

import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

/**
 * Basic logging utility class
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class LogUtil {

  /**
   * logs info on an AC.
   *
   * @param ac an <code>AdminComponent</code> value
   * @param logger a <code>Logger</code> value
   */
  public static void logAc(AdminComponent ac, Logger logger) {
    if(ac == null)
      return;

    logger.info(PropertyAccessor.getProperty("id", ac.getId()));
    logger.info(PropertyAccessor.getProperty("longName", ac.getLongName()));
    logger.info(PropertyAccessor.getProperty("preferredName", ac.getPreferredName()));
    logger.info(PropertyAccessor.getProperty("version", new Float[] {ac.getVersion()}));
    logger.info(PropertyAccessor.getProperty("workflowStatus", ac.getWorkflowStatus()));
    logger.info(PropertyAccessor.getProperty("preferredDefinition", ac.getPreferredDefinition()));
  }

}