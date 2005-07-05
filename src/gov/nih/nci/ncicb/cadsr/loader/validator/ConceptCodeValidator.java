package gov.nih.nci.ncicb.cadsr.loader.validator;

import java.util.*;

import gov.nih.nci.ncicb.cadsr.domain.*;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.loader.util.*;

public class ConceptCodeValidator implements Validator {

  private ElementsLists elements;

  private ValidationItems items = ValidationItems.getInstance();

  public ConceptCodeValidator(ElementsLists elements) {
    this.elements = elements;
  }

  /**
   * returns a list of Validation errors.
   */
  public ValidationItems validate() {
    
    List<ObjectClass> ocs = (List<ObjectClass>)elements.getElements(DomainObjectFactory.newObjectClass().getClass());
    if(ocs != null)
      for(ObjectClass o : ocs) {
        if(StringUtil.isEmpty(o.getPreferredName()))
          items.addItem(new ValidationError("Class: " + o.getLongName() + " has no concept code.", o));
        else {
          checkConcepts(o);
        }
      }    


    List<Property> props = (List<Property>)elements.getElements(DomainObjectFactory.newProperty().getClass());
    if(props != null)
      for(Property o : props ) 
        if(StringUtil.isEmpty(o.getPreferredName()))
          items.addItem(new ValidationError("Attribute: " + o.getLongName() + " has no concept code.", o));
        else {
          checkConcepts(o);
        }
    
//     List<Concept> concepts = (List<Concept>)elements.getElements(DomainObjectFactory.newConcept().getClass());
//     if(concepts != null) {
//       for(Concept o : concepts ) {
//         Concept o = (Concept)it.next();
//         if(o.getPreferredName() == null) {

//         } else {
//           if(o.getLongName() == null)
//             items.addItem(new ValidationError(PropertyAccessor.getProperty("validation.concept.missing.longName", o.getPreferredName()), o));
//           if(o.getPreferredDefinition() == null) {
//             items.addItem(new ValidationError(PropertyAccessor.getProperty("validation.concept.missing.definition", o.getPreferredName()), o));
//           }
//           if(o.getDefinitionSource() == null)
//             items.addItem(new ValidationError(PropertyAccessor.getProperty("validation.concept.missing.source", o.getPreferredName()), o));
//         }
//       }
//     }

    return items;
  }

  private void checkConcepts(AdminComponent ac) {
    String[] conStr = ac.getPreferredName().split(":");
    for(String s : conStr) {
      Concept con = LookupUtil.lookupConcept(s);
      if(con.getLongName() == null)
        items.addItem(new ValidationError(PropertyAccessor.getProperty("validation.concept.missing.longName", con.getPreferredName()), ac));
      if(con.getPreferredDefinition() == null) {
        items.addItem(new ValidationError(PropertyAccessor.getProperty("validation.concept.missing.definition", con.getPreferredName()), ac));
      }
      if(con.getDefinitionSource() == null)
        items.addItem(new ValidationError(PropertyAccessor.getProperty("validation.concept.missing.source", con.getPreferredName()), ac));
    }
  }

}