package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;

import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.InheritedAttributeList;

import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;

import java.awt.GridBagLayout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConceptInheritanceViewPanel extends JPanel {

  private List<ObjectClass> inheritancePath = new ArrayList<ObjectClass>();

  private InheritedAttributeList inheritedAttributeList = InheritedAttributeList.getInstance();

  public ConceptInheritanceViewPanel() {
  }

  public void update(ObjectClass oc) {
    inheritancePath = new ArrayList<ObjectClass>();
    
    inheritancePath.add(oc);
    
    ObjectClass parentOc = inheritedAttributeList.getParentOc(oc);
    while (parentOc != null) {
        inheritancePath.add(0, parentOc);
        parentOc = inheritedAttributeList.getParentOc(parentOc);
    }
    drawTree();
  }

  private void drawTree() {
      setLayout(new GridBagLayout());
      
      int gridCounter = 0;
      for(int i = 0; i < inheritancePath.size(); i++) {
        ObjectClass oc = inheritancePath.get(i);
        String className = LookupUtil.lookupFullName(oc);
        UIUtil.insertInBag(this, new JLabel(className), 0, gridCounter, 1, 1);
        UIUtil.insertInBag(this, new JLabel("concepts go here"), 1, gridCounter, 1, 2);
        if(i < inheritancePath.size())
          UIUtil.insertInBag(this, new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("inheritance-arrow-up.gif"))), 0, gridCounter + 1, 1, 1);
        gridCounter += 3;
      }
  }
}