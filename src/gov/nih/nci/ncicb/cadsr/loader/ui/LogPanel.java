package gov.nih.nci.ncicb.cadsr.loader.ui;

import javax.swing.*;
import javax.swing.event.*;

import java.util.*;

import org.apache.log4j.spi.*;
import org.apache.log4j.*;

import gov.nih.nci.ncicb.cadsr.loader.event.LogListener;

public class LogPanel extends JPanel 
  implements LogListener {

  private LogPanel _this = this;

  public void append(LoggingEvent loggingEvent) {
    events.add(loggingEvent);
    _this.updateUI();
  }

  private List<LoggingEvent> events = new ArrayList<LoggingEvent>();

  public LogPanel() {
    initUI();
  }

  
  private void initUI() {
    JList list = new JList(new ListModel() {
        public void addListDataListener(ListDataListener l) {

        }
        public Object getElementAt(int index) {
          return events.get(index).getMessage();
        }
        public int getSize() {
          return events.size();
        }
        public void removeListDataListener(ListDataListener l) {
          
        }
      });

    add(list);
  }

}