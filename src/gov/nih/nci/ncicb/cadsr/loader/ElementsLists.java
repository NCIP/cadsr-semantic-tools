package gov.nih.nci.ncicb.cadsr.loader;

import gov.nih.nci.ncicb.cadsr.domain.*;
import java.util.*;

import org.apache.log4j.Logger;

public class ElementsLists {

  private HashMap objects = new HashMap();

  private Logger logger = Logger.getLogger(ElementsLists.class.getName());

  public void removeElement(Object o) {
    List list = (List)objects.get(o.getClass().getName());
    if(list == null)
      return;
    list.remove(o);
    
  }

  public void addElement(Object o) {
    List list = (List)objects.get(o.getClass().getName());
    if(list == null)
      list = new ArrayList();

//     logger.debug("creating key: " + o.getClass().getName());

    list.add(o);
    objects.put(o.getClass().getName(), list);
  }

  public List getElements(Class type) {
//     logger.debug("looking up: " + type.getName());
    return (List)objects.get(type.getName());
  }


}