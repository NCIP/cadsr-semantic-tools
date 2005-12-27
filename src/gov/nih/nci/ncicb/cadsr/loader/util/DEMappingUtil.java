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
package gov.nih.nci.ncicb.cadsr.loader.util;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import java.util.List;


public class DEMappingUtil {

  /**
   * This method will check that replacing the current DE
   * with a mapping to existing DE
   * will not conflict with any other DE.
   * 
   * @param oldDe currentMapping
   * @param newDe new Mapping
   * @return null if there is no conflict. The conflicting DE if there is a conflict.
   */
  public static DataElement checkConflict(DataElement oldDe, DataElement newDe) {
    
    ObjectClass oc = oldDe.getDataElementConcept().getObjectClass();
    if(oc.getPublicId() != null) {
      // Verify conflicts
      if(!newDe.getDataElementConcept().getObjectClass().getPublicId().equals(oc.getPublicId()) || !newDe.getDataElementConcept().getObjectClass().getVersion().equals(oc.getVersion())) {
        // Oc was already mapped by an existing DE. This DE conflicts with the previous mapping.
        // Now we need to find out what DE set the oc_id previously
        List<DataElement> des = ElementsLists.getInstance()
          .getElements(DomainObjectFactory.newDataElement());
        
        for(DataElement de : des) {
          if(de.getDataElementConcept().getObjectClass() != oldDe.getDataElementConcept().getObjectClass())
            continue;

          String ocId = de.getDataElementConcept().getObjectClass().getPublicId();
          if(oldDe != de && ocId != null && !ocId.equals(oc.getPublicId())) {
            return de;
          }
        }
        // we shouldn't be here
        return null;
      }
    }
    return null;
  }
}