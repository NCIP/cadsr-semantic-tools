package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;

public class ClassNode extends AbstractUMLNode {

  public ClassNode(ObjectClass oc) {
    fullPath = oc.getLongName();
    int ind = fullPath.lastIndexOf(".");
    display = fullPath.substring(ind + 1);
  }


}