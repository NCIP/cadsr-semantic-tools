package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.PermissibleValue;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.ListSelectionModel;

import org.apache.log4j.Logger;

public class PVCompareDialog extends JDialog implements ActionListener{

    private JLabel lvdLabel = new JLabel("Local Value Domains");
    private JLabel cadsrLabel = new JLabel("caDSR Value Domains");
    private JScrollPane lvdScrollPane = null;
    private JScrollPane cadsrScrollPane = null;
    
    private JButton closeButton = null;
    private JPanel mainPanel = null;
    private Logger logger = Logger.getLogger(PVCompareDialog.class.getName());
    private JList lvdPermissibleValueJList = null;
    private JList cadsrPermissibleValueJList = null;

    public PVCompareDialog(List<PermissibleValue> localPVs, List<PermissibleValue> cadsrPVs) {
  
      super((JFrame)null, true);

      mainPanel = new JPanel();
      mainPanel.setLayout(new GridBagLayout());

      lvdScrollPane = new JScrollPane(getLVDPermissibleValuesList(localPVs));
      cadsrScrollPane = new JScrollPane(getCadsrPermissibleValuesList(cadsrPVs));

      closeButton = new JButton(" Close ");
      closeButton.addActionListener(this);
      
      mainPanel.add(lvdLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(10, 0, 5, 10), 2, 2));
      mainPanel.add(lvdScrollPane, new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 0, 10, 10), 2, 2));
      mainPanel.add(cadsrLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(10, 0, 5, 10), 2, 2));
      mainPanel.add(cadsrScrollPane, new GridBagConstraints(1, 1, 1, 1, 0.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 0, 10, 10), 2, 2));
      mainPanel.add(closeButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 10, 0), 2, 2));
      
      this.add(mainPanel);
      this.setSize(450, 350);
    }

    private JList getLVDPermissibleValuesList(List<PermissibleValue> lvdPermissibleValueList){
        if(lvdPermissibleValueList.size() > 0){
            DefaultListModel permissibleValueListModel = new DefaultListModel();
            for(PermissibleValue pm : lvdPermissibleValueList){
                permissibleValueListModel.addElement(pm.getValue());
            }
            lvdPermissibleValueJList = new JList(permissibleValueListModel);
            lvdPermissibleValueJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            lvdPermissibleValueJList.setSelectedIndex(0);
        }
        return lvdPermissibleValueJList;
    }

    private JList getCadsrPermissibleValuesList(List<PermissibleValue> cadsrPermissibleValueList){
        if(cadsrPermissibleValueList.size() > 0){
            DefaultListModel permissibleValueListModel = new DefaultListModel();
            for(PermissibleValue pm : cadsrPermissibleValueList){
                permissibleValueListModel.addElement(pm.getValue());
            }
            cadsrPermissibleValueJList = new JList(permissibleValueListModel);
            cadsrPermissibleValueJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            cadsrPermissibleValueJList.setSelectedIndex(0);
        }
        return cadsrPermissibleValueJList;
    }

    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }

//    public static void main(String[] args) {
//      PVCompareDialog pvPanel = new PVCompareDialog(null, null);
//      pvPanel.setVisible(true);
//    }
}
