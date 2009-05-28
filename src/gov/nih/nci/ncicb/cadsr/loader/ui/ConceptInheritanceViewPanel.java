package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;

import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.ConceptUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.InheritedAttributeList;

import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
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
        UIUtil.insertInBag(this, new JLabel(getHtmlForName(className)), 0, gridCounter, 1, 1, GridBagConstraints.CENTER);
        UIUtil.insertInBag(this, new JLabel(getHtmlForConcepts(oc)), 1, gridCounter, 1, 1, GridBagConstraints.CENTER);
        if(inheritedAttributeList.isExcludedFromSemanticInheritance(oc)) {
          JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
          imgPanel.add(new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("ignore-concept-inheritance.gif"))));
          UIUtil.insertInBag(this, imgPanel, 2, gridCounter, 2, 1, GridBagConstraints.CENTER);
        }
        if(i < inheritancePath.size() - 1) {
          JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
          imgPanel.add(new JLabel(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("inheritance-arrow-up.gif"))));
          UIUtil.insertInBag(this, imgPanel, 0, gridCounter + 1, 2, 1, GridBagConstraints.CENTER);
        }
        gridCounter += 2;
      }
  }

  private String getHtmlForName(String name) {
    StringBuilder sb = new StringBuilder();
    sb.append("<html><body><br>");

    sb.append(name);

    sb.append("</body></html>");

    return sb.toString();
  }

  private String getHtmlForConcepts(ObjectClass oc) {
    StringBuilder sb = new StringBuilder();
    sb.append("<html><body>");

    String[] codes = oc.getPreferredName().split(":");
    for(String code : codes) {
      Concept con = ConceptUtil.getConceptFromCode(code);
      sb.append("<br>");
      sb.append(con.getPreferredName());
      sb.append(": ");
      sb.append(con.getLongName());
    }

    sb.append("</body></html>");

    return sb.toString();
  }
}