package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PackageNode extends AbstractUMLNode {

  public PackageNode(String fullName, String display) {
    fullPath = fullName;

    this.display = display != null?
      display:fullName;
 
  }

  public Icon getIcon() {
    return new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-package.gif"));

  }

}