package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import org.apache.log4j.Logger;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;

import java.util.*;


public class PropertyPersister extends UMLPersister {

  private static Logger logger = Logger.getLogger(PropertyPersister.class.getName());

  public PropertyPersister(ElementsLists list) {
    this.elements = list;
    defaults = UMLDefaults.getInstance();
  }

  public void persist() throws PersisterException {
    Property prop = DomainObjectFactory.newProperty();
    List props = (List) elements.getElements(prop.getClass());

    if (props != null) {
      for (ListIterator it = props.listIterator(); it.hasNext();) {
	prop = (Property) it.next();

	prop.setContext(defaults.getMainContext());


        String[] conceptCodes = prop.getPreferredName().split("-");
        List l = propertyDAO.findByConceptCodes(conceptCodes);

        prop.setLongName(prop.getConcept().getLongName());

        Concept primaryConcept = findConcept(conceptCodes[0]);

	if (l.size() == 0) {
	  prop.setPreferredName(prop.getLongName());
	  prop.setPreferredDefinition(primaryConcept.getPreferredDefinition());
          prop.setDefinitionSource(primaryConcept.getDefinitionSource());

	  prop.setVersion(new Float(1.0f));
	  prop.setWorkflowStatus(AdminComponent.WF_STATUS_RELEASED);
	  prop.setAudit(defaults.getAudit());

          logger.debug("property: " + prop.getLongName());

          try {
            prop.setId(propertyDAO.create(prop, conceptCodes));
            logger.info(PropertyAccessor.getProperty("created.prop"));
          } catch (DAOCreateException e){
            logger.error(PropertyAccessor.getProperty("created.prop.failed", e.getMessage()));
          } // end of try-catch
	} else {
          // !!! TODO Verify that next line is ok.
          String newPrefName = prop.getLongName();
	  prop = (Property) l.get(0);
	  logger.info(PropertyAccessor.getProperty("existed.prop"));
          // is long_name the same?
          // if not, then add alternate Name
          if(!newPrefName.equals(prop.getPreferredName())) {
            addAlternateName(prop, newPrefName);
          }
          

	}

	LogUtil.logAc(prop, logger);

	addProjectCs(prop);
	it.set(prop);
      }
    }

  }


}
