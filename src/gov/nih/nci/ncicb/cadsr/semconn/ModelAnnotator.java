package gov.nih.nci.ncicb.cadsr.semconn;

import gov.nih.nci.semantic.util.Configuration;

import org.apache.log4j.*;

import org.jaxen.JaxenException;

import org.jaxen.jdom.JDOMXPath;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

import org.jdom.input.SAXBuilder;

import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * <!-- LICENSE_TEXT_START -->
 * <!-- LICENSE_TEXT_END -->
 * Copyright 2001-2004 SAIC. Copyright 2001-2003 SAIC. This software was
 * developed in conjunction with the National Cancer Institute, and so to the
 * extent government employees are co-authors, any rights in such works shall
 * be subject to Title 17 of the United States Code, section 105.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the disclaimer of Article 3, below.
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution. 2. The end-user
 * documentation included with the redistribution, if any, must include the
 * following acknowledgment: "This product includes software developed by the
 * SAIC and the National Cancer Institute." If no such end-user documentation
 * is to be included, this acknowledgment shall appear in the software itself,
 * wherever such third-party acknowledgments normally appear. 3. The names
 * "The National Cancer Institute", "NCI" and "SAIC" must not be used to
 * endorse or promote products derived from this software. 4. This license
 * does not authorize the incorporation of this software into any third party
 * proprietary programs. This license does not authorize the recipient to use
 * any trademarks owned by either NCI or SAIC-Frederick. 5. THIS SOFTWARE IS
 * PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER
 * INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * @author caBIO Team
 * @version 1.0
 */
public final class ModelAnnotator {
  private static Logger log = Logger.getLogger(ModelAnnotator.class.getName());
  private String outputXMILocation;
  private String inputXMILocation;
  private Element modelElement;
  private String xmlns = "";
  public ReportHandler m_ReportHandler;
  private ArrayList umlEntities;
  private HashMap elementValues = null;
  private String UMLClass;
  private PrintStream infoStream = null;

  /**
   * Constructor
   */
  public ModelAnnotator() {
    Configuration.loadProperties();

    try {
      infoStream = new PrintStream(new FileOutputStream("Log.txt", true), true);
    }

    catch (FileNotFoundException fe) {
      log.error("FileNotFoundException: " + fe.getMessage());
    }
  }

  /**
   * Constructor
   *
   * @param inXMI
   * @param outXMI
   */
  public ModelAnnotator(
    String inXMI,
    String outXMI,
    String outputCsv) {
    this();

    outputXMILocation = outXMI;

    inputXMILocation = inXMI;

    m_ReportHandler = new ReportHandler(inputXMILocation, outputCsv);

    try {
      SAXBuilder builder = new SAXBuilder();

      Document doc = builder.build(inputXMILocation);

      modelElement = doc.getRootElement();
    }
    catch (Exception ex) {
      log.error("Exception: " + ex.getMessage());

      throw new RuntimeException("Error initializing model", ex);
    }

    try {
      run();
    }
    catch (Exception e) {
      log.error("Exception: " + e.getMessage());
    }
  }

  /**
   * Start of the process
   *
   * @throws Exception
   */
  public void run() throws Exception {
    readModel();

    ArrayList list = m_ReportHandler.processHandle(umlEntities);

    if ((list != null) && (list.size() > 0)) {
      updateModel(list);
    }

    else
    {
      writeModel();
    }
  }

  /**
   * Reads the model and creates list of umlEntities
   */
  public void readModel() {
    try {
      // Objects eligible for semantic lookup must reside in a UML:Package entity with attribute name="Logical Model" 
      Collection elements =
        getClasses(
          "//*[local-name()='Package' and @name='Logical Model']//*[local-name()='Class' and @isRoot='false']");

      String UMLClass = null;

      umlEntities = new ArrayList();

      for (Iterator i = elements.iterator(); i.hasNext();) {
        Element classElement = (Element) i.next();

        elementValues = new HashMap();

        UMLClass = (String) classElement.getAttributeValue("name");

        elementValues.put(Configuration.getUMLClassCol(), UMLClass);

        getTaggedValue(classElement, "documentation");

        List attributeList = getElements(classElement, "Attribute");

        for (Iterator iter = attributeList.iterator(); iter.hasNext();) {
          elementValues = new HashMap();

          elementValues.put(Configuration.getUMLClassCol(), UMLClass);

          getTaggedValue((Element) iter.next(), "description");
        }
      }
    }
    catch (Exception ex) {
      log.error("Exception occured while reading model: " + ex.getMessage());

      throw new RuntimeException("Error while reading model", ex);
    }
  }

  /**
   * Gets classes for the given expression
   *
   * @param xpathExpression
   *
   * @return
   *
   * @throws JaxenException
   */
  private Collection getClasses(String xpathExpression)
    throws JaxenException {
    JDOMXPath path = new JDOMXPath(xpathExpression);

    Collection elementCollection = path.selectNodes(modelElement);

    return elementCollection;
  }

  /**
   * Gets the specified elements of the specified classElement
   *
   * @param classElement
   * @param elementName
   *
   * @return
   */
  public List getElements(
    Element classElement,
    String elementName) throws JaxenException {
    List elementList = null;

    try {
      String exp = ".//*[local-name()='" + elementName + "']";

      elementList = (List) (new JDOMXPath(exp)).selectNodes(classElement);
    }
    catch (Exception ex) {
      log.error(
        "Exception occured while searching for elements " + elementName +
        " for class " + classElement.getAttributeValue("name") +
        ex.getMessage());

      throw new RuntimeException(
        "Error searching for elements " + elementName + " for class " +
        classElement.getAttributeValue("name"), ex);
    }

    return elementList;
  }

  /**
   * Gets the element of the specified classElement
   *
   * @param classElement
   * @param elementName
   *
   * @return
   */
  public Element getElement(
    Element classElement,
    String exp) throws JaxenException {
    Element element = null;

    try {
      element = (Element) (new JDOMXPath(exp)).selectSingleNode(classElement);
    }
    catch (Exception ex) {
      log.error(
        "Exception occured while searching for expression " + exp +
        " in class " + classElement.getAttributeValue("name") +
        ex.getMessage());

      throw new RuntimeException(
        "Error searching for expression " + exp + " in class " +
        classElement.getAttributeValue("name"), ex);
    }

    return element;
  }

  /**
   * Gets the TaggedValue element for the specified classElement and tag, and
   * adds to the list
   *
   * @param classElement
   * @param tag
   */
  public void getTaggedValue(
    Element classElement,
    String tag) throws JaxenException {
    Element taggedValue = null;

    String id = classElement.getAttributeValue("xmi.id");

    String tagName = null;

    ArrayList ccodeList = new ArrayList();

    ArrayList classificationList = new ArrayList();

    String description = null;

    int index = 0;

    elementValues.put(
      Configuration.getUMLEntityCol(), classElement.getAttributeValue("name"));

    try {
      String exp =
        "//*[local-name()='TaggedValue' and @modelElement='" + id + "']";

      List taggedValues = (List) (new JDOMXPath(exp)).selectNodes(modelElement);

      ArrayList tagNames = Configuration.getTagNames();

      for (Iterator iter = taggedValues.iterator(); iter.hasNext();) {
        taggedValue = (Element) iter.next();

        if (taggedValue.getAttributeValue("tag").equalsIgnoreCase(tag)) {
          description = (String) taggedValue.getAttributeValue("value");

          elementValues.put(Configuration.getUMLDescriptionCol(), description);
        }

        else {
          tagName = (String) taggedValue.getAttributeValue("tag");

          if (tagNames.contains(tagName)) {
            elementValues.put(tagName, taggedValue.getAttributeValue("value"));
          }
        }
      }

      umlEntities.add(elementValues);
    }
    catch (Exception ex) {
      log.error(
        "Error searching for TaggedValue " + tag + " for class " +
        classElement.getAttributeValue("name") + ex.getMessage());

      throw new RuntimeException(
        "Error searching for TaggedValue " + tag + " for class " +
        classElement.getAttributeValue("name"), ex);
    }
  }

  /**
   * Gets TaggedValue Element
   *
   * @param id
   * @param tag
   *
   * @return Element
   *
   * @throws JaxenException
   */
  private Element getTaggedValue(
    String id,
    String tag) throws JaxenException {
    Element tv = null;

    try {
      String exp =
        "//*[local-name()='TaggedValue' and @modelElement='" + id +
        "' and @tag='" + tag + "']";

      tv = (Element) (new JDOMXPath(exp)).selectSingleNode(modelElement);
    }
    catch (Exception ex) {
      log.error(
        "Error searching for TaggedValue " + tag + " for modelElement " + id +
        ", " + ex.getMessage());

      throw new RuntimeException(
        "Error searching for TaggedValue " + tag + " for modelElement " + id, ex);
    }

    return tv;
  }

  /**
   * Write Model
   */
  public void writeModel() {
    try {
      File f = new File(outputXMILocation);

      Writer writer = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");

      XMLOutputter xmlout = new XMLOutputter();

      xmlout.setFormat(Format.getPrettyFormat());

      writer.write(xmlout.outputString(modelElement));

      writer.flush();

      writer.close();
    }
    catch (Exception ex) {
      log.error(
        "Error writing to " + outputXMILocation + ": " + ex.getMessage());

      throw new RuntimeException("Error writing to " + outputXMILocation, ex);
    }
  }

  /**
   * Update Model with the specified list
   *
   * @param evsValues
   *
   * @throws Exception
   */
  public void updateModel(ArrayList evsValues) throws Exception {
    HashMap taggedValuesMap = null;

    String UMLClass = null;

    String exp = null;

    String UMLEntity = null;

    Element classElement = null;

    String xmiid = null;

    try {
      System.out.println("Annotating Model....");

      for (int i = 0; i < evsValues.size(); i++) {
        taggedValuesMap = (HashMap) evsValues.get(i);

        UMLClass = (String) taggedValuesMap.get(Configuration.getUMLClassCol());

        UMLEntity =
          (String) taggedValuesMap.get(Configuration.getUMLEntityCol());

        if (UMLClass.equals(UMLEntity)) {
          exp = "//*[local-name()='Class'and @name='" + UMLEntity + "']";

          classElement = getElement(modelElement, exp);

          xmiid = classElement.getAttributeValue("xmi.id");
        }

        else {
          exp = "//*[local-name()='Class'and @name='" + UMLClass + "']";

          classElement = getElement(modelElement, exp);

          exp = ".//*[local-name()='Attribute'and @name='" + UMLEntity + "']";

          Element attributeElement = getElement(classElement, exp);

          xmiid = attributeElement.getAttributeValue("xmi.id");
        }

        String VerifiedFlag =
          (String) taggedValuesMap.get(Configuration.getVerifiedFlagCol());

        if (VerifiedFlag.equals("1")) {
          buildTaggedValue(taggedValuesMap, xmiid, classElement.getNamespace());
        }
      }

      writeModel();
    }
    catch (NullPointerException ne) {
      log.error("NullPointerException: " + ne.getMessage());

      throw new Exception(ne.getMessage());
    }

    catch (Exception e) {
      log.error("Exception: " + e.getMessage());

      throw new Exception(
        "Exception occured while updating model: " + e.getMessage());
    }
  }

  /**
   * Builds new TaggedValue elements and adds it to the parent element
   *
   * @param taggedValuesMap
   * @param xmiid
   * @param namespace
   * @param parentElement
   *
   * @throws Exception
   */
  private void buildTaggedValue(
    HashMap taggedValuesMap,
    String xmiid,
    Namespace namespace) throws Exception {
    ArrayList tagNames = new ArrayList();

    tagNames = Configuration.getTagNames();

    ArrayList newTagNames = new ArrayList();

    boolean multipleCodes = false;

    Element tv = null;

    int index = 0;

    try {
      String classification =
        (String) taggedValuesMap.get(Configuration.getClassificationCol());

      String newId = getNewId(xmiid);

      for (int i = 0; i < tagNames.size(); i++) {
        String tagName = (String) tagNames.get(i);

        if (taggedValuesMap.containsKey(tagName)) {
          //get TaggedValue element for this tagName
          tv = getTaggedValue(xmiid, tagName);

          index = index + 1;

          //add  taggedvalue element
          addTaggedValue(
            tv, tagName, (String) taggedValuesMap.get(tagName),
            newId + (new Integer(index)).toString(), xmiid, namespace);
        }
      }
    }
    catch (Exception e) {
      log.error("Exception: " + e.getMessage());

      throw new Exception("Exception in buildTaggedValue: " + e.getMessage());
    }
  }

  /**
   * Build new id for xmi.id
   *
   * @param xmiid
   *
   * @return id throws JaxenException
   */
  private String getNewId(String xmiid) throws JaxenException {
    String id = null;

    try {
      String exp =
        "//*[local-name()='TaggedValue' and @modelElement='" + xmiid + "']";

      List tvs = (List) (new JDOMXPath(exp)).selectNodes(modelElement);

      Element tv = (Element) tvs.get(tvs.size() - 1);

      if (tv != null) {
        id = (String) tv.getAttributeValue("xmi.id") + "_tag";
      }

      else
      {
        id = xmiid + "_tag";
      }
    }

    catch (Exception e) {
      log.error("Exception while creating getNewId: " + e.getMessage());

      throw new RuntimeException(
        "Exception while creating getNewId" + e.getMessage());
    }

    return id;
  }

  /**
   * Create and add TaggedValues to the model
   *
   * @param element
   * @param tagName
   * @param value
   * @param index
   * @param xmiid
   * @param namespace
   *
   * @throws Exception
   */
  public void addTaggedValue(
    Element element,
    String tagName,
    String value,
    String newId,
    String xmiid,
    Namespace namespace) throws Exception {
    try {
      if (element != null) {
        element.setAttribute("value", value);
      }

      else {
        Element taggedValue = new Element("TaggedValue", namespace);

        taggedValue.setNamespace(
          Namespace.getNamespace("UML", "href://org.omg/UML"));

        taggedValue.setAttribute(new Attribute("xmi.id", newId));

        taggedValue.setAttribute(new Attribute("tag", tagName));

        taggedValue.setAttribute(new Attribute("modelElement", xmiid));

        taggedValue.setAttribute(new Attribute("value", value));

        Element elem1 = getElement(modelElement, "//*[local-name()='Model'");

        Element parentElement = elem1.getParentElement();

        parentElement.addContent(taggedValue);
      }
    }
    catch (Exception e) {
      log.error(
        "Exception while creating new TaggedValue element: " + e.getMessage());

      throw new Exception(
        "Exception in creating new TaggedValue element: " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    ModelAnnotator sc = new ModelAnnotator(args[0], args[1], args[2]);
  }

  public void addXMIElement() {
  }

  public void emailSemanticReport() {
  }

  public int getModelEntityCount() {
    return 0;
  }

  public boolean isValidXMI() {
    return false;
  }

  public void modifyXMIElement() {
  }
}
