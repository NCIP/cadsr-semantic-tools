package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.domain.PermissibleValue;

import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;

import java.util.*;

import org.apache.log4j.Logger;

public class PVCompareDialog extends JDialog {

  private List<PermissibleValue> localPVs, cadsrPVs;

  private JLabel lvdTitle = new JLabel("Local Value Domain"),
    cadsrTitle = new JLabel("caDSR Value Domain");

  public PVCompareDialog(List<PermissibleValue> localPVs, List<PermissibleValue> cadsrPVs) {
    super((JFrame)null, true);

    this.localPVs = localPVs;
    this.cadsrPVs = cadsrPVs;

    JPanel mainPanel = new JPanel(new GridBagLayout());

    UIUtil.insertInBag(mainPanel, lvdTitle, 0, 0);
    UIUtil.insertInBag(mainPanel, cadsrTitle, 2, 0);

  }

  public static void main(String[] args) {
    
    
    
  }

}
