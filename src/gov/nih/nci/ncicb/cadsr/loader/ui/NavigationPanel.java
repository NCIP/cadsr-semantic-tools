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

public class NavigationPanel extends JPanel implements ActionListener, MouseListener
{
  private JTree tree;
  private JPopupMenu popup;
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
      if(path != null) {
        DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) path.getLastPathComponent();
        
        Object o = dmtn.getUserObject();
        if((o instanceof ClassNode)
           || (o instanceof AttributeNode)
           ) {
          ViewChangeEvent evt = new ViewChangeEvent(ViewChangeEvent.VIEW_CONCEPTS);
          evt.setViewObject(dmtn.getUserObject());
          evt.setInNewTab(false);
          vcl.viewChanged(evt);
        } else if(o instanceof AssociationNode) {
          ViewChangeEvent evt = new ViewChangeEvent(ViewChangeEvent.VIEW_ASSOCIATION);
          evt.setViewObject(dmtn.getUserObject());
          evt.setInNewTab(false);
          vcl.viewChanged(evt);
        }
      }
    } else if(e.getButton() == MouseEvent.BUTTON2) {
      TreePath path = tree.getPathForLocation(e.getX(), e.getY());
      if(path != null) {
        DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) path.getLastPathComponent();
        ViewChangeEvent evt = new ViewChangeEvent(ViewChangeEvent.VIEW_CONCEPTS);
        evt.setViewObject(dmtn.getUserObject());
        evt.setInNewTab(true);
        vcl.viewChanged(evt);
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

  

}