package gov.nih.nci.ncicb.cadsr.jaas;

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
public class SwingCallbackHandler extends JDialog
  implements CallbackHandler {

  private boolean done = false;

  private JTextField userField;
  private JPasswordField pwdField;

  private JButton okButton, cancelButton;
  
  public SwingCallbackHandler() {
    super((JFrame)null, "Login Dialog");
    initUI();
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

  public boolean isDone() {
    return done;
  }

  private void initUI() {
    this.setSize(250, 120);

    JLabel userLabel = new JLabel("Username: "), 
      pwdLabel = new JLabel("Password");
    
    userField = new JTextField(12);
    pwdField = new JPasswordField();

    okButton = new JButton("Login");
    cancelButton = new JButton("Quit");

    KeyListener enterListener = new KeyAdapter() {
        public void keyReleased(KeyEvent evt) {
          if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JButton button = (JButton)evt.getComponent();
            button.doClick();
          }
        }
      };
    okButton.addKeyListener(enterListener);
    cancelButton.addKeyListener(enterListener);

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          done = true;
          dispose();
          if(evt.getSource() == cancelButton)
            System.exit(0);
        }
      };
    
        
    okButton.addActionListener(actionListener);
    cancelButton.addActionListener(actionListener);

    Container contentPane = this.getContentPane();
    contentPane.setLayout(new BorderLayout());

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    buttonPanel.add(okButton, null);
    buttonPanel.add(cancelButton);

    JPanel entryPanel = new JPanel();
    entryPanel.setLayout(new GridLayout(2, 2));
    entryPanel.add(userLabel);
    entryPanel.add(userField);
    entryPanel.add(pwdLabel);
    entryPanel.add(pwdField);

    contentPane.add(buttonPanel, BorderLayout.SOUTH);
    contentPane.add(entryPanel, BorderLayout.CENTER);
    

  }

}
