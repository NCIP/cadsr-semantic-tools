package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationEvent;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationListener;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.ArrayList;
import java.util.List;


public class NavigationButtonPanel extends JPanel implements ActionListener
{

  private JButton previousButton, nextButton;

  private List<NavigationListener> navigationListeners 
    = new ArrayList<NavigationListener>();

  static final private String PREVIOUS = "PREVIOUS",
    NEXT = "NEXT";


  public NavigationButtonPanel() {
    previousButton = new JButton("Previous");
    nextButton = new JButton("Next");

    previousButton.setActionCommand(PREVIOUS);
    nextButton.setActionCommand(NEXT);

    previousButton.addActionListener(this);
    nextButton.addActionListener(this);

    this.add(previousButton);
    this.add(nextButton);

  }


  public void actionPerformed(ActionEvent evt) {
    AbstractButton button = (AbstractButton)evt.getSource();
    
    if(button.getActionCommand().equals(PREVIOUS)) {
      NavigationEvent event = new NavigationEvent(NavigationEvent.NAVIGATE_PREVIOUS);
      fireNavigationEvent(event);
//       conceptEditorPanel.setRemove(false);
    } else if(button.getActionCommand().equals(NEXT)) {
      NavigationEvent event = new NavigationEvent(NavigationEvent.NAVIGATE_NEXT);
      fireNavigationEvent(event);
//       conceptEditorPanel.setRemove(false);
    }
  }

  private void fireNavigationEvent(NavigationEvent event) 
  {
    for(NavigationListener l : navigationListeners)
      l.navigate(event);
  }

  public void addNavigationListener(NavigationListener listener) 
  {
    navigationListeners.add(listener);
  }

}

