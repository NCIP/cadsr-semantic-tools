package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.event.*;
import java.io.*;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

public class ValidationPanelDescriptor 
  extends WizardPanelDescriptor {
  
  public static final String IDENTIFIER = "VALIDATION_PANEL";
  
  private ValidationPanel panel;
    
  public ValidationPanelDescriptor() {
    panel =  new ValidationPanel();
    setPanelDescriptorIdentifier(IDENTIFIER);
    setPanelComponent(panel);
  }
  
  public Object getNextPanelDescriptor() {
    return SemanticConnectorPanelDescriptor.IDENTIFIER;
  }
  
  public Object getBackPanelDescriptor() {
    return FileSelectionPanelDescriptor.IDENTIFIER;
  }  

}
