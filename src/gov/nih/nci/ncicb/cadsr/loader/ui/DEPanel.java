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

  private JButton searchDeButton = new JButton("Search Data Element");

  private JLabel deLongNameTitleLabel = new JLabel("Data Element Long Name"),
    deLongNameValueLabel = new JLabel(),
    deIdTitleLabel = new JLabel("Public ID / Version"),
    deIdValueLabel = new JLabel(),
//     deVersionTitleLabel = new JLabel("Data Element Version"),
//     deVersionValueLabel = new JLabel(),
    deContextNameTitleLabel = new JLabel("Data Element Context"),
    deContextNameValueLabel = new JLabel(),
    vdLongNameTitleLabel = new JLabel("Value Domain Long Name"), vdLongNameValueLabel = new JLabel();
    

  private static final String SEARCH = "SEARCH";
  
  public DEPanel()
  {
    this.setLayout(new BorderLayout());
    JPanel mainPanel = new JPanel(new GridBagLayout());

    insertInBag(mainPanel, searchDeButton, 0, 0, 2, 1);


     insertInBag(mainPanel, deLongNameTitleLabel, 0, 1);
     insertInBag(mainPanel, deLongNameValueLabel, 1, 1);

     insertInBag(mainPanel, deIdTitleLabel, 0, 2);
     insertInBag(mainPanel, deIdValueLabel, 1, 2);

//      insertInBag(mainPanel, deVersionTitleLabel, 0, 0);
//      insertInBag(mainPanel, deVersionValueLabel, 1, 0);

     insertInBag(mainPanel, deContextNameTitleLabel, 0, 3);
     insertInBag(mainPanel, deContextNameValueLabel, 1, 3);

     insertInBag(mainPanel, vdLongNameTitleLabel, 0, 4);
     insertInBag(mainPanel, vdLongNameValueLabel, 1, 4);


//     insertInBag(mainPanel, deIDField, 1, 0);
//     insertInBag(mainPanel, deVersionLabel, 0, 1);
//     insertInBag(mainPanel, deVersionField, 1, 1);
//     insertInBag(mainPanel, searchDeButton, 2, 0);

    this.add(mainPanel);
    this.setSize(300, 300);
    
    searchDeButton.setActionCommand(SEARCH);
    
    searchDeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          JButton button = (JButton)event.getSource();
          if(button.getActionCommand().equals(SEARCH)) {
            CadsrDialog cd = new CadsrDialog(CadsrDialog.MODE_DE);
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
  }
}