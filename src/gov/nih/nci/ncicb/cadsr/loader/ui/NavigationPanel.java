package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.ui.event.*;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;

import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import java.util.*;

public class NavigationPanel extends JPanel implements ActionListener
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

    tree.setCellRenderer(new UMLTreeCellRenderer());
   
    this.setLayout(new BorderLayout());
    this.add(tree, BorderLayout.CENTER);
    
    buildPopupMenu();
  }

  public void actionPerformed(ActionEvent event) {
    System.out.println("menuItem is selected");
    DefaultMutableTreeNode dmtn, node;
    
    TreePath path = tree.getSelectionPath();
    dmtn = (DefaultMutableTreeNode) path.getLastPathComponent();
    
    System.out.println((((UMLNode)dmtn.getUserObject()).getDisplay()));

  }


  public void buildPopupMenu() {
    JMenuItem menuItem = new JMenuItem("A test item");
    JPopupMenu popup = new JPopupMenu();
    menuItem.addActionListener(this);
    popup.add(menuItem);
    
    MouseListener popupListener = new TreeMouseListener(popup,tree);
    tree.addMouseListener(popupListener);
   
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