package gov.nih.nci.ncicb.cadsr.loader.validator;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModuleListener;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsResult;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class ConceptValidator implements Validator, CadsrModuleListener
{
  private ValidationItems items = ValidationItems.getInstance();
  
  private EvsModule evsModule;
  private CadsrModule cadsrModule;
  
  private ProgressListener progressListener;
  
  public ConceptValidator()
  {
  }
  
  public void addProgressListener(ProgressListener l) {
    progressListener = l;
  }
  
  public ValidationItems validate() {
  
    items = ValidationItems.getInstance();

    evsModule = new EvsModule(UserPreferences.getInstance().getPreTBox()?"Pre_NCI_Thesaurus":"NCI_Thesaurus");

    List<Concept> concepts = ElementsLists.getInstance().
            getElements(DomainObjectFactory.newConcept());
    
    final ExecutorService executor = Executors.newFixedThreadPool(1);
    final BlockingQueue<Future> futures = new LinkedBlockingQueue<Future>();
    final Map<Future,Concept> futureConceptMap = new HashMap<Future,Concept>();
    
    Set<String> seenConcepts = new HashSet<String>();
    
    for(final Concept concept : concepts) 
    {
        if (seenConcepts.contains(concept.getPreferredName())) {
            continue;
        }
        seenConcepts.add(concept.getPreferredName());
        
        Future<ValidatedConcept> future = executor.submit(new Callable<ValidatedConcept>() {
          public ValidatedConcept call() throws Exception {
            EvsResult result = evsModule.findByConceptCode(concept.getPreferredName().toUpperCase(), false);
            Collection<EvsResult> nameResult = 
                evsModule.findBySynonym(concept.getLongName(), false);
            
            Concept cadsrConcept = cadsrModule.findConceptByCode(concept.getPreferredName()); 
                
            return new ValidatedConcept(concept, nameResult, result, cadsrConcept);
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

      EvsResult evsByCodeResult = null;
      Collection<EvsResult> evsByNameResult = null;
      Concept cadsrByCodeResult = null;
      try {
        // block until the future is ready
        ValidatedConcept vc = future.get();
        evsByCodeResult = vc.getEvsByCodeResult();
        evsByNameResult = vc.getEvsByNameResult();
        cadsrByCodeResult = vc.getCadsrByCode();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      if(evsByCodeResult != null) {
        if(con.getLongName() == null || 
                !con.getLongName().equals(evsByCodeResult.getConcept().getLongName())) {
          items.addItem(new ValidationError(
                  PropertyAccessor.getProperty(
                  "mismatch.name.by.code", con.getLongName()),
                  new ConceptMismatchWrapper(1,evsByCodeResult.getConcept(),con)));
        }
        if(con.getPreferredDefinition() == null || 
                !con.getPreferredDefinition().trim().equals(
                evsByCodeResult.getConcept().getPreferredDefinition().trim())) {
          items.addItem(new MismatchDefByCodeError(
                  PropertyAccessor.getProperty(
                  "mismatch.def.by.code", con.getLongName()),new ConceptMismatchWrapper(2,evsByCodeResult.getConcept(),con)));
        }
      }

      if(evsByNameResult != null && evsByNameResult.size() == 1) 
      {
        for(EvsResult name : evsByNameResult) { 
          if(con.getPreferredName() == null || 
                  !con.getPreferredName().equals(
                          name.getConcept().getPreferredName())) { 
           items.addItem(new ValidationError(
                    PropertyAccessor.getProperty(
                    "mismatch.code.by.name", con.getLongName()), 
                    new ConceptMismatchWrapper(3,name.getConcept(),con)));
          }
          if(con.getPreferredDefinition() == null || 
                  !con.getPreferredDefinition().trim().equals(
                          name.getConcept().getPreferredDefinition().trim())) { 
            items.addItem(new ValidationError(
                    PropertyAccessor.getProperty(
                    "mismatch.def.by.name", con.getLongName()),
                    new ConceptMismatchWrapper(4,name.getConcept(),con)));
          }
        }
      }

      if(cadsrByCodeResult == null) {
        if(evsByCodeResult == null) {
          items.addItem(new ValidationError
                        (PropertyAccessor.getProperty
                         ("concept.not.in.cadsr.or.evs", con.getLongName()), 
                         con));
        } else {
          if(!con.getPreferredName().equals(evsByCodeResult.getConcept().getPreferredName())) {
            items.addItem(new ValidationError
                          (PropertyAccessor.getProperty
                           ("concept.not.in.cadsr.different.name", con.getLongName()), 
                           con));
          } else if(!con.getPreferredDefinition().equals(evsByCodeResult.getConcept().getPreferredDefinition())) {
            items.addItem(new ValidationError
                          (PropertyAccessor.getProperty
                           ("concept.not.in.cadsr.different.definition", con.getLongName()), 
                           con));
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
    private Concept localConcept;
    private Collection<EvsResult> evsByNameResult;
    private EvsResult evsByCodeResult;
    
    private Concept cadsrByCode;

    public ValidatedConcept(Concept localConcept, Collection<EvsResult> evsByNameResult, EvsResult evsByCodeResult, Concept cadsrByCode) {
      this.localConcept = localConcept;
      this.evsByNameResult = evsByNameResult;
      this.evsByCodeResult = evsByCodeResult;
      this.cadsrByCode = cadsrByCode;
    }
    public Concept getLocalConcept() {
      return localConcept;
    }
    public Collection<EvsResult> getEvsByNameResult() {
      return evsByNameResult;
    }
    public EvsResult getEvsByCodeResult() {
      return evsByCodeResult;
    }
    public Concept getCadsrByCode() {
        return cadsrByCode;
    }

  }
  
  public void setCadsrModule(CadsrModule cadsrModule) {
      this.cadsrModule = cadsrModule;
  }
  
}