package gov.nih.nci.ncicb.cadsr.loader.validator;

import java.util.List;
import java.util.ArrayList;

import gov.nih.nci.ncicb.cadsr.domain.*;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

public class UMLValidator implements Validator {

  private ElementsLists elements;

  public UMLValidator(ElementsLists elements) {
    this.elements = elements;
  }

  /**
   * returns a list of Validation errors.
   */
  public List<ValidationError> validate() {
    // !!! TODO
    
    ObjectClass oc = DomainObjectFactory.newObjectClass();
    List<ObjectClass> ocs = (List<ObjectClass>)elements.getElements(oc.getClass());

    for(ObjectClass o : ocs) {
      
    }

    return new ArrayList<ValidationError>();
  }

}