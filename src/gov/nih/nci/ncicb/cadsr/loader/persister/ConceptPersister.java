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

  }


}
