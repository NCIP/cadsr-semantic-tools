package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

public class DropDownButton extends JButton implements ActionListener {
  private JToolBar tb;

  private JButton mainButton, arrowButton;

  private final String MAIN_BUTTON = "mainButton",
    ARROW_BUTTON = "arrowButton";

  private JPopupMenu popup = new JPopupMenu();

  public DropDownButton(String title) {
    super();
    tb = new ToolBar();
    mainButton = new JButton(title);
    mainButton.setActionCommand(MAIN_BUTTON);
    arrowButton = new JButton(new DownArrow());
    arrowButton.setActionCommand(ARROW_BUTTON);
    init();
    setFixedSize();
    setBorder(null);
  }
  protected void setFixedSize() {
    arrowButton.setPreferredSize(new Dimension(15, 15));
    arrowButton.setMaximumSize(new Dimension(15, 15));
    arrowButton.setMinimumSize(new Dimension(15, 15));
  }
  private void init() {
    Icon disDownArrow = new DisabledDownArrow();
    arrowButton.setDisabledIcon(disDownArrow);
    arrowButton.setMaximumSize(new Dimension(15,100));
    mainButton.addActionListener(this); 
    arrowButton.addActionListener(this);
            
    setMargin(new Insets(0, 0, 0, 0));
            
    tb.setBorder(null);
    tb.setMargin(new Insets(0, 0, 0, 0));
    tb.setFloatable(false);
    tb.add(mainButton);
    tb.add(arrowButton);
    add(tb);
            
    setFixedSize(mainButton, arrowButton);
  }
  /**
   * Forces the width of this button to be the sum of the widths of the main
   * button and the arrow button. The height is the max of the main button or
   * the arrow button.
   */
  private void setFixedSize(JButton mainButton, JButton arrowButton) {
    int width = (int)(mainButton.getPreferredSize().getWidth() + arrowButton.getPreferredSize().getWidth());
    int height = (int)Math.max(mainButton.getPreferredSize().getHeight(), arrowButton.getPreferredSize().getHeight());
            
    setMaximumSize(new Dimension(width, height));
    setMinimumSize(new Dimension(width, height));
    setPreferredSize(new Dimension(width, height));
  }
    
  /**
   * Adds a component to the popup
   * @param component
   * @return
   */
  public Component addComponent(Component component) {
    return popup.add(component);
  }
         
  public void actionPerformed(ActionEvent ae){ 
    if(ae.getActionCommand().equals(ARROW_BUTTON)){
      JPopupMenu popup = getPopupMenu(); 
      popup.setVisible(true);
      popup.show(this, 0, this.getHeight()); 
    }
    else{
      for(ActionListener l : getActionListeners())
        l.actionPerformed(ae);
//      showCADSRSearchDialog();
    }
  } 

  public void unfocus() {
    popup.setVisible(false);
  }
    
  protected JPopupMenu getPopupMenu() { return popup; }

  private class DownArrow implements Icon {
    Color arrowColor = Color.black;
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
      g.setColor(arrowColor);
      g.drawLine(x, y, x+4, y);
      g.drawLine(x+1, y+1, x+3, y+1);
      g.drawLine(x+2, y+2, x+2, y+2);
    }
    
    public int getIconWidth() {
      return 6;
    }
    
    public int getIconHeight() {
      return 4;
    }
  }
    
  private class DisabledDownArrow extends DownArrow {
          
    public DisabledDownArrow() {
      arrowColor = new Color(140, 140, 140);
    }
     
    public void paintIcon(Component c, Graphics g, int x, int y) {
      super.paintIcon(c, g, x, y);
      g.setColor(Color.white);
      g.drawLine(x+3, y+2, x+4, y+1);
      g.drawLine(x+3, y+3, x+5, y+1);
    }
  }
    
  private class ToolBar extends JToolBar {
    public void updateUI() {
      super.updateUI();
      setBorder(null);
    }
  }
}
