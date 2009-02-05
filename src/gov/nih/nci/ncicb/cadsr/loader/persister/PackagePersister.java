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

import gov.nih.nci.ncicb.cadsr.dao.ClassificationSchemeDAO;
import gov.nih.nci.ncicb.cadsr.dao.ClassificationSchemeItemDAO;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import org.apache.log4j.Logger;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;


import java.util.*;


/**
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class PackagePersister implements Persister {
  
  private static Logger logger = Logger.getLogger(PackagePersister.class.getName());

  private UMLDefaults defaults = UMLDefaults.getInstance();
  private ElementsLists elements = ElementsLists.getInstance();

  private ProgressListener progressListener = null;
  
  private PersisterUtil persisterUtil;
  
  private ClassificationSchemeItemDAO classificationSchemeItemDAO;
    private ClassificationSchemeDAO classificationSchemeDAO;


  public PackagePersister() {
    initDAOs();
  }

  public void persist() {
    ClassificationSchemeItem pkg = DomainObjectFactory.newClassificationSchemeItem();

    List<ClassificationSchemeItem> packages = elements.getElements(pkg);

    Map packageCsCsis = defaults.getPackageCsCsis();

    if (packages != null) {
      for (ListIterator<ClassificationSchemeItem> it = packages.listIterator(); it.hasNext();) {
	pkg = it.next();
        
	pkg.setAudit(defaults.getAudit());
	pkg.setType(ClassificationSchemeItem.TYPE_UML_PACKAGE);

        ClassificationSchemeItem subProject = DomainObjectFactory.newClassificationSchemeItem();
        subProject.setLongName(defaults.getPackageDisplay(pkg.getLongName()));
        subProject.setType(ClassificationSchemeItem.TYPE_UML_PROJECT);
        // Verify is there is a sub project
        if(!subProject.getLongName().equals(pkg.getLongName())) {
          subProject.setAudit(defaults.getAudit());

          // See if it already exist in DB
          List l = classificationSchemeItemDAO.find(subProject);
          
          if (l.size() == 0) { // not in DB, create it. 
            if(StringUtil.isEmpty(subProject.getPreferredDefinition()))
              subProject.setPreferredDefinition("No Value Exists.");
            subProject.setId(classificationSchemeItemDAO.create(subProject));
          } else {
            subProject = (ClassificationSchemeItem) l.get(0);
          }
        } else {

        }
        

        List<AlternateName> parsedAltNames = new ArrayList<AlternateName>
          (pkg.getAlternateNames());
        pkg.removeAlternateNames();

	// See if it already exist in DB
	List l = classificationSchemeItemDAO.find(pkg);

	if (l.size() == 0) { // not in DB, create it.
          if(StringUtil.isEmpty(pkg.getPreferredDefinition()))
            pkg.setPreferredDefinition("No Value Exists.");
	  pkg.setId(classificationSchemeItemDAO.create(pkg));
	} else {
	  pkg = (ClassificationSchemeItem) l.get(0);
	}

        ClassSchemeClassSchemeItem subCsCsi = null;
        if(subProject.getId() != null) {
          subCsCsi = linkCsiToCs(subProject, defaults.getProjectCs(), null);
        }

        ClassSchemeClassSchemeItem packageCsCsi = linkCsiToCs(pkg, defaults.getProjectCs(), subCsCsi);

	// Put CS_CSI in cache so OCs can use it
	packageCsCsis.put(pkg.getLongName(), packageCsCsi);

        
        for (AlternateName an : parsedAltNames)
        {
          pkg.addAlternateName(an);
          persisterUtil.addAlternateName(pkg, an.getName(), an.getType(), pkg.getLongName());
        }

        
      }
    }

  }

  private ClassSchemeClassSchemeItem linkCsiToCs(ClassificationSchemeItem csi, ClassificationScheme cs, ClassSchemeClassSchemeItem parent) {
	List csCsis = cs.getCsCsis();
	boolean found = false;
	ClassSchemeClassSchemeItem newCsCsi = null;

        if(csCsis != null && csCsis.size() > 0) {
          for (ListIterator it = csCsis.listIterator(); it.hasNext();) {
            ClassSchemeClassSchemeItem csCsi = (ClassSchemeClassSchemeItem) it.next();
            
            if (csCsi.getCsi().getType().equals(csi.getType()) &&
                csCsi.getCsi().getLongName().equals(csi.getLongName())) {
              // There's already a CS_CSI. 
              // Does it have the same parent?
              
              if(
                 ((csCsi.getParent() == null) 
                  && (parent == null))
                 || 
                 ((csCsi.getParent() != null)
                  && (parent != null)
                  && (csCsi.getParent().getId() == parent.getId()))
                 )
                {
                  newCsCsi = csCsi;
//                   if(parent != null) 
//                     newCsCsi.setParent(parent);
                  found = true;
                }
            } 
          }
        }

	if (!found) {
	  logger.info(
            PropertyAccessor
            .getProperty("link.package.to.project", csi.getLongName()));
          newCsCsi = DomainObjectFactory.newClassSchemeClassSchemeItem();
	  newCsCsi.setCs(cs);
	  newCsCsi.setCsi(csi);
	  newCsCsi.setLabel(csi.getLongName());
          if(newCsCsi.getLabel().length() > 30)
            newCsCsi.setLabel(
              newCsCsi.getLabel().substring(0, 29));
	  newCsCsi.setAudit(defaults.getAudit());
          if(parent != null) {
            newCsCsi.setParent(parent);
          }
	  newCsCsi.setId(classificationSchemeDAO
                         .addClassificationSchemeItem
                         (cs, newCsCsi));

          defaults.refreshProjectCs();
	  logger.info(PropertyAccessor.getProperty("added.package"));
	}
        return newCsCsi;
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
      classificationSchemeItemDAO = DAOAccessor.getClassificationSchemeItemDAO();
          classificationSchemeDAO = DAOAccessor.getClassificationSchemeDAO();

  }


}
