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

  /**
   * Allows to set a parameter to the persister.
   *
   * @param key a <code>String</code> value
   * @param value an <code>Object</code> value
   */
  public void setParameter(String key, Object value);
}
