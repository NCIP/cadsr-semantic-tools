package gov.nih.nci.ncicb.cadsr.loader.defaults;

import gov.nih.nci.ncicb.cadsr.domain.*;

public interface DefaultsLoader {

  public abstract LoaderDefault loadDefaults(String projectName, Float projectVersion);

}