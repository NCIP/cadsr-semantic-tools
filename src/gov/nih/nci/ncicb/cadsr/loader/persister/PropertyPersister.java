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

        Property newProp = null;

	prop.setContext(defaults.getMainContext());

        String[] conceptCodes = prop.getPreferredName().split("-");
        List l = propertyDAO.findByConceptCodes(conceptCodes, prop.getContext());

        Concept[] concepts = new Concept[conceptCodes.length];
        for(int i=0; i<concepts.length; 
            concepts[i] = lookupConcept(conceptCodes[i++])
            );

        Concept primaryConcept = concepts[concepts.length - 1];
        String newDef = prop.getPreferredDefinition();
        String newName = prop.getLongName();
        String packageName = getPackageName(prop);

	if (l.size() == 0) {
          prop.setLongName(longNameFromConcepts(concepts));
	  prop.setPreferredDefinition(preferredDefinitionFromConcepts(concepts));
          prop.setDefinitionSource(primaryConcept.getDefinitionSource());

	  prop.setVersion(new Float(1.0f));
	  prop.setWorkflowStatus(AdminComponent.WF_STATUS_RELEASED);
	  prop.setAudit(defaults.getAudit());

          logger.debug("property: " + prop.getLongName());

          try {
            // remove for persistence
            prop.setAcCsCsis(null);

            logger.debug("Saving Property with " + prop.getPreferredName() + "v" + prop.getVersion() + " in context " + prop.getContext().getName());

            newProp = propertyDAO.create(prop, conceptCodes);
            logger.info(PropertyAccessor.getProperty("created.prop"));
          } catch (DAOCreateException e){
            logger.error(PropertyAccessor.getProperty("created.prop.failed", e.getMessage()));
            e.printStackTrace();
          } 

	} else {
	  newProp = (Property) l.get(0);
	  logger.info(PropertyAccessor.getProperty("existed.prop"));
          
	}

	LogUtil.logAc(newProp, logger);
        logger.info("-- Public ID: " + newProp.getPublicId());

        addAlternateName(newProp, newName, AlternateName.TYPE_UML_ATTRIBUTE, packageName);

	it.set(newProp);

        addPackageClassification(newProp, packageName);

        // This object still reference in DEC, update long_name to real long name
        prop.setLongName(newProp.getLongName());
      }
    }

  }


}
