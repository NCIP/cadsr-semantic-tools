package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;

public class ValidationNode extends AbstractUMLNode {

  public ValidationNode(ValidationItem item) {
    fullPath = item.getMessage();
    display = item.getMessage();
  }


}