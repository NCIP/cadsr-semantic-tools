package gov.nih.nci.ncicb.cadsr.loader.ui;
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

public class DEPanel extends JPanel
{
  private JLabel publicIDLabel = new JLabel("Property ID:");
  private JTextField publicIDField = new JTextField(10);
  private JLabel versionLabel = new JLabel("Property Version:");
  private JTextField versionField = new JTextField(10);
  private JLabel vdPublicIDLabel = new JLabel("Value Domain ID:");
  private JTextField vdPublicIDField = new JTextField(10);
  private JLabel vdVersionLabel = new JLabel("Value Domain Version:");
  private JTextField vdVersionField = new JTextField(10);
  private JButton propButton = new JButton("Search Property");
  private JButton vdButton = new JButton("Search Value Domain");
  
  private static final String PROP = "PROP";
  
  public DEPanel()
  {
    this.setLayout(new BorderLayout());
    JPanel mainPanel = new JPanel(new GridBagLayout());
    insertInBag(mainPanel, publicIDLabel, 0, 0);
    insertInBag(mainPanel, publicIDField, 1, 0);
    insertInBag(mainPanel, versionLabel, 0, 1);
    insertInBag(mainPanel, versionField, 1, 1);
//    insertInBag(mainPanel, vdPublicIDLabel, 0, 2);
//    insertInBag(mainPanel, vdPublicIDField, 1, 2);
//    insertInBag(mainPanel, vdVersionLabel, 0, 3);
//    insertInBag(mainPanel, vdVersionField, 1, 3);
    insertInBag(mainPanel, propButton, 2, 0);
//    insertInBag(mainPanel, vdButton, 2 , 2);
//    
    //this.setLayout(new BorderLayout());
    this.add(mainPanel);
    this.setSize(300, 300);
    
    propButton.setActionCommand(PROP);
    
    
    propButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
      JButton button = (JButton)event.getSource();
      if(button.getActionCommand().equals(PROP)) {
        CadsrDialog cd = new CadsrDialog(CadsrDialog.MODE_PROP);
        cd.setVisible(true);
      }

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
    DEPanel dePanel = new DEPanel();
    dePanel.setVisible(true);
    frame.add(dePanel);
    frame.setVisible(true);
    frame.setSize(450, 350);
    System.out.println("Is the Panel showing up?");
  }
}