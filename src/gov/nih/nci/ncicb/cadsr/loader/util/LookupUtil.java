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

}