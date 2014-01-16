/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

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

import gov.nih.nci.ncicb.cadsr.loader.persister.PersisterException;

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
 * <code>UMLLoader</code> is the starting class for running UML Loader un
 * command line. <br/>
 * Usage: UMLLoader dir-name project-name
 * <ul>
 * <li>dir-name is the full path to the directory containing the XMI files</li>
 * <li>project-name is the name of an existing project in the
 * UML_LOADER_DEFAULTS table of CADSR.</li>
 * </ul>
 * In order to start UML Loader, one needs a 'defaults' record in CADSR.
 * 
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 * 
 */
public class CadsrLoader {

	private static Logger logger = Logger
			.getLogger(CadsrLoader.class.getName());

	private Validator validator;
	private Parser parser;
	private Persister persister;

	private UserPreferences prefs;

	private DataSource dataSource;

	private java.util.List<RunModeListener> runModeListeners = new ArrayList<RunModeListener>();

	public void run(String fileDir, String projectName, Float projectVersion)
			throws Exception {
		//Set the private api false based on user requirement
		//Reverted the use of private api based on user requirement
		prefs.setUsePrivateApi(true);

		InitClass initClass = new InitClass(this);
		Thread t = new Thread(initClass);
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();

		// try {
		// Thread.currentThread().sleep(500);
		// } catch (Exception e){
		// } // end of try-catch

		UserSelections userSelections = UserSelections.getInstance();
		RunMode mode = RunMode.Loader;
		userSelections.setProperty("MODE", mode);
		userSelections.setProperty("SKIP_VD_VALIDATION", true);

		String[] filenames = new File(fileDir).list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".xmi") || name.endsWith(".uml");
			}
		});

		if (filenames == null)
			filenames = new String[0];

		if (filenames.length == 0) {
			logger.info(PropertyAccessor.getProperty("no.files"));
			System.exit(0);
		}

		SwingCallbackHandler sch = new SwingCallbackHandler();
		LoginContext lc = new LoginContext("UML_Loader", sch);

		sch.dispose();

		String username = null;

		try {
			lc.login();
			boolean loginSuccess = true;

			Subject subject = lc.getSubject();

			Iterator it = subject.getPrincipals().iterator();
			while (it.hasNext()) {
				username = it.next().toString();
				logger.debug(PropertyAccessor.getProperty("authenticated",
						username));
			}
		} catch (Exception ex) {
			logger.error(PropertyAccessor.getProperty("login.fail",
					ex.getMessage()));
			System.exit(1);
		}

		logger.info(PropertyAccessor.getProperty("nbOfFiles", filenames.length));

		synchronized (initClass) {
			if (!initClass.isDone())
				try {
					wait();
				} catch (Exception e) {
				} // end of try-catch
		}

		fireNewRunMode(mode);

		try {
			for (int i = 0; i < filenames.length; i++) {
				logger.info(PropertyAccessor.getProperty("startingFile",
						filenames[i]));

				UMLDefaults defaults = UMLDefaults.getInstance();
				try {
					defaults.initParams(projectName, projectVersion, username);
				} catch (Exception e) {
					logger.error(e);
					System.exit(1);
				} // end of try-catch
				defaults.initWithDB();
				defaults.setUsername(username);

				parser.parse(fileDir + "/" + filenames[i]);

			}

			ValidationItems items = validator.validate();
			Set<ValidationError> errors = items.getErrors();
			if (errors.size() > 0) {
				for (ValidationError error : errors) {
					logger.error(error.getMessage());
				}
				System.exit(1);
			}

			Set<ValidationWarning> warnings = items.getWarnings();
			if (warnings.size() > 0) {
				// Ask user if we should continue
				for (ValidationWarning warning : warnings) {
					logger.warn(warning.getMessage());
				}
				String answ = JOptionPane.showInputDialog(PropertyAccessor
						.getProperty("validation.continue"));
				if (answ == null || !answ.equals("y")) {
					System.exit(1);
				}
			}

			ProgressFrame progressFrame = new ProgressFrame(100);
			progressFrame.setVisible(true);

			persister.setProgressListener(progressFrame);

			persister.persist();

			progressFrame.dispose();

			logger.info("refreshing database views");
		} catch (PersisterException ex) {
			logger.error(ex.getMessage());
		}

		// Refresh the views
		Connection conn = null;
		CallableStatement cs = null;
		try {
			conn = dataSource.getConnection();
			cs = conn.prepareCall("{call sbrext_admin_mv.refresh_mvw}");
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		logger.info("refreshed databased views");

		System.exit(0);
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public void setPersister(Persister persister) {
		this.persister = persister;
	}

	public void setUserPreferences(UserPreferences preferences) {
		prefs = preferences;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setRunModeListeners(java.util.List<RunModeListener> listeners) {
		this.runModeListeners = listeners;
	}

	private void fireNewRunMode(RunMode runMode) {
		for (RunModeListener l : runModeListeners)
			l.setRunMode(runMode);
	}

}
