package gov.nih.nci.ncicb.cadsr.loader.validator;

import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.util.ConceptUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.InheritedAttributeList;
import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import java.util.List;

/**
 * Validates that concept Inheritance exists between Object Classes
 * 
 */
public class ConceptInheritanceValidator implements Validator
{
  private ValidationItems items = ValidationItems.getInstance();
  
  private ProgressListener progressListener;
  
  public ConceptInheritanceValidator()
  {
  }
  
  public void addProgressListener(ProgressListener l) {
    progressListener = l;
  }
  
  public ValidationItems validate() {

    List<ObjectClass> ocs = ElementsLists.getInstance().getElements(DomainObjectFactory.newObjectClass());

    InheritedAttributeList inheritedList =  InheritedAttributeList.getInstance();

    for(ObjectClass oc : ocs) {

      ConceptDerivationRule condr = ConceptUtil.findConceptDerivationRule(oc);
      ObjectClass parentOc = inheritedList.getParentOc(oc);
      if(parentOc != null) {
        ConceptDerivationRule parentCondr = ConceptUtil.findConceptDerivationRule(parentOc);
        
        try {
        // check that all parentOC concepts are in the childOC
        if(condr.getComponentConcepts().size() < parentCondr.getComponentConcepts().size()) {
          items.addItem(new ValidationConceptError(
                          PropertyAccessor.getProperty(
                            "concept.inheritance.error", LookupUtil.lookupFullName(oc), LookupUtil.lookupFullName(parentOc)), oc));
        } else {
          for(int i = 0; i < parentCondr.getComponentConcepts().size(); i++) {
            if(!condr.getComponentConcepts().get(i).getConcept().getPreferredName().equals(
                 parentCondr.getComponentConcepts().get(i).getConcept().getPreferredName()
                 )) {
              items.addItem(new ValidationConceptError(
                              PropertyAccessor.getProperty(
                                "concept.inheritance.error", LookupUtil.lookupFullName(oc), LookupUtil.lookupFullName(parentOc)), oc));
              
              // break out of for loop
              i = parentCondr.getComponentConcepts().size();
            }
          }
        }
        } catch (NullPointerException e) {
                System.out.println("hi");
        }

      }
    }
    
    return items;
  }
  
}