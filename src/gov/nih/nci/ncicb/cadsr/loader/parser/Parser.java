package gov.nih.nci.ncicb.cadsr.loader.parser;

import gov.nih.nci.ncicb.cadsr.loader.event.LoaderListener;


/**
 * <code>Parser</code> goes through a file and sends events to the listener.
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public interface Parser {
  /**
   * Sets the listener that will receiver LoaderEvents. Call this method first.
   *
   * @param listener a <code>LoaderListener</code> value
   */
  public void setListener(LoaderListener listener);

  /**
   * main parse method.
   *
   * @param filename a <code>String</code> value
   */
  public void parse(String filename);
}
