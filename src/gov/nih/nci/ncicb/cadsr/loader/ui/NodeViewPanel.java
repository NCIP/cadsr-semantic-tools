package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.ui.event.*;
import gov.nih.nci.ncicb.cadsr.loader.event.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public interface NodeViewPanel extends NavigationListener {

  public void addReviewListener(ReviewListener listener);

  public void addNavigationListener(NavigationListener listener);
  
  public void addElementChangeListener(ElementChangeListener listener);

  public void addPropertyChangeListener(PropertyChangeListener l);

  public String getName();
  public void setName(String name);

}