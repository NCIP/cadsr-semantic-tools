package gov.nih.nci.ncicb.cadsr.loader.validator;

import java.util.List;

public interface Validator {

  /**
   * returns a list of Validation errors.
   */
  public List<ValidationError> validate();  
}