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
import gov.nih.nci.ncicb.cadsr.spring.*;

import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.util.*;

import org.apache.log4j.Logger;

import java.util.*;


/**
 * This class will call the other UML Related Persisters
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class UMLPersister implements Persister {
  private static Logger logger = Logger.getLogger(UMLPersister.class.getName());

  private static Map<String,String> vdMapping = DatatypeMapping.getMapping();

  protected static AdminComponentDAO adminComponentDAO;
  protected static DataElementDAO dataElementDAO;
  protected static DataElementConceptDAO dataElementConceptDAO;
  protected static ValueDomainDAO valueDomainDAO;
  protected static PropertyDAO propertyDAO;
  protected static ObjectClassDAO objectClassDAO;
  protected static ObjectClassRelationshipDAO objectClassRelationshipDAO;
  protected static ClassificationSchemeDAO classificationSchemeDAO;
  protected static ClassificationSchemeItemDAO classificationSchemeItemDAO;
  protected static ClassSchemeClassSchemeItemDAO classSchemeClassSchemeItemDAO;
  protected static ConceptDAO conceptDAO;

  protected ElementsLists elements = ElementsLists.getInstance();

  protected Map<String, ValueDomain> valueDomains = new HashMap<String, ValueDomain>();

  protected UMLDefaults defaults = UMLDefaults.getInstance();

  public UMLPersister() {
    this.elements = ElementsLists.getInstance();
  }

  public void persist() throws PersisterException {

    initDAOs();

    new PackagePersister().persist();
    new ConceptPersister().persist();
    new PropertyPersister().persist();
    new ObjectClassPersister().persist();
    new DECPersister().persist();
    new DEPersister().persist();
    new OcRecPersister().persist();
  }

  protected ValueDomain lookupValueDomain(ValueDomain vd)
    throws PersisterException {

    if(vd.getLongName().startsWith("enum")) {
      vd.setLongName("java.lang.String");
    }

    ValueDomain result = valueDomains.get(vd.getLongName());

    if (result == null) { // not in cache -- go to db
      List<ValueDomain> l = valueDomainDAO.find(vd);

      if (l.size() == 0) {
	throw new PersisterException("Value Domain " +
				     vd.getLongName() + " does not exist.");
      }

      result = l.get(0);
      // store to cache
      valueDomains.put(result.getLongName(), result);
    }

    return result;
  }

  protected void addAlternateName(AdminComponent ac, String newName, String type, String packageName) {

    List<String> eager = new ArrayList<String>();
    eager.add("csCsis");

    List<AlternateName> altNames = adminComponentDAO.getAlternateNames(ac, eager);
    boolean found = false;
    ClassSchemeClassSchemeItem packageCsCsi = (ClassSchemeClassSchemeItem)defaults.getPackageCsCsis().get(packageName);
    for(AlternateName an : altNames) {
      if(an.getType().equals(type) && an.getName().equals(newName)) {
        found = true;
        logger.info(PropertyAccessor.getProperty(
                      "existed.altName", newName));

        if(packageName == null)
          return;
        
        boolean csFound = false;
        boolean parentFound = false;
        for(ClassSchemeClassSchemeItem csCsi : an.getCsCsis()) {
          if(csCsi.getId().equals(packageCsCsi.getId())) {
            csFound = true;
          } else if(csCsi.getId().equals(packageCsCsi.getParent().getId())) 
            parentFound = true;
        }
        if(!csFound) {
          classSchemeClassSchemeItemDAO.addCsCsi(an, packageCsCsi);
          logger.info(
            PropertyAccessor.getProperty(
              "linked.to.package",
              "Alternate Name"
              ));
        }
        if(!parentFound && packageCsCsi.getParent() != null) {
          classSchemeClassSchemeItemDAO.addCsCsi(an, packageCsCsi.getParent());
          logger.info(
            PropertyAccessor.getProperty(
              "linked.to.package",
              "Alternate Name"
              ));
        }

        
      }
    }
    
    if(!found) {
      AlternateName altName = DomainObjectFactory.newAlternateName();
      altName.setContext(defaults.getContext());
      altName.setAudit(defaults.getAudit());
      altName.setName(newName);
      altName.setType(type);
      altName.setId(adminComponentDAO.addAlternateName(ac, altName));
      logger.info(PropertyAccessor.getProperty(
                    "added.altName", 
                    new String[] {
                      altName.getName(),
                      ac.getLongName()
                    }));
      
      if(packageName != null) {
        classSchemeClassSchemeItemDAO.addCsCsi(altName, packageCsCsi);
        logger.info(
          PropertyAccessor.getProperty(
            "linked.to.package",
            "Alternate Name"
            ));
        if(packageCsCsi.getParent() != null) {
          classSchemeClassSchemeItemDAO.addCsCsi(altName, packageCsCsi.getParent());
          logger.info(
            PropertyAccessor.getProperty(
              "linked.to.package",
              "Alternate Name"
              ));
        }
      }      

    } 
  }

  protected void addAlternateDefinition(AdminComponent ac, String newDef, String type, String packageName) {

    List<String> eager = new ArrayList<String>();
    eager.add("csCsis");

    List<Definition> altDefs = adminComponentDAO.getDefinitions(ac, eager);
    boolean found = false;
    ClassSchemeClassSchemeItem packageCsCsi = (ClassSchemeClassSchemeItem)defaults.getPackageCsCsis().get(packageName);

    for(Definition def : altDefs) {
      if(def.getType().equals(type) && def.getDefinition().equals(newDef)) {
        found = true;
        logger.info(PropertyAccessor.getProperty(
                      "existed.altDef", newDef));
        
        boolean csFound = false;
        boolean parentFound = false;
        for(ClassSchemeClassSchemeItem csCsi : def.getCsCsis()) {
          if(csCsi.getId().equals(packageCsCsi.getId())) {
            csFound = true;
          } else if(csCsi.getId().equals(packageCsCsi.getParent().getId())) 
            parentFound = true;
        }
        if(!csFound) {
          classSchemeClassSchemeItemDAO.addCsCsi(def, packageCsCsi);
          logger.info(
            PropertyAccessor.getProperty(
              "linked.to.package",
              "Alternate Definition"
              ));
        }
        if(!parentFound && packageCsCsi.getParent() != null) {
          classSchemeClassSchemeItemDAO.addCsCsi(def, packageCsCsi.getParent());
          logger.info(
            PropertyAccessor.getProperty(
              "linked.to.package",
              "Alternate Name"
              ));
        }
        
      }
    }
    
    if(!found) {

      Definition altDef = DomainObjectFactory.newDefinition();
      altDef.setContext(defaults.getContext());
      altDef.setDefinition(newDef);
      altDef.setAudit(defaults.getAudit());
      altDef.setType(type);
      altDef.setId(adminComponentDAO.addDefinition(ac, altDef));
      logger.info(PropertyAccessor.getProperty(
                    "added.altDef", 
                    new String[] {
                      altDef.getId(),
                      altDef.getDefinition(),
                      ac.getLongName()
                    }));
      
      classSchemeClassSchemeItemDAO.addCsCsi(altDef, packageCsCsi);
      logger.info(
        PropertyAccessor.getProperty(
          "linked.to.package",
          "Alternate Definition"
          ));
      
      if(packageCsCsi.getParent() != null) {
        classSchemeClassSchemeItemDAO.addCsCsi(altDef, packageCsCsi.getParent());
        logger.info(
          PropertyAccessor.getProperty(
            "linked.to.package",
            "Alternate Definition"
            ));
      }
    } 
  }

  protected DataElementConcept lookupDec(String id) {
    List decs = (List) elements.getElements(DomainObjectFactory.newDataElementConcept().getClass());
    
    for (Iterator it = decs.iterator(); it.hasNext(); ) {
      DataElementConcept o = (DataElementConcept) it.next();

      if (o.getId().equals(id)) {
        return o;
      }
    }
    return null;
  }


  protected boolean isSameDefinition(String def, Concept[] concepts) {
    if((def == null) || def.length() == 0)
      return true;

    StringBuffer sb = new StringBuffer();
    
    for(int i=0; i < concepts.length; i++) {
      if(sb.length() > 0)
        sb.append("\n");
      sb.append(concepts[i].getPreferredDefinition());
    }

    return def.equals(sb.toString());
    
  }

  protected void addPackageClassification(AdminComponent ac, String packageName) {

    List l = adminComponentDAO.getClassSchemeClassSchemeItems(ac);

    ClassSchemeClassSchemeItem packageCsCsi = (ClassSchemeClassSchemeItem)defaults.getPackageCsCsis().get(packageName);

    // is projectCs linked?
    boolean found = false;
    boolean parentFound = false;

    for (ListIterator it = l.listIterator(); it.hasNext();) {
      ClassSchemeClassSchemeItem csCsi = (ClassSchemeClassSchemeItem) it.next();

      if(csCsi.getId().equals(packageCsCsi.getId()))
        found = true;
      else if(csCsi.getId().equals(packageCsCsi.getParent().getId())) 
        parentFound = true;
    }

    List csCsis = new ArrayList();

    if (!found) {
      logger.info(PropertyAccessor.
                  getProperty("attach.package.classification"));
      
      if (packageCsCsi != null) {
        csCsis.add(packageCsCsi);
        if(!parentFound && packageCsCsi.getParent() != null)
          csCsis.add(packageCsCsi.getParent());

        adminComponentDAO.addClassSchemeClassSchemeItems(ac, csCsis);
        logger.info(PropertyAccessor
                    .getProperty("added.package",
                                 new String[] {
                                   packageName, 
                                   ac.getLongName()}));
      } else {
        // PersistPackages should have taken care of it. 
        // We should not be here.
        logger.error(PropertyAccessor.getProperty("missing.package", new String[] {packageName, ac.getLongName()}));
      }
      
    }
  }

  protected String getPackageName(AdminComponent ac) {
    return 
      ((AdminComponentClassSchemeClassSchemeItem)ac.getAcCsCsis().get(0)).getCsCsi().getCsi().getName();
  }


  private void initDAOs() {
    adminComponentDAO = DAOAccessor.getAdminComponentDAO();
    dataElementDAO = DAOAccessor.getDataElementDAO();
    dataElementConceptDAO = DAOAccessor.getDataElementConceptDAO();
    valueDomainDAO = DAOAccessor.getValueDomainDAO();
    propertyDAO = DAOAccessor.getPropertyDAO();
    objectClassDAO = DAOAccessor.getObjectClassDAO();
    objectClassRelationshipDAO = DAOAccessor.getObjectClassRelationshipDAO();
    classificationSchemeDAO = DAOAccessor.getClassificationSchemeDAO();
    classificationSchemeItemDAO = DAOAccessor.getClassificationSchemeItemDAO();
    classSchemeClassSchemeItemDAO = DAOAccessor.getClassSchemeClassSchemeItemDAO();
    conceptDAO = DAOAccessor.getConceptDAO();
  }
}
