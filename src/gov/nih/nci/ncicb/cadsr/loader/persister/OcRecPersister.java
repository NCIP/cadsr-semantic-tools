package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import org.apache.log4j.Logger;

import java.util.*;


public class OcRecPersister extends UMLPersister {

  private static Logger logger = Logger.getLogger(OcRecPersister.class.getName());

  public OcRecPersister(ElementsLists list) {
    this.elements = list;
    defaults = UMLDefaults.getInstance();
  }

  public void persist() throws PersisterException {
    ObjectClassRelationship ocr = DomainObjectFactory.newObjectClassRelationship();
    List ocrs = (List) elements.getElements(ocr.getClass());

    if (ocrs != null) {
      for (ListIterator it = ocrs.listIterator(); it.hasNext();) {
	ocr = (ObjectClassRelationship) it.next();
	ocr.setContext(defaults.getContext());
	ocr.setAudit(defaults.getAudit());
	ocr.setVersion(defaults.getVersion());
	ocr.setWorkflowStatus(defaults.getWorkflowStatus());

	ocr.setPreferredDefinition(new OCRDefinitionBuilder().buildDefinition(ocr));

	if ((ocr.getLongName() == null) ||
	    (ocr.getLongName().length() == 0)) {
	  logger.debug("No Role name for association. Generating one");
	  ocr.setLongName(new OCRRoleNameBuilder().buildRoleName(ocr));
	}

	List ocs = elements.
	  getElements(DomainObjectFactory.newObjectClass()
		      .getClass());

	for (int j = 0; j < ocs.size(); j++) {
	  ObjectClass o = (ObjectClass) ocs.get(j);

	  if (o.getLongName().equals(ocr.getSource().getLongName())) {
	    ocr.setSource(o);
	  } else if (o.getLongName().equals(ocr.getTarget()
					    .getLongName())) {
	    ocr.setTarget(o);
	  }
	}

	LogUtil.logAc(ocr, logger);
	logger.info("-- Source Role: " + ocr.getSourceRole());
	logger.info("-- Source Cardinality: " +
                    ocr.getSourceLowCardinality() + "-" +
		    ocr.getSourceHighCardinality());
	logger.info("-- Target Role: " + ocr.getTargetRole());
	logger.info("-- Target Cardinality: " +
                    ocr.getTargetLowCardinality() + "-" +
		    ocr.getTargetHighCardinality());
	logger.info("-- Direction: " + ocr.getDirection());
	logger.info("-- Type: " + ocr.getType());

	// check if association already exists
	ObjectClassRelationship ocr2 = DomainObjectFactory.newObjectClassRelationship();
	ocr2.setSource(ocr.getSource());
	ocr2.setSourceRole(ocr.getSourceRole());
	ocr2.setTarget(ocr.getTarget());
	ocr2.setTargetRole(ocr.getTargetRole());

	List eager = new ArrayList();
	eager.add(EagerConstants.AC_CS_CSI);

	List l = objectClassRelationshipDAO.find(ocr2, eager);
	boolean found = false;

	if (l.size() > 0) {
	  for (Iterator it2 = l.iterator(); it2.hasNext();) {
	    ocr2 = (ObjectClassRelationship) it2.next();

	    List acCsCsis = (List) ocr2.getAcCsCsis();

	    for (Iterator it3 = acCsCsis.iterator(); it3.hasNext();) {
	      AdminComponentClassSchemeClassSchemeItem acCsCsi = (AdminComponentClassSchemeClassSchemeItem) it3.next();

	      if (acCsCsi.getCsCsi().getCs().getLongName().equals(defaults.getProjectCs().getLongName())) {
	      found = true;
	      logger.debug("Association with same classification already found");
	      }
	      
	    }
	  }
	}

	if (found) {
	  logger.info("Association already existed.");
	} else {
	  ocr.setId(objectClassRelationshipDAO.create(ocr));
	  addProjectCs(ocr);
	  logger.info("Created Association");
	}

	// !!! TODO also add package name
      }
    }

  }


}
