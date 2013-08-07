/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.test.end2end;

import gov.nih.nci.cadsr.common.Constants;
import gov.nih.nci.ncicb.cadsr.domain.LoaderDefault;
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.parser.Parser;
import gov.nih.nci.ncicb.cadsr.loader.persister.Persister;
import gov.nih.nci.ncicb.cadsr.loader.test.MainTestCase;
import gov.nih.nci.ncicb.cadsr.loader.util.BeansAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.RunMode;
import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;
import gov.nih.nci.ncicb.cadsr.loader.validator.Validator;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Set;

import org.junit.Test;

/**
 * Setup:
 * 
 * 1. Add classes/ directory into classpath (it needs to find resource.properties, beans.xml etc)
 * 2. Make sure classes/ is after test/src in your classpath
 * 3. Change test/src/spring-ds.properties accordingly
 * 4. Run ant -Dtarget.env=dev run at least once to get all the properties in DEV tier replaced by ant (if it does not work, you need to modify all files under classes/ manually!)
 * 5. Make sure all property/xml files are properly processed by ant/manually (e.g. beans.xml)
 * 
 * @author kim
 *
 */
public class End2EndTestCase extends MainTestCase {

	public End2EndTestCase(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	private UMLDefaults defaults = UMLDefaults.getInstance();
	
//	public End2EndTestCase() {
//		super("End2EndTestCase", End2EndTestCase.class, "/gov/nih/nci/ncicb/cadsr/loader/test/end2end/gf23659.xls");
//	}
	
	@Override
	protected void containerSetUp() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean requiresDatabase() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean runInRealContainer() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setUp() throws Exception{
		super.setUp();
//		setUserSelections();
		LoaderDefault loaderDefault = getLoaderDefaults();
		defaults.useDefaults(loaderDefault);
		defaults.initWithDB();
		setUserSelections();
	}
	
	/*public void testGF6757() {
		try {
			MockCaDSRLoader loader = new MockCaDSRLoader();
			loader.run("c:/documents and settings/mathura2/desktop/xmi", "SIW_TEST", new Float(1.0));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	@Test	
	public void testGF23659() {
		try {
			MockCaDSRLoader loader = new MockCaDSRLoader();
//			loader.run("c:/documents and settings/mathura2/desktop/xmi/test", "SIW_TEST", new Float(1.0));
//			loader.run("C:/apps/SIW_4_117/data", "James_TEST", new Float(1.0));
			loader.run("/Users/ag/SIW_4_117/data", "James_TEST", new Float(1.0));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private LoaderDefault getLoaderDefaults() {
		LoaderDefault loaderDefault = new LoaderDefaultImpl();
		loaderDefault.setProjectLongName("SIW_TEST");
		loaderDefault.setProjectName("SIW_TEST");
		loaderDefault.setProjectDescription("SIW_TEST");
		loaderDefault.setContextName("NCIP");
		
		return loaderDefault;
	}
	
	private void setUserSelections() {
		UserSelections userSelections = UserSelections.getInstance();
		userSelections.setProperty("ignore-vd", new Boolean(false));
		UserPreferences userPrefs = (UserPreferences)BeansAccessor.getBeanByName("usersPreferences");
		userPrefs.setUsePrivateApi(true);
		RunMode mode = RunMode.Loader;
	    userSelections.setProperty("MODE", mode);
	    userSelections.setProperty("SKIP_VD_VALIDATION", true);
		System.setProperty("java.security.auth.login.config", Thread.currentThread().getContextClassLoader().getResource("jaas.config").toExternalForm());
	}
	
	private class LoaderDefaultImpl implements LoaderDefault {
		private String contextName;
		private String projectDescription;
		private String projectLongName;
		private String projectName;
		
		public String getCdContextName() {
			return Constants.DEFAULT_CONTEXT;	//"caBIG";
		}
		public String getCdName() {
			return Constants.DEFAULT_CONTEXT;	//"CABIG";
		}
		public String getContextName() {
			if (contextName == null) {
				return "";
			}
			else {
				return contextName;
			}
		}
		public String getId() {
			// TODO Auto-generated method stub
			return null;
		}
		public String getPackageFilter() {
			// TODO Auto-generated method stub
			return null;
		}
		public String getProjectDescription() {
			if (projectDescription == null) {
				return "";
			}
			else {
				return projectDescription;
			}
		}
		public String getProjectLongName() {
			if (projectLongName == null) {
				return "";
			}
			else {
				return projectLongName;
			}
		}
		public String getProjectName() {
			if (projectName == null) {
				return "";
			}
			else {
				return projectName;
			}
		}
		public Float getProjectVersion() {
			return new Float(1.0);
		}
		public Float getVersion() {
			return new Float(1.0);
		}
		public String getWorkflowStatus() {
			return "RELEASED";
		}
		public void setCdContextName(String newCdContextName) {
			// TODO Auto-generated method stub
			
		}
		public void setCdName(String newCdName) {
			// TODO Auto-generated method stub
			
		}
		public void setContextName(String newContextName) {
			contextName = newContextName;
		}
		public void setId(String newId) {
			// TODO Auto-generated method stub
			
		}
		public void setPackageFilter(String newPackageFilter) {
			// TODO Auto-generated method stub
			
		}
		public void setProjectDescription(String newProjectDescription) {
			projectDescription = newProjectDescription;
			
		}
		public void setProjectLongName(String newProjectLongName) {
			projectLongName = newProjectLongName;
		}
		public void setProjectName(String newProjectName) {
			projectName = newProjectName;
		}
		public void setProjectVersion(Float newVersion) {
			// TODO Auto-generated method stub
			
		}
		public void setVersion(Float newVersion) {
			// TODO Auto-generated method stub
			
		}
		public void setWorkflowStatus(String newWorkflowStatus) {
			// TODO Auto-generated method stub
			
		}
		
		public boolean equals(Object o) {
			if (!(o instanceof LoaderDefault)) {
				return false;
			}
			LoaderDefault toCheck = (LoaderDefault)o;
			if (toCheck.getContextName().equals(getContextName()) 
					&& toCheck.getProjectDescription().equals(getProjectDescription())
					&& toCheck.getProjectLongName().equals(getProjectDescription())
					&& toCheck.getProjectName().equals(getProjectName())
					&& toCheck.getProjectVersion().equals(getProjectVersion())
					&& toCheck.getWorkflowStatus().equals(getWorkflowStatus())) {
				return true;
			}
			return false;
		}
	}
	
	public class MockCaDSRLoader {
		Parser parser = (Parser)BeansAccessor.getBeanByName("xmiParser2");
		Validator validator = (Validator) BeansAccessor.getBeanByName("loaderValidator");
		Persister persister = (Persister) BeansAccessor.getBeanByName("mainPersister");
		
		public void run(String dir, String projectName, Float version) {
			try {
				String[] filenames = new File(dir).list(new FilenameFilter() {
					public boolean accept(File dir, String name) {
					  return name.endsWith(".xmi") || name.endsWith(".uml");
					}
				      });
				
				for(String fileName: filenames) {
					parser.parse(dir + "/" +fileName);
				}
				
				ValidationItems items = validator.validate();
				  Set<ValidationError> errors = items.getErrors();
				  if(errors.size() > 0) {
				    for(ValidationError error : errors) {
				      System.out.println(error.getMessage());
				    }
				    System.exit(1);
				  }
				  
				  persister.persist();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
