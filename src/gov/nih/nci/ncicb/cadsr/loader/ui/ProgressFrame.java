package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.*;

public class ProgressFrame extends JFrame 
    implements ProgressListener
{

  JProgressBar progressBar;
  JLabel msgLabel;

  public ProgressFrame(int maximum)
  {
    initUI(maximum);
  }

  public void newProgressEvent(ProgressEvent evt) {
    if(evt.getGoal() != 0) {
      progressBar.setMaximum(evt.getGoal());
    }
    progressBar.setValue(evt.getStatus());
    
    // !!! TODO replace by StringUtil when migrating to UML Loader
    if(evt.getMessage() != null) {
      progressBar.setString(evt.getMessage());
      msgLabel.setText(evt.getMessage());
    }

  }

  private void initUI(int maximum) {
    progressBar = new JProgressBar(0, maximum);

    msgLabel = new JLabel();

    this.setSize(300, 100);

    Container contentPane = this.getContentPane();

    contentPane.setLayout(new BorderLayout());
    contentPane.add(progressBar, BorderLayout.CENTER);
    contentPane.add(msgLabel, BorderLayout.SOUTH);

    
    this.setVisible(true);
  }
}