package gov.nih.nci.ncicb.cadsr.loader.ui;

import javax.swing.*;
import java.awt.*;
import gov.nih.nci.ncicb.cadsr.loader.validator.*;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;
import javax.swing.tree.DefaultTreeCellRenderer;

public class UMLTreeCellRenderer extends DefaultTreeCellRenderer {

  public Component getTreeCellRendererComponent(JTree tree,
                                              Object value,
                                              boolean sel,
                                              boolean expanded,
                                              boolean leaf,
                                              int row,
					      boolean hasFocus) {

    super.getTreeCellRendererComponent(
                        tree, value, sel,
                        expanded, leaf, row,
                        hasFocus);

    DefaultMutableTreeNode node =
      (DefaultMutableTreeNode)value;
    UMLNode umlNode = (UMLNode) node.getUserObject();
    setIcon(umlNode.getIcon());

    return this;
  }

}