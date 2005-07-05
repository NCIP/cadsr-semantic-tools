package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;

public class TreeMouseListener extends MouseAdapter {
  private JPopupMenu popup;
  private JMenuItem menuItem;
  private JTree tree;

  public TreeMouseListener(JPopupMenu popupMenu, JTree tree) {
    this.tree = tree;
    popup = popupMenu;
  }

  public void mousePressed(MouseEvent e) {
    maybeShowPopup(e);
  }
  
  public void mouseReleased(MouseEvent e) {
    maybeShowPopup(e);
  }
  
  private void maybeShowPopup(MouseEvent e) {
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
    } else if(e.getButton() == MouseEvent.BUTTON1) {
      
    }
  
  }
}