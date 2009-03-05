package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.loader.util.*;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.UMLNode;

import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;


import java.awt.GridBagLayout;

import javax.swing.*;
import javax.swing.JLabel;

import java.util.*;

public class GMEViewPanel extends JPanel implements UserPreferencesListener {

  private JLabel gmeNamespaceLabel = new JLabel("GME Namespace"),
    gmeElementLabel = new JLabel("GME XML Element"),
    gmeLocRefLabel = new JLabel("GME XML Location Reference"),
    gmeSourceLocRefLabel = new JLabel("GME Source XML Location Reference"),
    gmeTargetLocRefLabel = new JLabel("GME Target XML Location Reference");
  
  private JTextField gmeNamespaceField = new JTextField(),
    gmeElementField = new JTextField(),
    gmeLocRefField = new JTextField(),
    gmeSourceLocRefField = new JTextField(),
    gmeTargetLocRefField = new JTextField();
  
  private JScrollPane scrollPane;

  private UMLNode node;

  public GMEViewPanel(UMLNode node) {
    this.node = node;
    
    initUI();
    updateFields();

    UserPreferences.getInstance().addUserPreferencesListener(this);
  }
  
  public GMEViewPanel() {
    initUI();
  }
  
  public void updateNode(UMLNode node) {
    this.node = node;
    
    updateFields();
  }

  private void initUI() {
    this.setLayout(new GridBagLayout());

    UIUtil.insertInBag(this, gmeNamespaceLabel, 0, 0);
    UIUtil.insertInBag(this, gmeNamespaceField, 1, 0);
    UIUtil.insertInBag(this, gmeElementLabel, 0, 1);
    UIUtil.insertInBag(this, gmeElementField, 1, 1);
    UIUtil.insertInBag(this, gmeLocRefLabel, 0, 2);
    UIUtil.insertInBag(this, gmeLocRefField, 1, 2);
    UIUtil.insertInBag(this, gmeSourceLocRefLabel, 0, 3);
    UIUtil.insertInBag(this, gmeSourceLocRefField, 1, 3);
    UIUtil.insertInBag(this, gmeTargetLocRefLabel, 0, 4);
    UIUtil.insertInBag(this, gmeTargetLocRefField, 1, 4);

    gmeNamespaceField.setEnabled(false);
    gmeElementField.setEnabled(false);
    gmeLocRefField.setEnabled(false);
    gmeSourceLocRefField.setEnabled(false);
    gmeTargetLocRefField.setEnabled(false);

  }

  private void updateFields() {
    gmeNamespaceField.setText("");
    gmeElementField.setText("");
    gmeLocRefField.setText("");
    gmeSourceLocRefField.setText("");
    gmeTargetLocRefField.setText("");


    List<AlternateName> altNames = new ArrayList<AlternateName>();
    if(node.getUserObject() instanceof AdminComponent) {
      AdminComponent ac = (AdminComponent)node.getUserObject();
      altNames = ac.getAlternateNames();
    } else if(node.getUserObject() instanceof ClassificationSchemeItem) {
      ClassificationSchemeItem csi = (ClassificationSchemeItem)node.getUserObject();
      altNames = csi.getAlternateNames();
    }
    if(altNames != null) {
      for(AlternateName altName : altNames) {
        if(altName.getType().equals(AlternateName.TYPE_GME_NAMESPACE)) {
          gmeNamespaceField.setText(altName.getName());
        }
        else if(altName.getType().equals(AlternateName.TYPE_GME_XML_ELEMENT)) {
          gmeElementField.setText(altName.getName());
        }
        else if(altName.getType().equals(AlternateName.TYPE_GME_XML_LOC_REF)) {
          gmeLocRefField.setText(altName.getName());
        }
        else if(altName.getType().equals(AlternateName.TYPE_GME_SRC_XML_LOC_REF)) {
          gmeSourceLocRefField.setText(altName.getName());
        }
        else if(altName.getType().equals(AlternateName.TYPE_GME_TARGET_XML_LOC_REF)) {
          gmeTargetLocRefField.setText(altName.getName());
        }
      }
    }

    if(UserPreferences.getInstance().getShowGMETags()) {
      gmeNamespaceLabel.setVisible(!StringUtil.isEmpty(gmeNamespaceField.getText()));
      gmeElementLabel.setVisible(!StringUtil.isEmpty(gmeElementField.getText()));
      gmeLocRefLabel.setVisible(!StringUtil.isEmpty(gmeLocRefField.getText()));
      gmeSourceLocRefLabel.setVisible(!StringUtil.isEmpty(gmeSourceLocRefField.getText()));
      gmeTargetLocRefLabel.setVisible(!StringUtil.isEmpty(gmeTargetLocRefField.getText()));
      
      gmeNamespaceField.setVisible(!StringUtil.isEmpty(gmeNamespaceField.getText()));
      gmeElementField.setVisible(!StringUtil.isEmpty(gmeElementField.getText()));
      gmeLocRefField.setVisible(!StringUtil.isEmpty(gmeLocRefField.getText()));
      gmeSourceLocRefField.setVisible(!StringUtil.isEmpty(gmeSourceLocRefField.getText()));
      gmeTargetLocRefField.setVisible(!StringUtil.isEmpty(gmeTargetLocRefField.getText()));
    } else {
      gmeNamespaceLabel.setVisible(false);
      gmeElementLabel.setVisible(false);
      gmeLocRefLabel.setVisible(false);
      gmeSourceLocRefLabel.setVisible(false);
      gmeTargetLocRefLabel.setVisible(false);
      
      gmeNamespaceField.setVisible(false);
      gmeElementField.setVisible(false);
      gmeLocRefField.setVisible(false);
      gmeSourceLocRefField.setVisible(false);
      gmeTargetLocRefField.setVisible(false);
    }
      
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setSize(500, 500);
    
    frame.add(new GMEViewPanel());
    
    frame.setVisible(true);
  }

  public void preferenceChange(UserPreferencesEvent event) 
  {
    updateFields();
  }

}
