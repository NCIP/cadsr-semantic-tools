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
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;

import gov.nih.nci.ncicb.cadsr.loader.util.*;

import org.apache.log4j.Logger;

/**
 * Validates presence of repTerm ID / Version in the VD. 
 * At this point, this is a requirement in UML Loader only, which is why this is removed from the main VD validator.
 */
public class ValueDomainRepTermValidator implements Validator {

  private ElementsLists elements = ElementsLists.getInstance();
  private ValidationItems items = ValidationItems.getInstance();
  private ProgressListener progressListener;
  private Logger logger = Logger.getLogger(ValueDomainRepTermValidator.class.getName());

  public ValueDomainRepTermValidator() {
  }

  public void addProgressListener(ProgressListener l) {
    progressListener = l;
  }
  
  private void fireProgressEvent(ProgressEvent evt) {
    if(progressListener != null)
      progressListener.newProgressEvent(evt);
  }

  public ValidationItems validate() {

    Boolean ignoreVD = (Boolean)UserSelections.getInstance().getProperty("ignore-vd");
    if(ignoreVD == null)
      ignoreVD = false;

    List<ValueDomain> vds = elements.getElements(DomainObjectFactory.newValueDomain());
    if(vds != null) {
      ProgressEvent evt = new ProgressEvent();
      evt.setMessage("Validating Value Domains ...");
      evt.setGoal(vds.size());
      evt.setStatus(0);
      fireProgressEvent(evt);
      int count = 1;
      for(ValueDomain vd : vds) {
        evt = new ProgressEvent();
        evt.setMessage(" Validating " + vd.getLongName());
        evt.setStatus(count++);
        fireProgressEvent(evt);

        if(vd.getRepresentation() != null) {
          if(StringUtil.isEmpty(vd.getRepresentation().getPublicId())) {
            items.addItem
              (new ValidationError
               (PropertyAccessor.getProperty
                ("vd.missing.repTermId", vd.getLongName()), vd));
          } 
          
          if(vd.getRepresentation().getVersion() == null) {
            items.addItem
              (new ValidationError
               (PropertyAccessor.getProperty
                ("vd.missing.repTermVersion", vd.getLongName()), vd));
          } 
        }
      }
    }
    return items;
  }
}