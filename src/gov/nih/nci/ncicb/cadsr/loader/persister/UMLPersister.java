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
  protected static ConceptDAO conceptDAO = DAOAccessor.getConceptDAO();

  protected static final String CSI_PACKAGE_TYPE = "UML_PACKAGE";
  protected ElementsLists elements = null;

  private Map valueDomains = new HashMap();

  protected UMLDefaults defaults = UMLDefaults.getInstance();

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
}
