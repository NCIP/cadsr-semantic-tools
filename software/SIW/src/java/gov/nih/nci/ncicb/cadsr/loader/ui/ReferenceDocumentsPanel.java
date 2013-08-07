/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;

import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModule;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class ReferenceDocumentsPanel extends JDialog {

  private ReferenceDocumentsPanel _this = this;
  
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

  private int closeStatus;
  
  public static int CLOSE_STATUS_CANCEL = 0,
    CLOSE_STATUS_OK = 1;

  public ReferenceDocumentsPanel() {
    super((java.awt.Frame)null, true);
    initUI();
  }

  private void initUI() {
    this.setTitle("Manage Reference Documents");

    JPanel mainPanel = new JPanel();
    JScrollPane scrollPane = new JScrollPane(mainPanel);
    this.getContentPane().add(scrollPane);

    mainPanel.setLayout(new GridBagLayout());

    languageCombo = new JComboBox(PropertyAccessor.getProperty("reference.documents.languages").split(","));
    UIUtil.insertInBag(mainPanel, languageLabel, 0, 0);
    UIUtil.insertInBag(mainPanel, languageCombo, 0, 1); 

    typeCombo = new JComboBox(PropertyAccessor.getProperty("reference.documents.types").split(","));
    UIUtil.insertInBag(mainPanel, typeLabel, 0, 2);
    UIUtil.insertInBag(mainPanel, typeCombo, 0, 3); 
    
    UIUtil.insertInBag(mainPanel, nameLabel, 0, 4);
    UIUtil.insertInBag(mainPanel, nameField, 0, 5); 

    UIUtil.insertInBag(mainPanel, urlLabel, 0, 6);
    UIUtil.insertInBag(mainPanel, urlField, 0, 7); 

    textTextArea.setLineWrap(true);
    textTextArea.setWrapStyleWord(true);
    JScrollPane textScrollPane = new JScrollPane(textTextArea);
    textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    textScrollPane.setPreferredSize(new Dimension(400, 100));

    UIUtil.insertInBag(mainPanel, textLabel, 0, 8);
    UIUtil.insertInBag(mainPanel, textScrollPane, 0, 9); 

    JPanel addButtonPanel = new JPanel();
    FlowLayout fl = new FlowLayout();
    fl.setAlignment(FlowLayout.RIGHT);
    addButtonPanel.setLayout(fl);
    
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
    addButtonPanel.add(addButton);
    
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

    addButtonPanel.add(removeButton);
    UIUtil.insertInBag(mainPanel, addButtonPanel, 0, 10); 


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
          java.awt.Point p = e.getPoint();
          int rowIndex = rowAtPoint(p);
          int colIndex = columnAtPoint(p);
          return (String)getModel().getValueAt(rowIndex, colIndex);
        }
      };
//     JScrollPane tableScrollPane = new JScrollPane(refDocTable);

    UIUtil.insertInBag(mainPanel, refDocTable, 0, 11);
    
    JPanel closeButtonPanel = new JPanel();
    fl = new FlowLayout(FlowLayout.RIGHT);
    closeButtonPanel.setLayout(fl);
    
    JButton okButton = new JButton("OK");
    okButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          closeStatus = CLOSE_STATUS_OK;
          _this.dispose();
        }
      });
    
    closeButtonPanel.add(okButton);

    JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          closeStatus = CLOSE_STATUS_CANCEL;
          _this.dispose();
        }
      });
    
    closeButtonPanel.add(cancelButton);


    UIUtil.insertInBag(mainPanel, closeButtonPanel, 0, 12);

    this.setSize(500, 600);

  }

  public int getCloseStatus() {
    return closeStatus;
  }

  public List<ReferenceDocument> getResult() {
    return refDocs;
  }

  public void setReferenceDocuments(List<ReferenceDocument> refDocs) {
    this.refDocs = refDocs;
    refDocTableModel.fireTableDataChanged();
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
    ReferenceDocumentsPanel refDocPanel = new ReferenceDocumentsPanel();
    refDocPanel.setVisible(true);
  }

  public void setCadsrModule(CadsrModule cadsrModule){
    this.cadsrModule = cadsrModule;
  }


}