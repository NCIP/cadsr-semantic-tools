package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class WizardSelectionDialog extends JDialog 
{

  public static final int SELECTION_NEW = 1;
  public static final int SELECTION_CONTINUE = 2;
  public static final int SELECTION_CANCEL = -1;

  private int selection = 0;

  private JButton newButton, continueButton;
  private JLabel newLabel, continueLabel;

  private JDialog _this = this;

  public WizardSelectionDialog()
  {
    super((JFrame)null, "Wizard >> Step 1");
    initUI();
  }

  private void initUI() {
    this.setSize(300, 200);

    newButton = new JButton(null, new ImageIcon(this.getClass().getResource("/new-work.gif")));

    continueButton = new JButton(null, new ImageIcon(this.getClass().getResource("/continue-work.gif")));

    newLabel = new JLabel("Start New Work");
    continueLabel = new JLabel("Continue Saved Work");

    Container contentPane = this.getContentPane();
    contentPane.setLayout(new GridLayout(2, 2));
    contentPane.add(newButton, null);
    contentPane.add(newLabel, null);
    contentPane.add(continueButton, null);
    contentPane.add(continueLabel, null);

    newButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          selection = SELECTION_NEW;
          _this.dispose();
        }
      });

    continueButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          selection = SELECTION_CONTINUE;
          _this.dispose();
        }
      });

    this.addWindowListener(new WindowAdapter()
      {
        public void windowClosing(WindowEvent e)
        {
          selection = SELECTION_CANCEL;
        }
      }
      );
    

  }

  public int getSelection() {
    return selection;
  }
  
}