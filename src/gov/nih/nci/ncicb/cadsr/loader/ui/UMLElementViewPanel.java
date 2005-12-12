package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.event.*;

import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationEvent;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;

import gov.nih.nci.ncicb.cadsr.loader.util.*;
import gov.nih.nci.ncicb.cadsr.loader.*;

import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.awt.event.ItemEvent;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class UMLElementViewPanel extends JPanel
  implements //ActionListener, KeyListener
             //, ItemListener,
             //UserPreferencesListener, 
             NavigationListener {

  private UMLElementViewPanel _this = this;

  private java.util.List<NavigationListener> navigationListeners 
    = new ArrayList<NavigationListener>();

  private ConceptEditorPanel conceptEditorPanel;
  private DEPanel dePanel;
  private OCPanel ocPanel;
  private ButtonPanel buttonPanel;

  private UMLNode node;

  private JPanel cardPanel;
  // initialize once the mode in which we're running
  
  private JPanel displayedPanel;

  static final String DE_PANEL_KEY = "dePanel", 
    OC_PANEL_KEY = "ocPanel",
    CONCEPT_PANEL_KEY = "conceptPanel";
    
  private Map<String, JPanel> panelKeyMap = new HashMap<String, JPanel>();

  public UMLElementViewPanel(UMLNode node) 
  {
    this.node = node;
  
    conceptEditorPanel = new ConceptEditorPanel(node);
    dePanel = new DEPanel(node);
    ocPanel = new OCPanel(node);
    buttonPanel = new ButtonPanel(conceptEditorPanel, this, dePanel, ocPanel);
    conceptEditorPanel.addPropertyChangeListener(buttonPanel);
    dePanel.addPropertyChangeListener(buttonPanel);
    ocPanel.addPropertyChangeListener(buttonPanel);
    cardPanel = new JPanel();
    initUI();
    updateNode(node);
  }
  
  private void initUI() {

    conceptEditorPanel.initUI();
    
    cardPanel.setLayout(new CardLayout());
    cardPanel.add(conceptEditorPanel, CONCEPT_PANEL_KEY);
    cardPanel.add(dePanel, DE_PANEL_KEY);
    cardPanel.add(ocPanel, OC_PANEL_KEY);
    
    panelKeyMap.put(CONCEPT_PANEL_KEY, conceptEditorPanel);
    panelKeyMap.put(DE_PANEL_KEY, dePanel);
    panelKeyMap.put(OC_PANEL_KEY, ocPanel);

    setLayout(new BorderLayout());
    this.add(cardPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.SOUTH);
    
  }
  
  public void switchCards(String key) 
  {
    CardLayout layout = (CardLayout)cardPanel.getLayout();
    layout.show(cardPanel, key);
    displayedPanel = panelKeyMap.get(key);
  }
  
  //new 
  public void updateNode(UMLNode node) 
  {
  
    this.node = node;
    // is UMLNode a de?
    Object o = node.getUserObject();
    if(o instanceof DataElement) { //if it is, does it have pubID
      DataElement de = (DataElement)o;
      if(!StringUtil.isEmpty(de.getPublicId())) 
      {
        switchCards(DE_PANEL_KEY);
        buttonPanel.switchDeButton("Switch to Concept");
      } else 
      {
        switchCards(CONCEPT_PANEL_KEY);
        buttonPanel.switchDeButton("Switch to DE");
      }
    } else if(o instanceof ObjectClass) {
      ObjectClass oc = (ObjectClass)o;
      if(!StringUtil.isEmpty(oc.getPublicId())) 
      {
        switchCards(OC_PANEL_KEY);
        buttonPanel.switchOcButton("Switch to Concept");
      }
      else 
      {
        switchCards(CONCEPT_PANEL_KEY);
        buttonPanel.switchOcButton("Switch to OC");
      }
    } 
//    else { // not a DE, it's an OC
//      switchCards("Concept");
//      buttonPanel.switchDeButton("Switch to DE");
//    }
  
    conceptEditorPanel.updateNode(node);
    dePanel.updateNode(node);
    ocPanel.updateNode(node);

    buttonPanel.update();
    
  }

  public boolean isReviewed() 
  {
    return ((ReviewableUMLNode)node).isReviewed();
  }
  //new
  public void apply(boolean value) 
  {
    conceptEditorPanel.apply(value);
  }

  public void navigate(NavigationEvent evt) {  
//      for(NavigationListener nl : navigationListeners)
//        nl.navigate(evt);
    buttonPanel.navigate(evt);
  }

  public void addReviewListener(ReviewListener listener) {
    buttonPanel.addReviewListener(listener);
  }

  public void addNavigationListener(NavigationListener listener) 
  {
//    navigationListeners.add(listener);
    buttonPanel.addNavigationListener(listener);
  }
  
  public void addElementChangeListener(ElementChangeListener listener) {
    conceptEditorPanel.addElementChangeListener(listener);
  }

  public void addPropertyChangeListener(PropertyChangeListener l) {
    conceptEditorPanel.addPropertyChangeListener(l);
    buttonPanel.addPropertyChangeListener(l);
    dePanel.addPropertyChangeListener(l);
    ocPanel.addPropertyChangeListener(l);
  }
  
  public void applyPressed() 
  {
    if(displayedPanel instanceof Editable) 
    {
     ((Editable)displayedPanel).applyPressed();
    }
    
  }



  






  
















  

  


  

  

  


  


}

      