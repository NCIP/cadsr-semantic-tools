package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

import java.io.File;

import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;
import gov.nih.nci.ncicb.cadsr.loader.event.*;

public class ValidationPanel extends JPanel {

  private JCheckBox validateCheckBox;
  private JPanel _this = this;
  
  public ValidationPanel()
  {
    initUI();
  }

  public boolean getSelection() {
    return validateCheckBox.isSelected();
  }

  private void initUI() {

    JPanel textPanel = new JPanel();
    textPanel.setLayout(new FlowLayout());
    
    textPanel.add(new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("validated.jpg"))));

    JLabel textLabel = new JLabel("You may choose to run Validation now, or manually run validation later");

    textPanel.add(textLabel);

    validateCheckBox = new JCheckBox("Yes, Please run Validation now");
    validateCheckBox.setSelected(true);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(1, 1));
    buttonPanel.add(validateCheckBox);

    this.setLayout(new BorderLayout());
    this.add(textPanel, BorderLayout.NORTH);
    this.add(buttonPanel, BorderLayout.CENTER);

  }

}