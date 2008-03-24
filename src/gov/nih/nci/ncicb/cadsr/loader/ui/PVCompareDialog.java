package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.PermissibleValue;

import java.util.List;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
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
    private List<PermissibleValue> localPermVals = null;
    private List<PermissibleValue> cadsrPermVals = null;

    public PVCompareDialog(List<PermissibleValue> localPVs, List<PermissibleValue> cadsrPVs) {
  
      super((JFrame)null, true);
      localPermVals = localPVs;
      cadsrPermVals = cadsrPVs;
      
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
            lvdPermissibleValueJList = new JList(permissibleValueListModel);
            lvdPermissibleValueJList.setCellRenderer(new MyCellRenderer());
            for(PermissibleValue pm : lvdPermissibleValueList){
                ListItem li = null;
                if(containsLvd(pm.getValue())) // It exists in other list, show it in white
                    li = new ListItem(Color.white, pm.getValue());
                else // It doesn't exist in other list, show it in yellow
                    li = new ListItem(Color.yellow, pm.getValue());
                permissibleValueListModel.addElement(li);
            }
            lvdPermissibleValueJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            lvdPermissibleValueJList.setSelectedIndex(0);
        }
        return lvdPermissibleValueJList;
    }
    private boolean containsLvd(String argValue){
        boolean exists = false;
        if(cadsrPermVals.size() > 0)
            for(PermissibleValue pm : cadsrPermVals)
                if(pm.getValue().equals(argValue)){
                    exists = true;
                    break;
                }
        return exists;
    }
        
    private JList getCadsrPermissibleValuesList(List<PermissibleValue> cadsrPermissibleValueList){
        if(cadsrPermissibleValueList.size() > 0){
            DefaultListModel permissibleValueListModel = new DefaultListModel();
            cadsrPermissibleValueJList = new JList(permissibleValueListModel);
            cadsrPermissibleValueJList.setCellRenderer(new MyCellRenderer());
            for(PermissibleValue pm : cadsrPermissibleValueList){
                ListItem li = null;
                if(containsCadsr(pm.getValue())) // It exists in other list, show it in white
                    li = new ListItem(Color.white, pm.getValue());
                else // It doesn't exist in other list, show it in yellow
                    li = new ListItem(Color.yellow, pm.getValue());
                permissibleValueListModel.addElement(li);
            }
            cadsrPermissibleValueJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            cadsrPermissibleValueJList.setSelectedIndex(0);
        }
        return cadsrPermissibleValueJList;
    }

    private boolean containsCadsr(String argValue){
        boolean exists = false;
        if(localPermVals.size() > 0)
            for(PermissibleValue pm : localPermVals)
                if(pm.getValue().equals(argValue)){
                    exists = true;
                    break;
                }
        return exists;
    }
        
    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }

    private static class ListItem {
        private Color color;
        private String value;
        public ListItem(Color c, String s) {
            color = c;
            value = s;
        }
        public Color getColor() {
            return color;
        }
        public String getValue() {
            return value;
        }
    }
    private static class MyCellRenderer extends JPanel implements ListCellRenderer {
        private JLabel lbl;
        public MyCellRenderer() {
            setOpaque(true);
            lbl = new JLabel();
            add(lbl);
        }
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean iss, boolean chf) {
            lbl.setText(((ListItem) value).getValue());
            setBackground(((ListItem) value).getColor());
            if (iss) 
                setBorder(BorderFactory.createLineBorder(Color.blue, 2));
            else 
                setBorder(BorderFactory.createLineBorder(list.getBackground(), 2));
           
           return this;
        }
    }
//    public static void main(String[] args) {
//      PVCompareDialog pvPanel = new PVCompareDialog(null, null);
//      pvPanel.setVisible(true);
//    }
}
