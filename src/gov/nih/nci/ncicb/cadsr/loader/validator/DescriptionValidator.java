package gov.nih.nci.ncicb.cadsr.loader.validator;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import java.util.*;
import gov.nih.nci.ncicb.cadsr.loader.util.*;

public class DescriptionValidator implements Validator
{
  private ElementsLists elements = ElementsLists.getInstance();

  private InheritedAttributeList inheritedAttributes = InheritedAttributeList.getInstance();
  
  private ValidationItems items = ValidationItems.getInstance();
  
  public DescriptionValidator()
  {
  }
  
  public void addProgressListener(ProgressListener l) {

  }
  
  public ValidationItems validate() 
  {
    List<ObjectClass> ocs = (List<ObjectClass>)elements.getElements(DomainObjectFactory.newObjectClass().getClass());
    if(ocs != null) {   
      for(ObjectClass oc : ocs) {  
        if(StringUtil.isEmpty(oc.getPreferredDefinition()))
          items.addItem(new ValidationError
                        (PropertyAccessor.getProperty
                         ("class.no.description", oc.getLongName()), oc));
      }
    }
    boolean found = false;
    List<DataElement> des = (List<DataElement>)elements.getElements(DomainObjectFactory.newDataElement().getClass());
    if(des != null) {  
      for(DataElement de : des) {  
        if(!inheritedAttributes.isInherited(de))
          if(de.getDefinitions() == null || de.getDefinitions().size() == 0)
            items.addItem(new ValidationError
                          (PropertyAccessor.getProperty
                           ("attribute.no.description",
                            de.getDataElementConcept().getLongName()), de));
      }
    }
    
    return items; 
    }
}