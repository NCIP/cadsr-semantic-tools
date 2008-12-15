package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.util.DatatypeMapping;

import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.*;

import javax.swing.*;

public class DatatypePanel extends JPanel 
{
  private JTextField datatypeField = new JTextField(20);
  private JComboBox associateComboBox = new JComboBox(new Vector(new HashSet(DatatypeMapping.getValues())));
  private JButton addButton = new JButton("Add");
  private JButton addToListButton = new JButton("Add to List");
  private JButton removeButton = new JButton("Remove");
  private JList systemDatatypesList;
  private JList userDatatypesList;
  private DefaultListModel systemListModel, userListModel;
  private Map<String, String> userMap = new HashMap<String, String>();
  
  
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
    TreeSet<String> keys = new TreeSet(DatatypeMapping.getSystemKeys());
    
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
    UIUtil.insertInBag(this, new JLabel("Enter Name for datatype"), 0, 0);
    UIUtil.insertInBag(this, new JLabel("Map to"), 1, 0);
    UIUtil.insertInBag(this, datatypeField, 0, 1);
    UIUtil.insertInBag(this, associateComboBox, 1, 1);
    UIUtil.insertInBag(this, addButton, 2, 1);
    UIUtil.insertInBag(this, addToListButton, 3, 1); 
   UIUtil.insertInBag(this, new JLabel("User Mappings"), 0, 2);
    UIUtil.insertInBag(this, userDatatypeScrollPane, 0, 3, 2, 1);
    UIUtil.insertInBag(this, new JLabel("System Mappings"), 0, 4);
    UIUtil.insertInBag(this, datatypeScrollPane, 0, 5, 2, 1);
    UIUtil.insertInBag(this, removeButton, 2, 3);
        
    addButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) 
      {     
        userMap.put(datatypeField.getText().toLowerCase(), (String) associateComboBox.getSelectedItem());
        updateUserList();
      }
    });

    addToListButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) 
      {     
        associateComboBox.addItem(datatypeField.getText());
        datatypeField.setText("");
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
  
  public Map<String, String> getUserDatatypes() 
  {
    return userMap;
  }
  
  private void updateUserList() {
    userListModel.removeAllElements();
    TreeSet<String> sortedUserKeys = new TreeSet(userMap.keySet());
    for(String key : sortedUserKeys) {
      userListModel.addElement(key + " -> " + userMap.get(key).toString() + " ");
    }
  }
 
  public static void main(String[] args) {
    DatatypeMapping dm = DatatypeMapping.getInstance();
    dm.setMappingURL("http://cadsrsiw-dev.nci.nih.gov/datatype-mapping.xml");

    UserSelections.getInstance().setProperty("FILENAME", "/home/ludetc/temp/xmi/temp.xmi");

    dm.addUserMapping();

    final DatatypePanel dp = new DatatypePanel();

    JFrame jf = new JFrame();
    jf.setLayout(new BorderLayout());
    jf.add(dp, BorderLayout.CENTER);

    JButton saveButton = new JButton("Save");
    saveButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          DatatypeMapping.writeUserMapping(dp.getUserDatatypes());
        }
      });
    
    
    jf.add(saveButton, BorderLayout.SOUTH);

    jf.setSize(800, 600);
    jf.setVisible(true);
    
  }
 
}