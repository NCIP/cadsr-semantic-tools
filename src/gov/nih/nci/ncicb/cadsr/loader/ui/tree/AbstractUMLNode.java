package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import java.util.Set;
import java.util.LinkedHashSet;

import javax.swing.Icon;

public abstract class AbstractUMLNode implements UMLNode {

  protected String fullPath;

  protected String display;
  protected Icon icon;

  // No complex ordering, we want to see elements
  // just the way they were inserted.
  private Set<UMLNode> children = new LinkedHashSet();
  
  private Set<ValidationNode> validationNodes = new LinkedHashSet();

  private UMLNode parent;

  protected Object userObject;

  public String getDisplay() {
    return display;
  }

  public String getFullPath() {
    return fullPath;
  }

  public Icon getIcon() {
    return icon;
  }

  public boolean equals(Object o) {
    if(o instanceof UMLNode) {
      UMLNode n = (UMLNode)o;
      return n.getFullPath().equals(getFullPath());
    } else
      return false;
  }

  public int hashCode() {
    return fullPath.hashCode();
  }

  public Set<UMLNode> getChildren() {
    return children;
  }

  public Set<ValidationNode> getValidationNodes() 
  {
    return validationNodes;
  }

  public void addValidationNode(ValidationNode child) 
  {
    validationNodes.add(child);
    child.setParent(this);
  }


  public void addChild(UMLNode child) {
    children.add(child);
    child.setParent(this);
  }

  public String toString() {
    return display;
  }

  public UMLNode getParent() {
    return parent;
  }

  public void setParent(UMLNode parent) {
    this.parent = parent;
  }
  
  public void setIcon(Icon newIcon) {
    icon = newIcon;
  }
  public Object getUserObject() {
    return userObject;
  }

}