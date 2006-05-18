package gov.nih.nci.ncicb.cadsr.semconn;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.loader.ChangeTracker;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.ReviewTracker;
import gov.nih.nci.ncicb.cadsr.loader.event.*;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsResult;

import gov.nih.nci.ncicb.cadsr.loader.parser.Parser;
import gov.nih.nci.ncicb.cadsr.loader.util.ConceptUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class SemConnEventHandler implements UMLHandler
{
  private EvsModule evsModule = new EvsModule();
  
  private ElementsLists elements;
  private ReviewTracker reviewTracker = ReviewTracker.getInstance();

  private Map<String,Collection<Concept>> cache = new HashMap();

  private static Logger logger = Logger.getLogger(SemConnEventHandler.class.getName());

  public SemConnEventHandler()
  {
    this.elements = ElementsLists.getInstance();
  }
  
  public void setParser(Parser parser) 
  {
    
  }
  
  public void newPackage(NewPackageEvent event) 
  {
    
  }
  public void newOperation(NewOperationEvent event) 
  {
    
  }
  public void newClass(NewClassEvent event) 
  {
  
    ObjectClass oc = DomainObjectFactory.newObjectClass();
 
    String className = event.getName();
    List<Concept> concepts = termToConcepts(className);
      
    oc.setPreferredName(ConceptUtil.preferredNameFromConcepts(concepts));
    oc.setLongName(event.getName());
    
    AlternateName fullName = DomainObjectFactory.newAlternateName();
    fullName.setType(AlternateName.TYPE_CLASS_FULL_NAME);
    fullName.setName(event.getName());
    AlternateName classAltName = DomainObjectFactory.newAlternateName();
    classAltName.setType(AlternateName.TYPE_UML_CLASS);
    classAltName.setName(event.getName().substring(event.getName().lastIndexOf(".") + 1));

    oc.addAlternateName(fullName);
    oc.addAlternateName(classAltName);
    
    reviewTracker.put(event.getName(), false);
    ChangeTracker.getInstance().put(event.getName(), true);

    elements.addElement(oc);
    
  }
  public void newValueDomain(NewValueDomainEvent event) 
  {
  }
  public void newValueMeaning(NewValueMeaningEvent event) {}
  public void newAttribute(NewAttributeEvent event){
    DataElement de = DomainObjectFactory.newDataElement();
    
    String propName = event.getName();
    List<Concept> concepts = termToConcepts(propName);
    
    de.setLongName(event.getName());
    
    String s = event.getClassName();
    int ind = s.lastIndexOf(".");
    String className = s.substring(ind + 1);
    String packageName = s.substring(0, ind);
    
    Property prop = DomainObjectFactory.newProperty();

    // store concept codes in preferredName
    prop.setPreferredName(ConceptUtil.preferredNameFromConcepts(concepts));

    //     prop.setPreferredName(event.getName());
    prop.setLongName(event.getName());
    
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
    
  }
  public void newInterface(NewInterfaceEvent event){}
  public void newStereotype(NewStereotypeEvent event){}
  public void newDataType(NewDataTypeEvent event){}
  public void newAssociation(NewAssociationEvent event){}
  public void newGeneralization(NewGeneralizationEvent event){}
  
  public Collection<Concept> searchEvs(String word) 
  {
    if(cache.containsKey(word.toLowerCase())) {
      logger.debug(word + " found in cache");
      return cache.get(word.toLowerCase());
    }
    
    logger.debug("Search for EVS: " + word);
    Collection<EvsResult> evsResult = evsModule.findBySynonym(word,false);
    Collection<Concept> result = new ArrayList();
    for(EvsResult  er : evsResult)
      if(er.getConcept().getPreferredName().equals(word))
        result.add(er.getConcept());
   
    if(result.isEmpty())
      for(EvsResult  er : evsResult)
        result.add(er.getConcept());
      //result = evsResult;
    cache.put(word.toLowerCase(),result);
    return result;  
  }
  
  private List<Concept> termToConcepts(String term)
  {
    List<Concept> found = new ArrayList();
    List<String> options = new ArrayList();
    List<String> words = new ArrayList();
    SemmConnUtility.evaluateString(term, options, words);
//    ProgressEvent event = new ProgressEvent();
//    event.setMessage("Searching EVS...");
//    event.setGoal(options.size()); 
//    int i = 1;
    for(String s : options) {
//      event.setMessage("Searching: " + s);
//      event.setStatus(i++);
      List<Concept> result = (List<Concept>)searchEvs(s);
      if(!result.isEmpty() && found.isEmpty()) 
        found.addAll(result);
    }
    
    if(found.isEmpty()) {
//      //make OC here
//      oc.setPreferredName(ConceptUtil.preferredNameFromConcepts(found));
//      oc.setLongName(event.getName());
//      //return;

      for(String s : words) {
        List<Concept> result2 = (List<Concept>)searchEvs(s);
      if(!result2.isEmpty() && found.isEmpty()) 
        found.addAll(result2);
        //return;
      }
  }
  
  for(Concept con : found) 
  {
   ElementsLists.getInstance().addElement(con); 
  }
  
  return found;
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
    
    SemConnEventHandler handler = new SemConnEventHandler();
    for(int i=0; i<testData.length; i++) 
    {
      List<Concept> concepts = handler.termToConcepts(testData[i]);
      String prefName = ConceptUtil.preferredNameFromConcepts(concepts);
      System.out.println(prefName.equals(testResults[i]));
    }
    
  }
}