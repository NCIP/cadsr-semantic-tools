/*
 * Copyright 2000-2003 Oracle, Inc. This software was developed in conjunction with the National Cancer Institute, and so to the extent government employees are co-authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105.
 *
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the disclaimer of Article 3, below. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 2. The end-user documentation included with the redistribution, if any, must include the following acknowledgment:
 *
 * "This product includes software developed by Oracle, Inc. and the National Cancer Institute."
 *
 * If no such end-user documentation is to be included, this acknowledgment shall appear in the software itself, wherever such third-party acknowledgments normally appear.
 *
 * 3. The names "The National Cancer Institute", "NCI" and "Oracle" must not be used to endorse or promote products derived from this software.
 *
 * 4. This license does not authorize the incorporation of this software into any proprietary programs. This license does not authorize the recipient to use any trademarks owned by either NCI or Oracle, Inc.
 *
 * 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, ORACLE, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 */
package gov.nih.nci.ncicb.cadsr.loader.event;

/**
 * Event indicating a new Association was found by the parser.
 * 
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
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