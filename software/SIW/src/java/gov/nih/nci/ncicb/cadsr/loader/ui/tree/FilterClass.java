/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import javax.swing.Icon;
import javax.swing.ImageIcon;


public class FilterClass implements ReviewableUMLNode
{

  static final Icon REVIEWED_ICON = 
    new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-class-checked.gif"));

  static final Icon DEFAULT_ICON = 
    new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-class.gif"));

  private String name, packageName;
  private boolean reviewed = true;

  public FilterClass(String name, String packageName) 
  {
    this.name = name;
    this.packageName = packageName;
    
  }

  public String getName() 
  {
    return name;
  }

  public String getPackageName() 
  {
    return packageName;
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

