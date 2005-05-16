package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

import java.io.File;

import gov.nih.nci.ncicb.cadsr.loader.util.*;
import gov.nih.nci.ncicb.cadsr.loader.event.*;

public class FileSelectionPanel extends JPanel 
implements ProgressListener {

  public static final int SELECTION_NEW = 1;
  public static final int SELECTION_CONTINUE = 2;
  public static final int SELECTION_CANCEL = -1;

  private JButton newButton, continueButton, browseButton;
  private JLabel newLabel, continueLabel;

  private JTextField filePathField;

  private JPanel _this = this;

  private ProgressPanel progressPanel;
  private boolean isProgress = false;
  private int goal;

  public FileSelectionPanel()
  {
    initUI();
  }

  public FileSelectionPanel(int progressGoal)
  {
    this.isProgress = true;
    this.goal = progressGoal;
    initUI();
  }

  public void newProgressEvent(ProgressEvent evt) {
    progressPanel.newProgressEvent(evt);
  }

  public String getSelection() {
    String s = filePathField.getText().replace('\\', '/');
    return s;
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

    JPanel browsePanel = new JPanel();

    browsePanel.setLayout(new GridBagLayout());

    insertInBag(browsePanel, 
                new JLabel("Click browse to search for an XMI file"), 0, 0, 2, 1);

    insertInBag(browsePanel, filePathField, 0, 1);
    insertInBag(browsePanel, browseButton, 1, 1);

    insertInBag(browsePanel, 
                new JLabel("Recent Files:"), 0, 2, 2, 1);

    final UserPreferences prefs = BeansAccessor.getUserPreferences();

    java.util.List<String> recentFiles = prefs.getRecentFiles();
    
    int y = 3;
    for(String s : recentFiles) {
      final JLabel jl = new JLabel(s);
      
      Font f = jl.getFont();
      Font newFont = new Font(f.getName(), f.getStyle(), f.getSize() - 2);
      jl.setFont(newFont);

      jl.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent evt) {
            filePathField.setText(jl.getText());
          }
        });


      insertInBag(browsePanel, jl, 0, y++, 2, 1);
    }

    browsePanel.setPreferredSize(new Dimension(400, 250));

    this.add(browsePanel, BorderLayout.CENTER);

    browseButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          String xmiDir = BeansAccessor.getUserPreferences().getXmiDir();
          JFileChooser chooser = new JFileChooser(xmiDir);
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
            String filePath = chooser.getSelectedFile().getAbsolutePath();
            filePathField.setText(filePath);
            prefs.setXmiDir(filePath);
            prefs.addRecentFile(filePath);
          }
        }
      });

    progressPanel = new ProgressPanel(goal);
    
    if(isProgress) {
      progressPanel.setVisible(true);
      browseButton.setEnabled(false);
      filePathField.setEnabled(false);
    } else {
      progressPanel.setVisible(false);
    }

    this.add(progressPanel, BorderLayout.SOUTH);

  }

  private void insertInBag(JPanel bagComp, Component comp, int x, int y) {

    insertInBag(bagComp, comp, x, y, 1, 1);

  }

  private void insertInBag(JPanel bagComp, Component comp, int x, int y, int width, int height) {
    JPanel p = new JPanel();
    p.add(comp);

    bagComp.add(p, new GridBagConstraints(x, y, width, height, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }


}