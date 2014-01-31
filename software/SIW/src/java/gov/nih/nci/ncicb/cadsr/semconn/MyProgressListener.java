package gov.nih.nci.ncicb.cadsr.semconn;

import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;

public class MyProgressListener implements ProgressListener{
    public MyProgressListener() {
    }
    
    public void newProgressEvent(ProgressEvent evt){
        System.out.println(evt.getMessage() + evt.getStatus() +"/" + evt.getGoal());
    }
    
    
}
