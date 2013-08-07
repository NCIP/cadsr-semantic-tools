/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

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
import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;

import gov.nih.nci.ncicb.cadsr.loader.event.NewConceptEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jaxen.JaxenException;
import org.jaxen.jdom.JDOMXPath;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;


/**
 * A writer for XMI files 
 *
 *
 * @deprecated no longer maintained as of 3.2
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class RoundtripWriter implements ElementWriter {

  private String output = null;

//   private UserSelections userSelections = UserSelections.getInstance();
//   private String input = (String)userSelections.getProperty("FILENAME");
  
  private String input = null;

  private Element modelElement;

  private HashMap<String, Element> elements = new HashMap();

  private ElementsLists elementsList = null;

  private ReviewTracker reviewTracker = ReviewTracker.getInstance(ReviewTrackerType.Owner);
  private ChangeTracker changeTracker = ChangeTracker.getInstance();

  private ProgressListener progressListener = null;

  private static Logger logger = Logger.getLogger(RoundtripWriter.class.getName());

  
  public RoundtripWriter(String inputFile) {
    this.input = inputFile;
    try {
      ProgressEvent pEvt = new ProgressEvent();
      pEvt.setGoal(-1);
      pEvt.setMessage("Opening File");
      if(progressListener != null)
        progressListener.newProgressEvent(pEvt);

      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(input);
      modelElement = doc.getRootElement();
    } catch (Exception ex) {
      throw new RuntimeException("Error initializing model", ex);
    }

  }

  public void write(ElementsLists elements) {


    this.elementsList = elements;
    
    readModel();
    updateElements();
    writeModel();

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

  /**
   * Copy / Paste from caCORE-Toolkit ModelAnnotator
   *
   */
  private void addTaggedValue
    (String tagName,
     String value,
     String newId,
     String xmiid,
     Namespace namespace) throws JaxenException {

      Element taggedValue = new Element("TaggedValue", namespace);
      taggedValue.setNamespace(Namespace.getNamespace("UML", "href://org.omg/UML"));
      taggedValue.setAttribute(new Attribute("xmi.id", newId));
      taggedValue.setAttribute(new Attribute("tag", tagName));
      taggedValue.setAttribute(new Attribute("modelElement", xmiid));
      taggedValue.setAttribute(new Attribute("value", value));
      
      Element elem1 = getElement(modelElement, "//*[local-name()='Model'");
      Element parentElement = elem1.getParentElement();
      parentElement.addContent(taggedValue);

  }

  /**
   *
   * Copy / Paste from caCORE-Toolkit ModelAnnotator
   *
   */
  private Element getElement
    (Element classElement, String exp)
    throws JaxenException {

    Element element = null;
    try {
      element = (Element) (new JDOMXPath(exp)).selectSingleNode(classElement);
      
    } catch (Exception ex) {
      throw new RuntimeException("Error searching for expression " + exp
                                 + " in class " + classElement.getAttributeValue("name"), ex);
    }
    return element;
  }


  private List<Element> getElements
    (Element classElement, String elementName)
    throws JaxenException {

    List<Element> elementList = null;
    try {
      String exp = ".//*[local-name()='"+elementName+"']";
      elementList = (List<Element>) (new JDOMXPath(exp)).selectNodes(classElement);
      
    } catch (Exception ex) {
      throw new RuntimeException("Error searching for elements " + elementName
                                 + " for class " + classElement.getAttributeValue("name"), ex);
    }
    return elementList;
  }


  /**
   * Copy / Paste from caCORE-Toolkit ModelAnnotator
   *
   */
  private void writeModel() {
    try {
      File f = new File(output);
      
      Writer writer = new OutputStreamWriter
        (new FileOutputStream(f), "UTF-8");
      XMLOutputter xmlout = new XMLOutputter();
      xmlout.setFormat(Format.getPrettyFormat());
      writer.write(xmlout.outputString(modelElement));
      writer.flush();
      writer.close();
    } catch (Exception ex) {
      throw new RuntimeException("Error writing to " + output, ex);
    }
  }
  
  /**
   * Copy / Paste from caCORE-Toolkit ModelAnnotator
   *
   */
  private String getNewId(String xmiid) throws JaxenException
  {
    String id = null;
    try
      {
        String exp = "//*[local-name()='TaggedValue' and @modelElement='"+ xmiid +"']";
        List tvs = (List)(new JDOMXPath(exp)).selectNodes(modelElement);

        Element tv = null;
        
        if(tvs != null && tvs.size() > 0)
          tv = (Element)tvs.get(tvs.size()-1);
        
        if(tv != null)
          id = (String)tv.getAttributeValue("xmi.id")+"_tag";
        else
          id = xmiid+"_tag";
        
      }
    catch(Exception e)
      {
        throw new RuntimeException("Exception while creating getNewId "+e.getMessage());
      }
    
    return id;
  }
  
 
  /**
   * Copy / Paste from caCORE-Toolkit ModelAnnotator
   *
   */
  private void readModel(){
    try {

      String xpath = "//*[local-name()='Model']/*[local-name()='Namespace.ownedElement']/*[local-name()='Package']";

      doPackage(xpath, "");
    } catch (JaxenException e){
    } // end of try-catch
  }

  private void updateElements() {
    try {
//       List<ObjectClass> ocs = elementsList.getElements(DomainObjectFactory.newObjectClass().getClass());
      List<DataElement> des = elementsList.getElements(DomainObjectFactory.newDataElement());

      ProgressEvent pEvt = new ProgressEvent();
      pEvt.setGoal(des.size() + 1);
      pEvt.setMessage("Injecting CaDSR Public IDs");
      pEvt.setStatus(0);
      if(progressListener != null)
        progressListener.newProgressEvent(pEvt);
      
//       for(ObjectClass oc : ocs) {
//         pEvt.setStatus(pEvt.getStatus() + 1);
//         if(progressListener != null)
//           progressListener.newProgressEvent(pEvt);

//         Element classElement = elements.get(oc.getLongName());

//         // does OC have a pub id ?
//         if((oc.getPublicId() != null) && (oc.getPublicId().length() > 0) ) {
//           String xpath = "//*[local-name()='TaggedValue' and starts-with(@tag, 'CADSR_OC_') and @modelElement='"
//             + classElement.getAttributeValue("xmi.id")
//             + "']";
          
//           JDOMXPath path = new JDOMXPath(xpath);
//           List<Element> conceptTvs = path.selectNodes(modelElement);
//           // drop all current concept tagged values
//           for(Element tvElt : conceptTvs) {
//             tvElt.getParentElement().removeContent(tvElt);
//           }
          
//           addTaggedValue
//             ("CADSR_OC_ID",
//              oc.getPublicId(),
//              getNewId(classElement.getAttributeValue("xmi.id")),
//              classElement.getAttributeValue("xmi.id"),
//              classElement.getNamespace());

//           addTaggedValue
//             ("CADSR_OC_VERSION",
//              oc.getVersion().toString(),
//              getNewId(classElement.getAttributeValue("xmi.id")),
//              classElement.getAttributeValue("xmi.id"),
//              classElement.getNamespace());

//         }
//       }

      for(DataElement de : des) {
        pEvt.setStatus(pEvt.getStatus() + 1);
        if(progressListener != null)
          progressListener.newProgressEvent(pEvt);

        String fullPropName = de.getDataElementConcept().getObjectClass().getLongName() + "." + de.getDataElementConcept().getProperty().getLongName();
        Element attributeElement = elements.get(fullPropName);

        if(attributeElement == null) {
          logger.info("Parser Can't find attribute: " + fullPropName + "\n Probably inherited. That should be ok.");
          continue;
        }
        
        if(!StringUtil.isEmpty(de.getPublicId())) {
          String xpath = "//*[local-name()='TaggedValue' and starts-with(@tag, 'CADSR_DE_')  and @modelElement='"
            + attributeElement.getAttributeValue("xmi.id")
            + "']";


          JDOMXPath path = new JDOMXPath(xpath);
          List<Element> tvs = path.selectNodes(modelElement);

          for(Element tv : tvs) {
            tv.getParentElement().removeContent(tv);
          }
          
          addTaggedValue
            ("CADSR_DE_ID",
             de.getPublicId(),
             getNewId(attributeElement.getAttributeValue("xmi.id")),
             attributeElement.getAttributeValue("xmi.id"),
             attributeElement.getNamespace());

          addTaggedValue
            ("CADSR_DE_VERSION",
             de.getVersion().toString(),
             getNewId(attributeElement.getAttributeValue("xmi.id")),
             attributeElement.getAttributeValue("xmi.id"),
             attributeElement.getNamespace());

          
        }

      }

//         if((de.getValueDomain().getPublicId() != null) && (de.getValueDomain().getPublicId().length() > 0)) {
          
//           String xpath = "//*[local-name()='TaggedValue' and (starts-with(@tag, 'CADSR_VD_') or (starts-with(@tag, 'CADSR_PROP_') )  and @modelElement='"
//             + attributeElement.getAttributeValue("xmi.id")
//             + "']";
          
//           JDOMXPath path = new JDOMXPath(xpath);
//           List<Element> tvs = path.selectNodes(modelElement);

//           for(Element tv : tvs) {
//             tv.getParentElement().removeContent(tv);
//           }

//           addTaggedValue
//             ("CADSR_VD_ID",
//              de.getValueDomain().getPublicId(),
//              getNewId(attributeElement.getAttributeValue("xmi.id")),
//              attributeElement.getAttributeValue("xmi.id"),
//              attributeElement.getNamespace());

//           addTaggedValue
//             ("CADSR_VD_VERSION",
//              de.getValueDomain().getVersion().toString(),
//              getNewId(attributeElement.getAttributeValue("xmi.id")),
//              attributeElement.getAttributeValue("xmi.id"),
//              attributeElement.getNamespace());


//           addTaggedValue
//             ("CADSR_PROP_ID",
//              de.getDataElementConcept().getProperty().getPublicId(),
//              getNewId(attributeElement.getAttributeValue("xmi.id")),
//              attributeElement.getAttributeValue("xmi.id"),
//              attributeElement.getNamespace());

//           addTaggedValue
//             ("CADSR_PROP_VERSION",
//              de.getDataElementConcept().getProperty().getVersion().toString(),
//              getNewId(attributeElement.getAttributeValue("xmi.id")),
//              attributeElement.getAttributeValue("xmi.id"),
//              attributeElement.getNamespace());
          
          
    } catch (JaxenException e){
    } 
    
  }

  
  private void doPackage(String xpath, String packageName) throws JaxenException {
    xpath = xpath + "/*[local-name()='Namespace.ownedElement']/*[local-name()='Package']";
    
    JDOMXPath path = new JDOMXPath(xpath);
    Collection<Element> packages = (Collection<Element>)path.selectNodes(modelElement);
    
    if(packages.size() == 0)
      return;

    for(Element pkg : packages) {
      doPackage(pkg, packageName);
    }

  }
  

  private void doPackage(Element pkg, String packageName) throws JaxenException {
 
    String packName = pkg.getAttributeValue("name");
    
    if(packName.indexOf(" ") != -1) {
      Element namespaceElt = pkg.getChild("Namespace.ownedElement", pkg.getNamespace());
      Collection<Element> packages = namespaceElt.getChildren("Package", pkg.getNamespace());
      for(Element subPkg : packages) {
        doPackage(subPkg, packageName);
      } 
      return;
    }
    
    if (packageName.length() == 0) {
      packageName = packName;
    }
    else {
      packageName += ("." + packName);
    }
    

    Element namespaceElt = pkg.getChild("Namespace.ownedElement", pkg.getNamespace());
    Collection<Element> classes = namespaceElt.getChildren("Class", pkg.getNamespace());
    
    
    for (Element classElement : classes) {
      String className = packageName + "." + classElement.getAttributeValue("name");
      
      elements.put(className, classElement);
      
      List<Element> attributes = getElements(classElement, "Attribute");
      
      for(Element attributeElt : attributes) {
        String attributeName = 
          className + "." + attributeElt.getAttributeValue("name");
        elements.put(attributeName, attributeElt);
      }
    }

    namespaceElt = pkg.getChild("Namespace.ownedElement", pkg.getNamespace());
    Collection<Element> packages = namespaceElt.getChildren("Package", pkg.getNamespace());
    for(Element subPkg : packages) {
      doPackage(subPkg, packageName);
    } 

  }
}