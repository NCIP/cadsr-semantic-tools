/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ui;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Checkbox;

import java.awt.event.*;

import java.util.*;

import org.apache.log4j.spi.*;
import org.apache.log4j.*;

import gov.nih.nci.ncicb.cadsr.loader.event.LogListener;

public class LogPanel extends JPanel 
  implements LogListener, MouseListener {

  private LogPanel _this = this;

  private JPopupMenu popupMenu;

  private JCheckBoxMenuItem debugCb = new JCheckBoxMenuItem("Debug", false),
    infoCb = new JCheckBoxMenuItem("Info", true),
    warnCb = new JCheckBoxMenuItem("Warnings", true),
    errorCb = new JCheckBoxMenuItem("Errors", true);

  private List<LoggingEvent> events = new ArrayList<LoggingEvent>();
  private List<LoggingEvent> displayEvents =
    new ArrayList<LoggingEvent>();

  private JList list;

  public void append(LoggingEvent loggingEvent) {
    events.add(loggingEvent);
    if(includeInDisplay(loggingEvent))
      displayEvents.add(loggingEvent);
    _this.updateUI();
  }

  public LogPanel() {
    initUI();
  }

  private boolean includeInDisplay(LoggingEvent evt) {
    if(evt.getLevel().equals(Level.DEBUG)) {
      return debugCb.isSelected();
    } else if(evt.getLevel().equals(Level.INFO)) {
      return infoCb.isSelected();
    } else if(evt.getLevel().equals(Level.WARN)) {
      return warnCb.isSelected();
    } else if(evt.getLevel().equals(Level.ERROR)) {
      return errorCb.isSelected();
    }
    return true;
  }

  private void rebuildDisplay() {
    displayEvents.clear();

    for(LoggingEvent evt : events) {
      if(includeInDisplay(evt))
        displayEvents.add(evt);
     
    }
    list.updateUI();
  }
  
  private void initUI() {
    setLayout(new BorderLayout());

    list = new JList(new ListModel() {
        public void addListDataListener(ListDataListener l) {

        }
        public Object getElementAt(int index) {
          return displayEvents.get(index).getMessage();
        }
        public int getSize() {
          return displayEvents.size();
        }
        public void removeListDataListener(ListDataListener l) {
          
        }
      });

    JScrollPane scrollPane = new JScrollPane(list);
    add(scrollPane, BorderLayout.CENTER);

    setupMenus();

    list.addMouseListener(this);

  }

  private void setupMenus() {
    popupMenu = new JPopupMenu();
    
    JMenuItem viewLabel = new JMenuItem("Select Event View Level");

    ActionListener cbAl = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        rebuildDisplay();
      }
    };
    
    debugCb.addActionListener(cbAl);
    infoCb.addActionListener(cbAl);
    warnCb.addActionListener(cbAl);
    errorCb.addActionListener(cbAl);

    infoCb.setSelected(true);
    warnCb.setSelected(true);
    errorCb.setSelected(true);

    debugCb.addActionListener(cbAl);
    infoCb.addActionListener(cbAl);
    warnCb.addActionListener(cbAl);
    errorCb.addActionListener(cbAl);

    popupMenu.add(viewLabel);
    popupMenu.add(debugCb);
    popupMenu.add(infoCb);
    popupMenu.add(warnCb);
    popupMenu.add(errorCb);

  }

  public void mousePressed(MouseEvent e) {
    showPopup(e);
  }
  
  public void mouseExited(MouseEvent e) {}
  
  public void mouseClicked(MouseEvent e) {}
  
  public void mouseEntered(MouseEvent e) {}
  
  public void mouseReleased(MouseEvent e) {
    showPopup(e);
  }
  
  private void showPopup(MouseEvent e) {
    if (e.isPopupTrigger()) {
      popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }
  }

  public static void main(String[] args) {
    JFrame testFrame = new JFrame();
    testFrame.setSize(400, 300);

    testFrame.getContentPane().add(new LogPanel());
    
    testFrame.setVisible(true);
  }

}