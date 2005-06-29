package gov.nih.nci.ncicb.cadsr.loader;
import gov.nih.nci.ncicb.cadsr.loader.event.ReviewEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ReviewListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.AbstractUMLNode;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.ReviewableUMLNode;
import java.util.HashMap;

/**
 * ReviewTracker keeps track of objects and their Review Status
 * by storing them in a HashMap
 */
public class ReviewTracker implements ReviewListener
{
  private HashMap<String, Boolean> reviewed = new HashMap(); 

  private static ReviewTracker instance = new ReviewTracker();
  private ReviewTracker() {

  }
  public static ReviewTracker getInstance() {
    return instance;
  }
  
  public Boolean get(String key) 
  {   
    return reviewed.get(key);
  }

  public void put(String key, boolean value) 
  {
    reviewed.put(key, value);
  }
  
  public void reviewChanged(ReviewEvent event) 
  {
    AbstractUMLNode absNode = (AbstractUMLNode) event.getUserObject();
    if(reviewed.get(absNode.getFullPath()) != null) 
    {
      reviewed.remove(absNode.getFullPath());
      this.put(absNode.getFullPath(), event.isReviewed());
    }
    else
      this.put(absNode.getFullPath(), event.isReviewed());
  }

}