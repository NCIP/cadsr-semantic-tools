package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.GridLayout;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;

import java.awt.event.*;

import javax.security.auth.callback.*;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

/**
 * Callback handler for Swing based apps
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class LoginPanel extends JPanel 
  implements CallbackHandler, ProgressListener
{

  private JLabel errorLabel;
  
  private JTextField userField;
  private JPasswordField pwdField;

  final static int GAP = 30;  

  private ProgressPanel progressPanel;
  private boolean isProgress = false;
  private int goal;

  public LoginPanel() {
    initUI();
  
  }
  public LoginPanel(int progressGoal)
  {
    this.isProgress = true;
    this.goal = progressGoal;
    initUI();
  }

  public void newProgressEvent(ProgressEvent evt) {
    progressPanel.newProgressEvent(evt);
  }

  public String getUsername() {
    return userField.getName();
  }
  
  public void setErrorMessage(String msg) {
    errorLabel.setText(msg);
  }

  public void handle(Callback[] callbacks) 
    throws java.io.IOException, UnsupportedCallbackException {

    for (int i = 0; i < callbacks.length; i++) {
      if (callbacks[i] instanceof NameCallback) {
        String user=userField.getText();
        ((NameCallback)callbacks[i]).setName(user);
        
      } else if (callbacks[i] instanceof PasswordCallback) {
        ((PasswordCallback)callbacks[i]).setPassword(pwdField.getPassword());
      } else {
        throw(new UnsupportedCallbackException(
                callbacks[i], "Callback class not supported"));
      }
    }
  }
  
  private void initUI() {

    this.setLayout(new BorderLayout());

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new SpringLayout());
//     this.setLayout(new BorderLayout());
    
    JLabel userLabel = new JLabel("Username: ", JLabel.TRAILING), 
      pwdLabel = new JLabel("Password: ", JLabel.TRAILING);
      
    errorLabel = new JLabel("", JLabel.TRAILING);
    
    userField = new JTextField(12);
    pwdField = new JPasswordField(12);

    // TEMP
    userField.setText("LADINO");
    pwdField.setText("LADINO");

    userLabel.setLabelFor(userField);
    pwdLabel.setLabelFor(pwdField);

    progressPanel = new ProgressPanel(goal);

    if(isProgress) {
      progressPanel.setVisible(true);
      userField.setEnabled(false);
      pwdField.setEnabled(false);
    } else {
      progressPanel.setVisible(false);
    }
    
    ImageIcon icon = new ImageIcon(mainPanel.getClass().getResource("/security_lock.jpg"));
    
    mainPanel.add(new JLabel(icon));
    mainPanel.add(errorLabel);
    mainPanel.add(userLabel);
    mainPanel.add(userField);
    mainPanel.add(pwdLabel);
    mainPanel.add(pwdField);

    SpringUtilities.makeCompactGrid(mainPanel,
                                    3, 2,
                                    GAP, GAP, //init x,y
                                    GAP, GAP/2);//xpad, ypad

    this.add(mainPanel, BorderLayout.CENTER);
    this.add(progressPanel, BorderLayout.SOUTH);

  }

}
