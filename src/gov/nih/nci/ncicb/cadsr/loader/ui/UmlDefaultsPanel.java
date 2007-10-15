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

import gov.nih.nci.ncicb.cadsr.loader.util.DatatypeMapping;
import gov.nih.nci.ncicb.cadsr.loader.util.DatatypeMappingXMLUtil;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableColumn;

import java.util.HashMap;
import java.util.Map;

import gov.nih.nci.ncicb.cadsr.domain.LoaderDefault;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;

import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

public class UmlDefaultsPanel extends JDialog implements ActionListener
{

  private UmlDefaultsPanel _this = this;
  private JButton applyButton, revertButton;
  private String OK = "OK", CANCEL = "CANCEL";
  
  private JTextField projectNameField = new JTextField(20),
    projectVersionField = new JTextField(20),
    contextNameField = new JTextField(20),
    versionField = new JTextField(20),
    workflowField = new JTextField(20),
    longNameField = new JTextField(20),
    conceptualDomainField = new JTextField(20),
    cdContextNameField = new JTextField(20),
    packageFilterField = new JTextField(20);
    
    private JTextArea descriptionField = new JTextArea();
    final private DatatypePanel dataPanel = new DatatypePanel();

  private String rowNames[] = 
  {
    "Project Name",
    "Project Version",
    "Context Name",
    "Version",
    "Workflow Status",
    "Project Long Name",
    "Project Description",
    "Concepual Domain",
    "CD Context Name",
    "Package Filter"
  };

  public UmlDefaultsPanel(java.awt.Frame parent)
  {
    super(parent, "UML Loader Defaults", true);
    this.setResizable(false);
    initUI();
  }

  private void initUI() 
  {
    
    
    JPanel mainPanel = new JPanel();
    //dataPanel = new DatatypePanel();
    mainPanel.setLayout(new GridBagLayout());
  
    JTabbedPane tabPane = new JTabbedPane();
    tabPane.addTab("Defaults", mainPanel);
    //tabPane.addTab("DataTypes", dataPanel);
    
    descriptionField.setLineWrap(true);
    descriptionField.setWrapStyleWord(true);
    
    //set ScrollPane for Text Area
    JScrollPane descriptionScrollPane = new JScrollPane(descriptionField);
    descriptionScrollPane
      .setVerticalScrollBarPolicy
      (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    descriptionScrollPane.setPreferredSize(new Dimension(200, 100));
  
    //insert default values
    UMLDefaults defaults = UMLDefaults.getInstance();
    projectNameField.setText(defaults.getProjectCs().getPreferredName());
    projectVersionField.setText(defaults.getProjectCs().getVersion().toString());
    contextNameField.setText(defaults.getProjectCs().getContext().getName());
    versionField.setText(defaults.getVersion().toString());
    workflowField.setText(defaults.getWorkflowStatus());
    longNameField.setText(defaults.getProjectCs().getLongName());
    descriptionField.setText(defaults.getProjectCs().getPreferredDefinition());
    conceptualDomainField.setText(defaults.getConceptualDomain().getPreferredName());
    cdContextNameField.setText(defaults.getConceptualDomain().getContext().getName());
    packageFilterField.setText(defaults.getPackageFilterString());
    
    UIUtil.insertInBag(mainPanel, new JLabel(rowNames[0]), 0, 0);
    UIUtil.insertInBag(mainPanel, new JLabel(rowNames[1]), 0, 1);
    UIUtil.insertInBag(mainPanel, new JLabel(rowNames[2]), 0, 2);
    UIUtil.insertInBag(mainPanel, new JLabel(rowNames[3]), 0, 3);
    UIUtil.insertInBag(mainPanel, new JLabel(rowNames[4]), 0, 4);
    UIUtil.insertInBag(mainPanel, new JLabel(rowNames[5]), 0, 5);
    UIUtil.insertInBag(mainPanel, new JLabel(rowNames[6]), 0, 6);
    UIUtil.insertInBag(mainPanel, new JLabel(rowNames[7]), 0, 7);
    UIUtil.insertInBag(mainPanel, new JLabel(rowNames[8]), 0, 8);
    UIUtil.insertInBag(mainPanel, new JLabel(rowNames[9]), 0, 9);
    
    UIUtil.insertInBag(mainPanel, projectNameField, 1, 0);
    UIUtil.insertInBag(mainPanel, projectVersionField, 1, 1);
    UIUtil.insertInBag(mainPanel, contextNameField, 1, 2);
    UIUtil.insertInBag(mainPanel, versionField, 1, 3);
    UIUtil.insertInBag(mainPanel, workflowField, 1, 4);
    UIUtil.insertInBag(mainPanel, longNameField, 1, 5);
    UIUtil.insertInBag(mainPanel, descriptionScrollPane, 1, 6, 2, 1);
    UIUtil.insertInBag(mainPanel, conceptualDomainField, 1, 7);
    UIUtil.insertInBag(mainPanel, cdContextNameField, 1, 8);
    UIUtil.insertInBag(mainPanel, packageFilterField, 1, 9);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    
    applyButton = new JButton("OK");
    applyButton.setActionCommand(OK);
    revertButton = new JButton("Cancel");
    revertButton.setActionCommand(CANCEL);
    
    buttonPanel.add(applyButton);
    buttonPanel.add(revertButton);
    
    applyButton.addActionListener(this);
    revertButton.addActionListener(this);

    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(tabPane, BorderLayout.CENTER);
    this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    this.setSize(450, 500);

    UIUtil.putToCenter(this);

  }
 
  private void fireDefaultsChanged() {
    LoaderDefault loaderDefault = DomainObjectFactory.newLoaderDefault();
    
    loaderDefault.setProjectName(projectNameField.getText());
    loaderDefault.setProjectVersion(new Float(projectVersionField.getText()));
    loaderDefault.setContextName(contextNameField.getText());
    loaderDefault.setVersion(new Float(versionField.getText()));
    loaderDefault.setWorkflowStatus(workflowField.getText());
    loaderDefault.setProjectLongName(longNameField.getText());
    loaderDefault.setProjectDescription(descriptionField.getText());
    loaderDefault.setCdName(conceptualDomainField.getText());
    loaderDefault.setCdContextName(cdContextNameField.getText());
    
    loaderDefault.setPackageFilter(packageFilterField.getText());

    UMLDefaults.getInstance().updateDefaults(loaderDefault);

  }
 
  public void actionPerformed(ActionEvent event) 
  {
    JButton button = (JButton)event.getSource();
    if(button.getActionCommand().equals(OK)) {
      fireDefaultsChanged();
      DatatypeMapping.writeUserMapping(dataPanel.getUserDatatypes());
    }
    _this.dispose();
  }
  
}