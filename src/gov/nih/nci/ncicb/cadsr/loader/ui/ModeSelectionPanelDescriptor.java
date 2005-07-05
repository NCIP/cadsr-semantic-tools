package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.event.*;

public class ModeSelectionPanelDescriptor 
  extends WizardPanelDescriptor {
  
  public static final String IDENTIFIER = "MODE_SELECTION_PANEL";
  private ModeSelectionPanel panel;
    
  public ModeSelectionPanelDescriptor() {
    panel =  new ModeSelectionPanel();
    setPanelDescriptorIdentifier(IDENTIFIER);
    setPanelComponent(panel);
  }
  
  public Object getNextPanelDescriptor() {
    return FileSelectionPanelDescriptor.IDENTIFIER;
  }
  
  public Object getBackPanelDescriptor() {
    return null;
  }  
  
//   public void actionPerformed(ActionEvent evt) {
//     String path = panel.getSelection();
//     File f = new File(path);
//     getWizardModel().setNextButtonEnabled(new Boolean(f.exists()));
//   }
    
}
