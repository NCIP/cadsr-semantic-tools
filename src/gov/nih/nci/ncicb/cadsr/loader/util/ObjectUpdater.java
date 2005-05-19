package gov.nih.nci.ncicb.cadsr.loader.util;

import gov.nih.nci.ncicb.cadsr.domain.*;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import java.util.*;

public class ObjectUpdater {

  public static void update(AdminComponent ac, Concept oldConcepts, Concept newConcepts) {

    ElementsLists elements = ElementsLists.getInstance();

    List concepts = elements.getElements(DomainObjectFactory.newConcept().getClass());
    
    List ocs = elements.getElements(DomainObjectFactory.newObjectClass().getClass());

    List props = elements.getElements(DomainObjectFactory.newObjectClass().getClass());

    


  }

  public static String preferredNameFromConcepts(List<Concept> concepts) {
    StringBuffer sb = new StringBuffer();
    for(Concept con : concepts) {
      if(sb.length() > 0)
        sb.insert(0, ":");
      sb.insert(0, con.getPreferredName());
    }
    return sb.toString();
  }

  public static String preferredNameFromConcepts(Concept[] concepts) {
    StringBuffer sb = new StringBuffer();
    for(Concept con : concepts) {
      if(sb.length() > 0)
        sb.insert(0, ":");
      sb.insert(0, con.getPreferredName());
    }
    return sb.toString();
  }


}