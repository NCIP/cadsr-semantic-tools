package gov.nih.nci.ncicb.cadsr.loader.parser;

import java.util.Iterator;

import org.omg.uml.foundation.core.*;
import org.omg.uml.foundation.extensionmechanisms.*;

import uml.MdrModelManager;
import uml.MdrModelManagerFactory;
import uml.MdrModelManagerFactoryImpl;

import org.omg.uml.modelmanagement.Model;
import org.omg.uml.modelmanagement.UmlPackage;

import java.io.*;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

public class XMIParser implements Parser {

  private UMLListener listener;

  private MdrModelManagerFactory fact;
  private MdrModelManager mgr;

  private String packageName="";
  private String className = "";

  public void setListener(LoaderListener listener) {
    this.listener = (UMLListener)listener;
  }

  public void parse(String filename) {
    try {
      fact = new MdrModelManagerFactoryImpl();
      mgr = fact.readModel("", filename);

      Model model = mgr.getModel();
      Iterator it = model.getOwnedElement().iterator();

      while(it.hasNext()) {
	Object o = it.next();
	
	if(o instanceof UmlPackage) {
	  doPackage((UmlPackage)o);
	} else if(o instanceof DataType) {
	  doDataType((DataType)o);
	} else if(o instanceof UmlAssociation) {
	  doAssociation((UmlAssociation)o);
	} else {
	  System.out.println("Root Element: " + o.getClass());
	}
      }
    } catch (Exception e){
      System.out.println("Could not parse: " + filename);
      e.printStackTrace();
    } // end of try-catch
  }
  
  private void doPackage(UmlPackage pack) {
    if(packageName.length() == 0) {
      packageName = pack.getName();
    } else {
      packageName += "." + pack.getName(); 
    }  

    ((UMLListener)listener).newPackage(new NewPackageEvent(packageName));

    Iterator it = pack.getOwnedElement().iterator();
    while(it.hasNext()) {
      Object o = it.next();
      if(o instanceof UmlPackage) {
	String oldPackage = packageName;
	doPackage((UmlPackage)o);
	packageName = oldPackage;
      } else if(o instanceof UmlClass) {
	doClass((UmlClass)o);
      } else if(o instanceof Stereotype) {
	doStereotype((Stereotype)o);
      } else if(o instanceof Component) {
	doComponent((Component)o);
      } else if(o instanceof UmlAssociation) {
	doAssociation((UmlAssociation)o);
      } else if(o instanceof Interface) {
	doInterface((Interface)o);
      } else {
	System.out.println("Package Child: " + o.getClass());
      }
    }
    
    packageName = "";
  }
  
  private void doClass(UmlClass clazz) {
    className = packageName + "." + clazz.getName();
//     System.out.println("Class: " + className);
    
    listener.newClass(new NewClassEvent(className));

    Iterator it = clazz.getFeature().iterator();
    while(it.hasNext()) {
      Object o = it.next();
      if(o instanceof Attribute) {
	doAttribute((Attribute)o);
      } else if(o instanceof Operation) {
	doOperation((Operation)o);
      } else {
	System.out.println("Class child: " + o.getClass());
      }
    }
    className = "";
  }

  private void doInterface(Interface interf) {
    className = packageName + "." + interf.getName();
//     System.out.println("Class: " + className);
    
    listener.newInterface(new NewInterfaceEvent(className));

    Iterator it = interf.getFeature().iterator();
    while(it.hasNext()) {
      Object o = it.next();
      if(o instanceof Attribute) {
	doAttribute((Attribute)o);
      } else if(o instanceof Operation) {
	doOperation((Operation)o);
      } else {
	System.out.println("Class child: " + o.getClass());
      }
    }
    className = "";
  }

  private void doAttribute(Attribute att) {
    NewAttributeEvent event = new NewAttributeEvent(att.getName());

    event.setClassName(className);
    event.setType(att.getType().getName());
    listener.newAttribute(event);
  }

  private void doDataType(DataType dt)
  {
    listener.newDataType(new NewDataTypeEvent(dt.getName()));
  }

  private void doOperation(Operation op) {
    NewOperationEvent event = new NewOperationEvent(op.getName());
    event.setClassName(className);
    listener.newOperation(event);
  }

  private void doStereotype(Stereotype st) {
    System.out.println("--- Stereotype " + st.getName());
  }

  private void doAssociation(UmlAssociation assoc) {
    System.out.println("-- Association: " + assoc.getName());
    Iterator it = assoc.getConnection().iterator();
    while(it.hasNext()) {
      Object o = it.next();
      if(o instanceof AssociationEnd) {
	AssociationEnd end = (AssociationEnd)o;
	Classifier classif = end.getType();
	System.out.println("----" + classif.getName());
      }
    }
  }

  private void doComponent(Component comp) {
    System.out.println("--- Component: " + comp.getName());
  }


}