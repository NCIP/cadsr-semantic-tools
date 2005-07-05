package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

import java.io.File;

import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;
import gov.nih.nci.ncicb.cadsr.loader.event.*;

public class ReportConfirmPanel extends JPanel {

  private JPanel _this = this;

  private JLabel reportLabel = new JLabel("No Processing Done");

  public ReportConfirmPanel()
  {
    initUI();
  }

  public void setFiles(String file1, String file2) {

    if(file1 == null) {
      reportLabel.setText("<html>Report Generation Failed. <br>" + file2 + "</html>");
    } else {
      reportLabel.setText("<html>Semantic Annotation for the file:<br><div align=center> " + file1 + " </div>was created. <br><br> The output report can be found here: <br><div align=center>" + file2 + "</div></html>");
    }
  }

  private void initUI() {

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    
    infoPanel.setBackground(Color.WHITE);
    infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    infoPanel.add(new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("xmi.gif"))), FlowLayout.LEFT);

    JLabel textLabel = new JLabel("<html>Confirmation</html>");

    infoPanel.add(textLabel);

    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(0, 1));

    buttonPanel.add(reportLabel);

    this.setLayout(new BorderLayout());
    this.add(infoPanel, BorderLayout.NORTH);
    this.add(buttonPanel, BorderLayout.CENTER);

  }

}