package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PackageNode extends AbstractUMLNode 
  implements ReviewableUMLNode 
  {

  static final Icon REVIEWED_ICON = 
    new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-package-checked.gif"));

  static final Icon DEFAULT_ICON = 
    new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-package.gif"));

  private boolean reviewed = true;

  public PackageNode(String fullName, String display) {
    fullPath = fullName;

    this.display = display != null?
      display:fullName;
    
    icon = DEFAULT_ICON;
  }

  public void setReviewed(boolean currentStatus) 
  {    
    boolean changeIcon = true;
    
    for(UMLNode l : getChildren()) 
    {
      ClassNode next = (ClassNode) l;
      if(!next.getIcon().equals(ClassNode.REVIEWED_ICON)) 
      {
        changeIcon = false;
        break;
      }
    }
    
    if(changeIcon)
    {
      setIcon(REVIEWED_ICON);
    }
    else
      setIcon(DEFAULT_ICON);
  }
  
  public boolean isReviewed() 
  {
    return reviewed;
  }

}