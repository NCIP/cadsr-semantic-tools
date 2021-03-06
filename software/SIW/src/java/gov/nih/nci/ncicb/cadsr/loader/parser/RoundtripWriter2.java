/*
 * Copyright 2000-2005 Oracle, Inc. This software was developed in conjunction with the National Cancer Institute, and so to the extent government employees are co-authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105.
 *
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the disclaimer of Article 3, below. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 2. The end-user documentation included with the redistribution, if any, must include the following acknowledgment:
 *
 * "This product includes software developed by Oracle, Inc. and the National Cancer Institute."
 *
 * If no such end-user documentation is to be included, this acknowledgment shall appear in the software itself, wherever such third-party acknowledgments normally appear.
 *
 * 3. The names "The National Cancer Institute", "NCI" and "Oracle" must not be used to endorse or promote products derived from this software.
 *
 * 4. This license does not authorize the incorporation of this software into any proprietary programs. This license does not authorize the recipient to use any trademarks owned by either NCI or Oracle, Inc.
 *
 * 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, ORACLE, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 */
package gov.nih.nci.ncicb.cadsr.loader.parser;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.*;
import gov.nih.nci.ncicb.cadsr.loader.util.*;

import gov.nih.nci.ncicb.cadsr.loader.event.NewConceptEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

import gov.nih.nci.ncicb.xmiinout.handler.*;
import gov.nih.nci.ncicb.xmiinout.domain.*;
import gov.nih.nci.ncicb.xmiinout.util.*;


/**
 * A writer for XMI files 
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class RoundtripWriter2 implements ElementWriter {

  private String output = null;

  private String input = null;

  private ElementsLists elementsList = null;

  private ReviewTracker ownerReviewTracker = ReviewTracker.getInstance(ReviewTrackerType.Owner), 
    curatorReviewTracker = ReviewTracker.getInstance(ReviewTrackerType.Curator);

  private ChangeTracker changeTracker = ChangeTracker.getInstance();

  private ProgressListener progressListener = null;

  private static Logger logger = Logger.getLogger(RoundtripWriter.class.getName());

  private HashMap<String, UMLClass> classMap = new HashMap<String, UMLClass>();
  private HashMap<String, UMLAttribute> attributeMap = new HashMap<String, UMLAttribute>();
  private HashMap<String, UMLAssociation> assocMap = new HashMap<String, UMLAssociation>();
  private HashMap<String, UMLPackage> packageMap = new HashMap<String, UMLPackage>();

  private UMLModel model = null;
  
  public RoundtripWriter2(String inputFile) {
    this.input = inputFile;
    try {
      ProgressEvent pEvt = new ProgressEvent();
      pEvt.setGoal(-1);
      pEvt.setMessage("Opening File");
      if(progressListener != null)
        progressListener.newProgressEvent(pEvt);

//       SAXBuilder builder = new SAXBuilder();
//       Document doc = builder.build(input);
//       modelElement = doc.getRootElement();
    } catch (Exception ex) {
      throw new RuntimeException("Error initializing model", ex);
    }

  }

  public void write(ElementsLists elements) {

    try {
      XmiInOutHandler handler = (XmiInOutHandler)(UserSelections.getInstance().getProperty("XMI_HANDLER"));
      model = handler.getModel();
      
      this.elementsList = elements;
      
      readModel();
      updateElements();
      
      handler.save(output);
    } catch (Exception ex) {
      throw new RuntimeException("Error initializing model", ex);
    }

  }

  public void setProgressListener(ProgressListener l) {
    progressListener = l;
  }

  public void setOutput(String url) {
    this.output = url;
  }

  public void setInput(String url) {
    this.input = url;
  }

  private void readModel(){
    for(UMLPackage pkg : model.getPackages()) {
      doPackage(pkg);
    }

    int i = 0;
    for(UMLAssociation assoc : model.getAssociations()) {
 
        assocMap.put(String.valueOf(i), assoc);
        
        i++;
    }

  }

  private void updateElements() {
    List<DataElement> des = elementsList.getElements(DomainObjectFactory.newDataElement());
    List<ObjectClass> ocs = elementsList.getElements(DomainObjectFactory.newObjectClass());
    List<ObjectClassRelationship> ocrs = elementsList.getElements(DomainObjectFactory.newObjectClassRelationship());
    List<ClassificationSchemeItem> csis = elementsList.getElements(DomainObjectFactory.newClassificationSchemeItem());


    ProgressEvent pEvt = new ProgressEvent();
    pEvt.setGoal(des.size() + ocs.size() + ocrs.size() + csis.size());
    pEvt.setMessage("Injecting CaDSR Public IDs");
    pEvt.setStatus(0);
    if(progressListener != null)
      progressListener.newProgressEvent(pEvt);
      
    InheritedAttributeList inheritedList = InheritedAttributeList.getInstance();

    for(DataElement de : des) {
      pEvt.setStatus(pEvt.getStatus() + 1);
      if(progressListener != null)
        progressListener.newProgressEvent(pEvt);

      DataElementConcept dec = de.getDataElementConcept();
      String fullPropName = null;
        
      for(AlternateName an : de.getAlternateNames()) {
        if(an.getType().equals(AlternateName.TYPE_FULL_NAME))
          fullPropName = an.getName();
      }
        
      if(!inheritedList.isInherited(de)) {
        UMLAttribute att = attributeMap.get(fullPropName);
        if(att == null) {
          logger.info("Parser Can't find attribute: " + fullPropName + "\n Probably inherited. That should be ok.");
          continue;
        }
        
        // remove all TVs for DE_ID and Version
        Collection<UMLTaggedValue> allTvs = att.getTaggedValues();
        for(UMLTaggedValue tv : allTvs) {
          if(tv.getName().startsWith("CADSR_DE"))
            att.removeTaggedValue(tv.getName());
        }
        
        if(!StringUtil.isEmpty(de.getPublicId())) {
          
          att.addTaggedValue(XMIParser2.TV_DE_ID,
                             de.getPublicId());
          att.addTaggedValue(XMIParser2.TV_DE_VERSION,
                             de.getVersion().toString());
        }

        String gmeTag = LookupUtil.lookupXMLLocRef(de);
        if(gmeTag != null) {
          UMLTaggedValue gmeTv = att.getTaggedValue(XMIParser2.TV_GME_XML_LOC_REFERENCE);
          // only add a tagged value if one is not already there.
          // in other words, don't replace
          if(gmeTv == null) {
            att.addTaggedValue(XMIParser2.TV_GME_XML_LOC_REFERENCE, 
                               gmeTag);
          }
        }

      } else { // in case of inherited attribute
        ObjectClass oc = de.getDataElementConcept().getObjectClass();
        String fullClassName = LookupUtil.lookupFullName(oc);
          
        UMLClass clazz = classMap.get(fullClassName);
        
        String attributeName = LookupUtil.lookupFullName(de);
        attributeName = attributeName.substring(attributeName.lastIndexOf(".") + 1);
          
        if(!StringUtil.isEmpty(de.getPublicId())) {
          clazz.removeTaggedValue(XMIParser2.TV_INHERITED_DE_ID.replace("{1}", attributeName));
          clazz.removeTaggedValue(XMIParser2.TV_INHERITED_DE_VERSION.replace("{1}", attributeName));
          clazz.removeTaggedValue(XMIParser2.TV_INHERITED_VD_ID.replace("{1}", attributeName));
          clazz.removeTaggedValue(XMIParser2.TV_INHERITED_VD_VERSION.replace("{1}", attributeName));
          
          clazz.addTaggedValue(XMIParser2.TV_INHERITED_DE_ID.replace("{1}", attributeName), de.getPublicId());
          clazz.addTaggedValue(XMIParser2.TV_INHERITED_DE_VERSION.replace("{1}", attributeName), de.getVersion().toString());
        }
      }
    }

    for(ObjectClass oc : ocs) {
      pEvt.setStatus(pEvt.getStatus() + 1);
      if(progressListener != null)
        progressListener.newProgressEvent(pEvt);

      String className = LookupUtil.lookupFullName(oc);
        
      UMLClass clazz = classMap.get(className);
      if(clazz == null) {
        logger.info("Parser Can't find clazz: " + className + "\n This is odd, look into it.");
        continue;
      }
        
      String gmeTag = LookupUtil.lookupXMLNamespace(oc);
      if(gmeTag != null) {
        UMLTaggedValue gmeTv = clazz.getTaggedValue(XMIParser2.TV_GME_NAMESPACE);
        // only add a tagged value if one is not already there.
        // in other words, don't replace
        if(gmeTv == null) {
          clazz.addTaggedValue(XMIParser2.TV_GME_NAMESPACE, 
                             gmeTag);
        }
      }

      gmeTag = LookupUtil.lookupXMLElementName(oc);
      if(gmeTag != null) {
        UMLTaggedValue gmeTv = clazz.getTaggedValue(XMIParser2.TV_GME_XML_ELEMENT);
        // only add a tagged value if one is not already there.
        // in other words, don't replace
        if(gmeTv == null) {
          clazz.addTaggedValue(XMIParser2.TV_GME_XML_ELEMENT, 
                             gmeTag);
        }
      }
    } 
 
    for(int i = 0; i < ocrs.size(); i++) {
      pEvt.setStatus(pEvt.getStatus() + 1);
      if(progressListener != null)
        progressListener.newProgressEvent(pEvt);

      ObjectClassRelationship ocr = ocrs.get(i);

      UMLAssociation assoc = assocMap.get(String.valueOf(i));

      String gmeTag = LookupUtil.lookupXMLSrcLocRef(ocr);
      if(gmeTag != null) {
        UMLTaggedValue gmeTv = assoc.getTaggedValue(XMIParser2.TV_GME_SOURCE_XML_LOC_REFERENCE);
        // only add a tagged value if one is not already there.
        // in other words, don't replace
        if(gmeTv == null) {
          assoc.addTaggedValue(XMIParser2.TV_GME_SOURCE_XML_LOC_REFERENCE, 
                             gmeTag);
        }
      }

      gmeTag = LookupUtil.lookupXMLTargetLocRef(ocr);
      if(gmeTag != null) {
        UMLTaggedValue gmeTv = assoc.getTaggedValue(XMIParser2.TV_GME_TARGET_XML_LOC_REFERENCE);
        // only add a tagged value if one is not already there.
        // in other words, don't replace
        if(gmeTv == null) {
          assoc.addTaggedValue(XMIParser2.TV_GME_TARGET_XML_LOC_REFERENCE, 
                             gmeTag);
        }
      }
    } 

    for(ClassificationSchemeItem csi : csis) {
      pEvt.setStatus(pEvt.getStatus() + 1);
      if(progressListener != null)
        progressListener.newProgressEvent(pEvt);

      UMLPackage pkg = packageMap.get(csi.getLongName());
      if(pkg == null) {
        logger.info("Parser Can't find Package: " + csi.getLongName() + "\n This shouldn't happend");
        continue;
      }
        
      String gmeTag = LookupUtil.lookupXMLNamespace(csi);
      if(gmeTag != null) {
        UMLTaggedValue gmeTv = pkg.getTaggedValue(XMIParser2.TV_GME_NAMESPACE);
        // only add a tagged value if one is not already there.
        // in other words, don't replace
        if(gmeTv == null) {
          pkg.addTaggedValue(XMIParser2.TV_GME_NAMESPACE, 
                             gmeTag);
        }
      }
    }

    

  }
  
  private void doPackage(UMLPackage pkg) {
 
    packageMap.put(getPackageName(pkg), pkg);

    for(UMLClass clazz : pkg.getClasses()) {
      String className = null;

      String st = clazz.getStereotype();
      boolean foundVd = false;
      if(st != null)
        for(int i=0; i < XMIParser2.validVdStereotypes.length; i++) {
          if(st.equalsIgnoreCase(XMIParser2.validVdStereotypes[i])) foundVd = true;
        }
      if(foundVd) {
        className = "ValueDomains." + clazz.getName();
      } else {
        className = getPackageName(pkg) + "." + clazz.getName();
      }        
      classMap.put(className, clazz);
      for(UMLAttribute att : clazz.getAttributes()) {
        attributeMap.put(className + "." + att.getName(), att);
      }
    }

    for(UMLPackage subPkg : pkg.getPackages()) {
      doPackage(subPkg);
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

}