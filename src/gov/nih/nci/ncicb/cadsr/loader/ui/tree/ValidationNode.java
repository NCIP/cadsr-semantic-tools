package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ValidationNode extends AbstractUMLNode {

  public ValidationNode(ValidationItem item) {
    fullPath = item.getMessage();
    display = item.getMessage();
  }

  public Icon getIcon() {
    return new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("red-dot.jpg"));
  }

}