package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.loader.UMLDefaults;
import org.apache.log4j.Logger;

import java.util.*;


public class ObjectClassPersister extends UMLPersister {

  private static Logger logger = Logger.getLogger(ObjectClassPersister.class.getName());

  public ObjectClassPersister(ElementsLists list) {
    this.elements = list;
    defaults = UMLDefaults.getInstance();
  }

  public void persist() throws PersisterException {
    ObjectClass oc = DomainObjectFactory.newObjectClass();
    List ocs = (List) elements.getElements(oc.getClass());

    logger.debug("ocs...");

    String packageName = null;

    if (ocs != null) {
      for (ListIterator it = ocs.listIterator(); it.hasNext();) {
	oc = (ObjectClass) it.next();
	oc.setContext(defaults.getContext());

	String className = oc.getLongName();
	int ind = className.lastIndexOf(".");
	packageName = className.substring(0, ind);
	className = className.substring(ind + 1);

	oc.setLongName(className);

	// does this oc exist?
	List eager = new ArrayList();
	eager.add(EagerConstants.AC_CS_CSI);

	List l = objectClassDAO.find(oc, eager);

	boolean packageFound = false;

	if (l.size() == 0) {
	  // !!!!! TODO
	  oc.setPreferredDefinition(oc.getLongName());
	  oc.setPreferredName(oc.getLongName());

	  oc.setVersion(new Float(1.0f));
	  oc.setWorkflowStatus(defaults.getWorkflowStatus());
	  oc.setAudit(defaults.getAudit());

	  oc.setId(objectClassDAO.create(oc));
	  logger.info("Created Object Class: ");
	} else {
	  oc = (ObjectClass) l.get(0);
	  logger.info("Object Class Existed: ");

	  List packages = oc.getAcCsCsis();

	  if (packages != null) {
	    for (Iterator it2 = packages.iterator();
		 it2.hasNext() && !packageFound;) {
	      AdminComponentClassSchemeClassSchemeItem acCsCsi = (AdminComponentClassSchemeClassSchemeItem) it2.next();
	      ClassSchemeClassSchemeItem csCsi = acCsCsi.getCsCsi();

	      if (csCsi.getCsi().getType().equals(CSI_PACKAGE_TYPE) &&
		  csCsi.getCsi().getName().equals(packageName)) {
		packageFound = true;
	      }
	    }
	  }
	}

	LogUtil.logAc(oc, logger);

	addProjectCs(oc);
	it.set(oc);

	// add CSI to hold package name
	// !!!! TODO
	if (!packageFound) {
	  // see if we have the package in cache
	  ClassSchemeClassSchemeItem packageCsCsi = (ClassSchemeClassSchemeItem) defaults.getPackageCsCsis().get(packageName);

	  if (packageCsCsi != null) {
	    List ll = new ArrayList();
	    ll.add(packageCsCsi);
	    adminComponentDAO.addClassSchemeClassSchemeItems(oc, ll);
	    logger.info("Added CS package: " + packageName);
	  } else {
	    // PersistPackages should have taken care of it. 
	    // We should not be here.
	    logger.error("Missing Package: " + packageName);
	  }
	} else {
	  logger.debug("Designation was found.");
	}
      }
    }

  }


}
