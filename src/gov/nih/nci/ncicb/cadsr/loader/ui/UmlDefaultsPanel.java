package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class UmlDefaultsPanel extends JPanel 
{

  private JTable table = null;
  private JButton applyButton, revertButton;

  private String values[][] = 
  {
    {"Project Name", "caCORE"},
    {"Project Version", "3.0"},
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
      };

    this.setLayout(new BorderLayout());

    JTable table = new JTable(dataModel);
    this.add(table, BorderLayout.CENTER);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    
    applyButton = new JButton("apply");
    revertButton = new JButton("revert");
    
    buttonPanel.add(applyButton);
    buttonPanel.add(revertButton);

    this.add(buttonPanel, BorderLayout.SOUTH);
  }
}