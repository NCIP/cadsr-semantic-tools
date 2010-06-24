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
import gov.nih.nci.ncicb.cadsr.loader.event.ElementChangeEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ElementChangeListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.AbstractUMLNode;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.ReviewableUMLNode;

import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;

import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;


import java.util.HashMap;

/**
 * ChangeTracker keeps track of objects and their modification Status
 * by storing them in a HashMap <br>
 * 
 * Upon saving a file, this tracker may be used to update 
 * only those elements that were modified
 * 
 * @author chris.ludet@oracle.com
 */
public class ChangeTracker implements ElementChangeListener
{
  private HashMap<String, Boolean> map = new HashMap<String, Boolean>(); 

  private static ChangeTracker instance = new ChangeTracker();
  private ChangeTracker() {

  }
  public static ChangeTracker getInstance() {
    return instance;
  }
  
  public Boolean get(String key) 
  {   
    if(map.containsKey(key))
      return map.get(key);
    else return false;
  }

  public void put(String key, boolean value) 
  {
    map.put(key, value);
  }
  
  public void elementChanged(ElementChangeEvent event) 
  {
    if(event.getUserObject() instanceof AbstractUMLNode) {
      AbstractUMLNode absNode = (AbstractUMLNode) event.getUserObject();
      this.put(absNode.getFullPath(), true);
    } else if(event.getUserObject() instanceof DataElement) {
      this.put(LookupUtil.lookupFullName((DataElement)event.getUserObject()), true);
    } else if(event.getUserObject() instanceof Concept) {
      this.put(((Concept)event.getUserObject()).getPreferredName(), true);
    } else if(event.getUserObject() instanceof ValueMeaning) {
      // !!! THIS IS NOT WORKING BECAUSE WE NEED THE VD NAME IN THERE 
      this.put("ValueDomains." + ((ValueMeaning)event.getUserObject()).getLongName(), true);
    }

  }

  public void clear() {
    map.clear();
  }
  
  public boolean isEmpty() {
    return map.isEmpty();
  }
}