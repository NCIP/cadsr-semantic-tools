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
  private ButtonPanel buttonPanel;

  // initialize once the mode in which we're running

  public UMLElementViewPanel(UMLNode node) 
  {
  
    conceptEditorPanel = new ConceptEditorPanel(node);
    buttonPanel = new ButtonPanel(conceptEditorPanel);
    conceptEditorPanel.addPropertyChangeListener(buttonPanel);
    initUI();
  }
  
  private void initUI() {

    conceptEditorPanel.initUI();
    
    setLayout(new BorderLayout());
    this.add(conceptEditorPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.SOUTH);
    
  }
  
  //new 
  public void updateNode(UMLNode node) 
  {
    conceptEditorPanel.updateNode(node);
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
  }



  






  
















  

  


  

  

  


  


}

      