package gov.nih.nci.ncicb.cadsr.loader.event;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.FilterPackage;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.FilterClass;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import java.util.*;

public class GmePreHandler 
  implements UMLHandler {

  private List<String> packageList = new ArrayList<String>();

  private ElementsLists elements = ElementsLists.getInstance();

  public GmePreHandler() 
  {
  }

  public void newPackage(NewPackageEvent event) {
      elements.addElement(new FilterPackage(event.getName()));
      packageList.add(event.getName());
  }
  
  public void newClass(NewClassEvent event) {

  }


  public void newOperation(NewOperationEvent event){}
  public void newValueDomain(NewValueDomainEvent event){}
  public void newValueMeaning(NewValueMeaningEvent event){}
  public void newAttribute(NewAttributeEvent event){}
  public void newInterface(NewInterfaceEvent event){}
  public void newStereotype(NewStereotypeEvent event){}
  public void newDataType(NewDataTypeEvent event){}
  public void newAssociation(NewAssociationEvent event){}
  public void newGeneralization(NewGeneralizationEvent event){}
  public void addProgressListener(ProgressListener listener) {}
  public void beginParsing() {
    
    packageList = new ArrayList<String>();

    List<FilterClass> filterClasses = new ArrayList<FilterClass>(elements.getElements(new FilterClass("", "")));
    
    for(FilterClass o : filterClasses) {
      elements.removeElement(o);
    }

    List<FilterPackage> filterPackages = new ArrayList<FilterPackage>(elements.getElements(new FilterPackage("")));
    for(FilterPackage o : filterPackages) {
      elements.removeElement(o);
    }
    
  }
  public void endParsing() {}


}