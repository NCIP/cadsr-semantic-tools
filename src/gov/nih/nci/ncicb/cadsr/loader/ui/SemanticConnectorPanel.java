package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

import java.io.File;

import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;
import gov.nih.nci.ncicb.cadsr.loader.event.*;

public class SemanticConnectorPanel extends JPanel 
  implements ProgressListener {

  private JRadioButton optionButton1, optionButton2;
  private JPanel _this = this;
  private ProgressPanel progressPanel;
  private boolean isProgress = false;
  private int goal;

  public static final int MODE_INITIAL = 1;
  public static final int MODE_SUBSEQUENT = 2;

  private int mode = 0;

  private String[][] options = 
  {{"error", "error", "error"}, 
   {"<html>It appears there is no Sematic Connector <br>annotation file for the file you selected.<br> Do you want to: </html>",
    "Extract Annotations from the XMI file?",
    "Run Semantic Connector to create an annotation file?"},
   {"<html>The is a Semantic Connector annotation file<br> Do you want to run Semantic Connector?<html>", 
    "No", 
    "Yes"}};
  
  public SemanticConnectorPanel()
  {
//     initUI();
  }
  public SemanticConnectorPanel(int progressGoal)
  {
    this.isProgress = true;
    this.goal = progressGoal;
//     initUI();
  }

  public void newProgressEvent(ProgressEvent evt) {
    progressPanel.newProgressEvent(evt);
  }

  public void setMode(int mode) {
    this.mode = mode;
    initUI();
  }

  public int getSelection() {
    return optionButton2.isSelected()?2:1;
  }

  private void initUI() {

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    
    infoPanel.setBackground(Color.WHITE);
    infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    infoPanel.add(new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("xmi.gif"))), FlowLayout.LEFT);
    
    JLabel textLabel = new JLabel(options[mode][0]);

    infoPanel.add(textLabel);

    ButtonGroup group = new ButtonGroup();
    
    optionButton1 = new JRadioButton(options[mode][1]);
    optionButton2 = new JRadioButton(options[mode][2]);

    optionButton1.setSelected(true);

    group.add(optionButton1);
    group.add(optionButton2);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(0, 1));

    buttonPanel.add
      (new JLabel("Semantic Connector will tag the XMI document with concepts."));

    buttonPanel.add(optionButton1);
    buttonPanel.add(optionButton2);

    progressPanel = new ProgressPanel(goal);

    if(isProgress) {
      progressPanel.setVisible(true);
      optionButton1.setEnabled(false);
      optionButton2.setEnabled(false);
    } else {
      progressPanel.setVisible(false);
    }

    this.setLayout(new BorderLayout());
    this.add(infoPanel, BorderLayout.NORTH);
    this.add(buttonPanel, BorderLayout.CENTER);
    this.add(progressPanel, BorderLayout.SOUTH);

  }

}