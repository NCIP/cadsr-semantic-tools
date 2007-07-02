package gov.nih.nci.ncicb.cadsr.loader.ui;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.event.ReviewEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ReviewEventType;
import gov.nih.nci.ncicb.cadsr.loader.event.ReviewListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationEvent;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.ReviewableUMLNode;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.UMLNode;
import gov.nih.nci.ncicb.cadsr.loader.util.RunMode;
import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel implements ActionListener, 
  PropertyChangeListener
{
  private JButton switchButton;
  private JCheckBox reviewButton;

  private NavigationButtonPanel navigationButtonPanel;
  private AddButtonPanel addButtonPanel;
  private ApplyButtonPanel applyButtonPanel;

    
  private List<NavigationListener> navigationListeners 
    = new ArrayList<NavigationListener>();
    
  private List<PropertyChangeListener> propChangeListeners 
    = new ArrayList<PropertyChangeListener>();
  
  private ConceptEditorPanel conceptEditorPanel;
  private Editable viewPanel;
  private Editable editable;
  
  static final String 
    PREVIEW = "PREVIEW",
    SETUP = "SETUP",
    SWITCH = "SWITCH",
    DELETEBUTTON = "DELETEBUTTON";

  static final String 
    SWITCH_TO_DE = "Map to DE",
    SWITCH_TO_OC = "Map to OC",
    SWITCH_TO_CONCEPT = "Map to Concepts";
    
  private RunMode runMode = null;
    
  
  public void addPropertyChangeListener(PropertyChangeListener l) {
    propChangeListeners.add(l);

    applyButtonPanel.addPropertyChangeListener(l);
  }
  
  public ButtonPanel(ConceptEditorPanel conceptEditorPanel, 
    Editable viewPanel, Editable editable) 
  {

    this.conceptEditorPanel = conceptEditorPanel;
    this.viewPanel = viewPanel;
    this.editable = editable;
    
    UserSelections selections = UserSelections.getInstance();
    
    runMode = (RunMode)(selections.getProperty("MODE"));
    
    switchButton = new JButton();


    ReviewableUMLNode revNode = (ReviewableUMLNode)conceptEditorPanel.getNode();

    switchButton.setActionCommand(SWITCH);
    
    switchButton.addActionListener(this);
    
    navigationButtonPanel = new NavigationButtonPanel();
    addButtonPanel = new AddButtonPanel(conceptEditorPanel);
    applyButtonPanel = new ApplyButtonPanel(viewPanel, revNode);

    this.add(addButtonPanel);
    this.add(applyButtonPanel);

    this.add(navigationButtonPanel);

    this.add(switchButton);

  }
  
  public void update() 
  {
    applyButtonPanel.update();
    if(editable instanceof DEPanel) {
      DataElement de = (DataElement)conceptEditorPanel.getNode().getUserObject();
      if(!StringUtil.isEmpty(de.getPublicId())) 
      {
        setSwitchButtonText(ButtonPanel.SWITCH_TO_CONCEPT);
      } else {
        setSwitchButtonText(ButtonPanel.SWITCH_TO_DE);
      }
    } else if(editable instanceof OCPanel) {
      ObjectClass oc = (ObjectClass)conceptEditorPanel.getNode().getUserObject();
      if(!StringUtil.isEmpty(oc.getPublicId())) 
      {
        setSwitchButtonText(ButtonPanel.SWITCH_TO_CONCEPT);
      }
      else 
      {
        setSwitchButtonText(ButtonPanel.SWITCH_TO_OC);
      }
    } else if(editable == null) { 
      return;
    }
  }
  
  public void addReviewListener(ReviewListener listener) {
//     reviewListeners.add(listener);
    applyButtonPanel.addReviewListener(listener);
  }
  
  public void addNavigationListener(NavigationListener listener) 
  {
    navigationListeners.add(listener);

    navigationButtonPanel.addNavigationListener(listener);
    applyButtonPanel.addNavigationListener(listener);
  }
  
  public void propertyChange(PropertyChangeEvent e) 
  {
    if (e.getPropertyName().equals(SETUP)) {
      initButtonPanel();
    } else if (e.getPropertyName().equals(SWITCH)) {
      switchButton.setEnabled((Boolean)e.getNewValue());
    }

    addButtonPanel.propertyChange(e);
    applyButtonPanel.propertyChange(e);
  }
  
  private void fireNavigationEvent(NavigationEvent event) 
  {
    for(NavigationListener l : navigationListeners)
      l.navigate(event);
  }
  
  private void setSwitchButtonText(String text) 
  {
    switchButton.setText(text);
  }
  
  public void setEnabled(boolean enabled) {
      addButtonPanel.setVisible(enabled);
      applyButtonPanel.setVisible(enabled);

      if (enabled) {
          // enabling does not necessarily enable every single button
          initButtonPanel();
          return;
      }
//       addButton.setEnabled(false);
//       saveButton.setEnabled(false);
      switchButton.setEnabled(false);
//       reviewButton.setEnabled(false);
  }
  
  private void initButtonPanel() {  
    Concept[] concepts = conceptEditorPanel.getConcepts();
//     if(conceptEditorPanel.areAllFieldEntered()) {
//       reviewButton.setEnabled(true);
// //       addButton.setEnabled(true);
//     } else {
// //       addButton.setEnabled(false);
//       reviewButton.setEnabled(false);
//     }

    boolean reviewButtonState = false;
    if(concepts.length == 0)
      reviewButtonState = false;
    else 
      reviewButtonState = true;
  
    switchButton.setVisible(editable instanceof DEPanel);
    
    if(editable instanceof DEPanel && conceptEditorPanel.getVDPanel().isMappedToLocalVD())
      switchButton.setEnabled(false);
    
    // disable add if DEPanel is showing
    if(editable instanceof DEPanel) {
      DataElement de = (DataElement)conceptEditorPanel.getNode().getUserObject();
      if(!StringUtil.isEmpty(de.getPublicId())) {
        addButtonPanel.setVisible(false);
        reviewButtonState = true;
      } else {
        addButtonPanel.setVisible(true);
        
      }
    } else if(editable instanceof OCPanel) {
      ObjectClass oc = (ObjectClass)conceptEditorPanel.getNode().getUserObject();
      if(!StringUtil.isEmpty(oc.getPublicId())) {
        addButtonPanel.setVisible(false);
        reviewButtonState = true;
      } else {
        addButtonPanel.setVisible(true);
      }
    } else if(editable == null) {
      addButtonPanel.setVisible(true);
    }

    applyButtonPanel.init(reviewButtonState);
    
  }
  
  
  public void setEditablePanel(Editable editable) {
    this.editable = editable;
  }

  public void navigate(NavigationEvent evt) {  
    applyButtonPanel.navigate(evt);
  }
  
  private void firePropertyChangeEvent(PropertyChangeEvent evt) {
    for(PropertyChangeListener l : propChangeListeners) 
      l.propertyChange(evt);
  }
  
  public void actionPerformed(ActionEvent evt) {
    AbstractButton button = (AbstractButton)evt.getSource();
    if(button.getActionCommand().equals(SWITCH)) {
      if(switchButton.getText().equals(SWITCH_TO_DE)) {
        ((UMLElementViewPanel)viewPanel).switchCards(UMLElementViewPanel.DE_PANEL_KEY);
        switchButton.setText(SWITCH_TO_CONCEPT);
        addButtonPanel.setVisible(false);
      } else if (switchButton.getText().equals(SWITCH_TO_CONCEPT)) {
        ((UMLElementViewPanel)viewPanel).switchCards(UMLElementViewPanel.CONCEPT_PANEL_KEY);
        if(editable instanceof DEPanel) {
          switchButton.setText(SWITCH_TO_DE);
        } else if(editable instanceof OCPanel) {
          switchButton.setText(SWITCH_TO_OC);
        } else if(editable == null) {
        
        }
        addButtonPanel.setVisible(true);
      } else if(switchButton.getText().equals(SWITCH_TO_OC)) {
         ((UMLElementViewPanel)viewPanel).switchCards(UMLElementViewPanel.OC_PANEL_KEY);
         switchButton.setText(SWITCH_TO_CONCEPT);
      }
    }
  }
 
  
}