package gov.nih.nci.ncicb.cadsr.loader.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;


public class AssociationViewPanel extends JScrollPane {
  
  private ObjectClassRelationship ocr;

  private JLabel[] labels = new JLabel[] {
    new JLabel("Direction"),
    new JLabel("Source Class"),
    new JLabel("Source Role"),
    new JLabel("Source Multiplicity"),
    new JLabel("Target Class"),
    new JLabel("Target Role"),
    new JLabel("Target Multiplicity")
  };

  private JLabel dirLbl = new JLabel(), 
    srcClassLbl = new JLabel(), 
    srcRoleLbl = new JLabel(), 
    srcMultLbl = new JLabel(), 
    tgtClassLbl = new JLabel(), 
    tgtRoleLbl = new JLabel(), 
    tgtMultLbl = new JLabel();

  public AssociationViewPanel(ObjectClassRelationship ocr) {
    this.ocr = ocr;

    initValues();
    initUI();
  }

  public void update(ObjectClassRelationship ocr) {
    this.ocr = ocr;
    initValues();
  }

  private void initUI() {
    JPanel scrollPanel = new JPanel(new BorderLayout());

    JPanel mainPanel = new JPanel(new GridBagLayout());
    
    insertInBag(mainPanel, labels[0], 0, 0);
    insertInBag(mainPanel, labels[1], 0, 1);
    insertInBag(mainPanel, labels[2], 0, 2);
    insertInBag(mainPanel, labels[3], 0, 3);
    insertInBag(mainPanel, labels[4], 0, 4);
    insertInBag(mainPanel, labels[5], 0, 5);
    insertInBag(mainPanel, labels[6], 0, 6);
    
    insertInBag(mainPanel, dirLbl, 1, 0);
    insertInBag(mainPanel, srcClassLbl, 1, 1);
    insertInBag(mainPanel, srcRoleLbl, 1, 2);
    insertInBag(mainPanel, srcMultLbl, 1, 3);
    insertInBag(mainPanel, tgtClassLbl, 1, 4);
    insertInBag(mainPanel, tgtRoleLbl, 1, 5);
    insertInBag(mainPanel, tgtMultLbl, 1, 6);
    
    scrollPanel.add(mainPanel, BorderLayout.CENTER);
    
    this.setViewportView(scrollPanel);

  }

  private void initValues() {
    dirLbl.setText(ocr.getDirection());
    srcClassLbl.setText(ocr.getSource().getLongName());
    srcRoleLbl.setText(ocr.getSourceRole());
    srcMultLbl.setText(ocr.getSourceLowCardinality() + ".." + ocr.getSourceHighCardinality());

    tgtClassLbl.setText(ocr.getTarget().getLongName());
    tgtRoleLbl.setText(ocr.getTargetRole());
    tgtMultLbl.setText(ocr.getTargetLowCardinality() + ".." + ocr.getTargetHighCardinality());
    
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setLayout(new BorderLayout());
    frame.setSize(500, 400);
    frame.show();
  }

  private void insertInBag(JPanel bagComp, Component comp, int x, int y) {
    JPanel p = new JPanel();
    p.add(comp);

    bagComp.add(p, new GridBagConstraints(x, y, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }

}

