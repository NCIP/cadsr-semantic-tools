package gov.nih.nci.ncicb.cadsr.loader.jaas;

import javax.sql.DataSource;

import gov.nih.nci.ncicb.cadsr.spring.ApplicationContextFactory;

public class SpringDataSourceProvider implements DataSourceProvider {

  public DataSource getDataSource(String name) {
    return (DataSource) ApplicationContextFactory.getApplicationContext()
      .getBean(name);
  }

}