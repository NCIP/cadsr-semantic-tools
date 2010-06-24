package gov.nih.nci.ncicb.cadsr.loader.ui;


import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;

import java.awt.BorderLayout;

import java.awt.event.ItemEvent;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ItemListener;

import javax.swing.*;

public class ExpertsCacheDialog extends JDialog implements ActionListener, ItemListener{

  private JPanel mainPanel = null;
  
  private JCheckBox enableDisableFieldsCheckbox = null;
  private JLabel mneLabel = null;
  private JLabel ttlLabel = null;
  private JLabel hoursLabel = null;
  private JLabel daysLabel = null;
  private JLabel messageLabel = null;
  private JScrollPane mneScrollPane = null;
  private JComboBox hoursCombobox = null;
  private JComboBox daysCombobox = null;
  private JButton clearCacheButton = null;
  private JButton setCacheButton = null;
  private JButton closeButton = null;
  private JTextArea mneTextArea = null;
  private final String CLEAR_CACHE = "CLEAR_CACHE";
  private final String SET_CACHE = "SET_CACHE";
  private final String CLOSE = "CLOSE";

  private Integer[] hours = {new Integer(0), new Integer(1), new Integer(2), new Integer(3), new Integer(4), new Integer(5),
        new Integer(6), new Integer(7), new Integer(8), new Integer(9), new Integer(10), new Integer(11),
        new Integer(12), new Integer(13), new Integer(14), new Integer(15), new Integer(16), new Integer(17),
        new Integer(18), new Integer(19), new Integer(20), new Integer(21), new Integer(22), new Integer(23)};
  private Integer[] days = {new Integer(0), new Integer(1), new Integer(2), new Integer(3), new Integer(4), new Integer(5),
        new Integer(6), new Integer(7), new Integer(8), new Integer(9), new Integer(10), new Integer(11),
        new Integer(12), new Integer(13), new Integer(14)};

  private ValidationChoicePanel validationPanel;
  
  public ExpertsCacheDialog() {
    
    super((JFrame)null, true);
    this.setLayout(new BorderLayout());
    
    setTitle(PropertyAccessor.getProperty("expertsCache.title"));
    
    mainPanel = new JPanel(new GridBagLayout());
    messageLabel = new JLabel(" ");
    
    enableDisableFieldsCheckbox = new JCheckBox("Enable or Diable Cache");
    enableDisableFieldsCheckbox.addItemListener(this);

    mneLabel = new JLabel("Max Number of Elements");
    mneTextArea = new JTextArea();
    mneTextArea.setLineWrap(true);
    mneTextArea.setWrapStyleWord(true);
    mneScrollPane = new JScrollPane(mneTextArea);
    mneScrollPane.setPreferredSize(new Dimension(200, 100));
    mneScrollPane.getVerticalScrollBar().setUnitIncrement(500);

    ttlLabel = new JLabel("Time to Live");
    hoursLabel = new JLabel("Hours");
    hoursCombobox = new JComboBox(hours);
    daysLabel = new JLabel("Days");
    daysCombobox = new JComboBox(days);
    JPanel ttlPanel = new JPanel();
    ttlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    ttlPanel.add(hoursCombobox);
    ttlPanel.add(hoursLabel);
    ttlPanel.add(daysCombobox);
    ttlPanel.add(daysLabel);
    
    clearCacheButton = new JButton("Clear Cache");
    clearCacheButton.setActionCommand(CLEAR_CACHE);
    clearCacheButton.addActionListener(this);

    setCacheButton = new JButton("Set Cache Parameters");
    setCacheButton.setActionCommand(SET_CACHE);
    setCacheButton.addActionListener(this);

    closeButton = new JButton("Close");
    closeButton.setActionCommand(CLOSE);
    closeButton.addActionListener(this);

//     noValidationBox.addActionListener(new ActionListener() {
//         public void actionPerformed(ActionEvent evt) {
//           UserSelections.getInstance().setProperty("NO_VALIDATION", new Boolean(noValidationBox.isSelected()));
//         }
//       });

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    buttonPanel.add(setCacheButton);
    buttonPanel.add(new JLabel("    "));
    buttonPanel.add(closeButton);

    UIUtil.insertInBag(mainPanel, messageLabel, 0, 0);
    
    UIUtil.insertInBag(mainPanel, enableDisableFieldsCheckbox, 0, 1);

    UIUtil.insertInBag(mainPanel, mneLabel, 0, 4);
    UIUtil.insertInBag(mainPanel, mneScrollPane, 1, 4, 3, 1); 
    
    UIUtil.insertInBag(mainPanel, ttlLabel, 0, 6);
    UIUtil.insertInBag(mainPanel, ttlPanel, 1, 6);
    
    UIUtil.insertInBag(mainPanel, clearCacheButton, 0, 8);
    UIUtil.insertInBag(mainPanel, buttonPanel, 1, 8);

    validationPanel = new ValidationChoicePanel();
    UIUtil.insertInBag(mainPanel, validationPanel, 0, 9);

    this.add(mainPanel, BorderLayout.CENTER);
    this.setSize(800, 700);
  }

  public void actionPerformed(ActionEvent ae) {
    if(ae.getActionCommand().equals(CLEAR_CACHE)){
        messageLabel.setForeground(Color.RED);
        messageLabel.setText("<html><B>Cache is Cleared</B><html>");
    } else if(ae.getActionCommand().equals(SET_CACHE)){
        messageLabel.setForeground(Color.RED);
        messageLabel.setText("<html><B>Cache is Set</B><html>");
    } else {
//         this.setVisible(false);
      UserSelections.getInstance().setProperty("VALIDATORS_CHOICES", validationPanel.getValidatorChoices());
      this.dispose();
    }
  }

    public void itemStateChanged(ItemEvent ie) {
        if(enableDisableFieldsCheckbox.isSelected()){
            mneTextArea.setEnabled(true);
            hoursCombobox.setEnabled(true);
            daysCombobox.setEnabled(true);
            setCacheButton.setEnabled(true);
        }
        else{
            mneTextArea.setEnabled(false);
            hoursCombobox.setEnabled(false);
            daysCombobox.setEnabled(false);
            setCacheButton.setEnabled(false);
        }
    }

  public static void main(String[] args) {
    ExpertsCacheDialog dialog = new ExpertsCacheDialog();
    
    dialog.setVisible(true);
  }

}
