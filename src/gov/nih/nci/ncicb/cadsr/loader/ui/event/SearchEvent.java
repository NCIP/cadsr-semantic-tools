package gov.nih.nci.ncicb.cadsr.loader.ui.event;

public class SearchEvent 
{
  String searchString;
  
  public SearchEvent(String find)
  {
    searchString = find;
  }
  
  public String getSearchString() 
  {
    return searchString;
  }
}