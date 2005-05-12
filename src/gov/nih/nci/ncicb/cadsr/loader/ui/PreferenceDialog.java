package gov.nih.nci.ncicb.cadsr.loader.ui;
import gov.nih.nci.ncicb.cadsr.loader.util.BeansAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PreferenceDialog extends JDialog implements ActionListener
{
  private JCheckBox associationBox = new JCheckBox("View Associations in Class Tree");
  private JButton apply = new JButton("Apply");
  private JButton cancel = new JButton("Cancel");
  
  public PreferenceDialog(JFrame owner)
  {
    super(owner, "Preferences");
    JPanel southPanel = new JPanel();
    southPanel.add(apply);
    southPanel.add(cancel);
    
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(associationBox);
    this.getContentPane().add(southPanel,BorderLayout.SOUTH);
    this.setSize(200,100);
    
    apply.addActionListener(this);
    
    UserPreferences prefs = BeansAccessor.getUserPreferences();
    if(prefs.getViewAssociationType().equalsIgnoreCase("true"))
      associationBox.setSelected(true);
    else
      associationBox.setSelected(false);
  }
  
  public void actionPerformed(ActionEvent event) 
  {
    UserPreferences prefs = BeansAccessor.getUserPreferences();
    if(associationBox.isSelected())
      prefs.setViewAssociationType("true");
    else
      prefs.setViewAssociationType("false");
    

  }
}