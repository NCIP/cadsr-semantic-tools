package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.util.List;

import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.loader.event.ElementChangeEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ElementChangeListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.ClassNode;
import gov.nih.nci.ncicb.cadsr.loader.util.*;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.UMLNode;

import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ExcludeFromSemanticInheritancePanel extends JPanel implements Editable, DocumentListener, ItemListener  {

  private UMLNode node;

  private JTextArea reasonTextField = new JTextArea("");
  private JCheckBox excludeBox = new JCheckBox("Exclude from Semantic Inheritance");

  private InheritedAttributeList inheritedList = InheritedAttributeList.getInstance();

  private List<ElementChangeListener> changeListeners = new ArrayList<ElementChangeListener>();
  private List<PropertyChangeListener> propChangeListeners = new ArrayList<PropertyChangeListener>();  

  private JScrollPane reasonScrollPane;

  public ExcludeFromSemanticInheritancePanel(UMLNode node) {
    this.node = node;
    
    initUI();
    updateFields();

  }
  
  public ExcludeFromSemanticInheritancePanel() {
    initUI();
  }

  public void updateNode(UMLNode node) {
    this.node = node;

    updateFields();
  }

  private void initUI() {
    this.setLayout(new GridBagLayout());

    excludeBox.addItemListener(this);
    UIUtil.insertInBag(this, excludeBox, 0, 0);

    reasonTextField.setLineWrap(true);
    reasonTextField.setWrapStyleWord(true);

    reasonTextField.getDocument().addDocumentListener(this);
    
    reasonTextField.setBorder(javax.swing.BorderFactory.createTitledBorder("Enter Reason for Exclusion (Optional)"));

//     scrollPane = new JScrollPane(reasonTextField);
//     scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    reasonScrollPane = new JScrollPane(reasonTextField);
    reasonScrollPane.setPreferredSize(new Dimension(250, 100));

    UIUtil.insertInBag(this, reasonScrollPane, 0, 1);

  }

  private void updateFields() {
    this.setVisible(node instanceof ClassNode);

    if(!(node instanceof ClassNode))
      return;
    
    ObjectClass oc = (ObjectClass)node.getUserObject();
    boolean excluded = inheritedList.isExcludedFromSemanticInheritance(oc);
    
    excludeBox.setSelected(excluded);

    reasonScrollPane.setVisible(excluded);
    
    if(excluded) {
      String reason = inheritedList.getSemanticExclusionReason(oc);
      if(reason == null)
        reason = "";

      reasonTextField.setText(reason);
    }
  }

  public void applyPressed() {

    if(!(node instanceof ClassNode))
      return;
    
    ObjectClass oc = (ObjectClass)node.getUserObject();
    inheritedList.excludeFromSemanticInheritance(oc);

    if(!StringUtil.isEmpty(reasonTextField.getText()))
      inheritedList.addReasonForSemanticExclusion(oc, reasonTextField.getText());
    
    fireElementChangeEvent(new ElementChangeEvent((Object)node));
    
  }
  
  private void fireElementChangeEvent(ElementChangeEvent event) {
    for(ElementChangeListener l : changeListeners)
      l.elementChanged(event);
  }

  private void firePropertyChangeEvent(PropertyChangeEvent evt) {
    for(PropertyChangeListener l : propChangeListeners) 
      l.propertyChange(evt);
  }

  public void addPropertyChangeListener(PropertyChangeListener l) {
    super.addPropertyChangeListener(l);;
    propChangeListeners.add(l);
  }

  public void addElementChangeListener(ElementChangeListener listener){
    changeListeners.add(listener);
  }
  

  public void insertUpdate(DocumentEvent e) {
    firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, true));
  }
  
  public void removeUpdate(DocumentEvent e) {
    firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, true));
  }
  
  public void changedUpdate(DocumentEvent e) {
    firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, true));
  }
  
  public void itemStateChanged(ItemEvent e) {
    reasonScrollPane.setVisible(excludeBox.isSelected());
  
    updateUI();
  
    firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, true));
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setSize(500, 500);
    
    frame.add(new ExcludeFromSemanticInheritancePanel());
    
    frame.setVisible(true);
  }
  
}
