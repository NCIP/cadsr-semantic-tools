package gov.nih.nci.ncicb.cadsr.loader.event;

public class NewAttributeEvent implements LoaderEvent {

  private String name;
  private String className;

  public NewAttributeEvent(String name, String className) {
    this.name = name;
    this.className = className;
  }

  public String getName() {
    return name;
  }
  
  public String getClassName() {
    return className;
  }

}