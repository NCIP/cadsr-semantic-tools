package gov.nih.nci.ncicb.cadsr.jaas;

import javax.sql.DataSource;

/**
 * Interface used by DBModule. An implementation if this class is passed as a parameter in the jaas config. Login module calls back this method to obtain a DataSource.
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public interface DataSourceProvider  {

  public abstract DataSource getDataSource(String name);

}