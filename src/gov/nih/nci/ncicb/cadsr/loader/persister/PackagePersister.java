package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import org.apache.log4j.Logger;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import java.util.*;


public class PackagePersister extends UMLPersister {

  
  private static Logger logger = Logger.getLogger(PackagePersister.class.getName());

  public PackagePersister(ElementsLists list) {
    this.elements = list;
    defaults = UMLDefaults.getInstance();
  }

  public void persist() {
    ClassificationSchemeItem pkg = DomainObjectFactory.newClassificationSchemeItem();
    List packages = (List) elements.getElements(pkg.getClass());

    Map packageCsCsis = defaults.getPackageCsCsis();


    if (packages != null) {
      for (ListIterator it = packages.listIterator(); it.hasNext();) {
	pkg = (ClassificationSchemeItem) it.next();
	pkg.setAudit(defaults.getAudit());
	pkg.setType(CSI_PACKAGE_TYPE);
        pkg.setName(defaults.getPackageDisplay(pkg.getComments()));

	// See if it already exist in DB
	List l = classificationSchemeItemDAO.find(pkg);

	if (l.size() == 0) { // not in DB, create it.
	  pkg.setId(classificationSchemeItemDAO.create(pkg));
	} else {
	  pkg = (ClassificationSchemeItem) l.get(0);
	}

	// link package CSI to project CS.
	List csCsis = defaults.getProjectCs().getCsCsis();
	boolean found = false;
	ClassSchemeClassSchemeItem packageCsCsi = null;


        if(csCsis != null && csCsis.size() > 0) {
          for (ListIterator it2 = csCsis.listIterator(); it2.hasNext();) {
            ClassSchemeClassSchemeItem csCsi = (ClassSchemeClassSchemeItem) it2.next();
            
            if (csCsi.getCsi().getType().equals(CSI_PACKAGE_TYPE) &&
                csCsi.getCsi().getName().equals(pkg.getName())) {
              packageCsCsi = csCsi;
              found = true;
            }
          }
        }

	if (!found) {
	  logger.info(
            PropertyAccessor
            .getProperty("link.package.to.project", pkg.getName()));
          packageCsCsi = DomainObjectFactory.newClassSchemeClassSchemeItem();
	  packageCsCsi.setCs(defaults.getProjectCs());
	  packageCsCsi.setCsi(pkg);
	  packageCsCsi.setLabel(pkg.getName());
	  packageCsCsi.setAudit(defaults.getAudit());

	  packageCsCsi.setId(classificationSchemeDAO.addClassificationSchemeItem(defaults.getProjectCs(),
                                                                                 packageCsCsi));
	  logger.info(PropertyAccessor.getProperty("added.package"));
	}

	// Put CS_CSI in cache so OCs can use it
	packageCsCsis.put(pkg.getComments(), packageCsCsi);
      }
    }

  }


}
