package gov.nih.nci.ncicb.cadsr.loader;

import gov.nih.nci.ncicb.cadsr.domain.*;
import java.util.*;

public class ElementsLists {

//   public static final String TYPE_DEC = "dec";
//   public static final String TYPE_OC = "oc";
//   public static final String TYPE_PROP = "prop";

  private HashMap objects = new HashMap();

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

//     System.out.println("creating key: " + o.getClass().getName());

    list.add(o);
    objects.put(o.getClass().getName(), list);
  }

  public List getElements(Class type) {
//     System.out.println("looking up: " + type.getName());
    return (List)objects.get(type.getName());
  }


}