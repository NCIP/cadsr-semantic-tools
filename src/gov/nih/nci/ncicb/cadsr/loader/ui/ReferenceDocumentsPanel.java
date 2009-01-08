package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;

import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModule;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class ReferenceDocumentsPanel extends JPanel {
  
  private List<ReferenceDocument> refDocs = new ArrayList<ReferenceDocument>();

  private JLabel languageLabel = new JLabel("Select Language"),
    typeLabel = new JLabel("Select Reference Document Type"),
    nameLabel = new JLabel("Create Reference Document Name (Maximum 255 Characters)"),
    textLabel = new JLabel("Create Reference Document Text (Maximum 4000 Characters)"), 
    urlLabel = new JLabel("Create Reference Document URL (Maximum 240 Characters)");
 
  private JTextArea textTextArea = new JTextArea(30, 5);
  private JTextField nameField = new JTextField(30),
    urlField = new JTextField(30);

  private JComboBox languageCombo, typeCombo;

  private CadsrModule cadsrModule = null;
  
  private JTable refDocTable;
  private AbstractTableModel refDocTableModel;

  public ReferenceDocumentsPanel() {
    initUI();
  }
  

  private void initUI() {
    this.setLayout(new GridBagLayout());

    languageCombo = new JComboBox(PropertyAccessor.getProperty("reference.documents.languages").split(","));
    UIUtil.insertInBag(this, languageLabel, 0, 0, 2, 1);
    UIUtil.insertInBag(this, languageCombo, 0, 1, 2, 1); 

    typeCombo = new JComboBox(PropertyAccessor.getProperty("reference.documents.types").split(","));
    UIUtil.insertInBag(this, typeLabel, 0, 2, 2, 1);
    UIUtil.insertInBag(this, typeCombo, 0, 3, 2, 1); 
    
    UIUtil.insertInBag(this, nameLabel, 0, 4, 2, 1);
    UIUtil.insertInBag(this, nameField, 0, 5, 2, 1); 

    UIUtil.insertInBag(this, urlLabel, 0, 6, 2, 1);
    UIUtil.insertInBag(this, urlField, 0, 7, 2, 1); 

    textTextArea.setLineWrap(true);
    textTextArea.setWrapStyleWord(true);
    JScrollPane textScrollPane = new JScrollPane(textTextArea);
    textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    textScrollPane.setPreferredSize(new Dimension(400, 100));

    UIUtil.insertInBag(this, textLabel, 0, 8, 2, 1);
    UIUtil.insertInBag(this, textScrollPane, 0, 9, 2, 1); 
    
    JButton addButton = new JButton("Add");
    addButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          
          ReferenceDocument refDoc = DomainObjectFactory.newReferenceDocument();
          if(StringUtil.isEmpty(nameField.getText()))
            return;
          refDoc.setName(nameField.getText());
            
          refDoc.setType(typeCombo.getSelectedItem().toString());
          
          refDoc.setLanguage(languageCombo.getSelectedItem().toString());

          if(StringUtil.isEmpty(urlField.getText()) && StringUtil.isEmpty(textTextArea.getText()))
            return;

          if(!StringUtil.isEmpty(urlField.getText()))
            refDoc.setUrl(urlField.getText());

          if(!StringUtil.isEmpty(textTextArea.getText()))
            refDoc.setText(textTextArea.getText());
          
          refDocs.add(refDoc);
          
          refDocTableModel.fireTableDataChanged();
        }
    });
    UIUtil.insertInBag(this, addButton, 0, 10); 
    
    JButton removeButton = new JButton("Remove");
    removeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          int row = refDocTable.getSelectedRow();
          if(row > -1) {
            refDocs.remove(row);
            refDocTableModel.fireTableDataChanged();
          }

        }
      });
    UIUtil.insertInBag(this, removeButton, 1, 10); 


    refDocTableModel = new AbstractTableModel() {
        private String[] columnNames = new String[] {"Name", "Type", "URL", "Language", "Text"};

        public String getColumnName(int col) {
          return columnNames[col].toString();
        }
        public int getRowCount() { 
          return refDocs.size(); 
        }
        public int getColumnCount() { return columnNames.length; }

        public Object getValueAt(int row, int col) {
          ReferenceDocument refDoc = refDocs.get(row);

          String s = "";

          switch (col) {
          case 0:
            s = refDoc.getName();
            break;
          case 1:
            s = refDoc.getType();
            break;
          case 2:
            s = refDoc.getUrl();
            break;
          case 3: 
            s = refDoc.getLanguage();
            break;
          case 4: 
            s += "<html><body>";
            s += refDoc.getText();
            s += "</body></html>"; 
            break;
          default:
            break;
          }
           
          return s;
        }
        public boolean isCellEditable(int row, int col)
        { return false; }
      };

    // New table overriding the tooltip method
    refDocTable = new JTable(refDocTableModel) {
        public String getToolTipText(java.awt.event.MouseEvent e) {
          String tip = null;
          java.awt.Point p = e.getPoint();
          int rowIndex = rowAtPoint(p);
          int colIndex = columnAtPoint(p);
          return (String)getModel().getValueAt(rowIndex, colIndex);
        }
      };
    JScrollPane tableScrollPane = new JScrollPane(refDocTable);

    UIUtil.insertInBag(this, refDocTable, 0, 11, 2, 1);
    

  }

  public void setReferenceDocuments(List<ReferenceDocument> refDocs) {
    this.refDocs = refDocs;
  }

  private void addReferenceDocument(String language, 
                                    String type,
                                    String name,
                                    String text,
                                    String url) {

    ReferenceDocument refDoc = DomainObjectFactory.newReferenceDocument();
    refDoc.setLanguage(language);
    refDoc.setType(type);
    refDoc.setName(name);
    refDoc.setText(text);
    refDoc.setUrl(url);
    
  }
  
  public static void main(String[] args) {
    JFrame f = new JFrame();
    f.setSize(500, 500);
    f.add(new ReferenceDocumentsPanel());
    f.setVisible(true);
  }

  public void setCadsrModule(CadsrModule cadsrModule){
    this.cadsrModule = cadsrModule;
  }


}