package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.GridLayout;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;

import java.awt.event.*;

import javax.security.auth.callback.*;


/**
 * Callback handler for Swing based apps
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class LoginPanel extends JPanel 
  implements CallbackHandler {

  private JLabel errorLabel;
  
  private JTextField userField;
  private JPasswordField pwdField;

  final static int GAP = 30;  

  public LoginPanel() {
    initUI();
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
    this.setLayout(new SpringLayout());
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
    
    ImageIcon icon = new ImageIcon(this.getClass().getResource("/security_lock.jpg"));
    
    this.add(new JLabel(icon));
    this.add(errorLabel);
    this.add(userLabel);
    this.add(userField);
    this.add(pwdLabel);
    this.add(pwdField);

    SpringUtilities.makeCompactGrid(this,
                                    3, 2,
                                    GAP, GAP, //init x,y
                                    GAP, GAP/2);//xpad, ypad

  }

}
