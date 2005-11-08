package gov.nih.nci.ncicb.cadsr.loader.ui;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OCPanel extends JPanel
{
  private OCPanel _this = this;

  private JLabel publicIdLabel = new JLabel("Public ID:");
  private JTextField publicIdField = new JTextField(10);
  private JLabel versionLabel = new JLabel("Version:");
  private JTextField versionField = new JTextField(10);
  private JButton cadsrButton = new JButton("Search");
  
  private CadsrDialog cadsrDialog;
  
  public OCPanel()
  {
    this.setLayout(new BorderLayout());
    JPanel mainPanel = new JPanel(new GridBagLayout());
    insertInBag(mainPanel, publicIdLabel, 0, 0);
    insertInBag(mainPanel, publicIdField, 1, 0);
    insertInBag(mainPanel, versionLabel, 0, 1);
    insertInBag(mainPanel, versionField, 1, 1);
    insertInBag(mainPanel, cadsrButton, 2, 0);
    
    //this.setLayout(new BorderLayout());
    this.add(mainPanel);
    this.setSize(300, 300);
    
    cadsrButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
      if(cadsrDialog == null)
        cadsrDialog = new CadsrDialog(CadsrDialog.MODE_OC);
      
      AdminComponent ac = cadsrDialog.getAdminComponent();
      //_this.updateUI();
      if(ac != null) {
        publicIdField.setText(ac.getPublicId());
        versionField.setText(ac.getVersion().toString());
      }
      cadsrDialog.setVisible(true);
      _this.updateUI();
      }
    });
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
    JFrame frame = new JFrame();
    OCPanel ocPanel = new OCPanel();
    ocPanel.setVisible(true);
    frame.add(ocPanel);
    frame.setVisible(true);
    frame.setSize(450, 350);
    System.out.println("Is the Panel showing up?");
  }
}