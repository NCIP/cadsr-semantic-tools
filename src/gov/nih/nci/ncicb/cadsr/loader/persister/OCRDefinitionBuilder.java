package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.domain.*;

/**
 * Utility method to build the definition of an OCR.
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class OCRDefinitionBuilder  {

  public String buildDefinition(ObjectClassRelationship ocr) {
    	String definition = null;


	if(ocr.getType().equals(ObjectClassRelationship.TYPE_IS)) {
	  if (ocr.getSourceHighCardinality() == -1) {
	    definition = "Many-to-";
	  } else {
	    definition = "One-to-";
	  }
	  
	  if (ocr.getTargetHighCardinality() == -1) {
	    definition += "many";
	  } else {
	    definition += "one";
	  }
	} else if(ocr.getType().equals(ObjectClassRelationship.TYPE_HAS)) {
	  definition = ocr.getSource().getLongName()
	    + " generalizes " 
	    + ocr.getTarget().getLongName();
	}


	return definition;
  }

}

