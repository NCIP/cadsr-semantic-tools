package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.event.*;
import java.io.*;

public class SemanticConnectorPanelDescriptor 
  extends WizardPanelDescriptor {
  
  public static final String IDENTIFIER = "SEMANTIC_CONNECTOR_PANEL";
    
  public SemanticConnectorPanelDescriptor() {
    super(IDENTIFIER, new SemanticConnectorPanel());
  }
  
  public Object getNextPanelDescriptor() {
    return FINISH;
  }
  
  public Object getBackPanelDescriptor() {
    return FileSelectionPanelDescriptor.IDENTIFIER;
  }  
  
    
}
