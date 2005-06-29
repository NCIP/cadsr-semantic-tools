package gov.nih.nci.ncicb.cadsr.loader.util;
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.ui.ModeSelectionPanel;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.*;
import java.util.prefs.Preferences;
import java.util.*;

public class UserPreferences {

  Preferences prefs = Preferences.userRoot().node("UMLLOADER");
  List<UserPreferencesListener> userPrefsListeners = new ArrayList(); 
  private UserSelections userSelections = UserSelections.getInstance();
  private static UserPreferences instance = new UserPreferences();
  private UserPreferences() {}
  public static UserPreferences getInstance() {
    return instance;
  }
  
  public String getViewAssociationType() 
  {
    return prefs.get("ViewAssociations", "false");
  }
  
  public void setViewAssociationType(String value) 
  {
    prefs.put("ViewAssociations", value);
    UserPreferencesEvent event = new UserPreferencesEvent(UserPreferencesEvent.VIEW_ASSOCIATION, value);
    fireUserPreferencesEvent(event);
  }

  public String getUmlDescriptionOrder() 
  {
    return prefs.get("UmlDescriptionOrder", "last");
  }
  
  public void setUmlDescriptionOrder(String value) 
  {
    prefs.put("UmlDescriptionOrder", value);
    UserPreferencesEvent event = new UserPreferencesEvent(UserPreferencesEvent.UML_DESCRIPTION, value);
    fireUserPreferencesEvent(event);
  }
  
  public String getModeSelection() 
  {
    return null;
  }
  
  public void setModeSelection(String value) 
  {
    prefs.put("ModeSelection", value);
  }

  public String getXmiDir() {
    return prefs.get("xmiDir","/");
  }

  public void setXmiDir(String dir) {
    prefs.put("xmiDir",dir);
  }

  public List<String> getRecentFiles() {
    UserSelections selections = UserSelections.getInstance();
    RunMode runMode = (RunMode)(selections.getProperty("MODE"));

    if(runMode == null) {
      return new ArrayList();
    }
    String s = prefs.get(runMode.toString() + "-recentFiles", "");
    if(StringUtil.isEmpty(s))
      return new ArrayList();
    
    return new ArrayList(Arrays.asList(s.split("\\$\\$")));

  }

  public void addRecentFile(String filePath) {
    UserSelections selections = UserSelections.getInstance();
    RunMode runMode = (RunMode)(selections.getProperty("MODE"));

    System.out.println("***** runmode: " + runMode);

    List<String> files = getRecentFiles();
    
    if(!files.contains(filePath)) {
      if(files.size() > 4)
        files.remove(0);
      files.add(filePath);
    } else {
      if(files.size() > 4)
        files.remove(0);
      files.remove(filePath);
      files.add(filePath);
    }
    
    StringBuilder sb = new StringBuilder();
    for(String s : files) {
      if(sb.length() > 0)
        sb.append("$$");
      sb.append(s);
    }
    prefs.put(runMode.toString() + "-recentFiles", sb.toString());
  }

  private void fireUserPreferencesEvent(UserPreferencesEvent event) 
  {
    for(UserPreferencesListener l : userPrefsListeners)
      l.preferenceChange(event);
  }

  public void addUserPreferencesListener(UserPreferencesListener listener) 
  {
    userPrefsListeners.add(listener);
  }

  public void setUserSelections(UserSelections us) {
    userSelections = us;
  }

}
