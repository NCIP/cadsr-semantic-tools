package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import gov.nih.nci.ncicb.cadsr.domain.DataElement;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class AttributeNode extends AbstractUMLNode 
  implements ReviewableUMLNode
  {
  static final Icon REVIEWED_ICON = 
    new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-attribute-checked.gif"));

  static final Icon DEFAULT_ICON = 
    new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-attribute.gif"));


  private boolean reviewed = false;
  
  public AttributeNode(DataElement de) {
    display = de.getDataElementConcept().getProperty().getLongName();

    fullPath = de.getDataElementConcept().getObjectClass().getLongName()
      + "." + display;
    userObject = de;
    
    icon = DEFAULT_ICON;
  }

  public void setReviewed(boolean currentStatus) {
    reviewed = currentStatus;

    setIcon(reviewed?REVIEWED_ICON:DEFAULT_ICON);

    //calls setReviewed method on parent 
    ClassNode parent = (ClassNode) getParent();
    parent.setReviewed(parent.isReviewed());


  }
  
  public boolean isReviewed() {
    return reviewed;  
  }
}