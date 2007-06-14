package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.plaf.ColorUIResource;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;


public class CheckRenderer extends JPanel implements TreeCellRenderer {
  protected JCheckBox check;
  protected TreeLabel label;
  
  public CheckRenderer() {
    setLayout(null);
    check = new JCheckBox();
    add(label = new TreeLabel());
    check.setBackground(UIManager.getColor("Tree.textBackground"));
  }

  public Component getTreeCellRendererComponent(JTree tree, Object value,
               boolean isSelected, boolean expanded,
               boolean leaf, int row, boolean hasFocus) {
    String  stringValue = tree.convertValueToText(value, isSelected,
			expanded, leaf, row, hasFocus);
    setEnabled(tree.isEnabled());
    check.setSelected(((CheckNode)value).isSelected());
    label.setFont(tree.getFont());
    label.setText(stringValue);
    label.setSelected(isSelected);
    label.setFocus(hasFocus);

    Object userObject = ((CheckNode)value).getUserObject();
    if(userObject instanceof FilterClass)
      label.setIcon(((FilterClass)userObject).getIcon());
    else if(userObject instanceof FilterPackage)
      label.setIcon(((FilterPackage)userObject).getIcon());
    else if (leaf) {
      label.setIcon(UIManager.getIcon("Tree.leafIcon"));
    } else if (expanded) {
      label.setIcon(UIManager.getIcon("Tree.openIcon"));
    } else {
      label.setIcon(UIManager.getIcon("Tree.closedIcon"));
    }	    
    return this;
  }
  
  public Dimension getPreferredSize() {
    Dimension d_check = check.getPreferredSize();
    Dimension d_label = label.getPreferredSize();
    return new Dimension(d_check.width  + d_label.width,
      (d_check.height < d_label.height ?
       d_label.height : d_check.height));
  }
  
  public void doLayout() {
    Dimension d_check = new Dimension(0,0);
    Dimension d_label = label.getPreferredSize();
    int y_check = 0;
    label.setLocation(0,0);    
    label.setBounds(0,0,d_label.width,d_label.height);    
  }
   
  
  public void setBackground(Color color) {
    if (color instanceof ColorUIResource)
      color = null;
    super.setBackground(color);
  }
  
    
  class TreeLabel extends JLabel {
    boolean isSelected;
    boolean hasFocus;
    
    TreeLabel() {
    }
        
    public void setBackground(Color color) {
	if(color instanceof ColorUIResource)
	    color = null;
	super.setBackground(color);
    } 
         
    public void paint(Graphics g) {
      String str;
      if ((str = getText()) != null) {
        if (0 < str.length()) {
          if (isSelected) {
            g.setColor(UIManager.getColor("Tree.selectionBackground"));
          } else {
            g.setColor(UIManager.getColor("Tree.textBackground"));
          }
          Dimension d = getPreferredSize();
          int imageOffset = 0;
          Icon currentI = getIcon();
          if (currentI != null) {
            imageOffset = currentI.getIconWidth() + Math.max(0, getIconTextGap() - 1);
          }
          g.fillRect(imageOffset, 0, d.width -1 - imageOffset, d.height);
          if (hasFocus) {
            g.setColor(UIManager.getColor("Tree.selectionBorderColor"));
            g.drawRect(imageOffset, 0, d.width -1 - imageOffset, d.height -1);     
         }
        }
      }
      super.paint(g);
    }
  
    public Dimension getPreferredSize() {
      Dimension retDimension = super.getPreferredSize();
      if (retDimension != null) {
        retDimension = new Dimension(retDimension.width + 3,
				 retDimension.height);
      }
      return retDimension;
    }
    
    void setSelected(boolean isSelected) {
      this.isSelected = isSelected;
    }
    
    void setFocus(boolean hasFocus) {
      this.hasFocus = hasFocus;
    }
  }
}    
