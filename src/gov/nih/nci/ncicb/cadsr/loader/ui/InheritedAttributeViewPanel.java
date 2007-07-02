package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.event.*;

import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationEvent;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;

import gov.nih.nci.ncicb.cadsr.loader.util.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class InheritedAttributeViewPanel extends JPanel
  implements NodeViewPanel, Editable {

  private DEPanel dePanel;
  private VDPanel vdPanel;

  private ApplyButtonPanel applyButtonPanel;
  private NavigationButtonPanel navButtonPanel;


  private UMLNode node;

  private JPanel cardPanel;
  
  private JPanel displayedPanel;

  static final String DE_PANEL_KEY = "dePanel", 
    VD_PANEL_KEY = "vdPanel";
    
  private Map<String, JPanel> panelKeyMap = new HashMap<String, JPanel>();

  public InheritedAttributeViewPanel(UMLNode node) 
  {
    this.node = node;
  
    dePanel = new DEPanel(node);
    vdPanel = new VDPanel(node);

    applyButtonPanel = new ApplyButtonPanel(this, (ReviewableUMLNode)node);
    navButtonPanel = new NavigationButtonPanel();    

//     buttonPanel = new ButtonPanel(conceptEditorPanel, this, dePanel);

//     conceptEditorPanel.addPropertyChangeListener(buttonPanel);
//     dePanel.addPropertyChangeListener(buttonPanel);
//     ocPanel.addPropertyChangeListener(buttonPanel);
//     cardPanel = new JPanel();
//     initUI();
//     updateNode(node);
  }

  private void initUI() {

    setLayout(new BorderLayout());
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
    JLabel space = new JLabel("      ");
    buttonPanel.add(space);
    buttonPanel.add(navButtonPanel);
    buttonPanel.add(applyButtonPanel);    

    this.add(buttonPanel, BorderLayout.SOUTH);
  }


  public void addReviewListener(ReviewListener listener) {
    applyButtonPanel.addReviewListener(listener);
  }

  public void addNavigationListener(NavigationListener listener) 
  {
    applyButtonPanel.addNavigationListener(listener);
  }
  
  public void addElementChangeListener(ElementChangeListener listener) {
    dePanel.addElementChangeListener(listener);
  }

  public void addPropertyChangeListener(PropertyChangeListener l) {
    applyButtonPanel.addPropertyChangeListener(l);
    dePanel.addPropertyChangeListener(l);
  }

  public void navigate(NavigationEvent evt) {  
    applyButtonPanel.navigate(evt);
  }

  public void applyPressed() throws ApplyException
  {
    dePanel.applyPressed();
//     if(displayedPanel instanceof Editable) 
//     {
//      ((Editable)displayedPanel).applyPressed();
//     }
  }

}