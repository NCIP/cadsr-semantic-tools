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
import gov.nih.nci.ncicb.cadsr.loader.ui.event.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.event.*;

import java.util.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * Dialog allowing to search for a node.
 * 
 * @author Anwar Ahmad
 */
public class SearchDialog extends JDialog implements ActionListener, KeyListener 
{
  private JTextField searchField = new JTextField(10);
  private JButton findButton = new JButton("Find Next");
  private JCheckBox searchFromBottom = new JCheckBox("Search From Bottom");
  private JCheckBox searchByLongName = new JCheckBox("Search By Concept Name");
  private List<SearchListener> searchListeners = new ArrayList();
  private static final String FINDNEXT = "FINDNEXT";
  
  public SearchDialog(JFrame owner)
  {
    super(owner, "Find");
    this.getContentPane().setLayout(new FlowLayout());
    this.getContentPane().add(searchField);
    this.getContentPane().add(findButton);
    this.getContentPane().add(searchFromBottom);
    this.getContentPane().add(searchByLongName);
    this.setSize(200,150);
    findButton.setActionCommand(FINDNEXT);    
    
    findButton.addActionListener(this);
    findButton.addKeyListener(this);
    searchField.addKeyListener(this);
  }
  
  public void addSearchListener(SearchListener listener) 
  {
    searchListeners.add(listener);
  }
  
  public void fireSearchEvent(SearchEvent event) 
  {
    for(SearchListener l : searchListeners)
      l.search(event);
  }
  
  public void actionPerformed(ActionEvent event) 
  {
    JButton button = (JButton)event.getSource();
    if(button.getActionCommand().equals(FINDNEXT)) 
    {
      String s = searchField.getText();
      SearchEvent searchEvent = new SearchEvent(s, searchFromBottom.isSelected(),
        searchByLongName.isSelected(), false);
      fireSearchEvent(searchEvent);
      //this.setVisible(false);
    }
  }
  
  public void keyPressed(KeyEvent evt) {
    if(evt.getKeyCode() == KeyEvent.VK_ENTER)
      findButton.doClick();
  }

  public void keyTyped(KeyEvent evt) {
    if(evt.getKeyCode() == KeyEvent.VK_ENTER)
      findButton.doClick();
  }

  public void keyReleased(KeyEvent evt) {
  }
  
}