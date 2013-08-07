/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

/*
 * Copyright 2000-2003 Oracle, Inc. This software was developed in conjunction with the National Cancer Institute, and so to the extent government employees are co-authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105.
 *
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the disclaimer of Article 3, below. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 2. The end-user documentation included with the redistribution, if any, must include the following acknowledgment:
 *
 * "This product includes software developed by Oracle, Inc. and the National Cancer Institute."
 *
 * If no such end-user documentation is to be included, this acknowledgment shall appear in the software itself, wherever such third-party acknowledgments normally appear.
 *
 * 3. The names "The National Cancer Institute", "NCI" and "Oracle" must not be used to endorse or promote products derived from this software.
 *
 * 4. This license does not authorize the incorporation of this software into any proprietary programs. This license does not authorize the recipient to use any trademarks owned by either NCI or Oracle, Inc.
 *
 * 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, ORACLE, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 */
package gov.nih.nci.ncicb.cadsr.loader.ui.util;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.AttributeNode;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.UMLNode;

import java.util.Enumeration;
import javax.swing.tree.*;
import javax.swing.*;

public class TreeUtil 
{
  private static TreeNode[] treeNode;

  public static void collapseAll(JTree tree) {
    for (int i = tree.getRowCount() - 1; i >= 0; i--) {
      tree.collapseRow(i);
    }
  }

  public static void expandAll(JTree tree, TreeNode root) {
    //Create TreePath from the root 
    TreePath parent = new TreePath(root);

    doNode(tree, root, parent);
    
    //Traverse if the root has any children
  }

  public static TreeNode[] getPathFromUMLNode(JTree tree, UMLNode node) {
    TreeModel model = tree.getModel();
    Object root = model.getRoot();
    return goJTree(model, root, node);

  }

    private static TreeNode[] goJTree( TreeModel model, Object o, UMLNode node ){    
        int count = model.getChildCount(o);    
        for( int i=0;  i < count;  i++ )    {        
            DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)model.getChild(o, i );
            if(node == dmtn.getUserObject()){
                treeNode = new TreeNode[dmtn.getPath().length];
                treeNode = dmtn.getPath();
                break;
            }
            else            
                goJTree(model, model.getChild(o, i), node);    
        }
        return treeNode;
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