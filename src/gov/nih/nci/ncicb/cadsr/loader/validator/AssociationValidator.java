package gov.nih.nci.ncicb.cadsr.loader.validator;

import java.util.*;

import gov.nih.nci.ncicb.cadsr.domain.*;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

public class AssociationValidator implements Validator {

  private ElementsLists elements;

  public AssociationValidator(ElementsLists elements) {
    this.elements = elements;
  }

  /**
   * returns a list of Validation errors.
   */
  public List validate() {
    List errors = new ArrayList();
    ObjectClassRelationship ocr = DomainObjectFactory.newObjectClassRelationship();
    List ocrs = elements.getElements(ocr.getClass());
    if(ocrs == null) {
      return errors;
    }
    for(Iterator it = ocrs.iterator(); it.hasNext(); ) {
      ocr = (ObjectClassRelationship)it.next();
      if(ocr.getSource() == null || ocr.getTarget() == null) {
        errors.add(new ValidationError
                   (SEVERITY_ERROR, PropertyAccessor.getProperty(
                      "association.missing.end", 
                      new String[] {ocr.getSourceRole(),
                                    ocr.getTargetRole()})));
        it.remove();
        continue;
      }
      if(!areRoleNamesValid(ocr)) {
        errors.add(new ValidationError
                   (SEVERITY_ERROR, PropertyAccessor.getProperty(
                      "association.missing.role", 
                      new String[] {ocr.getSource().getLongName(),
                                    ocr.getTarget().getLongName()})));
        it.remove();
        continue;
      }
    }
    
    return errors;
  }

  private boolean areRoleNamesValid(ObjectClassRelationship ocr) {
    return !(
      ocr.getType().equals(ObjectClassRelationship.TYPE_HAS)
      && 
      (ocr.getTargetRole() == null 
       || ocr.getTargetRole().length() == 0
       || 
       (ocr.getDirection().equals(ObjectClassRelationship.DIRECTION_BOTH) && (
          ocr.getSourceRole().length() == 0 
          ||ocr.getSourceRole() == null )
        )
       )
      );
  }

}