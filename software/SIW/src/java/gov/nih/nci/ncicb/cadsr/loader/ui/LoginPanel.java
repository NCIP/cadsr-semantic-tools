/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

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

import java.awt.event.*;

import javax.security.auth.callback.*;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

import java.beans.*;

/**
 * Not currently used
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class LoginPanel extends JPanel 
  implements CallbackHandler, ProgressListener,
             ActionListener
{

  private JLabel errorLabel;
  
  private JTextField userField;
  private JPasswordField pwdField;

  private JCheckBox offLineCB;

  final static int GAP = 20;  

  private ProgressPanel progressPanel;
  private boolean isProgress = false;
  private int goal;

  private PropertyChangeListener propListener;

  public LoginPanel() {
    initUI();
  
  }
  public LoginPanel(int progressGoal)
  {
    this.isProgress = true;
    this.goal = progressGoal;
    initUI();
  }

  public void addPropertyChangeListener(PropertyChangeListener l) {
    propListener = l;

    if(propListener != null) {
      propListener.propertyChange(new PropertyChangeEvent(offLineCB, "WORK_OFFLINE", !offLineCB.isSelected(), offLineCB.isSelected()));
    }
  }

  public void newProgressEvent(ProgressEvent evt) {
    progressPanel.newProgressEvent(evt);
  }

  public String getUsername() {
    return userField.getName();
  }

  public void setErrorMessage(String msg) {
    errorLabel.setText(msg);
  }

  public void handle(Callback[] callbacks) 
    throws java.io.IOException, UnsupportedCallbackException {

    for (int i = 0; i < callbacks.length; i++) {
      if (callbacks[i] instanceof NameCallback) {
        String user=userField.getText();
        ((NameCallback)callbacks[i]).setName(user);
        
      } else if (callbacks[i] instanceof PasswordCallback) {
        ((PasswordCallback)callbacks[i]).setPassword(pwdField.getPassword());
      } else {
        throw(new UnsupportedCallbackException(
                callbacks[i], "Callback class not supported"));
      }
    }
  }
  
  private void initUI() {

    this.setLayout(new BorderLayout());

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new SpringLayout());
    
    JLabel userLabel = new JLabel("Username: ", JLabel.TRAILING), 
      pwdLabel = new JLabel("Password: ", JLabel.TRAILING),
      offLineLabel = new JLabel("Work Offline");
      
    errorLabel = new JLabel("", JLabel.TRAILING);
    
    userField = new JTextField(12);
    pwdField = new JPasswordField(12);

    offLineCB = new JCheckBox();
    offLineCB.addActionListener(this);

    // TEMP
    userField.setText("LADINO");
    pwdField.setText("LADINO");

    userLabel.setLabelFor(userField);
    pwdLabel.setLabelFor(pwdField);

    progressPanel = new ProgressPanel(goal);
//     progressPanel.setSize(0, 30);

    if(isProgress) {
      progressPanel.setVisible(true);
      userField.setEnabled(false);
      pwdField.setEnabled(false);
    } else {
      progressPanel.setVisible(false);
    }
    
    // System class loader will not find resources in jar files loaded by JNLP
    ImageIcon icon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("security_lock.jpg"));
    
    mainPanel.add(new JLabel(icon));
    mainPanel.add(errorLabel);
    mainPanel.add(userLabel);
    mainPanel.add(userField);
    mainPanel.add(pwdLabel);
    mainPanel.add(pwdField);
    mainPanel.add(offLineLabel);
    mainPanel.add(offLineCB);

    SpringUtilities.makeCompactGrid(mainPanel,
                                    4, 2,
                                    GAP, GAP, //init x,y
                                    GAP, GAP/2);//xpad, ypad

    this.add(mainPanel, BorderLayout.CENTER);
    this.add(progressPanel, BorderLayout.SOUTH);

  }

  public void actionPerformed(ActionEvent evt) {
    if(offLineCB.isSelected()) {
      userField.setEnabled(false);
      pwdField.setEnabled(false);
    } else {
      userField.setEnabled(true);
      pwdField.setEnabled(true);
    }
    
    if(propListener != null) {
      propListener.propertyChange(new PropertyChangeEvent(offLineCB, "WORK_OFFLINE", !offLineCB.isSelected(), offLineCB.isSelected()));
    }
    
  }

}
