package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class FilterPackage implements ReviewableUMLNode
{

  static final Icon REVIEWED_ICON = 
    new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-package-checked.gif"));

  static final Icon DEFAULT_ICON = 
    new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-package.gif"));

  private String name;
  private boolean reviewed = true;

  public FilterPackage(String packageName) 
  {
    this.name = packageName;
  }

  public String getName() 
  {
    return name;
  }

  public void setReviewed(boolean b) 
  {
    reviewed = b;
  }
  public boolean isReviewed() 
  {
    return reviewed;
  }  

  public Icon getIcon() 
  { 
    return reviewed?REVIEWED_ICON:DEFAULT_ICON;
  }

  public String toString() 
  {
    return getName();
  }
}

