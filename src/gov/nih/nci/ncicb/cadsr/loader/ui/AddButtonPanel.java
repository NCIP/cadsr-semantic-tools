package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;


public class AddButtonPanel extends JPanel implements ActionListener, PropertyChangeListener
{

  private JButton addButton;

  static final String ADD = "ADD";

  private ConceptEditorPanel conceptEditorPanel;

  public AddButtonPanel(ConceptEditorPanel p) {
    conceptEditorPanel = p;

    addButton = new JButton("Add");
    addButton.setActionCommand(ADD);
    addButton.addActionListener(this);

    this.add(addButton);

  }

  public void propertyChange(PropertyChangeEvent e) 
  {
    if(e.getPropertyName().equals(ADD)) {
      addButton.setEnabled((Boolean)e.getNewValue());
    }
  }

  public void actionPerformed(ActionEvent evt) {
    AbstractButton button = (AbstractButton)evt.getSource();

    if(button.getActionCommand().equals(ADD)) 
        conceptEditorPanel.addPressed();
    
  }


}
