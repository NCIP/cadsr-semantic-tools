/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

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

import java.util.List;
import java.util.ArrayList;

/**
 * Events with a concept should extend this event
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class NewConceptualEvent implements LoaderEvent {


  private String description;
  private boolean reviewed = false;

  private List<NewConceptEvent> concepts = new ArrayList<NewConceptEvent>();


  private String persistenceId;

  private Float persistenceVersion;

  /**
   * Indicates that the object already is persisted with this version number. 
   * May be null if this object is new.
   * @return the PersistenceVersion value.
   */
  public Float getPersistenceVersion() {
    return persistenceVersion;
  }

  /**
   * Set the PersistenceVersion value.
   * @param newPersistenceVersion The new PersistenceVersion value.
   */
  public void setPersistenceVersion(Float newPersistenceVersion) {
    this.persistenceVersion = newPersistenceVersion;
  }

  

  /**
   * ID representing the object if it already exists. 
   * May be null if this object is new.
   * @return the PersistenceId value.
   */
  public String getPersistenceId() {
    return persistenceId;
  }

  /**
   * Set the PersistenceId value.
   * @param newPersistenceId The new PersistenceId value.
   */
  public void setPersistenceId(String newPersistenceId) {
    this.persistenceId = newPersistenceId;
  }

  

  /**
   * Get the Description value.
   * @return the Description value.
   */
  public String getDescription() {
    return description;
  }

  public boolean isReviewed() 
  {
    return reviewed;
  }
  
  public void setReviewed(boolean b) 
  {
    reviewed = b;
  }
  
  /**
   * Describe <code>getConcepts</code> method here.
   *
   * @return a <code>List</code> of NewConceptEvent
   */
  public List<NewConceptEvent> getConcepts() {
    return concepts;
  }

  public void addConcept(NewConceptEvent concept) {
    concepts.add(concept);
  }
  
  /**
   * Set the Description value.
   * @param newDescription The new Description value.
   */
  public void setDescription(String newDescription) {
    this.description = newDescription;
  }
  

}