package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.util.HashMap;
import java.util.Map;

import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;

public class UmlDefaultsPanel extends JDialog implements ActionListener
{

  private UmlDefaultsPanel _this = this;

  private JTable table = null;
  private JButton applyButton, revertButton;

  private Map<String, String> values = new HashMap();

  private String rowNames[] = 
  {
    "Project Name",
    "Project Version",
    "Context Name",
    "Version",
    "Workflow Status",
    "Project Long Name",
    "Project Description",
    "Concepual Domain",
    "CD Context Name",
    "Package Filter"
  };

  public UmlDefaultsPanel()
  {
    super((java.awt.Frame)null, "UML Loader Defaults", true);
    initValues();
    initUI();
  }

  private void initValues() {
    UMLDefaults defaults = UMLDefaults.getInstance();
    values.put(rowNames[0], defaults.getProjectCs().getPreferredName());
    values.put(rowNames[1], defaults.getProjectCs().getVersion().toString());
    values.put(rowNames[2], defaults.getProjectCs().getContext().getName());
    values.put(rowNames[3], "1.0");
    values.put(rowNames[4], defaults.getWorkflowStatus());
    values.put(rowNames[5], defaults.getProjectCs().getLongName());
    values.put(rowNames[6], defaults.getProjectCs().getPreferredDefinition());
    values.put(rowNames[7], defaults.getConceptualDomain().getLongName());
    values.put(rowNames[8], defaults.getProjectCs().getPreferredDefinition());
    values.put(rowNames[9], defaults.getProjectCs().getPreferredDefinition());
  }

  private void initUI() 
  {
    TableModel dataModel = new AbstractTableModel() {
        public int getColumnCount() { return 2; }
        public int getRowCount() { return rowNames.length;}
        public Object getValueAt(int row, int col) { 
          if(col == 0)
            return rowNames[row];
          else 
            return values.get(rowNames[row]);
        }
        public boolean isCellEditable(int row, int col) {
          if(col != 0)
            return true;
          else
            return false;
        }
        public void setValueAt(Object value, int row, int col) 
        {
          if(col == 1)
            values.put(rowNames[row], value.toString());
          fireTableCellUpdated(row,col);          
        }
      };


    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    JTable table = new JTable(dataModel);
    mainPanel.add(table, BorderLayout.CENTER);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    
    applyButton = new JButton("OK");
    revertButton = new JButton("Cancel");
    
    buttonPanel.add(applyButton);
    buttonPanel.add(revertButton);
    
    applyButton.addActionListener(this);
    revertButton.addActionListener(this);

    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(mainPanel, BorderLayout.CENTER);
    this.setSize(300, 225);


  }
  
  public void actionPerformed(ActionEvent event) 
  {
    _this.dispose();
  }
}