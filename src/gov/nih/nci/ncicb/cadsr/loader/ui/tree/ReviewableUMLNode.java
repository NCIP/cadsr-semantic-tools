package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

public interface ReviewableUMLNode 
{
  /**
   * sets reviewed to currentStatus
   */
  public void setReviewed(boolean currentStatus);

  /**
   * @return reviewed
   */
  public boolean isReviewed();
  
}