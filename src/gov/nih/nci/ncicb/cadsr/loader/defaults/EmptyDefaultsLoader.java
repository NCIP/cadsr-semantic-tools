package gov.nih.nci.ncicb.cadsr.loader.defaults;

import gov.nih.nci.ncicb.cadsr.domain.*;

import org.apache.log4j.Logger;

import java.util.*;

import java.io.FileInputStream;
import java.io.File;

public class EmptyDefaultsLoader  {

  private static Logger logger = Logger.getLogger(EmptyDefaultsLoader.class.getName());

  public LoaderDefault loadDefaults() {

    try {
      LoaderDefault loaderDefault = DomainObjectFactory.newLoaderDefault();

      logger.debug("No properties found -- Loading Empty Defaults.");

      loaderDefault.setProjectName("");
      loaderDefault.setProjectVersion(1f);
      loaderDefault.setContextName("TEST");
      loaderDefault.setVersion(1f);
      loaderDefault.setWorkflowStatus("DRAFT NEW");
      loaderDefault.setProjectLongName("");
      loaderDefault.setProjectDescription("Please Describe your project");
      loaderDefault.setCdName("UML_DEFAULT_CD");
      loaderDefault.setCdContextName("caCORE");

      loaderDefault.setPackageFilter("<Classes>");

      return loaderDefault;
    } catch (Exception e){
      e.printStackTrace();
      logger.fatal(e);
      return null;
    } // end of try-catch

  }

}