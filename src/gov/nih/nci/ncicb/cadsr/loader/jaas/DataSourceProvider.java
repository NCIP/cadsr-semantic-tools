package gov.nih.nci.ncicb.cadsr.loader.jaas;

import javax.sql.DataSource;

public interface DataSourceProvider  {

  public abstract DataSource getDataSource(String name);

}