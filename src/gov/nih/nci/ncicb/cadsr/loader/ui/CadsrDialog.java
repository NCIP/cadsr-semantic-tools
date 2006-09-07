package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import gov.nih.nci.ncicb.cadsr.loader.ext.*;

import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.log4j.*;

public class CadsrDialog extends JDialog implements ActionListener, KeyListener, CadsrModuleListener
{
  private CadsrDialog _this = this;

  private JLabel searchLabel = new JLabel("Search:");
  private JTextField searchField = new JTextField(10);
  private JLabel whereToSearchLabel = new JLabel("Search By");
  private JLabel numberOfResultsLabel = new JLabel("Results Per Page");
  private JComboBox searchSourceCombo;
  private JComboBox numberOfResultsCombo;
  
  private JButton searchButton = new JButton("Search");
  
  private AbstractTableModel tableModel = null;
  private JTable resultTable = null;
  
  private static final String PUBLIC_ID = "Public ID";
  private static final String LONG_NAME = "Long Name";
  
  private JButton previousButton = new JButton("Previous"),
    nextButton = new JButton("Next"), 
    closeButton = new JButton("Close");
  
  private JLabel indexLabel = new JLabel("");

  private CadsrModule cadsrModule;
  private FreestyleModule freestyleModule;

  private static String SEARCH = "SEARCH",
    PREVIOUS = "PREVIOUS",
    NEXT = "NEXT",
    CLOSE = "CLOSE";
    
  private java.util.List<AdminComponent> resultSet = new ArrayList<AdminComponent>();

  private String[] columnNames = {
    "LongName", "Preferred Name", "Public Id", "Version", 
    "Preferred Definition", "Context Name"
  };

  private int colWidth[] = {15, 15, 15, 15, 30, 15};

  private UserPreferences prefs = UserPreferences.getInstance();

  private int pageSize = prefs.getCadsrResultsPerPage();

  private int pageIndex = 0;
  
  private AdminComponent choiceAdminComponent = null;

  public static final int MODE_OC = 1;
  public static final int MODE_PROP = 2;
  public static final int MODE_VD = 3;
  public static final int MODE_DE = 4;
  public static final int MODE_CD = 5;
  
  private int mode;

  private static Logger logger = Logger.getLogger(CadsrDialog.class.getName());
  
  
  
  public CadsrDialog(int runMode)
  {
    super((JFrame)null, true);
    
    this.mode = runMode;

    switch (mode) {
    case MODE_OC:
      this.setTitle("Search for Object Class");
      break;
    case MODE_PROP:
      this.setTitle("Search for Property");
      break;
    case MODE_VD:
      this.setTitle("Search for Value Domain");
      break;
    case MODE_DE:
      this.setTitle("Search for Data Element");
      break;
    case MODE_CD:
      this.setTitle("Search for Conceptual Domain");
      break;
    }

    this.getContentPane().setLayout(new BorderLayout());

    String values[] = {LONG_NAME, PUBLIC_ID};
    searchSourceCombo = new JComboBox(values);
    JPanel searchPanel = new JPanel(new GridBagLayout());
    
   
    tableModel = new AbstractTableModel() {
        public String getColumnName(int col) {
          return columnNames[col].toString();
        }
        public int getRowCount() { 
          return (int)Math.min(resultSet.size(), pageSize); 
        }
        public int getColumnCount() { return columnNames.length; }
        public Object getValueAt(int row, int col) {
          row = row + pageSize * pageIndex;

          if(row >= resultSet.size())
            return "";

          AdminComponent res = resultSet.get(row);
          
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
            s = res.getContext().getName();
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
              choiceAdminComponent = (resultSet.get(pageIndex * pageSize + row));
              if(mode == MODE_DE) {
                DataElement de = (DataElement)choiceAdminComponent;
                // is this DE valid?
                // i.e does it have an Object Class and Property?
                // if not, throw error message
                if(de.getDataElementConcept().getObjectClass() == null || de.getDataElementConcept().getProperty() == null) {
                  JOptionPane.showMessageDialog
                    (null, PropertyAccessor.getProperty("de.invalid"), "Invalid Selection", JOptionPane.ERROR_MESSAGE);
                  choiceAdminComponent = null;
                  return;
                }
              }
              
              _this.setVisible(false);
            }
          }
        }
      });
    
    JScrollPane scrollPane = new JScrollPane(resultTable);
    
    Integer[] number = {new Integer(5),
                        new Integer(10),
                        new Integer(25),
                        new Integer(50),
                        new Integer(100)};
    
    numberOfResultsCombo = new JComboBox(number);
    numberOfResultsCombo.setSelectedItem(prefs.getCadsrResultsPerPage());
    
    if(mode == MODE_DE) {
      searchButton.setText("Freestyle Search");
      searchField.setColumns(20);
      insertInBag(searchPanel, searchLabel, 0, 0);
      insertInBag(searchPanel, searchField, 1, 0);
      insertInBag(searchPanel, searchButton, 4, 0);
    }
    
    else {
      insertInBag(searchPanel, searchLabel, 0, 0);
      insertInBag(searchPanel, searchField, 1, 0);
      insertInBag(searchPanel, whereToSearchLabel, 2, 0);
      insertInBag(searchPanel, searchSourceCombo, 3, 0);
      insertInBag(searchPanel, searchButton, 4, 0);
    }
    
    searchField.addKeyListener(this);

    searchButton.addActionListener(this);
    searchButton.addKeyListener(this);
    searchButton.setActionCommand(SEARCH);
    
    JPanel browsePanel = new JPanel();
    browsePanel.add(previousButton);
    browsePanel.add(indexLabel);
    browsePanel.add(nextButton);
    browsePanel.add(closeButton);
    browsePanel.add(numberOfResultsLabel);
    browsePanel.add(numberOfResultsCombo);
    
    numberOfResultsCombo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        JComboBox cb = (JComboBox)event.getSource();
        Integer selection = (Integer)cb.getSelectedItem();
        pageSize = selection.intValue();
        updateTable();
        updateIndexLabel();
        prefs.setCadsrResultsPerPage(selection.intValue());
      }
    });
    
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
    this.setSize(600,525);
    
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

    if(button.getActionCommand().equals(SEARCH)) {
      
      String selection = (String) searchSourceCombo.getSelectedItem();
      String text = searchField.getText();

      resultSet = new ArrayList();
      
      _this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      
      try {
        Map<String, Object> queryFields = new HashMap<String, Object>();
        if(selection.equals(LONG_NAME)) {
          queryFields.put(CadsrModule.LONG_NAME, text);
        } else if(selection.equals(PUBLIC_ID)) {
          queryFields.put(CadsrModule.PUBLIC_ID, new Long(text));
        }
        switch (mode) {
        case MODE_OC:
          resultSet.addAll(cadsrModule.findObjectClass(queryFields));
          break;
        case MODE_PROP:
          resultSet.addAll(cadsrModule.findProperty(queryFields));
          break;
        case MODE_DE:
//           resultSet.addAll(cadsrModule.findDataElement(queryFields));
          resultSet.addAll(freestyleModule.findDataElements(text));
          break;
        case MODE_VD:
          resultSet.addAll(cadsrModule.findValueDomain(queryFields));       
        }
      } catch (Exception e){
        logger.error("Error querying Cadsr " + e);
      } // end of try-catch
      
      _this.setCursor(Cursor.getDefaultCursor());

      // remove excluded contexts
      {
        List<String> excludeContext = Arrays.asList(PropertyAccessor.getProperty("vd.exclude.contexts").split(","));
        for(ListIterator<AdminComponent> it = resultSet.listIterator(); it.hasNext();) {
          AdminComponent ac = it.next();
          if(excludeContext.contains(ac.getContext().getName())) {
            it.remove();
          }
        }
      }

      pageIndex = 0;
      updateTable();
      
    }
    else if(button.getActionCommand().equals(PREVIOUS)) {
      pageIndex--;
      updateTable();
    } else if(button.getActionCommand().equals(NEXT)) {
      pageIndex++;
      updateTable();
    } else if(button.getActionCommand().equals(CLOSE)) {
      this.setVisible(false);
    }
    updateIndexLabel();
  }
  
  private void updateIndexLabel() {
    if(resultSet.size() == 0) {
      indexLabel.setText("");
    } else {
      StringBuilder sb = new StringBuilder();
      int start = pageSize * pageIndex;
      int end = (int)Math.min(resultSet.size(), start + pageSize); 
      sb.append(start);
      sb.append("-");
      sb.append(end);
      sb.append(" of " + resultSet.size());
      indexLabel.setText(sb.toString());
    }
    
  }

  private void updateTable() {
    tableModel.fireTableDataChanged();

    previousButton.setEnabled(pageIndex > 0);

    nextButton.setEnabled(resultSet.size() > (pageIndex * pageSize + pageSize));

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
 
  /**
   * IoC setter
   */
  public void setCadsrModule(CadsrModule module) {
    this.cadsrModule = module;
  }
 
  public void setFreestyleModule(FreestyleModule freestyleModule) {
    this.freestyleModule = freestyleModule;
  }

  public static void main(String[] args) 
  {
        CadsrDialog dialog = new CadsrDialog(CadsrDialog.MODE_VD);

    dialog.setCadsrModule(new CadsrPublicApiModule("http://cabio.nci.nih.gov/cacore31/http/remoteService"));
    dialog.setVisible(true);
  }
}