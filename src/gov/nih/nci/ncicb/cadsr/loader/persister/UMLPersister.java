package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.spring.*;

import gov.nih.nci.ncicb.cadsr.loader.UMLDefaults;

import org.apache.log4j.Logger;

import java.util.*;


public class UMLPersister implements Persister {
  private static Logger logger = Logger.getLogger(UMLPersister.class.getName());
  protected static AdminComponentDAO adminComponentDAO;
  protected static DataElementDAO dataElementDAO;
  protected static DataElementConceptDAO dataElementConceptDAO;
  protected static ValueDomainDAO valueDomainDAO;
  protected static PropertyDAO propertyDAO;
  protected static ObjectClassDAO objectClassDAO;
  protected static ObjectClassRelationshipDAO objectClassRelationshipDAO;
  protected static ClassificationSchemeDAO classificationSchemeDAO;
  protected static ClassificationSchemeItemDAO classificationSchemeItemDAO;
  protected static ConceptDAO conceptDAO;

  static {
    ApplicationContextFactory.init("applicationContext.xml");


    //     logger.debug("Loading DataElementDAO bean");
    dataElementDAO = (DataElementDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("dataElementDAO");

    //     logger.debug("Loading AdminComponentDAO bean");
    adminComponentDAO = (AdminComponentDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("adminComponentDAO");

    //     logger.debug("Loading DataElementConceptDAO bean");
    dataElementConceptDAO = (DataElementConceptDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("dataElementConceptDAO");


    //     logger.debug("Loading VDDAO bean");
    valueDomainDAO = (ValueDomainDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("valueDomainDAO");

    //     logger.debug("Loading PropertyDAO bean");
    propertyDAO = (PropertyDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("propertyDAO");

    //     logger.debug("Loading ObjectClassDAO bean");
    objectClassDAO = (ObjectClassDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("objectClassDAO");

    //     logger.debug("Loading ObjectClassRelationshipDAO bean");
    objectClassRelationshipDAO = (ObjectClassRelationshipDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("objectClassRelationshipDAO");

    //     logger.debug("Loading CSDAO bean");
    classificationSchemeDAO = (ClassificationSchemeDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("classificationSchemeDAO");

    //     logger.debug("Loading CSIDAO bean");
    classificationSchemeItemDAO = (ClassificationSchemeItemDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("classificationSchemeItemDAO");


    //     logger.debug("Loading ConceptDAO bean");
    conceptDAO = (ConceptDAO) ApplicationContextFactory.getApplicationContext()
      .getBean("conceptDAO");
  }

  protected static final String CSI_PACKAGE_TYPE = "UML_PACKAGE";
  protected ElementsLists elements = null;

  protected Map params = new HashMap();
  private Map valueDomains = new HashMap();

  protected UMLDefaults defaults = UMLDefaults.getInstance();

  public UMLPersister() {
    
  }

  public UMLPersister(ElementsLists list) {
    this.elements = list;
  }

  public void setParameter(String key, Object value) {
    params.put(key, value);
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
      logger.info(
	"Project Classification was not found. Attaching it now.");
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
