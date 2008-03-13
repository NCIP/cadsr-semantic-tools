package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.event.ReviewListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationEvent;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.event.ElementChangeListener;

import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationListener;
import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;

import java.util.*;
import javax.swing.*;
import java.awt.*;

import java.beans.PropertyChangeListener;

/**
 * Wraps the various LVD view Panels and button Panel
 */
public class LVDPanel extends JPanel implements Editable, NodeViewPanel{

  private ValueDomainViewPanel vdViewPanel;
  private MapToExistingVDPanel mteVDPanel;
  private VDButtonPanel vdButtonPanel;
  private JPanel cardPanel;

  static final String VD_FIELDS_PANEL_KEY = "vdFieldPanel";
  static final String VD_MTE_PANEL_KEY = "vdMtePanel";
      
  private Map<String, JPanel> panelKeyMap = new HashMap<String, JPanel>();

  private boolean isInitialized = false;
  
  private JPanel displayedPanel;
    
  public LVDPanel() {
  }
  
  private void initUI() {
    isInitialized = true;
    vdButtonPanel = new VDButtonPanel(this);
    
    cardPanel = new JPanel();

    cardPanel.setLayout(new CardLayout());
    cardPanel.add(vdViewPanel, VD_FIELDS_PANEL_KEY);
    cardPanel.add(mteVDPanel, VD_MTE_PANEL_KEY);
    
    panelKeyMap.put(VD_FIELDS_PANEL_KEY, vdViewPanel);
    panelKeyMap.put(VD_MTE_PANEL_KEY, mteVDPanel);

    setLayout(new BorderLayout());
    
    JPanel buttonSpacedPanel = new JPanel();
    buttonSpacedPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
    JLabel spaceLabel = new JLabel("      ");
    buttonSpacedPanel.add(spaceLabel);
    buttonSpacedPanel.add(vdButtonPanel);

    JPanel editPanel = new JPanel();
    editPanel.setLayout(new GridBagLayout());
    
    UIUtil.insertInBag(editPanel, cardPanel, 0, 1);
    
    JScrollPane scrollPane = new JScrollPane(editPanel);
    scrollPane.getVerticalScrollBar().setUnitIncrement(30);

    this.add(scrollPane, BorderLayout.CENTER);
    this.add(buttonSpacedPanel, BorderLayout.SOUTH);
    
    vdViewPanel.addPropertyChangeListener(vdButtonPanel);
    mteVDPanel.addPropertyChangeListener(vdButtonPanel);
  }

  public void update(ValueDomain vd, UMLNode umlNode) 
  {
    if(!isInitialized)
      initUI();
    vdViewPanel.update(vd, umlNode);
    mteVDPanel.update(vd, umlNode);
    
    if(StringUtil.isEmpty(vd.getPublicId())) {
        switchCards(VD_FIELDS_PANEL_KEY);
        vdButtonPanel.update((ReviewableUMLNode)umlNode, true);
    } else {
        switchCards(VD_MTE_PANEL_KEY);
        vdButtonPanel.update((ReviewableUMLNode)umlNode, false);
    }
    
  }

  public void switchCards(String key) {
    CardLayout layout = (CardLayout)cardPanel.getLayout();
    layout.show(cardPanel, key);
    displayedPanel = panelKeyMap.get(key);
  }

  public void setVdViewPanel(ValueDomainViewPanel vdViewPanel) {
    this.vdViewPanel = vdViewPanel;
  }
  
  public void setMteVDPanel(MapToExistingVDPanel mteVDPanel) {
    this.mteVDPanel = mteVDPanel;
  }

  public void addElementChangeListener(ElementChangeListener listener){
    vdViewPanel.addElementChangeListener(listener);
    mteVDPanel.addElementChangeListener(listener);
  }
  
  public void addPropertyChangeListener(PropertyChangeListener l) {
    super.addPropertyChangeListener(l);;
    vdViewPanel.addPropertyChangeListener(l);
    mteVDPanel.addPropertyChangeListener(l);
  }

  public void applyPressed() throws ApplyException {
       ((Editable)displayedPanel).applyPressed();
  }

  public void addNavigationListener(NavigationListener listener) {
      vdButtonPanel.addNavigationListener(listener);
  }

  public void addReviewListener(ReviewListener listener) {
  }

  public void updateNode(UMLNode node) {
  }

  public void navigate(NavigationEvent event) {
      vdButtonPanel.navigate(event);
  } 
}
