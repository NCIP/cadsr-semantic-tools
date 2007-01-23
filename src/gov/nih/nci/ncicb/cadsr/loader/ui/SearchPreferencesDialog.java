package gov.nih.nci.ncicb.cadsr.loader.ui;
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SearchPreferencesDialog extends JDialog 
  implements ActionListener 
{
  private DefaultListModel listModel;
  private JList registrationStatusesList;
  private JCheckBox workflowStatusCheckBox;
  private JButton okButton = new JButton("OK");
  private JButton cancelButton = new JButton("Cancel");
  private static String OK = "OK";
  private static String CANCEL = "CANCEL";
  private UserSelections selections = UserSelections.getInstance();

  public SearchPreferencesDialog()
  {
    super((JFrame)null, true);
    initUI();
  }
  
  public void initUI() {
    this.setTitle("Search Preferences");
  
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new GridBagLayout());
    
    listModel = new DefaultListModel();
    String[] statuses = PropertyAccessor.getProperty("registration.statuses").split(",");
    for(String s : statuses)
      listModel.addElement(s);
      
    registrationStatusesList = new JList(listModel);
    registrationStatusesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    
    //get default excluded registration statuses from userSelections first
    Object[] selectionsArray = 
      (Object[])selections.getProperty("exclude.registration.statuses");
    Object[] listArray = listModel.toArray();
    int[] indices;
    if(selectionsArray != null) {
      indices = new int[selectionsArray.length];
      for(int i=0; i < selectionsArray.length; i++)
        for(int j=0; j < listArray.length; j++)
          if(((String)listArray[j]).equals((String)selectionsArray[i]))
            indices[i] = j;
    
      registrationStatusesList.setSelectedIndices(indices);
    }
    //if no userSelection exists then get excluded registration statuses from
    //resource.properties
    else {
    //set which registration Statuses are excluded by default
    String[] defaultStatuses = PropertyAccessor.getProperty("default.excluded.registration.statuses").split(",");
    
    indices = new int[defaultStatuses.length];
//    for(int m=0; m < indices.length; m++)
    for(int i=0; i < defaultStatuses.length; i++)
      for(int j=0; j < listArray.length; j++)
        if(((String)listArray[j]).equals(defaultStatuses[i]))
          indices[i] = j;
    
    registrationStatusesList.setSelectedIndices(indices);
    selections.setProperty("exclude.registration.statuses",
      registrationStatusesList.getSelectedValues());
      
    }
//    for(String s : defaultStatuses)
//      registrationStatusesList.setSelectedValue(s, true);
      
    JScrollPane listScrollPane = new JScrollPane(registrationStatusesList);
    listScrollPane
      .setVerticalScrollBarPolicy
      (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    listScrollPane.setPreferredSize(new Dimension(300, 125));

    Boolean excludeRetiredWF = 
      (Boolean)selections.getProperty("exclude.retired.workflow.status");

    workflowStatusCheckBox = new JCheckBox("");
    
    if(excludeRetiredWF != null)
      workflowStatusCheckBox.setSelected(excludeRetiredWF);
    else 
      workflowStatusCheckBox.setSelected(false);
    
    insertInBag(centerPanel, new JLabel("Exclude Registration Status(es)"), 0,0);
    insertInBag(centerPanel, listScrollPane, 0, 1, 2, 1);
    insertInBag(centerPanel, new JLabel("<html>Exclude Retired Workflow Status<font size=1><br>CMTE Approved<br>CMTE SUBMTD<br>CMTE SUBMTD USED<br>RETIRED ARCHIVED<br>RETIRED PHASED OUT<br>RETIRED WITHDRAWN</font></html>"), 0, 2);
    insertInBag(centerPanel, workflowStatusCheckBox, 1, 2);
    
    okButton.setActionCommand(OK);
    cancelButton.setActionCommand(CANCEL);
    okButton.addActionListener(this);
    cancelButton.addActionListener(this);
    
    JPanel southPanel = new JPanel();
    southPanel.add(okButton);
    southPanel.add(cancelButton);
    
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(centerPanel, BorderLayout.CENTER);
    this.getContentPane().add(southPanel, BorderLayout.SOUTH);
    this.setSize(450,500);
  }
  
  public void actionPerformed(ActionEvent evt) 
  {
    JButton button = (JButton)evt.getSource();
    if(button.getActionCommand().equals(OK)) {
      
      selections.setProperty("exclude.registration.statuses", 
        registrationStatusesList.getSelectedValues());
      selections.setProperty("exclude.retired.workflow.status", 
        workflowStatusCheckBox.isSelected());
      this.dispose();
    }
    if(button.getActionCommand().equals(CANCEL)) {
      this.dispose();
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

  public static void main(String[] args) 
  { 
    SearchPreferencesDialog spd = new SearchPreferencesDialog();
    spd.setVisible(true);
  }
  
}