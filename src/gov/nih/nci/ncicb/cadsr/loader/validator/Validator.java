package gov.nih.nci.ncicb.cadsr.loader.validator;

import java.util.List;

/**
 * Validators need to implement this interface. Validators must be run before persistance occurs. 
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public interface Validator {

  /*
   * These do not belong here and should be removed.
  */
  public static final String SEVERITY_ERROR = "Error";
  public static final String SEVERITY_WARNING = "Warning";
  public static final String SEVERITY_FATAL = "Fatal";

  /**
   * @return a list of Validation errors.
   */
  public List validate();  
}