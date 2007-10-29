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

import gov.nih.nci.ncicb.xmiinout.domain.*;

import java.util.*;

public class LookupUtil {

  public static Concept lookupConcept(String conceptCode) {
    List<Concept> concepts = 
        ElementsLists.getInstance().getElements(DomainObjectFactory.newConcept());
    
    for (Concept con : concepts) {
      if(con.getPreferredName() != null)
        if(con.getPreferredName().equals(conceptCode))
          return con;
    }
    return null;

  }
  
  public static List<ObjectClassRelationship> lookupOcrs(ObjectClass oc) {
    List<ObjectClassRelationship> ocrs = 
        ElementsLists.getInstance().getElements(DomainObjectFactory.newObjectClassRelationship());

    List<ObjectClassRelationship> result = new ArrayList<ObjectClassRelationship>();

    for (Iterator it = ocrs.iterator(); it.hasNext();) {
      ObjectClassRelationship ocr = (ObjectClassRelationship)it.next();
      
      // Lookup by reference
      if((ocr.getSource() == oc) || (ocr.getTarget() == oc))
        result.add(ocr);
    }
    return result;

  }

  public static ValueDomain lookupValueDomain(String longName) {
    List<ValueDomain> vds = ElementsLists.getInstance().getElements(DomainObjectFactory.newValueDomain());

    for(ValueDomain vd : vds) 
      if(vd.getLongName().equals(longName))
        return vd;
    
    return null;

  }

  /**
   * by preferred Name
   */
  public static ObjectClass lookupObjectClass(String preferredName) {
    List<ObjectClass> ocs = ElementsLists.getInstance().getElements(DomainObjectFactory.newObjectClass());
    
    for (ObjectClass oc : ocs) {
      if (oc.getPreferredName().equals(preferredName)) {
        return oc;
      }
    }
    return null;
  }

  /**
   * by public id / version
   */
  public static ObjectClass lookupObjectClass(String publicID, Float version) {
    List<ObjectClass> ocs = ElementsLists.getInstance().getElements(DomainObjectFactory.newObjectClass());
    
    for (ObjectClass oc : ocs) {
      if (oc.getPublicId().equals(publicID) && oc.getVersion().equals(version)) { // 
        return oc;
      }
    }
    return null;
  }

  
  public static Property lookupProperty(String preferredName) {
    List<Property> props = ElementsLists.getInstance().getElements(DomainObjectFactory.newProperty());
    
    for (Property prop : props) {
      if (prop.getPreferredName().equals(preferredName)) {
        return prop;
      }
    }
    return null;
  }

  /**
   * by public id / version
   */
  public static Property lookupProperty(String publicID, Float version) {
    List<Property> props = ElementsLists.getInstance().getElements(DomainObjectFactory.newProperty());
    
    for (Property prop : props) {
      if (prop.getPublicId().equals(publicID) && prop.getVersion().equals(version)) { // 
        return prop;
      }
    }
    return null;
  }

  public static ObjectClass lookupObjectClass(AlternateName altName) {
    for(ObjectClass oc : ElementsLists.getInstance().getElements(DomainObjectFactory.newObjectClass())) {
      for(AlternateName an : oc.getAlternateNames()) {
        if(an.getType().equals(altName.getType()) && an.getName().equals(altName.getName()))
          return oc;
      }
    }
    return null;
  }

  
  public static DataElement lookupDataElement(AlternateName altName) {
    for(DataElement de : ElementsLists.getInstance().getElements(DomainObjectFactory.newDataElement())) {
      for(AlternateName an : de.getAlternateNames()) {
        if(an.getType().equals(altName.getType()) && an.getName().equals(altName.getName()))
          return de;
      }
    }
    return null;
  }


  public static String lookupFullName(ObjectClass oc) {
    for(AlternateName altName : oc.getAlternateNames()) {
      if(altName.getType().equals(AlternateName.TYPE_CLASS_FULL_NAME))
        return altName.getName();
    }
    return null;
  }

  public static String lookupFullName(DataElement de) {
    for(AlternateName altName : de.getAlternateNames()) {
      if(altName.getType().equals(AlternateName.TYPE_FULL_NAME))
        return altName.getName();
    }
    return null;
  }

  public static String getPackageName(UMLPackage pkg) {
    StringBuffer pack = new StringBuffer();
    String s = null;
    do {
      s = null;
      if(pkg != null) {
        s = pkg.getName(); 
        if(s.indexOf(" ") == -1) {
          if(pack.length() > 0)
            pack.insert(0, '.');
          pack.insert(0, s);
        }
        pkg = pkg.getParent();
      }
    } while (s != null);
    
    return pack.toString();
  }


}