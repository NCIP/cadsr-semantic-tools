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

import gov.nih.nci.ncicb.cadsr.dao.AdminComponentDAO;
import gov.nih.nci.ncicb.cadsr.dao.ConceptDAO;
import gov.nih.nci.ncicb.cadsr.domain.*;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import org.apache.log4j.Logger;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;

import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsResult;

import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;

import java.util.*;


/**
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class ConceptPersister implements Persister {

  private static Logger logger = Logger.getLogger(ConceptPersister.class.getName());

  private EvsModule evsModule = new EvsModule("PRE_NCI_Thesaurus");

  private UMLDefaults defaults = UMLDefaults.getInstance();
  private ElementsLists elements = ElementsLists.getInstance();

  private ProgressListener progressListener = null;
  
  private static ConceptDAO conceptDAO;
  private static AdminComponentDAO adminComponentDAO;

  private PersisterUtil persisterUtil;

  public ConceptPersister() {
    initDAOs();
  }

  public void persist() {
    Concept con = DomainObjectFactory.newConcept();
    List<Concept> cons = elements.getElements(con);

    int consSize = cons.size();

    logger.debug("ConceptPersister.persist()");

    sendProgressEvent(0, consSize, "Persisting Concepts");

    int count = 1;
    if (cons != null) {
      for(Iterator it = cons.iterator(); it.hasNext();) {
        Concept c = (Concept)it.next();
        con.setPreferredName(c.getPreferredName());
        logger.debug("concept name: " + con.getPreferredName());
        sendProgressEvent(count++, consSize, "Concept : " + con.getPreferredName());
        List l = conceptDAO.find(con);

        if(l.size() == 0) { // concept does not exist: create it
          c.setVersion(new Float(1.0f));
          c.setContext(defaults.getMainContext());
	  c.setWorkflowStatus(AdminComponent.WF_STATUS_RELEASED);
	  c.setAudit(defaults.getAudit());
          c.setOrigin(defaults.getOrigin());
          c.setEvsSource(PropertyAccessor.getProperty("default.evsSource"));
          c.setLifecycle(defaults.getLifecycle());

          c.setId(conceptDAO.create(c));
          logger.info(PropertyAccessor.getProperty("created.concept"));
          LogUtil.logAc(c, logger);
        } else { // concept exist: See if we need to add alternate def.
          logger.info(PropertyAccessor.getProperty("existed.concept", c.getPreferredName()));



          String newSource = c.getDefinitionSource();
          String newDef = c.getPreferredDefinition();
          String newName = c.getLongName();
          c = (Concept)l.get(0);

          EvsResult evsResult = null;
          // verify that name in input and name in caDSR are the same
          if(!newName.equalsIgnoreCase(c.getLongName())) {
            // the names are not the same. 
            // lookup EVS to see if what's in input is in sync with EVS
            evsResult = evsModule.findByConceptCode(c.getPreferredName(), false);
            
            if(evsResult != null) {
              Concept conRes = evsResult.getConcept();
              if(conRes.getLongName().equals(newName)) {
                // evs return came same as input, so update caDSR
                conceptDAO.updateName(c, newName);
                logger.info("Updated Concept name for concept:  " + c.getPreferredName());
                
                addAlternateName(c, c.getLongName(), AlternateName.PRIOR_PREFERRED_NAME);
              }
            } else { // this concept is not in EVS, the names are not the same. Stop the load.
              logger.error(PropertyAccessor.getProperty("cant.validate.concept", 
                                                        new String[]{c.getPreferredName(), "preferred name"}));
            }

          }

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

  private void addAlternateName(Concept con, String newName, String type) 
  {
    List<String> eager = new ArrayList<String>();

    List<AlternateName> altNames = adminComponentDAO.getAlternateNames(con, eager);
    boolean found = false;
    for(AlternateName an : altNames) {
      if(an.getType().equals(type) && an.getName().equals(newName)) {
        found = true;
        logger.info(PropertyAccessor.getProperty(
                      "existed.altName", newName));
      }
    }
    
    if(!found) {
      AlternateName altName = DomainObjectFactory.newAlternateName();
      altName.setContext(defaults.getContext());
      altName.setAudit(defaults.getAudit());
      altName.setName(newName);
      altName.setType(type);
      altName.setId(adminComponentDAO.addAlternateName(con, altName));
      logger.info(PropertyAccessor.getProperty(
                    "added.altName", 
                    new String[] {
                      altName.getName(),
                      con.getLongName()
                    }));
      
    } 
  }

  protected void sendProgressEvent(int status, int goal, String message) {
    if(progressListener != null) {
      ProgressEvent pEvent = new ProgressEvent();
      pEvent.setMessage(message);
      pEvent.setStatus(status);
      pEvent.setGoal(goal);
      
      progressListener.newProgressEvent(pEvent);

    }
  }

  public void setPersisterUtil(PersisterUtil pu) {
    persisterUtil = pu;
  }

  public void setProgressListener(ProgressListener listener) {
    progressListener = listener;
  }

  private void initDAOs()  {
    conceptDAO = DAOAccessor.getConceptDAO();
    adminComponentDAO = DAOAccessor.getAdminComponentDAO();
  }
  
}
