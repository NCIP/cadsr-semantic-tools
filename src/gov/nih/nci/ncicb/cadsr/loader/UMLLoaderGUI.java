package gov.nih.nci.ncicb.cadsr.loader;

import gov.nih.nci.ncicb.cadsr.loader.ui.*;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.io.*;
import java.util.*;

import gov.nih.nci.ncicb.cadsr.loader.event.*;
import gov.nih.nci.ncicb.cadsr.loader.parser.*;
import gov.nih.nci.ncicb.cadsr.loader.persister.*;
import gov.nih.nci.ncicb.cadsr.loader.validator.*;
import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;

import org.apache.log4j.Logger;

import gov.nih.nci.ncicb.cadsr.jaas.SwingCallbackHandler;

public class UMLLoaderGUI 
{

  private static Logger logger = Logger.getLogger(UMLLoaderGUI.class.getName());
  private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


  public UMLLoaderGUI()
  {

    System.setProperty("java.security.auth.login.config", Thread.currentThread().getContextClassLoader().getResource("jaas.config").toExternalForm());

    Frame f = new Frame();
    Wizard wizard = new Wizard(f);

    wizard.getDialog().setTitle("UML Loader");
    
//     WizardPanelDescriptor lpDesc = new LoginPanelDescriptor();
//     wizard.registerWizardPanel(LoginPanelDescriptor.IDENTIFIER, lpDesc);

//     WizardPanelDescriptor plpDesc = new ProgressLoginPanelDescriptor();
//     wizard.registerWizardPanel(ProgressLoginPanelDescriptor.IDENTIFIER, plpDesc);

    WizardPanelDescriptor descriptor2 = new FileSelectionPanelDescriptor();
    wizard.registerWizardPanel(FileSelectionPanelDescriptor.IDENTIFIER, descriptor2);

    WizardPanelDescriptor validationDesc = new ValidationPanelDescriptor();
    wizard.registerWizardPanel(ValidationPanelDescriptor.IDENTIFIER, validationDesc);

    WizardPanelDescriptor descriptor3 = new SemanticConnectorPanelDescriptor();
    wizard.registerWizardPanel(SemanticConnectorPanelDescriptor.IDENTIFIER, descriptor3);


    WizardPanelDescriptor descriptor4 = new ProgressSemanticConnectorPanelDescriptor();
    wizard.registerWizardPanel(ProgressSemanticConnectorPanelDescriptor.IDENTIFIER, descriptor4);

    
    wizard.setCurrentPanel(FileSelectionPanelDescriptor.IDENTIFIER);
    int wizResult = wizard.showModalDialog();

    if(wizResult != 0) {
      System.exit(0);
    }

    Frame frame = new MainFrame();
    putToCenter(frame);
    frame.addWindowListener(new WindowAdapter()
      {
        public void windowClosing(WindowEvent e)
        {
          System.exit(0);
        }
      });
    frame.setVisible(true);
  }

  /**
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

    InitClass initClass = new InitClass();
    Thread t = new Thread(initClass);
    /* high priority because:
     * If the user is fast at entering its user name
     * (namely, username / password is provided automatically)
     * Then it's possible for the login module to want to access
     *      spring before it's initialized. 
     * Would not happen in normal run, but in dev runs, it may.
     * This seems to have absolutely no effect (on linux at least). Will investigate later. 
     */
    t.setPriority(Thread.MAX_PRIORITY);
    t.start();

    new UMLLoaderGUI();
  }

//   private String doLogin() {
//     String username = null;
//     try {
//       SwingCallbackHandler handler = new SwingCallbackHandler();
//       putToCenter(handler);
      
//       handler.show();
      
//       LoginContext lc = new LoginContext("UML_Loader", handler);
      
//       while(!handler.isDone()) {
//         try {
//           Thread.currentThread().sleep(100);
//         } catch (InterruptedException e) {

//         } // end of try-catch
//       }

//       lc.login();
//       boolean loginSuccess = true;
      
//       Subject subject = lc.getSubject();
      
//       Iterator it = subject.getPrincipals().iterator();
//       while (it.hasNext()) {
// 	username = it.next().toString();
// 	logger.debug(PropertyAccessor.getProperty("authenticated", username));
//       }
//     } catch (Exception ex) {
//       Icon lockIcon = new ImageIcon(this.getClass().getResource("/security_lock.jpg"));
//       JOptionPane.showMessageDialog((Component)null, "Incorrect Username / password", "Login Failed", JOptionPane.ERROR_MESSAGE, lockIcon);
//       logger.error(PropertyAccessor.getProperty("login.fail",ex.getMessage()));
//       System.exit(1);
//     }
    
//     System.out.println("Done with Login");

//     return username;
    
//   }

  private void putToCenter(Component comp) {
    comp.setLocation((screenSize.width - comp.getSize().width) / 2, (screenSize.height - comp.getSize().height) / 2);
  }

}