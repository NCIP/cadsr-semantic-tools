package gov.nih.nci.ncicb.cadsr.loader.util;

import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jaxen.JaxenException;
import org.jaxen.jdom.JDOMXPath;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public class DatatypeMappingXMLUtil {

  public static Map<String, String> readMapping(URL url) {
    try {
      SAXBuilder builder = new SAXBuilder();
      
      Document doc = builder.build(url);
      Element modelElement = doc.getRootElement();
      
      Map<String, String> vdMap = new HashMap();
      List<Element> dataTypes = modelElement.getChildren();
      for(Element aliasList : dataTypes)
        for(Element alias : (List<Element>)aliasList.getChildren())
          vdMap.put(alias.getAttributeValue("name"), aliasList.getAttributeValue("name"));
          

      return vdMap;
    } catch (Exception e){
      e.printStackTrace();
    } // end of try-catch
      

      return null;
  }

  public static void writeMapping(String filename, Map<String, String> mapping) {

     Element rootElement = new Element("Datatype-Mapping");
     Document userDoc = new Document(rootElement);
     Set<String> keys = mapping.keySet();
     
     Map<String, Element> elementsMap = new HashMap<String, Element>();
     
     for(String key : keys) {
   
      String mapValue = mapping.get(key);
      Element aliasList = elementsMap.get(mapValue);
      if(aliasList == null) {
        aliasList = new Element("alias-list");
        aliasList.setAttribute("name", mapValue);
        rootElement.addContent(aliasList);
        elementsMap.put(mapValue, aliasList);
      }
   
      Element alias = new Element("alias");
      alias.setAttribute("name", key);
      aliasList.addContent(alias);

     }   
      
      
     try 
     {
     FileWriter fw = new FileWriter(filename);
     
     new XMLOutputter(Format.getPrettyFormat()).output(userDoc, fw);

     }
     catch (Exception e) 
     {
       throw new RuntimeException("Error writing to " + filename,  e);
     }
  }

}