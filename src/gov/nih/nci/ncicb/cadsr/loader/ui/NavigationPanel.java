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

  private Object[] treeValues = 
  {
    "caCore 3.0", 
    new Object[] {
      "CSM", 
      new Object[] {
        "Group",
        new Object[] {
          "groupName", "groupDescription"
        },
        "User",
        new Object[] {
          "userName", "userDescription"
        }
      },
      "caDSR",
      new Object[] {
        "DataElementConcept", 
        new Object[] {
          "oc", "property"
        },
        "DataElement",
        new Object[] {
          "valueDomain", "dec"
        }
      }
    }
  };

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

    this.setLayout(new BorderLayout());
    this.add(tree, BorderLayout.CENTER);
  }

  private DefaultMutableTreeNode buildTree() {
    
//     DefaultMutableTreeNode node = new DefaultMutableTreeNode((String)treeValues[0]);

//     return doNode(node, (Object[])treeValues[1]);

     DefaultMutableTreeNode node = new DefaultMutableTreeNode(rootNode);

     return doNode(node, rootNode);
    

  }

  private DefaultMutableTreeNode doNode(DefaultMutableTreeNode node, UMLNode parentNode) {

    Set<UMLNode> children = parentNode.getChildren();
    for(UMLNode child : children) {
      DefaultMutableTreeNode newNode = 
        new DefaultMutableTreeNode(child);

      node.add(newNode);
      doNode(newNode, child);
    }

    return node;
    
  }

  private void attachMenu() {

  }

}