package gov.nih.nci.ncicb.cadsr.loader.event;

public class NewOperationEvent implements LoaderEvent {

  private String name;
  private String className;

  public NewOperationEvent(String name) {
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