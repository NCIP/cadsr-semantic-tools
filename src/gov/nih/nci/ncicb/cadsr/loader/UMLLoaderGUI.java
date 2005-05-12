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

    WizardPanelDescriptor modeSelDesc = new ModeSelectionPanelDescriptor();
    wizard.registerWizardPanel(ModeSelectionPanelDescriptor.IDENTIFIER, modeSelDesc);

    WizardPanelDescriptor descriptor2 = new FileSelectionPanelDescriptor();
    wizard.registerWizardPanel(FileSelectionPanelDescriptor.IDENTIFIER, descriptor2);

    WizardPanelDescriptor validationDesc = new ValidationPanelDescriptor();
    wizard.registerWizardPanel(ValidationPanelDescriptor.IDENTIFIER, validationDesc);

    WizardPanelDescriptor descriptor3 = new SemanticConnectorPanelDescriptor();
    wizard.registerWizardPanel(SemanticConnectorPanelDescriptor.IDENTIFIER, descriptor3);


    WizardPanelDescriptor descriptor4 = new ProgressSemanticConnectorPanelDescriptor();
    wizard.registerWizardPanel(ProgressSemanticConnectorPanelDescriptor.IDENTIFIER, descriptor4);

    
    wizard.setCurrentPanel(ModeSelectionPanelDescriptor.IDENTIFIER);
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

    new UMLLoaderGUI();
  }

  private void putToCenter(Component comp) {
    comp.setLocation((screenSize.width - comp.getSize().width) / 2, (screenSize.height - comp.getSize().height) / 2);
  }

}