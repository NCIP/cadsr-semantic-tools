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
  implements LogListener {

  private LogPanel _this = this;

  private JCheckBox debugCb = new JCheckBox("Debug", false),
    infoCb = new JCheckBox("Info", true),
    warnCb = new JCheckBox("Warnings", true),
    errorCb = new JCheckBox("Errors", true);

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

    JPanel cbPanel = new JPanel();
    cbPanel.setLayout(new GridLayout(4, 1));

    ActionListener cbAl = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        rebuildDisplay();
      }
    };

    cbPanel.add(debugCb);
    cbPanel.add(infoCb);
    cbPanel.add(warnCb);
    cbPanel.add(errorCb);

    infoCb.setSelected(true);
    warnCb.setSelected(true);
    errorCb.setSelected(true);

    debugCb.addActionListener(cbAl);
    infoCb.addActionListener(cbAl);
    warnCb.addActionListener(cbAl);
    errorCb.addActionListener(cbAl);

    add(cbPanel, BorderLayout.EAST);


  }

}