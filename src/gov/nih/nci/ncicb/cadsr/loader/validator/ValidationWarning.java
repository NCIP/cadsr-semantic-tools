package gov.nih.nci.ncicb.cadsr.loader.validator;

/**
 * Contains message & cause for a Validation Warning. 
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 * @version 1.0
 */
public class ValidationWarning extends ValidationItem{

  public ValidationWarning(String message, Object rootCause) {
    super(message, rootCause);
  }

}