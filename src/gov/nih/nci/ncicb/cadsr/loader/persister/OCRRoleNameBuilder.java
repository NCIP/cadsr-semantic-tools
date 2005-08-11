package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.domain.*;

public class OCRRoleNameBuilder  {


  public String buildRoleName(ObjectClassRelationship ocr) 
  {
    return buildRoleName(ocr, true);
  }

  public String buildDisplayRoleName(ObjectClassRelationship ocr) 
  {
    return buildRoleName(ocr, false);
  }

  private String buildRoleName(ObjectClassRelationship ocr, boolean longName) {

    if(ocr.getType().equals(ocr.TYPE_IS)) {
      return ocr.getSource().getLongName() + "-->" + ocr.getTarget().getLongName();
    }

    StringBuffer roleName = new StringBuffer();
    
    if(ocr.getDirection().equals(ObjectClassRelationship.DIRECTION_BOTH)) {
      if(longName) {
        roleName.append(ocr.getSource().getLongName()); }
      if (ocr.getSourceRole() != null) {
        if(longName)
          roleName.append(".")
            .append(ocr.getSourceRole());
        else
          roleName.append(ocr.getSourceRole());
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
    
    if(longName) {
    roleName.append(ocr.getTarget().getLongName()); }

    if (ocr.getTargetRole() != null) {
      if(longName)
        roleName.append(".")
          .append(ocr.getTargetRole());
      else
        roleName.append(ocr.getTargetRole());
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