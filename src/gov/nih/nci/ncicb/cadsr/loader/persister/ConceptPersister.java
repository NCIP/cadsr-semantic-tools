package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import org.apache.log4j.Logger;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;

import java.util.*;


public class ConceptPersister extends UMLPersister {

  private static Logger logger = Logger.getLogger(ConceptPersister.class.getName());

  public ConceptPersister(ElementsLists list) {
    this.elements = list;
    defaults = UMLDefaults.getInstance();
  }

  public void persist() {
    Concept con = DomainObjectFactory.newConcept();
    List cons = elements.getElements(con.getClass());

    logger.debug("ConceptPersister.persist()");

    if (cons != null) {
      for(Iterator it = cons.iterator(); it.hasNext();) {
        Concept c = (Concept)it.next();
        con.setPreferredName(c.getPreferredName());
        List l = conceptDAO.find(con);

        if(l.size() == 0) { // concept does not exist: create it
          c.setVersion(new Float(1.0f));
          c.setContext(defaults.getMainContext());
	  c.setWorkflowStatus(AdminComponent.WF_STATUS_RELEASED);
	  c.setAudit(defaults.getAudit());
          c.setId(conceptDAO.create(c));
          logger.info(PropertyAccessor.getProperty("created.concept"));
          LogUtil.logAc(c, logger);
        } else { // concept exist: See if we need to add alternate def.
          logger.info(PropertyAccessor.getProperty("existed.concept", c.getPreferredName()));
          Concept c2 = (Concept)l.get(0);
          c.setId(c2.getId());
          if(!c.getDefinitionSource().equalsIgnoreCase(c2.getDefinitionSource())) { // Add alt def.

            logger.debug("Concept " + c.getPreferredName() + " had different definition source. ");
            Definition def = DomainObjectFactory.newDefinition();
            def.setDefinition(c.getDefinitionSource() + " | " + c.getPreferredDefinition());
            def.setAudit(defaults.getAudit());
            def.setContext(defaults.getContext());
            adminComponentDAO.addDefinition(c, def);
            logger.info(PropertyAccessor.getProperty("added.altDef", new String[]{c.getPreferredName(), def.getDefinition(), "Concept"}));
          } else {
            // Do nothing, this is the common case where the concept existed and had the same def source.
          }
        }
      }
    }
  }


}
