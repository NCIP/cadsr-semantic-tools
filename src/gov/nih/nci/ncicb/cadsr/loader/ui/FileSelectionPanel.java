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
    infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    infoPanel.setBackground(Color.WHITE);
    infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    infoPanel.add(new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("xmi.gif"))));

    JLabel infoLabel = new JLabel("<html>Please choose a file to parse<br>The file must be in XMI format</html>");
    infoPanel.add(infoLabel);
    
    this.add(infoPanel, BorderLayout.NORTH);
    
    browseButton = new JButton("Browse");
    filePathField = new JTextField(30);
    filePathField.setText("/home/ludetc/dev/umlloader-gui/2.0.0/data/gene_fixed.xmi");
    JPanel browsePanel = new JPanel();

    browsePanel.setLayout(new GridBagLayout());

    insertInBag(browsePanel, 
                new JLabel("Click browse to search for an XMI file"), 0, 0, 2, 1);

    insertInBag(browsePanel, filePathField, 0, 1);
    insertInBag(browsePanel, browseButton, 1, 1);

//     browsePanel.add(filePathField);
//     browsePanel.add(browseButton);
    
    browsePanel.setPreferredSize(new Dimension(400, 200));

    this.add(browsePanel, BorderLayout.CENTER);

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

  private void insertInBag(JPanel bagComp, Component comp, int x, int y) {

    insertInBag(bagComp, comp, x, y, 1, 1);

  }

  private void insertInBag(JPanel bagComp, Component comp, int x, int y, int width, int height) {
    JPanel p = new JPanel();
    p.add(comp);

    bagComp.add(p, new GridBagConstraints(x, y, width, height, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }


}