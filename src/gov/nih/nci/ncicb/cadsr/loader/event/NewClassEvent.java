package gov.nih.nci.ncicb.cadsr.loader.event;

public class NewClassEvent implements LoaderEvent {

  private String name;
  private String conceptCode;
  private String packageName;

  public NewClassEvent(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  /**
   * Get the ConceptCode value.
   * @return the ConceptCode value.
   */
  public String getConceptCode() {
    return conceptCode;
  }

  /**
   * Get the PackageName value.
   * @return the PackageName value.
   */
  public String getPackageName() {
    return packageName;
  }

  /**
   * Set the PackageName value.
   * @param newPackageName The new PackageName value.
   */
  public void setPackageName(String newPackageName) {
    this.packageName = newPackageName;
  }

  
  /**
   * Set the ConceptCode value.
   * @param newConceptCode The new ConceptCode value.
   */
  public void setConceptCode(String newConceptCode) {
    this.conceptCode = newConceptCode;
  }
  
}