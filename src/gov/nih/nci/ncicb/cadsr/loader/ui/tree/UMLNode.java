package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import java.util.Set;
import javax.swing.Icon;

public interface UMLNode {

  /**
   * Returns the field that should be displayer in a tree
   */
  public String getDisplay();

  /**
   * A full name or path to the contained object
   */
  public String getFullPath();

  /**
   * Icon that should be used to display this node
   */
  public Icon getIcon();

  /**
   * Returns this node's children, if any
   */
  public Set<UMLNode> getChildren();

  /**
   * Appends a child to the list of children.
   */
  public void addChild(UMLNode child);

  /**
   * A reference to the parent node
   */
  public UMLNode getParent();
  
  /**
   * Set the parent Node
   */
  public void setParent(UMLNode parent);

  /**
   * set icon to the newIcon 
   */
  public void setIcon(Icon newIcon);

  /**
   * @return the contained object
   */
  public Object getUserObject();

}