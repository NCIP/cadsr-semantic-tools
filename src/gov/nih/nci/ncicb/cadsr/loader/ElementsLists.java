package gov.nih.nci.ncicb.cadsr.loader;

import gov.nih.nci.ncicb.cadsr.model.*;
import java.util.*;

public class ElementsLists {

//   public static final String TYPE_DEC = "dec";
//   public static final String TYPE_OC = "oc";
//   public static final String TYPE_PROP = "prop";

  private HashMap objects = new HashMap();

  public void addElement(Object o) {
    List list = (List)objects.get(o.getClass().getName());
    if(list == null)
      list = new ArrayList();

    list.add(o);
  }

  public List getElements(Class type) {
    return (List)objects.get(type.getName());
  }

}