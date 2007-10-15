package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import java.awt.*;
import javax.swing.*;

import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

public class ValueDomainViewPanel extends JPanel
{
  private JLabel vdPrefDefTitleLabel = new JLabel("VD Preferred Definition"),
    vdDatatypeTitleLabel = new JLabel("VD Datatype"),
    vdTypeTitleLabel = new JLabel("VD Type"),
    vdCdIdTitleLabel = new JLabel("VD CD PublicId / Version"),
    vdRepIdTitleLabel = new JLabel("Representation PublicId / Version"),
    vdCdLongNameTitleLabel = new JLabel("VD CD Long Name");
  
  private JTextArea vdPrefDefValueTextField = new JTextArea();
  private JTextField vdDatatypeValueLabel = new JTextField(),
    vdTypeValueLabel = new JTextField(),
    vdCdIdValueLabel = new JTextField(),
    vdRepIdValueLabel = new JTextField(),
    vdCdLongNameValueLabel = new JTextField();
  
  private JScrollPane scrollPane;
  
  private ValueDomain vd;

  public ValueDomainViewPanel(ValueDomain vd)
  {
    this.vd = vd;
    initValues();
    initUI();
  }
  
  public void update(ValueDomain vd) 
  {
    this.vd = vd;
    initValues();
  }
  
  private void initUI() 
  {
    this.setLayout(new BorderLayout());
    JPanel mainPanel = new JPanel(new GridBagLayout());
    
    vdPrefDefValueTextField.setEditable(false);
    vdDatatypeValueLabel.setEditable(false);
    vdTypeValueLabel.setEditable(false);
    vdCdIdValueLabel.setEditable(false);
    vdRepIdValueLabel.setEditable(false);
    vdCdLongNameValueLabel.setEditable(false);
        
    vdPrefDefValueTextField.setLineWrap(true);
    vdPrefDefValueTextField.setWrapStyleWord(true);
    scrollPane = new JScrollPane(vdPrefDefValueTextField);
    scrollPane
      .setVerticalScrollBarPolicy
      (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    //    scrollPane.getVerticalScrollBar().setUnitIncrement(30);

    //vdPrefDefValueTextField.setSize(200,200);
    scrollPane = new JScrollPane(vdPrefDefValueTextField);
    scrollPane.setPreferredSize(new Dimension(200, 100));
    
    UIUtil.insertInBag(mainPanel, vdPrefDefTitleLabel, 0, 1);
    UIUtil.insertInBag(mainPanel, scrollPane, 1, 1, 3, 1); 
    
    UIUtil.insertInBag(mainPanel, vdDatatypeTitleLabel, 0, 3);
    UIUtil.insertInBag(mainPanel, vdDatatypeValueLabel, 1, 3);
    
    UIUtil.insertInBag(mainPanel, vdTypeTitleLabel, 0, 4);
    UIUtil.insertInBag(mainPanel, vdTypeValueLabel, 1, 4);
    
    UIUtil.insertInBag(mainPanel, vdCdIdTitleLabel, 0, 5);
    UIUtil.insertInBag(mainPanel, vdCdIdValueLabel, 1, 5);
    
    UIUtil.insertInBag(mainPanel, vdCdLongNameTitleLabel, 0, 6);
    UIUtil.insertInBag(mainPanel, vdCdLongNameValueLabel, 1, 6);

    UIUtil.insertInBag(mainPanel, vdRepIdTitleLabel, 0, 7);
    UIUtil.insertInBag(mainPanel, vdRepIdValueLabel, 1, 7);
    
    this.add(mainPanel);
  }
  
  private void initValues() 
  {
    vdPrefDefValueTextField.setText(vd.getPreferredDefinition());
    vdDatatypeValueLabel.setText(vd.getDataType());
    vdTypeValueLabel.setText(vd.getVdType());
    vdCdIdValueLabel.setText(vd.getConceptualDomain().getPublicId() +
      " / " + vd.getConceptualDomain().getVersion());
    if(vd.getConceptualDomain().getLongName() != null
        && !vd.getConceptualDomain().getLongName().equals(""))
      vdCdLongNameValueLabel.setText(vd.getConceptualDomain().getLongName());
    else
      vdCdLongNameValueLabel.setText("Unable to lookup CD Long Name");

    vdRepIdValueLabel.setText(vd.getRepresentation().getPublicId() +
      " / " + vd.getRepresentation().getVersion());

  }
  
  public static void main(String args[]) 
  {
//    JFrame frame = new JFrame();
//    ValueDomainViewPanel vdPanel = new ValueDomainViewPanel();
//    vdPanel.setVisible(true);
//    frame.add(vdPanel);
//    frame.setVisible(true);
//    frame.setSize(450, 350);
  }

}