package gov.nih.nci.ncicb.cadsr.loader.validator;

import java.util.*;

import gov.nih.nci.ncicb.cadsr.domain.*;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.loader.util.*;

public class ConceptCodeValidator implements Validator {

  private ElementsLists elements;

  public ConceptCodeValidator(ElementsLists elements) {
    this.elements = elements;
  }

  /**
   * returns a list of Validation errors.
   */
  public List validate() {
    List errors = new ArrayList();
    
    List conceptErrors = (List)elements.getElements(ConceptError.class);
    if(conceptErrors != null) 
      for(Iterator it = conceptErrors.iterator(); it.hasNext(); ) {
        ConceptError o = (ConceptError)it.next();
        errors.add(o);
      }
    

    ObjectClass oc = DomainObjectFactory.newObjectClass();
    List ocs = (List)elements.getElements(oc.getClass());
    if(ocs != null)
      for(Iterator it = ocs.iterator(); it.hasNext(); ) {
        ObjectClass o = (ObjectClass)it.next();
        if(o.getPreferredName() == null || o.getPreferredName().length() < 4) {
          errors.add(new ValidationError(SEVERITY_ERROR, "Class: " + o.getLongName() + " has no concept code."));
        }
      }

    Property prop = DomainObjectFactory.newProperty();
    List props = elements.getElements(prop.getClass());
    if(props != null)
      for(Iterator it = props.iterator(); it.hasNext(); ) {
        Property o = (Property)it.next();
        if(o.getPreferredName() == null || o.getPreferredName().length() < 4) {
          errors.add(new ValidationError(SEVERITY_ERROR, "Attribute: " + o.getLongName() + " has no concept code."));
        }
      }


    Concept con = DomainObjectFactory.newConcept();
    List concepts = elements.getElements(con.getClass());
    if(concepts != null) {
      for(Iterator it = concepts.iterator(); it.hasNext(); ) {
        Concept o = (Concept)it.next();
        if(o.getPreferredName() == null) {
          // capture this above. Dont need any more.
//           errors.add(new ValidationError(SEVERITY_ERROR, 
//                                          PropertyAccessor.getProperty("validation.concept.missing")));
        } else {
          if(StringUtil.isEmpty(o.getLongName()))
            errors.add(new ValidationError(SEVERITY_ERROR,
                                           PropertyAccessor.getProperty("validation.concept.missing.longName", o.getPreferredName())));
          if(StringUtil.isEmpty(o.getPreferredDefinition()))
            errors.add(
              new ValidationError(SEVERITY_ERROR,
                                  PropertyAccessor.getProperty("validation.concept.missing.definition", o.getPreferredName())));
          if(StringUtil.isEmpty(o.getDefinitionSource()))
            errors.add(
              new ValidationError(SEVERITY_ERROR,
                                  PropertyAccessor.getProperty("validation.concept.missing.source", o.getPreferredName())));
        }
      }
    }

    return errors;
  }

}