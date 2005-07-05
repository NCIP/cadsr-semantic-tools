package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

public class ProgressFileSelectionPanelDescriptor extends WizardPanelDescriptor 
  implements ProgressListener {
  
  public static final String IDENTIFIER = "PROGRESS_FILE_SELECTION_PANEL";
  
  private FileSelectionPanel panel;

  public ProgressFileSelectionPanelDescriptor() {
    panel =  new FileSelectionPanel(-1);
    setPanelDescriptorIdentifier(IDENTIFIER);
    setPanelComponent(panel);
  }
  
  public void newProgressEvent(ProgressEvent evt) {
    if(evt.getStatus() < 0) { // Failure
      getWizardModel().setBackButtonEnabled(Boolean.TRUE);
      getWizard().doClickBack();
    }

    panel.newProgressEvent(evt);
    if(evt.getStatus() > 0)
      if(evt.getGoal() == evt.getStatus()) {
        getWizardModel().setNextButtonEnabled(Boolean.TRUE);
        getWizard().doClickNext();
      }
  }

  public void aboutToDisplayPanel() {
    getWizardModel().setNextButtonEnabled(Boolean.FALSE);
  }
  
  public Object getBackPanelDescriptor() {
    return FileSelectionPanelDescriptor.IDENTIFIER;
  }  
    
}
