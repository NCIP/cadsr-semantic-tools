/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.validator;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.util.*;
import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModuleListener;
import java.util.*;

public class DuplicateClassValidator implements Validator
{
  private ElementsLists elements = ElementsLists.getInstance();
  private ValidationItems items = ValidationItems.getInstance();

  /**
   * Simple validator that checks if any 2 classes have the same name in the same package
   */
  public DuplicateClassValidator()
  {
  }
  
  public void addProgressListener(ProgressListener l) {

  }
  
  public ValidationItems validate() {
    List<ObjectClass> ocs = elements.getElements(DomainObjectFactory.newObjectClass());
    Map<String, ObjectClass> foundNames = new HashMap<String, ObjectClass>();
    
    if(ocs != null) {
      for(ObjectClass oc : ocs) {  
        ObjectClass foundOc = foundNames.get(oc.getLongName());
        if(foundOc != null) {
          items.addItem(new ValidationError
                        (PropertyAccessor.getProperty
                         ("class.same.name", oc.getLongName()), oc));

          items.addItem(new ValidationError
                        (PropertyAccessor.getProperty
                         ("class.same.name", oc.getLongName()), foundOc));
        } else {
          foundNames.put(oc.getLongName(), oc);
        }
      }
    }
    
    return items;
  }
}