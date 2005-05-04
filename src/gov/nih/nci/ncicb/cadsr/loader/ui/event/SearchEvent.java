package gov.nih.nci.ncicb.cadsr.loader.ui.event;

public class SearchEvent 
{
  String searchString;
  boolean searchFromBottom;
  
  public SearchEvent(String find, boolean searchFromBottom)
  {
    searchString = find;
    this.searchFromBottom = searchFromBottom;
  }
  
  public String getSearchString() 
  {
    return searchString;
  }
  
  public boolean getSearchFromBottom() 
  {
    return searchFromBottom;
  }
}