/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.validator;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.util.InheritedAttributeList;
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
    InheritedAttributeList inheritedAttrs = InheritedAttributeList.getInstance();
    
    for(ObjectClass oc : ocs) {
      evt = new ProgressEvent();
      evt.setMessage(" Validating " + oc.getLongName());
      evt.setStatus(count++);
      fireProgressEvent(evt);
      
      List<ObjectClass> childOCs = inheritedAttrs.getChildrenOc(oc);
      
      for(DataElement de : des) {
        for(DataElement de2 : des) {
          if(de != de2) {
        	  if(de.getDataElementConcept().getObjectClass() == oc) {
        		  if(de2.getDataElementConcept().getObjectClass() == oc) {
        			  if(de.getDataElementConcept().getProperty().getLongName().equals(
                              de2.getDataElementConcept().getProperty().getLongName())) {
        				  items.addItem(new ValidationError
                                  (PropertyAccessor.getProperty
                                    ("de.same.attribute", de.getDataElementConcept().getProperty().getLongName()),de));
        			  }          
        		  }
        		  else {
        			  if (objectClassInList(de2.getDataElementConcept().getObjectClass(), childOCs)) {
        				  if (!inheritedAttrs.isInherited(de2) && propertiesEqual(de, de2)) {
        					  ValidationError error = new ValidationError
                              (PropertyAccessor.getProperty
                                      ("de.same.inherited.attribute", de2.getDataElementConcept().getLongName(),
                                    		  de.getDataElementConcept().getLongName()),de2);
        					  error.setIncludeInInherited(true);
        					  items.addItem(error);
        				  }
        			  }
        		  }
        	  }
          }
        }
        
    }
  }
  return items;
}
  
  private boolean objectClassInList(ObjectClass oc, List<ObjectClass> ocs) {
	  String ocName = oc.getLongName();
	  if (ocName != null) {
		  for (ObjectClass listOC: ocs) {
			  String listOCName = listOC.getLongName();
			  if (listOCName != null && ocName.equals(listOCName)) {
				  return true;
			  }
		  }
	  }
	  
	  return false;
  }
  
  private boolean propertiesEqual(DataElement de1, DataElement de2) {
	  Property prop1 = getProperty(de1);
	  Property prop2 = getProperty(de2);
	  
	  if (prop1 != null && prop2 != null) {
		  String longName1 = prop1.getLongName();
		  String longName2 = prop2.getLongName();
		  
		  if (longName1 != null && longName2 != null && longName1.equals(longName2)) {
			  return true;
		  }
	  }
	  return false;
  }
  
  private Property getProperty(DataElement de) {
	  DataElementConcept dec = de.getDataElementConcept();
	  if (dec != null) {
		  if (dec.getProperty() != null) {
			  return dec.getProperty();
		  }
	  }
	  
	  return null;
  }

}