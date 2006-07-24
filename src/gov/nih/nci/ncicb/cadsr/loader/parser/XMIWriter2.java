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
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;

import gov.nih.nci.ncicb.cadsr.loader.event.NewConceptEvent;

import gov.nih.nci.ncicb.xmiinout.handler.*;
import gov.nih.nci.ncicb.xmiinout.domain.*;

import java.io.*;
import java.util.*;

/**
 * A writer for XMI files 
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class XMIWriter2 implements ElementWriter {

  private String output = null;

  private UserSelections userSelections = UserSelections.getInstance();

  private HashMap<String, UMLClass> classMap = new HashMap<String, UMLClass>();
  private HashMap<String, UMLAttribute> attributeMap = new HashMap<String, UMLAttribute>();

  private ElementsLists cadsrObjects = null;

  private ReviewTracker reviewTracker = ReviewTracker.getInstance();
  private ChangeTracker changeTracker = ChangeTracker.getInstance();

  private ProgressListener progressListener;
  
  UMLModel model = null;
  XmiInOutHandler handler = null;

  public XMIWriter2() {
    try {
      handler = XmiHandlerFactory.getXmiHandler(HandlerEnum.EADefault);
      

    } catch (Exception ex) {
      throw new RuntimeException("Error initializing model", ex);
    }
  }

  public void write(ElementsLists elements) throws ParserException {
    try {
      String input = (String)userSelections.getProperty("FILENAME");
      String s = input.replaceAll("\\ ", "%20");
    
      // Some file systems use absolute URIs that do 
      // not start with '/'. 
      if(!s.startsWith("/"))
        s = "/" + s;    
      java.net.URI uri = new java.net.URI("file://" + s);
      handler.load(uri);
      model = handler.getModel("EA Model");

      this.cadsrObjects = elements;
    
      sendProgressEvent(0, 0, "Parsing Model");
      readModel();
      sendProgressEvent(0, 0, "Marking Human reviewed");
      markHumanReviewed();
      sendProgressEvent(0, 0, "Updating Elements");
      updateChangedElements();
      sendProgressEvent(0, 0, "ReWriting Model");
      handler.save(output);

    } catch (Exception ex) {
      throw new RuntimeException("Error initializing model", ex);
    }

  }

  public void setOutput(String url) {
    this.output = url;
  }

  public void setProgressListener(ProgressListener listener) {
    progressListener = listener;
  }


  
  private void readModel() {
    for(UMLPackage pkg : model.getPackages())
      doPackage(pkg);
  }

  private void updateChangedElements()  {
    List<ObjectClass> ocs = cadsrObjects.getElements(DomainObjectFactory.newObjectClass());
    List<DataElement> des = cadsrObjects.getElements(DomainObjectFactory.newDataElement());
    List<ValueDomain> vds = cadsrObjects.getElements(DomainObjectFactory.newValueDomain());
    
    int goal = ocs.size() + des.size() + vds.size();
    int status = 0;
    sendProgressEvent(status, goal, "");
    
    for(ObjectClass oc : ocs) {
      String fullClassName = null;
      for(AlternateName an : oc.getAlternateNames()) {
        if(an.getType().equals(AlternateName.TYPE_CLASS_FULL_NAME))
          fullClassName = an.getName();
      }

      sendProgressEvent(status++, goal, "Class: " + fullClassName);

      UMLClass clazz = classMap.get(fullClassName);
      boolean changed = changeTracker.get(fullClassName);

      if(changed) {
        // drop all current concept tagged values
        Collection<UMLTaggedValue> allTvs = clazz.getTaggedValues();
        for(UMLTaggedValue tv : allTvs) {
          if(tv.getName().startsWith("ObjectClass") ||
             tv.getName().startsWith("ObjectQualifier"));
          clazz.removeTaggedValue(tv.getName());
        }

        String [] conceptCodes = oc.getPreferredName().split(":");
          
        addConceptTvs(clazz, conceptCodes, XMIParser.TV_TYPE_CLASS);
      }
        
    }
      
    for(DataElement de : des) {
      DataElementConcept dec = de.getDataElementConcept();
      String fullPropName = null;

      for(AlternateName an : de.getAlternateNames()) {
        if(an.getType().equals(AlternateName.TYPE_FULL_NAME))
          fullPropName = an.getName();
      }
      sendProgressEvent(status++, goal, "Attribute: " + fullPropName);

      UMLAttribute att = attributeMap.get(fullPropName);
        
      boolean changed = changeTracker.get(fullPropName);
      if(changed) {
        // drop all current concept tagged values
        Collection<UMLTaggedValue> allTvs = att.getTaggedValues();
        for(UMLTaggedValue tv : allTvs) {
          if(tv.getName().startsWith("Property") ||
             tv.getName().startsWith("PropertyQualifier"));
          att.removeTaggedValue(tv.getName());
        }

        // Map to Existing DE
        if(!StringUtil.isEmpty(de.getPublicId()) && de.getVersion() != null) {
          att.addTaggedValue(XMIParser.TV_DE_ID,
                             de.getPublicId());

          att.addTaggedValue(XMIParser.TV_DE_VERSION,
                             de.getVersion().toString());

        } else {
          String [] conceptCodes = dec.getProperty().getPreferredName().split(":");
          addConceptTvs(att, conceptCodes, XMIParser.TV_TYPE_PROPERTY);
        }

      }
    }

    for(ValueDomain vd : vds) {

      sendProgressEvent(status++, goal, "Value Domain: " + vd.getLongName());

      for(PermissibleValue pv : vd.getPermissibleValues()) {
        ValueMeaning vm = pv.getValueMeaning();
        String fullPropName = "ValueDomains." + vd.getLongName() + "." + vm.getShortMeaning();
        UMLAttribute att = attributeMap.get(fullPropName);
          
        boolean changed = changeTracker.get(fullPropName);
        if(changed) {
          // drop all current concept tagged values
          Collection<UMLTaggedValue> allTvs = att.getTaggedValues();
          for(UMLTaggedValue tv : allTvs) {
            if(tv.getName().startsWith("Property") ||
               tv.getName().startsWith("PropertyQualifier"));
            att.removeTaggedValue(tv.getName());
          }

          String [] conceptCodes = ConceptUtil.getConceptCodes(vm);
          addConceptTvs(att, conceptCodes, XMIParser.TV_TYPE_VM);
        }
      }
    }

    changeTracker.clear();
  }

  private void addConceptTvs(UMLTaggableElement elt, String[] conceptCodes, String type) {
    if(conceptCodes.length == 0)
      return;

    addConceptTv(elt, conceptCodes[conceptCodes.length - 1], type, "", 0);

    for(int i= 1; i < conceptCodes.length; i++) {
      
      addConceptTv(elt, conceptCodes[conceptCodes.length - i - 1], type, XMIParser.TV_QUALIFIER, i);

    }

  }

  private void addConceptTv(UMLTaggableElement elt, String conceptCode, String type, String pre, int n) {

    Concept con = LookupUtil.lookupConcept(conceptCode);
    if(con == null)
      return;

    String tvName = type + pre + XMIParser.TV_CONCEPT_CODE + ((n>0)?""+n:"");

    elt.addTaggedValue(tvName,
                       con.getPreferredName());
    
    tvName = type + pre + XMIParser.TV_CONCEPT_DEFINITION + ((n>0)?""+n:"");
    elt.addTaggedValue
      (tvName,
       con.getPreferredDefinition());
    
    tvName = type + pre + XMIParser.TV_CONCEPT_DEFINITION_SOURCE + ((n>0)?""+n:"");
    elt.addTaggedValue
      (tvName,
       con.getDefinitionSource());
      
      tvName = type + pre + XMIParser.TV_CONCEPT_PREFERRED_NAME + ((n>0)?""+n:"");
    elt.addTaggedValue
      (tvName,
       con.getLongName());
  }
  
  private void markHumanReviewed() throws ParserException {
    try{ 
      List<ObjectClass> ocs = (List<ObjectClass>)cadsrObjects.getElements(DomainObjectFactory.newObjectClass().getClass());
      List<DataElementConcept> decs = (List<DataElementConcept>) cadsrObjects.getElements(DomainObjectFactory.newDataElementConcept().getClass());
      
      for(ObjectClass oc : ocs) {
        String fullClassName = null;
        for(AlternateName an : oc.getAlternateNames()) {
          if(an.getType().equals(AlternateName.TYPE_CLASS_FULL_NAME))
            fullClassName = an.getName();
        }

        UMLClass clazz = classMap.get(fullClassName);

        clazz.removeTaggedValue(XMIParser2.TV_HUMAN_REVIEWED);
        boolean reviewed = reviewTracker.get(fullClassName);
        clazz.addTaggedValue(XMIParser2.TV_HUMAN_REVIEWED, reviewed?"1":"0");
      }

      for(DataElementConcept dec : decs) {
        String fullPropName = dec.getObjectClass().getLongName() + "." + dec.getProperty().getLongName();
        
        Boolean reviewed = reviewTracker.get(fullPropName);
        if(reviewed == null) {
          continue;
        }
        
        UMLAttribute umlAtt = attributeMap.get(fullPropName);
        umlAtt.removeTaggedValue(XMIParser2.TV_HUMAN_REVIEWED);
        
        umlAtt.addTaggedValue(XMIParser2.TV_HUMAN_REVIEWED,
                              reviewed?"1":"0");
      }
    } catch (RuntimeException e) {
      throw new ParserException(e);
    }
  }
  
  private void doPackage(UMLPackage pkg) {
    for(UMLClass clazz : pkg.getClasses()) {
      String className = null;
      if(clazz.getStereotype() != null && clazz.getStereotype().equals(XMIParser2.VD_STEREOTYPE)) {
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

  protected void sendProgressEvent(int status, int goal, String message) {
    if(progressListener != null) {
      ProgressEvent pEvent = new ProgressEvent();
      pEvent.setMessage(message);
      pEvent.setStatus(status);
      pEvent.setGoal(goal);
      
      progressListener.newProgressEvent(pEvent);
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