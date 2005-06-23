package gov.nih.nci.ncicb.cadsr.loader.util;

import gov.nih.nci.ncicb.cadsr.domain.*;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import java.util.*;

public class ObjectUpdater {

  public static void update(AdminComponent ac, Concept[] oldConcepts, Concept[] newConcepts) {

    if(ac instanceof ObjectClass) {
      ObjectClass oc = (ObjectClass)ac;
      oc.setPreferredName(preferredNameFromConcepts(newConcepts));
    } else if(ac instanceof DataElement) {
      DataElement de = (DataElement)ac;

      de.getDataElementConcept().getProperty().setPreferredName(preferredNameFromConcepts(newConcepts));
      
    }

    addNewConcepts(newConcepts);
    removeStaleConcepts(oldConcepts);

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

  private static void removeStaleConcepts(Concept[] concepts) {
    ElementsLists elements = ElementsLists.getInstance();

    List<ObjectClass> ocs = (List<ObjectClass>)elements.getElements(DomainObjectFactory.newObjectClass().getClass());
    List<Property> props = (List<Property>)elements.getElements(DomainObjectFactory.newProperty().getClass());

    a:
    for(Concept concept : concepts) {
      boolean found = false;
      if(StringUtil.isEmpty(concept.getPreferredName()))
        continue a;
      for(ObjectClass oc : ocs) {
        String[] codes = oc.getPreferredName().split(":");
        for(String code : codes) {
          if(code.equals(concept.getPreferredName())) {
            found = true;
            continue a;
          }
        }
      }
      for(Property prop : props) {
        String[] codes = prop.getPreferredName().split(":");
        for(String code : codes) {
          if(code.equals(concept.getPreferredName())) {
            found = true;
            continue a;
          }
        }
      }

      if(!found) {
        removeFromConcepts(concept);
      }
    }
  }

  private static void addNewConcepts(Concept[] newConcepts) {
    ElementsLists elements = ElementsLists.getInstance();
    List<Concept> concepts = (List<Concept>)elements.getElements(DomainObjectFactory.newConcept().getClass());
    
    for(Concept concept : newConcepts) {
      boolean found = false;
      for(Concept con : concepts) {
        if(con.getPreferredName().equals(concept.getPreferredName())) {
          found = true;
          break;
        }
      }
      if(!found) {
        elements.addElement(concept);
      }
    }


  }

  private static void removeFromConcepts(Concept concept) {
    ElementsLists elements = ElementsLists.getInstance();
    List<Concept> concepts = (List<Concept>)elements.getElements(DomainObjectFactory.newConcept().getClass());
    
    for(int i = 0, n = concepts.size(); i<n; i++) {
      if(concepts.get(i).getPreferredName().equals(concept.getPreferredName())) {
        concepts.remove(i);
        return;
      }
    }
    
    concepts.remove(concept);

  }

}