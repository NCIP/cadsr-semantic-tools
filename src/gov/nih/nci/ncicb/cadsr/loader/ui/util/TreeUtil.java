package gov.nih.nci.ncicb.cadsr.loader.ui.util;

import java.util.Enumeration;
import javax.swing.tree.*;
import javax.swing.*;

public class TreeUtil 
{

  public static void expandAll(JTree tree, TreePath parent) 
  {
    TreeNode node = (TreeNode) parent.getLastPathComponent();
    //Traverse all children
    if(node.getChildCount() >= 0)
    {
      for(Enumeration e=node.children(); e.hasMoreElements();) 
      {
        TreeNode n = (TreeNode) e.nextElement();
        TreePath path = parent.pathByAddingChild(n);
        expandAll(tree,path);
      }
    }
    
    tree.expandPath(parent);
  }
}