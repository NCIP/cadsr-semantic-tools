package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.loader.persister.OCRRoleNameBuilder;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class AssociationNode extends AbstractUMLNode {

  public AssociationNode(ObjectClassRelationship ocr) {
    OCRRoleNameBuilder nameBuilder = new OCRRoleNameBuilder();
    String roleName = nameBuilder.buildRoleName(ocr);

    fullPath = roleName;
    display = roleName;
    
    userObject = ocr;
  }

  public Icon getIcon() {
    return new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("tree-class.gif"));

  }

}