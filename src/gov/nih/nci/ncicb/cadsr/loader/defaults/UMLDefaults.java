package gov.nih.nci.ncicb.cadsr.loader.defaults;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.loader.persister.PersisterException;
import gov.nih.nci.ncicb.cadsr.loader.persister.LogUtil;

import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * <code>UMLDefaults</code> is a placeholder for UMLLoader's defaults.
 * <br/> Implements the Singleton pattern.
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 * @version 1.0
 */
public class UMLDefaults {
  private static Logger logger = Logger.getLogger(UMLDefaults.class.getName());

  private static UMLDefaults singleton = new UMLDefaults();

  private String projectName, projectLongName, projectDescription;
  private Float projectVersion;
  private String workflowStatus;
  private Float version;
  private Context context;
  private ConceptualDomain conceptualDomain;
  private ClassificationScheme projectCs;
  private Context mainContext;

  // default Audit holds username
  private Audit audit;
  private Map packageCsCsis = new HashMap();

  private Map params = new HashMap();

  private Map packageFilter = new HashMap();
  private String defaultPackage = null;

  private UMLDefaults() {
  }

  /**
   *
   * @return the singleton
   */
  public static UMLDefaults getInstance() {
    return singleton;
  }

  
  /**
   * Called by the main class to initialize defaults.
   *
   * @param projectName the project name
   * @param username the authenticated username
   * @exception PersisterException if an error occurs
   */

  public void initParams(String filename) throws PersisterException  {
    LoaderDefault loaderDefault = new AttachedFileDefaultsLoader().loadDefaults(filename);
    initParams(loaderDefault);
  }

  public void initParams(String projectName, Float projectVersion, String username) throws PersisterException {
    LoaderDefault loaderDefault = new DBDefaultsLoader().loadDefaults(projectName, projectVersion);

    initParams(loaderDefault);

  }

  private void initParams(LoaderDefault loaderDefault) throws PersisterException {

    audit = DomainObjectFactory.newAudit();

    if (loaderDefault == null) {
      throw new PersisterException(
	"Defaults not found. Please create a profile first.");
    }

    this.projectName = loaderDefault.getProjectName();
    this.projectVersion = loaderDefault.getProjectVersion();
    
    String cName = loaderDefault.getContextName();
    
    if (cName == null) {
      throw new PersisterException("Context Name not Set.");
    }
    
    context = DomainObjectFactory.newContext();
    context.setName(cName);

    
    version = new Float(loaderDefault.getVersion().toString());
    
    workflowStatus = loaderDefault.getWorkflowStatus();
    
    if (workflowStatus == null) {
      throw new PersisterException("WorkflowStatus not Set.");
    }
    
    projectLongName = loaderDefault.getProjectLongName();
    projectDescription = loaderDefault.getProjectDescription();

    conceptualDomain = DomainObjectFactory.newConceptualDomain();
    conceptualDomain.setPreferredName(loaderDefault.getCdName());

    Context cdContext = DomainObjectFactory.newContext();
    cdContext.setName(loaderDefault.getCdContextName());

    conceptualDomain.setContext(cdContext);


    logger.info(PropertyAccessor.getProperty("listOfPackages"));
    String filt = loaderDefault.getPackageFilter();
    if(filt != null) {
      String[] pkgs = filt.split(",");
      for(int i=0; i<pkgs.length; i++) {
        String s = pkgs[i].trim();
        int ind = s.indexOf(">");
        
        String alias = null; 
        String pkg = null;
        if(ind > 0) {
          alias = s.substring(1, ind).trim();
          pkg = s.substring(ind+1).trim();
          if((pkg.length() == 0) && (pkgs.length == 1)) {
            defaultPackage = alias;
            logger.info(PropertyAccessor.getProperty("packageAlias.default", alias));
          }
        } else {
          alias = pkg = s;
        }
        
        packageFilter.put(pkg, alias);
        
        if(defaultPackage == null)
          logger.info(PropertyAccessor.getProperty("packageAlias", new String[] {pkg, alias}));
      }
      logger.info(PropertyAccessor.getProperty("endOfList"));
    } else {
      logger.info(PropertyAccessor.getProperty("no.package.filter"));
    }
  }

  public void initWithDB() throws PersisterException {
    ContextDAO contextDAO = DAOAccessor.getContextDAO();
    ConceptualDomainDAO conceptualDomainDAO = DAOAccessor.getConceptualDomainDAO();

    context = contextDAO.findByName(context.getName());
    if (context == null) {
      logger.fatal(PropertyAccessor.getProperty("context.not.found", context.getName()));
      System.exit(1);
    }
    
    mainContext = contextDAO.findByName(PropertyAccessor.getProperty("context.main.name"));
    if (mainContext == null) {
      logger.fatal(PropertyAccessor.getProperty("context.not.found", PropertyAccessor.getProperty("context.main.name")));
      System.exit(1);
    } 

    Context cdContext = contextDAO.findByName(conceptualDomain.getContext().getName());
    if (cdContext == null) {
      throw new PersisterException("CD Context not found.");
    }
    conceptualDomain.setContext(cdContext);
      
    try {
      conceptualDomain = (ConceptualDomain) conceptualDomainDAO.find(conceptualDomain)
        .get(0);
    } catch (NullPointerException e) {
      throw new PersisterException("CD: " +
                                   conceptualDomain.getPreferredName() + " not found.");
    }
    
    initClassifications();

  }

  /**
   * Creates or loads classifications and CSI that will be used.
   *
   * @exception PersisterException if an error occurs
   */
  private void initClassifications() throws PersisterException {
    
    ClassificationSchemeDAO classificationSchemeDAO = DAOAccessor.getClassificationSchemeDAO();

    projectCs = DomainObjectFactory.newClassificationScheme();
    projectCs.setPreferredName(projectName);
    projectCs.setVersion(projectVersion);
    projectCs.setContext(context);

    ArrayList eager = new ArrayList();
    eager.add(EagerConstants.CS_CSI);
    List result = classificationSchemeDAO.find(projectCs, eager);

    if (result.size() == 0) { // need to add projectName CS
      projectCs.setLongName(projectLongName);
      projectCs.setWorkflowStatus(projectCs.WF_STATUS_DRAFT_NEW);

      projectCs.setPreferredDefinition(projectDescription);

      // !!! TODO
      projectCs.setType("Project");
      projectCs.setLabelType(ClassificationScheme.LABEL_TYPE_ALPHA);

      projectCs.setAudit(audit);
      projectCs.setId(classificationSchemeDAO.create(projectCs));

      logger.info(PropertyAccessor.getProperty("created.project.cs"));
      LogUtil.logAc(projectCs, logger);
      logger.info(PropertyAccessor.getProperty("type", projectCs.getType()));

    } 
    else { 
      logger.info(PropertyAccessor.getProperty("existed.project.cs"));
      projectCs = (ClassificationScheme) result.get(0);

    }

  }

  /**
   * @return default workflow status
   */
  public String getWorkflowStatus() {
    return workflowStatus;
  }
  /**
   * @return default context
   */
  public Context getContext() {

    return context;
  }
  /**
   * @return default version
   */
  public Float getVersion() {
    return version;
  }
  /**
   * @return default CD
   */
  public ConceptualDomain getConceptualDomain() {
    return conceptualDomain;
  }
  /**
   * @return the project CS
   */
  public ClassificationScheme getProjectCs() {
    return projectCs;
  }

  public void refreshProjectCs() {
    ClassificationSchemeDAO classificationSchemeDAO = DAOAccessor.getClassificationSchemeDAO();

    ArrayList eager = new ArrayList();
    eager.add(EagerConstants.CS_CSI);
    projectCs = (ClassificationScheme)(classificationSchemeDAO.find(projectCs, eager).get(0));
    
  }
  
  /**
   * @return default Audit object -- contains username.
   */
  public Audit getAudit() {
    return audit;
  }
  /**
   * @return package cs_csi
   */
  public Map getPackageCsCsis() {
    return packageCsCsis;
  }
  /**
   * The package filter contains a list of packages that will be loaded by UML Loader. If empty, all packages are to be loaded.
   * @return the package filter.
   */
  public Map getPackageFilter() {
    return packageFilter;
  }

  public String getDefaultPackageAlias() {
    return defaultPackage;
  }

  public String getPackageDisplay(String packageName) {
    if(packageFilter.containsKey(packageName))
      return (String)packageFilter.get(packageName);
    else if(defaultPackage != null)
      return defaultPackage;
    else
      return packageName;
  }

  public Context getMainContext() {
    return mainContext;
  }
 
  public void setUsername(String username) {
    audit.setCreatedBy(username);
  }
 
}