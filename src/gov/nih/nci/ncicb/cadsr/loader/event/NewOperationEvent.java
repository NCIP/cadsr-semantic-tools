package gov.nih.nci.ncicb.cadsr.loader.event;

public class NewOperationEvent implements LoaderEvent {

  private String name;
  private String className;

  public NewOperationEvent(String name, String className) {
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