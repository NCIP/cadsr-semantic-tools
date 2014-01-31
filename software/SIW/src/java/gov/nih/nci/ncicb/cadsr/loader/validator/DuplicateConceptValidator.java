package gov.nih.nci.ncicb.cadsr.loader.validator;

import gov.nih.nci.ncicb.cadsr.domain.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.util.*;
import java.util.*;

public class DuplicateConceptValidator implements Validator
{
  private ElementsLists elements = ElementsLists.getInstance();
  
  private ValidationItems items = ValidationItems.getInstance();

  public DuplicateConceptValidator()
  {
  }
  
  public void addProgressListener(ProgressListener l) {

  }
  
  public ValidationItems validate() {
    List<ObjectClass> ocs = elements.getElements(DomainObjectFactory.newObjectClass());
    for(ObjectClass oc : ocs) {
      ConceptDerivationRule condr = oc.getConceptDerivationRule();
      if(condr != null && condr.getComponentConcepts() != null && condr.getComponentConcepts().size() > 0) {
        List<String> conceptCodes  = new ArrayList<String>();
        for(ComponentConcept comp : condr.getComponentConcepts()) {
          if(conceptCodes.contains(comp.getConcept().getPreferredName())) {
            items.addItem(new ValidationConceptWarning(
                            PropertyAccessor.getProperty(
                              "concept.class.duplication.warning", LookupUtil.lookupFullName(oc)), oc));
            
          }
          conceptCodes.add(comp.getConcept().getPreferredName());
        }
      }
    }
    
    List<DataElement> des = elements.getElements(DomainObjectFactory.newDataElement());

    for(DataElement de : des) {
      Property prop = de.getDataElementConcept().getProperty();

      ConceptDerivationRule condr = prop.getConceptDerivationRule();
      if(condr != null && condr.getComponentConcepts() != null && condr.getComponentConcepts().size() > 0) {
        List<String> conceptCodes  = new ArrayList<String>();
        for(ComponentConcept comp : condr.getComponentConcepts()) {
          if(conceptCodes.contains(comp.getConcept().getPreferredName())) {
            items.addItem(new ValidationConceptWarning(
                            PropertyAccessor.getProperty(
                              "concept.attribute.duplication.warning"), prop));
            
          }
          conceptCodes.add(comp.getConcept().getPreferredName());
        }
      }
    }

    return items;
  }

}