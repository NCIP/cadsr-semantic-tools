package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.event.ElementChangeListener;

import java.util.*;
import javax.swing.*;
import java.awt.*;

import java.beans.PropertyChangeListener;

/**
 * Wraps the various LVD view Panels and button Panel
 */
public class LVDPanel extends JPanel {

  private ValueDomainViewPanel vdViewPanel;
  private JPanel cardPanel;
  private UMLNode node;

  static final String VD_FIELDS_PANEL_KEY = "vdFieldPanel";

  private boolean isInitialized = false;
    
  public LVDPanel() {
  }
  
//   public LVDPanel(UMLNode node) {
//     this.node = node;

//     vdViewPanel = new ValueDomainViewPanel();

//     initUI();

//   }


  private void initUI() {
    isInitialized = true;

    cardPanel = new JPanel();

    cardPanel.setLayout(new CardLayout());
    cardPanel.add(vdViewPanel, VD_FIELDS_PANEL_KEY);

    setLayout(new BorderLayout());
    
//     JPanel newPanel = new JPanel();
//     newPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
//     JLabel space = new JLabel("      ");
//     newPanel.add(space);
//     newPanel.add(buttonPanel);

    JPanel editPanel = new JPanel();
    editPanel.setLayout(new GridBagLayout());
    
    UIUtil.insertInBag(editPanel, cardPanel, 0, 1);
    
    JScrollPane scrollPane = new JScrollPane(editPanel);
    scrollPane.getVerticalScrollBar().setUnitIncrement(30);

    this.add(scrollPane, BorderLayout.CENTER);
//     this.add(newPanel, BorderLayout.SOUTH);
    
  }

  public void update(ValueDomain vd, UMLNode umlNode) 
  {

    if(!isInitialized)
      initUI();

    vdViewPanel.update(vd, umlNode);
    
  }

  public void setVdViewPanel(ValueDomainViewPanel vdViewPanel) {
    this.vdViewPanel = vdViewPanel;
  }
  
  public void addElementChangeListener(ElementChangeListener listener){
    vdViewPanel.addElementChangeListener(listener);
  }
  
  public void addPropertyChangeListener(PropertyChangeListener l) {
      super.addPropertyChangeListener(l);;
      vdViewPanel.addPropertyChangeListener(l);
  }
  
}