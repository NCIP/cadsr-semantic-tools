package gov.nih.nci.ncicb.cadsr.loader.event;

public class NewClassEvent extends ConceptualEvent {

  private String name;
  private String packageName;

  public NewClassEvent(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
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

  
  
}