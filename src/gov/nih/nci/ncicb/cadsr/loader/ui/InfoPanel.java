package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.loader.util.*;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.UMLNode;

import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;


import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.JLabel;

import java.util.*;

public class InfoPanel extends JPanel {

  private JPanel _this = this;
  private JLabel reportLabel = new JLabel("");

  public InfoPanel() {
    initUI();
  }


  public void setOutputText(String text) {
    reportLabel.setText("<html>" + text + "</html>");
  }

  private void initUI() {
    JPanel textPanel = new JPanel();
    textPanel.setLayout(new GridLayout(0, 1));
    textPanel.add(reportLabel);

    this.setLayout(new BorderLayout());
    this.add(textPanel, BorderLayout.CENTER);

  }

  
}
