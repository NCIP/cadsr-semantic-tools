package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ClassNode extends AbstractUMLNode 
  implements ReviewableUMLNode
{

  static final Icon REVIEWED_ICON = 
    new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-class.gif"));

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


  public void setReviewed(boolean b) 
  {
      this.reviewed = b;
      
      boolean changeIcon = true;
      
      // iterate over children
      for(UMLNode l : this.getChildren())
      {
        if(icon != AttributeNode.REVIEWED_ICON)
        changeIcon = false;
      }
      
      if(changeIcon) 
      {
        this.setIcon(REVIEWED_ICON);
      } 
//      else
//      {
//        this.setIcon(REVIEWED_ICON);
//        if() 
//        {
//          
//        } else 
//        {
//          
//        }
//      }
      
  }
  
  public boolean isReviewed() 
  {
    return reviewed;
  }

}