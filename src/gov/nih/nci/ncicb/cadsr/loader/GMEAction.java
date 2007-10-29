package gov.nih.nci.ncicb.cadsr.loader;

import gov.nih.nci.ncicb.cadsr.loader.parser.*;
import gov.nih.nci.ncicb.cadsr.loader.util.*;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.loader.ChangeTracker;

import java.util.*;

import java.net.URLEncoder;

public class GMEAction {

  private Parser parser;
  private ElementWriter writer;

  private UMLDefaults defaults = UMLDefaults.getInstance();
  protected ElementsLists elements = ElementsLists.getInstance();

  private ProgressListener progressListener = null;

  public void generateDefaults(String input, String output, String projectName, Float projectVesion, Context context) {

    ChangeTracker changeTracker = ChangeTracker.getInstance();

    String namespace = defaultNamespace(projectName, projectVesion, context);

    ProgressEvent pEvt = new ProgressEvent();
    pEvt.setGoal(0);
    pEvt.setMessage("Parsing ...");
    pEvt.setStatus(0);
    progressListener.newProgressEvent(pEvt);

    try {
      parser.parse(input);
    } catch (ParserException e) {
      throw new RuntimeException(e);
    } 

    List<ObjectClass> ocs = elements.getElements(DomainObjectFactory.newObjectClass());
    for(ObjectClass oc : ocs) {
      String className = LookupUtil.lookupFullName(oc);
      String pkgName = className;
      if(pkgName.indexOf(".") > 0) {
        pkgName = pkgName.substring(0, pkgName.lastIndexOf("."));

        if(!hasAltName(oc.getAlternateNames(), AlternateName.TYPE_GME_NAMESPACE)) {
          AlternateName altName = DomainObjectFactory.newAlternateName();
          altName.setType(AlternateName.TYPE_GME_NAMESPACE);
          altName.setName(namespace + "/" + pkgName);
          
          oc.addAlternateName(altName);
          changeTracker.put(className, true);
        }

        if(!hasAltName(oc.getAlternateNames(), AlternateName.TYPE_GME_XML_ELEMENT)) {
          AlternateName altName = DomainObjectFactory.newAlternateName();
          altName.setType(AlternateName.TYPE_GME_XML_ELEMENT);
          altName.setName(className.substring(className.lastIndexOf(".") + 1));
          
          oc.addAlternateName(altName);
          changeTracker.put(className, true);
        }
      }
    }

    List<DataElement> des = elements.getElements(DomainObjectFactory.newDataElement());
    for(DataElement de : des) {
      String attName = LookupUtil.lookupFullName(de);
      if(attName.indexOf(".") > 0) {
        
        if(!hasAltName(de.getAlternateNames(), AlternateName.TYPE_GME_XML_LOC_REF)) {
          AlternateName altName = DomainObjectFactory.newAlternateName();
          altName.setType(AlternateName.TYPE_GME_XML_LOC_REF);
          altName.setName(attName.substring(attName.lastIndexOf(".") + 1));
          de.addAlternateName(altName);
          
          changeTracker.put(attName, true);
        }
      }
    }

//     // do the project 
//     {
//       ClassificationScheme cs = UMLDefaults.getInstance().getProjectCs();
//       AlternateName altName = DomainObjectFactory.newAlternateName();
//       altName.setType(AlternateName.TYPE_GME_NAMESPACE);
//       altName.setName(namespace);
//       cs.addAlternateName(altName);
//     }
    UserSelections.getInstance().setProperty("GME_NAMESPACE", namespace);

    List<ClassificationSchemeItem> csis = elements.getElements(DomainObjectFactory.newClassificationSchemeItem());
    for(ClassificationSchemeItem csi : csis) {
      if(!hasAltName(csi.getAlternateNames(), AlternateName.TYPE_GME_NAMESPACE)) {
        AlternateName altName = DomainObjectFactory.newAlternateName();
        altName.setType(AlternateName.TYPE_GME_NAMESPACE);
        altName.setName(namespace + "/" + csi.getName());
        csi.addAlternateName(altName);
        
        changeTracker.put(csi.getName(), true);
      }
    }


    List<ObjectClassRelationship> ocrs = elements.getElements(DomainObjectFactory.newObjectClassRelationship());
    for(ObjectClassRelationship ocr : ocrs) {
      if(!hasAltName(ocr.getAlternateNames(), AlternateName.TYPE_GME_SRC_XML_LOC_REF)
         || !hasAltName(ocr.getAlternateNames(), AlternateName.TYPE_GME_TARGET_XML_LOC_REF)) {

        if(!StringUtil.isEmpty(ocr.getTargetRole())) {
          AlternateName altName = DomainObjectFactory.newAlternateName();
          altName.setType(AlternateName.TYPE_GME_TARGET_XML_LOC_REF);
          altName.setName(ocr.getTargetRole());
          ocr.addAlternateName(altName);
        }          

        if(!StringUtil.isEmpty(ocr.getSourceRole())) {
          AlternateName altName = DomainObjectFactory.newAlternateName();
          altName.setType(AlternateName.TYPE_GME_SRC_XML_LOC_REF);
          altName.setName(ocr.getSourceRole());
          ocr.addAlternateName(altName);
        }
        
        OCRRoleNameBuilder nameBuilder = new OCRRoleNameBuilder();
        String fullName = nameBuilder.buildRoleName(ocr);

        changeTracker.put(fullName, true);
      }
    }

    pEvt.setGoal(50);
    pEvt.setMessage("Writing ...");
    pEvt.setStatus(100);
    progressListener.newProgressEvent(pEvt);

    try {
      writer.setOutput(output);
      writer.write(elements);
    } catch (ParserException e) {
      throw new RuntimeException(e);
    } 
    
    pEvt.setGoal(100);
    pEvt.setMessage("Done");
    pEvt.setStatus(100);
    pEvt.setCompleted(true);
    progressListener.newProgressEvent(pEvt);

  }

  // does this list contain alt name of type "type"
  private boolean hasAltName(List<AlternateName> altNames, String type) {
    for(AlternateName an : altNames) {
      if(an.getType().equals(type)) 
        return true;
    }
    return false;
  }

  public void cleanup(String input, String output) {
    ChangeTracker changeTracker = ChangeTracker.getInstance();

    ProgressEvent pEvt = new ProgressEvent();
    pEvt.setGoal(0);
    pEvt.setMessage("Parsing ...");
    pEvt.setStatus(0);
    progressListener.newProgressEvent(pEvt);

    try {
      parser.parse(input);
    } catch (ParserException e) {
      throw new RuntimeException(e);
    } 

    List<ObjectClass> ocs = elements.getElements(DomainObjectFactory.newObjectClass());
    for(ObjectClass oc : ocs) {
      String className = LookupUtil.lookupFullName(oc);
      oc.removeAlternateNames();
      
      changeTracker.put(className, true);
    }

    List<DataElement> des = elements.getElements(DomainObjectFactory.newDataElement());
    for(DataElement de : des) {
      String attName = LookupUtil.lookupFullName(de);
      de.removeAlternateNames();

      changeTracker.put(attName, true);
    }

    List<ClassificationSchemeItem> csis = elements.getElements(DomainObjectFactory.newClassificationSchemeItem());
    for(ClassificationSchemeItem csi : csis) {
      csi.removeAlternateNames();
      
      changeTracker.put(csi.getName(), true);
    }
    

    try {
      writer.setOutput(output);
      writer.write(elements);
    } catch (ParserException e) {
      throw new RuntimeException(e);
    } 
    
    pEvt.setGoal(100);
    pEvt.setMessage("Done");
    pEvt.setStatus(100);
    pEvt.setCompleted(true);
    progressListener.newProgressEvent(pEvt);

  }


  private String defaultNamespace(String projectName, Float projectVersion, Context context) {
    try {
      String namespace = 
        "gme://" + URLEncoder.encode(projectName, "UTF-8") + "." + URLEncoder.encode(context.getName(), "UTF-8") + "/" + projectVersion;

      return namespace;
    } catch (java.io.UnsupportedEncodingException e) {
      // not gonna happen (UTF-8)
    } 
    return null;
    
  }

  public void setParser(Parser parser) {
    this.parser = parser;
  }

  public void setWriter(ElementWriter writer) {
    this.writer = writer;
  }

  public void addProgressListener(ProgressListener l) {
    progressListener = l;
  }

}