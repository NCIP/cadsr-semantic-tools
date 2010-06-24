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
 * Sent when an element's review state has changed
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class ReviewEvent {

  private Object userObject;
  private boolean reviewed;

  /**
   * Describe type here.
   */
  private ReviewEventType type;

  /**
   * Get the UserObject value.
   * @return the UserObject value.
   */
  public Object getUserObject() {
    return userObject;
  }

  /**
   * Get the IsReviewed value.
   * @return the IsReviewed value.
   */
  public boolean isReviewed() {
    return reviewed;
  }

  /**
   * Get the <code>Type</code> value.
   *
   * @return a <code>ReviewEventType</code> value
   */
  public ReviewEventType getType() {
    return type;
  }

  /**
   * Set the <code>Type</code> value.
   *
   * @param newType The new Type value.
   */
  public void setType(final ReviewEventType newType) {
    this.type = newType;
  }

  /**
   * Set the IsReviewed value.
   * @param newIsReviewed The new IsReviewed value.
   */
  public void setReviewed(boolean reviewed) {
    this.reviewed = reviewed;
  }

  /**
   * Set the UserObject value.
   * @param newUserObject The new UserObject value.
   */
  public void setUserObject(Object newUserObject) {
    this.userObject = newUserObject;
  }

  

}