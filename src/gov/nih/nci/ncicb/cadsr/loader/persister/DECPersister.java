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
        DataElementConcept newDec = DomainObjectFactory.newDataElementConcept();
	dec = (DataElementConcept) it.next();
        
        String packageName = getPackageName(dec);
        
        // update object class with persisted one
        dec.setObjectClass(lookupObjectClass(
                             dec.getObjectClass().getPreferredName()));
        newDec.setObjectClass(dec.getObjectClass());

        // update object class with persisted one
        dec.setProperty(lookupProperty(
                             dec.getProperty().getPreferredName()));
        newDec.setProperty(dec.getProperty());
        
        String newName = dec.getLongName();

	logger.debug("dec name: " + dec.getLongName());
        logger.debug("alt Name: " + newName);

	// does this dec exist?
	List l = dataElementConceptDAO.find(newDec);

        String newDef = dec.getPreferredDefinition();
	if (l.size() == 0) {
          dec.setConceptualDomain(defaults.getConceptualDomain());
          dec.setContext(defaults.getContext());
          dec.setLongName(
            dec.getObjectClass().getLongName()
            + " " + 
            dec.getProperty().getLongName()
            );
	  dec.setPreferredDefinition(
            dec.getObjectClass().getPreferredDefinition() + "\n" +
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
          newDec = dataElementConceptDAO.create(dec);
	  logger.info(PropertyAccessor.getProperty("created.dec"));

	} else {
	  newDec = (DataElementConcept) l.get(0);
	  logger.info(PropertyAccessor.getProperty("existed.dec"));

          /* if DEC alreay exists, check context
           * If context is different, add Used_by alt_name
           */
          if(newDec.getContext().getId() != defaults.getContext().getId()) {
            addAlternateName(newDec, defaults.getContext().getName(), AlternateName.TYPE_USED_BY, null);
          }
          

	}

        // is definition the same?
        // if not, then add alternate Def
        
        if((newDef.length() > 0) && !newDef.equals(newDec.getPreferredDefinition())) {
          System.out.println("Adding Alt Def");
          addAlternateDefinition(newDec, newDef, Definition.TYPE_UML_DEC, packageName
);
        }

        addAlternateName(newDec, newName, AlternateName.TYPE_UML_DEC, packageName);

	LogUtil.logAc(newDec, logger);
        logger.info("-- Public ID: " + newDec.getPublicId());
	logger.info(PropertyAccessor
                    .getProperty("oc.longName",
                                 newDec.getObjectClass().getLongName()));
	logger.info(PropertyAccessor
                    .getProperty("prop.longName",
                                 newDec.getProperty().getLongName()));


        addPackageClassification(newDec, packageName);
	it.set(newDec);

        // dec still referenced in DE. Need ID to retrieve it in DEPersister.
        dec.setId(newDec.getId());        

      }
    }

  }


}
