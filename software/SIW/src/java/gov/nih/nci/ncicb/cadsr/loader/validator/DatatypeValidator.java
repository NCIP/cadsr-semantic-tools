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
package gov.nih.nci.ncicb.cadsr.loader.validator;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.util.DatatypeMapping;

import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;

import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.util.AttributeDatatypePair;
import gov.nih.nci.ncicb.cadsr.loader.util.DEMappingUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.RunMode;

import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;

import java.util.List;

import org.apache.log4j.Logger;

public class DatatypeValidator implements Validator
{
  private ElementsLists elements = ElementsLists.getInstance();
  
  private ValidationItems items = ValidationItems.getInstance();
  private Logger logger = Logger.getLogger(DatatypeValidator.class.getName());
  
  public DatatypeValidator()
  {
  }

  public void addProgressListener(ProgressListener l) {

  }

  /**
   * @return a list of Validation errors.
   */
  public ValidationItems validate()
  {
    UserSelections userSelections = UserSelections.getInstance();
    RunMode mode = (RunMode)(userSelections.getProperty("MODE"));
    if(!mode.equals(RunMode.Reviewer) && !mode.equals(RunMode.UnannotatedXmi))
      return items;

    List<DataElement> des = elements.getElements(DomainObjectFactory.newDataElement());
    if(des != null)
      for(DataElement de : des) 
      {
        // dont validate if mapped to existing DE.
        if(de.getPublicId() != null && de.getVersion() != null)
          continue;

        // dont validate if mapped to existing VD
        if(de.getValueDomain() != null &&
           de.getValueDomain().getPublicId() != null &&
           de.getValueDomain().getVersion() != null)
          continue;

        // Don't validate is mapped to LVD
        if(DEMappingUtil.isMappedToLVD(de))
            continue;

//        String datatype = de.getValueDomain().getLongName();
         List<AttributeDatatypePair> attTypesPairs = elements.getElements(new AttributeDatatypePair("", ""));
         String datatype = null;
         String attributeName = LookupUtil.lookupFullName(de);
         for(AttributeDatatypePair pair : attTypesPairs) {
           if(pair.getAttributeName().equals(attributeName)) {
             datatype = pair.getDatatype();
           }
         }
        
        if(StringUtil.isEmpty(datatype)) {
          logger.error("Datatype is null for attribute: " + LookupUtil.lookupFullName(de));
        }

        // Is the datatype a simple datatype?
        if(DatatypeMapping.getKeys().contains(datatype)) 
          continue;
        
        // If mapping this DE to a VD Name, check that the VD
        // is in the model
//        ValueDomain vd = LookupUtil.lookupValueDomain(de.getValueDomain().getLongName());
//        if(vd != null)
//          continue;
       
        items.addItem(new ValidationError
             (PropertyAccessor.getProperty
              ("validation.type.invalid",
               new String[] {datatype, 
                             de.getDataElementConcept().getLongName()})
              ,de));
        
      }
    
    return items;
    
  }
}