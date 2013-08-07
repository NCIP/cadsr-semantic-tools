/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.test;

import gov.nih.nci.cadsr.freestylesearch.util.*;

import javax.sql.DataSource;
import java.util.*;

import gov.nih.nci.cadsr.domain.*;

import gov.nih.nci.ncicb.cadsr.loader.util.BeansAccessor;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.InputStreamResource;

import java.sql.*;

public class FreestyleTest {

//   private DataSource datasource;

  public FreestyleTest() {
    System.out.println("FS Constructor");
  }

  public void test() {
//     BeanFactory factory = new XmlBeanFactory(new InputStreamResource(Thread.currentThread().getContextClassLoader().getResourceAsStream("spring-datasources.xml")));
//     DataSource ds = (DataSource)factory.getBean("dataSource");

    // use programmed defaults
    Search search = new Search();
    
    // Connect to the database - One of the setDataDescription(...) methods MUST be called prior to any other class method.
//     search.setDataDescription(ds);
    search.setDataDescription("http://freestyle-qa.nci.nih.gov");
    search.setCoreApiUrl("http://cabio-qa.nci.nih.gov/cacore32/http/remoteService");

//     System.out.println("\n findReturningDefault");
//     // Perform a search and get a default ASCII result
//     Vector<String> results1 = search.findReturningDefault("congestive heart failure");
//     for(String res: results1) {
//       System.out.println(res);
//     }
    
//     System.out.println("\n findReturningResultSet ");
//     // Perform a search and get a search result set
//     Vector<SearchResultSet> results2 = search.findReturningResultSet("congestive heart failure");

//     for(SearchResultSet res: results2) {
//       System.out.println(res.getType() + " " + res.getIdseq());
//     }
    
//     System.out.println("\n findReturningAdministeredComponent");
//     // Perform a search and get caCORE API AdministeredComponent results
//     Vector<AdministeredComponent> results3 = search.findReturningAdministeredComponent("congestive heart failure"); 
//     for(AdministeredComponent ac: results3) {
//       System.out.println(ac.getId() + " " + ac.getLongName() + " " + ac.getPreferredName());
//     }

//     System.out.println("\n Search DE for 'Person Name'");
//     search.restrictResultsByType(SearchAC.DE);
//     Vector<AdministeredComponent> results4 = search.findReturningAdministeredComponent("something"); 
//     for(AdministeredComponent ac: results4) {
//       System.out.println(ac.getId() + " " + ac.getLongName() + " " + ac.getPreferredName() + " WF status:" + ac.getWorkflowStatusName());
//     }

    try {
      Vector<SearchResults> srResult = search.findReturningSearchResults("Person");
      for(SearchResults sr : srResult) {
        System.out.println(sr.getLongName());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
  
//   public void setDatasource(DataSource ds) {
//     System.out.println("Set Datasource");

//     this.datasource = ds;
//   }

  public static void main(String[] args) {
    FreestyleTest fs = new FreestyleTest();
    fs.test();
  }
}

