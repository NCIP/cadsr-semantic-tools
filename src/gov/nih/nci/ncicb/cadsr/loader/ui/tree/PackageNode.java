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
package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PackageNode extends AbstractUMLNode<UMLNode> 
  implements ReviewableUMLNode {
  
  static final Icon REVIEWED_ICON = 
    new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-package-checked.gif"));
  
  static final Icon DEFAULT_ICON = 
    new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-package.gif"));

  private boolean reviewed = true, 
    isInherited = false;

  public PackageNode(ClassificationSchemeItem csi, String display) {
    fullPath = csi.getName();
    
    this.display = display != null?
      display:fullPath;
    
    userObject = csi;
  }
  
  public PackageNode(String fullName, String display) {
    this(fullName, display, false);
  }

  public PackageNode(String fullName, String display, boolean isInherited) {
    fullPath = fullName;
    
    this.display = display != null?
      display:fullName;
    
    icon = DEFAULT_ICON;
    
    this.isInherited = isInherited;
  }

  public void setReviewed(boolean currentStatus) 
  {    
    boolean changeIcon = true;
    
    for(UMLNode l : getChildren()) 
    {
      if(l instanceof ClassNode) {
        ClassNode next = (ClassNode) l;
        if(!next.getIcon().equals(ClassNode.REVIEWED_ICON)) 
          {
            changeIcon = false;
            break;
          }
      } else if(l instanceof ValueDomainNode) {
        ValueDomainNode next = (ValueDomainNode) l;
        if(!next.getIcon().equals(ValueDomainNode.REVIEWED_ICON)) 
          {
            changeIcon = false;
            break;
          }
      }
    }
    
    if(changeIcon)
    {
      setIcon(REVIEWED_ICON);
    }
    else
      setIcon(DEFAULT_ICON);
  }
  
  public boolean isReviewed() 
  {
    return reviewed;
  }

  public boolean isInherited() {
    return isInherited;
  }

}