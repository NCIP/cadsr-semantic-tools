package gov.nih.nci.ncicb.cadsr.loader.util;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import java.util.*;

public class LookupUtil {

  public static Concept lookupConcept(String conceptCode) {
    List concepts = (List) ElementsLists.getInstance().getElements(DomainObjectFactory.newConcept().getClass());
    
    for (Iterator it = concepts.iterator(); it.hasNext();) {
      Concept con = (Concept)it.next();
      
      if(con.getPreferredName() != null)
        if(con.getPreferredName().equals(conceptCode))
          return con;
    }
    return null;

  }
  
  public static List<ObjectClassRelationship> lookupOcrs(ObjectClass oc) {
    List ocrs = (List)ElementsLists.getInstance().getElements(DomainObjectFactory.newObjectClassRelationship().getClass());

    List<ObjectClassRelationship> result = new ArrayList<ObjectClassRelationship>();

    for (Iterator it = ocrs.iterator(); it.hasNext();) {
      ObjectClassRelationship ocr = (ObjectClassRelationship)it.next();
      
      // Lookup by reference
      if((ocr.getSource() == oc) || (ocr.getTarget() == oc))
        result.add(ocr);
    }
    return result;

  }

}