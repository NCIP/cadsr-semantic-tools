package gov.nih.nci.ncicb.cadsr.loader.event;

/**
 * Used by UML Loader to indicate a new inheritance event.
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class NewGeneralizationEvent implements LoaderEvent {

  private String parentClassName;
  private String childClassName;

  /**
   * Get the ParentClassName value.
   * @return the ParentClassName value.
   */
  public String getParentClassName() {
    return parentClassName;
  }

  /**
   * Get the ChildClassName value.
   * @return the ChildClassName value.
   */
  public String getChildClassName() {
    return childClassName;
  }

  /**
   * Set the ChildClassName value.
   * @param newChildClassName The new ChildClassName value.
   */
  public void setChildClassName(String newChildClassName) {
    this.childClassName = newChildClassName;
  }

  
  /**
   * Set the ParentClassName value.
   * @param newParentClassName The new ParentClassName value.
   */
  public void setParentClassName(String newParentClassName) {
    this.parentClassName = newParentClassName;
  }

  

}