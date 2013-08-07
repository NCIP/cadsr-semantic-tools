/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.util;

import gov.nih.nci.ncicb.cadsr.loader.event.LogListener;

import org.apache.log4j.spi.*;
import org.apache.log4j.*;

import java.util.List;

public class SiwAppender extends ConsoleAppender {

  List<LogListener> listeners;

  public void append(LoggingEvent loggingEvent) {
    for(LogListener l : listeners) 
      l.append(loggingEvent);
  }

  
  public void setLogListeners(List<LogListener> l) {
    this.listeners = l;
  }
  
  public boolean requiresLayout() {
    return true;
  }

}