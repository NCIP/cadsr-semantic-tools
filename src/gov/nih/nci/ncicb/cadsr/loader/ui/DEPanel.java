package gov.nih.nci.ncicb.cadsr.loader.ui;
import gov.nih.nci.ncicb.cadsr.dao.DataElementDAO;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
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
public class DEPanel extends JPanel
{

  private JButton searchDeButton = new JButton("Search Data Element");

  private JLabel deLongNameTitleLabel = new JLabel("Data Element Long Name"),
    deLongNameValueLabel = new JLabel(),
    deIdTitleLabel = new JLabel("Public ID / Version"),
    deIdValueLabel = new JLabel(),
//     deVersionTitleLabel = new JLabel("Data Element Version"),
//     deVersionValueLabel = new JLabel(),
    deContextNameTitleLabel = new JLabel("Data Element Context"),
    deContextNameValueLabel = new JLabel(),
    vdLongNameTitleLabel = new JLabel("Value Domain Long Name"), vdLongNameValueLabel = new JLabel();
  
  private DataElement tempDE;
  private UMLNode node;

  private List<PropertyChangeListener> propChangeListeners 
    = new ArrayList<PropertyChangeListener>();  

  private static final String SEARCH = "SEARCH";
  
  public DEPanel(UMLNode node)  {
    this.node = node;
    
    
    this.setLayout(new BorderLayout());
    JPanel mainPanel = new JPanel(new GridBagLayout());
    
    insertInBag(mainPanel, searchDeButton, 1, 5, 2, 1);
    
    
    insertInBag(mainPanel, deLongNameTitleLabel, 0, 1);
    insertInBag(mainPanel, deLongNameValueLabel, 1, 1);

    insertInBag(mainPanel, deIdTitleLabel, 0, 2);
    insertInBag(mainPanel, deIdValueLabel, 1, 2);

    insertInBag(mainPanel, deContextNameTitleLabel, 0, 3);
    insertInBag(mainPanel, deContextNameValueLabel, 1, 3);

    insertInBag(mainPanel, vdLongNameTitleLabel, 0, 4);
    insertInBag(mainPanel, vdLongNameValueLabel, 1, 4);

    JPanel titlePanel = new JPanel();
    JLabel title = new JLabel("Map to CDE");
    titlePanel.add(title);

    this.add(mainPanel);
    this.add(titlePanel, BorderLayout.NORTH);
    this.setSize(300, 300);
    
    searchDeButton.setActionCommand(SEARCH);
    
    searchDeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          JButton button = (JButton)event.getSource();
          if(button.getActionCommand().equals(SEARCH)) {
            CadsrDialog cd = new CadsrDialog(CadsrDialog.MODE_DE);
            cd.setVisible(true);
            
            tempDE = (DataElement)cd.getAdminComponent();
            
            if(tempDE != null){

              deLongNameValueLabel.setText(tempDE.getLongName());
              deIdValueLabel.setText(tempDE.getPublicId() + "v" + tempDE.getVersion());
              deContextNameValueLabel.setText(tempDE.getContext().getName());
              vdLongNameValueLabel.setText(tempDE.getValueDomain().getLongName());
            
              firePropertyChangeEvent(
                new PropertyChangeEvent(this, ButtonPanel.SAVE, null, true));
            }
          }
          
        }
      });
    
  }
  
  public void updateNode(UMLNode node) 
  {
  
    this.node = node;
    if((node.getUserObject() instanceof DataElement)) {
      DataElement de = (DataElement)node.getUserObject();
    
    if(de.getPublicId() != null) {
      deLongNameValueLabel.setText(de.getLongName());
      deIdValueLabel.setText(de.getPublicId());
      deContextNameValueLabel.setText(de.getContext().getName());
      vdLongNameValueLabel.setText(de.getValueDomain().getLongName());
    }
    else 
    {
      deLongNameValueLabel.setText("");
      deIdValueLabel.setText("");
      deContextNameValueLabel.setText("");
      vdLongNameValueLabel.setText("");
    }
    }
  }
  
  public void addPropertyChangeListener(PropertyChangeListener l) {
    propChangeListeners.add(l);
  }

  private void firePropertyChangeEvent(PropertyChangeEvent evt) {
    for(PropertyChangeListener l : propChangeListeners) 
      l.propertyChange(evt);
  }
  
  public void applyPressed() 
  {
    apply();
  }
  
  public void apply() 
  {
    ((DataElement)node.getUserObject()).setLongName(tempDE.getLongName());
    ((DataElement)node.getUserObject()).setPublicId(tempDE.getPublicId());
    ((DataElement)node.getUserObject()).setVersion(tempDE.getVersion());
    ((DataElement)node.getUserObject()).setContext(tempDE.getContext());
    ((DataElement)node.getUserObject()).setValueDomain(tempDE.getValueDomain());
    
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
//    DEPanel dePanel = new DEPanel();
//    dePanel.setVisible(true);
//    frame.add(dePanel);
//    frame.setVisible(true);
//    frame.setSize(450, 350);
  }
}