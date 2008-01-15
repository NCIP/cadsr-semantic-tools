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

import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;

import gov.nih.nci.ncicb.cadsr.loader.UserSelections;

import java.awt.GridLayout;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Cursor;

import java.awt.event.*;

import java.util.*;

import gov.nih.nci.ncicb.cadsr.loader.event.*;
import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;
import gov.nih.nci.ncicb.cadsr.loader.ext.*;

import org.apache.log4j.Logger;


/**
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class RoundtripPanel extends JPanel implements KeyListener {

  private JPanel _this = this;

  private JLabel projectPrefName = new JLabel("Preferred Name");
  private JLabel projectVersion = new JLabel("Version");
  private JLabel projectWorkflowStatus = new JLabel("Workflow Status");
  private JLabel projectContextName = new JLabel("Context Name");
  private JLabel selectedProjectPrefName = new JLabel();
  private JLabel selectedProjectVersion = new JLabel();
  private JLabel selectedProjectWorkflowStatus = new JLabel();
  private JLabel selectedProjectContextName = new JLabel();

  private JButton searchButton = new JButton("Search");
  
  private List<ActionListener> actionListeners = new ArrayList<ActionListener>();

  private CadsrDialog cadsrDialog;

  private boolean verified = false;

  private static Logger logger = Logger.getLogger(RoundtripPanel.class.getName());

  public RoundtripPanel() {
    initUI();
  }

  public void addActionListener(ActionListener l) {
    actionListeners.add(l);
  }

  private void initUI() {

    this.setLayout(new BorderLayout());
    
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    infoPanel.setBackground(Color.WHITE);
    infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    infoPanel.add(new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("siw-logo3_2.gif"))));

    JLabel infoLabel = new JLabel("<html>Please click the Search button <br>to select project for Roundtrip operation.</html>");
    infoPanel.add(infoLabel);
    
    this.add(infoPanel, BorderLayout.NORTH);

    JPanel entryPanel = new JPanel();
    entryPanel.setLayout(null);

    JLabel searchText = new JLabel("<html>Please click the Search button <br>to select project for Roundtrip operation.</html>");
    searchText.setBounds(new Rectangle(30, 80, 160, 50));
    searchButton.setBounds(new Rectangle(275, 100, 80, 20));

    projectPrefName.setBounds(new Rectangle(30, 180, 135, 20));
    projectPrefName.setVisible(false);
    projectVersion.setBounds(new Rectangle(30, 210, 135, 20));
    projectVersion.setVisible(false);
    projectWorkflowStatus.setBounds(new Rectangle(30, 240, 135, 20));
    projectWorkflowStatus.setVisible(false);
    projectContextName.setBounds(new Rectangle(30, 270, 135, 20));
    projectContextName.setVisible(false);
    
    selectedProjectPrefName.setBounds(new Rectangle(180, 180, 500, 20));
    selectedProjectPrefName.setVisible(false);
    selectedProjectVersion.setBounds(new Rectangle(180, 210, 500, 20));
    selectedProjectVersion.setVisible(false);
    selectedProjectWorkflowStatus.setBounds(new Rectangle(180, 240, 500, 20));
    selectedProjectWorkflowStatus.setVisible(false);
    selectedProjectContextName.setBounds(new Rectangle(180, 270, 500, 20));
    selectedProjectContextName.setVisible(false);
    
    searchButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        cadsrDialog.setVisible(true);
        ClassificationScheme cs = (ClassificationScheme)cadsrDialog.getAdminComponent();
        if(cs == null) return;
        
        UserSelections.getInstance().setProperty("SELECTED_PROJECT", cs);;
        
        projectPrefName.setVisible(true);
        projectVersion.setVisible(true);
        projectWorkflowStatus.setVisible(true);
        projectContextName.setVisible(true);
        
        selectedProjectPrefName.setText(cs.getPreferredName());
        selectedProjectPrefName.setVisible(true);
        selectedProjectVersion.setText(cs.getVersion().toString());
        selectedProjectVersion.setVisible(true);
        selectedProjectWorkflowStatus.setText(cs.getWorkflowStatus());
        selectedProjectWorkflowStatus.setVisible(true);
        selectedProjectContextName.setText(cs.getContext().getName());
        selectedProjectContextName.setVisible(true);
        verified = true;

        fireActionEvent(null);
    }});
    
    entryPanel.add(searchText, null);
    entryPanel.add(searchButton, null);
    entryPanel.add(projectPrefName, null);
    entryPanel.add(projectVersion, null);
    entryPanel.add(projectWorkflowStatus, null);
    entryPanel.add(projectContextName, null);
    entryPanel.add(selectedProjectPrefName, null);
    entryPanel.add(selectedProjectVersion, null);
    entryPanel.add(selectedProjectWorkflowStatus, null);
    entryPanel.add(selectedProjectContextName, null);    
    
    this.add(entryPanel, BorderLayout.CENTER);

  }


  private void fireActionEvent(ActionEvent evt) {
    for (ActionListener l : actionListeners) {
      l.actionPerformed(evt);
    }
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    
    frame.getContentPane().add(new RoundtripPanel());
    frame.setVisible(true);
  }

  public void keyPressed(KeyEvent evt) {}
  public void keyTyped(KeyEvent evt) {}

  public boolean isVerified() {
    return verified;
  }
  
  public void setCadsrDialog(CadsrDialog cadsrDialog) {
    this.cadsrDialog = cadsrDialog;
  }

    public void keyReleased(KeyEvent e) {
    }
}
