package gov.nih.nci.ncicb.cadsr.loader.event;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.domain.*;

import java.util.List;

public class XMIUMLListener implements UMLListener {

  private ElementsLists elements;

  public XMIUMLListener(ElementsLists elements) {
    this.elements = elements;
  }


  public void newPackage(NewPackageEvent event) {
    System.out.println("Package: " + event.getName());
  }
  public void newOperation(NewOperationEvent event) {
    System.out.println("Operation: " + event.getClassName() + "." + event.getName());
  }
  public void newClass(NewClassEvent event) {
//     System.out.println("Class: " + event.getName());
    ObjectClass oc = DomainObjectFactory.newObjectClass();
    oc.setLongName(event.getName());
    elements.addElement(oc);
  }
  public void newAttribute(NewAttributeEvent event) {
    System.out.println("Attribute: " + event.getClassName() + "." + event.getName());

    Property prop = DomainObjectFactory.newProperty();
//     prop.setPreferredName(event.getName());
    prop.setLongName(event.getName());

    DataElementConcept dec = DomainObjectFactory.newDataElementConcept();
    dec.setLongName(event.getClassName() + event.getName());
    dec.setProperty(prop);

    
    ObjectClass oc = DomainObjectFactory.newObjectClass();
    List ocs = elements.getElements(oc.getClass());
    for(int i=0; i<ocs.size(); i++) {
      ObjectClass o = (ObjectClass)ocs.get(i);
      if(o.getLongName().equals(event.getClassName()))
	oc = o;
    }
    dec.setObjectClass(oc);

    
    DataElement de = DomainObjectFactory.newDataElement();
    de.setLongName(dec.getLongName() + event.getType());
    
    de.setDataElementConcept(dec);

    ValueDomain vd = DomainObjectFactory.newValueDomain();
    vd.setPreferredName(event.getType());
    de.setValueDomain(vd);

    elements.addElement(de);
    elements.addElement(dec);
    elements.addElement(prop);
    
  }
  public void newInterface(NewInterfaceEvent event) {
    System.out.println("Interface: " + event.getName());
  }
  public void newStereotype(NewStereotypeEvent event) {
    System.out.println("Stereotype: " + event.getName());
  }
  public void newDataType(NewDataTypeEvent event) {
    System.out.println("DataType: " + event.getName());
  }


}