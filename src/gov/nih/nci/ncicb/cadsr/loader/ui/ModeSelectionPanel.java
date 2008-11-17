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

import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import java.awt.*;
import java.awt.event.*;

import java.util.Date;

import javax.swing.*;

import gov.nih.nci.ncicb.cadsr.loader.util.*;

import java.util.Calendar;

/**
 * Wizard step to choose run mode
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class ModeSelectionPanel extends JPanel implements MouseListener, KeyListener {

  private JRadioButton unannotatedXmiOption, roundtripOption, annotateOption, reviewOption, curateOption, gmeDefaultsOption, gmeCleanupOption;
  private ButtonGroup group;
  private JPanel _this = this;
  private JPanel infoPanel = null, privateApiPanel = null;
  private UserSelections userSelections = UserSelections.getInstance();
  private UserPreferences prefs = UserPreferences.getInstance();
  private long timePressed;

  private JCheckBox privateApiCb = new JCheckBox("Use Private API", prefs.isUsePrivateApi());

  
  public ModeSelectionPanel()
  {
    initUI();
  }

  public void addActionListener(ActionListener l) {
    unannotatedXmiOption.addActionListener(l);
    roundtripOption.addActionListener(l);
    annotateOption.addActionListener(l);
    curateOption.addActionListener(l);
    reviewOption.addActionListener(l);
    gmeDefaultsOption.addActionListener(l);
    gmeCleanupOption.addActionListener(l);
  }

  public String getSelection() {
    return group.getSelection().getActionCommand();
  }

  public boolean usePrivateApi() {
    return privateApiCb.isSelected();
  }

  private void initUI() {

    this.setLayout(new BorderLayout());
    
    infoPanel = new JPanel();
    infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    infoPanel.setBackground(Color.WHITE);
    infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    JLabel imgLabel = new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("siw-logo3_2.gif")));
    infoPanel.add(imgLabel);
    imgLabel.addMouseListener(new MouseAdapter() {
        long timePressed;
        public void mousePressed(MouseEvent me) {
          timePressed = me.getWhen();
        }
        public void mouseReleased(MouseEvent me) {
          Date dte = new Date(me.getWhen() - timePressed);
          Calendar cal = Calendar.getInstance();
          cal.setTime(dte);
          int timeDiff = cal.get(Calendar.SECOND);
          
          if(timeDiff > 10){
            _this.add(privateApiPanel, BorderLayout.SOUTH);
          }
        }
      });

    JLabel infoLabel = new JLabel("<html>Welcome to the " + PropertyAccessor.getProperty("siw.title") + "</html>");
    infoPanel.add(infoLabel);
    
    this.add(infoPanel, BorderLayout.NORTH);
    
    group = new ButtonGroup();

    unannotatedXmiOption = new JRadioButton("1. " + RunMode.UnannotatedXmi.getTitleName() + " (" + RunMode.UnannotatedXmi.getAuthor() + ")");
    unannotatedXmiOption.setActionCommand(RunMode.UnannotatedXmi.toString());

    roundtripOption = new JRadioButton("2. " + RunMode.Roundtrip.getTitleName() + " (" + RunMode.Roundtrip.getAuthor() + ")");
    roundtripOption.setActionCommand(RunMode.Roundtrip.toString());
    
    annotateOption = new JRadioButton("3. " + RunMode.GenerateReport.getTitleName() + " (" + RunMode.GenerateReport.getAuthor() + ")");
    annotateOption.setActionCommand(RunMode.GenerateReport.toString());
        
    curateOption = new JRadioButton("4. " + RunMode.Curator.getTitleName() + " (" + RunMode.Curator.getAuthor() + ")");
    curateOption.setActionCommand(RunMode.Curator.toString());

    reviewOption = new JRadioButton("5. " + RunMode.Reviewer.getTitleName() + " (" + RunMode.Reviewer.getAuthor() + ")");
    reviewOption.setActionCommand(RunMode.Reviewer.toString());

    gmeDefaultsOption = new JRadioButton("6. " + RunMode.GMEDefaults.getTitleName() + " (" + RunMode.GMEDefaults.getAuthor() + ")");
    gmeDefaultsOption.setActionCommand(RunMode.GMEDefaults.toString());

    gmeCleanupOption = new JRadioButton("7. " + RunMode.GMECleanup.getTitleName() + " (" + RunMode.GMECleanup.getAuthor() + ")");
    gmeCleanupOption.setActionCommand(RunMode.GMECleanup.toString());

    // Get the preference setting for mode and set the radio buttons appropriately.
    RunMode mode = RunMode.UnannotatedXmi;
    try {
      mode = RunMode.valueOf(prefs.getModeSelection());
    }
    catch (Exception ex){
      // Ignore any exceptions and let "mode" default.
    }
    
    switch (mode) {
    case AnnotateXMI:
      reviewOption.setSelected(true);
      break;
      
    case Curator:
      curateOption.setSelected(true);
      break;
      
    case Roundtrip:
      reviewOption.setSelected(true);
      break;
      
    case Reviewer:
      reviewOption.setSelected(true);
      break;

    case GMEDefaults:
      gmeDefaultsOption.setSelected(true);
      break;

    case GMECleanup:
      gmeCleanupOption.setSelected(true);
      break;
      
    default:
      unannotatedXmiOption.setSelected(true);
      break;
    }
    
    group.add(unannotatedXmiOption);
    group.add(roundtripOption);
    group.add(annotateOption);
    group.add(curateOption);
    group.add(reviewOption);
    group.add(gmeDefaultsOption);
    group.add(gmeCleanupOption);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(0, 1));

    buttonPanel.add
      (new JLabel("<html>Choose from the following SIW Options:</html>"));

    buttonPanel.add(unannotatedXmiOption);
    buttonPanel.add(roundtripOption);
    buttonPanel.add(annotateOption);
    buttonPanel.add(curateOption);
    buttonPanel.add(reviewOption);
    buttonPanel.add(gmeDefaultsOption);
    buttonPanel.add(gmeCleanupOption);

    this.setLayout(new BorderLayout());
    this.add(infoPanel, BorderLayout.NORTH);
    this.add(buttonPanel, BorderLayout.CENTER);


    Font font = new Font("Serif", Font.PLAIN, 11);

    privateApiCb.setFont(font);
    JLabel privateApiLabel = new JLabel("<html><body>The private API is offered as an alternative to the caCORE public API.<br> It requires that the user be inside the NCI network or use VPN.</body></html>");
    privateApiLabel.setFont(font);

    privateApiPanel = new JPanel();
    privateApiPanel.setLayout(new FlowLayout());
    privateApiPanel.add(privateApiCb);
    privateApiPanel.add(privateApiLabel);

    privateApiCb.setSelected(false);

//     this.add(privateApiPanel, BorderLayout.SOUTH);
    infoPanel.addMouseListener(this);

    unannotatedXmiOption.addKeyListener(this);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Prototype");
    frame.getContentPane().add(new ModeSelectionPanel());
    frame.setVisible(true);
  }

  public void mousePressed(MouseEvent me) {
    timePressed = me.getWhen();
  }
  public void mouseReleased(MouseEvent me) {
    Date dte = new Date(me.getWhen() - timePressed);
    Calendar cal = Calendar.getInstance();
    cal.setTime(dte);
    int timeDiff = cal.get(Calendar.SECOND);
    
    if(timeDiff > 10){
      ExpertsCacheDialog ecDialog = new ExpertsCacheDialog();
      ecDialog.setVisible(true);
      ecDialog.setAlwaysOnTop(true);
    }
  }
  public void mouseClicked(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}


  private long timeTyped = 0;
  private StringBuilder sb = new StringBuilder();
  public void keyTyped(KeyEvent e) {
  }

  public void keyPressed(KeyEvent e) {
  }

  /*
   * to trigger expert mode, click option one, then type 'expert'
   */
  public void keyReleased(KeyEvent e) {
    long diff = e.getWhen() - timeTyped;
    timeTyped = e.getWhen();
    if(sb.length() == 0) {
      sb.append(e.getKeyChar());
    } else if(diff < 1000) {
      sb.append(e.getKeyChar());
      if(sb.toString().equals("expert")) {
        ExpertsCacheDialog ecDialog = new ExpertsCacheDialog();
        ecDialog.setVisible(true);
        ecDialog.setAlwaysOnTop(true);
      }
    } else {
      sb = new StringBuilder(e.getKeyChar());
    }
      
  }
}
