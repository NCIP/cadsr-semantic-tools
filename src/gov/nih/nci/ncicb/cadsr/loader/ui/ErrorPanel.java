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

public class ErrorPanel extends JPanel 
 implements MouseListener {

  private JTree tree;
  private Set displaySet = new HashSet();
  private JPopupMenu popup;

  public ErrorPanel(UMLNode rootNode) {
    initUI(rootNode);
    buildPopupMenu();
  }

  private void initUI(UMLNode rootNode) {
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

  public void buildPopupMenu() {
    popup = new JPopupMenu();
    JMenuItem menuItem = new JMenuItem("Export Errors");
    popup.add(menuItem);
  }

  private void firstRun(UMLNode node) {
    Set<UMLNode> children = node.getChildren();
    
    Set<ValidationNode> valNodes = node.getValidationNodes();

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

