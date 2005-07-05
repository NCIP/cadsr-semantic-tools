package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ClassNode extends AbstractUMLNode 
  implements ReviewableUMLNode
{

  static final Icon REVIEWED_ICON = 
    new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-class-checked.gif"));

  static final Icon DEFAULT_ICON = 
    new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-class.gif"));

  
  private boolean reviewed = false;

  public ClassNode(ObjectClass oc) {
    fullPath = oc.getLongName();
    int ind = fullPath.lastIndexOf(".");
    display = fullPath.substring(ind + 1);
    
    userObject = oc;
    
    icon = DEFAULT_ICON;
  }


  public void setReviewed(boolean currentStatus) 
  {
      reviewed = currentStatus;
      
      boolean changeIcon = true;
      
      // iterate over children
      // if all children are reviewed then review the class
      for(UMLNode l : getChildren())
      {
        AttributeNode next = (AttributeNode) l;
        if(!next.isReviewed()) 
        {
          changeIcon = false;
          break;
        }
      }
      
      if(changeIcon && currentStatus) 
      {
        setIcon(REVIEWED_ICON);
      } 
      else
        setIcon(DEFAULT_ICON);
        
      PackageNode parent = (PackageNode) getParent();
      parent.setReviewed(parent.isReviewed());

  }
  
  public boolean isReviewed() 
  {
    return reviewed;
  }

}