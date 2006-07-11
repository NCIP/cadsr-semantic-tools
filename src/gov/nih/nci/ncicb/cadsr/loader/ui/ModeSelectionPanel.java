/*
 * Copyright 2000-2005 Oracle, Inc. This software was developed in conjunction with the National Cancer Institute, and so to the extent government employees are co-authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105.
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

import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import gov.nih.nci.ncicb.cadsr.loader.util.*;

/**
 * Wizard step to choose run mode
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class ModeSelectionPanel extends JPanel {

  private JRadioButton fixEaOption, roundtripOption, reportOption, curateOption, annotateOption, reviewOption;
  private ButtonGroup group;
  private JPanel _this = this;

  private UserSelections userSelections = UserSelections.getInstance();
  private UserPreferences prefs = UserPreferences.getInstance();

  private JCheckBox privateApiCb = new JCheckBox("Use Private API", prefs.isUsePrivateApi());
  
  public ModeSelectionPanel()
  {
    initUI();
  }

  public void addActionListener(ActionListener l) {
    fixEaOption.addActionListener(l);
    roundtripOption.addActionListener(l);
    reportOption.addActionListener(l);
    curateOption.addActionListener(l);
    annotateOption.addActionListener(l);
    reviewOption.addActionListener(l);
  }

  public String getSelection() {
    return group.getSelection().getActionCommand();
  }

  public boolean usePrivateApi() {
    return privateApiCb.isSelected();
  }

  private void initUI() {

    this.setLayout(new BorderLayout());
    
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    infoPanel.setBackground(Color.WHITE);
    infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    infoPanel.add(new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("siw-logo3_2.gif"))));

    JLabel infoLabel = new JLabel("<html>Welcome to the Semantic Integration Workbench</html>");
    infoPanel.add(infoLabel);
    
    this.add(infoPanel, BorderLayout.NORTH);
    
    group = new ButtonGroup();

    fixEaOption = new JRadioButton("Run the 'Fix-XMI' Task");
    fixEaOption.setActionCommand(RunMode.FixEa.toString());

    roundtripOption = new JRadioButton("Perform XMI Roundtrip");
    roundtripOption.setActionCommand(RunMode.Roundtrip.toString());
    
    reportOption = new JRadioButton("Run Semantic Connector (First Run)");
    reportOption.setActionCommand(RunMode.GenerateReport.toString());
        
    curateOption = new JRadioButton("Curate Semantic Connector Report");
    curateOption.setActionCommand(RunMode.Curator.toString());

    annotateOption = new JRadioButton("Run Semantic Connector (Second Run)");
    annotateOption.setActionCommand(RunMode.AnnotateXMI.toString());
       
    reviewOption = new JRadioButton("Review Annotated Model");
    reviewOption.setActionCommand(RunMode.Reviewer.toString());

    if(prefs.getModeSelection() == null) 
      reportOption.setSelected(true);
    else if(prefs.getModeSelection().equals(RunMode.GenerateReport.toString()))
      reportOption.setSelected(true);
    else if(prefs.getModeSelection().equals(RunMode.Curator.toString()))
      curateOption.setSelected(true);
    else if(prefs.getModeSelection().equals(RunMode.Reviewer.toString()))
      reviewOption.setSelected(true);
    else
      reportOption.setSelected(true);
      
    group.add(fixEaOption);
    group.add(roundtripOption);
    group.add(reportOption);
    group.add(curateOption);
    group.add(annotateOption);
    group.add(reviewOption);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(0, 1));

    buttonPanel.add
      (new JLabel("<html>Choose from the following SIW Options:</html>"));

    buttonPanel.add(fixEaOption);
    buttonPanel.add(roundtripOption);
    buttonPanel.add(reportOption);
    buttonPanel.add(curateOption);
    buttonPanel.add(annotateOption);
    buttonPanel.add(reviewOption);

    this.setLayout(new BorderLayout());
    this.add(infoPanel, BorderLayout.NORTH);
    this.add(buttonPanel, BorderLayout.CENTER);


    Font font = new Font("Serif", Font.PLAIN, 11);

    privateApiCb.setFont(font);
    JLabel privateApiLabel = new JLabel("<html><body>The private API is offered as an alternative to the caCORE public API.<br> It requires that the user be inside the NCI network or use VPN.</body></html>");
    privateApiLabel.setFont(font);

    JPanel privateApiPanel = new JPanel();
    privateApiPanel.setLayout(new FlowLayout());
    privateApiPanel.add(privateApiCb);
    privateApiPanel.add(privateApiLabel);

    this.add(privateApiPanel, BorderLayout.SOUTH);

  }

  private void insertInBag(JPanel bagComp, Component comp, int x, int y) {

    insertInBag(bagComp, comp, x, y, 1, 1);

  }

  private void insertInBag(JPanel bagComp, Component comp, int x, int y, int width, int height) {
    JPanel p = new JPanel();
    p.add(comp);

    bagComp.add(p, new GridBagConstraints(x, y, width, height, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }
  
}