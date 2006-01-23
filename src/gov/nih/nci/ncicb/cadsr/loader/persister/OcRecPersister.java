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
package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.loader.util.*;

import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import org.apache.log4j.Logger;

import java.util.*;


/**
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class OcRecPersister extends UMLPersister {

  private static Logger logger = Logger.getLogger(OcRecPersister.class.getName());

  public OcRecPersister(ElementsLists list) {
    this.elements = list;
    defaults = UMLDefaults.getInstance();
  }

  public void persist() throws PersisterException {
    ObjectClassRelationship ocr = DomainObjectFactory.newObjectClassRelationship();
    List ocrs = (List) elements.getElements(ocr.getClass());

    if (ocrs != null) {
      for (ListIterator it = ocrs.listIterator(); it.hasNext();) {
	ocr = (ObjectClassRelationship) it.next();
	ocr.setContext(defaults.getContext());
	ocr.setAudit(defaults.getAudit());
	ocr.setVersion(defaults.getVersion());
	ocr.setWorkflowStatus(defaults.getWorkflowStatus());

        String sourcePackage = getPackageName(ocr.getSource());
        String targetPackage = getPackageName(ocr.getTarget());

	ocr.setPreferredDefinition(new OCRDefinitionBuilder().buildDefinition(ocr));

	if ((ocr.getLongName() == null) ||
	    (ocr.getLongName().length() == 0)) {
	  logger.debug("No Role name for association. Generating one");
	  ocr.setLongName(new OCRRoleNameBuilder().buildRoleName(ocr));
	}

	List<ObjectClass> ocs = elements.
	  getElements(DomainObjectFactory.newObjectClass());

// 	for (ObjectClass o : ocs) {
// 	  if (o.getPreferredName().equals(ocr.getSource().getPreferredName())) {
// 	    ocr.setSource(o);
// 	  } 
//           if (o.getPreferredName().equals(ocr.getTarget()
// 					    .getPreferredName())) {
// 	    ocr.setTarget(o);
// 	  }
// 	}
        ObjectClass sOcr = ocr.getSource();

socr:
        for(AlternateName an : sOcr.getAlternateNames()) {
          if(an.getType().equals(AlternateName.TYPE_CLASS_FULL_NAME)) {
            ocr.setSource(LookupUtil.lookupObjectClass(an));
            break socr;
          }
        }
        ObjectClass tOcr = ocr.getTarget();
tocr:
        for(AlternateName an : tOcr.getAlternateNames()) {
          if(an.getType().equals(AlternateName.TYPE_CLASS_FULL_NAME)) {
            ocr.setTarget(LookupUtil.lookupObjectClass(an));
            break tocr;
          }
        }



//         logger.info(PropertyAccessor
//                     .getProperty("created.association"));
	LogUtil.logAc(ocr, logger);
	logger.info(PropertyAccessor
                    .getProperty("source.role",  ocr.getSourceRole()));
	logger.info(PropertyAccessor
                    .getProperty("source.class",  ocr.getSource().getLongName()));
	logger.info(PropertyAccessor.getProperty
                    ("source.cardinality", new Object[]
                      {new Integer(ocr.getSourceLowCardinality()),
                       new Integer(ocr.getSourceHighCardinality())}));
	logger.info(PropertyAccessor
                    .getProperty("target.role",  ocr.getTargetRole()));
	logger.info(PropertyAccessor
                    .getProperty("target.class",  ocr.getTarget().getLongName()));
	logger.info(PropertyAccessor.getProperty
                    ("target.cardinality", new Object[]
                      {new Integer(ocr.getTargetLowCardinality()),
                       new Integer(ocr.getTargetHighCardinality())}));
	logger.info(PropertyAccessor.getProperty
                    ("direction", ocr.getDirection()));
	logger.info(PropertyAccessor.getProperty
                    ("type", ocr.getType()));

	// check if association already exists
	ObjectClassRelationship ocr2 = DomainObjectFactory.newObjectClassRelationship();
	ocr2.setSource(ocr.getSource());
	ocr2.setSourceRole(ocr.getSourceRole());
	ocr2.setTarget(ocr.getTarget());
	ocr2.setTargetRole(ocr.getTargetRole());

	List eager = new ArrayList();
	eager.add(EagerConstants.AC_CS_CSI);

	List l = objectClassRelationshipDAO.find(ocr2, eager);
// 	boolean found = false;

// 	if (l.size() > 0) {
// 	  for (Iterator it2 = l.iterator(); it2.hasNext();) {
// 	    ocr2 = (ObjectClassRelationship) it2.next();

// 	    List acCsCsis = (List) ocr2.getAcCsCsis();

// 	    for (Iterator it3 = acCsCsis.iterator(); it3.hasNext();) {
// 	      AdminComponentClassSchemeClassSchemeItem acCsCsi = (AdminComponentClassSchemeClassSchemeItem) it3.next();

// 	      if (acCsCsi.getCsCsi().getCs().getLongName().equals(defaults.getProjectCs().getLongName())) {
// 	      found = true;
// 	      logger.debug("Association with same classification already found");
// 	      }
	      
// 	    }
// 	  }
// 	}
        
        if (l.size() > 0) {
          logger.info(PropertyAccessor.getProperty("existed.association"));
          ocr = (ObjectClassRelationship)l.get(0);
        } else {
//           ocr.setPreferredName(
//             ocr.getSource().getPublicId() + "-" +
//             ocr.getSource().getVersion() + ":" + 
//             ocr.getTarget().getPublicId() + "-" + 
//             ocr.getTarget().getVersion()
//             );

          ocr.setId(objectClassRelationshipDAO.create(ocr));
          // 	  addProjectCs(ocr);
	  logger.info(PropertyAccessor.getProperty("created.association"));
	}
        
        addPackageClassification(ocr, sourcePackage);
        addPackageClassification(ocr, targetPackage);
      }
    }
  }    
  
}
