package gov.nih.nci.ncicb.cadsr.loader.event;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.domain.*;

import java.util.List;

import org.apache.log4j.Logger;

public class XMIUMLListener implements UMLListener {

  private ElementsLists elements;
  private Logger logger = Logger.getLogger(XMIUMLListener.class.getName());

  public XMIUMLListener(ElementsLists elements) {
    this.elements = elements;
  }


  public void newPackage(NewPackageEvent event) {
    logger.debug("Package: " + event.getName());
  }
  public void newOperation(NewOperationEvent event) {
    logger.debug("Operation: " + event.getClassName() + "." + event.getName());
  }
  public void newClass(NewClassEvent event) {
//     logger.debug("Class: " + event.getName());
    ObjectClass oc = DomainObjectFactory.newObjectClass();
    oc.setLongName(event.getName());
    elements.addElement(oc);
  }
  public void newAttribute(NewAttributeEvent event) {
    logger.debug("Attribute: " + event.getClassName() + "." + event.getName());

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
    logger.debug("Interface: " + event.getName());
  }
  public void newStereotype(NewStereotypeEvent event) {
    logger.debug("Stereotype: " + event.getName());
  }
  public void newDataType(NewDataTypeEvent event) {
    logger.debug("DataType: " + event.getName());
  }


}