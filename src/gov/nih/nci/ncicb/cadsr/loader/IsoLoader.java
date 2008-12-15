package gov.nih.nci.ncicb.cadsr.loader;

import gov.nih.nci.ncicb.cadsr.jaas.SwingCallbackHandler;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.parser.Parser;
import gov.nih.nci.ncicb.cadsr.loader.persister.Persister;
import gov.nih.nci.ncicb.cadsr.loader.ui.ProgressFrame;
import gov.nih.nci.ncicb.cadsr.loader.util.BeansAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.RunMode;
import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationWarning;
import gov.nih.nci.ncicb.cadsr.loader.validator.Validator;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.sql.DataSource;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;


/**
 *
 * <code>UMLLoader</code> is the starting class for running UML Loader un command line. <br/>
 * Usage: UMLLoader dir-name project-name
 * <ul><li>dir-name is the full path to the directory containing the XMI files</li>
 * <li>project-name is the name of an existing project in the UML_LOADER_DEFAULTS table of CADSR.</li>
 * </ul>
 * In order to start UML Loader, one needs a 'defaults' record in CADSR. 
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 * 
 */
public class IsoLoader {

  private static Logger logger = Logger.getLogger(UMLLoader.class.getName());

  private Validator validator;
  private Parser parser;
  private Persister persister;

  private UserPreferences prefs;
  
  private DataSource dataSource;

  private java.util.List<RunModeListener> runModeListeners = new ArrayList<RunModeListener>();
  
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

    Float projectVersion = null;
    try {
      projectVersion = new Float(args[2]);
    } catch (NumberFormatException ex) {
      System.err.println("Parameter projectVersion must be a number");
      System.exit(1);
    }

    CadsrLoader loader = BeansAccessor.getIsoLoader();

    String ignoreVd = (String)(System.getProperties().get("ignore-vd"));
    if((ignoreVd != null) & (ignoreVd.equals("true"))) {
      System.out.println("********** IGNORE VD ************");
      
      UserSelections.getInstance().setProperty("ignore-vd", new Boolean(true));
    } else 
      UserSelections.getInstance().setProperty("ignore-vd", new Boolean(false));

    loader.run(args[0], args[1], projectVersion);

  }

}