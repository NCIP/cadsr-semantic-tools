package gov.nih.nci.ncicb.cadsr.loader.ui;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsResult;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.*;
import javax.swing.*;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;



public class ValidateConceptsDialog extends JDialog 
  implements ListSelectionListener
{
  private EvsModule module = new EvsModule();
  private Map<Concept, Concept> errorList = new HashMap<Concept, Concept>();
//  private List<ObjectClass> ocList = new ArrayList();
//  private List<DataElementConcept> decList = new ArrayList();
  private List<AcListElementWrapper> wrapperList = 
    new ArrayList<AcListElementWrapper>();
  
  private JSplitPane jSplitPane1 = new JSplitPane();
  
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JEditorPane editorPane1 = new JEditorPane("text/html", "");
  private JEditorPane editorPane2 = new JEditorPane("text/html", "");
    
  private DefaultListModel listModel = new DefaultListModel();
  private JList list;
  private JScrollPane scrollPane;
  
  private AbstractTableModel tableModel = null;
  private JTable resultTable = null;
  
  private ProgressPanel progressPanel = new ProgressPanel(100);
  
  private String[] columnNames = {
    "Element Concept", "EVS Concept"
  };

  private int colWidth[] = {30, 30};

  private static int PAGE_SIZE = 5;

  private int pageIndex = 0;
  
  private java.util.List<AdminComponent> resultSet = new ArrayList<AdminComponent>();
  
  private AcListElementWrapper value;
  
  public ValidateConceptsDialog(JFrame owner)
  {
    super(owner, "Validate Concepts");
    this.getContentPane().setLayout(new BorderLayout());


    SwingWorker worker = new SwingWorker() {
      public Object construct() {    
        List<Concept> concepts = ElementsLists.getInstance().
            getElements(DomainObjectFactory.newConcept());
        
        ProgressEvent event = new ProgressEvent();
        event.setGoal(concepts.size() + 1);
        event.setMessage("Validating Concepts");
        progressPanel.newProgressEvent(event);
        
        int pStatus = 0;
        for(Concept con : concepts) 
        {
          event = new ProgressEvent();
          event.setMessage("Validating " + con.getLongName());
          event.setStatus(pStatus++);
          progressPanel.newProgressEvent(event);
          
          //EvsResult result = null;
          
          //if(con.getPreferredName() != null) {
            EvsResult result = module.findByConceptCode(con.getPreferredName(), false);
          //}
          if(result != null) 
          {
            if(!con.getLongName().equals(result.getConcept().getLongName())
            || !con.getPreferredDefinition().equals(result.getConcept().getPreferredDefinition()))
              errorList.put(con, result.getConcept());             
          }
        }
        
        List<ObjectClass> ocs = ElementsLists.getInstance().
            getElements(DomainObjectFactory.newObjectClass());
        
        List<DataElementConcept> decs = ElementsLists.getInstance().
            getElements(DomainObjectFactory.newDataElementConcept());
        
        for(ObjectClass oc : ocs)
          for(Concept concept : errorList.keySet()) {
            String temp = oc.getPreferredName();
            String split[] = temp.split(":");
            for(int i = 0; i < split.length; i++)
              if(split[i].equals(concept.getPreferredName()))
                wrapperList.add(new AcListElementWrapper(oc, concept));
          }
        
        for(DataElementConcept dec : decs)
          for(Concept concept : errorList.keySet()) {
            String temp = dec.getProperty().getPreferredName();
            String split[] = temp.split(":");
            for(int i = 0; i < split.length; i++)
              if(split[i].equals(concept.getPreferredName()))
                wrapperList.add(new AcListElementWrapper(dec, concept));
          }
        
        event = new ProgressEvent();
        event.setMessage("Done ");
        event.setStatus(100);
        event.setGoal(100);
        progressPanel.newProgressEvent(event);
         
        for(AcListElementWrapper val : wrapperList)
          listModel.addElement(val);
          
          return null;
      }
    };
    worker.start();
        
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(list);        
        
        list.addListSelectionListener(this);
        list.setCellRenderer(new MyCellRenderer());
        
        JPanel mainPanel = new JPanel();
        JLabel instructions = new JLabel("Review the concepts' information");
        instructions.setBounds(new Rectangle(110, 0, 220, 20));
        mainPanel.setSize(new Dimension(400, 340));
        mainPanel.setLayout(null);
        list.setBounds(new Rectangle(10, 30, 140, 300));
        jSplitPane1.setBounds(new Rectangle(160, 30, 220, 300));
        jSplitPane1.setDividerLocation(135);
        jSplitPane1.setBackground(Color.WHITE);
        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        
        jLabel1.setBackground(Color.WHITE);
        jLabel1.setBorder(javax.swing.BorderFactory.createTitledBorder("EVS Concept"));
        jLabel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Element Concept"));

        editorPane1.setEditable(false);
        editorPane2.setEditable(false);
        
        JScrollPane editorScrollPane = new JScrollPane(editorPane1);
        JScrollPane editorScrollPane2 = new JScrollPane(editorPane2);
        jSplitPane1.add(editorScrollPane, JSplitPane.RIGHT);
        jSplitPane1.add(editorScrollPane2, JSplitPane.LEFT);
        
        mainPanel.add(jSplitPane1, null);
        mainPanel.add(list, null);
        mainPanel.add(instructions);
        
        
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        
//        this.getContentPane().add(tablePanel, BorderLayout.CENTER);
//        this.getContentPane().add(listPanel, BorderLayout.WEST);
//        
        this.getContentPane().add(new JLabel("Select an element from the list to view the concepts' information"));
        this.setSize(400, 410);
        this.getContentPane().add(progressPanel, BorderLayout.SOUTH);
        this.setResizable(false);
        UIUtil.putToCenter(this);
  }
  
  public void valueChanged(ListSelectionEvent e) 
  {
    if (e.getValueIsAdjusting() == false) {
      if (list.getSelectedIndex() != -1) {
        value = (AcListElementWrapper)list.getSelectedValue(); 
        editorPane1.setText(getConceptHtml(value.getConcept()));
        editorPane2.setText(getConceptHtml(value.getConcept()));
        //tableModel.fireTableDataChanged();
   
      }
    }
  }
  
  private String getConceptHtml(Concept con) 
  {
    StringBuilder sb = new StringBuilder();
    sb.append("<html><body>");
    sb.append("<b>Code: </b>" + con.getPreferredName() + "<br>");
    sb.append("<b>Name: </b>" + con.getLongName() + "<br>");
    sb.append("<b>Definition: </b>" + con.getPreferredDefinition() + "<br>");
    sb.append("</body></html>");
    
    return sb.toString();
  }
  
  class AcListElementWrapper<T extends AdminComponent>
{
  private T ac;
  private Concept con;
  AcListElementWrapper(T ac, Concept con) 
  {
    this.ac = ac;
    this.con = con;
  }
  T getAc() 
  {
    return ac;
  }
  
  Concept getConcept() 
  {
    return con;
  }
  
}
class MyCellRenderer extends JLabel implements ListCellRenderer {
  public MyCellRenderer() {
         setOpaque(true);
     }
  public Component getListCellRendererComponent(
         JList list,
         Object value,
         int index,
         boolean isSelected,
         boolean cellHasFocus)
     {
  
        AcListElementWrapper acW = (AcListElementWrapper)value;
        
        int ind = acW.getAc().getLongName().lastIndexOf(".");
        String className = acW.getAc().getLongName().substring(ind + 1);
        setText(className);
        if (isSelected) {
             setBackground(list.getSelectionBackground());
               setForeground(list.getSelectionForeground());
           }
         else {
               setBackground(list.getBackground());
               setForeground(list.getForeground());
           }
        setEnabled(list.isEnabled());
        return this;
     }

}

}


