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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

  private List<NavigationListener> navigationListeners 
    = new ArrayList<NavigationListener>();

  private ConceptEditorPanel conceptEditorPanel;
  private DEPanel dePanel;
  private ButtonPanel buttonPanel;

  private UMLNode node;

  private JPanel cardPanel;
  // initialize once the mode in which we're running

  public UMLElementViewPanel(UMLNode node) 
  {
    this.node = node;
  
    conceptEditorPanel = new ConceptEditorPanel(node);
    dePanel = new DEPanel(node);
    buttonPanel = new ButtonPanel(conceptEditorPanel, this, dePanel);
    conceptEditorPanel.addPropertyChangeListener(buttonPanel);
    dePanel.addPropertyChangeListener(buttonPanel);
    cardPanel = new JPanel();
    initUI();
    updateNode(node);
  }
  
  private void initUI() {

    conceptEditorPanel.initUI();
    
    cardPanel.setLayout(new CardLayout());
    cardPanel.add(conceptEditorPanel, "Concept");
    cardPanel.add(dePanel, "DEPanel");
    
    setLayout(new BorderLayout());
    this.add(cardPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.SOUTH);
    
  }
  
  public void switchCards(String text) 
  {
    CardLayout layout = (CardLayout)cardPanel.getLayout();
    layout.show(cardPanel, text);
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
        switchCards("DEPanel");
        buttonPanel.changeSwitchButton("Switch to Concept");
      } else 
      {
        switchCards("Concept");
        buttonPanel.changeSwitchButton("Switch to DE");
      }
    } else { // not a DE, it's an OC
      switchCards("Concept");
      buttonPanel.changeSwitchButton("Switch to DE");
    }
  
    conceptEditorPanel.updateNode(node);
    dePanel.updateNode(node);

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
  }



  






  
















  

  


  

  

  


  


}

      