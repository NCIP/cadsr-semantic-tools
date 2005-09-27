package gov.nih.nci.ncicb.cadsr.loader.ui;
import gov.nih.nci.ncicb.cadsr.loader.util.DatatypeMapping;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class DatatypePanel extends JPanel implements ActionListener
{
  private JTextField datatypeField = new JTextField(20);
  private JComboBox associateComboBox = new JComboBox(new Vector(new HashSet(DatatypeMapping.getValues())));
  private JButton addButton = new JButton("Add");
  private JButton removeButton = new JButton("Remove");
  private JList systemDatatypesList;
  private JList userDatatypesList;
  private DefaultListModel systemListModel, userListModel;
  private Map<String, String> userMap = new HashMap();
  
  
  public DatatypePanel()
  {
    initUI();
  }

  public void initUI()
  {
    this.setLayout(new GridBagLayout());  
    
    systemListModel = new DefaultListModel();
    userListModel = new DefaultListModel();
    
    //inserts all system mappings
    Map<String, String> systemMap = DatatypeMapping.getSystemMapping();
    Set<String> keys = DatatypeMapping.getSystemKeys();
    for(String key : keys) {
      systemListModel.addElement(key + " -> " + systemMap.get(key).toString() + " ");
    }
    
    //inserts all user mappings
    userMap = DatatypeMapping.getUserMapping();
    updateUserList();

    //set List Model for System Mappings
    systemDatatypesList = new JList(systemListModel);
    systemDatatypesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    //set List Model for User Mappings     
    userDatatypesList = new JList(userListModel);
    userDatatypesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    //set ScrollPane for User Mappings
    JScrollPane userDatatypeScrollPane = new JScrollPane(userDatatypesList);
    userDatatypeScrollPane
      .setVerticalScrollBarPolicy
      (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    userDatatypeScrollPane.setPreferredSize(new Dimension(300, 125));
    
    //set ScrollPane for System Mappings
    JScrollPane datatypeScrollPane = new JScrollPane(systemDatatypesList);
    datatypeScrollPane
      .setVerticalScrollBarPolicy
      (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    datatypeScrollPane.setPreferredSize(new Dimension(300, 125));
        
    //inserts components into the panel    
    insertInBag(this, new JLabel("Enter Name for datatype"), 0, 0);
    insertInBag(this, new JLabel("Map to"), 1, 0);
    insertInBag(this, datatypeField, 0, 1);
    insertInBag(this, associateComboBox, 1, 1);
    insertInBag(this, addButton, 2, 1);
    insertInBag(this, new JLabel("User Mappings"), 0, 2);
    insertInBag(this, userDatatypeScrollPane, 0, 3, 2, 1);
    insertInBag(this, new JLabel("System Mappings"), 0, 4);
    insertInBag(this, datatypeScrollPane, 0, 5, 2, 1);
    insertInBag(this, removeButton, 2, 3);
        
    addButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) 
      {     
        userMap.put(datatypeField.getText().toLowerCase(), (String) associateComboBox.getSelectedItem());
        updateUserList();
      }
    });
    
    removeButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) 
      { 
        String t = (String) userDatatypesList.getSelectedValue();
        String temp[] = t.split("->");
        userMap.remove(temp[0].trim());
        
        updateUserList();
      }
    });
        
  }
  
  public Map getUserDatatypes() 
  {
    return userMap;
  }
  
  public void actionPerformed(ActionEvent event) 
  {
    
  }
  
  private void updateUserList() {
    userListModel.removeAllElements();
    Set<String> userKeys = userMap.keySet();
    for(String key : userKeys) {
      userListModel.addElement(key + " -> " + userMap.get(key).toString() + " ");
    }
  }
  
  private void insertInBag(JPanel bagComp, Component comp, int x, int y) {

    insertInBag(bagComp, comp, x, y, 1, 1);

  }

  private void insertInBag(JPanel bagComp, Component comp, int x, int y, int width, int height) {
    JPanel p = new JPanel();
    p.add(comp);

    bagComp.add(p, new GridBagConstraints(x, y, width, height, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }
}