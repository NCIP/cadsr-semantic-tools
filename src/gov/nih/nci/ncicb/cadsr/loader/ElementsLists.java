package gov.nih.nci.ncicb.cadsr.loader;

import gov.nih.nci.ncicb.cadsr.domain.*;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * List of cadsr objects to be persisted.<br/>
 *
 * The UMLListener feeds this list. The UMLPersister consumes this list.
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class ElementsLists {

  private HashMap objects = new HashMap();

  private Logger logger = Logger.getLogger(ElementsLists.class.getName());

  private static ElementsLists instance = new ElementsLists();

  private ElementsLists() {}

  public static ElementsLists getInstance() {
    return instance;
  }

  /**
   * remove an element from this list.
   *
   * @param o an <code>Object</code> 
   */
  public void removeElement(Object o) {
    List list = (List)objects.get(o.getClass().getName());
    if(list == null)
      return;
    list.remove(o);
    
  }

  /**
   * Add an element to this list.
   *
   * @param o an <code>Object</code>
   */
  public void addElement(Object o) {
    List list = (List)objects.get(o.getClass().getName());
    if(list == null)
      list = new ArrayList();

    list.add(o);
    objects.put(o.getClass().getName(), list);
  }

  /**
   * Get the list of elements with for a particular class type.
   *
   * @param type The class of the list of objects to return.
   * @return a <code>List</code> of objects of class <code>type<code>
   */
  public List getElements(Class type) {
    return (List)objects.get(type.getName());
  }


}