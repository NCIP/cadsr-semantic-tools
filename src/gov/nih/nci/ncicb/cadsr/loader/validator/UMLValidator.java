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
    List errors = new ArrayList();
    
    ObjectClass oc = DomainObjectFactory.newObjectClass();
    List ocs = (List)elements.getElements(oc.getClass());
    if(ocs != null)
      for(Iterator it = ocs.iterator(); it.hasNext(); ) {
        ObjectClass o = (ObjectClass)it.next();
        if(o.getConcept().getPreferredName() == null) {
          errors.add(new ValidationError(SEVERITY_ERROR, "Class: " + o.getLongName() + " has no concept code."));
        }
      }

    Property prop = DomainObjectFactory.newProperty();
    List props = elements.getElements(prop.getClass());
    if(props != null)
      for(Iterator it = props.iterator(); it.hasNext(); ) {
        Property o = (Property)it.next();
        if(o.getConcept().getPreferredName() == null) {
          errors.add(new ValidationError(SEVERITY_ERROR, "Attribute: " + o.getLongName() + " has no concept code."));
        }
      }


    Concept con = DomainObjectFactory.newConcept();
    List concepts = elements.getElements(con.getClass());
    if(concepts != null) {
      for(Iterator it = concepts.iterator(); it.hasNext(); ) {
        Concept o = (Concept)it.next();
        if(o.getPreferredName() == null) {
          errors.add(new ValidationError(SEVERITY_ERROR, "A Concept is missing its PreferredName."));
        } else {
          if(o.getLongName() == null)
            errors.add(new ValidationError(SEVERITY_ERROR, "Concept: " + o.getPreferredName() + " is missing Long Name."));
          if(o.getPreferredDefinition() == null)
            errors.add(new ValidationError(SEVERITY_ERROR, "Concept: " + o.getPreferredName() + " is missing Preferred Definition."));
          if(o.getDefinitionSource() == null)
            errors.add(new ValidationError(SEVERITY_ERROR, "Concept: " + o.getPreferredName() + " is missing Definition Source."));
        }
      }
    }

    return errors;
  }

}