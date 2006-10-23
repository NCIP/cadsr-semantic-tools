package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.event.ElementChangeListener;
import gov.nih.nci.ncicb.cadsr.loader.event.ReviewListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationEvent;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.UMLNode;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Association Concept Editor Panel
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class AssociationConceptPanel extends JPanel
  implements NavigationListener, Editable {

  private UMLNode node;
  
  private ButtonPanel buttonPanel;
  private ConceptEditorPanel conceptEditorPanel;

  public AssociationConceptPanel(UMLNode node) {
    this.node = node;
    initUI();
  }

  private void initUI() {
    conceptEditorPanel = new ConceptEditorPanel(node);
    buttonPanel = new ButtonPanel(conceptEditorPanel, this, null);

    conceptEditorPanel.initUI();

    JPanel buttonSpacedPanel = new JPanel();
    buttonSpacedPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
    JLabel spaceLabel = new JLabel("      ");
    buttonSpacedPanel.add(spaceLabel);
    buttonSpacedPanel.add(buttonPanel);

    setLayout(new BorderLayout());

    this.add(conceptEditorPanel, BorderLayout.CENTER);
    this.add(buttonSpacedPanel, BorderLayout.SOUTH);

    conceptEditorPanel.addPropertyChangeListener(buttonPanel);

  }

  public void updateNode(UMLNode node) {

    conceptEditorPanel.updateNode(node);

    buttonPanel.propertyChange
      (new PropertyChangeEvent(this, ButtonPanel.SETUP, null, true));
    
    buttonPanel.update();
  }
  
  public void navigate(NavigationEvent evt) {  
    buttonPanel.navigate(evt);
  }

  public void addReviewListener(ReviewListener listener) {
    buttonPanel.addReviewListener(listener);
  }
  
  public void addNavigationListener(NavigationListener listener) {
    buttonPanel.addNavigationListener(listener);
  }
  
  public void addElementChangeListener(ElementChangeListener listener) {
    conceptEditorPanel.addElementChangeListener(listener);
  }

  public void addPropertyChangeListener(PropertyChangeListener l) {
    conceptEditorPanel.addPropertyChangeListener(l);
    buttonPanel.addPropertyChangeListener(l);
  }
  
  public void applyPressed() throws ApplyException {
    conceptEditorPanel.applyPressed();
  }
}

