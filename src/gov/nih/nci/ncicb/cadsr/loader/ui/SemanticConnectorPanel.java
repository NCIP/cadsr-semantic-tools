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

  private JRadioButton noButton, yesButton;
  private JPanel _this = this;
  private ProgressPanel progressPanel;
  private boolean isProgress = false;
  private int goal;
  
  public SemanticConnectorPanel()
  {
    initUI();
  }
  public SemanticConnectorPanel(int progressGoal)
  {
    this.isProgress = true;
    this.goal = progressGoal;
    initUI();
  }

  public void newProgressEvent(ProgressEvent evt) {
    progressPanel.newProgressEvent(evt);
  }

  public boolean getSelection() {
    return yesButton.isSelected();
  }

  private void initUI() {

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    
    infoPanel.setBackground(Color.WHITE);
    infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    infoPanel.add(new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("xmi.gif"))), FlowLayout.LEFT);
    
    JLabel textLabel = new JLabel("<html>Do you want to run Semantic Connector <br> on the file you selected?</html>");

    infoPanel.add(textLabel);

    ButtonGroup group = new ButtonGroup();
    
    noButton = new JRadioButton("No, I have already run Semantic Connector");
    yesButton = new JRadioButton("Yes, I need to run Semantic Connector");

    yesButton.setSelected(true);

    group.add(yesButton);
    group.add(noButton);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(0, 1));

    buttonPanel.add
      (new JLabel("Semantic Connector will tag the XMI document with concepts."));

    buttonPanel.add(yesButton);
    buttonPanel.add(noButton);

    progressPanel = new ProgressPanel(goal);

    if(isProgress) {
      progressPanel.setVisible(true);
      yesButton.setEnabled(false);
      noButton.setEnabled(false);
    } else {
      progressPanel.setVisible(false);
    }

    this.setLayout(new BorderLayout());
    this.add(infoPanel, BorderLayout.NORTH);
    this.add(buttonPanel, BorderLayout.CENTER);
    this.add(progressPanel, BorderLayout.SOUTH);

  }

}