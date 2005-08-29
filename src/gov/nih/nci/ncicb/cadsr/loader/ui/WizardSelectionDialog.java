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

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class WizardSelectionDialog extends JDialog 
{

  public static final int SELECTION_NEW = 1;
  public static final int SELECTION_CONTINUE = 2;
  public static final int SELECTION_CANCEL = -1;

  private int selection = 0;

  private JButton newButton, continueButton;
  private JLabel newLabel, continueLabel;

  private JDialog _this = this;

  public WizardSelectionDialog()
  {
    super((JFrame)null, "Wizard >> Step 1");
    initUI();
  }

  private void initUI() {
    this.setSize(300, 200);

    newButton = new JButton(null, new ImageIcon(this.getClass().getResource("/new-work.gif")));

    continueButton = new JButton(null, new ImageIcon(this.getClass().getResource("/continue-work.gif")));

    newLabel = new JLabel("Start New Work");
    continueLabel = new JLabel("Continue Saved Work");

    Container contentPane = this.getContentPane();
    contentPane.setLayout(new GridLayout(2, 2));
    contentPane.add(newButton, null);
    contentPane.add(newLabel, null);
    contentPane.add(continueButton, null);
    contentPane.add(continueLabel, null);

    newButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          selection = SELECTION_NEW;
          _this.dispose();
        }
      });

    continueButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          selection = SELECTION_CONTINUE;
          _this.dispose();
        }
      });

    this.addWindowListener(new WindowAdapter()
      {
        public void windowClosing(WindowEvent e)
        {
          selection = SELECTION_CANCEL;
        }
      }
      );
    

  }

  public int getSelection() {
    return selection;
  }
  
}