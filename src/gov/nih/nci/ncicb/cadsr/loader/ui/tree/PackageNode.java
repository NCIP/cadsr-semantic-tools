package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;


public class PackageNode extends AbstractUMLNode {

  public PackageNode(String fullName, String display) {
    fullPath = fullName;

    this.display = display != null?
      display:fullName;
 
  }


}