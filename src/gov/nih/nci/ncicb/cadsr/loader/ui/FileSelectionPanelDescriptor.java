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
//     panel.addFileActionListener(this);
    setPanelComponent(panel);
  }
  
  public Object getNextPanelDescriptor() {
    return ValidationPanelDescriptor.IDENTIFIER;
  }
  
  public Object getBackPanelDescriptor() {
    return ModeSelectionPanelDescriptor.IDENTIFIER;
  }  
  
  public void actionPerformed(ActionEvent evt) {
    String path = panel.getSelection();
    File f = new File(path);
    getWizardModel().setNextButtonEnabled(new Boolean(f.exists()));
  }
    
}
