package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.event.*;
import java.io.*;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

public class ProgressSemanticConnectorPanelDescriptor 
  extends WizardPanelDescriptor 
  implements ProgressListener {
  
  public static final String IDENTIFIER = "PROGRESS_SEMANTIC_CONNECTOR_PANEL";
  
  private SemanticConnectorPanel panel;
    
  public void newProgressEvent(ProgressEvent evt) {
    panel.newProgressEvent(evt);
    if(evt.getGoal() == evt.getStatus()) {
      getWizardModel().setNextButtonEnabled(Boolean.TRUE);
    }
  }


  public ProgressSemanticConnectorPanelDescriptor() {
    panel =  new SemanticConnectorPanel(-1);
    setPanelDescriptorIdentifier(IDENTIFIER);
    setPanelComponent(panel);
  }
 
  public void aboutToDisplayPanel() {
    getWizardModel().setNextButtonEnabled(Boolean.FALSE);
  }
 
  public Object getNextPanelDescriptor() {
    return FINISH;
  }
  
  public Object getBackPanelDescriptor() {
    return FileSelectionPanelDescriptor.IDENTIFIER;
  }  

}
