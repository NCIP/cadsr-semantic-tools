package gov.nih.nci.ncicb.cadsr.loader.event;

/**
 * Event indicating a new Association was found by the parser.
 * 
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class NewAssociationEvent implements LoaderEvent {

  private String aClassName;
  private String bClassName;
  private String roleName;
  private int aHighCardinality;
  private int bHighCardinality;
  private int aLowCardinality;
  private int bLowCardinality;
  private String aRole;
  private String bRole;
  private String direction;
  private String type;

  /**
   * Get the AClassName value.
   * @return the AClassName value.
   */
  public String getAClassName() {
    return aClassName;
  }
  
  /**
   * Get the BClassName value.
   * @return the BClassName value.
   */
  public String getBClassName() {
    return bClassName;
  }


  /**
   * Get the RoleName value.
   * @return the RoleName value.
   */
  public String getRoleName() {
    return roleName;
  }


  /**
   * Get the ALowCardinality value.
   * @return the ALowCardinality value.
   */
  public int getALowCardinality() {
    return aLowCardinality;
  }

  /**
   * Get the BLowCardinality value.
   * @return the BLowCardinality value.
   */
  public int getBLowCardinality() {
    return bLowCardinality;
  }

  /**
   * Get the AHighCardinality value.
   * @return the AHighCardinality value.
   */
  public int getAHighCardinality() {
    return aHighCardinality;
  }

  /**
   * Get the BHighCardinality value.
   * @return the BHighCardinality value.
   */
  public int getBHighCardinality() {
    return bHighCardinality;
  }

  /**
   * Get the ARole value.
   * @return the ARole value.
   */
  public String getARole() {
    return aRole;
  }

  /**
   * Get the BRole value.
   * @return the BRole value.
   */
  public String getBRole() {
    return bRole;
  }


  /**
   * Get the Direction value.
   * @return the Direction value.
   */
  public String getDirection() {
    return direction;
  }

  /**
   * Set the Direction value.
   * @param newDirection The new Direction value.
   */
  public void setDirection(String newDirection) {
    this.direction = newDirection;
  }

  
  /**
   * Set the BRole value.
   * @param newBRole The new BRole value.
   */
  public void setBRole(String newBRole) {
    this.bRole = newBRole;
  }

  
  /**
   * Set the ARole value.
   * @param newARole The new ARole value.
   */
  public void setARole(String newARole) {
    this.aRole = newARole;
  }

  

  /**
   * Set the BLowCardinality value.
   * @param newBLowCardinality The new BLowCardinality value.
   */
  public void setBLowCardinality(int newBLowCardinality) {
    this.bLowCardinality = newBLowCardinality;
  }

  
  /**
   * Set the ALowCardinality value.
   * @param newALowCardinality The new ALowCardinality value.
   */
  public void setALowCardinality(int newALowCardinality) {
    this.aLowCardinality = newALowCardinality;
  }

  /**
   * Set the BHighCardinality value.
   * @param newBHighCardinality The new BHighCardinality value.
   */
  public void setBHighCardinality(int newBHighCardinality) {
    this.bHighCardinality = newBHighCardinality;
  }

  
  /**
   * Set the AHighCardinality value.
   * @param newAHighCardinality The new AHighCardinality value.
   */
  public void setAHighCardinality(int newAHighCardinality) {
    this.aHighCardinality = newAHighCardinality;
  }

  

  /**
   * Set the RoleName value.
   * @param newRoleName The new RoleName value.
   */
  public void setRoleName(String newRoleName) {
    this.roleName = newRoleName;
  }

  /**
   * Set the BClassName value.
   * @param newBClassName The new BClassName value.
   */
  public void setBClassName(String newBClassName) {
    this.bClassName = newBClassName;
  }
  
  /**
   * Set the AClassName value.
   * @param newAClassName The new AClassName value.
   */
  public void setAClassName(String newAClassName) {
    this.aClassName = newAClassName;
  }
  

}