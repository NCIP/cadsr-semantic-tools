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
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.util.BeansAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.RunMode;
import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Dialog displaying all configurable User Preferences.
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class PreferenceDialog extends JDialog implements ActionListener
{
  private JCheckBox associationBox = new JCheckBox("View Associations in Class Tree");
  private JCheckBox umlDescriptionBox = new JCheckBox("Display UML Description Last");
  private JCheckBox evsAutoSearchBox = new JCheckBox("Automatically Search EVS on EVS link");
  private JButton apply = new JButton("Apply");
  private JButton cancel = new JButton("Cancel");
  private JButton ok = new JButton("OK");
  private static final String APPLY = "Apply";
  private static final String CANCEL = "Cancel";
  private static final String OK = "OK";
  
  public PreferenceDialog(JFrame owner)
  {
    super(owner, "Preferences");
    JPanel southPanel = new JPanel();
    southPanel.add(ok);
    southPanel.add(cancel);
    southPanel.add(apply);
    
    JPanel centerPanel = new JPanel();
    centerPanel.add(associationBox); 
    centerPanel.add(umlDescriptionBox);
    centerPanel.add(evsAutoSearchBox);

    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(centerPanel);
    this.getContentPane().add(southPanel,BorderLayout.SOUTH);
    this.setSize(300,150);
    
    apply.setActionCommand(APPLY);
    cancel.setActionCommand(CANCEL);
    ok.setActionCommand(OK);
    
    apply.addActionListener(this);
    cancel.addActionListener(this);
    ok.addActionListener(this);
    
    UserSelections selections = UserSelections.getInstance();
    RunMode runMode = (RunMode)(selections.getProperty("MODE"));
    
    if(runMode.equals(RunMode.Curator)) 
      associationBox.setVisible(false);
      
    UserPreferences prefs = UserPreferences.getInstance();
      
    if(prefs.getViewAssociationType().equalsIgnoreCase("true"))
      associationBox.setSelected(true);
    else
      associationBox.setSelected(false);
      
    if(prefs.getUmlDescriptionOrder().equals("first"))
      umlDescriptionBox.setSelected(false);
    else
      umlDescriptionBox.setSelected(true);
 
    evsAutoSearchBox.setSelected(prefs.getEvsAutoSearch());
  }
  
  public void actionPerformed(ActionEvent event) 
  {
    JButton button = (JButton)event.getSource();
    
    if(button.getActionCommand().equals(OK)) {
      UserPreferences prefs = UserPreferences.getInstance();
      if(associationBox.isSelected())
        prefs.setViewAssociationType("true");
      else
        prefs.setViewAssociationType("false");
      
      if(umlDescriptionBox.isSelected())
        prefs.setUmlDescriptionOrder("last");
      else
        prefs.setUmlDescriptionOrder("first");

      prefs.setEvsAutoSeatch(evsAutoSearchBox.isSelected());
        
      this.dispose();
    }
    
    if(button.getActionCommand().equals(APPLY)) {
      UserPreferences prefs = UserPreferences.getInstance();
      if(associationBox.isSelected())
        prefs.setViewAssociationType("true");
      else
        prefs.setViewAssociationType("false");
      
      if(umlDescriptionBox.isSelected())
        prefs.setUmlDescriptionOrder("last");
      else
        prefs.setUmlDescriptionOrder("first");  
      
    }
    
    if(button.getActionCommand().equals(CANCEL))
      this.dispose();

  }
}