package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.util.BeansAccessor;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

import javax.security.auth.login.*;

import gov.nih.nci.ncicb.cadsr.loader.*;
import gov.nih.nci.ncicb.cadsr.loader.event.*;
import gov.nih.nci.ncicb.cadsr.loader.parser.*;
import gov.nih.nci.ncicb.cadsr.loader.validator.*;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.TreeBuilder;
import gov.nih.nci.ncicb.cadsr.loader.util.*;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;

import java.io.File;

import org.apache.log4j.Logger;


/**
 * This class is responsible for reacting to events generated by pushing any of the
 * three buttons, 'Next', 'Previous', and 'Cancel.' Based on what button is pressed,
 * the controller will update the model to show a new panel and reset the state of
 * the buttons as necessary.
 */
public class WizardController implements ActionListener {
    
  private Wizard wizard;
  private String username;
  private String filename;
  private String outputCsv;

  private RunMode mode;

  private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
  private static Logger logger = Logger.getLogger(WizardController.class.getName());

  private UserSelections userSelections = UserSelections.getInstance();

  private UserPreferences prefs = UserPreferences.getInstance();
    /**
     * This constructor accepts a reference to the Wizard component that created it,
     * which it uses to update the button components and access the WizardModel.
     * @param w A callback to the Wizard component that created this controller.
     */    
    public WizardController(Wizard w) {
      wizard = w;

    }
  
    /**
     * Calling method for the action listener interface. This class listens for actions
     * performed by the buttons in the Wizard class, and calls methods below to determine
     * the correct course of action.
     * @param evt The ActionEvent that occurred.
     */    
    public void actionPerformed(java.awt.event.ActionEvent evt) {
      if (evt.getActionCommand().equals(Wizard.CANCEL_BUTTON_ACTION_COMMAND))
        cancelButtonPressed();
      else if (evt.getActionCommand().equals(Wizard.BACK_BUTTON_ACTION_COMMAND))
        backButtonPressed();
      else if (evt.getActionCommand().equals(Wizard.NEXT_BUTTON_ACTION_COMMAND))
        nextButtonPressed();
    }
    
    
    
    private void cancelButtonPressed() {
        
        wizard.close(Wizard.CANCEL_RETURN_CODE);
    }

    private void nextButtonPressed() {
 
        WizardModel model = wizard.getModel();
        WizardPanelDescriptor descriptor = model.getCurrentPanelDescriptor();

        Object nextPanelDescriptor = descriptor.getNextPanelDescriptor();
//         if(descriptor.getPanelDescriptorIdentifier().equals(LoginPanelDescriptor.IDENTIFIER)) {
//           final LoginPanel panel = (LoginPanel)descriptor.getPanelComponent();
//           final ProgressLoginPanelDescriptor thisDesc =
//             (ProgressLoginPanelDescriptor)model
//             .getPanelDescriptor(nextPanelDescriptor);

//           final SwingWorker worker = new SwingWorker() {
//               public Object construct() {
//                 try {
//                   boolean workOffline = (Boolean)UserSelections.getInstance()
//                     .getProperty("WORK_OFFLINE");
                  
//                   ProgressEvent evt = null;

//                   if(!workOffline) {
//                     evt = new ProgressEvent();
//                     evt.setMessage("Sending credentials...");
//                     thisDesc.newProgressEvent(evt);
                    
//                     LoginContext lc = new LoginContext("UML_Loader", panel);    
//                     lc.login();
//                     username = panel.getUsername();
//                     panel.setErrorMessage("");
//                   } else {
//                     try {
//                       Thread.currentThread().sleep(100);
//                     } catch (Exception e){
//                     } // end of try-catch
//                   }
                  
//                   evt = new ProgressEvent();
//                   evt.setGoal(100);
//                   evt.setStatus(100);
//                   evt.setMessage("Done");
//                   thisDesc.newProgressEvent(evt);
//                 } catch (Exception e){
//                   ProgressEvent evt = new ProgressEvent();
//                   evt.setStatus(-1);
//                   evt.setGoal(-1);
//                   evt.setMessage("Failed");
//                   thisDesc.newProgressEvent(evt);

//                   username = null;
//                   panel.setErrorMessage("Login / Password incorrect");
//                 } // end of try-catch
//                 return null;
//               }
//             };
//           worker.start(); 
//         }

        if(descriptor.getPanelDescriptorIdentifier().equals(ModeSelectionPanelDescriptor.IDENTIFIER)) {
          ModeSelectionPanel panel = 
            (ModeSelectionPanel)descriptor.getPanelComponent();
          
          
          mode = Enum.valueOf(RunMode.class, panel.getSelection());

          userSelections.setProperty("MODE", mode);

          prefs.setModeSelection(mode.toString());

          FileSelectionPanelDescriptor fileDesc =
            (FileSelectionPanelDescriptor)model
            .getPanelDescriptor(FileSelectionPanelDescriptor.IDENTIFIER);
          fileDesc.init();

          ProgressFileSelectionPanelDescriptor desc =
            (ProgressFileSelectionPanelDescriptor)model
            .getPanelDescriptor(ProgressFileSelectionPanelDescriptor.IDENTIFIER);

          if(mode.equals(RunMode.GenerateReport)) {
            desc.setNextPanelDescriptor(ReportConfirmPanelDescriptor.IDENTIFIER);
          } else if(mode.equals(RunMode.Reviewer)) {
            desc.setNextPanelDescriptor("FINISH");
          } else if(mode.equals(RunMode.Curator)) {
            desc.setNextPanelDescriptor("FINISH");
          }

        }

        if(descriptor.getPanelDescriptorIdentifier().equals(FileSelectionPanelDescriptor.IDENTIFIER)) {
          FileSelectionPanel panel = 
            (FileSelectionPanel)descriptor.getPanelComponent();
          filename = panel.getSelection();

          userSelections.setProperty("FILENAME", filename);

          ReportConfirmPanelDescriptor reportDesc =
            (ReportConfirmPanelDescriptor)model
            .getPanelDescriptor(ReportConfirmPanelDescriptor.IDENTIFIER);
          final ReportConfirmPanel reportPanel = 
            (ReportConfirmPanel)reportDesc.getPanelComponent();
          

          final ProgressFileSelectionPanelDescriptor progressDesc =
            (ProgressFileSelectionPanelDescriptor)model
            .getPanelDescriptor(ProgressFileSelectionPanelDescriptor.IDENTIFIER);


          if(mode.equals(RunMode.GenerateReport)) {
            File csvFile = new File(SemanticConnectorUtil.getCsvFilename(filename));
            boolean doIt = true;
            if(csvFile.exists()) {
              int result = JOptionPane
                .showConfirmDialog
                (null,
                 "<html>A report already exists for this file, if you choose to continue, <br>the existing report will be lost. <br><br>Do you want to continue?", 
                 "Continue?", JOptionPane.YES_NO_OPTION);
              
              if(result == JOptionPane.YES_OPTION) {
                csvFile.delete();
              } else {
                doIt = false;
                reportPanel.setFiles(null, "User cancelled Semantic Annotation");
              }
            }

            if(doIt) {
              SwingWorker worker = new SwingWorker() {
                  public Object construct() {
                    try {
                      ProgressEvent evt = new ProgressEvent();
                      evt.setMessage("Creating Annotation Report ...");
                      progressDesc.newProgressEvent(evt);
                      
                      outputCsv = SemanticConnectorUtil.generateReport(filename);
                      reportPanel.setFiles(filename, outputCsv);
                      evt = new ProgressEvent();
                      evt.setGoal(100);
                      evt.setStatus(100);
                      evt.setMessage("Done");
                      progressDesc.newProgressEvent(evt);

                    } catch (SemanticConnectorException e){
                      reportPanel.setFiles(null, "An error occured.");
                    } // end of try-catch
                    return null;
                  }
                };
              worker.start(); 
            }
          }
          else if(mode.equals(RunMode.Curator)) {
            SwingWorker worker = new SwingWorker() {
                public Object construct() {
                  try {
                    Parser parser = new CsvParser();
                    ElementsLists elements = ElementsLists.getInstance();
                    UMLHandler listener = new UMLDefaultHandler(elements);
                    parser.setEventHandler(listener);
                    parser.addProgressListener(progressDesc);
                    
//                     UMLDefaults defaults = UMLDefaults.getInstance();
//                     defaults.initParams(filename);
                    
                    parser.parse(filename);
                    
                    Validator validator = new UMLValidator(elements);
                    validator.validate();
                    
                    TreeBuilder tb = TreeBuilder.getInstance();
                    tb.init();
                    tb.buildTree(elements);

                    wizard.close(Wizard.FINISH_RETURN_CODE);
                    
                    return null;
                  } catch (Exception e){
                    e.printStackTrace();
                    logger.error(e);
                    return null;
                  } // end of try-catch
                }
              };
            worker.start(); 
          }
          else if(mode.equals(RunMode.Reviewer)) {
            SwingWorker worker = new SwingWorker() {
                public Object construct() {
                  try {
                    XMIParser  parser = new XMIParser();
                    ElementsLists elements = ElementsLists.getInstance();
                    UMLHandler listener = new UMLDefaultHandler(elements);
                    parser.setEventHandler(listener);
                    parser.addProgressListener(progressDesc);
                    UMLDefaults defaults = UMLDefaults.getInstance();
                    defaults.initParams(filename);
                    
                    parser.parse(filename);
                    
                    Validator validator = new UMLValidator(elements);
                    validator.validate();
                    
                    TreeBuilder tb = TreeBuilder.getInstance();
                    tb.init();
                    tb.buildTree(elements);

                    wizard.close(Wizard.FINISH_RETURN_CODE);
                    
                    return null;
                  } catch (Exception e){
                    logger.error(e);
                    return null;
                  } // end of try-catch
                }
              };
            worker.start(); 
            
          }
        }
        
        if (nextPanelDescriptor instanceof WizardPanelDescriptor.FinishIdentifier) {
            wizard.close(Wizard.FINISH_RETURN_CODE);
        } else {        
            wizard.setCurrentPanel(nextPanelDescriptor);
        }

    }

    private void backButtonPressed() {
 
        WizardModel model = wizard.getModel();
        WizardPanelDescriptor descriptor = model.getCurrentPanelDescriptor();
 
        //  Get the descriptor that the current panel identifies as the previous
        //  panel, and display it.
        
        Object backPanelDescriptor = descriptor.getBackPanelDescriptor();        
        wizard.setCurrentPanel(backPanelDescriptor);
        
    }

    
    void resetButtonsToPanelRules() {
    
        //  Reset the buttons to support the original panel rules,
        //  including whether the next or back buttons are enabled or
        //  disabled, or if the panel is finishable.
        
        WizardModel model = wizard.getModel();
        WizardPanelDescriptor descriptor = model.getCurrentPanelDescriptor();
        
        //  If the panel in question has another panel behind it, enable
        //  the back button. Otherwise, disable it.
        
        model.setBackButtonText(Wizard.DEFAULT_BACK_BUTTON_TEXT);
        
        if (descriptor.getBackPanelDescriptor() != null)
            model.setBackButtonEnabled(Boolean.TRUE);
        else
            model.setBackButtonEnabled(Boolean.FALSE);

        //  If the panel in question has one or more panels in front of it,
        //  enable the next button. Otherwise, disable it.
 
        if (descriptor.getNextPanelDescriptor() != null)
            model.setNextButtonEnabled(Boolean.TRUE);
        else
            model.setNextButtonEnabled(Boolean.FALSE);
 
        //  If the panel in question is the last panel in the series, change
        //  the Next button to Finish and enable it. Otherwise, set the text
        //  back to Next.
        
        if (descriptor.getNextPanelDescriptor() instanceof WizardPanelDescriptor.FinishIdentifier) {
            model.setNextButtonText(Wizard.DEFAULT_FINISH_BUTTON_TEXT);
            model.setNextButtonEnabled(Boolean.TRUE);
        } else
            model.setNextButtonText(Wizard.DEFAULT_NEXT_BUTTON_TEXT);
        
    }
    
  private void putToCenter(Component comp) {
    comp.setLocation((screenSize.width - comp.getSize().width) / 2, (screenSize.height - comp.getSize().height) / 2);
  }
  
}
