package gov.nih.nci.ncicb.cadsr.loader.event;

public class NewPackageEvent implements LoaderEvent {

  private String name;

  public NewPackageEvent(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
  
}