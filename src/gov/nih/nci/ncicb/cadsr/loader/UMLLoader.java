package gov.nih.nci.ncicb.cadsr.loader;

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
import gov.nih.nci.ncicb.cadsr.loader.parser.*;

public class UMLLoader {

  public static void main(String[] args) throws Exception {

    String[] filenames = new File(args[0]).list(new FilenameFilter() {
	public boolean accept(File dir, String name) {
	  return name.endsWith(".xmi");
	}
      });
    
    System.out.println(filenames.length + " files to process");
    
    ElementsLists elements = new ElementsLists();
    LoaderListener listener = new UMLListener(elements);

    for(int i=0; i<filenames.length; i++) {
      System.out.println("\n\n++++++++++++ Starting file: " + filenames[i]);

      XMIParser  parser = new XMIParser();
      parser.setListener(listener);
      parser.parse(args[0] + "/" + filenames[i]);
    }

  }
   

}