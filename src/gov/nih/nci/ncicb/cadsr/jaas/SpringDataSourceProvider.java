package gov.nih.nci.ncicb.cadsr.jaas;

import javax.sql.DataSource;

import gov.nih.nci.ncicb.cadsr.spring.ApplicationContextFactory;

/**
 * Appropriate DataSourceProvider for SpringContainer. 
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class SpringDataSourceProvider implements DataSourceProvider {

  /**
   *
   * @param name The name of the dataSource defined with Spring
   * @return a <code>DataSource</code> 
   */
  public DataSource getDataSource(String name) {
    return (DataSource) ApplicationContextFactory.getApplicationContext()
      .getBean(name);
  }

}