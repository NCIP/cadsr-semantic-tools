package gov.nih.nci.ncicb.cadsr.loader.event;

/**
 * Not Used.
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
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