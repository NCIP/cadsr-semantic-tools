package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import org.apache.log4j.Logger;

import java.util.*;


public class DEPersister extends UMLPersister {

  private static Logger logger = Logger.getLogger(DEPersister.class.getName());

  public DEPersister(ElementsLists list) {
    this.elements = list;
    defaults = UMLDefaults.getInstance();
  }

  public void persist() throws PersisterException {
    DataElement de = DomainObjectFactory.newDataElement();
    List des = (List) elements.getElements(de.getClass());

    logger.debug("des...");

    if (des != null) {
      for (ListIterator it = des.listIterator(); it.hasNext();) {
	try {
	  de = (DataElement) it.next();
          DataElement newDe = DomainObjectFactory.newDataElement();

          String packageName = getPackageName(de);

          de.setDataElementConcept(findDec(de.getDataElementConcept().getId()));
          newDe.setDataElementConcept(de.getDataElementConcept());

	  de.setValueDomain(lookupValueDomain(de.getValueDomain()));
          newDe.setValueDomain(de.getValueDomain());
          
	  List l = dataElementDAO.find(newDe);

          de.setLongName(
            de.getDataElementConcept()
            .getLongName() + " "
            + de.getValueDomain().getPreferredName());
          
	  if (l.size() == 0) {
            de.setContext(defaults.getContext());
            de.setPreferredName(
              de.getDataElementConcept().getPublicId() + "-" +
              de.getDataElementConcept().getVersion() + ":" + 
              de.getValueDomain().getPublicId() + "-" + 
              de.getValueDomain().getVersion()
              );
            
	    de.setVersion(defaults.getVersion());
	    de.setWorkflowStatus(defaults.getWorkflowStatus());

	    de.setAudit(defaults.getAudit());
	    logger.debug("Creating DE: " + de.getLongName());
	    de.setId(dataElementDAO.create(de));
	    logger.info(PropertyAccessor.getProperty("created.de"));
	  } else {
	    de = (DataElement) l.get(0);
	    logger.info(PropertyAccessor.getProperty("existed.de"));

            /* if DE alreay exists, check context
             * If context is different, add Used_by alt_name
             */
            if(de.getContext().getId() != defaults.getContext().getId()) {
              addAlternateName(de, defaults.getContext().getName(), AlternateName.TYPE_USED_BY, null);
            }


	  }

	  LogUtil.logAc(de, logger);
	  logger.info(PropertyAccessor.
                      getProperty("vd.preferredName",
                                  de.getValueDomain().getPreferredName()));

          addPackageClassification(de, packageName);
	  it.set(de);
	} catch (PersisterException e) {
	  logger.error("Could not persist DE: " + de.getLongName());
	  logger.error(e.getMessage());
	} // end of try-catch
      }
    }

  }


}
