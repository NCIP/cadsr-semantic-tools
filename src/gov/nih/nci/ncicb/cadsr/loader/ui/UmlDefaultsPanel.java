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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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

  private JTable table = null;
  private TableModel dataModel;
  private JButton applyButton, revertButton;

  private Map<String, String> values = new HashMap<String, String>();

  private String OK = "OK", CANCEL = "CANCEL";

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
    initValues();
    initUI();
  }

  private void initValues() {
    UMLDefaults defaults = UMLDefaults.getInstance();
    values.put(rowNames[0], defaults.getProjectCs().getPreferredName());
    values.put(rowNames[1], defaults.getProjectCs().getVersion().toString());
    values.put(rowNames[2], defaults.getProjectCs().getContext().getName());
    values.put(rowNames[3], "1.0");
    values.put(rowNames[4], defaults.getWorkflowStatus());
    values.put(rowNames[5], defaults.getProjectCs().getLongName());
    values.put(rowNames[6], defaults.getProjectCs().getPreferredDefinition());
    values.put(rowNames[7], defaults.getConceptualDomain().getPreferredName());
    values.put(rowNames[8], defaults.getProjectCs().getPreferredDefinition());
    values.put(rowNames[9], defaults.getPackageFilterString());
  }


  private void initUI() 
  {
    dataModel = new AbstractTableModel() {
        public int getColumnCount() { return 2; }
        public int getRowCount() { return rowNames.length;}
        public Object getValueAt(int row, int col) { 
          if(col == 0)
            return rowNames[row];
          else 
            return values.get(rowNames[row]);
        }
        public boolean isCellEditable(int row, int col) {
          if(col != 0)
            return true;
          else
            return false;
        }
        public void setValueAt(Object value, int row, int col) 
        {
          if(col == 1) {
            values.put(rowNames[row], value.toString());
            System.out.println("updated");
          }
          fireTableCellUpdated(row,col);          
        }
      };



    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    JTable table = new JTable(dataModel);
    mainPanel.add(table, BorderLayout.CENTER);

    TableColumn col = table.getColumnModel().getColumn(1);
    col.setPreferredWidth(20);

    
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

    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(mainPanel, BorderLayout.CENTER);
    this.setSize(300, 225);

    UIUtil.putToCenter(this);

  }
 
  private void fireDefaultsChanged() {
    LoaderDefault loaderDefault = DomainObjectFactory.newLoaderDefault();
    
    loaderDefault.setProjectName((String)dataModel.getValueAt(0, 1));
    loaderDefault.setProjectVersion(new Float((String)dataModel.getValueAt(1, 1)));
    loaderDefault.setContextName((String)dataModel.getValueAt(2, 1));
    loaderDefault.setVersion(new Float((String)dataModel.getValueAt(3, 1)));
    loaderDefault.setWorkflowStatus((String)dataModel.getValueAt(4, 1));
    loaderDefault.setProjectLongName((String)dataModel.getValueAt(5, 1));
    loaderDefault.setProjectDescription((String)dataModel.getValueAt(6, 1));
    loaderDefault.setCdName((String)dataModel.getValueAt(7, 1));
    loaderDefault.setCdContextName((String)dataModel.getValueAt(8, 1));
    
    loaderDefault.setPackageFilter((String)dataModel.getValueAt(9, 1));

    UMLDefaults.getInstance().updateDefaults(loaderDefault);

  }
 
  public void actionPerformed(ActionEvent event) 
  {
    JButton button = (JButton)event.getSource();
    if(button.getActionCommand().equals(OK)) {
      fireDefaultsChanged();
    }
    _this.dispose();
  }
  
  
}