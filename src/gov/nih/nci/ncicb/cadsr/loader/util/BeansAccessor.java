package gov.nih.nci.ncicb.cadsr.loader.util;

import java.io.FileInputStream;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;

import org.apache.log4j.Logger;

public class BeansAccessor {
  
  private static UserPreferences userPreferences;

  private static Logger logger = Logger.getLogger(BeansAccessor.class.getName());

  public static UserPreferences getUserPreferences() {

    if(userPreferences == null) {
      userPreferences = (UserPreferences)getFactory().getBean("userPreferences");
    }

    return userPreferences;
  }
  
  private static BeanFactory getFactory() {
    try {
      return new XmlBeanFactory( ClassLoader.getSystemResourceAsStream("beans.xml"));
    } catch (Exception e){
      logger.error(e.getMessage());
    } // end of try-catch
    return null;
  }

}