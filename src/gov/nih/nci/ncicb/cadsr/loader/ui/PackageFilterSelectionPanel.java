package gov.nih.nci.ncicb.cadsr.loader.ui;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;

import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.util.*;
import javax.swing.ToolTipManager;

public class PackageFilterSelectionPanel extends JPanel
  implements ActionListener
{
  private JLabel packageLabel = new JLabel("Package Name");
  private JLabel aliasLabel = new JLabel("Alias Name");
  private JTextField packagePathField = new JTextField(23);
  private JButton addButton = new JButton("Add");
  private JList packageList;
  private JButton removeButton = new JButton(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("delete-x.gif")));
  private DefaultListModel packageListModel;
  
  private Map packageFilter = new HashMap();
  
  private static final String ADD = "Add";
  private static final String REMOVE = "Remove";
  
  public PackageFilterSelectionPanel()
  {
    packageListModel = new DefaultListModel();
    packageList = new JList(packageListModel);
    
    addButton.setActionCommand(ADD);
    removeButton.setActionCommand(REMOVE);
    
    addButton.addActionListener(this);
    removeButton.addActionListener(this);
    init();
  }
  
  public void init() 
  {
    initUI();
  }
  
  public void initUI() 
  {
    this.setLayout(new BorderLayout());
    
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    infoPanel.setBackground(Color.WHITE);
    infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    infoPanel.add(new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("siw-logo3_2.gif"))));
    JLabel infoLabel = new JLabel("<html>Enter the packages that you would like to filter by. </html>");
    JLabel explainLabel = new JLabel("<html><u color=BLUE>Explain this</u></html>");
    ToolTipManager.sharedInstance().registerComponent(explainLabel);
    ToolTipManager.sharedInstance().setDismissDelay(3600000);
    explainLabel.setToolTipText("<html>Each model is divided into packages. This  " +
    "<br>panel lets you filter by certain packages. " +
    "<br>To filter by a certain package " +
    "<br>you must enter the name of the package " +
    "<br>in the field and then click the add button.  By " +
    "<br>clicking add you put that package into the " +
    "<br>filter list.  Only the packages in the filter list will " +
    "<br>have their items processed by the Semantic " +
    "<br>Connector. To remove a package from the list" +
    "<br>click on the name of the package you want" +
    "<br>to remove and click on the X button.  If the " +
    "<br>list is empty then all packages will be " +
    "<br>processed (no filtering will occur). </html>");
    infoPanel.add(infoLabel);
    infoPanel.add(explainLabel);
    
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new GridBagLayout());
    
    JScrollPane scrollPane = new JScrollPane(packageList);
    
    removeButton.setPreferredSize(new Dimension(28,29));

    packagePathField.setFont(new Font("Serif", Font.PLAIN, 12));
    packageList.setFont(new Font("Serif", Font.PLAIN, 12));

    UIUtil.insertInBag(centerPanel, packageLabel, 0, 0);
    UIUtil.insertInBag(centerPanel, packagePathField, 0, 1);
    UIUtil.insertInBag(centerPanel, addButton, 2, 1);
    UIUtil.insertInBag(centerPanel, scrollPane, 0, 2, 2, 1);
    UIUtil.insertInBag(centerPanel, removeButton, 2, 2);
    
    this.setLayout(new BorderLayout());
    this.add(infoPanel, BorderLayout.NORTH);
    this.add(centerPanel, BorderLayout.CENTER);
    
    
  }
  
  public Map getPackageFilter()
  {
    return packageFilter;
  }
  
  public void actionPerformed(ActionEvent event) 
  {
    JButton button = (JButton)event.getSource();
    
    if(button.getActionCommand().equals(ADD)) {
      packageListModel.addElement(packagePathField.getText());
      if(!packageFilter.containsKey(packagePathField.getText())) 
      {
        packageFilter.put(packagePathField.getText(), "alias");  
      }
    }
    if(button.getActionCommand().equals(REMOVE)) {
      //packageListModel.removeElement(packagePathField.getText() +":"+ aliasPathField.getText());
      if(packageList.getSelectedValue() != null) {
      String selected = (String)packageList.getSelectedValue();
      //String selectedArray[] = selected.split(":");
      packageListModel.removeElement(selected);
      if(packageFilter.containsKey(selected))
        packageFilter.remove(selected);
      }
    }
  }
  
}