package gov.nih.nci.ncicb.cadsr.semconn;

import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;

import java.util.Map;

public interface Subject {
    public void addProgressListener(ProgressListener pl);
    public void removeProgressListener(ProgressListener pl);
    public Map getProgressListeners();
    public void notify(ProgressEvent event );    
}
