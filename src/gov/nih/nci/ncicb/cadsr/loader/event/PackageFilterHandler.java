package gov.nih.nci.ncicb.cadsr.loader.event;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.FilterPackage;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.FilterClass;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import java.util.*;

public class PackageFilterHandler 
  implements UMLHandler {

  private List<String> packageList = new ArrayList<String>();

  private ElementsLists elements = ElementsLists.getInstance();

  public PackageFilterHandler() 
  {
  }

  public void newPackage(NewPackageEvent event) {
  }
  
  public void newClass(NewClassEvent event) {
    String pName = event.getPackageName();

    if (!packageList.contains(pName)) {
      elements.addElement(new FilterPackage(pName));
      packageList.add(pName);
    }

    elements.addElement(new FilterClass(event.getName(), pName));

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
  public void beginParsing() {}
  public void endParsing() {}


}