/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.semconn;

import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

public class SubjectClass implements Subject{
    private Map progressListeners = new HashMap();
    private static Logger log = Logger.getLogger(SubjectClass.class.getName());

    public SubjectClass() {
    }
    
    public void addProgressListener(ProgressListener pl){
        progressListeners.put(pl, pl);
    }
    
    public void addProgressListeners(Map pls){
        progressListeners.putAll(pls);
    }
    
    public Map getProgressListeners(){
        return progressListeners;
    }
    public void removeProgressListener(ProgressListener pl){
        progressListeners.remove(pl);
    }
    
    public void notify(ProgressEvent event){
        if (progressListeners==null || progressListeners.isEmpty()){
            log.warn("No progress listeners registered. Nothing to notify");
            return;
        }
        
        Iterator it = progressListeners.values().iterator();
        while (it.hasNext()){
            ProgressListener pl = (ProgressListener)it.next();
            pl.newProgressEvent(event);
        }
    }
    
    public void notifyEventDone(String msg){
        ProgressEvent event = new ProgressEvent();
        event.setMessage(msg);
        event.setStatus(100);
        event.setGoal(event.getStatus());
        notify(event);
    }
        
}
