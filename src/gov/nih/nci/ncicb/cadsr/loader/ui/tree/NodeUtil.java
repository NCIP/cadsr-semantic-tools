package gov.nih.nci.ncicb.cadsr.loader.ui.tree;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;

public class NodeUtil 
{
  public NodeUtil()
  {
  }

  public static Concept[] getConceptsFromNode(UMLNode node) 
  {
    Concept[] concepts = null;
      String[] conceptCodes = null;
      if(node instanceof ClassNode) {
        ObjectClass oc = (ObjectClass)node.getUserObject();
        conceptCodes = oc.getPreferredName().split("-");
        
      } else if(node instanceof AttributeNode) {
        Property prop = ((DataElement)node.getUserObject()).getDataElementConcept().getProperty();
        conceptCodes = prop.getPreferredName().split("-");
      } 
      
      if((conceptCodes == null ) || StringUtil.isEmpty(conceptCodes[0])) {
        conceptCodes = new String[0];
      }

      concepts = new Concept[conceptCodes.length];

      for(int i=0; i<concepts.length; 
          concepts[i] = LookupUtil.lookupConcept(conceptCodes[i++])
          );
      if((concepts.length > 0) && (concepts[0] == null))
        concepts = new Concept[0];
    
    return concepts;
  }
}