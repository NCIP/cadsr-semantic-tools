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
    List<Concept> cons = (List<Concept>) elements.getElements(con.getClass());

    logger.debug("ConceptPersister.persist()");

    if (cons != null) {
      for(Concept c : cons) {
        con.setPublicId(c.getPublicId());
        List l = conceptDAO.find(con);
        if(l == 0) { // concept does not exist: create it
          
        } else { // concept exist: See if we need to add alternate def.
          
        }
      }
    }
  }


}
