package gov.nih.nci.ncicb.cadsr.semconn;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.loader.ChangeTracker;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.ReviewTracker;
import gov.nih.nci.ncicb.cadsr.loader.ReviewTrackerType;
import gov.nih.nci.ncicb.cadsr.loader.util.RunMode;
import gov.nih.nci.ncicb.cadsr.loader.event.NewAssociationEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.NewAttributeEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.NewClassEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.NewDataTypeEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.NewGeneralizationEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.NewInterfaceEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.NewOperationEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.NewPackageEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.NewStereotypeEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.NewValueDomainEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.NewValueMeaningEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.event.UMLHandler;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

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
  
  public void newValueDomain(NewValueDomainEvent event) {}
  public void newValueMeaning(NewValueMeaningEvent event) {}
  
  public void newAttribute(NewAttributeEvent event)
  {
    if(event.isReviewed())
      return;

    String propName = event.getName();
      
    DataElement de = DomainObjectFactory.newDataElement();
    de.setLongName(propName);
    
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
                    fname.getComponent().setPreferredName(fname.getName().get());
                    
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
