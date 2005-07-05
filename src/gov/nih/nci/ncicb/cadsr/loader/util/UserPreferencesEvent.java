package gov.nih.nci.ncicb.cadsr.loader.util;

public class UserPreferencesEvent 
{
  public static final int VIEW_ASSOCIATION = 1;
  public static final String IN_CLASS_ASSOCIATION = "true";
  public static final String NOT_IN_CLASS_ASSOCIATION = "false";
  public static final int UML_DESCRIPTION = 2;
  public static final int MODE_SELECTION = 3;
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