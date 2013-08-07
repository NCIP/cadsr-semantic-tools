/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

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
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

import java.util.List;
import java.util.ArrayList;

public class VDPanel extends JPanel implements MouseListener, ActionListener {
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
  
  private JToolBar toolbar = new JToolBar();
  private DropDownButton dropDownButton;
  
  private static final String MAP_CADSR_VD = "Search caDSR Value Domain",
    MAP_LOCAL_VD = "Map to Local Value Domain",
    CLEAR_VD = "Clear Value Domain";

  private JLabel cadsrVDLabel = new JLabel(MAP_CADSR_VD),
    mapToLVDLabel = new JLabel(MAP_LOCAL_VD),
    clearVDLabel = new JLabel(CLEAR_VD);

  private List<PropertyChangeListener> propChangeListeners = new ArrayList<PropertyChangeListener>(); 
  
  private List<ElementChangeListener> changeListeners = new ArrayList<ElementChangeListener>();
  
  protected ElementsLists elements = ElementsLists.getInstance();
  
  private ValueDomain tempVD, vd;
  private UMLNode node;
  private boolean modified = false;
  private boolean isLVD = false;
  private ValueDomain selectedLVD = null;
  private JPanel cadsrVDPanel, lvdPanel, clearVDPanel;
  private JPanel mainPanel;
  
  public VDPanel(UMLNode node) {
    this.node = node;
    DataElement de = null;
    if(node.getUserObject() instanceof DataElement) {
      de = (DataElement)node.getUserObject();
      vd = de.getValueDomain();
    }    
    
    this.setLayout(new BorderLayout());
    mainPanel = new JPanel(new GridBagLayout());
      
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
      
    dropDownButton = new DropDownButton("Value Domains");
      
    dropDownButton.addActionListener(this);

    cadsrVDPanel = new JPanel();
    cadsrVDPanel.setBackground(Color.WHITE);
    cadsrVDPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    cadsrVDPanel.add(cadsrVDLabel);
      
    lvdPanel = new JPanel();
    lvdPanel.setBackground(Color.WHITE);
    lvdPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    lvdPanel.add(mapToLVDLabel);
    
    clearVDPanel = new JPanel();
    clearVDPanel.setBackground(Color.WHITE);
    clearVDPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    clearVDPanel.add(clearVDLabel);

    dropDownButton.addComponent(clearVDPanel);
    dropDownButton.addComponent(cadsrVDPanel);
    dropDownButton.addComponent(lvdPanel);
    toolbar.add(dropDownButton);
    toolbar.setFloatable(false);
      
    UIUtil.insertInBag(mainPanel, toolbar, 1, 8, 2, 1);
    mainPanel.setBorder(BorderFactory.createTitledBorder("Value Domain"));
      
    cadsrVDLabel.addMouseListener(this);
    mapToLVDLabel.addMouseListener(this);
    clearVDLabel.addMouseListener(this);
      
    this.add(mainPanel);
    this.setSize(300, 300);
  }
  
  public void showCADSRSearchDialog(){
    CadsrDialog cd = BeansAccessor.getCadsrVDDialog();
    cd.setVisible(true);
    
    tempVD = (ValueDomain)cd.getAdminComponent();
    if(tempVD != null) {
      removeAll();
      makePanel();
      lvdValueLabel.setText(tempVD.getLongName());
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
      
      firePropertyChangeEvent
        (new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, true));
      
      modified = true;
      setMainPanel();
    }
  }

  public void showLVDSearchDialog(){
    List<ValueDomain> lvds = elements.getElements(DomainObjectFactory.newValueDomain());
    MapToLVD mapToLVD = new MapToLVD(lvds); 
    mapToLVD.setAlwaysOnTop(true);
    mapToLVD.setVisible(true);
    
    if(lvds != null){
      selectedLVD = mapToLVD.getLocalValueDomain();
      if(selectedLVD != null){
        removeAll();
        addLongName();
        lvdValueLabel.setText(selectedLVD.getLongName());
        vdLongNameValueLabel.setText(selectedLVD.getLongName());
        vdLongNameTitleLabel.setVisible(true);
        lvdTitleLabel.setVisible(true);
        
        vdPublicIdValueLabel.setText("");
        vdContextNameValueLabel.setText("");
        vdVersionValueLabel.setText("");
        vdDatatypeValueLabel.setText("");
        
        vdPublicIdTitleLabel.setVisible(false);
        vdContextNameTitleLabel.setVisible(false);
        vdVersionTitleLabel.setVisible(false);
        vdDatatypeTitleLabel.setVisible(false);
        
        firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, true));
        
        modified = true;
        isLVD = true;
        setMainPanel();
      }
    }
  }

  private void clearVDMapping() {
    DataElement de = null;
    if(node.getUserObject() instanceof DataElement) {
      de = (DataElement)node.getUserObject();
      vd = de.getValueDomain();
    }    

    String datatype = null;
    if(de != null) {
      List<AttributeDatatypePair> attTypesPairs = elements.getElements(new AttributeDatatypePair("", ""));
      String attributeName = LookupUtil.lookupFullName(de);
      for(AttributeDatatypePair pair : attTypesPairs) {
        if(pair.getAttributeName().equals(attributeName)) {
          datatype = pair.getDatatype();
        }
      }
    }
    removeAll();

    mainPanel = new JPanel(new GridBagLayout());
    UIUtil.insertInBag(mainPanel, vdDatatypeTitleLabel, 0, 1);
    UIUtil.insertInBag(mainPanel, vdDatatypeValueLabel, 1, 1);
    vdDatatypeTitleLabel.setVisible(true);
    vdDatatypeValueLabel.setVisible(true);
    setMainPanel();

    vdDatatypeValueLabel.setText(datatype);

    tempVD = DomainObjectFactory.newValueDomain();
    tempVD.setLongName(datatype);


    firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, true));
    
    modified = true;
    isLVD = false;
  }

  public void mouseClicked(MouseEvent e) {
    cadsrVDPanel.setBackground(Color.WHITE);
    lvdPanel.setBackground(Color.WHITE);
    clearVDPanel.setBackground(Color.WHITE);
    dropDownButton.unfocus();
    if(((JLabel)e.getSource()).getText().equals(MAP_CADSR_VD))
      showCADSRSearchDialog();        
    else if(((JLabel)e.getSource()).getText().equals(MAP_LOCAL_VD))
      showLVDSearchDialog();
    else if(((JLabel)e.getSource()).getText().equals(CLEAR_VD))
      clearVDMapping();
  }

  public void mousePressed(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {
    if(((JLabel)e.getSource()).getText().equals(MAP_CADSR_VD))
      cadsrVDPanel.setBackground(Color.LIGHT_GRAY);
    else if(((JLabel)e.getSource()).getText().equals(MAP_LOCAL_VD))
      lvdPanel.setBackground(Color.LIGHT_GRAY);
    else if(((JLabel)e.getSource()).getText().equals(CLEAR_VD))
      clearVDPanel.setBackground(Color.LIGHT_GRAY);
  }
  public void mouseExited(MouseEvent e) {
    if(((JLabel)e.getSource()).getText().equals(MAP_CADSR_VD))
      cadsrVDPanel.setBackground(Color.WHITE);
    else if(((JLabel)e.getSource()).getText().equals(MAP_LOCAL_VD))
      lvdPanel.setBackground(Color.WHITE);
    else if(((JLabel)e.getSource()).getText().equals(CLEAR_VD))
      clearVDPanel.setBackground(Color.WHITE);
  }


  public void applyPressed() 
  {
    apply();
  }
  
  public void setEnabled(boolean enabled) {
    searchVdButton.setEnabled(enabled);
  }
  
  public void apply() {
    if(!modified)
      return;
    modified = false;

    DataElement _de = (DataElement)node.getUserObject();
    
    //     if(node.getUserObject() instanceof DataElement) 
    vd = _de.getValueDomain();
    
    DEMappingUtil.setMappedToLVD(_de, isLVD);
    if(!isLVD){
      if(tempVD != null) {
        _de.setValueDomain(tempVD);
      }
    } else {
      isLVD = false;
      _de.setValueDomain(selectedLVD);
    }
    firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, false));
    firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.REVIEW, null, true));
    fireElementChangeEvent(new ElementChangeEvent(node));
  }
  
  public void makePanel(){
    mainPanel = new JPanel(new GridBagLayout());
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
  }
  
  public void addAll() {
    makePanel();
    
    vdLongNameTitleLabel.setVisible(true);
    vdPublicIdTitleLabel.setVisible(true);
    vdContextNameTitleLabel.setVisible(true);
    vdVersionTitleLabel.setVisible(true);
    vdDatatypeTitleLabel.setVisible(true);
    
    vdLongNameValueLabel.setText(vd.getLongName()); 
    vdContextNameValueLabel.setText(vd.getContext().getName());
    vdVersionValueLabel.setText(vd.getVersion().toString());
    vdPublicIdValueLabel.setText(vd.getPublicId());
    vdDatatypeValueLabel.setText(vd.getDataType());
    setMainPanel();
  }    

  public void removeAll() {
    this.remove(mainPanel);
  }

  
  
  public void addDatatype() {
    mainPanel = new JPanel(new GridBagLayout());
    UIUtil.insertInBag(mainPanel, lvdTitleLabel, 0, 0);
    UIUtil.insertInBag(mainPanel, lvdValueLabel, 1, 0);
    UIUtil.insertInBag(mainPanel, vdDatatypeTitleLabel, 0, 1);
    UIUtil.insertInBag(mainPanel, vdDatatypeValueLabel, 1, 1);
    vdDatatypeTitleLabel.setVisible(true);
    vdDatatypeValueLabel.setVisible(true);
    setMainPanel();
  }
  
  public void addLongName() {
    mainPanel = new JPanel(new GridBagLayout());
    UIUtil.insertInBag(mainPanel, lvdTitleLabel, 0, 0);
    UIUtil.insertInBag(mainPanel, lvdValueLabel, 1, 0);
    UIUtil.insertInBag(mainPanel, vdLongNameTitleLabel, 0, 1);
    UIUtil.insertInBag(mainPanel, vdLongNameValueLabel, 1, 1);
    vdLongNameTitleLabel.setVisible(true);
    vdLongNameValueLabel.setVisible(true);
    setMainPanel();
  }
  
  public void setMainPanel() {
    UIUtil.insertInBag(mainPanel, toolbar, 2, 8, 2, 1);
    mainPanel.setBorder(BorderFactory.createTitledBorder("Value Domain"));
    this.add(mainPanel);
    this.setSize(400, 400);
  }
  
  public void updateNode(UMLNode node) {
    this.node = node;
    removeAll();
    if(node.getUserObject() instanceof DataElement) {
      DataElement de = (DataElement)node.getUserObject();
      vd = de.getValueDomain();
      searchVdButton.setVisible(!DEMappingUtil.isMappedToLVD(de));
      
      if(vd != null && !StringUtil.isEmpty(vd.getPublicId())) {
        addAll();
      }
      else 
        { 
          if(!DEMappingUtil.isMappedToLVD(de)){
            List<AttributeDatatypePair> attTypesPairs = elements.getElements(new AttributeDatatypePair("", ""));
            String datatype = null;
            String attributeName = LookupUtil.lookupFullName(de);
            for(AttributeDatatypePair pair : attTypesPairs) {
              if(pair.getAttributeName().equals(attributeName)) {
                datatype = pair.getDatatype();
              }
            }
            addDatatype();
            vdDatatypeValueLabel.setText(datatype);
          }else{
            addLongName();
            vdLongNameValueLabel.setText(vd.getLongName()); 
            vdContextNameValueLabel.setText("");
            vdVersionValueLabel.setText("");
            vdPublicIdValueLabel.setText("");
            vdDatatypeValueLabel.setText("");
          }
        }
      
      if(vdLongNameValueLabel.getText().trim().equals(""))
        vdLongNameTitleLabel.setVisible(false);
      if(vdVersionValueLabel.getText().trim().equals(""))
        vdVersionTitleLabel.setVisible(false);
      if(vdPublicIdValueLabel.getText().trim().equals(""))
        vdPublicIdTitleLabel.setVisible(false);
      if(vdDatatypeValueLabel.getText() == null || vdDatatypeValueLabel.getText().trim().equals(""))
        vdDatatypeTitleLabel.setVisible(false);
      if(vdContextNameValueLabel.getText().trim().equals(""))
        vdContextNameTitleLabel.setVisible(false);

      if(DEMappingUtil.isMappedToLVD(de)) {
        lvdTitleLabel.setVisible(true);
        lvdValueLabel.setText(LookupUtil.lookupFullName(vd));
      } else {
        lvdTitleLabel.setVisible(false);
      }
    }
  }

  public void actionPerformed(ActionEvent evt) {
    showCADSRSearchDialog();
  }

  public void addPropertyChangeListener(PropertyChangeListener l) {
    if (propChangeListeners != null) { propChangeListeners.add(l); }
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