package gov.nih.nci.ncicb.cadsr.loader.defaults;

import gov.nih.nci.ncicb.cadsr.domain.*;

import org.apache.log4j.Logger;

import java.util.*;

import java.io.FileInputStream;

public class AttachedFileDefaultsLoader  {

  private static Logger logger = Logger.getLogger(AttachedFileDefaultsLoader.class.getName());

  public LoaderDefault loadDefaults(String modelFileName) {

    try {
      LoaderDefault loaderDefault = DomainObjectFactory.newLoaderDefault();
      Properties props = new Properties();

      int ind = modelFileName.lastIndexOf(".xmi");
      String propsFileName = modelFileName.substring(0, ind);
      propsFileName = propsFileName + ".properties";

      logger.debug("loading prop file: " + propsFileName);

      props.load(new FileInputStream(propsFileName));
//       props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("file://" + propsFileName));

      logger.debug("loaded");

      loaderDefault.setProjectName(props.getProperty("projectName"));
      loaderDefault.setProjectVersion(new Float(props.getProperty("projectVersion")));
      loaderDefault.setContextName(props.getProperty("contextName"));
      loaderDefault.setVersion(new Float(props.getProperty("version")));
      loaderDefault.setWorkflowStatus(props.getProperty("workflowStatus"));
      loaderDefault.setProjectLongName(props.getProperty("projectLongName"));
      loaderDefault.setProjectDescription(props.getProperty("projectDescription"));
      loaderDefault.setCdName(props.getProperty("cdName"));
      loaderDefault.setCdContextName(props.getProperty("cdContextName"));

      loaderDefault.setPackageFilter(props.getProperty("packageFilter"));

      return loaderDefault;
    } catch (Exception e){
      e.printStackTrace();
      logger.fatal(e);
      return null;
    } // end of try-catch

  }

}