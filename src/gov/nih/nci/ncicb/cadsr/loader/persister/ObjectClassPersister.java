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

import gov.nih.nci.ncicb.cadsr.dao.DAOCreateException;
import gov.nih.nci.ncicb.cadsr.dao.EagerConstants;
import gov.nih.nci.ncicb.cadsr.dao.ObjectClassDAO;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.Definition;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.util.ConceptUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.ConventionUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

/**
 * 
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class ObjectClassPersister implements Persister {
  private static Logger logger = Logger.getLogger(ObjectClassPersister.class);

  private UMLDefaults defaults = UMLDefaults.getInstance();
  private ElementsLists elements = ElementsLists.getInstance();

  private ProgressListener progressListener = null;
  
  private PersisterUtil persisterUtil;
  
  private ObjectClassDAO objectClassDAO;

  public ObjectClassPersister() {
    initDAOs();
  }

  public void persist() throws PersisterException
  {
    ObjectClass oc = DomainObjectFactory.newObjectClass();
    List<ObjectClass> ocs = elements.getElements(oc);

    if (ocs != null)
    {
      int count = 0;
      sendProgressEvent(count++, ocs.size(), "Object Class");

      for (ListIterator<ObjectClass> it = ocs.listIterator(); it.hasNext();)
      {
        ObjectClass newOc = null;

        oc = it.next();
        logger.debug(oc.getLongName());

        sendProgressEvent(count++, ocs.size(), "OC : " + oc.getLongName());

        oc.setContext(defaults.getMainContext());

        String className = LookupUtil.lookupFullName(oc);
        int ind = className.lastIndexOf(".");
        String packageName = className.substring(0, ind);
        className = className.substring(ind + 1);
        String newDef = oc.getPreferredDefinition();

        List<AlternateName> parsedAltNames = new ArrayList<AlternateName>(oc
                .getAlternateNames());
        oc.removeAlternateNames();
        oc.removeDefinitions();

        // Use case for existing Element
        if (!StringUtil.isEmpty(oc.getPublicId()) && oc.getVersion() != null)
        {
          newOc = existingMapping(oc, packageName);
          it.set(newOc);
          persisterUtil.addPackageClassification(newOc, packageName);

          for (AlternateName an : parsedAltNames)
          {
            oc.addAlternateName(an);
            newOc.addAlternateName(an);
            persisterUtil.addAlternateName(newOc, an.getName(), an.getType(), packageName);
          }

          logger.info(PropertyAccessor.getProperty("mapped.to.existing.oc"));
          continue;
        } // otherwise search by concepts

        // does this oc exist?
        List<String> eager = new ArrayList<String>();

        String[] conceptCodes = oc.getPreferredName().split(":");
        Concept[] concepts = new Concept[conceptCodes.length];
        for (int i = 0; i < concepts.length; concepts[i] = LookupUtil
                .lookupConcept(conceptCodes[i++]));

        List<ObjectClass> l = objectClassDAO.findByConceptCodes(conceptCodes,
                oc.getContext(), eager);

        Concept primaryConcept = concepts[concepts.length - 1];

        if (l.size() == 0)
        {
          oc.setLongName(ConceptUtil.longNameFromConcepts(concepts));
          oc.setPreferredDefinition(ConceptUtil
                  .preferredDefinitionFromConcepts(concepts));
          oc.setDefinitionSource(primaryConcept.getDefinitionSource());

          oc.setVersion(1.0f);
          oc.setWorkflowStatus(AdminComponent.WF_STATUS_RELEASED);
          oc.setAudit(defaults.getAudit());
          oc.setOrigin(defaults.getOrigin());
          oc.setLifecycle(defaults.getLifecycle());

          List acCsCsis = oc.getAcCsCsis();
          try
          {
            newOc = objectClassDAO.create(oc, conceptCodes);
            logger.info(PropertyAccessor.getProperty("created.oc"));

          }
          catch (DAOCreateException e)
          {
            logger.error(PropertyAccessor.getProperty("created.oc.failed", e
                    .getMessage()));
          } // end of try-catch
          // restore this since we use for package
          oc.setAcCsCsis(acCsCsis);

        }
        else
        {
          String newDefSource = primaryConcept.getDefinitionSource();
          String newConceptDef = primaryConcept.getPreferredDefinition();
          newOc = l.get(0);

          for (AlternateName an : parsedAltNames)
          {
            newOc.addAlternateName(an);
          }

          logger.info(PropertyAccessor.getProperty("existed.oc"));

          // is concept source the same?
          // if not, then add alternate Def
          if (!newDefSource.equals(newOc.getDefinitionSource()))
          {
            persisterUtil.addAlternateDefinition(newOc, newConceptDef, newDefSource,
                    packageName);
          }

        }

        LogUtil.logAc(newOc, logger);
        logger.info("public ID: " + newOc.getPublicId());

          persisterUtil.addAlternateDefinition(newOc, newDef, Definition.TYPE_UML_CLASS,
                  packageName);


        for (AlternateName an : parsedAltNames)
        {
          oc.addAlternateName(an);
          persisterUtil.addAlternateName(newOc, an.getName(), an.getType(), packageName);
        }

        it.set(newOc);
        oc.setLongName(newOc.getLongName());
        oc.setPreferredName(newOc.getPreferredName());
        persisterUtil.addPackageClassification(newOc, packageName);

      }
    }

  }

  private ObjectClass existingMapping(ObjectClass oc, String packageName)
          throws PersisterException
  {

    List<String> eager = new ArrayList<String>();
    eager.add(EagerConstants.AC_CS_CSI);

    String newDef = oc.getPreferredDefinition();

    List<AlternateName> parsedAltNames = new ArrayList<AlternateName>(oc
            .getAlternateNames());
    List<ObjectClass> l = objectClassDAO.find(oc, eager);

    if (l.size() == 0)
      throw new PersisterException(PropertyAccessor.getProperty(
              "oc.existing.error", ConventionUtil.publicIdVersion(oc)));

    ObjectClass existingOc = l.get(0);

    for (AlternateName an : parsedAltNames)
    {
      persisterUtil.addAlternateName(existingOc, an.getName(), an.getType(), packageName);
      existingOc.addAlternateName(an);
    }

    if (!StringUtil.isEmpty(newDef))
      persisterUtil.addAlternateDefinition(existingOc, newDef, Definition.TYPE_UML_CLASS,
              packageName);

    return existingOc;

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

  public void setProgressListener(ProgressListener listener) {
    progressListener = listener;
  }
  
  public void setPersisterUtil(PersisterUtil pu) {
    persisterUtil = pu;
  }
  
  private void initDAOs()  {
    objectClassDAO = DAOAccessor.getObjectClassDAO();
  }

}
