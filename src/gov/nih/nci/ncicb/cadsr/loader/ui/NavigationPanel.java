package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.ui.event.*;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import java.util.*;

public class NavigationPanel extends JPanel 
{
  private JTree tree;;

  private UMLNode rootNode = TreeBuilder.getRootNode(); 

  private Set<NavigationListener> navListeners = new HashSet<NavigationListener>();

  public NavigationPanel()
  {
    try
    {
      initUI();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  public void addNavigationListener(NavigationListener l) {
    navListeners.add(l);
  }
  
  private void initUI() throws Exception
  {
    DefaultMutableTreeNode top = buildTree();
      
    tree = new JTree(top);
    tree.setRootVisible(false);
    tree.setShowsRootHandles(true);

    this.setLayout(new BorderLayout());
    this.add(tree, BorderLayout.CENTER);
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

  private void attachMenu() {

  }

}