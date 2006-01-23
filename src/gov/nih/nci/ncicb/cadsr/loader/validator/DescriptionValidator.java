package gov.nih.nci.ncicb.cadsr.loader.validator;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import java.util.*;
import gov.nih.nci.ncicb.cadsr.loader.util.*;

public class DescriptionValidator implements Validator
{
  private ElementsLists elements;
  
  private ValidationItems items = ValidationItems.getInstance();
  
  public DescriptionValidator(ElementsLists elements)
  {
    this.elements = elements;
  }
  
  public void addProgressListener(ProgressListener l) {

  }
  
  public ValidationItems validate() 
  {
    List<ObjectClass> ocs = (List<ObjectClass>)elements.getElements(DomainObjectFactory.newObjectClass().getClass());
    if(ocs != null) {   
      for(ObjectClass oc : ocs) {  
        if(StringUtil.isEmpty(oc.getPreferredDefinition()))
          items.addItem(new ValidationWarning
                        (PropertyAccessor.getProperty
                         ("class.no.description", oc.getLongName()), oc));
      }
    }
    boolean found = false;
    List<DataElement> des = (List<DataElement>)elements.getElements(DomainObjectFactory.newDataElement().getClass());
    if(des != null) {  
      for(DataElement de : des) {  
        for(AlternateName an : de.getAlternateNames()) {
          if(an.getType() == AlternateName.TYPE_UML_DE) 
            found = true;
            break;
        }
      if(!found)
        items.addItem(new ValidationWarning
                      (PropertyAccessor.getProperty
                       ("attribute.no.description",
                        de.getDataElementConcept().getLongName()), de));
      }
    }
    
    return items; 
    }
}