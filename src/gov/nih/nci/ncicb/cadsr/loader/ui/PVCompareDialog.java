package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.PermissibleValue;

import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

import java.awt.GridBagLayout;

import javax.swing.*;

import java.util.*;

public class PVCompareDialog extends JDialog {


    private JLabel lvdLabel = new JLabel("Local Value Domains");
    private JList localValueDomainsLeftList = null;
    private String[] leftListElements = { "Bird", "Cat", "Dog", "Rabbit", "Pig", "dukeWaveRed",
        "kathyCosmo", "lainesTongue", "left", "middle", "right", "stickerface", "Value WAYYYYYYY TOOOO BIGGGG"};
    private JScrollPane leftScrollPane = null;
    
    private JLabel cadsrLabel = new JLabel("caDSR Value Domains");
    private JList caDSRValueDomainsRightList = null;
    private String[] rightListElements = { "Value 1", "Value 2", "Value 3", "Value 4", "Value 5", "Value 6",
        "Value 7", "Value 8", "Value 9", "Value 10", "Value 111111111111111", "Value 12"};
    private JScrollPane rightScrollPane = null;
    
    private JPanel mainPanel = null;

    public PVCompareDialog(List<PermissibleValue> localPVs, List<PermissibleValue> cadsrPVs) {
  
      super((JFrame)null, true);


      mainPanel = new JPanel();
      mainPanel.setLayout(new GridBagLayout());

      localValueDomainsLeftList = new JList(leftListElements);
      localValueDomainsLeftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      localValueDomainsLeftList.setSelectedIndex(0);
      leftScrollPane = new JScrollPane(localValueDomainsLeftList);
     
      caDSRValueDomainsRightList = new JList(rightListElements);
      caDSRValueDomainsRightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      caDSRValueDomainsRightList.setSelectedIndex(0);
      rightScrollPane = new JScrollPane(caDSRValueDomainsRightList);

      UIUtil.insertInBag(mainPanel, lvdLabel, 0, 0);
      UIUtil.insertInBag(mainPanel, leftScrollPane, 0, 1);
      UIUtil.insertInBag(mainPanel, cadsrLabel, 1, 0);
      UIUtil.insertInBag(mainPanel, rightScrollPane, 1, 1);
      
      this.add(mainPanel);
      this.setSize(450, 350);
    }

  public static void main(String[] args) {
     PVCompareDialog pvPanel = new PVCompareDialog(null, null);
     pvPanel.setVisible(true);
  }

}
