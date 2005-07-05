package gov.nih.nci.ncicb.cadsr.loader.util;

import java.util.*;
import java.text.MessageFormat;

import java.io.*;

import org.apache.log4j.Logger;

public class PropertyAccessor {
  
  private static Logger logger = Logger.getLogger(PropertyAccessor.class.getName());

  private static Properties properties;

  private static PropertyAccessor instance = new PropertyAccessor();

  private PropertyAccessor() {
    String filename = "resources.properties";
    properties = new Properties();
    try {
      properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(filename));
    } catch (IOException e){
      logger.fatal("Resource Properties could not be loaded (" + filename + "). Exiting now.");
      logger.fatal(e.getMessage());
      System.exit(1);
    } // end of try-catch
  }

  public static String getProperty(String key) {
    return properties.getProperty(key);
  }

  public static String getProperty(String key, Object[] args) {
    return MessageFormat.format(properties.getProperty(key), args);
  }

  public static String getProperty(String key, String arg) {
    return getProperty(key, new Object[]{arg});
  }

  public static String getProperty(String key, int arg) {
    return getProperty(key, new Object[]{new Integer(arg)});
  }

}