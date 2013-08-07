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
package gov.nih.nci.ncicb.cadsr.loader.util;

import java.io.File;
import java.util.HashMap;

import gov.nih.nci.ncicb.cadsr.semconn.ReportHandler;
import gov.nih.nci.ncicb.cadsr.semconn.ModelAnnotator;
import gov.nih.nci.ncicb.cadsr.semconn.Configuration;
import gov.nih.nci.ncicb.cadsr.semconn.FixEAXMI;

import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;

import org.jdom.Element;
import org.jdom.Attribute;
import org.jdom.Namespace;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jaxen.JaxenException;
import org.jaxen.jdom.JDOMXPath;

import org.jdom.input.SAXBuilder;

import gov.nih.nci.ncicb.cadsr.semconn.SemanticConnectorException;

import java.util.*;

public class SemanticConnectorUtil {

  ProgressListener progressListener;

  public void addProgressListener(ProgressListener l) {
    progressListener = l;
  }

  public static String getCsvFilename(String xmiFilename) {
    
    return xmiFilename.substring(0, xmiFilename.lastIndexOf("/") + 1)
      + "EVSReport_" 
      + xmiFilename.substring(xmiFilename.lastIndexOf("/")+1,xmiFilename.lastIndexOf("."))
      + ".csv";

  }


  /**
   * @return the location of the generated report.
   */  
  public String annotateXmi(String inputXmi) 
    throws SemanticConnectorException {
    

    String filepath = inputXmi.substring(0, inputXmi.lastIndexOf("/") + 1);
    
    String outputXmi = filepath + "Annotated_" + inputXmi.substring(inputXmi.lastIndexOf("/") + 1);

    ModelAnnotator annotator = new ModelAnnotator();
    annotator.addProgressListener(progressListener);

    try {
      annotator.annotateXMI(
                            inputXmi,
                            getCsvFilename(inputXmi),
                            outputXmi,
                            true
                            );
    } catch (Exception e){
    } // end of try-catch

    return outputXmi;
    
  }


  /**
   * @return the location of the generated report.
   */  
  public String generateReport(String inputXmi) 
    throws SemanticConnectorException {
    

    String filepath = inputXmi.substring(0, inputXmi.lastIndexOf("/") + 1);
    
    ModelAnnotator annotator = new ModelAnnotator();
    annotator.addProgressListener(progressListener);

    String outputCsv = getCsvFilename(inputXmi);

    try {
      annotator.generateEVSReport(
                                  inputXmi,
                                  outputCsv,
                                  true) ;
    } catch (Exception e){
    } // end of try-catch
      
    return outputCsv;

//     return filepath + "/" + "EVSReport_" + inputXmi.substring(inputXmi.lastIndexOf("/")+1, inputXmi.lastIndexOf(".")) + ".csv";
    
  }

  public String fixXmi(String filename) {
    String filepath = filename.substring(0, filename.lastIndexOf("/") + 1);

    String fixedXmi = filepath + "/fixed_" + filename.substring(filename.lastIndexOf("/")+1);


//     int ind = filename.lastIndexOf(".xmi");
//     String filenameNoExt = filename.substring(0, ind);
//     String fixedXmi = filenameNoExt + ".xmi";
    try {
      new FixEAXMI().fix(filename, fixedXmi);
    } catch (Exception e){
      e.printStackTrace();
    } // end of try-catch
    return fixedXmi;
  }

//   private static ArrayList readModel(Element modelElement){
//     try
//       {
//         // Objects eligible for semantic lookup must reside in a UML:Package entity with attribute name="Logical Model" 
//         Collection elements = getClasses(modelElement, "//*[local-name()='Package' and @name='Logical Model']//*[local-name()='Class' and @isRoot='false']");

//         String UMLClass = null;

//         ArrayList<Element> umlEntities = new ArrayList();
        
//         Map<String, String> elementValues = null;
        
//         for (Element classElement : umlEntities) {
//           elementValues = new HashMap();
//           UMLClass = (String)classElement.getAttributeValue("name");
          
//           elementValues.put(Configuration.getUMLClassCol(), UMLClass);
          
// //           getTaggedValue(classElement, "documentation");

//           List<Element> attributeList = getElements(classElement, "Attribute");

          
//           for(Element attElt : attributeList)
//             {
//               elementValues = new HashMap();
//               elementValues.put(Configuration.getUMLClassCol(), UMLClass);
// //               getTaggedValue((Element)iter.next(), "description");
//             }
//           }
//         return umlEntities;
//       } catch (Exception ex) {
//       throw new RuntimeException("Error while reading model", ex);
//     }
//   }

//   private static Collection getClasses(Element modelElement, String xpathExpression) throws JaxenException {
//     JDOMXPath path = new JDOMXPath(xpathExpression);
//     Collection elementCollection = path.selectNodes(modelElement);
//     return elementCollection;
//   }


//   private static List<Element> getElements(Element classElement, String elementName) throws JaxenException{
    
//     List<Element> elementList = null;
//     try {
//       String exp = ".//*[local-name()='"+elementName+"']";
//       elementList = (List) (new JDOMXPath(exp)).selectNodes(classElement);
      
//     } catch (Exception ex) {
//       throw new RuntimeException("Error searching for elements " + elementName
//                                  + " for class " + classElement.getAttributeValue("name"), ex);
//     }
//     return elementList;
    
//   }


// 	public void getTaggedValue(Element classElement, String tag) throws JaxenException
// 	{
// 		Element taggedValue = null;
// 		String id = classElement.getAttributeValue("xmi.id");
// 		String tagName = null;
// 		ArrayList ccodeList = new ArrayList();
// 		ArrayList classificationList = new ArrayList();
// 		String description = null;
// 		int index = 0;

// 		elementValues.put(Configuration.getUMLEntityCol(), classElement.getAttributeValue("name"));
// 		try {

// 			String exp = "//*[local-name()='TaggedValue' and @modelElement='"
// 					+ id + "']";
// 			List taggedValues = (List) (new JDOMXPath(exp)).selectNodes(modelElement);

// 			ArrayList tagNames = Configuration.getTagNames();

// 			for(Iterator iter=taggedValues.iterator(); iter.hasNext();)
// 			{

// 			   taggedValue = (Element)iter.next();

// 			   if(taggedValue.getAttributeValue("tag").equalsIgnoreCase(tag))
// 			   {
// 			   	  description = (String)taggedValue.getAttributeValue("value");
// 			      elementValues.put(Configuration.getUMLDescriptionCol(), description);
// 			   }
// 			   else
// 			   {
// 			   	  tagName = (String)taggedValue.getAttributeValue("tag");
// 			   	  if(tagNames.contains(tagName))
// 					 elementValues.put(tagName, taggedValue.getAttributeValue("value"));
// 			   }
// 			}

// 			umlEntities.add(elementValues);

// 		} catch (Exception ex) {
// 			throw new RuntimeException("Error searching for TaggedValue " + tag
// 					+ " for class " + classElement.getAttributeValue("name"), ex);
// 		}

// 	}


}