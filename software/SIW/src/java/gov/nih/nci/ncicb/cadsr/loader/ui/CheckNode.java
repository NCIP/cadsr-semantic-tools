package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.io.*;
import java.util.*;
import javax.swing.tree.*;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.ReviewableUMLNode;

public class CheckNode extends DefaultMutableTreeNode {

  public final static int SINGLE_SELECTION = 0;
  public final static int DIG_IN_SELECTION = 4;
  protected int selectionMode;
//   protected boolean isSelected;

  public CheckNode() {
    this(null);
  }

  public CheckNode(ReviewableUMLNode userObject) {
    this(userObject, true, true);
  }

  public CheckNode(ReviewableUMLNode userObject, boolean allowsChildren
                                    , boolean isSelected) {
    super(userObject, allowsChildren);
    userObject.setReviewed(isSelected);
    setSelectionMode(DIG_IN_SELECTION);
  }


  public void setSelectionMode(int mode) {
    selectionMode = mode;
  }

  public int getSelectionMode() {
    return selectionMode;
  }

  public void setSelected(boolean isSelected) {
    ReviewableUMLNode userObject = (ReviewableUMLNode)getUserObject();
    userObject.setReviewed(isSelected);

    if ((selectionMode == DIG_IN_SELECTION)
        && (children != null)) {
      for(Object o : children) {
        CheckNode node = (CheckNode)o;
        node.setSelected(isSelected);
      }
    }

    // unselected parent if you unselect child
    if(!isSelected && parent instanceof CheckNode) {
      CheckNode pNode = (CheckNode)parent;
      ReviewableUMLNode pObject = (ReviewableUMLNode)(pNode.getUserObject());
      pObject.setReviewed(isSelected);
    }
  }
  
  public boolean isSelected() {
    ReviewableUMLNode userObject = (ReviewableUMLNode)getUserObject();
    return userObject.isReviewed();
  }


  // If you want to change "isSelected" by CellEditor,
  /*
  public void setUserObject(Object obj) {
    if (obj instanceof Boolean) {
      setSelected(((Boolean)obj).booleanValue());
    } else {
      super.setUserObject(obj);
    }
  }
  */

}


