package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.event.*;

import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationEvent;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;

import gov.nih.nci.ncicb.cadsr.loader.util.*;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

import java.beans.PropertyChangeListener;

import java.util.*;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;
import java.awt.Insets;


public class InheritedAttributeViewPanel extends JPanel
  implements NodeViewPanel, Editable {

  private DEPanel dePanel;
  private VDPanel vdPanel;

  private ApplyButtonPanel applyButtonPanel;
  private NavigationButtonPanel navButtonPanel;

  private UMLNode node;

  static final String DE_PANEL_KEY = "dePanel", 
    VD_PANEL_KEY = "vdPanel";

  private ElementsLists elements = ElementsLists.getInstance();

  private UserPreferences prefs = UserPreferences.getInstance();

  public InheritedAttributeViewPanel(UMLNode node) 
  {
    this.node = node;
  
    dePanel = new DEPanel(node);
    vdPanel = new VDPanel(node);

    applyButtonPanel = new ApplyButtonPanel(this, (ReviewableUMLNode)node);
    navButtonPanel = new NavigationButtonPanel();    

//     buttonPanel = new ButtonPanel(conceptEditorPanel, this, dePanel);

//     conceptEditorPanel.addPropertyChangeListener(buttonPanel);
    dePanel.addPropertyChangeListener(applyButtonPanel);
    vdPanel.addPropertyChangeListener(applyButtonPanel);
    //     ocPanel.addPropertyChangeListener(buttonPanel);
//     cardPanel = new JPanel();
    initUI();
    updateNode(node);
  }

  private void initUI() {

    setLayout(new BorderLayout());

    JPanel editPanel = new JPanel();
    editPanel.setLayout(new GridBagLayout());

    if(prefs.getUmlDescriptionOrder().equals("first"))
      insertInBag(editPanel, UIUtil.createDescriptionPanel(node), 0, 0);
    else
      insertInBag(editPanel, UIUtil.createDescriptionPanel(node), 0, 3); 

    insertInBag(editPanel, dePanel, 0, 1);
    insertInBag(editPanel, vdPanel, 0, 2);

//     if(prefs.getUmlDescriptionOrder().equals("last"))

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
    JLabel space = new JLabel("      ");
    buttonPanel.add(space);
    buttonPanel.add(navButtonPanel);
    buttonPanel.add(applyButtonPanel);    

    JScrollPane scrollPane = new JScrollPane(editPanel);

    this.add(scrollPane, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.SOUTH);
  }


  private void insertInBag(JPanel bagComp, Component comp, int x, int y) {
    insertInBag(bagComp, comp, x, y, 1, 1);
  }
  private void insertInBag(JPanel bagComp, Component comp, int x, int y, int width, int height) {
    JPanel p = new JPanel();
    p.add(comp);

    bagComp.add(p, new GridBagConstraints(x, y, width, height, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
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
    vdPanel.addElementChangeListener(listener);
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
    vdPanel.applyPressed();

    updateView();

  }


  public void updateNode(UMLNode node) {
    
    this.node = node;
    // is UMLNode a de?
//    DataElement de = (DataElement)node.getUserObject();

//     if(!StringUtil.isEmpty(de.getPublicId())) {
//       switchCards(DE_PANEL_KEY);
//     } else {
//       switchCards(CONCEPT_PANEL_KEY);
//     }
      
    dePanel.updateNode(node);
    vdPanel.updateNode(node);

    
//     buttonPanel.setEditablePanel(dePanel);

//     buttonPanel.propertyChange
//       (new PropertyChangeEvent(this, ButtonPanel.SETUP, null, true));
    
    applyButtonPanel.update();
    
    // need to figure out what goes in next line
    applyButtonPanel.init(true);


    updateView();
    
  }

  private void updateView() {
    // find the parent node so we can decide what's allowed here.

    // find rootNode
    UMLNode rootNode = null, tempNode = node.getParent();
    while(tempNode != null) {
      rootNode = tempNode;
      tempNode = tempNode.getParent();
    }
    String fpath = node.getFullPath();
    int sd = fpath.lastIndexOf(".");
    String className = fpath.substring(0, sd);
    String attributeName = fpath.substring(sd+1);
    
    AttributeNode parentNode = findSuper(className, attributeName, rootNode);

    DataElement de = (DataElement)node.getUserObject();
    DataElement parentDE = (DataElement)parentNode.getUserObject();
      
    if (parentNode != null) {

      if(StringUtil.isEmpty(parentDE.getPublicId())) {
        dePanel.setVisible(false);
      } else {
        dePanel.setVisible(true);
      }
    }

    if(StringUtil.isEmpty(de.getPublicId())) {
      vdPanel.setVisible(true);
    } else {
      vdPanel.setVisible(false);
    }
    

  }


  /**
   * Find the given attribute in the given class's inheritance hierarchy. 
   * Returns the first non-inherited AttributeNode.
   * @return
   */
  private AttributeNode findSuper(String className, String attributeName, UMLNode rootNode) {

    // find the superclass by searching the OCR's for a generalization 
    
    List<ObjectClassRelationship> ocrs = 
      elements.getElements(DomainObjectFactory.newObjectClassRelationship());
    
    String superClassName = null;
    for(ObjectClassRelationship ocr : ocrs) {
      if (ocr.getType() == ObjectClassRelationship.TYPE_IS && 
          LookupUtil.lookupFullName(ocr.getSource()).equals(className)) {
        superClassName = LookupUtil.lookupFullName(ocr.getTarget());
        break;
      }
    }
    
    if (superClassName == null) {
      System.err.println("Superclass not found for "+className);
      return null;
    }
    
    // find the super class in the tree
    
    int div = superClassName.lastIndexOf(".");
    String sPackage = superClassName.substring(0, div);
    String sName = superClassName.substring(div+1);


    for(Object pchild : rootNode.getChildren()) {
      PackageNode pnode = (PackageNode)pchild;
      if (pnode.getFullPath().equals(sPackage)) {
        for(Object cchild : pnode.getChildren()) {
          ClassNode cnode = (ClassNode)cchild;
          if (cnode.getDisplay().equals(sName)) {
            PackageNode inherited = null;
            for(Object achild : cnode.getChildren()) {
              if ("Inherited Attributes".equals(
                    ((UMLNode)achild).getDisplay())) {
                // remember the inheritance subtree for later
                inherited = (PackageNode)achild;
              }
              else if (achild instanceof AttributeNode) {
                AttributeNode anode = (AttributeNode)achild;
                if (anode.getDisplay().equals(attributeName)) {
                  return anode;
                }
              }
            }
            // attribute wasn't found, check inheritance subtree
            if (inherited != null) {
              for(Object achild : inherited.getChildren()) {
                AttributeNode anode = (AttributeNode)achild;
                if (anode.getDisplay().equals(attributeName)) {
                  return findSuper(cnode.getFullPath(), attributeName, rootNode);
                }
              }
              
            }
          }
        } 
      }
    }
      
    return null;
  }


}