package gov.nih.nci.ncicb.cadsr.loader.validator;

import java.util.List;

/**
 * Validators need to implement this interface. Validators must be run before persistance occurs. 
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public interface Validator {

  /**
   * @return a list of Validation errors.
   */
  public ValidationItems validate();  

}