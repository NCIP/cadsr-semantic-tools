package gov.nih.nci.ncicb.cadsr.loader.event;

public class NewClassEvent implements LoaderEvent {

  private String name;

  public NewClassEvent(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
  
}