package gov.nih.nci.ncicb.cadsr.loader.event;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.model.*;

public class UMLListener implements LoaderListener {

  private ElementsLists elements;

  public UMLListener(ElementsLists elements) {
    this.elements = elements;
  }

  public void notification(LoaderEvent event) {

    if(event instanceof NewPackageEvent)
      this.newEvent((NewPackageEvent)event);
    else if(event instanceof NewClassEvent)
      this.newEvent((NewClassEvent)event);
    else if(event instanceof NewOperationEvent)
      this.newEvent((NewOperationEvent)event);
    else if(event instanceof NewAttributeEvent)
      this.newEvent((NewAttributeEvent)event);
    else if(event instanceof NewStereotypeEvent)
      this.newEvent((NewStereotypeEvent)event);
    else if(event instanceof NewDataTypeEvent)
      this.newEvent((NewDataTypeEvent)event);
    else if(event instanceof NewInterfaceEvent)
      this.newEvent((NewInterfaceEvent)event);
    else
      newEvent(event);

  }

  private void newEvent(LoaderEvent event) {
    System.out.println("???? Unknown event: " + event.getClass());
  }

  private void newEvent(NewPackageEvent event) {
    System.out.println("Package: " + event.getName());
  }
  private void newEvent(NewOperationEvent event) {
    System.out.println("Operation: " + event.getClassName() + "." + event.getName());
  }
  private void newEvent(NewClassEvent event) {
    System.out.println("Class: " + event.getName());
    ObjectClass oc = new ObjectClass();
  }
  private void newEvent(NewAttributeEvent event) {
    System.out.println("Attribute: " + event.getClassName() + "." + event.getName());
  }
  private void newEvent(NewInterfaceEvent event) {
    System.out.println("Interface: " + event.getName());
  }
  private void newEvent(NewStereotypeEvent event) {
    System.out.println("Stereotype: " + event.getName());
  }
  private void newEvent(NewDataTypeEvent event) {
    System.out.println("DataType: " + event.getName());
  }


}