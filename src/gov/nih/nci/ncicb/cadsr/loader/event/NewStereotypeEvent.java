package gov.nih.nci.ncicb.cadsr.loader.event;

public class NewStereotypeEvent implements LoaderEvent {

  private String name;

  public NewStereotypeEvent(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
  
}