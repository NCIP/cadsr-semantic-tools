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

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import org.apache.log4j.Logger;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;

import java.util.*;


/**
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class ConceptPersister extends UMLPersister {

  private static Logger logger = Logger.getLogger(ConceptPersister.class.getName());

  public ConceptPersister(ElementsLists list) {
    this.elements = list;
  }

  public void persist() {
    Concept con = DomainObjectFactory.newConcept();
    List cons = elements.getElements(con.getClass());

    logger.debug("ConceptPersister.persist()");

    if (cons != null) {
      for(Iterator it = cons.iterator(); it.hasNext();) {
        Concept c = (Concept)it.next();
        con.setPreferredName(c.getPreferredName());
        logger.debug("concept name: " + con.getPreferredName());
        List l = conceptDAO.find(con);

        if(l.size() == 0) { // concept does not exist: create it
          c.setVersion(new Float(1.0f));
          c.setContext(defaults.getMainContext());
	  c.setWorkflowStatus(AdminComponent.WF_STATUS_RELEASED);
	  c.setAudit(defaults.getAudit());
          c.setOrigin(defaults.getOrigin());
          c.setEvsSource(PropertyAccessor.getProperty("default.evsSource"));
          c.setId(conceptDAO.create(c));
          logger.info(PropertyAccessor.getProperty("created.concept"));
          LogUtil.logAc(c, logger);
        } else { // concept exist: See if we need to add alternate def.
          logger.info(PropertyAccessor.getProperty("existed.concept", c.getPreferredName()));

          String newSource = c.getDefinitionSource();
          String newDef = c.getPreferredDefinition();
          c = (Concept)l.get(0);
          
//           Concept c2 = (Concept)l.get(0);
//           c.setId(c2.getId());
          if(!newSource.equalsIgnoreCase(c.getDefinitionSource())) { // Add alt def.

            logger.debug("Concept " + c.getPreferredName() + " had different definition source. ");
            Definition def = DomainObjectFactory.newDefinition();
            def.setType(newSource);
            def.setDefinition(newDef);
            def.setAudit(defaults.getAudit());
            def.setContext(defaults.getContext());
            adminComponentDAO.addDefinition(c, def);
            logger.info(PropertyAccessor.getProperty("added.altDef", new String[]{c.getPreferredName(), newDef, "Concept"}));
          } else {
            // Do nothing, this is the common case where the concept existed and had the same def source.
          }
        }
      }
    }
  }


}
