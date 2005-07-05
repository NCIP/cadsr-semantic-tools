package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

/**
 * Dummy class until we plug in UML Loader
 */
public class ProgressSimulator {

  private ProgressListener listener;
  
  private String[] fakeProgress = 
  {
    "Persisting Packages", "2",
    "Persisting Concepts", "25",
    "Persisting Classes", "8",
    "Persisting Attributes", "10",
    "Persisting Data Element Concepts", "50",
    "Persisting Data Elements", "50",
  };

  public void addProgressListener(ProgressListener l) {
    this.listener = l;
  }

  public void run() {

    int counter = 0;
    int status = 0;
    while(counter < fakeProgress.length) {
      ProgressEvent evt = new ProgressEvent();
      evt.setMessage(fakeProgress[counter++]);
      evt.setStatus(status);
      listener.newProgressEvent(evt);

      int n = new Integer(fakeProgress[counter++]).intValue();
      for(int i = 0; i<n; i++) {
        ProgressEvent evt2 = new ProgressEvent();
        evt2.setStatus(++status);
        listener.newProgressEvent(evt2);
        try
        {
          Thread.currentThread().sleep(20);
        }
        catch (InterruptedException e)
        {
          
        }
      }
      
    }

  }

}
