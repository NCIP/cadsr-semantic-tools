package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.event.*;
import java.io.*;

public class FileSelectionPanelDescriptor 
  extends WizardPanelDescriptor
  implements ActionListener {
  
  public static final String IDENTIFIER = "FILE_SELECTION_PANEL";
  private FileSelectionPanel panel;
    
  public FileSelectionPanelDescriptor() {
    panel =  new FileSelectionPanel();
    setPanelDescriptorIdentifier(IDENTIFIER);
    setPanelComponent(panel);

    nextPanelDescriptor = ProgressFileSelectionPanelDescriptor.IDENTIFIER;
    backPanelDescriptor = ModeSelectionPanelDescriptor.IDENTIFIER;
  }

  public void init() {
    panel.init();
  }
  
  public void actionPerformed(ActionEvent evt) {
    String path = panel.getSelection();
    File f = new File(path);
    getWizardModel().setNextButtonEnabled(new Boolean(f.exists()));
  }
    
}
