package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.domain.*;

public class OCRRoleNameBuilder  {

  public String buildRoleName(ObjectClassRelationship ocr) {

    if(ocr.getType().equals(ocr.TYPE_IS)) {
      return ocr.getSource().getLongName() + "-->" + ocr.getTarget().getLongName();
    }

    StringBuffer roleName = new StringBuffer();
    
    if(ocr.getDirection().equals(ObjectClassRelationship.DIRECTION_BOTH)) {
      roleName.append(ocr.getSource().getLongName());
      if (ocr.getSourceRole() != null) {
        roleName.append(".")
          .append(ocr.getSourceRole());
      }
      
      String cardinality = "" + ocr.getSourceLowCardinality();
      if(ocr.getSourceLowCardinality() != ocr.getSourceHighCardinality()) {
        cardinality += ".."; 
        if(ocr.getSourceHighCardinality()==-1) {
          cardinality += "*";
        } else {
          cardinality += ocr.getSourceHighCardinality();
        }
      }
      
      roleName.append("(")
        .append(cardinality)
        .append(")::");
    }
    
    roleName.append(ocr.getTarget().getLongName());

    if (ocr.getTargetRole() != null) {
      roleName.append(".")
        .append(ocr.getTargetRole());
    }

    String cardinality = "" + ocr.getTargetLowCardinality();
    if(ocr.getTargetLowCardinality() != ocr.getTargetHighCardinality()) {
      cardinality += ".."; 
      if(ocr.getTargetHighCardinality()==-1) {
	cardinality += "*";
      } else {
	cardinality += ocr.getTargetHighCardinality();
      }
    }    

    roleName.append("(")
      .append(cardinality)
      .append(")");

    return roleName.toString();
  }

}