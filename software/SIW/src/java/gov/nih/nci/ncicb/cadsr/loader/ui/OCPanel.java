/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModule;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.UMLNode;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.List;
import java.util.ArrayList;
public class OCPanel extends JPanel
 implements Editable {
  private OCPanel _this = this;

  private JButton searchOcButton = new JButton("Search Object Class");

  private JLabel ocPublicIdLabel = new JLabel("Public ID / Version"),
  ocPublicIdValueLabel = new JLabel(),
  ocLongNameLabel = new JLabel("Object Class Long Name"),
  ocLongNameValueLabel = new JLabel(),
  conceptCodeSummaryLabel = new JLabel("Concept Code Summary"),
  conceptCodeSummaryValue = new JLabel(),
  conceptNameSummaryLabel = new JLabel("Concept Name Summary"),
  conceptNameSummaryValue = new JLabel();

  private ObjectClass tempOC, oc;
  private UMLNode node;
  private CadsrModule cadsrModule;
  
  private List<PropertyChangeListener> propChangeListeners 
    = new ArrayList<PropertyChangeListener>();
  
  private static final String SEARCH = "SEARCH";
  private UserPreferences prefs = UserPreferences.getInstance();

  public OCPanel(UMLNode node)
  {
    if((node.getUserObject() instanceof ObjectClass))
      oc = (ObjectClass)node.getUserObject();
      
    this.setLayout(new BorderLayout());
    JPanel conceptCodeNameSummaryPanel = new JPanel(new GridBagLayout());
    if(prefs.getShowConceptCodeNameSummary()){
        UIUtil.insertInBag(conceptCodeNameSummaryPanel, conceptCodeSummaryLabel, 0, 1);
        UIUtil.insertInBag(conceptCodeNameSummaryPanel, conceptCodeSummaryValue, 1, 1);

        UIUtil.insertInBag(conceptCodeNameSummaryPanel, conceptNameSummaryLabel, 0, 2);
        UIUtil.insertInBag(conceptCodeNameSummaryPanel, conceptNameSummaryValue, 1, 2);
    }
    JPanel mainPanel = new JPanel(new GridBagLayout());
    UIUtil.insertInBag(mainPanel, ocLongNameLabel, 0, 0);
    UIUtil.insertInBag(mainPanel, ocLongNameValueLabel, 1, 0);
    UIUtil.insertInBag(mainPanel, ocPublicIdLabel, 0, 1);
    UIUtil.insertInBag(mainPanel, ocPublicIdValueLabel, 1, 1);
    
    this.setLayout(new BorderLayout());
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new BorderLayout());
    topPanel.add(conceptCodeNameSummaryPanel, BorderLayout.NORTH);
    this.add(topPanel, BorderLayout.NORTH);
    JPanel centrePanel = new JPanel();
    centrePanel.setLayout(new BorderLayout());
    centrePanel.add(mainPanel, BorderLayout.CENTER);
    this.add(centrePanel, BorderLayout.CENTER);
    this.setSize(300, 300);
    
    searchOcButton.setActionCommand(SEARCH);
    
//    searchOcButton.addActionListener(new ActionListener() {
//      public void actionPerformed(ActionEvent event) {
//      JButton button = (JButton)event.getSource();
//      if(button.getActionCommand().equals(SEARCH)) {
//        CadsrDialog cd = new CadsrDialog(CadsrDialog.MODE_OC);
//        cd.setVisible(true);
//        
//        tempOC = (ObjectClass)cd.getAdminComponent();
//      if(tempOC != null) {
//        ocPublicIdValueLabel.setText(tempOC.getPublicId());
//        ocVersionValueLabel.setText(tempOC.getVersion().toString());
//        
//        firePropertyChangeEvent(
//                new PropertyChangeEvent(this, ButtonPanel.SAVE, null, true));
//      }
//    
//      }
//    }
//    });
  }
  
  public void updateNode(UMLNode node) 
  {
    this.node = node;
    if((node.getUserObject() instanceof ObjectClass)) {
      oc = (ObjectClass)node.getUserObject();
    
    if(oc.getPublicId() != null) 
    {
      ocLongNameValueLabel.setText(oc.getLongName());
      ocPublicIdValueLabel.setText(oc.getPublicId() + " v" + oc.getVersion().toString());
      if(prefs.getShowConceptCodeNameSummary()){
        List<gov.nih.nci.ncicb.cadsr.domain.Concept> concepts = cadsrModule.getConcepts(oc);
        if(concepts != null && concepts.size() > 0){                
            StringBuffer conceptCodeSummary = new StringBuffer();
            StringBuffer conceptNameSummary = new StringBuffer();
            for(Concept con : concepts){
                conceptCodeSummary.append(con.getPreferredName());
                conceptCodeSummary.append(" ");
                conceptNameSummary.append(con.getLongName());
                conceptNameSummary.append(" ");
            }
            conceptCodeSummaryValue.setText(conceptCodeSummary.toString());
            conceptNameSummaryValue.setText(conceptNameSummary.toString());
        }
      }
    }
    else 
    {
      ocPublicIdValueLabel.setText("");
      ocLongNameValueLabel.setText("");
      conceptCodeSummaryValue.setText("");
      conceptNameSummaryValue.setText("");
    }
    
    }
    
  }
  
  public ObjectClass getOc() 
  {
    return oc;
  }
  
  public void applyPressed() 
  {
    apply();
  }
  
  public void apply() 
  {
    if(tempOC != null){
        oc.setPublicId(tempOC.getPublicId());
        oc.setVersion(tempOC.getVersion());
    }
  }
  
  public void addPropertyChangeListener(PropertyChangeListener l) {
    if (propChangeListeners != null) { propChangeListeners.add(l); }
  }
  
  private void firePropertyChangeEvent(PropertyChangeEvent evt) {
    for(PropertyChangeListener l : propChangeListeners) 
      l.propertyChange(evt);
  }
  
    public void setCadsrModule(CadsrModule cadsrModule) {
        this.cadsrModule = cadsrModule;
    }
  public static void main(String[] args)
  {
//    JFrame frame = new JFrame();
//    OCPanel ocPanel = new OCPanel();
//    ocPanel.setVisible(true);
//    frame.add(ocPanel);
//    frame.setVisible(true);
//    frame.setSize(450, 350);
//    System.out.println("Is the Panel showing up?");
  }
}