package gov.nih.nci.ncicb.cadsr.loader.parser;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

import org.apache.log4j.Logger;

import org.omg.uml.foundation.core.*;
import org.omg.uml.foundation.datatypes.MultiplicityRange;
import org.omg.uml.foundation.extensionmechanisms.*;
import org.omg.uml.modelmanagement.Model;
import org.omg.uml.modelmanagement.UmlPackage;

import uml.MdrModelManager;
import uml.MdrModelManagerFactory;
import uml.MdrModelManagerFactoryImpl;

import java.io.*;

import java.util.*;


public class XMIParser implements Parser {
  private static final String TV_CONCEPT = "EVS_CONCEPT";
  private static final String EA_CONTAINMENT = "containment";
  private static final String EA_UNSPECIFIED = "Unspecified";
  private UMLListener listener;
  private MdrModelManagerFactory fact;
  private MdrModelManager mgr;
  private String packageName = "";
  private String className = "";
  private List associations = new ArrayList();
  private Logger logger = Logger.getLogger(XMIParser.class.getName());
  private List generalizationEvents = new ArrayList();
  private List associationEvents = new ArrayList();

  public void setListener(LoaderListener listener) {
    this.listener = (UMLListener) listener;
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
    if (packageName.length() == 0) {
      packageName = pack.getName();
    }
    else {
      packageName += ("." + pack.getName());
    }

    ((UMLListener) listener).newPackage(new NewPackageEvent(packageName));

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
    String pName = clazz.getNamespace().getName();

    className = clazz.getName();

    if (pName != null) {
      className = pName + "." + className;
    }

    NewClassEvent event = new NewClassEvent(className);
    event.setPackageName(pName);

    TaggedValue tv = mgr.getTaggedValue(clazz, TV_CONCEPT);

    if (tv != null) {
      event.setConceptCode(tv.getValue());
    }

    listener.newClass(event);

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

    TaggedValue tv = mgr.getTaggedValue(att, TV_CONCEPT);

    if (tv != null) {
      event.setConceptCode(tv.getValue());
    }

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
        logger.debug("end A is navigable: " + end.isNavigable());

        if (end.isNavigable()) {
          navig += 'A';
        }

        Classifier classif = end.getType();
        event.setACardinality(cardinality(end));
        event.setAClassName(
          classif.getNamespace().getName() + "." + classif.getName());
        event.setARole(end.getName());

        TaggedValue tv = mgr.getTaggedValue(end, EA_CONTAINMENT);

        if (tv != null) {
          logger.debug("containment: " + tv.getValue());
        }
      }
    }

    if (it.hasNext()) {
      Object o = it.next();

      if (o instanceof AssociationEnd) {
        AssociationEnd end = (AssociationEnd) o;
        logger.debug("end B is navigable: " + end.isNavigable());

        if (end.isNavigable()) {
          navig += 'B';
        }

        // This for EA only. EA has direction called "unspecified". We want to treat that as bi-directional. EA stores 'Unspecified' as a tagged valued.
        TaggedValue tv = mgr.getTaggedValue(end, EA_CONTAINMENT);

        if ((tv != null) && (tv.getValue().equals(EA_UNSPECIFIED))) {
          navig = "AB";
        }

        if (tv != null) {
          logger.debug("containment: " + tv.getValue());
        }

        Classifier classif = end.getType();
        event.setBCardinality(cardinality(end));
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
}
