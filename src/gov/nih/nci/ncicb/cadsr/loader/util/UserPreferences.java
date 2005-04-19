package gov.nih.nci.ncicb.cadsr.loader.util;

import java.util.prefs.Preferences;

public class UserPreferences {

  public String getXmiDir() {
    Preferences prefs = Preferences.userRoot().node("UMLLOADER");
    return prefs.get("xmiDir","/");
  }

  public void setXmiDir(String dir) {
    Preferences prefs = Preferences.userRoot().node("UMLLOADER");
    prefs.put("xmiDir",dir);
  }

}
