/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ConceptInheritanceDialog extends JDialog implements ActionListener {
  
  private JButton okButton = new JButton("Proceed"),
    cancelButton = new JButton("Cancel");

  private String OK_COMMAND = "OK",
    CANCEL_COMMAND = "CANCEL";

  private ConceptInheritanceViewPanel viewPanel;
  private JScrollPane mainScrollPane;

  public ConceptInheritanceDialog(ConceptInheritanceViewPanel viewPanel) {
    super((JFrame)null, true);

    this.viewPanel = viewPanel;
    
    initUI();
  }

  public ConceptDerivationRule getConceptDerivationRule() {
    return viewPanel.getConceptDerivationRule();
  }
  
  private void initUI() {
    this.setSize(400, 500);

    setLayout(new BorderLayout());
    
    mainScrollPane = new JScrollPane(viewPanel);
    
    add(mainScrollPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(okButton);
    buttonPanel.add(cancelButton);

    add(buttonPanel, BorderLayout.SOUTH);

    okButton.setActionCommand(OK_COMMAND);
    cancelButton.setActionCommand(CANCEL_COMMAND);

    okButton.addActionListener(this);
    cancelButton.addActionListener(this);
  }

  public void actionPerformed(ActionEvent event) {
    if(event.getActionCommand().equals(OK_COMMAND)) {
      viewPanel.applyInheritance();
    }

    this.dispose();
  }
}