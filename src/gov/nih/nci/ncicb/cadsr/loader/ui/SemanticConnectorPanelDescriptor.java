package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.event.*;
import java.io.*;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

public class SemanticConnectorPanelDescriptor 
  extends WizardPanelDescriptor {
  
  public static final String IDENTIFIER = "SEMANTIC_CONNECTOR_PANEL";
  
  private SemanticConnectorPanel panel;
    
  public SemanticConnectorPanelDescriptor() {
    panel =  new SemanticConnectorPanel();
    setPanelDescriptorIdentifier(IDENTIFIER);
    setPanelComponent(panel);
  }
  
  public Object getNextPanelDescriptor() {
    return ProgressSemanticConnectorPanelDescriptor.IDENTIFIER;
  }
  
  public Object getBackPanelDescriptor() {
    return FileSelectionPanelDescriptor.IDENTIFIER;
  }  

}
