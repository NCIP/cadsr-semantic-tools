package gov.nih.nci.ncicb.cadsr.loader.persister;

/**
 * Persisters need to implement this interface. <br/>Persisters read objects from the elements list and persist them.
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public interface Persister {

  /**
   * Main persisting method
   *
   * @exception PersisterException if an error occurs
   */
  public void persist() throws PersisterException ;

}
