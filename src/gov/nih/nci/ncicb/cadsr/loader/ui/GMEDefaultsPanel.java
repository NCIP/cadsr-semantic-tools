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
import gov.nih.nci.ncicb.cadsr.loader.util.*;
import gov.nih.nci.ncicb.cadsr.loader.ext.*;

import gov.nih.nci.ncicb.cadsr.domain.Context;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.FilterPackage;

import java.awt.Dimension;

import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

import org.apache.log4j.Logger;


/**
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class GMEDefaultsPanel extends JPanel implements KeyListener, CadsrModuleListener
{

  private JPanel _this = this;

  private JTextField projectNameField = new JTextField();
  private JTextField projectVersionField = new JTextField(5);
  private CustomCombo contextComboBox = new CustomCombo();
  private CustomCombo packageComboBox = new CustomCombo();


  private List<ActionListener> actionListeners = new ArrayList<ActionListener>();

  private CadsrModule cadsrModule;

  private static Logger logger = Logger.getLogger(GMEDefaultsPanel.class.getName());

  private Collection<Context> contexts;
  
  private List<FilterPackage> packages = new ArrayList<FilterPackage>();

  public GMEDefaultsPanel() {
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

    JLabel infoLabel = new JLabel("<html>Please select a project and context<br> Version must be a number</html>");
    infoPanel.add(infoLabel);
    
    this.add(infoPanel, BorderLayout.NORTH);

    JPanel entryPanel = new JPanel();
    entryPanel.setLayout(null);

    projectNameField.setText("");
    JLabel projectNameLabel = new JLabel("Project Name");
    projectNameLabel.setBounds(new Rectangle(30, 80, 135, 20));
    projectNameField.setBounds(new Rectangle(180, 80, 175, 20));

    JLabel projectVersionLabel = new JLabel("Project Version");
    projectVersionLabel.setBounds(new Rectangle(30, 125, 135, 20));
    projectVersionField.setBounds(new Rectangle(180, 125, 175, 20));

    JLabel contextLabel = new JLabel("Context");
    contextLabel.setBounds(new Rectangle(30, 165, 135, 20));
    contextComboBox.setBounds(new Rectangle(180, 165, 175, 20));

    
    JLabel packageLabel = new JLabel("Select Package");
    packageLabel.setBounds(new Rectangle(30, 210, 135, 20));
    packageComboBox.setBounds(new Rectangle(180, 210, 175, 20));

    entryPanel.add(projectNameLabel, null);
    entryPanel.add(projectNameField, null);
    entryPanel.add(projectVersionLabel, null);
    entryPanel.add(projectVersionField, null);
    entryPanel.add(contextLabel, null);
    entryPanel.add(contextComboBox, null);
    entryPanel.add(packageLabel, null);
    entryPanel.add(packageComboBox, null);
    
    this.add(entryPanel, BorderLayout.CENTER);

    projectVersionField.addKeyListener(this);
    projectNameField.addKeyListener(this);
    
  }

  void init() {
    contexts = cadsrModule.getAllContexts();

      int contentCount = contextComboBox.getItemCount();
      int i=0;
      while(i<=contentCount && contentCount != 0){
          contextComboBox.removeItemAt(i);
          contentCount = contextComboBox.getItemCount();
      }

    for(Context _con : contexts) {
      contextComboBox.addItem(_con.getName());
    }

    contextComboBox.setSelectedItem(PropertyAccessor.getProperty("gme.generate.default.context"));
  }

  public String getProjectName() {
    return projectNameField.getText();
  }
  public String getProjectVersion() {
    return projectVersionField.getText();
  }

  public Context getContext() {
    String selected = (String)contextComboBox.getSelectedItem();
    for(Context _con : contexts) {
      if(_con.getName().equals(selected))
        return _con;
    }
    return null;
  }
  
  public FilterPackage getPackage(){
    String selected =  (String)packageComboBox.getSelectedItem();
    for(FilterPackage _pkg : packages){
        if(_pkg.getName().equals(selected))
            return _pkg;
    }
    
    return null;
  }

  private void fireActionEvent(ActionEvent evt) {
    for (ActionListener l : actionListeners) {
      l.actionPerformed(evt);
    }
  }


  public void keyReleased(KeyEvent evt) {
    fireActionEvent(null);
  }
  public void keyPressed(KeyEvent evt) {}
  public void keyTyped(KeyEvent evt) {}

  public boolean isVerified() {
    boolean enable = !StringUtil.isEmpty(getProjectName());
    try {
      enable = (new Float(getProjectVersion()) > 0);
    } catch (NumberFormatException e){
      enable = false;
    } // end of try-catch
    return enable;
  }
  
  public void setCadsrModule(CadsrModule cadsrModule) {
    this.cadsrModule = cadsrModule;
  }
  
  public void initPackages() {
      packages = ElementsLists.getInstance().getElements(new FilterPackage(""));
      
      //packageComboBox.removeAll();
      int pkgCount = packageComboBox.getItemCount();
      int i=0;
      while(i<=pkgCount && pkgCount != 0){
          packageComboBox.removeItemAt(i);
          pkgCount = packageComboBox.getItemCount();
      }
          
      for(FilterPackage pkg : packages) 
          packageComboBox.addItem(pkg.getName());

      if(packageComboBox.getItemCount() > 0)
        for(int j=0; j<packageComboBox.getItemCount(); j++)
            if(packageComboBox.getItemAt(j).toString().trim().equalsIgnoreCase("Logical View.Logical Model")){
                packageComboBox.setSelectedIndex(j);
                break;
            }
  }
  
  public static void main(String[] args) 
  {
    JFrame frame = new JFrame();
    frame.setSize(500, 500);
    
    frame.add(new GMEDefaultsPanel());
    
    frame.setVisible(true);
  }
}
class CustomCombo extends JComboBox{
    public CustomCombo(){
        super();
        setUI(new CustomComboUI());
    }//end of default constructor
    
    public class CustomComboUI extends BasicComboBoxUI{
        protected ComboPopup createPopup(){
            BasicComboPopup popup = new BasicComboPopup(comboBox){
                protected JScrollPane createScroller() {
                        return new JScrollPane( list, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
              }//end of method createScroller
            };
            return popup;
        }//end of method createPopup
    }//end of inner class myComboUI
}
