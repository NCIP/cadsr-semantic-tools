package gov.nih.nci.ncicb.cadsr.loader.ui;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.UMLNode;
import gov.nih.nci.ncicb.cadsr.loader.util.*;
import gov.nih.nci.ncicb.cadsr.loader.event.*;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

import java.util.List;
import java.util.ArrayList;
public class VDPanel extends JPanel 
{
  private JButton searchVdButton = new JButton("Search Value Domain");
  
  private JLabel vdLongNameTitleLabel = new JLabel("Long Name: "),
    vdLongNameValueLabel = new JLabel(""),
    vdPublicIdTitleLabel = new JLabel("Public Id: "),
    vdPublicIdValueLabel = new JLabel(""),
    vdContextNameTitleLabel = new JLabel("Context Name: "),
    vdContextNameValueLabel = new JLabel(""),
    vdVersionTitleLabel = new JLabel("Version: "),
    vdVersionValueLabel = new JLabel(""),
    vdDatatypeTitleLabel = new JLabel("Datatype: "),
    vdDatatypeValueLabel = new JLabel(""),
    lvdTitleLabel = new JLabel("Local Value Domain: "),
    lvdValueLabel = new JLabel("");


  private static final String SEARCH = "SEARCH";
  
  private List<PropertyChangeListener> propChangeListeners 
    = new ArrayList<PropertyChangeListener>(); 

  private List<ElementChangeListener> changeListeners 
    = new ArrayList<ElementChangeListener>();
  
  private ValueDomain tempVD, vd;
  private UMLNode node;
  private boolean modified = false;

  public VDPanel(UMLNode node)
  {
    this.node = node;
    DataElement de = null;
    if(node.getUserObject() instanceof DataElement) {
      de = (DataElement)node.getUserObject();
      vd = de.getValueDomain();
    }    

    this.setLayout(new BorderLayout());
    JPanel mainPanel = new JPanel(new GridBagLayout());
    
    UIUtil.insertInBag(mainPanel, lvdTitleLabel, 0, 0);
    UIUtil.insertInBag(mainPanel, lvdValueLabel, 1, 0);
    UIUtil.insertInBag(mainPanel, vdLongNameTitleLabel, 0, 1);
    UIUtil.insertInBag(mainPanel, vdLongNameValueLabel, 1, 1);
    UIUtil.insertInBag(mainPanel, vdPublicIdTitleLabel, 0, 2);
    UIUtil.insertInBag(mainPanel, vdPublicIdValueLabel, 1, 2);
    UIUtil.insertInBag(mainPanel, vdContextNameTitleLabel, 0, 3);
    UIUtil.insertInBag(mainPanel, vdContextNameValueLabel, 1, 3);
    UIUtil.insertInBag(mainPanel, vdVersionTitleLabel, 0, 4);
    UIUtil.insertInBag(mainPanel, vdVersionValueLabel, 1, 4);
    UIUtil.insertInBag(mainPanel, vdDatatypeTitleLabel, 0, 5);
    UIUtil.insertInBag(mainPanel, vdDatatypeValueLabel, 1, 5);
    
    UIUtil.insertInBag(mainPanel, searchVdButton, 1, 6, 2, 1);

    mainPanel.setBorder
        (BorderFactory.createTitledBorder("Value Domain"));
    
    this.add(mainPanel);
    this.setSize(300, 300);

    
    searchVdButton.setActionCommand(SEARCH);
//     if(de != null)
//       searchVdButton.setVisible(!DEMappingUtil.isMappedToLocalVD(de));
      
    searchVdButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        JButton button = (JButton)event.getSource();
        if(button.getActionCommand().equals(SEARCH)) {
          CadsrDialog cd = BeansAccessor.getCadsrVDDialog();
          cd.setVisible(true);
        
          tempVD = (ValueDomain)cd.getAdminComponent();
          if(tempVD != null) {
            vdLongNameValueLabel.setText(tempVD.getLongName());
            vdPublicIdValueLabel.setText(tempVD.getPublicId());
            vdContextNameValueLabel.setText(tempVD.getContext().getName());
            vdVersionValueLabel.setText(tempVD.getVersion().toString());
            vdDatatypeValueLabel.setText(tempVD.getDataType());
            
            vdLongNameTitleLabel.setVisible(true);
            vdPublicIdTitleLabel.setVisible(true);
            vdContextNameTitleLabel.setVisible(true);
            vdVersionTitleLabel.setVisible(true);
            vdDatatypeTitleLabel.setVisible(true);
            
            firePropertyChangeEvent(
                new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, true));

            modified = true;
          }
        }
      }
  });
}
  
  public void applyPressed() 
  {
    apply();
  }
  
  public void setEnabled(boolean enabled) {
      searchVdButton.setEnabled(enabled);
  }
  
  public void apply() 
  {
    if(!modified)
      return;
    modified = false;

    if(node.getUserObject() instanceof DataElement) 
      vd = ((DataElement)node.getUserObject()).getValueDomain();

    if(tempVD != null) {
      vd.setLongName(tempVD.getLongName());
      vd.setPublicId(tempVD.getPublicId());
      vd.setVersion(tempVD.getVersion());
      vd.setContext(tempVD.getContext());
      vd.setDataType(tempVD.getDataType());
    }
    
    firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, false));
    firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.REVIEW, null, true));
    fireElementChangeEvent(new ElementChangeEvent(node));

  }
  
  public void updateNode(UMLNode node) 
  {
    this.node = node;
    if(node.getUserObject() instanceof DataElement) {
      DataElement de = (DataElement)node.getUserObject();
      vd = de.getValueDomain();
      searchVdButton.setVisible(DEMappingUtil.isMappedToLocalVD(de) == null);


      vdLongNameValueLabel.setText(vd.getLongName()); 
      
      if(vd != null && !StringUtil.isEmpty(vd.getPublicId())) {
        vdContextNameValueLabel.setText(vd.getContext().getName());
        vdVersionValueLabel.setText(vd.getVersion().toString());
        vdPublicIdValueLabel.setText(vd.getPublicId());
        vdDatatypeValueLabel.setText(vd.getDataType());
        
        vdLongNameTitleLabel.setVisible(true);
        vdPublicIdTitleLabel.setVisible(true);
        vdContextNameTitleLabel.setVisible(true);
        vdVersionTitleLabel.setVisible(true);
        vdDatatypeTitleLabel.setVisible(true);
      }
      else 
        { 
          vdContextNameValueLabel.setText("");
          vdVersionValueLabel.setText("");
          vdPublicIdValueLabel.setText("");
          vdDatatypeValueLabel.setText("");
        }
      
      if(vdLongNameValueLabel.getText().equals(""))
        vdLongNameTitleLabel.setVisible(false);
      if(vdVersionValueLabel.getText().equals(""))
        vdVersionTitleLabel.setVisible(false);
      if(vdPublicIdValueLabel.getText().equals(""))
        vdPublicIdTitleLabel.setVisible(false);
      if(vdDatatypeValueLabel.getText() == null || vdDatatypeValueLabel.getText().equals(""))
        vdDatatypeTitleLabel.setVisible(false);
      if(vdContextNameValueLabel.getText().equals(""))
        vdContextNameTitleLabel.setVisible(false);

      if(DEMappingUtil.isMappedToLocalVD(de) != null) {
        lvdTitleLabel.setVisible(true);
        lvdValueLabel.setText(LookupUtil.lookupFullName(vd));
        vdLongNameTitleLabel.setVisible(false);
        vdLongNameValueLabel.setVisible(false);
      } else {
        lvdTitleLabel.setVisible(false);
      }

    }
  }

  public void addPropertyChangeListener(PropertyChangeListener l) {
    propChangeListeners.add(l);
  }
  public void addElementChangeListener(ElementChangeListener listener) {
    changeListeners.add(listener);
  }
  
  private void firePropertyChangeEvent(PropertyChangeEvent evt) {
    for(PropertyChangeListener l : propChangeListeners) 
      l.propertyChange(evt);
  }
  
  private void fireElementChangeEvent(ElementChangeEvent event) {
    for(ElementChangeListener l : changeListeners)
      l.elementChanged(event);
  }
  

  
}