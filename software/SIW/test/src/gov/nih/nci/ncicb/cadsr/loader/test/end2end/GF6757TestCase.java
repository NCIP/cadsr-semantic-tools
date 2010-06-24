package gov.nih.nci.ncicb.cadsr.loader.test.end2end;

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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Set;

import javax.sql.DataSource;

public class GF6757TestCase extends MainTestCase {

	private UMLDefaults defaults = UMLDefaults.getInstance();
	
	public GF6757TestCase() {
		super("GF6757TestCase", GF6757TestCase.class, "/gov/nih/nci/ncicb/cadsr/loader/test/end2end/gf6757-2.xls");
	}
	
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
		LoaderDefault loaderDefault = getLoaderDefaults();
		defaults.useDefaults(loaderDefault);
		defaults.initWithDB();
		setUserSelections();
	}
	
	public void testGF6757() {
		try {
			MockCaDSRLoader loader = new MockCaDSRLoader();
			loader.run("c:/documents and settings/mathura2/desktop/xmi/gf6757", "SIW_TEST", new Float(1.0));
			
			try {
				DataSource ds = super.getDataSource();
				Connection con = ds.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("select * from CON_DERIVATION_RULES_EXT a, COMPONENT_CONCEPTS_EXT b, CONCEPTS_EXT c " +
													"where a.CONDR_IDSEQ=b.CONDR_IDSEQ and b.CON_IDSEQ=c.CON_IDSEQ and c.PREFERRED_NAME in ('C51964', 'C48936')");
				
				assertTrue(rs.next());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
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
		loaderDefault.setContextName("caBIG");
		
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
			return "caBIG";
		}
		public String getCdName() {
			return "CABIG";
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
