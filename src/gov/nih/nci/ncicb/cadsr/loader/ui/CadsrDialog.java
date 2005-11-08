package gov.nih.nci.ncicb.cadsr.loader.ui;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModule;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class CadsrDialog extends JDialog implements ActionListener, KeyListener
{
  private CadsrDialog _this = this;

  private JLabel searchLabel = new JLabel("Search:");
  private JTextField searchField = new JTextField(10);
  private JLabel whereToSearchLabel = new JLabel("Search By");
  private JComboBox searchSourceCombo;
  
  private JButton searchButton = new JButton("Search");
  
  private AbstractTableModel tableModel = null;
  private JTable resultTable = null;
  
  private static final String PUBLIC_ID = "Public ID";
  private static final String LONG_NAME = "Long Name";
  
  private JButton previousButton = new JButton("Previous"),
    nextButton = new JButton("Next"), 
    closeButton = new JButton("Close");
  
  private JLabel indexLabel = new JLabel("");

  private static String SEARCH = "SEARCH",
    PREVIOUS = "PREVIOUS",
    NEXT = "NEXT",
    CLOSE = "CLOSE";
    
  private java.util.List resultSet = new ArrayList();

  private String[] columnNames = {
    "LongName", "Preferred Name", "Public Id", "Version", 
    "Preferred Definition", "Context Name"
  };

  private int colWidth[] = {15, 15, 15, 15, 30, 15};

  private static int PAGE_SIZE = 5;

  private int pageIndex = 0;
  
  private AdminComponent choiceAdminComponent = null;

  public static final int MODE_OC = 1;
  public static final int MODE_PROP = 2;
  public static final int MODE_VD = 3;
  
  private int mode;
  
  public CadsrDialog(int mode)
  {
    super((JFrame)null, true);
    
    this.mode = mode;

    if(mode == MODE_OC)
    this.setTitle("Search for Object Class");
    if(mode == MODE_PROP)
    this.setTitle("Search for Property");
    if(mode == MODE_VD)
    this.setTitle("Search for Value Domain");
    
    this.getContentPane().setLayout(new BorderLayout());

    String values[] = {LONG_NAME, PUBLIC_ID};
    searchSourceCombo = new JComboBox(values);
    JPanel searchPanel = new JPanel(new GridBagLayout());
    
   
    tableModel = new AbstractTableModel() {
        public String getColumnName(int col) {
          return columnNames[col].toString();
        }
        public int getRowCount() { 
          return (int)Math.min(resultSet.size(), PAGE_SIZE); 
        }
        public int getColumnCount() { return columnNames.length; }
        public Object getValueAt(int row, int col) {
          row = row + PAGE_SIZE * pageIndex;

          if(row >= resultSet.size())
            return "";

          AdminComponent res = (AdminComponent)resultSet.get(row);
          
          String s = "";
          switch (col) {
          case 0:
            s = res.getLongName();
            break;
          case 1:
            s = res.getPreferredName();
            break;
          case 2:
            s = res.getPublicId();
            break;
          case 3:
            s = res.getVersion().toString();
            break;
          case 4:
            s = res.getPreferredDefinition();
            break;
          case 5:
            s = res.getContext().toString();
            break;
          default:
            break;
          }
          return s;
        }
        public boolean isCellEditable(int row, int col)
        { return false; }
  
   };
    
   resultTable = new JTable(tableModel) {
        public String getToolTipText(java.awt.event.MouseEvent e) {
          String tip = null;
          java.awt.Point p = e.getPoint();
          int rowIndex = rowAtPoint(p);
          int colIndex = columnAtPoint(p);
          
          return (String)getModel().getValueAt(rowIndex, colIndex);
        }
      };
      
    DefaultTableCellRenderer  tcrColumn  =  new DefaultTableCellRenderer();
    tcrColumn.setVerticalAlignment(JTextField.TOP);
    resultTable.getColumnModel().getColumn(3).setCellRenderer(tcrColumn);
    resultTable.getColumnModel().getColumn(4).setCellRenderer(tcrColumn);
    
    int c = 0;
    for(int width : colWidth) {
      TableColumn col = resultTable.getColumnModel().getColumn(c++);
      col.setPreferredWidth(width);
    }
    
    resultTable.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent evt) {
          if(evt.getClickCount() == 2) {
            int row = resultTable.getSelectedRow();
            if(row > -1) {
              choiceAdminComponent = ((AdminComponent)resultSet.get(pageIndex * PAGE_SIZE + row));
              _this.dispose();
            }
          }
        }
      });
    
    JScrollPane scrollPane = new JScrollPane(resultTable);
    
    insertInBag(searchPanel, searchLabel, 0, 0);
    insertInBag(searchPanel, searchField, 1, 0);
    insertInBag(searchPanel, whereToSearchLabel, 2, 0);
    insertInBag(searchPanel, searchSourceCombo, 3, 0);
    insertInBag(searchPanel, searchButton, 4, 0);
    
    searchField.addKeyListener(this);

    searchButton.addActionListener(this);
    searchButton.addKeyListener(this);
    searchButton.setActionCommand(SEARCH);
    
    JPanel browsePanel = new JPanel();
    browsePanel.add(previousButton);
    browsePanel.add(nextButton);
    browsePanel.add(closeButton);
    
    previousButton.setActionCommand(PREVIOUS);
    nextButton.setActionCommand(NEXT);
    closeButton.setActionCommand(CLOSE);
    previousButton.setEnabled(false);
    nextButton.setEnabled(false);
    previousButton.addActionListener(this);
    nextButton.addActionListener(this);
    closeButton.addActionListener(this);
    
    
    this.getContentPane().add(searchPanel, BorderLayout.NORTH);
    this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    this.getContentPane().add(browsePanel, BorderLayout.SOUTH);
    this.setSize(600,500);
    
  }
  
  public void startSearch(String searchString) {
    searchField.setText(searchString);
    searchButton.doClick();
  }
  
  public AdminComponent getAdminComponent() {
    try {
      return choiceAdminComponent;
    } finally {
      choiceAdminComponent = null;
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
  
  public void actionPerformed(ActionEvent event) 
  {
    JButton button = (JButton)event.getSource();
    if(button.getActionCommand().equals(SEARCH) && mode == MODE_OC) {
      String selection = (String) searchSourceCombo.getSelectedItem();
      String text = searchField.getText();

      CadsrModule module = new CadsrModule();

      resultSet = new ArrayList();

      _this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

      if(selection.equals(LONG_NAME)) {
        resultSet.addAll(module.findOCByLongName(text));
      }
      if(selection.equals(PUBLIC_ID)) {
        resultSet.addAll(module.findOCByPublicId(text));
      }

      _this.setCursor(Cursor.getDefaultCursor());
    
      updateTable();

    }
    
    if(button.getActionCommand().equals(SEARCH) && mode == MODE_PROP) {
      String selection = (String) searchSourceCombo.getSelectedItem();
      String text = searchField.getText();

      CadsrModule module = new CadsrModule();

      resultSet = new ArrayList();

      _this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      
      if(selection.equals(LONG_NAME)) {
       // module.findPropByLongName(text);
        resultSet.addAll(module.findPropByLongName(text));       
        System.out.println("Finished the search");
      }
      
      if(selection.equals(PUBLIC_ID)) {
        resultSet.addAll(module.findPropByPublicId(text));
      }
      
      _this.setCursor(Cursor.getDefaultCursor());
    
      updateTable();
    }
    else if(button.getActionCommand().equals(PREVIOUS)) {
      pageIndex--;
      updateTable();
    } else if(button.getActionCommand().equals(NEXT)) {
      pageIndex++;
      updateTable();
    } else if(button.getActionCommand().equals(CLOSE)) {
      this.dispose();
    }
    updateIndexLabel();
  }
  
  private void updateIndexLabel() {
    if(resultSet.size() == 0) {
      indexLabel.setText("");
    } else {
      StringBuilder sb = new StringBuilder();
      int start = PAGE_SIZE * pageIndex;
      int end = (int)Math.min(resultSet.size(), start + PAGE_SIZE); 
      sb.append(start);
      sb.append("-");
      sb.append(end);
      indexLabel.setText(sb.toString());
    }
    
  }

  private void updateTable() {
    tableModel.fireTableDataChanged();

    previousButton.setEnabled(pageIndex > 0);

    nextButton.setEnabled(resultSet.size() > (pageIndex * PAGE_SIZE + PAGE_SIZE));

  }
  
  public void keyPressed(KeyEvent evt) {
    if(evt.getKeyCode() == KeyEvent.VK_ENTER)
      searchButton.doClick();
  }

  public void keyTyped(KeyEvent evt) {
    if(evt.getKeyCode() == KeyEvent.VK_ENTER)
      searchButton.doClick();
  }

  public void keyReleased(KeyEvent evt) {
  }
  
  public static void main(String[] args) 
  {
    CadsrDialog dialog = new CadsrDialog(CadsrDialog.MODE_OC);
    dialog.setVisible(true);
  }
}