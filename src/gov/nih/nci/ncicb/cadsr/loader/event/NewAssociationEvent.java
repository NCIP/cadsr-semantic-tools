package gov.nih.nci.ncicb.cadsr.loader.event;

public class NewAssociationEvent implements LoaderEvent {

  private String aClassName;
  private String bClassName;
  private String roleName;
  private String aCardinality;
  private String bCardinality;
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
   * Get the ACardinality value.
   * @return the ACardinality value.
   */
  public String getACardinality() {
    return aCardinality;
  }

  /**
   * Get the BCardinality value.
   * @return the BCardinality value.
   */
  public String getBCardinality() {
    return bCardinality;
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
   * Set the BCardinality value.
   * @param newBCardinality The new BCardinality value.
   */
  public void setBCardinality(String newBCardinality) {
    this.bCardinality = newBCardinality;
  }

  
  /**
   * Set the ACardinality value.
   * @param newACardinality The new ACardinality value.
   */
  public void setACardinality(String newACardinality) {
    this.aCardinality = newACardinality;
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