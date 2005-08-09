package gov.nih.nci.ncicb.cadsr.loader.validator;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.util.DatatypeMapping;

import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import java.util.List;
public class DatatypeValidator implements Validator
{
  private ElementsLists elements;
  
  private ValidationItems items = ValidationItems.getInstance();
  
  public DatatypeValidator(ElementsLists elements)
  {
    this.elements = elements;
  }
  
  /**
   * @return a list of Validation errors.
   */
  public ValidationItems validate()
  {
    //DataElement o = DomainObjectFactory.newDataElement();
    List<DataElement> des = (List<DataElement>) elements.getElements(DomainObjectFactory.newDataElement().getClass());
    if(des != null)
      for(DataElement de : des) 
      {
        String prefName = de.getValueDomain().getPreferredName();

        if(!DatatypeMapping.getValues().contains(prefName) && !DatatypeMapping.getKeys().contains(prefName)) 
          items.addItem(new ValidationError(
                        PropertyAccessor.getProperty(
                        "validation.type.invalid",
                        new String[] {prefName, 
                                      de.getDataElementConcept().getLongName()})
                                      ,de));
                                      

      }
    
    return items;
    
  }
}