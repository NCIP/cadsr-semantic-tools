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
import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;

import java.security.*;
import javax.security.auth.*;
import javax.security.auth.login.*;
import javax.security.auth.callback.CallbackHandler;


import org.apache.log4j.Logger;

import gov.nih.nci.ncicb.cadsr.jaas.ConsoleCallbackHandler;


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

    if(args.length != 3) {
      System.err.println(PropertyAccessor.getProperty("usage"));
      System.exit(1);
    }

    new UMLLoader().run(args);
  }

  private void run(String[] args) throws Exception {
    InitClass initClass = new InitClass(this);
    Thread t = new Thread(initClass);
    /* high priority because:
     * If the user is fast at entering its user name
     * (namely, username / password is provided automatically)
     * Then it's possible for the login module to want to access
     *      spring before it's initialized. 
     * Would not happen in normal run, but in dev runs, it may.
     * This seems to have absolutely no effect (on linux at least). Will investigate later. 
     */
    t.setPriority(Thread.MAX_PRIORITY);
    t.start();

    try {
      Thread.currentThread().sleep(500);
    } catch (Exception e){
    } // end of try-catch

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
	logger.debug(PropertyAccessor.getProperty("authenticated", username));
      }
    } catch (Exception ex) {
      logger.error(PropertyAccessor.getProperty("login.fail",ex.getMessage()));
      System.exit(1);
    }
    
    String projectName = args[1];

    Float projectVersion = null;
    try {
      projectVersion = new Float(args[2]);
    } catch (NumberFormatException ex) {
      System.err.println("Parameter projectVersion must be a number");
      System.exit(1);
    }
    
    logger.info(PropertyAccessor.getProperty("nbOfFiles", filenames.length));
    
    ElementsLists elements = new ElementsLists();
    Validator validator = new UMLValidator(elements);
    UMLHandler listener = new UMLDefaultHandler(elements);

    synchronized(initClass) {
      if(!initClass.isDone())
        try {
          wait();
        } catch (Exception e){
        } // end of try-catch
    }
    
    for(int i=0; i<filenames.length; i++) {
      logger.info(PropertyAccessor.getProperty("startingFile", filenames[i]));

      UMLDefaults defaults = UMLDefaults.getInstance();
      defaults.initParams(projectName, projectVersion, username);
      defaults.initClassifications();

      XMIParser  parser = new XMIParser();
      parser.setEventHandler(listener);
      parser.parse(args[0] + "/" + filenames[i]);
      
    }

    List errors = validator.validate();
    if(errors.size() > 0) {
      // Ask user if we should continue
      for(Iterator it=errors.iterator(); it.hasNext();) {
        ValidationError error = (ValidationError)it.next();
        // !!! TODO choose error, warning, etc ...
        logger.error(error.getSeverity() + ": " + error.getMessage());
      }
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      System.out.print(PropertyAccessor.getProperty("validation.continue"));
      String answ = br.readLine();
      if(!answ.equals("y")) {
        System.exit(1);
      }
    }


    Persister persister = new UMLPersister(elements);
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
      DAOAccessor p = new DAOAccessor();
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

