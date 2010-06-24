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

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

import java.io.File;

import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;
import gov.nih.nci.ncicb.cadsr.loader.event.*;

public class SemanticConnectorPanel extends JPanel 
  implements ProgressListener {

  private JRadioButton optionButton1, optionButton2;
  private JPanel _this = this;
  private ProgressPanel progressPanel;
  private boolean isProgress = false;
  private int goal;

  public static final int MODE_INITIAL = 1;
  public static final int MODE_SUBSEQUENT = 2;

  private int mode = 0;

  private String[][] options = 
  {{"error", "error", "error"}, 
   {"<html>It appears there is no Sematic Connector <br>annotation file for the file you selected.<br> Do you want to: </html>",
    "Extract Annotations from the XMI file?",
    "Run Semantic Connector to create an annotation file?"},
   {"<html>The is a Semantic Connector annotation file<br> Do you want to run Semantic Connector?<html>", 
    "No", 
    "Yes"}};
  
  public SemanticConnectorPanel()
  {
//     initUI();
  }
  public SemanticConnectorPanel(int progressGoal)
  {
    this.isProgress = true;
    this.goal = progressGoal;
//     initUI();
  }

  public void newProgressEvent(ProgressEvent evt) {
    progressPanel.newProgressEvent(evt);
  }

  public void setMode(int mode) {
    this.mode = mode;
    initUI();
  }

  public int getSelection() {
    return optionButton2.isSelected()?2:1;
  }

  private void initUI() {

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    
    infoPanel.setBackground(Color.WHITE);
    infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    infoPanel.add(new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("xmi.gif"))), FlowLayout.LEFT);
    
    JLabel textLabel = new JLabel(options[mode][0]);

    infoPanel.add(textLabel);

    ButtonGroup group = new ButtonGroup();
    
    optionButton1 = new JRadioButton(options[mode][1]);
    optionButton2 = new JRadioButton(options[mode][2]);

    optionButton1.setSelected(true);

    group.add(optionButton1);
    group.add(optionButton2);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(0, 1));

    buttonPanel.add
      (new JLabel("Semantic Connector will tag the XMI document with concepts."));

    buttonPanel.add(optionButton1);
    buttonPanel.add(optionButton2);

    progressPanel = new ProgressPanel(goal);

    if(isProgress) {
      progressPanel.setVisible(true);
      optionButton1.setEnabled(false);
      optionButton2.setEnabled(false);
    } else {
      progressPanel.setVisible(false);
    }

    this.setLayout(new BorderLayout());
    this.add(infoPanel, BorderLayout.NORTH);
    this.add(buttonPanel, BorderLayout.CENTER);
    this.add(progressPanel, BorderLayout.SOUTH);

  }

}