package gov.nih.nci.ncicb.cadsr.loader.parser;

import gov.nih.nci.ncicb.cadsr.loader.event.LoaderHandler;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;


/**
 * <code>Parser</code> goes through a file and sends events to the handler.
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public interface Parser {
  /**
   * Sets the listener that will receiver LoaderEvents. Call this method first.
   *
   * @param listener a <code>LoaderListener</code> value
   */
  public void setEventHandler(LoaderHandler handler);

  /**
   * main parse method.
   *
   * @param filename a <code>String</code> value
   */
  public void parse(String filename);

  public void addProgressListener(ProgressListener listener);
}
