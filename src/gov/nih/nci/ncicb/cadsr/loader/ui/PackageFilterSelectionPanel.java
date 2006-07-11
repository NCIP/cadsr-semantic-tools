package gov.nih.nci.ncicb.cadsr.loader.ui;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
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

public class PackageFilterSelectionPanel extends JPanel
  implements ActionListener
{
  private JLabel packageLabel = new JLabel("Package Name");
  private JLabel aliasLabel = new JLabel("Alias Name");
  private JTextField packagePathField = new JTextField(32);
  private JTextField aliasPathField = new JTextField(10);
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
    
    JLabel infoLabel = new JLabel("<html>Enter the packages that you would like to filter by</html>");
    infoPanel.add(infoLabel);
    
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new GridBagLayout());
    
    JScrollPane scrollPane = new JScrollPane(packageList);
    
    removeButton.setPreferredSize(new Dimension(28,29));
    
    insertInBag(centerPanel, packageLabel, 0, 0);
    //insertInBag(centerPanel, aliasLabel, 1, 0);
    insertInBag(centerPanel, packagePathField, 0, 1);
    //insertInBag(centerPanel, aliasPathField, 1, 1);
    insertInBag(centerPanel, addButton, 2, 1);
    insertInBag(centerPanel, scrollPane, 0, 2, 2, 1);
    insertInBag(centerPanel, removeButton, 2, 2);
    
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
  
  private void insertInBag(JPanel bagComp, Component comp, int x, int y) {

    insertInBag(bagComp, comp, x, y, 1, 1);

  }

  private void insertInBag(JPanel bagComp, Component comp, int x, int y, int width, int height) {
    JPanel p = new JPanel();
    p.add(comp);

    bagComp.add(p, new GridBagConstraints(x, y, width, height, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }
}