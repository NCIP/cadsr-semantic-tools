package gov.nih.nci.ncicb.cadsr.loader.ui;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.UMLNode;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.List;
import java.util.ArrayList;
public class OCPanel extends JPanel
 implements Editable {
  private OCPanel _this = this;

  private JButton searchOcButton = new JButton("Search Object Class");

  private JLabel ocPublicIdLabel = new JLabel("Public ID / Version"),
  ocPublicIdValueLabel = new JLabel(),
  ocLongNameLabel = new JLabel("Object Class Long Name"),
  ocLongNameValueLabel = new JLabel();
  
  private ObjectClass tempOC, oc;
  private UMLNode node;
  
  private List<PropertyChangeListener> propChangeListeners 
    = new ArrayList<PropertyChangeListener>();
  
  private static final String SEARCH = "SEARCH";
  
  public OCPanel(UMLNode node)
  {
    if((node.getUserObject() instanceof ObjectClass))
      oc = (ObjectClass)node.getUserObject();
      
    this.setLayout(new BorderLayout());
    JPanel mainPanel = new JPanel(new GridBagLayout());
    insertInBag(mainPanel, ocLongNameLabel, 0, 0);
    insertInBag(mainPanel, ocLongNameValueLabel, 1, 0);
    insertInBag(mainPanel, ocPublicIdLabel, 0, 1);
    insertInBag(mainPanel, ocPublicIdValueLabel, 1, 1);
    //insertInBag(mainPanel, ocVersionLabel, 0, 1);
    //insertInBag(mainPanel, ocVersionValueLabel, 1, 1);
    //insertInBag(mainPanel, searchOcButton, 1, 2);
    
    JPanel titlePanel = new JPanel();
    JLabel title = new JLabel("Map to OC");
    titlePanel.add(title);
    
    //this.setLayout(new BorderLayout());
    this.add(titlePanel, BorderLayout.NORTH);
    this.add(mainPanel);
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
      
    }
    else 
    {
      ocPublicIdValueLabel.setText("");
      ocLongNameValueLabel.setText("");
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
    oc.setPublicId(tempOC.getPublicId());
    oc.setVersion(tempOC.getVersion());
  }
  
  public void addPropertyChangeListener(PropertyChangeListener l) {
    propChangeListeners.add(l);
  }
  
  private void firePropertyChangeEvent(PropertyChangeEvent evt) {
    for(PropertyChangeListener l : propChangeListeners) 
      l.propertyChange(evt);
  }
  
  private void insertInBag(JPanel bagComp, Component comp, int x, int y) {
    insertInBag(bagComp, comp, x, y, 1, 1);
  }

  private void insertInBag(JPanel bagComp, Component comp, int x, int y, int width, int height) {
    JPanel p = new JPanel();
    p.add(comp);

    bagComp.add(p, new GridBagConstraints(x, y, width, height, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
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