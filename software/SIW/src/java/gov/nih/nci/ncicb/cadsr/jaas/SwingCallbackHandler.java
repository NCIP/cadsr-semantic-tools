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
package gov.nih.nci.ncicb.cadsr.jaas;

import java.io.*;

import javax.security.auth.callback.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.Rectangle;


/**
 * Simple CallbackHandler that queries the standard input. This is appropriate for console mode only.
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class SwingCallbackHandler 
  extends JDialog
  implements CallbackHandler, ActionListener {

  private JTextField usernameField = new JTextField();
  private JLabel usernameLabel = new JLabel();
  private JLabel passwordLabel = new JLabel();
  private JPasswordField passwordField = new JPasswordField();
  private JButton okButton = new JButton();
  private JButton cancelButton = new JButton();

  private JDialog _this = this;
  
  public SwingCallbackHandler()
  {
    super((Frame)null, true);
    try
      {
        jbInit();
        this.setVisible(true);
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }
  
  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(null);
    this.setSize(new Dimension(299, 201));
    usernameField.setBounds(new Rectangle(130, 35, 135, 25));
    usernameLabel.setText("Username");
    usernameLabel.setBounds(new Rectangle(25, 35, 140, 25));
    passwordLabel.setText("Password");
    passwordLabel.setBounds(new Rectangle(25, 80, 150, 30));
    passwordField.setBounds(new Rectangle(130, 80, 135, 30));
    okButton.setText("OK");
    okButton.setBounds(new Rectangle(135, 135, 55, 20));
    okButton.setActionCommand("OK");
    okButton.addActionListener(this);
    cancelButton.setText("Cancel");
    cancelButton.setBounds(new Rectangle(200, 135, 75, 20));
    cancelButton.setActionCommand("Cancel");
    cancelButton.addActionListener(this);
    this.getContentPane().add(cancelButton, null);
    this.getContentPane().add(okButton, null);
    this.getContentPane().add(passwordField, null);
    this.getContentPane().add(passwordLabel, null);
    this.getContentPane().add(usernameLabel, null);
    this.getContentPane().add(usernameField, null);
    
    this.getRootPane().setDefaultButton(okButton);
  }

  public void handle(Callback[] callbacks) 
    throws java.io.IOException, UnsupportedCallbackException {

    for (int i = 0; i < callbacks.length; i++) {
      if (callbacks[i] instanceof NameCallback) {
        ((NameCallback)callbacks[i]).setName(usernameField.getText());
      } else if (callbacks[i] instanceof PasswordCallback) {
        ((PasswordCallback)callbacks[i]).setPassword(passwordField.getPassword());
      } else {
        throw(new UnsupportedCallbackException(
                callbacks[i], "Callback class not supported"));
      }
    }
  }

  public void actionPerformed(ActionEvent evt) {
    if(evt.getActionCommand().equals("OK")) {
      _this.setVisible(false);
    } else if(evt.getActionCommand().equals("Cancel")) {
      System.exit(0);
    }

  }

}

