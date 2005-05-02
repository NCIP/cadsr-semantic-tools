package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.ui.event.*;

import gov.nih.nci.ncicb.cadsr.loader.event.ReviewEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ReviewListener;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.TreeUtil;

import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import java.util.*;
import javax.swing.tree.TreeSelectionModel;

public class NavigationPanel extends JPanel 
  implements ActionListener, MouseListener, ReviewListener, NavigationListener,
  KeyListener
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
    
//    tree.getSelectionModel().setSelectionMode
//      (TreeSelectionModel.SINGLE_TREE_SELECTION);
      
    //Traverse Tree expanding all nodes    
    TreeUtil.expandAll(tree, top);

    tree.setCellRenderer(new UMLTreeCellRenderer());
   
    this.setLayout(new BorderLayout());

    JScrollPane scrollPane = new JScrollPane(tree);
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

      vcl.viewChanged(evt);

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
//    DefaultMutableTreeNode selected = 
//      (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
//       
//    //if the down arrow is pressed then display the next element
//    //in the tree in the ViewPanel
//    //if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP)
//    
//    if(e.getKeyCode() == KeyEvent.VK_DOWN  && selected != null) 
//    {
//      //if (selected.getNextNode() != null) 
//      //{
//        TreePath path =  new TreePath(selected.getNextNode().getPath());
//        newViewEvent(path);
//      //}
//    }
//    //if the up arrow is pressed then display the previous element
//    //in the tree in the ViewPanel
//    if(e.getKeyCode() == KeyEvent.VK_UP && selected != null)
//    {
//      //if (selected.getPreviousNode() != null) 
//      //{
//        TreePath path =  new TreePath(selected.getPreviousNode().getPath());
//        newViewEvent(path);
//      //}  
//    }
        
        
////        if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) 
////        {
////
////          TreePath path =  tree.getSelectionPath();
////          newViewEvent(path);
////        }
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
        vcl.viewChanged(evt);
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
  	    vcl.viewChanged(evt);
  	  } else if(o instanceof AssociationNode) {
  	    ViewChangeEvent evt = new ViewChangeEvent(ViewChangeEvent.VIEW_ASSOCIATION);
  	    evt.setViewObject(dmtn.getUserObject());
  	    evt.setInNewTab(false);
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
  
  public void navigate(NavigationEvent event) 
  {    
    DefaultMutableTreeNode selected = 
      (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
    
      if(event.getType() == NavigationEvent.NAVIGATE_NEXT) 
      {      
        TreePath path =  new TreePath(selected.getNextNode().getPath());
        tree.setSelectionPath(path);
        newViewEvent(path);
      }
      else 
        if(event.getType() == NavigationEvent.NAVIGATE_PREVIOUS) 
        {
            TreePath path = new TreePath(selected.getPreviousNode().getPath());
            tree.setSelectionPath(path);
            newViewEvent(path);            
        }
    
  }
  
//  public void search(SearchEvent event) 
//  {
//    System.out.println("When the button is pressed I'm inside search!!!");
//    System.out.println("The search term was " + event.getSearchString());
//    
//    //DefaultMutableTreeNode theRoot = (DefaultMutableTreeNode) rootNode;
//    TreePath path = tree.getNextMatch(event.getSearchString(), 0, Position.Bias.Forward);
//    //if searchString isn't found then return error message
//    if(path == null)
//    {
//      JOptionPane.showMessageDialog(null,"Text Not Found", "No Match",JOptionPane.ERROR_MESSAGE);
//    }
//    //else select the node that matches and display that element in viewPanel
//    else
//    {
//      tree.setSelectionPath(path);
//      tree.scrollPathToVisible(path);
//      //if(tree.isVisible(path) == false)
//      //  tree.makeVisible(path);  
//      newViewEvent(path);
//    }
    
    
//  }

}