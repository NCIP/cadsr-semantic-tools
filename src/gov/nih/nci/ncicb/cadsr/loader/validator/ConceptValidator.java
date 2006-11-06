package gov.nih.nci.ncicb.cadsr.loader.validator;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsResult;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class ConceptValidator implements Validator
{
  private ValidationItems items = ValidationItems.getInstance();
  
  private EvsModule module;
  
  private ProgressListener progressListener;
  
  public ConceptValidator()
  {
  }
  
  public void addProgressListener(ProgressListener l) {
    progressListener = l;
  }
  
  public ValidationItems validate() {

    module = new EvsModule(UserPreferences.getInstance().getPreTBox()?"PRE_NCI_Thesaurus":"NCI_Thesaurus");

    List<Concept> concepts = ElementsLists.getInstance().
            getElements(DomainObjectFactory.newConcept());
    
    final ExecutorService executor = Executors.newFixedThreadPool(10);
    final BlockingQueue<Future> futures = new LinkedBlockingQueue<Future>();
    final Map<Future,Concept> futureConceptMap = new HashMap<Future,Concept>();
    
    for(final Concept concept : concepts) 
    {
      Future<ValidatedConcept> future = executor.submit(new Callable<ValidatedConcept>() {
          public ValidatedConcept call() throws Exception {
            EvsResult result = module.findByConceptCode(concept.getPreferredName(), false);
            Collection<EvsResult> nameResult = 
                module.findByPreferredName(concept.getLongName(), false);
            return new ValidatedConcept(concept, nameResult, result);
          }
      });
      
      futures.add(future);
      futureConceptMap.put(future, concept);
    }

    int pStatus = 0;
    
    // wait for each future to complete
    for(Future<ValidatedConcept> future : futures) {

      final Concept con = futureConceptMap.get(future);
      if(progressListener != null) {
        ProgressEvent event = new ProgressEvent();
        event.setMessage("Validating " + con.getLongName());
        event.setStatus(pStatus++);
        progressListener.newProgressEvent(event);
      }

      ValidatedConcept vc = null;
      try {
        // block until the future is ready
        vc = future.get();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      EvsResult result = vc.getResult();
      if(result != null) {
        if(con.getLongName() == null || 
                !con.getLongName().equals(result.getConcept().getLongName())) {
          items.addItem(new ValidationError(
                  PropertyAccessor.getProperty(
                  "mismatch.name.by.code", con.getLongName()),
                  new ConceptMismatchWrapper(1,result.getConcept(),con)));
          //highlightDifferentNameByCode.add(result.getConcept());
          //errorList.put(con, result.getConcept());
        }
        if(con.getPreferredDefinition() == null || 
                !con.getPreferredDefinition().trim().equals(
                result.getConcept().getPreferredDefinition().trim())) {
          items.addItem(new MismatchDefByCodeError(
                  PropertyAccessor.getProperty(
                  "mismatch.def.by.code", con.getLongName()),new ConceptMismatchWrapper(2,result.getConcept(),con)));
          //highlightDifferentDefByCode.add(result.getConcept());
          //errorList.put(con, result.getConcept());
        }
      }

      Collection<EvsResult> nameResult = vc.getNameResult();
      if(nameResult != null && nameResult.size() == 1) 
      {
        for(EvsResult name : nameResult) { 
          if(con.getPreferredName() == null || 
                  !con.getPreferredName().equals(
                          name.getConcept().getPreferredName())) { 
           items.addItem(new ValidationError(
                    PropertyAccessor.getProperty(
                    "mismatch.code.by.name", con.getLongName()), 
                    new ConceptMismatchWrapper(3,name.getConcept(),con)));
              //highlightDifferentCodeByName.add(name.getConcept());
              //errorNameList.put(con, name.getConcept());
          }
          if(con.getPreferredDefinition() == null || 
                  !con.getPreferredDefinition().trim().equals(
                          name.getConcept().getPreferredDefinition().trim())) { 
            items.addItem(new ValidationError(
                    PropertyAccessor.getProperty(
                    "mismatch.def.by.name", con.getLongName()),
                    new ConceptMismatchWrapper(4,name.getConcept(),con)));
            //highlightDifferentDefByName.add(name.getConcept());      
            //errorNameList.put(con, name.getConcept());
          }
        }
      }
      
      // a slight delay so that we can see the progress in the UI, since the 
      // futures tend to complete in batches
      try {
          Thread.sleep(20);
      }
      catch (InterruptedException e) {
          e.printStackTrace();
      }
    }
    
    return items;
  }
  
  /**
   * These are the EVS query results for a given concept.
   */
  public class ValidatedConcept {
    private Concept concept;
    private Collection<EvsResult> nameResult;
    private EvsResult result;
    public ValidatedConcept(Concept concept, Collection<EvsResult> nameResult, EvsResult result) {
        this.concept = concept;
        this.nameResult = nameResult;
        this.result = result;
    }
    public Concept getConcept() {
        return concept;
    }
    public Collection<EvsResult> getNameResult() {
        return nameResult;
    }
    public EvsResult getResult() {
        return result;
    }

  }
}