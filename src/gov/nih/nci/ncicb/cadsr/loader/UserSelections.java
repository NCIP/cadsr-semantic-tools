package gov.nih.nci.ncicb.cadsr.loader;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.util.*;

public class UserSelections 
  implements PropertyChangeListener 
{

  private HashMap properties = new HashMap();

  private List<PropertyChangeListener> propChangeListeners = new ArrayList();

  public void addPropertyChangeListener(PropertyChangeListener l) {
    propChangeListeners.add(l);
  }

  public void propertyChange(PropertyChangeEvent evt) {
    properties.put(evt.getPropertyName(), evt.getNewValue());
  }

  public void setProperty(String name, Object value) {
    properties.put(name, value);
  }

  public Object getProperty(String name) {
    return properties.get(name);
  }

  private void firePropertyChange(PropertyChangeEvent evt) {

  }

}