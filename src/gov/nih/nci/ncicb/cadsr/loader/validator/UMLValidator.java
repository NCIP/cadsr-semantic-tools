package gov.nih.nci.ncicb.cadsr.loader.validator;

import java.util.*;

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
  public List validate() {
    // !!! TODO

    List errors = new ArrayList();
    
    ObjectClass oc = DomainObjectFactory.newObjectClass();
    List ocs = (List)elements.getElements(oc.getClass());
    for(Iterator it = ocs.iterator(); it.hasNext(); ) {
      ObjectClass o = (ObjectClass)it.next();
      if(o.getConcept().getPreferredName() == null) {
        errors.add(new ValidationError(SEVERITY_ERROR, "Class: " + o.getLongName() + " has no concept code."));
      }
    }

    Property prop = DomainObjectFactory.newProperty();
    List props = elements.getElements(prop.getClass());
    for(Iterator it = props.iterator(); it.hasNext(); ) {
      Property o = (Property)it.next();
//       if(o.getConcept().getPreferredName() == null) {
//         errors.add(new ValidationError(SEVERITY_ERROR, "Attribute: " + o.getLongName() + " has no concept code."));
//       }
    }

    return errors;
  }

}