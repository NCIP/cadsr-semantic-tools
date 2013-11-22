/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ext;

import gov.nih.nci.cadsr.freestylesearch.util.Search;
import gov.nih.nci.cadsr.freestylesearch.util.SearchAC;
import gov.nih.nci.cadsr.freestylesearch.util.SearchResults;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;


public class FreestyleModule {

  Search search = new Search();

  private static Logger logger = Logger.getLogger(FreestyleModule.class.getName());

  public List<gov.nih.nci.cadsr.domain.DataElement> findDataElements(String searchString) {
    search.restrictResultsByType(SearchAC.DE);
    Vector<gov.nih.nci.cadsr.domain.AdministeredComponent> acResult = search.findReturningAdministeredComponent(searchString);
    
    List<gov.nih.nci.cadsr.domain.DataElement> result = new ArrayList<gov.nih.nci.cadsr.domain.DataElement>();
    for(gov.nih.nci.cadsr.domain.AdministeredComponent ac : acResult)
      result.add((gov.nih.nci.cadsr.domain.DataElement)ac);

    return new ArrayList(CadsrTransformer.deListPublicToPrivate(result));
  }
  
  public List<SearchResults> findSearchResults(String searchString) 
  {
    return findSearchResults(searchString, false);
  }

  public List<SearchResults> findSearchResults(String searchString, boolean excludeRetired) 
  {
    long l = new java.util.Date().getTime();

    if(excludeRetired) {
      System.out.println("excluding WF Retired");
      search.restrictResultsByWorkflowNotRetired();
    }
    else {
      search.resetResultsByWorkflowNotRetired();
    }

    search.restrictResultsByType(SearchAC.DE);
    search.setResultsLimit(2000);

    Vector<SearchResults> srResult = search.findReturningSearchResults(searchString);
    
    int t = (int)((new java.util.Date().getTime() - l)/1000);
    if(t > 20)
      logger.debug("It took " + t + "s to run this query: " + searchString);
    
    return srResult;
  }


//   public void setDatasource(DataSource ds) {
//     search.setDataDescription(ds);
//   }
  
  public void setDatasourceLocation(String location) 
  {
    search.setDataDescription(location);
  }
  
  public void setPublicApi(String publicApi) 
  {
    search.setCoreApiUrl(publicApi);
  }

}