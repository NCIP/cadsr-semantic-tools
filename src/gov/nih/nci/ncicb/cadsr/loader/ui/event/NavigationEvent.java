package gov.nih.nci.ncicb.cadsr.loader.ui.event;

public class NavigationEvent {

  public static final int UNDEFINED = 0;
  public static final int NAVIGATE_NEXT = 1;
  public static final int NAVIGATE_PREVIOUS = 2;

  private int type = 0;

  public NavigationEvent(int type) {
    this.type = type;
  }

  public int getType() 
  {
    return type;
  }

}
