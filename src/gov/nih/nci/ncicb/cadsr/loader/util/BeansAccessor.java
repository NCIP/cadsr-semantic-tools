package gov.nih.nci.ncicb.cadsr.loader.util;

import java.io.FileInputStream;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;

import org.apache.log4j.Logger;

import gov.nih.nci.ncicb.cadsr.loader.UserSelections;

public class BeansAccessor {
  
  private static UserSelections userSelections;
  private static UserPreferences userPreferences;

  private static Logger logger = Logger.getLogger(BeansAccessor.class.getName());

  public static UserSelections getUserSelections() {

    if(userSelections == null) {
      userSelections = (UserSelections)getFactory().getBean("userSelections");
    }

    return userSelections;
  }

  public static UserPreferences getUserPreferences() {

    if(userPreferences == null) {
      userPreferences = (UserPreferences)getFactory().getBean("userPreferences");
    }

    return userPreferences;
  }
  
  private static BeanFactory getFactory() {
    try {
      return new XmlBeanFactory(Thread.currentThread().getContextClassLoader().getResourceAsStream("beans.xml"));
    } catch (Exception e){
      logger.error(e.getMessage());
    } // end of try-catch
    return null;
  }

}