/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.event.ActionEvent;

import java.util.List;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;

import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import java.util.ArrayList;

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

public class MapToLVD extends JDialog implements ActionListener{
    private JPanel mainPanel = null, textPanel = null, 
        dataPanel = null, closeButtonPanel = null;
    private JLabel textLabel = new JLabel("<html><b>Please select a Local Value Domain</b></html>");
    private JButton closeButton = new JButton("Close");
    private List<ValueDomain> lvds = null;
    private JList lvdValueJList = null;
    private JScrollPane lvdScrollPane = null;
    private int selectedIndex = -1;
    private ValueDomain selectedValueDomain = null;

    public MapToLVD(List<ValueDomain> lvds) {
      super((JFrame)null, true);
      setTitle(PropertyAccessor.getProperty("maptolvd.title"));
      
      this.lvds = lvds;
      this.setLayout(new BorderLayout());
      this.setSize(300,225);
      
      mainPanel = new JPanel();
      mainPanel.setLayout(new BorderLayout());
      
      textPanel = new JPanel();
      textPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
      textPanel.add(textLabel);
      
      if((lvds != null) && (lvds.size() > 0)){
        lvdScrollPane = new JScrollPane(getLVDValuesList(lvds));
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
              JList theList = (JList) mouseEvent.getSource();
              if (mouseEvent.getClickCount() == 2) {
                selectedIndex = theList.locationToIndex(mouseEvent.getPoint());
                setLocalValueDomain(selectedIndex);
                setVisible(false);
              }
            }
          };
        lvdValueJList.addMouseListener(mouseListener);
      }
      else
        lvdScrollPane = new JScrollPane(new JLabel("<HTML><B color=RED>&nbsp;&nbsp;There are no Local Value Domains "+ 
                                                   "\n" +"to choose from.  </B></HTML>"));
      dataPanel = new JPanel();
        dataPanel.setLayout(new BorderLayout());
        dataPanel.add(lvdScrollPane, BorderLayout.CENTER);

        closeButton.addActionListener(this);
        closeButtonPanel = new JPanel();
        closeButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        closeButtonPanel.add(closeButton);

        mainPanel.add(textPanel, BorderLayout.NORTH);
        mainPanel.add(dataPanel, BorderLayout.CENTER);
        mainPanel.add(closeButtonPanel, BorderLayout.SOUTH);
        
        this.add(mainPanel, BorderLayout.CENTER);
    }

    private JList getLVDValuesList(List<ValueDomain> lvdValueList){
      if(lvdValueList.size() > 0){
        DefaultListModel lvdValueListModel = new DefaultListModel();
        lvdValueJList = new JList(lvdValueListModel);
        lvdValueJList.setCellRenderer(new MyCellRenderer());
        
        List values = new ArrayList();
        for(ValueDomain vd : lvdValueList) 
          values.add(LookupUtil.lookupFullName(vd));
        
        for(int i=0; i<values.size(); i++){
          ListItem li = new ListItem(values.get(i).toString());
          lvdValueListModel.addElement(li);
        }
        lvdValueJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      }
      return lvdValueJList;
    }
  
  public void setLocalValueDomain(int selectedIndex){
    selectedValueDomain = lvds.get(selectedIndex);
  }
  
  public ValueDomain getLocalValueDomain(){
    return selectedValueDomain;
  }
  
  public void actionPerformed(ActionEvent e) {
    if(lvdValueJList != null) {
      selectedIndex = lvdValueJList.getSelectedIndex();
      if(selectedIndex != -1)
        setLocalValueDomain(selectedIndex);
    }
    this.setVisible(false);
  }
    
    private static class ListItem {
      private String value;
      public ListItem(String s) {
        value = s;
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
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.TRAILING);
        setLayout(fl);
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        add(lbl);
      }
      public Component getListCellRendererComponent(JList list, Object value, int index, boolean iss, boolean chf) {
        lbl.setText(((ListItem) value).getValue());
        if (iss) 
          setBorder(BorderFactory.createLineBorder(Color.blue, 2));
        else 
          setBorder(BorderFactory.createLineBorder(list.getBackground(), 2));

        return this;
      }
    }
}
