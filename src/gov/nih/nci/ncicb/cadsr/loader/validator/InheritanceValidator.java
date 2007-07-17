package gov.nih.nci.ncicb.cadsr.loader.validator;

import java.util.*;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.dao.ValueDomainDAO;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;

import gov.nih.nci.ncicb.cadsr.loader.util.*;

/**
 * Validate issues that come from mapping inherited attributes <ul>
 * <li>Inherited attributes CDE mapping must have matching properties
 * all the way down the inheritance path.
 * <ul>
 */
public class InheritanceValidator implements Validator {
  private ElementsLists elements = ElementsLists.getInstance();

  private ValidationItems items = ValidationItems.getInstance();

  private ProgressListener progressListener;

  private InheritedAttributeList inheritedList = InheritedAttributeList.getInstance();

  public InheritanceValidator() {
  }

  public void addProgressListener(ProgressListener l) {
    progressListener = l;
  }

  /**
   * returns a list of Validation errors.
   */
  public ValidationItems validate() {
    
    Collection<DataElement> inheritedDEs = inheritedList.getAllInherited();

    for(DataElement inheritedDE : inheritedDEs) {
      if(inheritedDE.getPublicId() != null) {
        DataElement parentDE = inheritedList.getParent(inheritedDE);
        while(parentDE != null) {
          if(parentDE.getPublicId() != null) {
            if(!inheritedDE.getPublicId().equals(parentDE.getPublicId())
               || !inheritedDE.getVersion().equals(parentDE.getVersion())) {
              items.addItem(new ValidationError
                            (PropertyAccessor.getProperty
                             ("inherited.and.parent.property.mismatch", LookupUtil.lookupFullName(inheritedDE), LookupUtil.lookupFullName(parentDE)),inheritedDE));
            }
          } else {
//             if(!inheritedList.isInherited(parentDE)) {
//               items.addItem(new ValidationError
//                             (PropertyAccessor.getProperty
//                              ("parent.attribute.not.mapped", LookupUtil.lookupFullName(inheritedDE)), inheritedDE));
              
//             }
          }
          parentDE = inheritedList.getParent(parentDE);
        }
      }
    }
    return items;
  }

}