package gov.nih.nci.ncicb.cadsr.loader.event;

public class NewAttributeEvent implements LoaderEvent {

  private String name;
  private String className;
  private String type;
  private String conceptCode;

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

  /**
   * Get the ConceptCode value.
   * @return the ConceptCode value.
   */
  public String getConceptCode() {
    return conceptCode;
  }

  /**
   * Set the ConceptCode value.
   * @param newConceptCode The new ConceptCode value.
   */
  public void setConceptCode(String newConceptCode) {
    this.conceptCode = newConceptCode;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setClassName(String className) {
    this.className = className;
  }

}