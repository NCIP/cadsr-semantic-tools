package gov.nih.nci.ncicb.cadsr.loader.validator;

import java.util.List;

public interface Validator {

  public static final String SEVERITY_ERROR = "Error";
  public static final String SEVERITY_WARNING = "Warning";
  public static final String SEVERITY_FATAL = "Fatal";

  /**
   * returns a list of Validation errors.
   */
  public List validate();  
}