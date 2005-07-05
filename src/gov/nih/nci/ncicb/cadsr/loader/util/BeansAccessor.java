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
  
  private static Logger logger = Logger.getLogger(BeansAccessor.class.getName());

  private static XmlBeanFactory factory = null;

  public static ElementWriter getWriter() {
    RunMode mode = (RunMode)UserSelections.getInstance().getProperty("MODE");
    if(mode.equals(RunMode.Reviewer)) {
      return (ElementWriter)getFactory().getBean("xmiWriter");
    } else if(mode.equals(RunMode.Curator)) {
      return (ElementWriter)getFactory().getBean("csvWriter");
    }
    else return null;
      
  }
    
  private static BeanFactory getFactory() {
    try {
      if(factory != null)
        return factory;
      return new XmlBeanFactory(Thread.currentThread().getContextClassLoader().getResourceAsStream("beans.xml"));
    } catch (Exception e){
      logger.error(e.getMessage());
    } // end of try-catch
    return null;
  }
  
}