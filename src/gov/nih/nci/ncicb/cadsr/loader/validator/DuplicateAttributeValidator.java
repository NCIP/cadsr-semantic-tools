package gov.nih.nci.ncicb.cadsr.loader.validator;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import java.util.*;

public class DuplicateAttributeValidator implements Validator
{
  private ElementsLists elements = ElementsLists.getInstance();
  
  private ValidationItems items = ValidationItems.getInstance();
  
  public void addProgressListener(ProgressListener l) {

  }

  public DuplicateAttributeValidator()
  {
  }
  
  public ValidationItems validate() {
    List<ObjectClass> ocs = elements.getElements(DomainObjectFactory.newObjectClass());
    List<DataElement> des = elements.getElements(DomainObjectFactory.newDataElement());
    for(ObjectClass oc : ocs) {
       Map<String, DataElement> deList = new HashMap<String, DataElement>();
       for(DataElement de : des) {
        for(DataElement de2 : des) {
          if(de != de2)
          if(de.getDataElementConcept().getObjectClass() == oc)
            if(de2.getDataElementConcept().getObjectClass() == oc)
              if(de.getDataElementConcept().getProperty().getLongName().equals(
                 de2.getDataElementConcept().getProperty().getLongName()))
                    items.addItem(new ValidationError
                      (PropertyAccessor.getProperty
                        ("de.same.attribute", de.getDataElementConcept().getProperty().getLongName()),de));
                
        }     
    }
  }
  return items;
}

}