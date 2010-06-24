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
package gov.nih.nci.ncicb.cadsr.loader.validator;

import java.util.*;

import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;

public class UMLValidator implements Validator {

  private List<Validator> validators;

  public UMLValidator() {
  }
  
  public void addProgressListener(ProgressListener l) {
    for(Validator val : validators)
      val.addProgressListener(l);
  }
  
  /**
   * returns a list of Validation errors.
   */
  public ValidationItems validate() {
    
    Boolean noValidation = (Boolean)UserSelections.getInstance().getProperty("NO_VALIDATION");
    if(noValidation != null && noValidation.equals(true))
      return ValidationItems.getInstance();

    Map<String, Boolean> validatorChoices = (Map<String, Boolean>)UserSelections.getInstance()
      .getProperty("VALIDATORS_CHOICES");
    
    for(Validator val : validators) {
      String name = val.getClass().getName();
      if(validatorChoices != null && validatorChoices.get(name) != null && validatorChoices.get(name).equals(true)) {
        continue;
      }
      val.validate();
    }

    
    // !!TODO here we should add validators that we only want to run on subsequent calls 
    
    return ValidationItems.getInstance();
  }

  public void setValidators(List<Validator> v) {
    this.validators = v;
  }

  public  List<Validator> getValidators() {
    return validators;
  }

}