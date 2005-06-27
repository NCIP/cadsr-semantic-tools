package gov.nih.nci.ncicb.cadsr.loader.util;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.TreeBuilder;
import java.io.FileInputStream;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;

import org.apache.log4j.Logger;

import gov.nih.nci.ncicb.cadsr.loader.parser.ElementWriter;
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.ReviewTracker;

public class BeansAccessor {
  
  private static UserSelections userSelections;
  private static UserPreferences userPreferences;
  private static TreeBuilder treeBuilder;
  private static ReviewTracker reviewTracker;

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
  
  public static TreeBuilder getTreeBuilder() {

    if(treeBuilder == null) {
      treeBuilder = (TreeBuilder)getFactory().getBean("treeBuilder");
    }

    return treeBuilder;
  }

  public static ElementWriter getWriter() {
    RunMode mode = (RunMode)getUserSelections().getProperty("MODE");
    if(mode.equals(RunMode.Reviewer)) {
      return (ElementWriter)getFactory().getBean("xmiWriter");
    } else if(mode.equals(RunMode.Curator)) {
      return (ElementWriter)getFactory().getBean("csvWriter");
    }
    else return null;
      
  }
  
  public static ReviewTracker getReviewTracker() {
    if(reviewTracker == null) {
      reviewTracker = (ReviewTracker)getFactory().getBean("reviewTracker");
    }

    return reviewTracker;
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