package gov.nih.nci.ncicb.cadsr.loader.ui;

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

  private ConceptInheritanceViewPanel viewPanel;
  private JScrollPane mainScrollPane;

  public ConceptInheritanceDialog(ConceptInheritanceViewPanel viewPanel) {
    super((JFrame)null, true);

    this.viewPanel = viewPanel;
    
    initUI();

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

    okButton.addActionListener(this);
    cancelButton.addActionListener(this);

  }

  public void actionPerformed(ActionEvent event) {
    this.dispose();
  }
}