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

import gov.nih.nci.ncicb.cadsr.loader.event.*;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;

import org.apache.log4j.Logger;

import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.RunMode;

import gov.nih.nci.ncicb.cadsr.loader.validator.*;

import gov.nih.nci.ncicb.cadsr.loader.UserSelections;

import gov.nih.nci.ncicb.xmiinout.handler.*;
import gov.nih.nci.ncicb.xmiinout.domain.*;


import java.io.*;

import java.util.*;

/**
 * Implemetation of <code>Parser</code> for XMI files. Navigates the XMI document and sends UML Object events.
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class XMIParser2 implements Parser {
  private static final String EA_CONTAINMENT = "containment";
  private static final String EA_UNSPECIFIED = "Unspecified";
  private UMLHandler listener;

  private String packageName = "";
  private String className = "";
  private Logger logger = Logger.getLogger(XMIParser2.class.getName());

  private List<NewAssociationEvent> associationEvents = new ArrayList<NewAssociationEvent>();
  
  // Key = child class Name
  Map<String, NewGeneralizationEvent> childGeneralizationMap = 
    new HashMap<String, NewGeneralizationEvent>();

  private String reviewTag = null;
  
  private ProgressListener progressListener = null;

//   public final static String VD_STEREOTYPE = "CADSR Value Domain";

  public static final String TV_PROP_ID = "CADSR_PROP_ID";
  public static final String TV_PROP_VERSION = "CADSR_PROP_VERSION";

  public static final String TV_DE_ID = "CADSR_DE_ID";
  public static final String TV_DE_VERSION = "CADSR_DE_VERSION";

  public static final String TV_VALUE_DOMAIN = "CADSR Local Value Domain";

  public static final String TV_VD_ID = "CADSR_VD_ID";
  public static final String TV_VD_VERSION = "CADSR_VD_VERSION";
  public static final String TV_OC_ID = "CADSR_OC_ID";
  public static final String TV_OC_VERSION = "CADSR_OC_VERSION";


  public static final String TV_VD_DEFINITION = "CADSR_ValueDomainDefinition";
  public static final String TV_VD_DATATYPE = "CADSR_ValueDomainDatatype";
  public static final String TV_VD_TYPE = "CADSR_ValueDomainType";
  public static final String TV_CD_ID = "CADSR_ConceptualDomainPublicID";
  public static final String TV_CD_VERSION = "CADSR_ConceptualDomainVersion";

  /**
   * Tagged Value name for Concept Code
   */
  public static final String TV_CONCEPT_CODE = "ConceptCode";

  /**
   * Tagged Value name for Concept Preferred Name
   */
  public static final String TV_CONCEPT_PREFERRED_NAME = "ConceptPreferredName";

  /**
   * Tagged Value name for Concept Definition
   */
  public static final String TV_CONCEPT_DEFINITION = "ConceptDefinition";

  /**
   * Tagged Value name for Concept Definition Source
   */
  public static final String TV_CONCEPT_DEFINITION_SOURCE = "ConceptDefinitionSource";


  /**
   * Qualifier Tagged Value prepender. 
   */
  public static final String TV_QUALIFIER = "Qualifier";

  /**
   * ObjectClass Tagged Value prepender. 
   */
  public static final String TV_TYPE_CLASS = "ObjectClass";

  /**
   * Property Tagged Value prepender. 
   */
  public static final String TV_TYPE_PROPERTY = "Property";

  /**
   * ValueDomain Tagged Value prepender. 
   */
  public static final String TV_TYPE_VD = "ValueDomain";

  /**
   * Value Meaning Tagged Value prepender. 
   */
  public static final String TV_TYPE_VM = "ValueMeaning";

  /**
   * Association Role Tagged Value prepender. 
   */
  public static final String TV_TYPE_ASSOC_ROLE = "AssociationRole";

  /**
   * Association Source Tagged Value prepender. 
   */
  public static final String TV_TYPE_ASSOC_SOURCE = "AssociationSource";

  /**
   * Association Target Tagged Value prepender. 
   */
  public static final String TV_TYPE_ASSOC_TARGET = "AssociationTarget";
  

  /**
   * Tagged Value name for Documentation
   */
  public static final String TV_DOCUMENTATION = "documentation";
  public static final String TV_DESCRIPTION = "description";

  // replaced by type specific review tags
  //   public static final String TV_HUMAN_REVIEWED = "HUMAN_REVIEWED";
  public static final String TV_OWNER_REVIEWED = "OWNER_REVIEWED";
  public static final String TV_CURATOR_REVIEWED = "CURATOR_REVIEWED";

  private int totalNumberOfElements = 0, currentElementIndex = 0;


  private String[] bannedClassNames = null;
  {
    bannedClassNames = PropertyAccessor.getProperty("banned.classNames").split(",");
  }
  public static final String[] validVdStereotypes = 
    PropertyAccessor.getProperty("vd.valid.stereotypes").split(",");

  public void setEventHandler(LoaderHandler handler) {
    this.listener = (UMLHandler) handler;
    if (progressListener != null) {
      listener.addProgressListener(progressListener);
    }
  }

  public void parse(String filename) throws ParserException {
    try {

      RunMode runMode = (RunMode)(UserSelections.getInstance().getProperty("MODE"));
      if(runMode.equals(RunMode.Curator) || (runMode.equals(RunMode.GenerateReport))) {
        reviewTag = TV_CURATOR_REVIEWED;
      } else {
        reviewTag = TV_OWNER_REVIEWED;
      }

      long start = System.currentTimeMillis();
      
      listener.beginParsing();
        
      ProgressEvent evt = new ProgressEvent();
      evt.setMessage("Parsing ...");
      fireProgressEvent(evt);
      
      XmiInOutHandler handler = XmiHandlerFactory.getXmiHandler(HandlerEnum.EADefault);

      String s = filename.replaceAll("\\ ", "%20");

      // Some file systems use absolute URIs that do 
      // not start with '/'. 
      if(!s.startsWith("/"))
        s = "/" + s;    
      java.net.URI uri = new java.net.URI("file://" + s);
      handler.load(uri);

      UMLModel model = handler.getModel("EA Model");

      // save in memory for fast-save
      UserSelections.getInstance().setProperty("XMI_HANDLER", handler);

      totalNumberOfElements = countNumberOfElements(model);
      
      evt.setMessage("Parsing ...");
      evt.setGoal(totalNumberOfElements);
      fireProgressEvent(evt);

      for(UMLPackage pkg : model.getPackages()) {
        doPackage(pkg);
      }
      for(UMLAssociation assoc : model.getAssociations()) {
        doAssociation(assoc);
      }

      for (UMLGeneralization g : model.getGeneralizations()) {
        UMLClass parentClass = g.getSupertype();

        UMLClass subClass = g.getSubtype();
        // Check if the parent is not explicitely excluded.
        String ppName = getPackageName(parentClass.getPackage());
        if(StringUtil.isEmpty(ppName) || !isInPackageFilter(ppName)) {
          logger.info(PropertyAccessor.getProperty("skip.inheritance", ppName + "." + parentClass.getName(), getPackageName(subClass.getPackage()) + "." + subClass.getName()));
          continue;
        }

        NewGeneralizationEvent gEvent = new NewGeneralizationEvent();
        gEvent.setParentClassName(
          getPackageName(parentClass.getPackage()) + "." + parentClass.getName());

        gEvent.setChildClassName(
          getPackageName(subClass.getPackage()) + "." + subClass.getName());

        childGeneralizationMap.put(gEvent.getChildClassName(), gEvent);

      }
      
      fireLastEvents();
      
      listener.endParsing();
      long stop = System.currentTimeMillis();
      logger.debug("parsing took: "+(stop-start)+" ms");
    }
    catch (Exception e) {
      throw new ParserException(e);
    } // end of try-catch
  }

  private int countNumberOfElements(UMLModel model) {
    int count = 0;
    for(UMLPackage pkg : model.getPackages()) {
      count = countPackage(pkg, count);
    } 
    return count;
  }
  private int countPackage(UMLPackage pkg, int count) {
    for(UMLPackage subPkg : pkg.getPackages()) {
      count = countPackage(subPkg, count);
    }
    for(UMLClass clazz : pkg.getClasses()) {
      count++;
      count = countClass(clazz, count);
    }
    return count;
  }

  private int countClass(UMLClass clazz, int count) {
    for(UMLAttribute att : clazz.getAttributes())
      count++;

    return count;
  }

  private void doPackage(UMLPackage pack) {
    UMLDefaults defaults = UMLDefaults.getInstance();

    if (packageName.length() == 0) {
      //       if(pack.getName().indexOf(" ") == -1)
      packageName = pack.getName();
    }
    else {
      //       if(pack.getName().indexOf(" ") == -1)
      packageName += ("." + pack.getName());
    }

    if(isInPackageFilter(packageName)) {
      listener.newPackage(new NewPackageEvent(packageName));
    } else {
      logger.info(PropertyAccessor.getProperty("skip.package", packageName));
    }

    for(UMLPackage subPkg : pack.getPackages()) {
      String oldPackage = packageName;
      doPackage(subPkg);
      packageName = oldPackage;
    }

    for(UMLClass clazz : pack.getClasses()) {
      doClass(clazz);
    }

    packageName = "";
  }

  private void doClass(UMLClass clazz) {
    UMLDefaults defaults = UMLDefaults.getInstance();
    String pName = getPackageName(clazz.getPackage());

    className = clazz.getName();

    String st = clazz.getStereotype();
    if(st != null) {
      boolean foundVd = false;
      for(int i=0; i<validVdStereotypes.length; i++) {
        if(st.equalsIgnoreCase(validVdStereotypes[i])) foundVd = true;
      }
      if(foundVd) {
        doValueDomain(clazz);
        return;
      }
    }
      
    if (pName != null) {
      className = pName + "." + className;
    }

    currentElementIndex++;
    ProgressEvent evt = new ProgressEvent();
    evt.setMessage("Parsing " + className);
    evt.setStatus(currentElementIndex);
    fireProgressEvent(evt);

    NewClassEvent event = new NewClassEvent(className.trim());
    event.setPackageName(pName);

    setConceptInfo(clazz, event, TV_TYPE_CLASS);

    logger.debug("CLASS: " + className);
    logger.debug("CLASS PACKAGE: " + getPackageName(clazz.getPackage()));

    if(isClassBanned(className)) {
      logger.info(PropertyAccessor.getProperty("class.filtered", className));
      return;
    }
    
    if(StringUtil.isEmpty(pName)) 
    {
      logger.info(PropertyAccessor.getProperty("class.no.package", className));
      return;
    }

    String description = getDocumentation(clazz, TV_DOCUMENTATION);
    if(description != null) {
      event.setDescription(description);
    } else {
      description = getDocumentation(clazz, TV_DESCRIPTION);
      if(description != null) {
        event.setDescription(description);
      }
    }

    UMLTaggedValue tv = clazz.getTaggedValue(reviewTag);
    if(tv != null) {
      event.setReviewed(tv.getValue().equals("1")?true:false);
    }

    if(isInPackageFilter(pName)) {
      listener.newClass(event);
    } else {
      logger.info(PropertyAccessor.getProperty("class.filtered", className));
      return;
    }


    for(UMLAttribute att : clazz.getAttributes()) {
        doAttribute(att);
    }

    className = "";

  }

  private void doValueDomain(UMLClass clazz) {
    UMLDefaults defaults = UMLDefaults.getInstance();

    className = clazz.getName();


    currentElementIndex++;
    ProgressEvent evt = new ProgressEvent();
    evt.setMessage("Parsing " + className);
    evt.setStatus(currentElementIndex);
    fireProgressEvent(evt);

    NewValueDomainEvent event = new NewValueDomainEvent(className.trim());

    String pName = getPackageName(clazz.getPackage());
    if(pName != null)
      event.setPackageName(pName);

    setConceptInfo(clazz, event, TV_TYPE_VD);

    logger.debug("Value Domain: " + className);

    UMLTaggedValue tv = clazz.getTaggedValue(TV_VD_DEFINITION);
    if(tv != null) {
      event.setDescription(tv.getValue());
    }

    tv = clazz.getTaggedValue(TV_VD_DATATYPE);
    if(tv != null) {
      event.setDatatype(tv.getValue());
    }

    tv = clazz.getTaggedValue(TV_VD_TYPE);
    if(tv != null) {
      event.setType(tv.getValue());
    }

    tv = clazz.getTaggedValue(TV_CD_ID);
    if(tv != null) {
      event.setCdId(tv.getValue());
    }
    
    tv = clazz.getTaggedValue(TV_CD_VERSION);
    if(tv != null) {
      try {
        event.setCdVersion(new Float(tv.getValue()));
      } catch (NumberFormatException e){
        logger.warn(PropertyAccessor.getProperty("version.numberFormatException", tv.getValue()));
      } // end of try-catch
    }


    tv = clazz.getTaggedValue(reviewTag);
    if(tv != null) {
      event.setReviewed(tv.getValue().equals("1")?true:false);
    }

    listener.newValueDomain(event);

    for (UMLAttribute att : clazz.getAttributes()) {
      doValueMeaning(att);
    }

    className = "";

  }



//   private void doInterface(Interface interf) {
//     className = packageName + "." + interf.getName();

//     //     logger.debug("Class: " + className);
//     listener.newInterface(new NewInterfaceEvent(className.trim()));

//     Iterator it = interf.getFeature().iterator();

//     while (it.hasNext()) {
//       Object o = it.next();
//       if (o instanceof Attribute) {
//         doAttribute((Attribute) o);
//       }
//       else if (o instanceof Operation) {
//         doOperation((Operation) o);
//       }
//       else {
//         logger.debug("Class child: " + o.getClass());
//       }
//     }

//     className = "";
//   }

  private void doAttribute(UMLAttribute att) {
    NewAttributeEvent event = new NewAttributeEvent(att.getName().trim());
    event.setClassName(className);

    currentElementIndex++;
    ProgressEvent evt = new ProgressEvent();
    evt.setMessage("Parsing " + att.getName());
    evt.setStatus(currentElementIndex);
    fireProgressEvent(evt);


    if(att.getDatatype() == null || att.getDatatype().getName() == null) {
      ValidationItems.getInstance()
        .addItem(new ValidationFatal
                 (PropertyAccessor
                  .getProperty
                  ("validation.type.missing.for"
                   , event.getClassName() + "." + event.getName()),
                  null));
      return;
    }

    // See if datatype is a simple datatype or a value domain.
    UMLTaggedValue tv = att.getTaggedValue(TV_VALUE_DOMAIN);
    if(tv != null) {       // Use Value Domain
      event.setType(tv.getValue());
    } else {               // Use datatype
      event.setType(att.getDatatype().getName());
    }

    String description = getDocumentation(att, TV_DESCRIPTION);
    if(description != null) {
      event.setDescription(description);
    } else {
      description = getDocumentation(att, TV_DOCUMENTATION);
      if(description != null) {
        event.setDescription(description);
      }
    }

    tv = att.getTaggedValue(reviewTag);
    if(tv != null) {
      event.setReviewed(tv.getValue().equals("1")?true:false);
    }

    // Is this attribute mapped to an existing CDE?
    tv = att.getTaggedValue(TV_DE_ID);
    if(tv != null) {
      event.setPersistenceId(tv.getValue());
    }

    tv = att.getTaggedValue(TV_DE_VERSION);
    if(tv != null) {
      try {
        event.setPersistenceVersion(new Float(tv.getValue()));
      } catch (NumberFormatException e){
      } // end of try-catch
    }

    tv = att.getTaggedValue(TV_VD_ID);
    if(tv != null) {
      event.setTypeId(tv.getValue());
    }

    tv = att.getTaggedValue(TV_VD_VERSION);
    if(tv != null) {
      try {
        event.setTypeVersion(new Float(tv.getValue()));
      } catch (NumberFormatException e){
      } // end of try-catch
    }


    setConceptInfo(att, event, TV_TYPE_PROPERTY);

    listener.newAttribute(event);
  }

  private void doValueMeaning(UMLAttribute att) {
    NewValueMeaningEvent event = new NewValueMeaningEvent(att.getName().trim());
    event.setValueDomainName(className);

    currentElementIndex++;
    ProgressEvent evt = new ProgressEvent();
    evt.setMessage("Parsing " + att.getName());
    evt.setStatus(currentElementIndex);
    fireProgressEvent(evt);

    UMLTaggedValue tv = att.getTaggedValue(reviewTag);
    if(tv != null) {
      event.setReviewed(tv.getValue().equals("1")?true:false);
    }

    String description = getDocumentation(att, TV_DESCRIPTION);
    if(description != null) {
      event.setDescription(description);
    } else {
      description = getDocumentation(att, TV_DOCUMENTATION);
      if(description != null) {
        event.setDescription(description);
      }
    }

    setConceptInfo(att, event, TV_TYPE_VM);

    listener.newValueMeaning(event);
  }


//   private void doDataType(UMLDataType dt) {
//     listener.newDataType(new NewDataTypeEvent(dt.getName()));
//   }

//   private void doOperation(Operation op) {
//     NewOperationEvent event = new NewOperationEvent(op.getName());
//     event.setClassName(className);
//     listener.newOperation(event);
//   }

//   private void doStereotype(Stereotype st) {
//     logger.debug("--- Stereotype " + st.getName());
//   }

  private void doAssociation(UMLAssociation assoc) {
    NewAssociationEvent event = new NewAssociationEvent();
    event.setRoleName(assoc.getRoleName());

    List<UMLAssociationEnd> ends = assoc.getAssociationEnds();
    if(ends.size() != 2)
      return;
    
    final UMLAssociationEnd aEnd = ends.get(0);
    final UMLAssociationEnd bEnd = ends.get(1);

    String navig = "";
    if (aEnd.isNavigable()) navig += 'A';
    if (bEnd.isNavigable()) navig += 'B';
    event.setDirection(navig);
    
    // direction B?
    String atype = TV_TYPE_ASSOC_TARGET;
    String btype = TV_TYPE_ASSOC_SOURCE;
    if (navig.equals("B")) {
        atype = TV_TYPE_ASSOC_SOURCE;
        btype = TV_TYPE_ASSOC_TARGET;
    }
    
    // set tagged values and create subevents
    NewAssociationEndEvent aEvent = doAssociationEnd(aEnd, atype);
    if (aEvent == null) return;
    NewAssociationEndEvent bEvent = doAssociationEnd(bEnd, btype);
    if (bEvent == null) return;
    
    event.setAEvent(aEvent);
    event.setBEvent(bEvent);
    
    UMLTaggedValue tv = assoc.getTaggedValue(reviewTag);
    if(tv != null) {
      event.setReviewed(tv.getValue().equals("1"));
    }
    setConceptInfo(assoc, event, TV_TYPE_ASSOC_ROLE);
    
    // netbeans seems to read self pointing associations wrong. 
    // Such that an end is navigable but has no target role, 
    // even though it does in the model.
    if(aEvent.getClassName().equals(bEvent.getClassName())) {
      if(navig.equals("B") && StringUtil.isEmpty(bEvent.getRoleName())) {
          bEvent.setRoleName(aEvent.getRoleName());
        bEvent.setLowCardinality(aEvent.getLowCardinality());
        bEvent.setHighCardinality(aEvent.getHighCardinality());
      } else if (navig.equals("A") && StringUtil.isEmpty(aEvent.getRoleName())) {
          aEvent.setRoleName(bEvent.getRoleName());
          aEvent.setLowCardinality(bEvent.getLowCardinality());
          aEvent.setHighCardinality(bEvent.getHighCardinality());
      }
    }

    logger.debug("Adding association. AClassName: " + aEvent.getClassName());
    
    associationEvents.add(event);
  }

//   private void doComponent(Component comp) {
//     logger.debug("--- Component: " + comp.getName());
//   }

//   private String cardinality(AssociationEnd end) {
//     Collection range = end.getMultiplicity().getRange();

//     for (Iterator it = range.iterator(); it.hasNext();) {
//       MultiplicityRange mr = (MultiplicityRange) it.next();
//       int low = mr.getLower();
//       int high = mr.getUpper();

//       if (low == high) {
//         return "" + low;
//       }
//       else {
//         String h = (high >= 0) ? ("" + high) : "*";

//         return low + ".." + h;
//       }
//     }

//     return "";
//   }

  

  private NewAssociationEndEvent doAssociationEnd(UMLAssociationEnd end, 
          String type) {
      
      NewAssociationEndEvent event = new NewAssociationEndEvent();
      
      UMLClass endClass = (UMLClass)(end.getUMLElement());
      String pName = getPackageName(endClass.getPackage());
      
      if(StringUtil.isEmpty(pName) || !isInPackageFilter(pName)) {
        logger.info(PropertyAccessor.getProperty("skip.association", endClass.getName() + " " + end.getRoleName()));
        logger.debug("assoc end role name: " + end.getRoleName());
        return null;
      }
      
      event.setLowCardinality(end.getLowMultiplicity());
      event.setHighCardinality(end.getHighMultiplicity());
      event.setClassName(getPackageName(endClass.getPackage()) + "." + endClass.getName());
      event.setRoleName(end.getRoleName());
      
      if(event.getClassName() == null) {
        logger.debug("AClassName: NULL");
        return null;
      } else {
        logger.debug("AClassName: " + event.getClassName());
      }

      UMLTaggedValue tv = end.getTaggedValue(reviewTag);
      if(tv != null) {
        event.setReviewed(tv.getValue().equals("1"));
      }

      setConceptInfo(end, event, type);
      
      return event;
  }
  
  private void fireLastEvents() {
    for (Iterator<NewAssociationEvent> it = associationEvents.iterator(); it.hasNext();) {
      listener.newAssociation(it.next());
    }


    for(Iterator<String> it = childGeneralizationMap.keySet().iterator(); it.hasNext(); ) {
      String childClass = it.next();
      recurseInheritance(childClass);
      
//       listener.newGeneralization(childGeneralizationMap.get(childClass));
      it = childGeneralizationMap.keySet().iterator(); it.hasNext();

    }

    ProgressEvent evt = new ProgressEvent();
    evt.setGoal(100);
    evt.setStatus(100);
    evt.setMessage("Done parsing");
    fireProgressEvent(evt);

  }

  private void recurseInheritance(String childClass) {
    NewGeneralizationEvent genz = childGeneralizationMap.get(childClass);
    if(childGeneralizationMap.containsKey(genz.getParentClassName())) {
      recurseInheritance(genz.getParentClassName());
    }

    listener.newGeneralization(genz);
    childGeneralizationMap.remove(childClass);

  }

  private void setConceptInfo(UMLTaggableElement elt, NewConceptualEvent event, String type) {
    NewConceptEvent concept = new NewConceptEvent();
    setConceptInfo(elt, concept, type, "", 0);

    if(!StringUtil.isEmpty(concept.getConceptCode()))
      event.addConcept(concept);
    
    concept = new NewConceptEvent();
    for(int i=1;setConceptInfo(elt, concept, type, TV_QUALIFIER, i); i++) {

      if(!StringUtil.isEmpty(concept.getConceptCode()))
        event.addConcept(concept);

      concept = new NewConceptEvent();
    }

  }

  private boolean setConceptInfo(UMLTaggableElement elt, NewConceptEvent event, String type, String pre, int n) {
  
    UMLTaggedValue tv = elt.getTaggedValue(type + pre + TV_CONCEPT_CODE + ((n>0)?""+n:""));
    if (tv != null) {
      event.setConceptCode(tv.getValue().trim());
    } else 
      return false;
    
    tv = elt.getTaggedValue(type + pre + TV_CONCEPT_DEFINITION + ((n>0)?""+n:""));
    if (tv != null) {
      event.setConceptDefinition(tv.getValue().trim());
    }

    tv = elt.getTaggedValue(type + pre + TV_CONCEPT_DEFINITION_SOURCE + ((n>0)?""+n:""));
    if (tv != null) {
      event.setConceptDefinitionSource(tv.getValue().trim());
    }
    
    tv = elt.getTaggedValue(type + pre + TV_CONCEPT_PREFERRED_NAME + ((n>0)?""+n:""));
    if (tv != null) {
      event.setConceptPreferredName(tv.getValue().trim());
    }

    event.setOrder(n);
    return true;

  }

  private boolean isInPackageFilter(String pName) {
    Map packageFilter = UMLDefaults.getInstance().getPackageFilter();
    return (packageFilter.size() == 0) || (packageFilter.containsKey(pName) || (UMLDefaults.getInstance().getDefaultPackageAlias() != null));
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

  private boolean isClassBanned(String className) {
    for(int i=0; i<bannedClassNames.length; i++) {
      if(className.indexOf(bannedClassNames[i]) > -1) return true;
    }
    return false;
  }

  private void fireProgressEvent(ProgressEvent evt) {
    if(progressListener != null)
      progressListener.newProgressEvent(evt);
  }

  public void addProgressListener(ProgressListener progressListener) {
    this.progressListener = progressListener;
    if (listener != null) {
      listener.addProgressListener(progressListener);
    }
  }

  private String getDocumentation(UMLTaggableElement elt, String tag) {
    UMLTaggedValue tv = elt.getTaggedValue(tag);
    
    StringBuilder sb = new StringBuilder();
    if(tv == null)
      return null;
    else {
      sb.append(tv.getValue());
      for(int i = 2;true; i++) {
        tv = elt.getTaggedValue(tag + i);
        if(tv == null)
          return sb.toString();
        else {
          sb.append(tv.getValue());
        }
      }
    }
  }
}