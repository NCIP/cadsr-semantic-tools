package gov.nih.nci.ncicb.cadsr.loader.persister;

public interface Persister {

  public void persist() throws PersisterException ;

  public void setParameter(String key, Object value);
}
