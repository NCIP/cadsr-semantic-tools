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

  private ViewChangeListener vcl;

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

  public void addViewChangeListener(ViewChangeListener l) {
    // replace by list
    vcl = l;
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

      vcl.viewChanged(evt);

    }
  }


  public void buildPopupMenu() {
    JMenuItem newTabItem = new JMenuItem("Open in New Tab");
    JMenuItem openItem = new JMenuItem("Open");

    newTabItem.setActionCommand("OPEN_NEW_TAB");
    openItem.setActionCommand("OPEN");

    JPopupMenu popup = new JPopupMenu();

    newTabItem.addActionListener(this);
    openItem.addActionListener(this);

    popup.add(openItem);
    popup.add(newTabItem);
    
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

}