package gov.nih.nci.ncicb.cadsr.loader.event;

public class NewInterfaceEvent implements LoaderEvent {

  private String name;

  public NewInterfaceEvent(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
  
}