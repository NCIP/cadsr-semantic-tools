package gov.nih.nci.ncicb.cadsr.loader.defaults;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;

import gov.nih.nci.ncicb.cadsr.loader.persister.PersisterException;
import gov.nih.nci.ncicb.cadsr.loader.persister.LogUtil;

import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import org.apache.log4j.Logger;

import java.util.*;

public class DBDefaultsLoader implements DefaultsLoader {

  private LoaderDAO loaderDAO = DAOAccessor.getLoaderDAO();

  public LoaderDefault loadDefaults(String projectName, Float projectVersion) {

    return loaderDAO.findByProject(projectName, projectVersion);

  }

}