package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.event.*;
import gov.nih.nci.ncicb.cadsr.loader.util.*;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.*;

public class ProgressPanel extends JPanel 
    implements ProgressListener
{

  private JProgressBar progressBar;
  private JLabel msgLabel;

  public ProgressPanel(int maximum)
  {
    initUI(maximum);
  }

  public void newProgressEvent(ProgressEvent evt) {
    if(evt.getGoal() != 0) {
      progressBar.setMaximum(evt.getGoal());
      if(evt.getGoal() == evt.getStatus())
        progressBar.setIndeterminate(false);
    }
    progressBar.setValue(evt.getStatus());


    if(!StringUtil.isEmpty(evt.getMessage())) {
      progressBar.setString(evt.getMessage());
      msgLabel.setText(evt.getMessage());
    }

  }

  private void initUI(int maximum) {
    if(maximum > 0)
      progressBar = new JProgressBar(0, maximum);
    else {
      progressBar = new JProgressBar();
      progressBar.setIndeterminate(true);
    }
      
    this.setSize(300, 100);

    msgLabel = new JLabel("");

    this.setLayout(new BorderLayout());
    this.add(progressBar, BorderLayout.CENTER);
    this.add(msgLabel, BorderLayout.SOUTH);

  }


  public static void main(String[] args) {
    ProgressFrame progressFrame = new ProgressFrame(-1);

    ProgressEvent evt = new ProgressEvent();
    evt.setMessage("Loading Defaults");
    progressFrame.newProgressEvent(evt);
    
  }
}