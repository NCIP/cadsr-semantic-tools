package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.spring.*;

import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import org.apache.log4j.Logger;

import java.util.*;


public class UMLPersister implements Persister {
  private static Logger logger = Logger.getLogger(UMLPersister.class.getName());
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

  private Map valueDomains = new HashMap();

  protected UMLDefaults defaults = UMLDefaults.getInstance();

  protected static final String CSI_PACKAGE_TYPE = "UML_PACKAGE";

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

  protected void addProjectCs(AdminComponent ac) throws PersisterException {
    List l = adminComponentDAO.getClassSchemeClassSchemeItems(ac);

    // is projectCs linked?
    boolean found = false;

    for (ListIterator it = l.listIterator(); it.hasNext();) {
      ClassSchemeClassSchemeItem csCsi = (ClassSchemeClassSchemeItem) it.next();

      if (csCsi.getCs().getLongName().equals(defaults.getProjectCs().getLongName())) {
	if (csCsi.getCsi().getName().equals(defaults.getDomainCsi().getName())) {
	  found = true;
	}
      }
    }

    List csCsis = new ArrayList();

    if (!found) {
      logger.info(PropertyAccessor.
                  getProperty("attach.project.classification"));
      csCsis.add(defaults.getProjectCsCsi());
      adminComponentDAO.addClassSchemeClassSchemeItems(ac, csCsis);
    }
  }




  protected ValueDomain lookupValueDomain(ValueDomain vd)
    throws PersisterException {
    ValueDomain result = (ValueDomain) valueDomains.get(vd.getPreferredName());

    if (result == null) { // not in cache -- go to db

      List l = valueDomainDAO.find(vd);

      if (l.size() == 0) {
	throw new PersisterException("Value Domain " +
				     vd.getPreferredName() + " does not exist.");
      }

      result = (ValueDomain) l.get(0);
      valueDomains.put(result.getPreferredName(), result);
    }

    return result;
  }

  protected void addAlternateName(AdminComponent ac, String newName) {

    List altNames = adminComponentDAO.getAlternateNames(ac);
    boolean found = false;
    for(Iterator it = altNames.iterator(); it.hasNext(); ) {
      AlternateName an = (AlternateName)it.next();
      if(an.getType().equals(AlternateName.TYPE_SYNONYM) && an.getName().equals(newName)) {
        found = true;
        logger.info(PropertyAccessor.getProperty(
                      "existed.altName", newName));
        
        boolean csFound = false;
        for(Iterator it2 = an.getCsCsis().iterator(); it2.hasNext();) {
          ClassSchemeClassSchemeItem csCsi = (ClassSchemeClassSchemeItem)it2.next();
          if(csCsi.getId().equals(defaults.getProjectCsCsi().getId())) {
            csFound = true;
          }
        }
        if(!csFound) {
          classSchemeClassSchemeItemDAO.addCsCsi(an, defaults.getProjectCsCsi());
          logger.info(
            PropertyAccessor.getProperty(
              "linked.to.project",
              "Alternate Name"
              ));
        }
        
      }
    }
    
    if(!found) {
      AlternateName altName = DomainObjectFactory.newAlternateName();
      altName.setContext(defaults.getContext());
      altName.setName(newName);
      altName.setType(AlternateName.TYPE_SYNONYM);
      altName.setId(adminComponentDAO.addAlternateName(ac, altName));
      logger.info(PropertyAccessor.getProperty(
                    "added.altName", 
                    new String[] {
                      altName.getName(),
                      ac.getLongName()
                    }));
      
      classSchemeClassSchemeItemDAO.addCsCsi(altName, defaults.getProjectCsCsi());
      logger.info(
        PropertyAccessor.getProperty(
          "linked.to.project",
          "Alternate Name"
          ));
      
    } 
  }

  protected void addAlternateDefinition(AdminComponent ac, String newDef, String type) {

    List altDefs = adminComponentDAO.getDefinitions(ac);
    boolean found = false;
    for(Iterator it = altDefs.iterator(); it.hasNext(); ) {
      Definition def = (Definition)it.next();
      if(def.getType().equals(type) && def.getDefinition().equals(newDef)) {
        found = true;
        logger.info(PropertyAccessor.getProperty(
                      "existed.altDef", newDef));
        
        boolean csFound = false;
        for(Iterator it2 = def.getCsCsis().iterator(); it2.hasNext();) {
          ClassSchemeClassSchemeItem csCsi = (ClassSchemeClassSchemeItem)it2.next();
          if(csCsi.getId().equals(defaults.getProjectCsCsi().getId())) {
            csFound = true;
          }
        }
        if(!csFound) {
          classSchemeClassSchemeItemDAO.addCsCsi(def, defaults.getProjectCsCsi());
          logger.info(
            PropertyAccessor.getProperty(
              "linked.to.project",
              "Alternate Definition"
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
      
      classSchemeClassSchemeItemDAO.addCsCsi(altDef, defaults.getProjectCsCsi());
      logger.info(
        PropertyAccessor.getProperty(
          "linked.to.project",
          "Alternate Definition"
          ));
      
    } 
  }

  

}
