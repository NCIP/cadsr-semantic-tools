package gov.nih.nci.ncicb.cadsr.loader.validator;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import java.util.*;

public class DuplicateAttributeValidator implements Validator
{
  private ElementsLists elements = ElementsLists.getInstance();
  
  private ValidationItems items = ValidationItems.getInstance();

  private ProgressListener progressListener;
  
  public void addProgressListener(ProgressListener l) {
    progressListener = l;
  }

  private void fireProgressEvent(ProgressEvent evt) {
    if(progressListener != null)
      progressListener.newProgressEvent(evt);
  }

  public DuplicateAttributeValidator()
  {
  }
  
  public ValidationItems validate() {
    List<ObjectClass> ocs = elements.getElements(DomainObjectFactory.newObjectClass());
    List<DataElement> des = elements.getElements(DomainObjectFactory.newDataElement());

    ProgressEvent evt = new ProgressEvent();
    evt.setMessage("Validating Object Classes ...");
    evt.setGoal(ocs.size());
    evt.setStatus(0);
    fireProgressEvent(evt);
    int count = 1;
    for(ObjectClass oc : ocs) {
      evt = new ProgressEvent();
      evt.setMessage(" Validating " + oc.getLongName());
      evt.setStatus(count++);
      fireProgressEvent(evt);
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