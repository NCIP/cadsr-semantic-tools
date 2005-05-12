package gov.nih.nci.ncicb.cadsr.loader.ui.event;

public class UserPreferencesEvent 
{
  public static final int VIEW_ASSOCIATION = 1;
  public static final String IN_CLASS_ASSOCIATION = "true";
  public static final String NOT_IN_CLASS_ASSOCIATION = "false";
  private int typeOfEvent;
  private String value;
  
  public UserPreferencesEvent(int typeOfEvent, String value)
  {
    this.typeOfEvent = typeOfEvent;
    this.value = value;
  }
  
  public int getTypeOfEvent() 
  {
    return typeOfEvent;
  }
  
  public String getValue() 
  {
    return value;
  }
  
  
}