package gov.nih.nci.ncicb.cadsr.loader.event;

import org.apache.log4j.spi.LoggingEvent;


public interface LogListener {
  public void append(LoggingEvent loggingEvent);
}