package gov.nih.nci.ncicb.cadsr.loader.util;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jaxen.JaxenException;
import org.jaxen.jdom.JDOMXPath;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public class DatatypeMappingXMLUtil {

  public static Map<String, String> readMapping(URI uri) {
    try {
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(new java.io.File(uri));
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

  public void writeMapping(String uri, Map mapping) {
    
  }

}