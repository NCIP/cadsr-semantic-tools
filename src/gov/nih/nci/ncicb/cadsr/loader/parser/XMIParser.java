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

  private LoaderListener listener;

  private MdrModelManagerFactory fact;
  private MdrModelManager mgr;

  private String packageName="";
  private String className = "";

  public void setListener(LoaderListener listener) {
    this.listener = listener;
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

    ((UMLListener)listener).notification(new NewPackageEvent(packageName));

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
    
    listener.notification(new NewClassEvent(className));

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
    
    listener.notification(new NewInterfaceEvent(className));

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
    listener.notification(new NewAttributeEvent(att.getName(), className));
  }

  private void doDataType(DataType dt)
  {
    listener.notification(new NewDataTypeEvent(dt.getName()));
  }

  private void doOperation(Operation op) {
    listener.notification(new NewOperationEvent(op.getName(), className));
  }

  private void doStereotype(Stereotype st) {
    System.out.println("--- Stereotype " + st.getName());
  }

  private void doAssociation(UmlAssociation assoc) {
    System.out.println("*** Association: " + assoc.getName());
    Iterator it = assoc.getConnection().iterator();
    while(it.hasNext()) {
      Object o = it.next();
      System.out.println("*** " +  o.getClass());
      if(o instanceof AssociationEnd) {
	AssociationEnd end = (AssociationEnd)o;
	Classifier classif = end.getType();
	Iterator it2 = classif.getParticipant().iterator();
	while(it2.hasNext()) {
	  Object p = it2.next();
	  System.out.println("*** Participant: " + p.getClass());
	}
      }
    }
  }

  private void doComponent(Component comp) {
    System.out.println("--- Component: " + comp.getName());
  }


}