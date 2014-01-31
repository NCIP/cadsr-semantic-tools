package gov.nih.nci.ncicb.cadsr.loader.util;

import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.Rectangle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * Same as the ant replace task, but pops up a secret dialog.
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class AntSecretReplace
  extends JDialog
  implements ActionListener {

  private JLabel passwordLabel = new JLabel();
  private JPasswordField passwordField = new JPasswordField();
  private JButton okButton = new JButton();
  private JButton cancelButton = new JButton();

  private JDialog _this = this;
  
  private File inputFile, outputFile;
  private String expression;
  
  public AntSecretReplace(File inputFile, File outputFile, String expression)
  {
    super((Frame)null, true);
    
    this.inputFile = inputFile;
    this.outputFile = outputFile;
    this.expression = expression;
    
    try
      {
        jbInit();
        UIUtil.putToCenter(this);
        this.setVisible(true);
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
  }
  
  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(null);
    this.setSize(new Dimension(299, 201));
    passwordLabel.setText("Password");
    passwordLabel.setBounds(new Rectangle(25, 50, 150, 30));
    passwordField.setBounds(new Rectangle(130, 50, 135, 30));
    okButton.setText("OK");
    okButton.setBounds(new Rectangle(135, 135, 55, 20));
    okButton.setActionCommand("OK");
    okButton.addActionListener(this);
    cancelButton.setText("Cancel");
    cancelButton.setBounds(new Rectangle(200, 135, 75, 20));
    cancelButton.setActionCommand("Cancel");
    cancelButton.addActionListener(this);
    this.getContentPane().add(cancelButton, null);
    this.getContentPane().add(okButton, null);
    this.getContentPane().add(passwordField, null);
    this.getContentPane().add(passwordLabel, null);

    this.getRootPane().setDefaultButton(okButton);
  }

  public void actionPerformed(ActionEvent evt) {
    if(evt.getActionCommand().equals("OK")) {
      
	String line;
	StringBuffer sb = new StringBuffer();
	try {
          FileInputStream fis = new FileInputStream(inputFile);
          BufferedReader reader = new BufferedReader (new InputStreamReader(fis));
          while((line = reader.readLine()) != null) {
            line = line.replaceAll(expression, new String(passwordField.getPassword()));
            sb.append(line+"\n");
          }
          reader.close();
          BufferedWriter out = new BufferedWriter (new FileWriter(outputFile));
          out.write(sb.toString());
          out.close();
	}
	catch (Throwable e) {
          throw new RuntimeException(e);
	}
    } else if(evt.getActionCommand().equals("Cancel")) {
    }
    
    _this.dispose();

  }

  /**
   * 1. input file
   * 2. output file
   * 3. string to replace.
   **/
  public static void main(String[] args) {
    
    AntSecretReplace dialog = new AntSecretReplace(new File(args[0]), new File(args[1]), args[2]);


  }
  
}
