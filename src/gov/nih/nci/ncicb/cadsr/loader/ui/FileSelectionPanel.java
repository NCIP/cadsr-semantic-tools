package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

import java.io.File;

public class FileSelectionPanel extends JPanel 
{

  public static final int SELECTION_NEW = 1;
  public static final int SELECTION_CONTINUE = 2;
  public static final int SELECTION_CANCEL = -1;

  private JButton newButton, continueButton, browseButton;
  private JLabel newLabel, continueLabel;

  private JTextField filePathField;

  private JPanel _this = this;

  public FileSelectionPanel()
  {
    initUI();
  }

  public String getSelection() {
    return filePathField.getText();
  }

  public void addFileActionListener(ActionListener l) {
    browseButton.addActionListener(l);
  }

  private void initUI() {

    this.setLayout(new BorderLayout());
    
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new GridLayout(0, 1));
    
    JLabel infoLabel = new JLabel("Please choose a file to parse");
    infoPanel.add(infoLabel);
    infoLabel = new JLabel("The file must be in XMI format");
    infoPanel.add(infoLabel);
    
    this.add(infoPanel, BorderLayout.NORTH);
    
    browseButton = new JButton("Browse");
    filePathField = new JTextField(30);
    filePathField.setText("/home/ludetc/dev/umlclassdiagramloader/2.0.0/data/gene_fixed.xmi");
    JPanel browsePanel = new JPanel();
    browsePanel.setLayout(new FlowLayout());
    browsePanel.add(filePathField);
    browsePanel.add(browseButton);
    
    this.add(browsePanel, BorderLayout.CENTER);

    browseButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {

          new ProgressFrame(-1);
          
          try {
            Thread.currentThread().sleep(10000);
          } catch (Exception e){
          } // end of try-catch
        }
      });

    
    browseButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          JFileChooser chooser = new JFileChooser();
          javax.swing.filechooser.FileFilter filter = 
            new javax.swing.filechooser.FileFilter() {
              public boolean accept(File f) {
                if (f.isDirectory()) {
                  return true;
                }                
                return f.getName().endsWith(".xmi");
              }
              public String getDescription() {
                return "XMI Files";
              }
            };
          
          chooser.setFileFilter(filter);
          int returnVal = chooser.showOpenDialog(null);
          if(returnVal == JFileChooser.APPROVE_OPTION) {
            filePathField.setText(chooser.getSelectedFile().getAbsolutePath());
          }
        }
      });
  }

}