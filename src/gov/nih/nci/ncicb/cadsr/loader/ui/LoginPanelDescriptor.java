package gov.nih.nci.ncicb.cadsr.loader.ui;

public class LoginPanelDescriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "LOGIN_PANEL";
    
    public LoginPanelDescriptor() {
        super(IDENTIFIER, new LoginPanel());
    }
    
    public Object getNextPanelDescriptor() {
      return FileSelectionPanelDescriptor.IDENTIFIER;
    }
    
    public Object getBackPanelDescriptor() {
      return null;
//         return DefaultSelectionPanelDescription.IDENTIFIER;
    }  
    
}
