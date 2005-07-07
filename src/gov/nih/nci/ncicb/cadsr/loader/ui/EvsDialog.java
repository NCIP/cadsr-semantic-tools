package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

public class EvsDialog extends JDialog implements ActionListener
{
  private JLabel searchLabel = new JLabel("Search String ");
  private JTextField searchField = new JTextField(10);
  private JLabel whereToSearchLabel = new JLabel("Search In");
  private JComboBox searchSourceCombo;

  private JButton searchButton = new JButton("Search");

  private AbstractTableModel tableModel = null;
  private JTable resultTable = null;

  private static final String SYNONYMS = "Synonyms";
  private static final String CONCEPT_CODE = "Concept Code";

  private JButton previousButton = new JButton("Previous"),
    nextButton = new JButton("Next");

  private static String SEARCH = "SEARCH",
    PREVIOUS = "PREVIOUS",
    NEXT = "NEXT";

  private java.util.List<EvsResult> resultSet = new ArrayList();

  private String[] columnNames = {
    "Code", "Name", "Synonyms", "Definition", "Source"
  };

  private static int PAGE_SIZE = 10;

  private int pageIndex = 0;

  public EvsDialog()
  {
    super();
    this.setTitle("Search Thesaurus");

    this.getContentPane().setLayout(new BorderLayout());

    String values[] = {SYNONYMS, CONCEPT_CODE};
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

          EvsResult res = resultSet.get(row);

          String s = "";
          switch (col) {
          case 0:
            s = res.getConcept().getPreferredName();
            break;
          case 1:
            s = res.getConcept().getLongName();
            break;
          case 2: 
            for(int i = 0; i<res.getSynonyms().length; i++)
              s += res.getSynonyms()[i];
            break;
          case 3:
            s = res.getConcept().getPreferredDefinition();
            break;
          case 4:
            s = res.getConcept().getDefinitionSource();
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

//     resultTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
    
    JScrollPane scrollPane = new JScrollPane(resultTable);

    insertInBag(searchPanel,searchLabel,0,0);
    insertInBag(searchPanel,searchField, 1,0);
    insertInBag(searchPanel,whereToSearchLabel, 2,0);
    insertInBag(searchPanel,searchSourceCombo, 3, 0);
    insertInBag(searchPanel,searchButton,4,0);

    searchButton.addActionListener(this);
    searchButton.setActionCommand(SEARCH);

    JPanel browsePanel = new JPanel();
    browsePanel.add(previousButton);
    browsePanel.add(nextButton);

    previousButton.setActionCommand(PREVIOUS);
    nextButton.setActionCommand(NEXT);
    previousButton.setEnabled(false);
    nextButton.setEnabled(false);
    previousButton.addActionListener(this);
    nextButton.addActionListener(this);

    this.getContentPane().add(searchPanel, BorderLayout.NORTH)
;
    this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    this.getContentPane().add(browsePanel, BorderLayout.SOUTH);

    this.setSize(500,600);
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

      EvsModule module = new EvsModule();

      resultSet = new ArrayList();

      if(selection.equals(CONCEPT_CODE)) {
        resultSet.add(module.findByConceptCode(text));
      }
      if(selection.equals(SYNONYMS)) {
        resultSet.addAll(module.findBySynonym(text));
      }
    
      updateTable();

    } else if(button.getActionCommand().equals(PREVIOUS)) {
      pageIndex--;
      updateTable();
    } else if(button.getActionCommand().equals(NEXT)) {
      pageIndex++;
      updateTable();
    }


  }

  private void updateTable() {
    tableModel.fireTableDataChanged();

    previousButton.setEnabled(pageIndex > 0);

    nextButton.setEnabled(resultSet.size() > (pageIndex * PAGE_SIZE + PAGE_SIZE));

  }
  
  public static void main(String[] args) {
    EvsDialog dialog = new EvsDialog();
    dialog.setVisible(true);
  }

}

