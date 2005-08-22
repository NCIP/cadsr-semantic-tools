package gov.nih.nci.ncicb.cadsr.loader.validator;

import java.util.*;

import gov.nih.nci.ncicb.cadsr.domain.*;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.util.RunMode;

public class UMLValidator implements Validator {

  private ElementsLists elements = ElementsLists.getInstance();
  private List validators;

  public UMLValidator() {
    validators = new ArrayList();
    validators.add(new ConceptCodeValidator(elements)); 
    validators.add(new AssociationValidator(elements));

    RunMode mode = (RunMode)(UserSelections.getInstance().getProperty("MODE"));

    if(mode.equals(RunMode.Reviewer))
      validators.add(new DatatypeValidator(elements));
 }

  /**
   * returns a list of Validation errors.
   */
  public ValidationItems validate() {
    for(Iterator it = validators.iterator(); it.hasNext(); ) {
      Validator val = (Validator)it.next();
      val.validate();
    }

    return ValidationItems.getInstance();
  }

}