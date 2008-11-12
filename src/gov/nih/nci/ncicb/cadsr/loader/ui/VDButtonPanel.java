package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.event.ReviewListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationEvent;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.ReviewableUMLNode;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

public class VDButtonPanel extends JPanel implements ActionListener, 
  PropertyChangeListener
{
  public JButton switchButton;

  private ApplyButtonPanel applyButtonPanel;

  private List<PropertyChangeListener> propChangeListeners = new ArrayList<PropertyChangeListener>();
  
  private Editable viewPanel;
  
  static final String SWITCH_TO_VD_SEARCH = "Map to Existing VD", 
    SWITCH_TO_VD_FIELDS = "Enter VD Details";
  
  public void addPropertyChangeListener(PropertyChangeListener l) {
    propChangeListeners.add(l);

    applyButtonPanel.addPropertyChangeListener(l);
  }
  
  public VDButtonPanel(Editable viewPanel) 
  {

    this.viewPanel = viewPanel;
    
    switchButton = new JButton(SWITCH_TO_VD_SEARCH);
    switchButton.addActionListener(this);
    JPanel switchButtonPanel = new JPanel();
    switchButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
    switchButtonPanel.add(switchButton);
    
    applyButtonPanel = new ApplyButtonPanel(viewPanel, null);
    setLayout(new FlowLayout(FlowLayout.LEFT));
    this.add(applyButtonPanel);
    this.add(switchButtonPanel);

  }
  
  public void update(ReviewableUMLNode node, boolean showMapToVDScreen) 
  {
    applyButtonPanel.update(node);
      if(showMapToVDScreen)
        setSwitchButtonText(VDButtonPanel.SWITCH_TO_VD_SEARCH);
      else
        setSwitchButtonText(VDButtonPanel.SWITCH_TO_VD_FIELDS);
  }
  
  public void propertyChange(PropertyChangeEvent e) 
  {
    applyButtonPanel.propertyChange(e);
  }
  
  private void setSwitchButtonText(String text) 
  {
    switchButton.setText(text);
  }
  
  public void setEnabled(boolean enabled) {
      applyButtonPanel.setVisible(enabled);
  }
  
  
  private void firePropertyChangeEvent(PropertyChangeEvent evt) {
    for(PropertyChangeListener l : propChangeListeners) 
      l.propertyChange(evt);
  }
  
  public void actionPerformed(ActionEvent evt) {
    if(switchButton.getText().equals(SWITCH_TO_VD_SEARCH)) {
      ((LVDPanel)viewPanel).switchCards(LVDPanel.VD_MTE_PANEL_KEY);
      switchButton.setText(SWITCH_TO_VD_FIELDS);
    } else if(switchButton.getText().equals(SWITCH_TO_VD_FIELDS)) {
      ((LVDPanel)viewPanel).switchCards(LVDPanel.VD_FIELDS_PANEL_KEY);
      switchButton.setText(SWITCH_TO_VD_SEARCH);
    }
  } 
  
  public void addNavigationListener(NavigationListener listener) 
  {
    applyButtonPanel.addNavigationListener(listener);
  }
   
  public void navigate(NavigationEvent evt) {  
    applyButtonPanel.navigate(evt);
  }
  
  public void addReviewListener(ReviewListener listener) {
    applyButtonPanel.addReviewListener(listener);
  }
    
}