/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.event.*;

import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationEvent;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;

import gov.nih.nci.ncicb.cadsr.loader.util.*;

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


public class PackageViewPanel extends JPanel
  implements NodeViewPanel {

  private UMLNode node;
  private GMEViewPanel gmeViewPanel;

  private NavigationButtonPanel navButtonPanel;

  public PackageViewPanel(UMLNode node) {
    this.node = node;

    initUI();
  }

  private void initUI() {
    this.setLayout(new BorderLayout());
    
    gmeViewPanel = new GMEViewPanel(node);

    navButtonPanel = new NavigationButtonPanel();    

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
    JLabel space = new JLabel("      ");
    buttonPanel.add(space);
    buttonPanel.add(navButtonPanel);


    JScrollPane scrollPane = new JScrollPane(gmeViewPanel);

    this.add(scrollPane, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.SOUTH);
  }

  public void updateNode(UMLNode node) {
    this.node = node;

    gmeViewPanel.updateNode(node);
  }

  public void addElementChangeListener(ElementChangeListener listener) {
//     throw new RuntimeException("Implement ME !!");
  }

  public void addNavigationListener(NavigationListener listener) 
  {
//     throw new RuntimeException("Implement ME !!");
  }

  public void addReviewListener(ReviewListener listener) {
//     throw new RuntimeException("Implement ME !!");
  }

  public void navigate(NavigationEvent evt) {  
//     throw new RuntimeException("Implement ME !!");
//     applyButtonPanel.navigate(evt);
  }


}