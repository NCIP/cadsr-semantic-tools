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

import gov.nih.nci.ncicb.cadsr.loader.event.*;
import gov.nih.nci.ncicb.cadsr.loader.util.*;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.*;

public class ProgressPanel extends JPanel 
    implements ProgressListener
{

  private JProgressBar progressBar;
  private JLabel msgLabel;

  public ProgressPanel(int maximum)
  {
    initUI(maximum);
  }

  public void newProgressEvent(ProgressEvent evt) {
    if(evt.getGoal() != 0) {
      progressBar.setMaximum(evt.getGoal());
      if(evt.getGoal() == evt.getStatus() || evt.getGoal() > 0)
        progressBar.setIndeterminate(false);
        
    }
    progressBar.setValue(evt.getStatus());


    if(!StringUtil.isEmpty(evt.getMessage())) {
      progressBar.setString(evt.getMessage());
      msgLabel.setText(evt.getMessage());
    }

  }

  private void initUI(int maximum) {
    if(maximum > 0)
      progressBar = new JProgressBar(0, maximum);
    else {
      progressBar = new JProgressBar();
      progressBar.setIndeterminate(true);
    }
      
    this.setSize(300, 100);

    msgLabel = new JLabel("");

    this.setLayout(new BorderLayout());
    this.add(progressBar, BorderLayout.CENTER);
    this.add(msgLabel, BorderLayout.SOUTH);

  }


  public static void main(String[] args) {
    ProgressFrame progressFrame = new ProgressFrame(-1);

    ProgressEvent evt = new ProgressEvent();
    evt.setMessage("Loading Defaults");
    progressFrame.newProgressEvent(evt);
    
  }
}