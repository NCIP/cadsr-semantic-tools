package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.event.*;
import gov.nih.nci.ncicb.cadsr.loader.util.RunMode;

public class PackageClassFilterPanelDescriptor 
  extends WizardPanelDescriptor 
  {
  
  public static final String IDENTIFIER = "PACKAGE_CLASS_FILTER_PANEL";
  private PackageClassFilterPanel panel;
    
  public PackageClassFilterPanelDescriptor() {
    panel =  new PackageClassFilterPanel();
    setPanelDescriptorIdentifier(IDENTIFIER);
    setPanelComponent(panel);

    backPanelDescriptor = FileSelectionPanelDescriptor.IDENTIFIER;
    nextPanelDescriptor = ProgressFileSelectionPanelDescriptor.IDENTIFIER;

  }

    public void init() 
    {   
      panel.init();
    }
    
}
