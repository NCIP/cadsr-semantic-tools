package gov.nih.nci.ncicb.cadsr.loader;

import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;

/**
 * <code>UMLLoader</code> starts a separate thread for initializing the environement, while users enter their username and password. 
 *
 */
public class InitClass implements Runnable {
//   Object parent;
  boolean done = false;
  
//   InitClass(Object parent) {
//     this.parent = parent;
//   }
  
  public void run() {
    DAOAccessor p = new DAOAccessor();
    synchronized (this) {
      done = true;
      notifyAll();
    }
  }
  
  public boolean isDone() {
    return done;
  }
  
}
