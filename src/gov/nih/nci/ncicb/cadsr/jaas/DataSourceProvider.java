package gov.nih.nci.ncicb.cadsr.jaas;

import javax.sql.DataSource;

public interface DataSourceProvider  {

  public abstract DataSource getDataSource(String name);

}