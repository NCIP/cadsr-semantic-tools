package gov.nih.nci.ncicb.cadsr.loader.parser;

import gov.nih.nci.ncicb.cadsr.loader.event.*;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;

import org.apache.log4j.Logger;

import org.omg.uml.foundation.core.*;
import org.omg.uml.foundation.datatypes.MultiplicityRange;
import org.omg.uml.foundation.extensionmechanisms.*;
import org.omg.uml.modelmanagement.Model;
import org.omg.uml.modelmanagement.UmlPackage;

import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import uml.MdrModelManager;
import uml.MdrModelManagerFactory;
import uml.MdrModelManagerFactoryImpl;

import java.io.*;

import java.util.*;

/**
 * Implemetation of <code>Parser</code> for XMI files. Navigates the XMI document and sends UML Object events.
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class XMIParser implements Parser {
  private static final String EA_CONTAINMENT = "containment";
  private static final String EA_UNSPECIFIED = "Unspecified";
  private UMLHandler listener;
  private MdrModelManagerFactory fact;
  private MdrModelManager mgr;
  private String packageName = "";
  private String className = "";
  private List associations = new ArrayList();
  private Logger logger = Logger.getLogger(XMIParser.class.getName());
  private List generalizationEvents = new ArrayList();
  private List associationEvents = new ArrayList();

  public void setEventHandler(LoaderHandler handler) {
    this.listener = (UMLHandler) handler;
  }

  public void parse(String filename) {
    try {
      fact = new MdrModelManagerFactoryImpl();
      mgr = fact.readModel("", filename);
      Model model = mgr.getModel();
      Iterator it = model.getOwnedElement().iterator();

      while (it.hasNext()) {
        Object o = it.next();

        if (o instanceof UmlPackage) {
          doPackage((UmlPackage) o);
        }
        else if (o instanceof DataType) {
          doDataType((DataType) o);
        }
        else if (o instanceof UmlAssociation) {
          doAssociation((UmlAssociation) o);
        }
        else if (o instanceof UmlClass) {
          doClass((UmlClass) o);
        }
        else {
          logger.debug("Root Element: " + o.getClass());
        }
      }

      fireLastEvents();
    }
    catch (Exception e) {
      logger.fatal("Could not parse: " + filename);
      e.printStackTrace();
    } // end of try-catch
  }

  private void doPackage(UmlPackage pack) {
    UMLDefaults defaults = UMLDefaults.getInstance();

    if (packageName.length() == 0) {
      packageName = pack.getName();
    }
    else {
      packageName += ("." + pack.getName());
    }

    if(isInPackageFilter(packageName)) {
      listener.newPackage(new NewPackageEvent(packageName));
    } else {
      logger.info(PropertyAccessor.getProperty("skip.package", packageName));
    }

    Iterator it = pack.getOwnedElement().iterator();

    while (it.hasNext()) {
      Object o = it.next();

      if (o instanceof UmlPackage) {
        String oldPackage = packageName;
        doPackage((UmlPackage) o);
        packageName = oldPackage;
      }
      else if (o instanceof UmlClass) {
        doClass((UmlClass) o);
      }
      else if (o instanceof Stereotype) {
        doStereotype((Stereotype) o);
      }
      else if (o instanceof Component) {
        doComponent((Component) o);
      }
      else if (o instanceof UmlAssociation) {
        doAssociation((UmlAssociation) o);
      }
      else if (o instanceof Interface) {
        doInterface((Interface) o);
      }
      else {
        logger.debug("Package Child: " + o.getClass());
      }
    }

    packageName = "";
  }

  private void doClass(UmlClass clazz) {
    UMLDefaults defaults = UMLDefaults.getInstance();
    String pName = clazz.getNamespace().getName();

    className = clazz.getName();

    if (pName != null) {
      className = pName + "." + className;
    }

    NewClassEvent event = new NewClassEvent(className);
    event.setPackageName(pName);

    setConceptInfo(clazz, event);

    if(isInPackageFilter(pName)) {
      listener.newClass(event);
    } else {
      logger.info(PropertyAccessor.getProperty("class.filtered", className));
      return;
    }

    for (Iterator it = clazz.getFeature().iterator(); it.hasNext();) {
      Object o = it.next();

      if (o instanceof Attribute) {
        doAttribute((Attribute) o);
      }
      else if (o instanceof Operation) {
        doOperation((Operation) o);
      }
      else {
        logger.debug("Class child: " + o.getClass());
      }
    }

    className = "";

    for (Iterator it = clazz.getGeneralization().iterator(); it.hasNext();) {
      Generalization g = (Generalization) it.next();

      if (g.getParent() instanceof UmlClass) {
        UmlClass p = (UmlClass) g.getParent();
        NewGeneralizationEvent gEvent = new NewGeneralizationEvent();
        gEvent.setParentClassName(
          p.getNamespace().getName() + "." + p.getName());
        gEvent.setChildClassName(
          clazz.getNamespace().getName() + "." + clazz.getName());

        generalizationEvents.add(gEvent);
      }
    }
  }

  private void doInterface(Interface interf) {
    className = packageName + "." + interf.getName();

    //     logger.debug("Class: " + className);
    listener.newInterface(new NewInterfaceEvent(className));

    Iterator it = interf.getFeature().iterator();

    while (it.hasNext()) {
      Object o = it.next();
      if (o instanceof Attribute) {
        doAttribute((Attribute) o);
      }
      else if (o instanceof Operation) {
        doOperation((Operation) o);
      }
      else {
        logger.debug("Class child: " + o.getClass());
      }
    }

    className = "";
  }

  private void doAttribute(Attribute att) {
    NewAttributeEvent event = new NewAttributeEvent(att.getName());
    event.setClassName(className);
    event.setType(att.getType().getName());
    
    setConceptInfo(att, event);

    listener.newAttribute(event);
  }

  private void doDataType(DataType dt) {
    listener.newDataType(new NewDataTypeEvent(dt.getName()));
  }

  private void doOperation(Operation op) {
    NewOperationEvent event = new NewOperationEvent(op.getName());
    event.setClassName(className);
    listener.newOperation(event);
  }

  private void doStereotype(Stereotype st) {
    logger.debug("--- Stereotype " + st.getName());
  }

  private void doAssociation(UmlAssociation assoc) {
    Iterator it = assoc.getConnection().iterator();
    NewAssociationEvent event = new NewAssociationEvent();
    event.setRoleName(assoc.getName());

    String navig = "";

    if (it.hasNext()) {
      Object o = it.next();

      if (o instanceof AssociationEnd) {
        AssociationEnd end = (AssociationEnd) o;
//         logger.debug("end A is navigable: " + end.isNavigable());

        if (end.isNavigable()) {
          navig += 'A';
        }

        Classifier classif = end.getType();
        if(!isInPackageFilter(classif.getNamespace().getName())) {
          logger.info(PropertyAccessor.getProperty("skip.association", classif.getName()));
          return;
        }


        Collection range = end.getMultiplicity().getRange();
        for (Iterator it2 = range.iterator(); it2.hasNext();) {
          MultiplicityRange mr = (MultiplicityRange) it2.next();
          int low = mr.getLower();
          int high = mr.getUpper();
          event.setALowCardinality(low);
          event.setAHighCardinality(high);
        }

        event.setAClassName(
          classif.getNamespace().getName() + "." + classif.getName());
        event.setARole(end.getName());

      }
    }

    if (it.hasNext()) {
      Object o = it.next();

      if (o instanceof AssociationEnd) {
        AssociationEnd end = (AssociationEnd) o;
//         logger.debug("end B is navigable: " + end.isNavigable());

        if (end.isNavigable()) {
          navig += 'B';
        }

        Classifier classif = end.getType();
        if(!isInPackageFilter(classif.getNamespace().getName())) {
          logger.info(PropertyAccessor.getProperty("skip.association", classif.getName()));
          return;
        }

        Collection range = end.getMultiplicity().getRange();
        for (Iterator it2 = range.iterator(); it2.hasNext();) {
          MultiplicityRange mr = (MultiplicityRange) it2.next();
          int low = mr.getLower();
          int high = mr.getUpper();
          logger.debug("B-LOW: " + low);
          logger.debug("B-HIGH: " + high);
          event.setBLowCardinality(low);
          event.setBHighCardinality(high);
        }

        event.setBClassName(
          classif.getNamespace().getName() + "." + classif.getName());
        event.setBRole(end.getName());
      }
    }

    event.setDirection(navig);

    associationEvents.add(event);
  }

  private void doComponent(Component comp) {
    logger.debug("--- Component: " + comp.getName());
  }

  private String cardinality(AssociationEnd end) {
    Collection range = end.getMultiplicity().getRange();

    for (Iterator it = range.iterator(); it.hasNext();) {
      MultiplicityRange mr = (MultiplicityRange) it.next();
      int low = mr.getLower();
      int high = mr.getUpper();

      if (low == high) {
        return "" + low;
      }
      else {
        String h = (high >= 0) ? ("" + high) : "*";

        return low + ".." + h;
      }
    }

    return "";
  }

  private void fireLastEvents() {
    for (Iterator it = associationEvents.iterator(); it.hasNext();) {
      listener.newAssociation((NewAssociationEvent) it.next());
    }

    for (Iterator it = generalizationEvents.iterator(); it.hasNext();) {
      listener.newGeneralization((NewGeneralizationEvent) it.next());
    }
  }

  private void setConceptInfo(ModelElement elt, NewConceptualEvent event) {

    TaggedValue tv = mgr.getTaggedValue(elt, NewConceptualEvent.TV_CONCEPT_CODE);
    if (tv != null) {
      event.setConceptCode(tv.getValue());
    }

    tv = mgr.getTaggedValue(elt, NewConceptualEvent.TV_CONCEPT_DEFINITION);
    if (tv != null) {
      event.setConceptDefinition(tv.getValue());
    }

    tv = mgr.getTaggedValue(elt, NewConceptualEvent.TV_CONCEPT_DEFINITION_SOURCE);
    if (tv != null) {
      event.setConceptDefinitionSource(tv.getValue());
    }
    
    tv = mgr.getTaggedValue(elt, NewConceptualEvent.TV_CONCEPT_PREFERRED_NAME);
    if (tv != null) {
      event.setConceptPreferredName(tv.getValue());
    }

  }

  private boolean isInPackageFilter(String pName) {
    Map packageFilter = UMLDefaults.getInstance().getPackageFilter();
    return (packageFilter.size() == 0) || (packageFilter.containsKey(pName));
  }
}
