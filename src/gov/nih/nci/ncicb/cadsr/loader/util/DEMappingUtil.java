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
        //return newDe;
        List<DataElement> des = ElementsLists.getInstance()
          .getElements(DomainObjectFactory.newDataElement());
    
        for(DataElement de : des) {
          if(de.getDataElementConcept().getObjectClass() != oldDe.getDataElementConcept().getObjectClass())
            continue;
          
          if(StringUtil.isEmpty(de.getPublicId()) || de.getVersion() == null )
            continue;

          String ocId = de.getDataElementConcept().getObjectClass().getPublicId();
          if(oldDe != de && ocId != null && !ocId.equals(newDe.getDataElementConcept().getObjectClass().getPublicId())) {
            return de;
          }
        }
         //we shouldn't be here
        return null;
      }
    }
    return null;
  }
  
  public static AdminComponent checkDuplicate(DataElement currentDe, DataElement newDataElement) 
  {
    ElementsLists elements = ElementsLists.getInstance();


    // 1. Verify that this DE will not implicitely map the OC to one
    // that is already used in the model.
    List<ObjectClass> ocs = elements.getElements(DomainObjectFactory.newObjectClass());
    if(ocs != null) {
      for(ObjectClass oc : ocs) {  
        if(oc.getPublicId() != null) {
          if(currentDe.getDataElementConcept().getObjectClass() != oc)
            if(oc.getPublicId().equals(newDataElement.getDataElementConcept().getObjectClass().getPublicId()))            
                return oc;
        }
      }
    }

    // 2. Check that we don't already have this exact DE public ID
    List<DataElement> des = elements.getElements(DomainObjectFactory.newDataElement());
    for(DataElement de : des) {
      if(de != currentDe) {
        if(!StringUtil.isEmpty(de.getPublicId()) && de.getPublicId().equals(newDataElement.getPublicId()))
          return de;
      }
    }
//    if(des != null && ocs != null) {
//      for(ObjectClass oc : ocs) 
////        Map<String, DataElement> deList = new HashMap<String, DataElement>();
//        for(DataElement de : des) 
//          if(newDataElement.getDataElementConcept().getObjectClass() == de.getDataElementConcept().getObjectClass())
//            if(newDataElement.getPublicId() == de.getPublicId())
//              return false;    
//    }
  
    return null;
  }
  
  /**
   * @return the LVD if mapped to one, null otherwise
   */
  public static ValueDomain isMappedToLocalVD(DataElement de) {
    InheritedAttributeList inheritedAttributes = InheritedAttributeList.getInstance();

    ValueDomain _vd = de.getValueDomain();
    ElementsLists elements = ElementsLists.getInstance();
    List<ValueDomain> vds = elements.getElements(DomainObjectFactory.newValueDomain());
    if(_vd.getPublicId() != null)
      return null;
    
    if(vds != null) {
      for(ValueDomain currentVd : vds) 
        if(currentVd.getLongName().equals(_vd.getLongName())) {
          // inherited att might be indirectly mapped to LVD. (thru the parent)
          if(inheritedAttributes.isInherited(de)) { 
            DataElement parentDE = inheritedAttributes.getParent(de);
            if(!de.getValueDomain().getLongName().equals(parentDE.getValueDomain().getLongName()))
              return currentVd;
            else return null;
          } else
            return currentVd;
        }
    }
    return null;
  }

}