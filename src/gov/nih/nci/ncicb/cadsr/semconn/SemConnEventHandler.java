package gov.nih.nci.ncicb.cadsr.semconn;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ChangeTracker;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.ReviewTracker;
import gov.nih.nci.ncicb.cadsr.loader.ReviewTrackerType;
import gov.nih.nci.ncicb.cadsr.loader.util.RunMode;
import gov.nih.nci.ncicb.cadsr.loader.event.*;

import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;
import java.util.*;
import java.util.concurrent.*;

import org.apache.log4j.Logger;

public class SemConnEventHandler implements UMLHandler {

  private ElementsLists elements;
  private ReviewTracker reviewTracker = ReviewTracker.getInstance(ReviewTrackerType.Curator);

  private static Logger logger = Logger.getLogger(SemConnEventHandler.class);

  // support for asynchronous EVS queries
  private final ExecutorService executor = Executors.newFixedThreadPool(10);
  private final BlockingQueue<FutureName> futures = new LinkedBlockingQueue<FutureName>();
  private final AsyncConceptConsumer conceptConsumer = new AsyncConceptConsumer();
  private final Thread conceptConsumerThread = new Thread(conceptConsumer);
  
  private ProgressListener progressListener;
  
  public SemConnEventHandler()
  {
    this.elements = ElementsLists.getInstance();
  }
  
  public void newPackage(NewPackageEvent event) {}
  public void newOperation(NewOperationEvent event) {}
  
  public void newClass(NewClassEvent event) 
  {
    if(event.isReviewed())
      return;
    
    String className = event.getName();

    ObjectClass oc = DomainObjectFactory.newObjectClass();
    oc.setLongName(className);
    AlternateName fullName = DomainObjectFactory.newAlternateName();
    fullName.setType(AlternateName.TYPE_CLASS_FULL_NAME);
    fullName.setName(className);
    AlternateName classAltName = DomainObjectFactory.newAlternateName();
    classAltName.setType(AlternateName.TYPE_UML_CLASS);
    classAltName.setName(className.substring(className.lastIndexOf(".") + 1));
    oc.addAlternateName(fullName);
    oc.addAlternateName(classAltName);

    reviewTracker.put(className, false);
    ChangeTracker.getInstance().put(className, true);
    
    // asynchronously look up EVS concept
    Future<String> preferredName = termToPreferredName(className);
    futures.add(new FutureName(oc, preferredName));
    
    elements.addElement(oc);
  }
  
  public void newValueDomain(NewValueDomainEvent event) 
  {
    //List<Concept> concepts = createConcepts(event);

    ValueDomain vd = DomainObjectFactory.newValueDomain();

    vd.setLongName(event.getName());
    vd.setPreferredDefinition(event.getDescription());
    vd.setVdType(event.getType());
    vd.setDataType(event.getDatatype());

    //ConceptualDomain cd = DomainObjectFactory.newConceptualDomain();
    //cd.setPublicId(event.getCdId());
    //cd.setVersion(event.getCdVersion());

    //vd.setConceptualDomain(cd);
    
    //     if(concepts.size() > 0)
    //vd.setConceptDerivationRule(createConceptDerivationRule(concepts));

    elements.addElement(vd);
    reviewTracker.put(event.getName(), event.isReviewed());
  }
  public void newValueMeaning(NewValueMeaningEvent event) 
  {
    if(event.isReviewed())
      return;
    
     String vmName = event.getName();
     
     ValueDomain vd = LookupUtil.lookupValueDomain(event.getValueDomainName());
     
     ValueMeaning vm = DomainObjectFactory.newValueMeaning();
     vm.setLongName(event.getName());
     
     //vm.setConceptDerivationRule(createConceptDerivationRule(concepts));
     
     PermissibleValue pv = DomainObjectFactory.newPermissibleValue();
     pv.setValueMeaning(vm);
     pv.setValue(event.getName());
     
     vd.addPermissibleValue(pv);
     
     elements.addElement(vm);
     reviewTracker.put("ValueDomains." + event.getValueDomainName() + "." + event.getName(), event.isReviewed());
     ChangeTracker.getInstance().put("ValueDomains." + event.getValueDomainName() + "." + event.getName(), true);
     
     Future<String> preferredName = termToPreferredName(vmName);
     futures.add(new FutureName(vm, preferredName));     
  }
  
  public void newAttribute(NewAttributeEvent event)
  {
    if(event.isReviewed())
      return;

    String propName = event.getName();
      
    DataElement de = DomainObjectFactory.newDataElement();
    de.setLongName(propName);

    if(event.getPersistenceId() != null) {
      de.setPublicId(event.getPersistenceId());
      de.setVersion(event.getPersistenceVersion());
    }

    String s = event.getClassName();
    int ind = s.lastIndexOf(".");
    String className = s.substring(ind + 1);
    String packageName = s.substring(0, ind);
    
    Property prop = DomainObjectFactory.newProperty();
    prop.setLongName(propName);
    
    DataElementConcept dec = DomainObjectFactory.newDataElementConcept();
    dec.setLongName(className + ":" + propName);
    dec.setProperty(prop);
    
    ObjectClass oc = DomainObjectFactory.newObjectClass();
    List<ObjectClass> ocs = elements.getElements(oc);
    
    for (ObjectClass o : ocs) {
      String fullClassName = null;
      for(AlternateName an : o.getAlternateNames()) {
        if(an.getType().equals(AlternateName.TYPE_CLASS_FULL_NAME))
          fullClassName = an.getName();
      }
      if (fullClassName.equals(event.getClassName())) {
        oc = o;
      }
    }
    
    dec.setObjectClass(oc);
    de.setDataElementConcept(dec);

    ValueDomain vd = DomainObjectFactory.newValueDomain();

    if(event.getTypeId() != null) {
      vd.setPublicId(event.getTypeId());
      vd.setVersion(event.getTypeVersion());
    }
    
    de.setValueDomain(vd);
    
    // Store alt Name for DE:
    // packageName.ClassName.PropertyName
    AlternateName fullName = DomainObjectFactory.newAlternateName();
    fullName.setType(AlternateName.TYPE_FULL_NAME);
    fullName.setName(packageName + "." + className + "." + propName);
    de.addAlternateName(fullName);
    
    reviewTracker.put(fullName.getName(), false);    
    ChangeTracker.getInstance().put(fullName.getName(), true);    
    
    // Store alt Name for DE:
    // ClassName:PropertyName
    fullName = DomainObjectFactory.newAlternateName();
    fullName.setType(AlternateName.TYPE_UML_DE);
    fullName.setName(className + ":" + propName);
    de.addAlternateName(fullName);
    
    elements.addElement(de);
    elements.addElement(dec);
    elements.addElement(prop);

    // asynchronously look up EVS concept
    Future<String> preferredName = termToPreferredName(propName);
    futures.add(new FutureName(prop, preferredName));
  }
  
  public void newInterface(NewInterfaceEvent event){}
  public void newStereotype(NewStereotypeEvent event){}
  public void newDataType(NewDataTypeEvent event){}
  public void newAssociation(NewAssociationEvent event){}
  public void newGeneralization(NewGeneralizationEvent event){}

  public void beginParsing() {
      logger.info("Starting concept consumer thread");
      conceptConsumerThread.start();
  }
  
  public void endParsing() {
    // tell the consumer that parsing is over
    conceptConsumer.endWhenQueueIsEmpty();
    
    try {
        // upgrade progress bar with what's left to do
        ProgressEvent evt = new ProgressEvent();
        evt.setMessage("Searching");
        evt.setGoal(futures.size());
        evt.setStatus(0);
        fireProgressEvent(evt);
        
        // wait for consumer thread to finish
        logger.info("Waiting for concept consumer thread");
        conceptConsumerThread.join();
        logger.info("Joined with concept consumer thread");
    }
    catch (InterruptedException e) {
        // ignore
    }
  }
  
  private ConceptDerivationRule createConceptDerivationRule(List<Concept> concepts) {

    ConceptDerivationRule condr = DomainObjectFactory.newConceptDerivationRule();
    List<ComponentConcept> compCons = new ArrayList<ComponentConcept>();
 
    int c = 0;
    for(Concept con : concepts) {
      ComponentConcept compCon = DomainObjectFactory.newComponentConcept();
      compCon.setConcept(con);
      compCon.setOrder(concepts.size() - 1 - c);
      compCon.setConceptDerivationRule(condr);
      compCons.add(compCon);
      c++;
    }

    condr.setComponentConcepts(compCons);
    return condr;
    

  }

  /**
   * Returns a future preferred name for the specified term.
   */
  private Future<String> termToPreferredName(String term) {
      return executor.submit(new AsyncConceptQuery(term));
  }

  /**
   * Groups an AdminComponent with its future name for processing.
   * 
   * @author <a href="mailto:rokickik@mail.nih.gov">Konrad Rokicki</a>
   */
  private class FutureName {
      private AdminComponent ac;
      private Future<String> name;
      public FutureName(AdminComponent ac, Future<String> name) {
        this.ac = ac;
        this.name = name;
      }
      public AdminComponent getComponent() {
        return ac;
      }
      public Future<String> getName() {
          return name;
      }
  }
  
  /**
   * Consumes the results of finished EVS concept queries. This class runs
   * in a thread and consumes FutureNames until parsing is finished.
   * 
   * @author <a href="mailto:rokickik@mail.nih.gov">Konrad Rokicki</a>
   */
  private class AsyncConceptConsumer implements Runnable {
    private boolean running = true;
    private int numConsumed = 0;
      
    public void run()  {
        
        // keep consuming until parsing is over and 
        // the futures queue has been depleted 
        while (running || !futures.isEmpty()) {
            try  {
                FutureName fname = futures.poll(1, TimeUnit.SECONDS);
                if (fname != null) {
                    
                    AdminComponent ac = fname.getComponent();
                    if(ac instanceof ValueMeaning) 
                    {
                      String codes = fname.getName().get();
                      String temp[] = codes.split(":");
                      List<Concept> listOfConcepts = new ArrayList();
                      for(int i=0; i < temp.length; i++) 
                      {
                        Concept con = DomainObjectFactory.newConcept();
                        con.setPreferredName(temp[i]);
                        listOfConcepts.add(con);
                        
                      }
                      ValueMeaning vm = (ValueMeaning)ac;
                      vm.setConceptDerivationRule(createConceptDerivationRule(listOfConcepts));  
                      
                    } else {
                      ac.setPreferredName(fname.getName().get());
                    }
                    
                    numConsumed++; 
                    ProgressEvent evt = new ProgressEvent();
                    String name = fname.getComponent().getLongName();
                    evt.setMessage("Searching " + name);
                    evt.setStatus(numConsumed);
                    fireProgressEvent(evt);
                    
                    // Since these threads tend to finish in batches, we need to
                    // slow things down just a little bit so that each message
                    // can be displayed on the screen, albeit very quickly.
                    Thread.sleep(20);
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void endWhenQueueIsEmpty() {
        running = false;
    }
  }

  private void fireProgressEvent(ProgressEvent evt) {
    if(progressListener != null)
      progressListener.newProgressEvent(evt);
  }

  public void addProgressListener(ProgressListener listener) {
    progressListener = listener;
  }
  
  public static void main(String[] args) 
  {
    String[] testData = 
    {
      "id"
    };
    String[] testResults =  
    {
      "Gene", "C12345:C45678", "C12345:C45678"
    };
    
    /* KR - API changed, test harness must be rewritten 
    SemConnEventHandler handler = new SemConnEventHandler();
    for(int i=0; i<testData.length; i++) 
    {
      List<Concept> concepts = handler.termToConcepts(testData[i]);
      String prefName = ConceptUtil.preferredNameFromConcepts(concepts);
      System.out.println(prefName.equals(testResults[i]));
    }
    */
  }
}
