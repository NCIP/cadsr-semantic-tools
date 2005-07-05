package gov.nih.nci.ncicb.cadsr.loader.ui.util;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.AttributeNode;
import java.util.Enumeration;
import javax.swing.tree.*;
import javax.swing.*;

public class TreeUtil 
{

  public static void expandAll(JTree tree, TreeNode root) {
    //Create TreePath from the root 
    TreePath parent = new TreePath(root);

    doNode(tree, root, parent);
    
    //Traverse if the root has any children
  }

  private static void doNode(JTree tree, TreeNode parentNode, TreePath parentPath) {
    if(parentNode.getChildCount() > 0)
    {
      for(Enumeration e=parentNode.children(); e.hasMoreElements();) 
      {
        TreeNode childNode = (TreeNode) e.nextElement();
        TreePath path = parentPath.pathByAddingChild(childNode);
        tree.expandPath(path);

        doNode(tree, childNode, path);
      }      
    }
  }

}