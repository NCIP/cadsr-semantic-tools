package gov.nih.nci.ncicb.cadsr.loader.util;

import gov.nih.nci.ncicb.xmiinout.handler.*;
import gov.nih.nci.ncicb.xmiinout.domain.*;

import org.apache.log4j.Logger;

import java.util.*;

public class DefinitionSplitter {
  
  private Logger logger = Logger.getLogger(DefinitionSplitter.class.getName());

  private String DEFAULT_SEPARATOR = "_";

  public void split(String filename, String output) {
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

//     handler.save(output);
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
    List<UMLTaggedValue> redoTvs = new ArrayList<UMLTaggedValue>();
    for(UMLTaggedValue tv : att.getTaggedValues()) {
      if(
        tv.getName().equals("documentation")
        || tv.getName().equals("description")
        || tv.getName().matches(".*ConceptDefinition[23456789]?")
        ) {
 
        if(tv.getValue().length() > 255) {
          redoTvs.add(tv);
        }
      }
    }

    for(UMLTaggedValue tv : redoTvs) {
      att.removeTaggedValue(tv.getName());
      addSplitTaggedValue(att, tv.getName(), tv.getValue(), DEFAULT_SEPARATOR);
    }
  }

  public static void addSplitTaggedValue(UMLTaggableElement elt, String tag, String value, String separator) 
  {  

    final int MAX_TV_SIZE = 255;

    if(value.length() > MAX_TV_SIZE) {
      int nbOfTags = (int)(Math.ceil((double)value.length() / (double)MAX_TV_SIZE));

      for(int i = 0; i < nbOfTags; i++) {
        String thisTag = (i==0)?tag:tag + separator + (i+1);

        int index = i*MAX_TV_SIZE;
        
        String thisValue = (index + MAX_TV_SIZE > value.length())?value.substring(index):value.substring(index, index + MAX_TV_SIZE);
        
        elt.addTaggedValue(thisTag, thisValue);
      }
      


    } else {
      elt.addTaggedValue(tag, value);
    }
  }

}