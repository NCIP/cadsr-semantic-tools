package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class UmlDefaultsPanel extends JPanel implements ActionListener
{

  private JTable table = null;
  private JButton applyButton, revertButton;

  private String values[][] = 
  {
    {"Project Name", "caCORE"},
    {"Project Version", "3.0"},
    {"Context Name", "Test"},
    {"Version", "1"},
    {"WorkflowStatus", "Draft New"},
    {"Project Long Name", "Put real Long Name here"},
    {"Project Description", "Put description here"},
    {"cdName", "Put cdName here"},
    {"cdContextName", "Test"},
    {"Package Filter", "<CSM>"}
  };

  public UmlDefaultsPanel()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  {
    TableModel dataModel = new AbstractTableModel() {
        public int getColumnCount() { return values[0].length; }
        public int getRowCount() { return values.length;}
        public Object getValueAt(int row, int col) { 
          return values[row][col];
        }
        public boolean isCellEditable(int row, int col) {
          if(col != 0)
            return true;
          else
            return false;
        }
        public void setValueAt(Object value, int row, int col) 
        {
          values[row][col] = (String)value;
          fireTableCellUpdated(row,col);          
        }
      };

    this.setLayout(new BorderLayout());

    JTable table = new JTable(dataModel);
    this.add(table, BorderLayout.CENTER);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    
    applyButton = new JButton("OK");
    revertButton = new JButton("Cancel");
    
    buttonPanel.add(applyButton);
    buttonPanel.add(revertButton);
    
    applyButton.addActionListener(this);
    revertButton.addActionListener(this);

    this.add(buttonPanel, BorderLayout.SOUTH);
  }
  
  public void actionPerformed(ActionEvent event) 
  {
    //this.dispose();
  }
}