package gov.nih.nci.ncicb.cadsr.loader.event;

public class ReviewEvent {

  private Object userObject;
  private boolean reviewed;

  /**
   * Get the UserObject value.
   * @return the UserObject value.
   */
  public Object getUserObject() {
    return userObject;
  }


  /**
   * Get the IsReviewed value.
   * @return the IsReviewed value.
   */
  public boolean isReviewed() {
    return reviewed;
  }

  /**
   * Set the IsReviewed value.
   * @param newIsReviewed The new IsReviewed value.
   */
  public void setReviewed(boolean reviewed) {
    this.reviewed = reviewed;
  }

  

  /**
   * Set the UserObject value.
   * @param newUserObject The new UserObject value.
   */
  public void setUserObject(Object newUserObject) {
    this.userObject = newUserObject;
  }

  

}