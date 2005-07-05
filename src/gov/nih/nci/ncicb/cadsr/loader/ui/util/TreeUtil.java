package gov.nih.nci.ncicb.cadsr.loader.ui.util;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.AttributeNode;
import java.util.Enumeration;
import javax.swing.tree.*;
import javax.swing.*;

public class TreeUtil 
{

  public static void expandAll(JTree tree, TreeNode root) 
  {
    //Create TreePath from the root 
    TreePath parent = new TreePath(root);
    
    //Traverse if the root has any children
    if(root.getChildCount() > 0)
    {
      //Traverse through the package nodes and expand them
      for(Enumeration e=root.children(); e.hasMoreElements();) 
      {
        TreeNode packageNode = (TreeNode) e.nextElement();
        TreePath path = parent.pathByAddingChild(packageNode);
        tree.expandPath(path);

        //Traverse through the class nodes and expand them
        for(Enumeration f=packageNode.children(); f.hasMoreElements();)
        {
          TreeNode classNode = (TreeNode) f.nextElement();
          TreePath path2 = path.pathByAddingChild(classNode);
          tree.expandPath(path2);
        }        
      }      
    }
    
  }
}