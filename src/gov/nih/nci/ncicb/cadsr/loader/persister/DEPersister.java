package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import org.apache.log4j.Logger;

import java.util.*;


public class DEPersister extends UMLPersister {
  private static Logger logger = Logger.getLogger(DEPersister.class.getName());
  public static String DE_PREFERRED_NAME_DELIMITER = "v";
  public static String DE_PREFERRED_NAME_CONCAT_CHAR = ":";

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

          de.setDataElementConcept(
            lookupDec(de.getDataElementConcept().getId()));
          newDe.setDataElementConcept(de.getDataElementConcept());

          de.setValueDomain(lookupValueDomain(de.getValueDomain()));
          newDe.setValueDomain(de.getValueDomain());

          String newDef = de.getPreferredDefinition();

          List l = dataElementDAO.find(newDe);
          //Did Extract Method refactoring
          /*de.setLongName(
            de.getDataElementConcept().getLongName() + " " +
            de.getValueDomain().getPreferredName());*/
          de.setLongName(this.deriveLongName(de));

          if (l.size() == 0) {
            de.setContext(defaults.getContext());
            de.setPreferredName(this.derivePreferredName(de));
            de.setVersion(defaults.getVersion());
            de.setWorkflowStatus(defaults.getWorkflowStatus());

            newDe.setAudit(defaults.getAudit());
            logger.debug("Creating DE: " + de.getLongName());
            List altNames = de.getAlternateNames();
            newDe = dataElementDAO.create(de);
            // restore altNames
            for(Iterator it2 = altNames.iterator(); it2.hasNext();) {
              AlternateName an = (AlternateName)it2.next();
              de.addAlternateName(an);
            }

            logger.info(PropertyAccessor.getProperty("created.de"));
          }
          else {
            newDe = (DataElement) l.get(0);
            logger.info(PropertyAccessor.getProperty("existed.de"));

            /* if DE alreay exists, check context
             * If context is different, add Used_by alt_name
             */
            if (newDe.getContext().getId() != defaults.getContext().getId()) {
              addAlternateName(
                newDe, defaults.getContext().getName(), AlternateName.TYPE_USED_BY,
                null);
            }

            if (
              (newDef.length() > 0) &&
                  !newDef.equals(newDe.getPreferredDefinition())) {
              System.out.println("Adding Alt Def");
              addAlternateDefinition(
                newDe, newDef, Definition.TYPE_UML_DE, packageName);
            }
          }

          LogUtil.logAc(newDe, logger);
          logger.info(
            PropertyAccessor.getProperty(
              "vd.preferredName", newDe.getValueDomain().getPreferredName()));

          addPackageClassification(newDe, packageName);

          List ans = de.getAlternateNames();
          for(Iterator it2 = ans.iterator(); it2.hasNext(); ) {
            AlternateName altName = (AlternateName)it2.next();
            
            addAlternateName(
              newDe, altName.getName(),
              altName.getType(), packageName);
          }
          it.set(newDe);
        }
        catch (PersisterException e) {
          logger.error("Could not persist DE: " + de.getLongName());
          logger.error(e.getMessage());
        } // end of try-catch
      }
    }
  }
  
  //Will need to declare this method as abstract method in UMLPersistor
  protected String derivePreferredName (AdminComponent ac ) {
    DataElement de = (DataElement)ac;
    String preferredName = de.getDataElementConcept().getPublicId() 
                          +DE_PREFERRED_NAME_DELIMITER
                          +de.getDataElementConcept().getVersion() 
                          +DE_PREFERRED_NAME_CONCAT_CHAR
                          +de.getValueDomain().getPublicId() 
                          +DE_PREFERRED_NAME_DELIMITER
                          +de.getValueDomain().getVersion();
    return preferredName;
  }
  
  //Will need to declare this method as abstract method in UMLPersistor
  protected String deriveLongName (AdminComponent ac ) {
    DataElement de = (DataElement)ac;
    String longName = de.getDataElementConcept().getLongName() 
                     + " "
                     +de.getValueDomain().getPreferredName();
    return longName;
  }
}
