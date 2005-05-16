package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import gov.nih.nci.ncicb.cadsr.loader.UserSelections;

public class LoginPanelDescriptor extends WizardPanelDescriptor 
                                          implements ActionListener
 {
    
   public static final String IDENTIFIER = "LOGIN_PANEL";
   
   private LoginPanel panel;
    
    public LoginPanelDescriptor() {
      panel = new LoginPanel();
//       panel.addPropertyChangeListener(UserSelections.getInstance());
      setPanelComponent(panel);
      setPanelDescriptorIdentifier(IDENTIFIER);
    }
    
    public Object getNextPanelDescriptor() {
      return ProgressLoginPanelDescriptor.IDENTIFIER;
    }
    
    public Object getBackPanelDescriptor() {
      return null;
//         return DefaultSelectionPanelDescription.IDENTIFIER;
    }  

   public void actionPerformed(ActionEvent evt) {
//      setNextButtonAccordingToCheckBox();
   }
            
    
   private void setNextButtonAccordingToCheckBox() {
//      if (panel.isCheckBoxSelected())
//        getWizardModel().setNextButtonEnabled(Boolean.TRUE);
//      else
//        getWizardModel().setNextButtonEnabled(Boolean.FALSE);    
   }
   
}
