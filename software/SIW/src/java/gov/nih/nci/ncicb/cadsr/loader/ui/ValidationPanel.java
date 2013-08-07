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

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

import java.io.File;

import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;
import gov.nih.nci.ncicb.cadsr.loader.event.*;

public class ValidationPanel extends JPanel {

  private JCheckBox validateCheckBox;
  private JPanel _this = this;
  
  public ValidationPanel()
  {
    initUI();
  }

  public boolean getSelection() {
    return validateCheckBox.isSelected();
  }

  private void initUI() {

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    
    infoPanel.setBackground(Color.WHITE);
    infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    infoPanel.add(new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("xmi.gif"))), FlowLayout.LEFT);
//     infoPanel.add(new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("validated.jpg"))));

    JLabel textLabel = new JLabel("<html>You may choose to run Validation now, <br> or manually run validation later</html>");

    infoPanel.add(textLabel);

    

    validateCheckBox = new JCheckBox("Yes, Please run Validation now");
    validateCheckBox.setSelected(true);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(0, 1));
    buttonPanel.add
      (new JLabel("<html>Validation will help you identify the items that have missing concepts <br> If this is the first you will be tagging the xmi document,<br> you may want to leave this checked off</html>"));
                    
    buttonPanel.add(validateCheckBox);

    this.setLayout(new BorderLayout());
    this.add(infoPanel, BorderLayout.NORTH);
    this.add(buttonPanel, BorderLayout.CENTER);

  }

}