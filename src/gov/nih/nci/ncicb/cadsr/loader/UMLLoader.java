package gov.nih.nci.ncicb.cadsr.loader;

import java.util.*;

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
import gov.nih.nci.ncicb.cadsr.loader.validator.*;

import java.security.*;
import javax.security.auth.*;
import javax.security.auth.login.*;
import javax.security.auth.callback.CallbackHandler;

import gov.nih.nci.ncicb.cadsr.spring.ApplicationContextFactory;

import org.apache.log4j.Logger;

import gov.nih.nci.ncicb.cadsr.loader.jaas.ConsoleCallbackHandler;


/**
 *
 * <code>UMLLoader</code> is the starting class for running UML Loader un command line. <br/>
 * Usage: UMLLoader dir-name project-name
 * <ul><li>dir-name is the full path to the directory containing the XMI files</li>
 * <li>project-name is the name of an existing project in the UML_LOADER_DEFAULTS table of CADSR.</li>
 * </ul>
 * In order to start UML Loader, one needs a 'defaults' record in CADSR. 
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 * 
 */
public class UMLLoader {

  private static Logger logger = Logger.getLogger(UMLLoader.class.getName());

  /**
   *
   * @param args a <code>String[]</code> value
   * @exception Exception if an error occurs
   */
  public static void main(String[] args) throws Exception {
    ApplicationContextFactory.init("applicationContext.xml");

    new UMLLoader().run(args);
  }

  private void run(String[] args) throws Exception {
    InitClass initClass = new InitClass(this);
    Thread t = new Thread(initClass);
    t.start();

    String[] filenames = new File(args[0]).list(new FilenameFilter() {
	public boolean accept(File dir, String name) {
	  return name.endsWith(".xmi");
	}
      });
    
    LoginContext lc = new LoginContext("UML_Loader", new ConsoleCallbackHandler());

    String username = null;
    
    try {
      lc.login();
      boolean loginSuccess = true;
      
      Subject subject = lc.getSubject();

      Iterator it = subject.getPrincipals().iterator();
      while (it.hasNext()) {
	username = it.next().toString();
	logger.debug("Authenticated username: " + username);
      }
    } catch (Exception ex) {
      logger.error("Failed to login: " + ex.getMessage());
      System.exit(1);
    }
    
    String projectName = args[1];
    
    logger.info(filenames.length + " files to process");
    
    ElementsLists elements = new ElementsLists();
    Validator validator = new UMLValidator(elements);
    UMLListener listener = new XMIUMLListener(elements);
    
    for(int i=0; i<filenames.length; i++) {
      logger.info("Starting file: " + filenames[i]);

      UMLDefaults defaults = UMLDefaults.getInstance();
      defaults.initParams(projectName, username);
      defaults.initClassifications();

      XMIParser  parser = new XMIParser();
      parser.setListener(listener);
      parser.parse(args[0] + "/" + filenames[i]);

      
      synchronized(initClass) {
	if(!initClass.isDone())
	  try {
	    wait();
	  } catch (Exception e){
	  } // end of try-catch
      }
    }

    List errors = validator.validate();
    if(errors.size() > 0) {
      // Ask user if we should continue
      for(Iterator it=errors.iterator(); it.hasNext();) {
        ValidationError error = (ValidationError)it.next();
        // !!! TODO choose error, warning, etc ...
        logger.error(error.getSeverity() + ": " + error.getMessage());
      }
      // !! TODO: Offer to continue anyway
      System.exit(1);
    }


    Persister persister = new UMLPersister(elements);
    persister.setParameter("projectName", projectName);
    persister.setParameter("username", username);
    persister.persist();

  }

  /**
   * <code>UMLLoader</code> starts a separate thread for initializing the environement, while users enter their username and password. 
   *
   */
  class InitClass implements Runnable {
    Object parent;
    boolean done = false;

    InitClass(Object parent) {
      this.parent = parent;
    }
    
    public void run() {
      UMLPersister p = new UMLPersister(null);
      synchronized (this) {
	done = true;
	notifyAll();
      }
    }
    
    public boolean isDone() {
      return done;
    }


  }

}

