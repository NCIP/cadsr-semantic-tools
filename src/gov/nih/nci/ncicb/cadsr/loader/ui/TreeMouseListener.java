package gov.nih.nci.ncicb.cadsr.loader.ui;

import javax.swing.*;
import javax.swing.tree.TreePath;
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
      if(path != null)
	popup.show(e.getComponent(),
		   e.getX(), e.getY());
    }
  
}
}