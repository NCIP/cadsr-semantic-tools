package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import org.apache.log4j.Logger;
import gov.nih.nci.ncicb.cadsr.loader.UMLDefaults;

import java.util.*;


public class PropertyPersister extends UMLPersister {

  private static Logger logger = Logger.getLogger(PropertyPersister.class.getName());

  public PropertyPersister(ElementsLists list) {
    this.elements = list;
    defaults = UMLDefaults.getInstance();
  }

  // !!!! TODO 
  // EVS CONCEPT CODE.
  public void persist() throws PersisterException {
    Property prop = DomainObjectFactory.newProperty();
    List props = (List) elements.getElements(prop.getClass());

    if (props != null) {
      for (ListIterator it = props.listIterator(); it.hasNext();) {
	prop = (Property) it.next();

	prop.setContext(defaults.getContext());

	// does this property exist?
	List l = propertyDAO.find(prop);

	if (l.size() == 0) {
	  // !!!!! TODO
	  prop.setPreferredDefinition(prop.getLongName());
	  prop.setPreferredName(prop.getLongName());

	  prop.setVersion(new Float(1.0f));
	  prop.setWorkflowStatus(defaults.getWorkflowStatus());
	  prop.setAudit(defaults.getAudit());

	  prop.setId(propertyDAO.create(prop));
	  logger.info("Created Property: ");
	} else {
	  prop = (Property) l.get(0);
	  logger.info("Property Existed: ");
	}

	LogUtil.logAc(prop, logger);

	addProjectCs(prop);
	it.set(prop);
      }
    }

  }


}
