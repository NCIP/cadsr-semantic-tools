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
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import org.apache.log4j.Logger;

import java.util.*;


/**
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class DEPersister extends UMLPersister {
  private static Logger logger = Logger.getLogger(DEPersister.class.getName());
  public static String DE_PREFERRED_NAME_DELIMITER = "v";
  public static String DE_PREFERRED_NAME_CONCAT_CHAR = ":";
  public static String DE_PREFERRED_DEF_CONCAT_CHAR = "_";

  public DEPersister(ElementsLists list) {
    this.elements = list;
    defaults = UMLDefaults.getInstance();
  }

  public void persist() throws PersisterException {
    DataElement de = DomainObjectFactory.newDataElement();
    List des = (List) elements.getElements(de.getClass());

    logger.debug("des...");

    if (des != null) {
      for (ListIterator it = des.listIterator(); it.hasNext();) {
        try {
          de = (DataElement) it.next();
          DataElement newDe = DomainObjectFactory.newDataElement();

          String packageName = getPackageName(de);

          de.setDataElementConcept(
            lookupDec(de.getDataElementConcept().getId()));
          newDe.setDataElementConcept(de.getDataElementConcept());

          de.setValueDomain(lookupValueDomain(de.getValueDomain()));
          newDe.setValueDomain(de.getValueDomain());

//           String newDef = de.getPreferredDefinition();

          List l = dataElementDAO.find(newDe);
          //Did Extract Method refactoring
          /*de.setLongName(
            de.getDataElementConcept().getLongName() + " " +
            de.getValueDomain().getPreferredName());*/
          de.setLongName(this.deriveLongName(de));

          if (l.size() == 0) {
            de.setContext(defaults.getContext());
            de.setPreferredName(this.derivePreferredName(de));
            de.setVersion(new Float(1.0f));
            de.setWorkflowStatus(defaults.getWorkflowStatus());

            de.setPreferredDefinition(
              de.getDataElementConcept().getPreferredDefinition()
              + DE_PREFERRED_DEF_CONCAT_CHAR +
              de.getValueDomain().getPreferredDefinition()
              );

            de.setAudit(defaults.getAudit());
            logger.debug("Creating DE: " + de.getLongName());
            List altNames = new ArrayList(de.getAlternateNames());
            List altDefs = new ArrayList(de.getDefinitions());
            newDe = dataElementDAO.create(de);

            // restore altNames
            for(Iterator it2 = altNames.iterator(); it2.hasNext();) {
              AlternateName an = (AlternateName)it2.next();
              de.addAlternateName(an);
            }
            // restore altDefs
            for(Iterator it2 = altDefs.iterator(); it2.hasNext();) {
              Definition def = (Definition)it2.next();
              de.addDefinition(def);
            }

            logger.info(PropertyAccessor.getProperty("created.de"));
          }
          else {
            newDe = (DataElement) l.get(0);
            logger.info(PropertyAccessor.getProperty("existed.de"));

            /* if DE alreay exists, check context
             * If context is different, add Used_by alt_name
             */
            if (!newDe.getContext().getId().equals(defaults.getContext().getId())) {
              addAlternateName(
                newDe, defaults.getContext().getName(), AlternateName.TYPE_USED_BY,
                null);
            }

          }

          LogUtil.logAc(newDe, logger);
          logger.info(
            PropertyAccessor.getProperty(
              "vd.preferredName", newDe.getValueDomain().getPreferredName()));

          addPackageClassification(newDe, packageName);

          for(Iterator it2 = de.getAlternateNames().iterator(); it2.hasNext(); ) {
            AlternateName altName = (AlternateName)it2.next();
            
            addAlternateName(
              newDe, altName.getName(),
              altName.getType(), packageName);
          }

          for(Iterator it2 = de.getDefinitions().iterator(); it2.hasNext(); ) {
            Definition def = (Definition)it2.next();
            addAlternateDefinition(
              newDe, def.getDefinition(), 
              def.getType(), packageName);
          }

          it.set(newDe);
        }
        catch (PersisterException e) {
          logger.error("Could not persist DE: " + de.getLongName());
          logger.error(e.getMessage());
        } // end of try-catch
      }
    }
  }
  
  //Will need to declare this method as abstract method in UMLPersistor
  protected String derivePreferredName (AdminComponent ac ) {
    DataElement de = (DataElement)ac;
    String preferredName = de.getDataElementConcept().getPublicId() 
                          +DE_PREFERRED_NAME_DELIMITER
                          +de.getDataElementConcept().getVersion() 
                          +DE_PREFERRED_NAME_CONCAT_CHAR
                          +de.getValueDomain().getPublicId() 
                          +DE_PREFERRED_NAME_DELIMITER
                          +de.getValueDomain().getVersion();
    return preferredName;
  }
  
  //Will need to declare this method as abstract method in UMLPersistor
  protected String deriveLongName (AdminComponent ac ) {
    DataElement de = (DataElement)ac;
    String longName = de.getDataElementConcept().getLongName() 
                     + " "
                     +de.getValueDomain().getPreferredName();
    return longName;
  }
}
