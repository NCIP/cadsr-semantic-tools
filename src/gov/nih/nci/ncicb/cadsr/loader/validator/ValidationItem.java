package gov.nih.nci.ncicb.cadsr.loader.validator;

/**
 * 
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 * @version 1.0
 */
public abstract class ValidationItem {

  private String message;
  private Object rootCause;

  public ValidationItem(String message, Object rootCause) {
    this.message = message;
    this.rootCause = rootCause;
  }

  /**
   * Get the Message value.
   * @return the Message value.
   */
  public String getMessage() {
    return message;
  }
  
  public Object getRootCause() {
    return rootCause;
  }

}