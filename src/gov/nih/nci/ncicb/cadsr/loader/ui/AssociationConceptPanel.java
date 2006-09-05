package gov.nih.nci.ncicb.cadsr.loader.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;

import gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 * Association Concept Editor Panel
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class AssociationConceptPanel extends JPanel
 implements Editable {

  private ObjectClassRelationship ocr;
  private UMLNode node;

  private int type;

  private ButtonPanel buttonPanel;
  private ConceptEditorPanel conceptEditorPanel;

  public AssociationConceptPanel(UMLNode node) {
    this.node = node;
    this.ocr = (ObjectClassRelationship)node.getUserObject();

    this.type = type;
    
    initUI();
  }

  public void applyPressed() {
    try {
      conceptEditorPanel.applyPressed();
    } catch (ApplyException e) {
      
    }
  }


  private void initUI() {
    conceptEditorPanel = new ConceptEditorPanel(node);
    buttonPanel = new ButtonPanel(conceptEditorPanel, this, null);

    conceptEditorPanel.initUI();

    JPanel buttonSpacedPanel = new JPanel();
    buttonSpacedPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
    JLabel spaceLabel = new JLabel("      ");
    buttonSpacedPanel.add(spaceLabel);
    buttonSpacedPanel.add(buttonPanel);

    setLayout(new BorderLayout());

    this.add(conceptEditorPanel, BorderLayout.CENTER);
    this.add(buttonSpacedPanel, BorderLayout.SOUTH);

    conceptEditorPanel.addPropertyChangeListener(buttonPanel);

  }

  public void addPropertyChangeListener(PropertyChangeListener l) {
    conceptEditorPanel.addPropertyChangeListener(l);
    buttonPanel.addPropertyChangeListener(l);
  }

  public void updateNode(UMLNode node) {

    conceptEditorPanel.updateNode(node);

    buttonPanel.propertyChange
      (new PropertyChangeEvent(this, ButtonPanel.SETUP, null, true));
    
    buttonPanel.update();

  }
  

}

