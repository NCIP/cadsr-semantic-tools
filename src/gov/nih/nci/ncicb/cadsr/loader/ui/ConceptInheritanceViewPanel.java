package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;

import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.ConceptUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.InheritedAttributeList;

import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;

import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;

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

  private ObjectClass oc = null;
  
  private ConceptDerivationRule resultCondr;

  public ConceptInheritanceViewPanel() {

  }

  public ConceptDerivationRule getConceptDerivationRule() {
    return resultCondr;
  }


  public void applyInheritance() {
    resultCondr = DomainObjectFactory.newConceptDerivationRule();

    InheritedAttributeList inheritedList =  InheritedAttributeList.getInstance();

    // oc is mapped to a public ID, nothing we can do.
    // or oc is marked as excluded from this rule. 
    // either way, we skip
    if(!StringUtil.isEmpty(oc.getPublicId()) || inheritedList.isExcludedFromSemanticInheritance(oc)) {

    } else {
      // start by storing a list of conDR for the Oc hierarchy.
      List<ConceptDerivationRule> condrList = new ArrayList<ConceptDerivationRule>();
      ConceptDerivationRule condr = ConceptUtil.findConceptDerivationRule(oc);
      resultCondr.setComponentConcepts(condr.getComponentConcepts());
//       resultCondr.getComponentConcepts().addAll(condr.getComponentConcepts());

      for(ObjectClass oc : inheritancePath) {
        condrList.add(0, oc.getConceptDerivationRule());
      }
      
      // now trickle concepts down. 
      for(int parentIndex = 1; parentIndex < condrList.size(); parentIndex++) {
        // 1. add one to each component concept order.
        for(ComponentConcept compCon : resultCondr.getComponentConcepts()) {
          compCon.setOrder(compCon.getOrder() + condrList.get(parentIndex).getComponentConcepts().size());
        }
        
        // add the concepts from each parent
        resultCondr.getComponentConcepts().addAll(0, condrList.get(parentIndex).getComponentConcepts());
      }
      
      // update preferredName as conceptList. (refactor this in future)
//       oc.setPreferredName(ConceptUtil.preferredNameFromConceptDerivationRule(resultCondr));
    }

  }

  public void update(ObjectClass oc) {
    this.oc = oc;
    resultCondr = null;

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