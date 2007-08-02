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
 * Used by UMLLoader's parser to indicate a new UML Class event.
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class NewValueDomainEvent extends NewConceptualEvent {

  private String name;
  private String datatype;
  private String type;
  private String cdId;
  private Float cdVersion;
  private String packageName;
  private String repTermId;
  private Float repTermVersion;

  public NewValueDomainEvent(String name) {
    this.name = name;
  }


  /**
   * Get the RepTermVersion value.
   * @return the RepTermVersion value.
   */
  public Float getRepTermVersion() {
    return repTermVersion;
  }

  /**
   * Set the RepTermVersion value.
   * @param newRepTermVersion The new RepTermVersion value.
   */
  public void setRepTermVersion(Float newRepTermVersion) {
    this.repTermVersion = newRepTermVersion;
  }

  
  /**
   * Get the RepId value.
   * @return the RepId value.
   */
  public String getRepTermId() {
    return repTermId;
  }

  /**
   * Set the RepId value.
   * @param newRepId The new RepId value.
   */
  public void setRepTermId(String newRepId) {
    this.repTermId = newRepId;
  }

  

  public String getName() {
    return name;
  }
 

  /**
   * Get the Datatype value.
   * @return the Datatype value.
   */
  public String getDatatype() {
    return datatype;
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
   * Set the Datatype value.
   * @param newDatatype The new Datatype value.
   */
  public void setDatatype(String newDatatype) {
    this.datatype = newDatatype;
  }


  /**
   * Get the Type value.
   * @return the Type value.
   */
  public String getType() {
    return type;
  }

  /**
   * Set the Type value.
   * @param newType The new Type value.
   */
  public void setType(String newType) {
    this.type = newType;
  }



  /**
   * Get the CdId value.
   * @return the CdId value.
   */
  public String getCdId() {
    return cdId;
  }

  /**
   * Set the CdId value.
   * @param newCdId The new CdId value.
   */
  public void setCdId(String newCdId) {
    this.cdId = newCdId;
  }


  /**
   * Get the CdVersion value.
   * @return the CdVersion value.
   */
  public Float getCdVersion() {
    return cdVersion;
  }

  /**
   * Set the CdVersion value.
   * @param newCdVersion The new CdVersion value.
   */
  public void setCdVersion(Float newCdVersion) {
    this.cdVersion = newCdVersion;
  }

  
  
}