package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import java.util.Set;
import javax.swing.Icon;

public interface UMLNode {

  public String getDisplay();

  public String getFullPath();

  public Icon getIcon();

  public Set<UMLNode> getChildren();

  public void addChild(UMLNode child);

  public UMLNode getParent();
  
  public void setParent(UMLNode parent);

}