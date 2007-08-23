package gov.nih.nci.ncicb.cadsr.loader.util;

import gov.nih.nci.ncicb.xmiinout.handler.*;
import gov.nih.nci.ncicb.xmiinout.domain.*;

import org.apache.log4j.Logger;

public class DefinitionSplitter {
  
  private Logger logger = Logger.getLogger(DefinitionSplitter.class.getName());
  
  public void split(String filename) {
    XmiInOutHandler handler = null;
    UMLModel model = null;
    
    // find extension
    String ext = null;
    if(filename.indexOf(".") > 0)
      ext = filename.substring(filename.lastIndexOf(".") + 1);
    
    String s = filename.replaceAll("\\ ", "%20");
    
    // Some file systems use absolute URIs that do 
    // not start with '/'. 
    if(!s.startsWith("/"))
      s = "/" + s;    
    try {
      java.net.URI uri = new java.net.URI("file://" + s);
      
      
      HandlerEnum handlerEnum = null;
      if(ext != null && ext.equals("uml")) 
        handlerEnum = HandlerEnum.ArgoUMLDefault;
      else
        handlerEnum = HandlerEnum.EADefault;
      
      handler = XmiHandlerFactory.getXmiHandler(handlerEnum);
      handler.load(uri);
      model = handler.getModel();
      if(model == null) {
        logger.info("Can't open file with expected parser, will try another");
        if(handlerEnum.equals(HandlerEnum.EADefault))
          handlerEnum = HandlerEnum.ArgoUMLDefault;
        else
          handlerEnum = HandlerEnum.EADefault;
        
        handler = XmiHandlerFactory.getXmiHandler(handlerEnum);
        handler.load(uri);
        model = handler.getModel();
        if(model == null) {
          throw new RuntimeException("Can't open file. Unknown format.");
        } 
      } 
    } catch (Exception e) {
      throw new RuntimeException("Can't open file. Unknown format.");
    } // end of try-catch
      
      // save in memory for fast-save
    
    for(UMLPackage pkg : model.getPackages()) {
      doPackage(pkg);
    }
  }  

  private void doPackage(UMLPackage pkg) {
    for(UMLPackage subPkg : pkg.getPackages()) {
      doPackage(subPkg);
    }

    for(UMLClass clazz : pkg.getClasses()) {
      doClass(clazz);
    }
  }

  private void doClass(UMLClass clazz) {
    for(UMLAttribute att : clazz.getAttributes()) {
      doAttribute(att);
    }
  }
  
  private void doAttribute(UMLAttribute att) {
    for(UMLTaggedValue tv : att.getTaggedValues()) {

    }
  }


}