package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ClassNode extends AbstractUMLNode {

  public ClassNode(ObjectClass oc) {
    fullPath = oc.getLongName();
    int ind = fullPath.lastIndexOf(".");
    display = fullPath.substring(ind + 1);
  }

  public Icon getIcon() {
    return new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-class.gif"));

  }

}