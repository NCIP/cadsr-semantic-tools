package gov.nih.nci.ncicb.cadsr.loader.event;

/**
 * Used by UMLLoader's parser to indicate a new UML Class event.
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class NewClassEvent extends NewConceptualEvent {

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