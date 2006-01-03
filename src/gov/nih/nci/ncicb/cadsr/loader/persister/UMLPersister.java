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

  protected static AdminComponentDAO adminComponentDAO = DAOAccessor.getAdminComponentDAO();
  protected static DataElementDAO dataElementDAO = DAOAccessor.getDataElementDAO();
  protected static DataElementConceptDAO dataElementConceptDAO = DAOAccessor.getDataElementConceptDAO();
  protected static ValueDomainDAO valueDomainDAO = DAOAccessor.getValueDomainDAO();
  protected static PropertyDAO propertyDAO = DAOAccessor.getPropertyDAO();
  protected static ObjectClassDAO objectClassDAO = DAOAccessor.getObjectClassDAO();
  protected static ObjectClassRelationshipDAO objectClassRelationshipDAO = DAOAccessor.getObjectClassRelationshipDAO();
  protected static ClassificationSchemeDAO classificationSchemeDAO = DAOAccessor.getClassificationSchemeDAO();
  protected static ClassificationSchemeItemDAO classificationSchemeItemDAO = DAOAccessor.getClassificationSchemeItemDAO();
  protected static ClassSchemeClassSchemeItemDAO classSchemeClassSchemeItemDAO = DAOAccessor.getClassSchemeClassSchemeItemDAO();
  protected static ConceptDAO conceptDAO = DAOAccessor.getConceptDAO();

  protected ElementsLists elements = null;

  protected Map<String, ValueDomain> valueDomains = new HashMap<String, ValueDomain>();

  protected UMLDefaults defaults = UMLDefaults.getInstance();

//   private static final String DEF_CONCAT_CHAR = "_";

//   protected static final String CSI_PACKAGE_TYPE = "UML_PACKAGE";

  public UMLPersister() {
    
  }

  public UMLPersister(ElementsLists list) {
    this.elements = list;
  }

  public void persist() throws PersisterException {

    new PackagePersister(elements).persist();
    new ConceptPersister(elements).persist();
    new PropertyPersister(elements).persist();
    new ObjectClassPersister(elements).persist();
    new DECPersister(elements).persist();
    new DEPersister(elements).persist();
    new OcRecPersister(elements).persist();
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
          if(csCsi.equals(packageCsCsi)) {
            csFound = true;
          } else if(csCsi.equals(packageCsCsi.getParent())) 
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

    List altDefs = adminComponentDAO.getDefinitions(ac);
    boolean found = false;
    ClassSchemeClassSchemeItem packageCsCsi = (ClassSchemeClassSchemeItem)defaults.getPackageCsCsis().get(packageName);

    for(Iterator it = altDefs.iterator(); it.hasNext(); ) {
      Definition def = (Definition)it.next();
      if(def.getType().equals(type) && def.getDefinition().equals(newDef)) {
        found = true;
        logger.info(PropertyAccessor.getProperty(
                      "existed.altDef", newDef));
        
        boolean csFound = false;
        boolean parentFound = false;
        for(Iterator it2 = def.getCsCsis().iterator(); it2.hasNext();) {
          ClassSchemeClassSchemeItem csCsi = (ClassSchemeClassSchemeItem)it2.next();
          if(csCsi.equals(packageCsCsi)) {
            csFound = true;
          } else if(csCsi.equals(packageCsCsi.getParent())) 
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

  protected Concept lookupConcept(String conceptCode) {
    List concepts = (List) elements.getElements(DomainObjectFactory.newConcept().getClass());

    for (Iterator it = concepts.iterator(); it.hasNext();) {
      Concept con = (Concept)it.next();
      if(con.getPreferredName().equals(conceptCode))
        return con;
    }
    return null;

  }

  protected Property lookupProperty(String preferredName) {
    List props = (List) elements.getElements(DomainObjectFactory.newProperty().getClass());
    
    for (Iterator it = props.iterator(); it.hasNext(); ) {
      Property o = (Property) it.next();

      if (o.getPreferredName().equals(preferredName)) {
        return o;
      }
    }
    return null;
  }

  protected ObjectClass lookupObjectClass(String preferredName) {
    List ocs = (List) elements.getElements(DomainObjectFactory.newObjectClass().getClass());
    
    for (Iterator it = ocs.iterator(); it.hasNext(); ) {
      ObjectClass o = (ObjectClass) it.next();

      if (o.getPreferredName().equals(preferredName)) {
        return o;
      }
    }
    return null;
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
      else if(csCsi.equals(packageCsCsi.getParent())) 
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

}
