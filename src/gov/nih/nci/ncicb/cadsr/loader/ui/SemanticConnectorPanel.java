package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

import java.io.File;

public class SemanticConnectorPanel extends JPanel 
{

  private JRadioButton noButton, yesButton;
  private JPanel _this = this;

  public SemanticConnectorPanel()
  {
    initUI();
  }

  public boolean getSelection() {
    return yesButton.isSelected();
  }

  private void initUI() {

    JPanel textPanel = new JPanel();
    textPanel.setLayout(new FlowLayout());
    
    textPanel.add(new JLabel(new ImageIcon(this.getClass().getResource("/new-work.gif"))));

    JLabel textLabel = new JLabel("Do you want to run Semantic Connector on the file you selected?");

    textPanel.add(textLabel);

    ButtonGroup group = new ButtonGroup();
    
    noButton = new JRadioButton("No, I have already run Semantic Connector");
    yesButton = new JRadioButton("Yes, I need to run Semantic Connector");

    yesButton.setSelected(true);

    group.add(yesButton);
    group.add(noButton);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(2, 1));
    buttonPanel.add(yesButton);
    buttonPanel.add(noButton);

    this.setLayout(new BorderLayout());
    this.add(textPanel, BorderLayout.NORTH);
    this.add(buttonPanel, BorderLayout.CENTER);

  }

}