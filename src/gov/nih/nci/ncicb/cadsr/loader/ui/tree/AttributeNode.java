package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import gov.nih.nci.ncicb.cadsr.domain.DataElement;

public class AttributeNode extends AbstractUMLNode {

  public AttributeNode(DataElement de) {
    display = de.getDataElementConcept().getProperty().getLongName();

    fullPath = de.getDataElementConcept().getObjectClass().getLongName()
      + display;
  }


}