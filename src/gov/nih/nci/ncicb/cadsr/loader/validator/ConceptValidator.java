package gov.nih.nci.ncicb.cadsr.loader.validator;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsResult;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import java.util.*;

public class ConceptValidator implements Validator
{

  private ElementsLists elements = ElementsLists.getInstance();

  private ValidationItems items = ValidationItems.getInstance();
  
  private EvsModule module = new EvsModule();
  
  private ProgressListener progressListener;
  
  public ConceptValidator()
  {
  }
  
  public void addProgressListener(ProgressListener l) {
    progressListener = l;
  }
  
  public ValidationItems validate() {
    List<Concept> concepts = ElementsLists.getInstance().
            getElements(DomainObjectFactory.newConcept());
    
    int pStatus = 0;
    for(Concept con : concepts) 
    {
      if(progressListener != null) {
          ProgressEvent event = new ProgressEvent();
          event.setMessage("Validating " + con.getLongName());
          event.setStatus(pStatus++);
          progressListener.newProgressEvent(event);
      }
      EvsResult result = null;
      
      //check to see if concept has already been searched by Concept Code
      //if(cacheByConceptCode.containsKey(con.getPreferredName())) 
      //  result = (EvsResult)cacheByConceptCode.get(con.getPreferredName());          
     // else
        result = module.findByConceptCode(con.getPreferredName(), false);
      if(result != null) 
      {  
        if(con.getLongName() == null || !con.getLongName().equals(result.getConcept().getLongName())) {
                items.addItem(new ValidationError(
                              PropertyAccessor.getProperty(
                              "mismatch.name.by.code", con.getLongName()),new ConceptMismatchWrapper(1,result.getConcept(),con)));
                //highlightDifferentNameByCode.add(result.getConcept());
                //errorList.put(con, result.getConcept());
              }
              if(con.getPreferredDefinition() == null || !con.getPreferredDefinition().trim().equals(result.getConcept().getPreferredDefinition().trim())) {
                items.addItem(new MismatchDefByCodeError(
                              PropertyAccessor.getProperty(
                              "mismatch.def.by.code", con.getLongName()),new ConceptMismatchWrapper(2,result.getConcept(),con)));
                //highlightDifferentDefByCode.add(result.getConcept());
                //errorList.put(con, result.getConcept());
              }
              //put concept in the cache
              //cacheByConceptCode.put(con.getPreferredName(), result);
      }
      Collection<EvsResult> nameResult = null;
          
          //check cache to see if concept has already been searched by Preferred Name
          //if(cacheByPreferredName.containsKey(con.getLongName()))
           // nameResult = (Collection<EvsResult>)cacheByPreferredName.get(con.getLongName());
          //else
            nameResult = module.findByPreferredName(con.getLongName(), false);
            
          if(nameResult != null && nameResult.size() == 1) 
          {
            for(EvsResult name : nameResult) { 
            if(con.getPreferredName() == null || !con.getPreferredName().equals(name.getConcept().getPreferredName())) { 
              items.addItem(new ValidationError(
                              PropertyAccessor.getProperty(
                              "mismatch.code.by.name", con.getLongName()), new ConceptMismatchWrapper(3,name.getConcept(),con)));
              //highlightDifferentCodeByName.add(name.getConcept());
              //errorNameList.put(con, name.getConcept());
            }
            if(con.getPreferredDefinition() == null || !con.getPreferredDefinition().trim().equals(name.getConcept().getPreferredDefinition().trim())) { 
              items.addItem(new ValidationError(
                              PropertyAccessor.getProperty(
                              "mismatch.def.by.name", con.getLongName()),new ConceptMismatchWrapper(4,name.getConcept(),con)));
              //highlightDifferentDefByName.add(name.getConcept());      
              //errorNameList.put(con, name.getConcept());
            }
          }
          //put concept in the cache
          //cacheByPreferredName.put(con.getLongName(), nameResult);
        }      
      
  }
  
  return items;
  }
}