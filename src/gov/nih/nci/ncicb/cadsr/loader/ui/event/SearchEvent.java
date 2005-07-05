package gov.nih.nci.ncicb.cadsr.loader.ui.event;

public class SearchEvent 
{
  String searchString;
  boolean searchFromBottom;
  boolean searchByLongName;
  
  public SearchEvent(String find, boolean searchFromBottom, boolean searchByLongName)
  {
    searchString = find;
    this.searchFromBottom = searchFromBottom;
    this.searchByLongName = searchByLongName;
  }
  
  public String getSearchString() 
  {
    return searchString;
  }
  
  public boolean getSearchFromBottom() 
  {
    return searchFromBottom;
  }
  
  public boolean getSearchByLongName() 
  {
    return searchByLongName;
  }
}