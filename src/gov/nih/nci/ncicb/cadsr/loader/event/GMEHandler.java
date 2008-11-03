package gov.nih.nci.ncicb.cadsr.loader.event;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.Definition;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.util.ConceptUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.OCRRoleNameBuilder;
import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class GMEHandler implements UMLHandler{

    private ElementsLists elements;
    private Logger logger = Logger.getLogger(UMLDefaultHandler.class.getName());
    private List packageList = new ArrayList();
    
    public GMEHandler() {
        this.elements = ElementsLists.getInstance();
    }

    public void newPackage(NewPackageEvent event) {
        logger.info("Package: " + event.getName());
        // handle this here if the package has GME info. If not, let classes handle package so we don't add empty package.
        if(!StringUtil.isEmpty(event.getGmeNamespace())) {
          ClassificationSchemeItem csi = DomainObjectFactory.newClassificationSchemeItem();
          String pName = event.getName();
          csi.setLongName(pName);
          if (!packageList.contains(pName)) {
            elements.addElement(csi);
            packageList.add(pName);
          }
          
          AlternateName gmeNamespaceName = DomainObjectFactory.newAlternateName();
          gmeNamespaceName.setType(AlternateName.TYPE_GME_NAMESPACE);
          gmeNamespaceName.setName(event.getGmeNamespace());
          csi.addAlternateName(gmeNamespaceName);
        }
    }

    public void newClass(NewClassEvent event) {
        logger.info("Class: " + event.getName());
        
        ObjectClass oc = DomainObjectFactory.newObjectClass();

        oc.setLongName(event.getName());
        elements.addElement(oc);
        
        AlternateName fullName = DomainObjectFactory.newAlternateName();
        fullName.setType(AlternateName.TYPE_CLASS_FULL_NAME);
        fullName.setName(event.getName());
        AlternateName className = DomainObjectFactory.newAlternateName();
        className.setType(AlternateName.TYPE_UML_CLASS);
        className.setName(event.getName().substring(event.getName().lastIndexOf(".") + 1));

        if(!StringUtil.isEmpty(event.getGmeNamespace())) {
          AlternateName gmeNamespaceName = DomainObjectFactory.newAlternateName();
          gmeNamespaceName.setType(AlternateName.TYPE_GME_NAMESPACE);
          gmeNamespaceName.setName(event.getGmeNamespace());
          oc.addAlternateName(gmeNamespaceName);
        }
        if(!StringUtil.isEmpty(event.getGmeXmlElement())) {
          AlternateName gmeXmlElementName = DomainObjectFactory.newAlternateName();
          gmeXmlElementName.setType(AlternateName.TYPE_GME_XML_ELEMENT);
          gmeXmlElementName.setName(event.getGmeXmlElement());
          oc.addAlternateName(gmeXmlElementName);
        }

        oc.addAlternateName(fullName);
        oc.addAlternateName(className);

        ClassificationSchemeItem csi = DomainObjectFactory.newClassificationSchemeItem();
        String pName = event.getPackageName();
        csi.setLongName(pName);
        if (!packageList.contains(pName)) {
          elements.addElement(csi);
          packageList.add(pName);
        }

    }

    public void newAttribute(NewAttributeEvent event) {
        logger.info("Attribute: " + event.getClassName() + "." +
                     event.getName());


        DataElement de = DomainObjectFactory.newDataElement();

        
        Property prop = DomainObjectFactory.newProperty();

        //     prop.setPreferredName(event.getName());
        prop.setLongName(event.getName());

        String propName = event.getName();

        String s = event.getClassName();
        int ind = s.lastIndexOf(".");
        String className = s.substring(ind + 1);
        String packageName = s.substring(0, ind);

        DataElementConcept dec = DomainObjectFactory.newDataElementConcept();
        dec.setLongName(className + ":" + propName);
        dec.setProperty(prop);
        
        logger.debug("DEC LONG_NAME: " + dec.getLongName());
        
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
        
        // Store alt Name for DE:
        // ClassName:PropertyName
        fullName = DomainObjectFactory.newAlternateName();
        fullName.setType(AlternateName.TYPE_UML_DE);
        fullName.setName(className + ":" + propName);
        de.addAlternateName(fullName);


        if(!StringUtil.isEmpty(event.getGmeXmlLocRef())) {
          AlternateName gmeXmlLocRefName = DomainObjectFactory.newAlternateName();
          gmeXmlLocRefName.setType(AlternateName.TYPE_GME_XML_LOC_REF);
          gmeXmlLocRefName.setName(event.getGmeXmlLocRef());
          de.addAlternateName(gmeXmlLocRefName);
        }

        elements.addElement(de);
        elements.addElement(dec);
        elements.addElement(prop);
    }

    public void newAssociation(NewAssociationEvent event) {
        ObjectClassRelationship ocr = DomainObjectFactory.newObjectClassRelationship();
        ObjectClass oc = DomainObjectFactory.newObjectClass();
        
        NewAssociationEndEvent aEvent = event.getAEvent();
        NewAssociationEndEvent bEvent = event.getBEvent();
        NewAssociationEndEvent sEvent = null;
        NewAssociationEndEvent tEvent = null; 
        
        List<ObjectClass> ocs = elements.getElements(oc);

        boolean aDone = false, 
          bDone = false;

        for(ObjectClass o : ocs) {
          String classFullName = null;
          for(AlternateName an : o.getAlternateNames()) {
            if(an.getType().equals(AlternateName.TYPE_CLASS_FULL_NAME))
              classFullName = an.getName();
          }
          if (classFullName == null) {
              System.err.println("No full class name found for "+o.getLongName());
              continue;
          }

          if (!aDone && (classFullName.equals(aEvent.getClassName()))) {
            if (event.getDirection().equals("B")) {
              sEvent = aEvent;
              ocr.setSource(o);
              ocr.setSourceRole(aEvent.getRoleName());
              ocr.setSourceLowCardinality(aEvent.getLowCardinality());
              ocr.setSourceHighCardinality(aEvent.getHighCardinality());
            } else {
              tEvent = aEvent;
              ocr.setTarget(o);
              ocr.setTargetRole(aEvent.getRoleName());
              ocr.setTargetLowCardinality(aEvent.getLowCardinality());
              ocr.setTargetHighCardinality(aEvent.getHighCardinality());
            }
            aDone = true;
          }
          if (!bDone && (classFullName.equals(bEvent.getClassName()))) {
            if (event.getDirection().equals("B")) {
              tEvent = bEvent;
              ocr.setTarget(o);
              ocr.setTargetRole(bEvent.getRoleName());
              ocr.setTargetLowCardinality(bEvent.getLowCardinality());
              ocr.setTargetHighCardinality(bEvent.getHighCardinality());
            } else {
              sEvent = bEvent;
              ocr.setSource(o);
              ocr.setSourceRole(bEvent.getRoleName());
              ocr.setSourceLowCardinality(bEvent.getLowCardinality());
              ocr.setSourceHighCardinality(bEvent.getHighCardinality());
            }
            bDone = true;
          }
        }

        if (event.getDirection().equals("AB")) {
          ocr.setDirection(ObjectClassRelationship.DIRECTION_BOTH);
        } else {
          ocr.setDirection(ObjectClassRelationship.DIRECTION_SINGLE);
        }

        ocr.setLongName(event.getRoleName());
        ocr.setType(ObjectClassRelationship.TYPE_HAS);

        if(sEvent == null || tEvent == null) {
          logger.warn("Skipping association because parent or child can't be found. Did you filter out some classes?");
          return;
        }

        if(!aDone)
          logger.debug("!aDone: " + aEvent.getClassName() + " -- " + bEvent.getClassName());

        if(!bDone) 
          logger.debug("!bDone: " + aEvent.getClassName() + " -- " + bEvent.getClassName());

        if(!StringUtil.isEmpty(event.getGmeSourceLocRef())) {
          AlternateName an = DomainObjectFactory.newAlternateName();
          an.setType(AlternateName.TYPE_GME_SRC_XML_LOC_REF);
          an.setName(event.getGmeSourceLocRef());
          ocr.addAlternateName(an);
        }

        if(!StringUtil.isEmpty(event.getGmeTargetLocRef())) {
          AlternateName an = DomainObjectFactory.newAlternateName();
          an.setType(AlternateName.TYPE_GME_TARGET_XML_LOC_REF);
          an.setName(event.getGmeTargetLocRef());
          ocr.addAlternateName(an);
        }

        elements.addElement(ocr);
    }

    public void newInterface(NewInterfaceEvent event) {}
    public void newStereotype(NewStereotypeEvent event) {}
    public void newDataType(NewDataTypeEvent event) {}
    public void newGeneralization(NewGeneralizationEvent event) {}
    public void beginParsing() {}
    public void endParsing() {}
    public void addProgressListener(ProgressListener listener) {}
    public void newOperation(NewOperationEvent event) {}
    public void newValueDomain(NewValueDomainEvent event) {}
    public void newValueMeaning(NewValueMeaningEvent event) {}


}
