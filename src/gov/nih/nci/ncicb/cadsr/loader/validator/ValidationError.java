package gov.nih.nci.ncicb.cadsr.loader.validator;

public class ValidationError {

  private String severity;
  private String message;

  public ValidationError(String severity, String message) {
    this.severity = severity;
    this.message = message;
  }

  /**
   * Get the Severity value.
   * @return the Severity value.
   */
  public String getSeverity() {
    return severity;
  }

  /**
   * Get the Message value.
   * @return the Message value.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Set the Message value.
   * @param newMessage The new Message value.
   */
  void setMessage(String newMessage) {
    this.message = newMessage;
  }

  /**
   * Set the Severity value.
   * @param newSeverity The new Severity value.
   */
  void setSeverity(String newSeverity) {
    this.severity = newSeverity;
  }

  


}