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

import java.awt.GridLayout;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;

import java.awt.event.*;

import javax.security.auth.callback.*;

/**
 * Callback handler for Swing based apps
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class SwingCallbackHandler extends JDialog
  implements CallbackHandler {

  private boolean done = false;

  private JTextField userField;
  private JPasswordField pwdField;

  private JButton okButton, cancelButton;
  
  public SwingCallbackHandler() {
    super((JFrame)null, "Login Dialog");
    initUI();
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

  public boolean isDone() {
    return done;
  }

  private void initUI() {
    this.setSize(250, 120);

    JLabel userLabel = new JLabel("Username: "), 
      pwdLabel = new JLabel("Password");
    
    userField = new JTextField(12);
    pwdField = new JPasswordField();

    okButton = new JButton("Login");
    cancelButton = new JButton("Quit");

    KeyListener enterListener = new KeyAdapter() {
        public void keyReleased(KeyEvent evt) {
          if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JButton button = (JButton)evt.getComponent();
            button.doClick();
          }
        }
      };
    okButton.addKeyListener(enterListener);
    cancelButton.addKeyListener(enterListener);

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          done = true;
          dispose();
          if(evt.getSource() == cancelButton)
            System.exit(0);
        }
      };
    
        
    okButton.addActionListener(actionListener);
    cancelButton.addActionListener(actionListener);

    Container contentPane = this.getContentPane();
    contentPane.setLayout(new BorderLayout());

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    buttonPanel.add(okButton, null);
    buttonPanel.add(cancelButton);

    JPanel entryPanel = new JPanel();
    entryPanel.setLayout(new GridLayout(2, 2));
    entryPanel.add(userLabel);
    entryPanel.add(userField);
    entryPanel.add(pwdLabel);
    entryPanel.add(pwdField);

    contentPane.add(buttonPanel, BorderLayout.SOUTH);
    contentPane.add(entryPanel, BorderLayout.CENTER);
    

  }

}
