package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.event.*;
import java.io.*;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

public class ReportConfirmPanelDescriptor 
  extends WizardPanelDescriptor {
  
  public static final String IDENTIFIER = "REPORT CONFIRM";
  
  private ReportConfirmPanel panel;
    
  public ReportConfirmPanelDescriptor() {
    panel =  new ReportConfirmPanel();
    setPanelDescriptorIdentifier(IDENTIFIER);
    setPanelComponent(panel);

    backPanelDescriptor = FileSelectionPanelDescriptor.IDENTIFIER;
  }

  public Object getNextPanelDescriptor() {
    return FINISH;
  }
}