package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
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
	oc.setContext(defaults.getMainContext());

	String className = oc.getLongName();
	int ind = className.lastIndexOf(".");
	packageName = className.substring(0, ind);
	className = className.substring(ind + 1);


	// does this oc exist?
	List eager = new ArrayList();
	eager.add(EagerConstants.AC_CS_CSI);

        String[] conceptCodes = oc.getPreferredName().split("-");
        Concept primaryConcept = findConcept(conceptCodes[0]);
	oc.setLongName(primaryConcept.getLongName());

        List l = objectClassDAO.findByConceptCodes(conceptCodes, eager);
        
	boolean packageFound = false;
        String newDef = oc.getPreferredDefinition();

	if (l.size() == 0) {
          // !! TODO Need decision on this
	  oc.setPreferredName(oc.getLongName());

	  oc.setPreferredDefinition(primaryConcept.getPreferredDefinition());
          oc.setDefinitionSource(primaryConcept.getDefinitionSource());

	  oc.setVersion(new Float(1.0f));
	  oc.setWorkflowStatus(AdminComponent.WF_STATUS_RELEASED);
	  oc.setAudit(defaults.getAudit());

          try {
            oc.setId(objectClassDAO.create(oc, conceptCodes));
            logger.info(PropertyAccessor.getProperty("created.oc"));
          } catch (DAOCreateException e){
            logger.error(PropertyAccessor.getProperty("created.oc.failed", e.getMessage()));
          } // end of try-catch
          // is definition the same?
          // if not, then add alternate Def
          if((newDef.length() > 0) && !newDef.equals(oc.getPreferredDefinition())) {
            addAlternateDefinition(oc, newDef, Definition.TYPE_UML);
          }
          
	} else {
          String newName = oc.getLongName();
          String newDefSource = primaryConcept.getDefinitionSource();
          String newConceptDef = primaryConcept.getPreferredDefinition();
	  oc = (ObjectClass) l.get(0);
	  logger.info(PropertyAccessor.getProperty("existed.oc"));
          // is long_name the same?
          // if not, then add alternate Name
          if(!newName.equals(oc.getLongName())) {
            addAlternateName(oc, newName);
          }

          // is definition the same?
          // if not, then add alternate Def
          if((newDef.length() > 0) && !newDef.equals(oc.getPreferredDefinition())) {
            addAlternateDefinition(oc, newDef, Definition.TYPE_UML);
          }

          // is concept source the same?
          // if not, then add alternate Def
          if(!newDefSource.equals(oc.getDefinitionSource())) {
            addAlternateDefinition(oc, newConceptDef, newDefSource);
          }

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
	    logger.info(PropertyAccessor
                        .getProperty("added.package",
                                     new String[] {
                                       packageName, 
                                       oc.getLongName()}));
	  } else {
	    // PersistPackages should have taken care of it. 
	    // We should not be here.
	    logger.error(PropertyAccessor.getProperty("missing.package", new String[] {packageName, className}));
	  }
	} else {
	  logger.debug("Package was found.");
	}
      }
    }

  }


}
