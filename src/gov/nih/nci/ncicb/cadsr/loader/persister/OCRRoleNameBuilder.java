package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.domain.*;

public class OCRRoleNameBuilder  {

  public String buildRoleName(ObjectClassRelationship ocr) {
    // !!! TODO This requirement is changing. Business Case will require a Role name for all associations.
    String roleName = ocr.getSource().getLongName();

    if (ocr.getSourceRole() != null) {
      roleName += ("." + ocr.getSourceRole());
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
      


    roleName += ("(" + cardinality + ")::" +
		 ocr.getTarget().getLongName());

    if (ocr.getTargetRole() != null) {
      roleName += ("." + ocr.getTargetRole());
    }

    cardinality = "" + ocr.getTargetLowCardinality();
    if(ocr.getTargetLowCardinality() != ocr.getTargetHighCardinality()) {
      cardinality += ".."; 
      if(ocr.getTargetHighCardinality()==-1) {
	cardinality += "*";
      } else {
	cardinality += ocr.getTargetHighCardinality();
      }
    }    

    roleName += ("(" + cardinality + ")");

    return roleName;
  }

}