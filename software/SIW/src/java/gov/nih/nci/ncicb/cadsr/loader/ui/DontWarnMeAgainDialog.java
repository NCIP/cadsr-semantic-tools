/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.util.*;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A dialog that says "Don't warn me about this again"
 *
 */
public class DontWarnMeAgainDialog extends JDialog {

  private JDialog _this = this;

  private JCheckBox dontAskMeCb = new JCheckBox(PropertyAccessor.getProperty("dont.warn.me.again"));
  
  private JLabel warningLabel;
  private String property = null;

  private UserPreferences userPrefs = UserPreferences.getInstance();

  public DontWarnMeAgainDialog(String property) {
    this.property = property;
    initUI();
  }

  public void initUI() {
    this.getContentPane().setLayout(new BorderLayout());

    JPanel contentPanel = new JPanel(new BorderLayout());

    warningLabel = new JLabel(PropertyAccessor.getProperty(property));
    contentPanel.add(warningLabel, BorderLayout.CENTER);

    dontAskMeCb.setSelected(userPrefs.getBoolean(property));
    contentPanel.add(dontAskMeCb, BorderLayout.SOUTH);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    
    JButton okButton = new JButton("OK");
    buttonPanel.add(okButton);

    okButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          userPrefs.setBoolean(property, dontAskMeCb.isSelected());
          _this.dispose();
        }
      });

    this.getContentPane().add(contentPanel, BorderLayout.CENTER);
    this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

    this.setTitle("Warning");

    this.setSize(400, 200);

    this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    UIUtil.putToCenter(this);
    this.setVisible(true);


  }
  

  public static void main(String[] args) {
    DontWarnMeAgainDialog dialog = new DontWarnMeAgainDialog("de.over.vd.mapping.warning");
  }

}