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

import gov.nih.nci.ncicb.cadsr.loader.ui.util.TreeUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeNode;
import javax.swing.tree.DefaultMutableTreeNode;

import gov.nih.nci.ncicb.cadsr.loader.validator.*;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;

import java.util.*;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * The error viewer. 
 *
 * @author Anwar Ahmad
 */
public class ErrorPanel extends JPanel 
 implements MouseListener {

  private JTree tree;
  private Set<UMLNode> displaySet = new HashSet<UMLNode>();
  private JPopupMenu popup;

  public ErrorPanel(UMLNode rootNode) {
    initUI(rootNode);
//     buildPopupMenu();
  }

  public void update(UMLNode rootNode) {
    this.removeAll();
    initUI(rootNode);

    this.updateUI();
  }


  private void initUI(UMLNode rootNode) {
    displaySet.clear();
    firstRun(rootNode);

    DefaultMutableTreeNode node = buildTree(rootNode);

    //create tree and make root not visible
    tree = new JTree(node);
    tree.setRootVisible(false);
    tree.setShowsRootHandles(true);

    //Traverse Tree expanding all nodes
    TreeUtil.expandAll(tree, node);
    
    tree.setCellRenderer(new UMLTreeCellRenderer());
    tree.addMouseListener(this);

    this.setLayout(new BorderLayout());

    JScrollPane scrollPane = new JScrollPane(tree);
    this.setPreferredSize(new Dimension(450, 110));
    this.add(scrollPane, BorderLayout.CENTER);
  }

  public void mousePressed(MouseEvent e) {
    showPopup(e);
  }
  public void mouseExited(MouseEvent e) {
  }
  public void mouseClicked(MouseEvent e) {
  }
  public void mouseEntered(MouseEvent e) {
  }
  public void mouseReleased(MouseEvent e) {
    showPopup(e);
  }
  private void showPopup(MouseEvent e) {
    if (e.isPopupTrigger()) {
      popup.show(e.getComponent(),
                 e.getX(), e.getY());
    }
  }

  private void buildPopupMenu() {
    popup = new JPopupMenu();
    JMenuItem menuItem = new JMenuItem("Export Errors");
    popup.add(menuItem);
  }

  private void firstRun(UMLNode node) {
    Set<UMLNode> children = node.getChildren();
    Set<ValidationNode> valNodes = node.getValidationNodes();
//     displaySet = new HashSet<UMLNode>();

    for(ValidationNode valNode : valNodes)
      navTree(valNode);

    for(UMLNode child : children) {
      firstRun(child);
    }
  }

  private void navTree(UMLNode node) {
    UMLNode pNode = node.getParent();
    if(pNode != null) {
      navTree(pNode);
    }
    displaySet.add(node);
  }

  private DefaultMutableTreeNode buildTree(UMLNode rootNode) {
    DefaultMutableTreeNode node = new DefaultMutableTreeNode(rootNode);

    return doNode(node);
  }

  private DefaultMutableTreeNode doNode(DefaultMutableTreeNode node) {
    UMLNode umlNode = (UMLNode)node.getUserObject();

    Set<UMLNode> children = umlNode.getChildren();
    Set<ValidationNode> valNodes = umlNode.getValidationNodes();

    for(ValidationNode valNode : valNodes) {
      DefaultMutableTreeNode newNode = 
        new DefaultMutableTreeNode(valNode);

      	node.add(newNode);
    }
    for(UMLNode child : children) {
      DefaultMutableTreeNode newNode = 
        new DefaultMutableTreeNode(child);

      if(displaySet.contains(child))
      	node.add(newNode);

      doNode(newNode);
    }

    return node;
    
  }



}

