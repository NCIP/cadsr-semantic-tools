package gov.nih.nci.ncicb.cadsr.loader.event;

public class NewAttributeEvent extends ConceptualEvent {

  private String name;
  private String className;
  private String type;

  public NewAttributeEvent(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
  
  public String getClassName() {
    return className;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setClassName(String className) {
    this.className = className;
  }

}