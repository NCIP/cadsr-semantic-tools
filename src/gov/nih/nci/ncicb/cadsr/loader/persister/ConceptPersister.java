package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import org.apache.log4j.Logger;
import gov.nih.nci.ncicb.cadsr.loader.UMLDefaults;

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
          c.setContext(defaults.getContext());
	  c.setWorkflowStatus(defaults.getWorkflowStatus());
	  c.setAudit(defaults.getAudit());
          c.setId(conceptDAO.create(c));
          logger.info("Created Concept: ");
          LogUtil.logAc(c, logger);
        } else { // concept exist: See if we need to add alternate def.
          // !! TODO
        }
      }
    }
  }


}
