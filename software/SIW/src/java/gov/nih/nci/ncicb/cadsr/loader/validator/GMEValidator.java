/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

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
    boolean gmeFound = false;
    if(csis != null)
      for(ClassificationSchemeItem csi : csis) {
        boolean nsFound = false, nameFound = false;
        for(AlternateName an : csi.getAlternateNames()) {
          if(an.getType().equals(AlternateName.TYPE_GME_NAMESPACE)) {
            nsFound = true;
            gmeFound = true;
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
        if(!nsFound) {
          items.addItem(new ValidationWarning
                        (PropertyAccessor.getProperty
                         ("gme.missing.namespace"), csi));
        }
      }

    List<ValidationItem> tempItems = new ArrayList<ValidationItem>();

    List<ObjectClass> ocs = elements.getElements(DomainObjectFactory.newObjectClass().getClass());
    if(ocs != null) {   
      for(ObjectClass oc : ocs) {  
        boolean nsFound = false, nameFound = false;
        for(AlternateName an : oc.getAlternateNames()) {
          if(an.getType().equals(AlternateName.TYPE_GME_NAMESPACE)) {
            nsFound = true;
            gmeFound = true;
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
            gmeFound = true;
          }
        }
        if(!nsFound) {
          tempItems.add(new ValidationWarning
                        (PropertyAccessor.getProperty
                         ("gme.missing.namespace"), oc));
        }
        if(!nameFound) {
          tempItems.add(new ValidationWarning
                        (PropertyAccessor.getProperty
                         ("gme.missing.xml.element"), oc));
        }

      }
    }

    List<DataElement> des = elements.getElements(DomainObjectFactory.newDataElement().getClass());
    if(des != null) {   
      for(DataElement de : des) {  
        boolean nameFound = false;
        for(AlternateName an : de.getAlternateNames()) {
          if(an.getType().equals(AlternateName.TYPE_GME_XML_LOC_REF)) {
            nameFound = true;
            gmeFound = true;
          }
        }
        if(!nameFound) {
          tempItems.add(new ValidationWarning
                        (PropertyAccessor.getProperty
                         ("gme.missing.xml.loc.ref"), de));
        }

      }
    }

    List<ObjectClassRelationship> ocrs = elements.getElements(DomainObjectFactory.newObjectClassRelationship().getClass());
    if(ocrs != null) {   
      for(ObjectClassRelationship ocr : ocrs) {  
        
        // don't look at generalizations
        if(ocr.getType().equals(ObjectClassRelationship.TYPE_IS))
          continue;

        boolean srcFound = false, targetFound = false;
        for(AlternateName an : ocr.getAlternateNames()) {
          if(an.getType().equals(AlternateName.TYPE_GME_SRC_XML_LOC_REF)) {
            srcFound = true;
            gmeFound = true;
          } else if(an.getType().equals(AlternateName.TYPE_GME_TARGET_XML_LOC_REF)) {
            targetFound = true;
            gmeFound = true;
          } 
        }
        if(!srcFound) {
          tempItems.add(new ValidationWarning
                        (PropertyAccessor.getProperty
                         ("gme.missing.src.loc.ref"), ocr));
        }
        if(!targetFound) {
          tempItems.add(new ValidationWarning
                        (PropertyAccessor.getProperty
                         ("gme.missing.target.loc.ref"), ocr));
        }

      }
    }
    
    
    // we found at least one gme tag, so print error for all
    if(gmeFound) {
      for(ValidationItem item : tempItems)
        items.addItem(item);
    }

    return items;
  }
}
      