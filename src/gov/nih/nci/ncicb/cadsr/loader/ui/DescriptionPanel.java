package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.Definition;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;
import gov.nih.nci.ncicb.cadsr.loader.event.ElementChangeEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ElementChangeListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.AttributeNode;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.ClassNode;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.UMLNode;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.ValueMeaningNode;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.BorderLayout;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.ToolTipManager;

public class DescriptionPanel extends JPanel implements Editable, DocumentListener
{
    public JTextArea descriptionArea = new JTextArea();
    
    private UMLNode node;
    private JPanel descriptionPanel;
    
    private List<ElementChangeListener> changeListeners = new ArrayList<ElementChangeListener>();
    private List<PropertyChangeListener> propChangeListeners = new ArrayList<PropertyChangeListener>();  
    
    public DescriptionPanel(UMLNode node){
        this.node = node;
    }

    public JPanel getDescriptionPanel() 
    {
        descriptionArea = UIUtil.createDescription(node);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);

        descriptionPanel = new JPanel();
        descriptionPanel.setBorder(BorderFactory.createTitledBorder(UIUtil.getPanelTitle()));   
        descriptionPanel.setLayout(new BorderLayout());
        descriptionPanel.add(descriptionScrollPane, BorderLayout.CENTER);

        descriptionArea.getDocument().addDocumentListener(this);

        return descriptionPanel;
    }
    
    public void applyPressed() {   
        boolean verify = true;
        String descText = descriptionArea.getText();
        String newDescText = descText.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "");
        if(!descText.equalsIgnoreCase(newDescText)){
            verify = false;
            descriptionArea.setText(newDescText);
        }
        if(!verify){
            JOptionPane.showMessageDialog
              (null, PropertyAccessor.getProperty("ui.desc.invalid.characters"), 
              "Invalid Characters", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(node instanceof ClassNode) {
          ObjectClass oc = (ObjectClass) node.getUserObject();
          oc.setPreferredDefinition(newDescText);
        } else if(node instanceof AttributeNode) {
          DataElement de = (DataElement) node.getUserObject();
          for(Definition def : (List<Definition>) de.getDefinitions()) {
            def.setDefinition(newDescText);
            break;
          }
        } else if(node instanceof ValueMeaningNode) {
          ValueMeaning vm = (ValueMeaning)node.getUserObject();
          for(Definition def :  vm.getDefinitions()) {
            def.setDefinition(newDescText);
            break;
          }
        }
        firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, false));
        fireElementChangeEvent(new ElementChangeEvent((Object)node));
    }
    
    public void disableInheritedAttDescArea(){
        descriptionArea.setEditable(false);
        descriptionArea.setForeground(Color.LIGHT_GRAY);
    }
    
    public void enableInheritedAttDescArea(){
        descriptionArea.setEditable(true);
        descriptionArea.setForeground(Color.WHITE);
    }
    
    public void setAttDescToolTip(String tooltipText){
        ToolTipManager.sharedInstance().registerComponent(descriptionArea);
        ToolTipManager.sharedInstance().setDismissDelay(3600000);
        descriptionArea.setToolTipText(tooltipText);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener l) {
        super.addPropertyChangeListener(l);;
        propChangeListeners.add(l);
    }

    public void addElementChangeListener(ElementChangeListener listener){
        changeListeners.add(listener);
    }
    private void fireElementChangeEvent(ElementChangeEvent event) {
        for(ElementChangeListener l : changeListeners)
            l.elementChanged(event);
    }
    private void firePropertyChangeEvent(PropertyChangeEvent evt) {
        for(PropertyChangeListener l : propChangeListeners) 
            l.propertyChange(evt);
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
}
