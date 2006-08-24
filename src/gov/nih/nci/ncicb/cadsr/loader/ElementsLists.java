/*
 * Copyright 2000-2003 Oracle, Inc. This software was developed in conjunction with the National Cancer Institute, and so to the extent government employees are co-authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105.
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

  private Map<Object, Map<String, String>> userValues = new HashMap<Object, Map<String, String>>();

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
  public synchronized void addElement(Object o) {
    List list = (List)objects.get(o.getClass().getName());
    if(list == null)
      list = new ArrayList();

    list.add(o);
    objects.put(o.getClass().getName(), list);
  }


  /**
   * Get the list of elements with for a particular class type.
   *
   * @param obj Object which class type is returned.
   * @return a <code>List</code> of objects of class <code>obj.getClass()<code>
   */
  public <T> List<T> getElements(T obj) {
    return (List<T>) getElements(obj.getClass());
  }

  /**
   * Get the list of elements with for a particular class type.
   *
   * @param type The class of the list of objects to return.
   * @return a <code>List</code> of objects of class <code>type<code>
   * @deprecated use <T> List<T> getElements(T obj) instead
   */
  public List getElements(Class type) {
    List l = (List)objects.get(type.getName());
    if(l != null)
      return l;
    else 
      return new ArrayList();
  }

  
  public String getUserValues(Object o, String key) {
    Map<String, String> map = userValues.get(o);
    if(map == null)
      return null;

    return map.get(key);
  }

  public void addUserValue(Object o, String key, String value) {
    Map map = userValues.get(o);
    if(map == null) {
      map = new HashMap<String, String>();
      userValues.put(o, map);
    }    

    map.put(key, value);

  }

}