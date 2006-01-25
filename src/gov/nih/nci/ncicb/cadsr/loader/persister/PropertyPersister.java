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

import org.apache.log4j.Logger;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;

import java.util.*;


/**
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class PropertyPersister extends UMLPersister {

  private static Logger logger = Logger.getLogger(PropertyPersister.class.getName());

  public PropertyPersister(ElementsLists list) {
    this.elements = list;
    defaults = UMLDefaults.getInstance();
  }

  public void persist() throws PersisterException {
    Property prop = DomainObjectFactory.newProperty();
    List<Property> props = elements.getElements(prop);

    if (props != null) {
      for (ListIterator<Property> it = props.listIterator(); it.hasNext();) {
	prop = it.next();
        logger.debug(prop.getLongName());
        logger.debug(prop.getPreferredName());

        Property newProp = null;

        String packageName = getPackageName(prop);
        String newDef = prop.getPreferredDefinition();
        String newName = prop.getLongName();

	prop.setContext(defaults.getMainContext());

        String[] conceptCodes = prop.getPreferredName().split(":");
        
        if(!StringUtil.isEmpty(prop.getPublicId()) && prop.getVersion() != null) {
          logger.debug("mapping to existing Prop");
          newProp = existingMapping(prop, newName, packageName);
          it.set(newProp);
          addPackageClassification(newProp, packageName);
	  logger.info(PropertyAccessor.getProperty("mapped.to.existing.prop"));
          continue;
        }


	List eager = new ArrayList();
	eager.add(EagerConstants.AC_CS_CSI);

        List<Property> l = propertyDAO.findByConceptCodes(conceptCodes, prop.getContext(), eager);

        Concept[] concepts = new Concept[conceptCodes.length];
        for(int i=0; i<concepts.length; 
            concepts[i] = LookupUtil.lookupConcept(conceptCodes[i++])
            );

        Concept primaryConcept = concepts[concepts.length - 1];

	if (l.size() == 0) {
          prop.setLongName(ConceptUtil.longNameFromConcepts(concepts));
	  prop.setPreferredDefinition(ConceptUtil.preferredDefinitionFromConcepts(concepts));
          prop.setDefinitionSource(primaryConcept.getDefinitionSource());

	  prop.setVersion(1.0f);
	  prop.setWorkflowStatus(AdminComponent.WF_STATUS_RELEASED);
	  prop.setAudit(defaults.getAudit());
          prop.setOrigin(defaults.getOrigin());

          logger.debug("property: " + prop.getLongName());

          try {
            // remove for persistence
            prop.setAcCsCsis(null);

            logger.debug("Saving Property with " + prop.getPreferredName() + "v" + prop.getVersion() + " in context " + prop.getContext().getName());

            newProp = propertyDAO.create(prop, conceptCodes);
            logger.info(PropertyAccessor.getProperty("created.prop"));
          } catch (DAOCreateException e){
            logger.error(PropertyAccessor.getProperty("created.prop.failed", e.getMessage()));
            e.printStackTrace();
          } 

	} else {
	  newProp = (Property) l.get(0);
	  logger.info(PropertyAccessor.getProperty("existed.prop"));
          
	}

	LogUtil.logAc(newProp, logger);
        logger.info("-- Public ID: " + newProp.getPublicId());

        addAlternateName(newProp, newName, AlternateName.TYPE_UML_ATTRIBUTE, packageName);

	it.set(newProp);

        addPackageClassification(newProp, packageName);

        // This object still reference in DEC, update long_name to real long name
        prop.setLongName(newProp.getLongName());
      }
    }

  }


  private Property existingMapping(Property prop, String newName, String packageName) throws PersisterException {

    List<String> eager = new ArrayList<String>();
    eager.add(EagerConstants.AC_CS_CSI);
    
    List<Property> l = propertyDAO.find(prop, eager);

    if(l.size() == 0)
      throw new PersisterException(PropertyAccessor.getProperty("prop.existing.error", ConventionUtil.publicIdVersion(prop)));
    
    Property existingProp = l.get(0);

    addAlternateName(existingProp, newName, AlternateName.TYPE_UML_CLASS ,packageName);

    return existingProp;

  }


}
