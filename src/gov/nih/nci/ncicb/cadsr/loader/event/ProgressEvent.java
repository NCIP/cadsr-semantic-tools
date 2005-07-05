package gov.nih.nci.ncicb.cadsr.loader.event;

public class ProgressEvent {

  private String message;
  private int goal;
  private int status;

  /**
   * Get the Message value.
   * @return the Message value.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Get the Goal value.
   * @return the Goal value.
   */
  public int getGoal() {
    return goal;
  }

  /**
   * Get the Status value.
   * @return the Status value.
   */
  public int getStatus() {
    return status;
  }

  /**
   * Set the Status value.
   * @param newStatus The new Status value.
   */
  public void setStatus(int newStatus) {
    this.status = newStatus;
  }

  
  /**
   * Set the Goal value.
   * @param newGoal The new Goal value.
   */
  public void setGoal(int newGoal) {
    this.goal = newGoal;
  }

  
  /**
   * Set the Message value.
   * @param newMessage The new Message value.
   */
  public void setMessage(String newMessage) {
    this.message = newMessage;
  }

  

}