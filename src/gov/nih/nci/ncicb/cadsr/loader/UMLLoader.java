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
import gov.nih.nci.ncicb.cadsr.loader.persister.*;

public class UMLLoader {

  public static void main(String[] args) throws Exception {

    String[] filenames = new File(args[0]).list(new FilenameFilter() {
	public boolean accept(File dir, String name) {
	  return name.endsWith(".xmi");
	}
      });


    String contextName = args[1];
    String projectName = args[2];
    String version = args[3];
    
    System.out.println(filenames.length + " files to process");
    
    ElementsLists elements = new ElementsLists();
    LoaderListener listener = new UMLListener(elements);

    for(int i=0; i<filenames.length; i++) {
      System.out.println("\n\n++++++++++++ Starting file: " + filenames[i]);

      XMIParser  parser = new XMIParser();
      parser.setListener(listener);
      parser.parse(args[0] + "/" + filenames[i]);

      Persister persister = new UMLPersister(elements);
      persister.setParameter("contextName", contextName);
      persister.setParameter("projectName", projectName);
      persister.setParameter("version", version);
      persister.persist();
      
    }

  }
   

}