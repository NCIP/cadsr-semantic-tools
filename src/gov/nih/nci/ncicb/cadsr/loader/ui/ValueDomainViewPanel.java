package gov.nih.nci.ncicb.cadsr.loader.ui;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import java.awt.*;
import javax.swing.*;

public class ValueDomainViewPanel extends JPanel
{
  private JLabel vdPrefDefTitleLabel = new JLabel("VD Preferred Definition"),
    vdPrefDefValueLabel = new JLabel(),
    vdDatatypeTitleLabel = new JLabel("VD Datatype"),
    vdDatatypeValueLabel = new JLabel(),
    vdTypeTitleLabel = new JLabel("VD Type"),
    vdTypeValueLabel = new JLabel(),
    vdCdIdTitleLabel = new JLabel("VD CD PublicId / Version"),
    vdCdIdValueLabel = new JLabel(),
    vdCdLongNameTitleLabel = new JLabel("VD CD Long Name"),
    vdCdLongNameValueLabel = new JLabel();
    
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
    
    insertInBag(mainPanel, vdPrefDefTitleLabel, 0, 1);
    insertInBag(mainPanel, vdPrefDefValueLabel, 1, 1); 
    
    insertInBag(mainPanel, vdDatatypeTitleLabel, 0, 2);
    insertInBag(mainPanel, vdDatatypeValueLabel, 1, 2);
    
    insertInBag(mainPanel, vdTypeTitleLabel, 0, 3);
    insertInBag(mainPanel, vdTypeValueLabel, 1, 3);
    
    insertInBag(mainPanel, vdCdIdTitleLabel, 0, 4);
    insertInBag(mainPanel, vdCdIdValueLabel, 1, 4);
    
    insertInBag(mainPanel, vdCdLongNameTitleLabel, 0, 5);
    insertInBag(mainPanel, vdCdLongNameValueLabel, 1, 5);
    
    this.add(mainPanel);
  }
  
  private void initValues() 
  {
    vdPrefDefValueLabel.setText(vd.getPreferredDefinition());
    vdDatatypeValueLabel.setText(vd.getDataType());
    vdTypeValueLabel.setText(vd.getVdType());
    vdCdIdValueLabel.setText(vd.getConceptualDomain().getPublicId());
    vdCdLongNameValueLabel.setText(vd.getConceptualDomain().getLongName());
    
  }
  
  private void insertInBag(JPanel bagComp, Component comp, int x, int y) {
    insertInBag(bagComp, comp, x, y, 1, 1);
  }
  
  private void insertInBag(JPanel bagComp, Component comp, int x, int y, int width, int height) {
    JPanel p = new JPanel();
    p.add(comp);

    bagComp.add(p, new GridBagConstraints(x, y, width, height, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
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