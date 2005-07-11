package gov.nih.nci.ncicb.cadsr.loader.validator;

/**
 * Contains cause and message for a Validation Fatal. 
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 * @version 1.0
 */
public class ValidationFatal extends ValidationItem {

  public ValidationFatal(String message, Object rootCause) {
    super(message, rootCause);
  }

}