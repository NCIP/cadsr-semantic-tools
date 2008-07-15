package gov.nih.nci.ncicb.cadsr.loader.parser;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.*;
import gov.nih.nci.ncicb.cadsr.loader.util.*;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;

import gov.nih.nci.ncicb.xmiinout.handler.*;
import gov.nih.nci.ncicb.xmiinout.domain.*;
import gov.nih.nci.ncicb.xmiinout.util.*;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * A writer for XMI files 
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class GMEWriter implements ElementWriter {

  private String output = null;

  private ElementsLists cadsrObjects = null;

  private ChangeTracker changeTracker = ChangeTracker.getInstance();

  private ProgressListener progressListener;
  
  private UMLModel model = null;
  private XmiInOutHandler handler = null;

  private Logger logger = Logger.getLogger(GMEWriter.class.getName());

  public void write(ElementsLists elements) throws ParserException {
    try {
      handler = (XmiInOutHandler)(UserSelections.getInstance().getProperty("XMI_HANDLER"));

      model = handler.getModel();

      this.cadsrObjects = elements;
    
      sendProgressEvent(0, 0, "Adding tags");

      addGmeTags();

      handler.save(output);

    } catch (Exception ex) {
      throw new RuntimeException("Error initializing model", ex);
    }

  }

  private void addGmeTags() {
    // do the project 
//     ClassificationScheme cs = UMLDefaults.getInstance().getProjectCs();
    String namespace = (String)UserSelections.getInstance().getProperty("GME_NAMESPACE");

    model.removeTaggedValue(XMIParser2.TV_GME_NAMESPACE);
    if(namespace != null) {
      model.addTaggedValue(XMIParser2.TV_GME_NAMESPACE, namespace);
    } else {
      
    }

    // now do the rest
    for(UMLPackage pkg : model.getPackages())
      doPackage(pkg);
    
  }

  private void doPackage(UMLPackage pkg) {
    AlternateName classAltName = DomainObjectFactory.newAlternateName();
    classAltName.setType(AlternateName.TYPE_CLASS_FULL_NAME);

    AlternateName attAltName = DomainObjectFactory.newAlternateName();
    attAltName.setType(AlternateName.TYPE_FULL_NAME);

    String pkgName = LookupUtil.getPackageName(pkg);
    boolean pkgChanged = changeTracker.get(pkgName);
    if(pkgChanged) {
      pkg.removeTaggedValue(XMIParser2.TV_GME_NAMESPACE);
      List<ClassificationSchemeItem> packages = cadsrObjects.getElements(DomainObjectFactory.newClassificationSchemeItem());
      for(ClassificationSchemeItem csi : packages) {
        if(csi.getLongName().equals(pkgName)) {
          for(AlternateName an : csi.getAlternateNames()) {
            if(an.getType().equals(AlternateName.TYPE_GME_NAMESPACE)) {
              pkg.addTaggedValue(XMIParser2.TV_GME_NAMESPACE, an.getName());
            } 
          }
        }
      }
    }

    for(UMLClass clazz : pkg.getClasses()) {
      String className = getPackageName(pkg) + "." + clazz.getName();

      boolean changed = changeTracker.get(className);
      if(changed) {
        // Remove all Class GME Tags
        clazz.removeTaggedValue(XMIParser2.TV_GME_NAMESPACE);
        clazz.removeTaggedValue(XMIParser2.TV_GME_XML_ELEMENT);

        classAltName.setName(className);
        ObjectClass oc = LookupUtil.lookupObjectClass(classAltName);
        if(oc != null)
          for(AlternateName an : oc.getAlternateNames()) {
            if(an.getType().equals(AlternateName.TYPE_GME_NAMESPACE)) {
              clazz.addTaggedValue(XMIParser2.TV_GME_NAMESPACE, an.getName());
            } else if(an.getType().equals(AlternateName.TYPE_GME_XML_ELEMENT)) {
              clazz.addTaggedValue(XMIParser2.TV_GME_XML_ELEMENT, an.getName());
            }
          }
      }
      
      
      for(UMLAttribute att : clazz.getAttributes()) {
        String attName = className + "." + att.getName();
        boolean attChanged = changeTracker.get(attName);
        if(attChanged) {
          // Remove all att GME Tags
          att.removeTaggedValue(XMIParser2.TV_GME_XML_LOC_REFERENCE);
        }
        attAltName.setName(attName);
        DataElement de = LookupUtil.lookupDataElement(attAltName);
        if(de != null)
          for(AlternateName an : de.getAlternateNames()) {
            if(an.getType().equals(AlternateName.TYPE_GME_XML_LOC_REF)) {
              att.addTaggedValue(XMIParser2.TV_GME_XML_LOC_REFERENCE, an.getName());
            }
          }
      }
    }

    for(UMLPackage subPkg : pkg.getPackages()) {
      doPackage(subPkg);
    }

    List<ObjectClassRelationship> ocrs = cadsrObjects.getElements(DomainObjectFactory.newObjectClassRelationship());

    List markedAsIgnored = (List)UserSelections.getInstance().getProperty("MARKED_IGNORED");
    
    // we're making the (correct) assumption that OCRs are stored in the same order as UMLAssociation in the XMI
    int xi = 0;
    OCRRoleNameBuilder nameBuilder = new OCRRoleNameBuilder();
    for(UMLAssociation assoc : model.getAssociations()) {
      if(markedAsIgnored.contains(assoc))
        continue;

      ObjectClassRelationship ocr = ocrs.get(xi++);
    
      String fullName = nameBuilder.buildRoleName(ocr);
      boolean ocrChanged = changeTracker.get(fullName);

      if(ocrChanged) {
        assoc.removeTaggedValue(XMIParser2.TV_GME_SOURCE_XML_LOC_REFERENCE);
        assoc.removeTaggedValue(XMIParser2.TV_GME_TARGET_XML_LOC_REFERENCE);
        
        for(AlternateName an : ocr.getAlternateNames()) {
          if(an.getType().equals(AlternateName.TYPE_GME_SRC_XML_LOC_REF)) {
            assoc.addTaggedValue(XMIParser2.TV_GME_SOURCE_XML_LOC_REFERENCE, an.getName());
          } else if(an.getType().equals(AlternateName.TYPE_GME_TARGET_XML_LOC_REF)) {
            assoc.addTaggedValue(XMIParser2.TV_GME_TARGET_XML_LOC_REFERENCE, an.getName());
          }
        }
      }
    }

  }

  private String getPackageName(UMLPackage pkg) {
    StringBuffer pack = new StringBuffer();
    String s = null;
    do {
      s = null;
      if(pkg != null) {
        s = pkg.getName(); 
        if(s.indexOf(" ") == -1) {
          if(pack.length() > 0)
            pack.insert(0, '.');
          pack.insert(0, s);
        }
        pkg = pkg.getParent();
      }
    } while (s != null);
    
    return pack.toString();
  }


  protected void sendProgressEvent(int status, int goal, String message) {
    if(progressListener != null) {
      ProgressEvent pEvent = new ProgressEvent();
      pEvent.setMessage(message);
      pEvent.setStatus(status);
      pEvent.setGoal(goal);
      
      progressListener.newProgressEvent(pEvent);
    }
  }

  public void setProgressListener(ProgressListener listener) {
    progressListener = listener;
  }
  public void setOutput(String url) {
    this.output = url;
  }

}
