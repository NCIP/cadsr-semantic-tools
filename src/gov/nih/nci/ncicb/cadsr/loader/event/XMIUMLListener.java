package gov.nih.nci.ncicb.cadsr.loader.event;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import org.apache.log4j.Logger;

import java.util.*;


/**
 * This class implements UMLListener specifically to handle events coming from an XMI Parser.<br/> The listener's responsibility is to transform events received into cadsr domain objects, and store those objects in the Elements List.
 *
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class XMIUMLListener implements UMLListener {
  private ElementsLists elements;
  private Logger logger = Logger.getLogger(XMIUMLListener.class.getName());
  private List packageList = new ArrayList();
  
  public XMIUMLListener(ElementsLists elements) {
    this.elements = elements;
  }

  public void newPackage(NewPackageEvent event) {
    logger.debug("Package: " + event.getName());
  }

  public void newOperation(NewOperationEvent event) {
    logger.debug("Operation: " + event.getClassName() + "." +
                 event.getName());
  }

  public void newClass(NewClassEvent event) {
    logger.debug("Class: " + event.getName());
    
    Concept concept = newConcept(event);
    
    ObjectClass oc = DomainObjectFactory.newObjectClass();
    oc.setConcept(concept);
    oc.setLongName(event.getName());
    elements.addElement(oc);

    if (!packageList.contains(event.getPackageName())) {
      ClassificationSchemeItem csi = DomainObjectFactory.newClassificationSchemeItem();
      csi.setName(event.getPackageName());
      elements.addElement(csi);
      packageList.add(event.getPackageName());
    }
  }

  public void newAttribute(NewAttributeEvent event) {
    logger.debug("Attribute: " + event.getClassName() + "." +
                 event.getName());

    Concept concept = newConcept(event);

    Property prop = DomainObjectFactory.newProperty();

    //     prop.setPreferredName(event.getName());
    prop.setLongName(event.getName());
    prop.setConcept(concept);

    DataElementConcept dec = DomainObjectFactory.newDataElementConcept();
    dec.setLongName(event.getClassName() + event.getName());
    dec.setProperty(prop);

    logger.debug("DEC LONG_NAME: " + dec.getLongName());

    ObjectClass oc = DomainObjectFactory.newObjectClass();
    List ocs = elements.getElements(oc.getClass());

    for (int i = 0; i < ocs.size(); i++) {
      ObjectClass o = (ObjectClass) ocs.get(i);

      if (o.getLongName().equals(event.getClassName())) {
        oc = o;
      }
    }

    dec.setObjectClass(oc);

    DataElement de = DomainObjectFactory.newDataElement();

    //     de.setLongName(dec.getLongName() + event.getType());
    de.setLongName(dec.getLongName());

    logger.debug("DE LONG_NAME: " + de.getLongName());

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

  public void newAssociation(NewAssociationEvent event) {
    ObjectClassRelationship ocr = DomainObjectFactory.newObjectClassRelationship();
    ObjectClass oc = DomainObjectFactory.newObjectClass();
    
    List ocs = elements.getElements(oc.getClass());
    logger.debug("direction: " + event.getDirection());
    
    for (int i = 0; i < ocs.size(); i++) {
      ObjectClass o = (ObjectClass) ocs.get(i);
      
      if (o.getLongName().equals(event.getAClassName())) {
        if (event.getDirection().equals("B")) {
          ocr.setSource(o);
          ocr.setSourceRole(event.getARole());
          ocr.setSourceLowCardinality(event.getALowCardinality());
          ocr.setSourceHighCardinality(event.getAHighCardinality());
        } else {
          ocr.setTarget(o);
          ocr.setTargetRole(event.getARole());
          ocr.setTargetLowCardinality(event.getALowCardinality());
          ocr.setTargetHighCardinality(event.getAHighCardinality());
        }
      } else if (o.getLongName().equals(event.getBClassName())) {
        if (event.getDirection().equals("B")) {
          ocr.setTarget(o);
          ocr.setTargetRole(event.getBRole());
          ocr.setTargetLowCardinality(event.getBLowCardinality());
          ocr.setTargetHighCardinality(event.getBHighCardinality());
        } else {
          ocr.setSource(o);
          ocr.setSourceRole(event.getBRole());
          ocr.setSourceLowCardinality(event.getBLowCardinality());
          ocr.setSourceHighCardinality(event.getBHighCardinality());
        }
      }
    }

    if (event.getDirection().equals("AB")) {
      ocr.setDirection(ObjectClassRelationship.DIRECTION_BOTH);
    } else {
      ocr.setDirection(ObjectClassRelationship.DIRECTION_SINGLE);
    }

    ocr.setLongName(event.getRoleName());
    ocr.setType(ObjectClassRelationship.TYPE_HAS);
    elements.addElement(ocr);

    //         logger.debug("Association: ");
    //         logger.debug("Source:");
    //         logger.debug("-- " + ocr.getSourceRole());
    //         logger.debug("-- " + ocr.getSource().getLongName());
    //         logger.debug("-- " + ocr.getSourceCardinality());
    //         logger.debug("Target: ");
    //         logger.debug("-- " + ocr.getTargetRole());
    //         logger.debug("-- " + ocr.getTarget().getLongName());
    //         logger.debug("-- " + ocr.getTargetCardinality());
  }

  public void newGeneralization(NewGeneralizationEvent event) {
    ObjectClassRelationship ocr = DomainObjectFactory.newObjectClassRelationship();
    ObjectClass oc = DomainObjectFactory.newObjectClass();

    List ocs = elements.getElements(oc.getClass());

    for (int i = 0; i < ocs.size(); i++) {
      ObjectClass o = (ObjectClass) ocs.get(i);

      if (o.getLongName().equals(event.getParentClassName())) {
        ocr.setTarget(o);
      } else if (o.getLongName().equals(event.getChildClassName())) {
        ocr.setSource(o);
      }
    }

    ocr.setType(ObjectClassRelationship.TYPE_HAS);
    elements.addElement(ocr);

    logger.debug("Generalization: ");
    logger.debug("Source:");
    logger.debug("-- " + ocr.getSource().getLongName());
    logger.debug("Target: ");
    logger.debug("-- " + ocr.getTarget().getLongName());
  }

  private Concept newConcept(NewConceptualEvent event) {
    Concept concept = DomainObjectFactory.newConcept();

    concept.setPreferredName(event.getConceptCode());
    concept.setPreferredDefinition(event.getConceptDefinition());
    concept.setDefinitionSource(event.getConceptDefinitionSource());
    concept.setLongName(event.getConceptPreferredName());

    elements.addElement(concept);
    return concept;
    
  }
}
