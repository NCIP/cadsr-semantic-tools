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

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    
    infoPanel.setBackground(Color.WHITE);
    infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    infoPanel.add(new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("xmi.gif"))), FlowLayout.LEFT);
//     infoPanel.add(new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("validated.jpg"))));

    JLabel textLabel = new JLabel("<html>You may choose to run Validation now, <br> or manually run validation later</html>");

    infoPanel.add(textLabel);

    

    validateCheckBox = new JCheckBox("Yes, Please run Validation now");
    validateCheckBox.setSelected(true);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(0, 1));
    buttonPanel.add
      (new JLabel("<html>Validation will help you identify the items that have missing concepts <br> If this is the first you will be tagging the xmi document, you may want to leave this checked off</html>"));
                    
    buttonPanel.add(validateCheckBox);

    this.setLayout(new BorderLayout());
    this.add(infoPanel, BorderLayout.NORTH);
    this.add(buttonPanel, BorderLayout.CENTER);

  }

}