package gov.nih.nci.ncicb.cadsr.loader.validator;

import java.util.*;

import gov.nih.nci.ncicb.cadsr.domain.*;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;

import gov.nih.nci.ncicb.cadsr.loader.util.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

/**
 * Validate issues that come from mapping inherited attributes <ul>
 * <li>Inherited attributes CDE mapping must have matching properties
 * all the way down the inheritance path.
 * <ul>
 */
public class GMEValidator implements Validator {
  private ElementsLists elements = ElementsLists.getInstance();

  private ValidationItems items = ValidationItems.getInstance();

  private ProgressListener progressListener;

  private InheritedAttributeList inheritedList = InheritedAttributeList.getInstance();

  private Logger logger = Logger.getLogger(InheritanceValidator.class.getName());

  public GMEValidator() {
  }

  public void addProgressListener(ProgressListener l) {
    progressListener = l;
  }

  /**
   * returns a list of Validation errors.
   */
  public ValidationItems validate() {
    List<ClassificationSchemeItem> csis = elements.getElements(DomainObjectFactory.newClassificationSchemeItem());
    if(csis != null)
      for(ClassificationSchemeItem csi : csis) {
        boolean nsFound = false, nameFound = false;
        for(AlternateName an : csi.getAlternateNames()) {
          if(an.getType().equals(AlternateName.TYPE_GME_NAMESPACE)) {
            nsFound = true;
            try {
              new URI(an.getName());
            } catch (URISyntaxException e) {
              // can't parse this. Not a valid URI
              items.addItem(new ValidationWarning
                            (PropertyAccessor.getProperty
                             ("not.a.valid.uri.error", an.getName()), csi));
              
            } 
          }
          
        }
      }
    List<ObjectClass> ocs = elements.getElements(DomainObjectFactory.newObjectClass().getClass());
    if(ocs != null) {   
      for(ObjectClass oc : ocs) {  
        boolean nsFound = false, nameFound = false;
        for(AlternateName an : oc.getAlternateNames()) {
          if(an.getType().equals(AlternateName.TYPE_GME_NAMESPACE)) {
            nsFound = true;
            try {
              new URI(an.getName());
            } catch (URISyntaxException e) {
              // can't parse this. Not a valid URI
              items.addItem(new ValidationWarning
                            (PropertyAccessor.getProperty
                             ("not.a.valid.uri.error", an.getName()), oc));
              
            }
          } else if(an.getType().equals(AlternateName.TYPE_GME_XML_ELEMENT)) {
            nameFound = true;
          }
        }
      }
    }
    
    return items;
  }
}
      