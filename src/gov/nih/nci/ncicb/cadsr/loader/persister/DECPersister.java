package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import org.apache.log4j.Logger;

import java.util.*;


public class DECPersister extends UMLPersister {

  private static Logger logger = Logger.getLogger(DECPersister.class.getName());

  public DECPersister(ElementsLists list) {
    this.elements = list;
    defaults = UMLDefaults.getInstance();
  }

  public void persist() throws PersisterException {
    DataElementConcept dec = DomainObjectFactory.newDataElementConcept();
    List decs = (List) elements.getElements(dec.getClass());

    logger.debug("decs... ");
    if (decs != null) {
      for (ListIterator it = decs.listIterator(); it.hasNext();) {
	dec = (DataElementConcept) it.next();

//         for (Iterator it2 = elements.getElements(DomainObjectFactory.newObjectClass().getClass()).iterator(); it2.hasNext(); ) {
//           ObjectClass o = (ObjectClass) it2.next();
//           if (o.getConcept().getId()
//               .equals(dec.getObjectClass()
//                       .getConcept().getId())) {
//             dec.setObjectClass(o);
//           }
//         }
        
        // update object class with persisted one
        dec.setObjectClass(findObjectClass(
                             dec.getObjectClass().getLongName()));

//         for (Iterator it2 = elements.getElements(DomainObjectFactory.newProperty().getClass()).iterator(); it2.hasNext(); ) {
//           Property o = (Property) it2.next();
//           if (o.getConcept().getId()
//               .equals(dec.getProperty()
//                       .getConcept().getId())) {
//             dec.setProperty(o);
//           }
//         }

        // update object class with persisted one
        dec.setProperty(findProperty(
                             dec.getProperty().getLongName()));
        

	dec.setContext(defaults.getContext());
	dec.setConceptualDomain(defaults.getConceptualDomain());

        
        String first = dec.getProperty().getLongName().substring(0, 1).toUpperCase();
        String propName = first + dec.getProperty().getLongName().substring(1);

        dec.setLongName(
          dec.getObjectClass().getLongName()
          + propName);

	logger.debug("dec name: " + dec.getLongName());

	// does this dec exist?
	List l = dataElementConceptDAO.find(dec);

	if (l.size() == 0) {
	  // !!!!! TODO
	  dec.setPreferredDefinition(
            dec.getObjectClass().getPreferredDefinition() + ":" +
            dec.getProperty().getPreferredDefinition()
            
            );

	  dec.setPreferredName(
            dec.getObjectClass().getPublicId() + "-" 
            + dec.getObjectClass().getVersion() + ":" 
            + dec.getProperty().getPublicId() + "-"
            + dec.getProperty().getVersion());

	  dec.setVersion(defaults.getVersion());
	  dec.setWorkflowStatus(defaults.getWorkflowStatus());

	  List props = elements.getElements(DomainObjectFactory.newProperty()
					    .getClass());

	  for (int j = 0; j < props.size(); j++) {
	    Property o = (Property) props.get(j);

	    if (o.getLongName().equals(dec.getProperty()
				       .getLongName())) {
	      dec.setProperty(o);
	    }
	  }

	  dec.setAudit(defaults.getAudit());
// 	  dec.setId(dataElementConceptDAO.create(dec));
          dec = dataElementConceptDAO.create(dec);
	  logger.info(PropertyAccessor.getProperty("created.dec"));
	} else {
	  dec = (DataElementConcept) l.get(0);
	  logger.info(PropertyAccessor.getProperty("existed.dec"));
	}

	LogUtil.logAc(dec, logger);
        logger.info("-- Public ID: " + dec.getPublicId());
	logger.info(PropertyAccessor
                    .getProperty("oc.longName",
                                 dec.getObjectClass().getLongName()));
	logger.info(PropertyAccessor
                    .getProperty("prop.longName",
                                 dec.getProperty().getLongName()));

	addProjectCs(dec);
	it.set(dec);

	// add designation to hold package name
	// !!!! TODO
      }
    }

  }


}
