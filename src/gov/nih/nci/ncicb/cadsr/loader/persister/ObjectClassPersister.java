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

    String packageName = null;

    if (ocs != null) {
      for (ListIterator it = ocs.listIterator(); it.hasNext();) {
        ObjectClass newOc = null;

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
        Concept[] concepts = new Concept[conceptCodes.length];
        for(int i=0; i<concepts.length; 
            concepts[i] = findConcept(conceptCodes[i++])
            );
        
        List l = objectClassDAO.findByConceptCodes(conceptCodes, eager);
        
        Concept primaryConcept = concepts[concepts.length - 1];

	boolean packageFound = false;
        String newDef = oc.getPreferredDefinition();
        String newName = className;

	if (l.size() == 0) {
          oc.setLongName(longNameFromConcepts(concepts));
	  oc.setPreferredDefinition(preferredDefinitionFromConcepts(concepts));
          oc.setDefinitionSource(primaryConcept.getDefinitionSource());

	  oc.setVersion(new Float(1.0f));
	  oc.setWorkflowStatus(AdminComponent.WF_STATUS_RELEASED);
	  oc.setAudit(defaults.getAudit());

          try {
//             oc.setId(objectClassDAO.create(oc, conceptCodes));
            newOc = objectClassDAO.create(oc, conceptCodes);
            logger.info(PropertyAccessor.getProperty("created.oc"));
          } catch (DAOCreateException e){
            logger.error(PropertyAccessor.getProperty("created.oc.failed", e.getMessage()));
          } // end of try-catch
          // is definition the same?
          // if not, then add alternate Def
          if((newDef.length() > 0) && !newDef.equals(newOc.getPreferredDefinition())) {
            addAlternateDefinition(newOc, newDef, Definition.TYPE_UML);
          }
          // is long_name the same?
          // if not, then add alternate Name
          if(!newName.equals(newOc.getLongName())) {
            addAlternateName(newOc, newName);
          }
          
	} else {
          String newDefSource = primaryConcept.getDefinitionSource();
          String newConceptDef = primaryConcept.getPreferredDefinition();
	  newOc = (ObjectClass) l.get(0);
	  logger.info(PropertyAccessor.getProperty("existed.oc"));
          // is long_name the same?
          // if not, then add alternate Name
          if(!newName.equals(newOc.getLongName())) {
            addAlternateName(newOc, newName);
          }

          // is definition the same?
          // if not, then add alternate Def
          if((newDef.length() > 0) && !newDef.equals(newOc.getPreferredDefinition())) {
            addAlternateDefinition(newOc, newDef, Definition.TYPE_UML);
          }

          // is concept source the same?
          // if not, then add alternate Def
          if(!newDefSource.equals(newOc.getDefinitionSource())) {
            addAlternateDefinition(newOc, newConceptDef, newDefSource);
          }

	  List packages = newOc.getAcCsCsis();

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

	LogUtil.logAc(newOc, logger);
        logger.info("public ID: " + newOc.getPublicId());

	addProjectCs(newOc);
	it.set(newOc);
        
        oc.setLongName(newOc.getLongName());

	// add CSI to hold package name
	// !!!! TODO
	if (!packageFound) {
	  // see if we have the package in cache
	  ClassSchemeClassSchemeItem packageCsCsi = (ClassSchemeClassSchemeItem) defaults.getPackageCsCsis().get(packageName);

	  if (packageCsCsi != null) {
	    List ll = new ArrayList();
	    ll.add(packageCsCsi);
	    adminComponentDAO.addClassSchemeClassSchemeItems(newOc, ll);
	    logger.info(PropertyAccessor
                        .getProperty("added.package",
                                     new String[] {
                                       packageName, 
                                       newOc.getLongName()}));
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
