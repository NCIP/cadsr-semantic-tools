package gov.nih.nci.ncicb.cadsr.loader.event;

public class NewAttributeEvent implements LoaderEvent {

  private String name;
  private String className;

  public NewAttributeEvent(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
  
  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

}