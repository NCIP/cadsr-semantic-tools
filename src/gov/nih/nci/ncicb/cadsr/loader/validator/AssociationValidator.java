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
  public ValidationItems validate() {
    ValidationItems items = ValidationItems.getInstance();
    ObjectClassRelationship ocr = DomainObjectFactory.newObjectClassRelationship();
    List ocrs = elements.getElements(ocr.getClass());
    if(ocrs == null) {
      return items;
    }
    for(Iterator it = ocrs.iterator(); it.hasNext(); ) {
      ocr = (ObjectClassRelationship)it.next();
      if(ocr.getSource() == null || ocr.getTarget() == null) {
        items.addItem(new ValidationError
                   (PropertyAccessor.getProperty(
                      "association.missing.end", 
                      new String[] {ocr.getSourceRole(),
                                    ocr.getTargetRole()}), 
                    ocr
                    ));
        it.remove();
        continue;
      }
      if(!areRoleNamesValid(ocr)) {
        items.addItem(new ValidationError
                   (PropertyAccessor.getProperty(
                      "association.missing.role", 
                      new String[] {ocr.getSource().getLongName(),
                                    ocr.getTarget().getLongName()}),
                    ocr)); 
                   ;
        it.remove();
        continue;
      }
    }
    
    return items;
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