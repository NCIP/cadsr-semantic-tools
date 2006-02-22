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

import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.*;

import gov.nih.nci.ncicb.cadsr.loader.event.ReviewEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ReviewListener;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.TreeUtil;

import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import java.util.*;

/**
 * Panel from where you navigate the UML Elements.
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class NavigationPanel extends JPanel 
  implements ActionListener, MouseListener, ReviewListener, NavigationListener,
  KeyListener, SearchListener, TreeListener
{
  private JTree tree;
  private JPopupMenu popup;
  private JScrollPane scrollPane;
  private UMLNode rootNode = TreeBuilder.getInstance().getRootNode(); 

  private List<ViewChangeListener> viewListeners = new ArrayList();
  private List<NavigationListener> navigationListeners = new ArrayList();


  public NavigationPanel()
  {
    try
    {
      initUI();
      TreeBuilder.getInstance().addTreeListener(this);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  public void addViewChangeListener(ViewChangeListener l) {
    viewListeners.add(l);
  }

  public void addNavigationListener(NavigationListener l) {
    navigationListeners.add(l);
  }

  public void reviewChanged(ReviewEvent event) {
    ReviewableUMLNode node = (ReviewableUMLNode)event.getUserObject();
    node.setReviewed(event.isReviewed());

    tree.repaint();
  }
  
  private void initUI() throws Exception
  {
    DefaultMutableTreeNode top = buildTree();
      
    tree = new JTree(top);
    tree.setRootVisible(false);
    tree.setShowsRootHandles(true);
    tree.addKeyListener(this);
    
    //Traverse Tree expanding all nodes    
    TreeUtil.expandAll(tree, top);

    tree.setCellRenderer(new UMLTreeCellRenderer());
   
    this.setLayout(new BorderLayout());

    scrollPane = new JScrollPane(tree);
    this.add(scrollPane, BorderLayout.CENTER);

    
    buildPopupMenu();
  }

  public void actionPerformed(ActionEvent event) {
    DefaultMutableTreeNode dmtn, node;
    
    TreePath path = tree.getSelectionPath();
    dmtn = (DefaultMutableTreeNode) path.getLastPathComponent();

    if(event.getSource() instanceof JMenuItem) {
      JMenuItem menuItem = (JMenuItem)event.getSource();
      
      ViewChangeEvent evt = new ViewChangeEvent(ViewChangeEvent.VIEW_CONCEPTS);
      evt.setViewObject(dmtn.getUserObject());

      if(menuItem.getActionCommand().equals("OPEN_NEW_TAB"))
        evt.setInNewTab(true);
      else 
        evt.setInNewTab(false);

      fireViewChangeEvent(evt);

    }
  }
  
  public void keyPressed(KeyEvent e) {
    DefaultMutableTreeNode selected = 
      (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
       
    //if the down arrow is pressed then display the next element
    //in the tree in the ViewPanel    
    if(e.getKeyCode() == KeyEvent.VK_DOWN  && selected != null) 
    {
      if (selected.getNextNode() != null) 
      {
        TreePath path =  new TreePath(selected.getNextNode().getPath());
        tree.makeVisible(path);
        newViewEvent(path);
      }
    }
    //if the up arrow is pressed then display the previous element
    //in the tree in the ViewPanel
    if(e.getKeyCode() == KeyEvent.VK_UP && selected != null)
    {
      if (selected.getPreviousNode() != null) 
      {
        TreePath path =  new TreePath(selected.getPreviousNode().getPath());
        tree.makeVisible(path);
        newViewEvent(path);
      }  
    }

  }
  
  public void keyTyped(KeyEvent e) {

  }
  
  public void keyReleased(KeyEvent e) {
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
    doMouseEvent(e);
  }
  
  private void showPopup(MouseEvent e) {
    if (e.isPopupTrigger()) {
      TreePath path = tree.getPathForLocation(e.getX(), e.getY());
      tree.setSelectionPath(path);
      if(path != null) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
        Object o = node.getUserObject();
        if((o instanceof ClassNode)
           || (o instanceof AttributeNode)
           )
          popup.show(e.getComponent(),
                     e.getX(), e.getY());
      }
    }
  }

  private void doMouseEvent(MouseEvent e) {
    if(e.getButton() == MouseEvent.BUTTON1) {
      TreePath path = tree.getPathForLocation(e.getX(), e.getY());
      newViewEvent(path);
    } else if(e.getButton() == MouseEvent.BUTTON2) {
      TreePath path = tree.getPathForLocation(e.getX(), e.getY());
      if(path != null) {
        DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) path.getLastPathComponent();
        ViewChangeEvent evt = new ViewChangeEvent(ViewChangeEvent.VIEW_CONCEPTS);
        evt.setViewObject(dmtn.getUserObject());
        evt.setInNewTab(true);

        fireViewChangeEvent(evt);
      }
    }
   }

  private void newViewEvent(TreePath path)
  {
  	if(path != null) {
  	  DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) path.getLastPathComponent();
  	  
  	  Object o = dmtn.getUserObject();
  	  if((o instanceof ClassNode)
  	     || (o instanceof AttributeNode)
  	     ) {
  	    ViewChangeEvent evt = new ViewChangeEvent(ViewChangeEvent.VIEW_CONCEPTS);
  	    evt.setViewObject(dmtn.getUserObject());
  	    evt.setInNewTab(false);

            fireViewChangeEvent(evt);
  	  } else if(o instanceof AssociationNode) {
  	    ViewChangeEvent evt = new ViewChangeEvent(ViewChangeEvent.VIEW_ASSOCIATION);
  	    evt.setViewObject(dmtn.getUserObject());
  	    evt.setInNewTab(false);

            fireViewChangeEvent(evt);
  	  }
        else if(o instanceof ValueDomainNode) {
        ViewChangeEvent evt = new ViewChangeEvent(ViewChangeEvent.VIEW_VALUE_DOMAIN);
        evt.setViewObject(dmtn.getUserObject());
        evt.setInNewTab(false);
        
        fireViewChangeEvent(evt);
        }
        else if(o instanceof ValueMeaningNode) {
        ViewChangeEvent evt = new ViewChangeEvent(ViewChangeEvent.VIEW_VALUE_MEANING);
        evt.setViewObject(dmtn.getUserObject());
        evt.setInNewTab(false);
        
        fireViewChangeEvent(evt);
        }
    }
  }


  private void buildPopupMenu() {
    JMenuItem newTabItem = new JMenuItem("Open in New Tab");
    JMenuItem openItem = new JMenuItem("Open");

    newTabItem.setActionCommand("OPEN_NEW_TAB");
    openItem.setActionCommand("OPEN");

    popup = new JPopupMenu();

    newTabItem.addActionListener(this);
    openItem.addActionListener(this);

    popup.add(openItem);
    popup.add(newTabItem);
    
    tree.addMouseListener(this);
   
  } 




  private DefaultMutableTreeNode buildTree() {
  
     DefaultMutableTreeNode node = new DefaultMutableTreeNode(rootNode);
          
     return doNode(node, rootNode);
    
  }

  private DefaultMutableTreeNode doNode(DefaultMutableTreeNode node, UMLNode parentNode) {

    Set<UMLNode> children = parentNode.getChildren();
    for(UMLNode child : children) {
      DefaultMutableTreeNode newNode = 
        new DefaultMutableTreeNode(child);

      if(!(child instanceof ValidationNode))
        node.add(newNode);  
      doNode(newNode, child);
    }

    return node;
    
  }
  
  public void treeChange(TreeEvent event) 
  {
    this.remove(scrollPane);
    rootNode = TreeBuilder.getInstance().getRootNode();
          
    try
    {
      initUI();
    }
    catch (Exception e)
    {
      
    }
   
    this.updateUI();
  }
  
  public void navigate(NavigationEvent event) 
  {    
    DefaultMutableTreeNode selected = 
      (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
    
      if(event.getType() == NavigationEvent.NAVIGATE_NEXT
        && selected.getNextNode() != null) 
      {      
        TreePath path =  new TreePath(selected.getNextNode().getPath());
        tree.setSelectionPath(path);
        tree.scrollPathToVisible(path);
        newViewEvent(path);
      }
      else 
        if(event.getType() == NavigationEvent.NAVIGATE_PREVIOUS
          && selected.getPreviousNode() != null) 
        {
            TreePath path = new TreePath(selected.getPreviousNode().getPath());
            tree.setSelectionPath(path);
            tree.scrollPathToVisible(path);
            newViewEvent(path);            
        }
    
  }
  
  public void search(SearchEvent event) 
  {
    DefaultMutableTreeNode selected =
     (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

    TreePath path = null;
    if(event.getSearchFromBeginning())
      selected = null;
    //if(event.getSearchByLongName()) {
    //search the tree in a forward direction from top to bottom
    if(!event.getSearchFromBottom()) {
      
      //if no node is selected then select the first node 
      if(selected == null)
        selected = (DefaultMutableTreeNode)tree.getPathForRow(0).getLastPathComponent();

      selected = selected.getNextNode();
      while(selected != null) {
        AbstractUMLNode n = (AbstractUMLNode) selected.getUserObject();
        if(event.getSearchByLongName()) 
        {
          Concept [] concepts = NodeUtil.getConceptsFromNode(n);
          for(int i=0; i < concepts.length; i++) {
            //System.out.println("Concepts" + i +" "+ concepts[i]);
            if(!event.getSearchString().toLowerCase().contains(concepts[i].getLongName().toLowerCase())) 
            {
              path = new TreePath(selected.getPath());
              tree.setSelectionPath(path);
              tree.scrollPathToVisible(path);
              newViewEvent(path);
              selected = null;
              break;
            }
          }    
        }
        else {
        //if there is a match then select that node in the tree
        if((n.getDisplay().toLowerCase()).contains(event.getSearchString().toLowerCase())) 
        {
          path = new TreePath(selected.getPath());
          tree.setSelectionPath(path);
          tree.scrollPathToVisible(path);
          newViewEvent(path);
          break;        
        }
      }
        if(selected != null)
          selected = selected.getNextNode();
      }
    }
    
    //search the tree in backward direction from bottom to top
    else 
    {
      //if no node is selected then select the last node in the tree
      if(selected == null)
        selected = (DefaultMutableTreeNode)tree.getPathForRow(tree.getRowCount()-1).getLastPathComponent();

      selected = selected.getPreviousNode();
      while(selected != null) {
        AbstractUMLNode n = (AbstractUMLNode) selected.getUserObject();
        if(event.getSearchByLongName()) 
        {
          Concept [] concepts = NodeUtil.getConceptsFromNode(n);
          for(int i=0; i < concepts.length; i++) {
            //System.out.println("Concepts" + i +" "+ concepts[i]);
            if(event.getSearchString().toLowerCase().contains(concepts[i].getLongName().toLowerCase())) 
            {
              path = new TreePath(selected.getPath());
              tree.setSelectionPath(path);
              tree.scrollPathToVisible(path);
              newViewEvent(path);
              selected = null;
              break;
            }
          }    
        }
        
        else {  
        //if there is a match then select that node in the tree
        if((n.getDisplay().toLowerCase()).contains(event.getSearchString().toLowerCase())) 
        {
          path = new TreePath(selected.getPath());
          tree.setSelectionPath(path);
          tree.scrollPathToVisible(path);
          newViewEvent(path);
          break;        
        }
        }
        if(selected != null)
          selected = selected.getPreviousNode();
      }
    }
    

    //if no match was found display error message
    if(path == null)
      {
        //int result = JOptionPane.showConfirmDialog(null, "Text Not Found", "No Match", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        //if(result == JOptionPane.YES_OPTION)
        //  search(event);
        
        JOptionPane.showMessageDialog(null,"Text Not Found", "No Match",JOptionPane.ERROR_MESSAGE);
      }

  }

  private void fireViewChangeEvent(ViewChangeEvent evt) {
    fireNavigationEvent(new NavigationEvent(NavigationEvent.NAVIGATE_NEXT));

    for(ViewChangeListener vcl : viewListeners) 
      vcl.viewChanged(evt);
  }
  
  private void fireNavigationEvent(NavigationEvent evt) {
    for(NavigationListener nl : navigationListeners) {
      nl.navigate(evt);
    }
  }

}