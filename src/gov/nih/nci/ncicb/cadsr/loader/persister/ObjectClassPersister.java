package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;

import gov.nih.nci.ncicb.cadsr.loader.util.*;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
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
            concepts[i] = LookupUtil.lookupConcept(conceptCodes[i++])
            );
        
        List l = objectClassDAO.findByConceptCodes(conceptCodes, oc.getContext(), eager);
        
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

          List acCsCsis = oc.getAcCsCsis();
          try {
            newOc = objectClassDAO.create(oc, conceptCodes);
            logger.info(PropertyAccessor.getProperty("created.oc"));
          } catch (DAOCreateException e){
            logger.error(PropertyAccessor.getProperty("created.oc.failed", e.getMessage()));
          } // end of try-catch
          // restore this since we use for package
          oc.setAcCsCsis(acCsCsis);

	} else {
          String newDefSource = primaryConcept.getDefinitionSource();
          String newConceptDef = primaryConcept.getPreferredDefinition();
	  newOc = (ObjectClass) l.get(0);
	  logger.info(PropertyAccessor.getProperty("existed.oc"));

          // is concept source the same?
          // if not, then add alternate Def
          if(!newDefSource.equals(newOc.getDefinitionSource())) {
            addAlternateDefinition(newOc, newConceptDef, newDefSource, packageName);
          }

	}

	LogUtil.logAc(newOc, logger);
        logger.info("public ID: " + newOc.getPublicId());

        // is definition the same?
        // if not, then add alternate Def
        if((newDef.length() > 0) && !newDef.equals(newOc.getPreferredDefinition())) {
          addAlternateDefinition(newOc, newDef, Definition.TYPE_UML_CLASS, packageName);
        }
        
        addAlternateName(newOc, newName, AlternateName.TYPE_UML_CLASS ,packageName);


// 	addProjectCs(newOc);
	it.set(newOc);
        
        oc.setLongName(newOc.getLongName());

        addPackageClassification(newOc, packageName);

      }
    }

  }


}
