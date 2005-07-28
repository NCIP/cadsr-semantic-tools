package gov.nih.nci.ncicb.cadsr.loader.ext;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.nih.nci.ncicb.cadsr.evs.*;

public class EvsModule 
{

  private static EVSQueryService evsService = new EVSQueryService();

  public EvsModule()
  {
  }
  
  public EvsResult findByConceptCode(String code, boolean includeRetired) 
  {

    try {
      List<EVSConcept> evsConcepts = (List<EVSConcept>)evsService.findConceptsByCode(code, includeRetired, 100);
      
      for(EVSConcept evsConcept : evsConcepts) {
        return evsConceptToEvsResult(evsConcept);
      }
    } catch (Exception e){
      e.printStackTrace();
    } // end of try-catch

    return null;
  }
  
  public Collection<EvsResult> findBySynonym(String s, boolean includeRetired) 
  {
    Collection<EvsResult> result = new ArrayList();

    try {
      List<EVSConcept> evsConcepts = (List<EVSConcept>)evsService.findConceptsBySynonym(s, includeRetired, 100);
      
      for(EVSConcept evsConcept : evsConcepts) {
        result.add(evsConceptToEvsResult(evsConcept));
      }
    } catch (Exception e){
      e.printStackTrace();
    } // end of try-catch

    return result;
  }

  private EvsResult evsConceptToEvsResult(EVSConcept evsConcept) {

    Concept c = DomainObjectFactory.newConcept();
    c.setPreferredName(evsConcept.getCode());
    c.setLongName(evsConcept.getPreferredName());

    gov.nih.nci.evs.domain.Definition def = null;

    if(evsConcept.getDefinitions().size() > 0) {
      def = (gov.nih.nci.evs.domain.Definition)evsConcept.getDefinitions().get(0);
    }

    if(def != null) {
      c.setPreferredDefinition(def.getDefinition());
      c.setDefinitionSource(def.getSource().getAbbreviation());
    } else 
      c.setPreferredDefinition("");
    
    String[] syns = new String[evsConcept.getSynonyms().size()];
    evsConcept.getSynonyms().toArray(syns);
    
    return new EvsResult(c, syns);
    

  }
}
