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
package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.loader.event.ElementChangeListener;
import gov.nih.nci.ncicb.cadsr.loader.event.ReviewListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationEvent;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.AssociationEndNode;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.UMLNode;

import java.beans.PropertyChangeListener;
import java.util.Set;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

/**
 * The Association viewer
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class AssociationViewPanel extends JTabbedPane
  implements NavigationListener {
    
  private ObjectClassRelationship ocr;
  private UMLNode node;

  private AssociationDetailViewPanel detailViewPanel;
  private AssociationConceptPanel roleConceptPanel;
  private AssociationConceptPanel sourceConceptPanel;
  private AssociationConceptPanel targetConceptPanel;

  private boolean showingConceptTabs = false;

  private GMEViewPanel gmeViewPanel;

  public AssociationViewPanel(UMLNode node) {
    this.node = node;
    this.ocr = (ObjectClassRelationship)node.getUserObject();

    initUI();
    update(node);
  }

  public void update(UMLNode node) {
    this.node = node;
    this.ocr = (ObjectClassRelationship)node.getUserObject();

    detailViewPanel.update(ocr);
    

    AssociationEndNode sourceEndNode = null, targetEndNode = null;
    Set<UMLNode> endNodes = node.getChildren();
    for(UMLNode n : endNodes) {
      AssociationEndNode endNode = (AssociationEndNode)n;
      if(endNode.getType() == AssociationEndNode.TYPE_SOURCE)
        sourceEndNode = endNode;
      else 
        targetEndNode = endNode;
    }

    gmeViewPanel.updateNode(node);
    roleConceptPanel.updateNode(node);
    sourceConceptPanel.updateNode(sourceEndNode);
    targetConceptPanel.updateNode(targetEndNode);

    if(ocr.getType().equals(ObjectClassRelationship.TYPE_HAS) && !showingConceptTabs) {
      addTab("Role", roleConceptPanel);
      addTab("Source", sourceConceptPanel);
      addTab("Target", targetConceptPanel);
      showingConceptTabs = true;
    } else 
    if(ocr.getType().equals(ObjectClassRelationship.TYPE_IS) && showingConceptTabs) {
      remove(roleConceptPanel);
      remove(sourceConceptPanel);
      remove(targetConceptPanel);
      showingConceptTabs = false;
    }
  }

  private void initUI() {

    JPanel detailPanel = new JPanel();
    detailPanel.setLayout(new BorderLayout());

    detailViewPanel = new AssociationDetailViewPanel(ocr);
    detailPanel.add(detailViewPanel, BorderLayout.CENTER);

    gmeViewPanel = new GMEViewPanel(node);
    detailPanel.add(gmeViewPanel, BorderLayout.SOUTH);

    JScrollPane scrollPane = new JScrollPane(detailPanel);
    addTab("Detail", scrollPane);
    
    AssociationEndNode sourceEndNode = null, targetEndNode = null;

    Set<UMLNode> endNodes = node.getChildren();
    for(UMLNode n : endNodes) {
      AssociationEndNode endNode = (AssociationEndNode)n;
      if(endNode.getType() == AssociationEndNode.TYPE_SOURCE)
        sourceEndNode = endNode;
      else 
        targetEndNode = endNode;
    }


    roleConceptPanel = new AssociationConceptPanel(node);
    sourceConceptPanel = new AssociationConceptPanel(sourceEndNode);
    targetConceptPanel = new AssociationConceptPanel(targetEndNode);
    
    if(ocr.getType().equals(ObjectClassRelationship.TYPE_HAS)) {
      addTab("Role", roleConceptPanel);
      addTab("Source", sourceConceptPanel);
      addTab("Target", targetConceptPanel);
      showingConceptTabs = true;
    }
  }

  public void addCustomPropertyChangeListener(PropertyChangeListener l) {
    if (roleConceptPanel != null) { roleConceptPanel.addPropertyChangeListener(l); }
    if (sourceConceptPanel != null) { sourceConceptPanel.addPropertyChangeListener(l); }
    if (targetConceptPanel != null) { targetConceptPanel.addPropertyChangeListener(l); }
  }

  public void navigate(NavigationEvent evt) {
      roleConceptPanel.navigate(evt);
      sourceConceptPanel.navigate(evt);
      targetConceptPanel.navigate(evt);
  }

  public void addReviewListener(ReviewListener listener) {
      roleConceptPanel.addReviewListener(listener);
      sourceConceptPanel.addReviewListener(listener);
      targetConceptPanel.addReviewListener(listener);
  }

  public void addNavigationListener(NavigationListener listener) {
      roleConceptPanel.addNavigationListener(listener);
      sourceConceptPanel.addNavigationListener(listener);
      targetConceptPanel.addNavigationListener(listener);
  }
  
  public void addElementChangeListener(ElementChangeListener listener) {
      roleConceptPanel.addElementChangeListener(listener);
      sourceConceptPanel.addElementChangeListener(listener);
      targetConceptPanel.addElementChangeListener(listener);
  }
}

