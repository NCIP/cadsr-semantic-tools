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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;


/**
 * The Association viewer
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class AssociationViewPanel extends JScrollPane {
  
  private ObjectClassRelationship ocr;

  private JLabel[] labels = new JLabel[] {
    new JLabel("Direction"),
    new JLabel("Source Class"),
    new JLabel("Source Role"),
    new JLabel("Source Multiplicity"),
    new JLabel("Target Class"),
    new JLabel("Target Role"),
    new JLabel("Target Multiplicity")
  };

  private JLabel dirLbl = new JLabel(), 
    srcClassLbl = new JLabel(), 
    srcRoleLbl = new JLabel(), 
    srcMultLbl = new JLabel(), 
    tgtClassLbl = new JLabel(), 
    tgtRoleLbl = new JLabel(), 
    tgtMultLbl = new JLabel();

  public AssociationViewPanel(ObjectClassRelationship ocr) {
    this.ocr = ocr;

    initValues();
    initUI();
  }

  public void update(ObjectClassRelationship ocr) {
    this.ocr = ocr;
    initValues();
  }

  private void initUI() {
    JPanel scrollPanel = new JPanel(new BorderLayout());

    JPanel mainPanel = new JPanel(new GridBagLayout());
    
    insertInBag(mainPanel, labels[0], 0, 0);
    insertInBag(mainPanel, labels[1], 0, 1);
    insertInBag(mainPanel, labels[2], 0, 2);
    insertInBag(mainPanel, labels[3], 0, 3);
    insertInBag(mainPanel, labels[4], 0, 4);
    insertInBag(mainPanel, labels[5], 0, 5);
    insertInBag(mainPanel, labels[6], 0, 6);
    
    insertInBag(mainPanel, dirLbl, 1, 0);
    insertInBag(mainPanel, srcClassLbl, 1, 1);
    insertInBag(mainPanel, srcRoleLbl, 1, 2);
    insertInBag(mainPanel, srcMultLbl, 1, 3);
    insertInBag(mainPanel, tgtClassLbl, 1, 4);
    insertInBag(mainPanel, tgtRoleLbl, 1, 5);
    insertInBag(mainPanel, tgtMultLbl, 1, 6);
    
    scrollPanel.add(mainPanel, BorderLayout.CENTER);
    
    this.setViewportView(scrollPanel);

  }

  private void initValues() {
    dirLbl.setText(ocr.getDirection());
    srcClassLbl.setText(ocr.getSource().getLongName());
    srcRoleLbl.setText(ocr.getSourceRole());
    srcMultLbl.setText(ocr.getSourceLowCardinality() + ".." + ocr.getSourceHighCardinality());

    tgtClassLbl.setText(ocr.getTarget().getLongName());
    tgtRoleLbl.setText(ocr.getTargetRole());
    tgtMultLbl.setText(ocr.getTargetLowCardinality() + ".." + ocr.getTargetHighCardinality());
    
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setLayout(new BorderLayout());
    frame.setSize(500, 400);
    frame.show();
  }

  private void insertInBag(JPanel bagComp, Component comp, int x, int y) {
    JPanel p = new JPanel();
    p.add(comp);

    bagComp.add(p, new GridBagConstraints(x, y, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }

}

