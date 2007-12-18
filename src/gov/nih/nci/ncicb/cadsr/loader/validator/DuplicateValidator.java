package gov.nih.nci.ncicb.cadsr.loader.validator;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.util.*;
import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModuleListener;
import java.util.*;

public class DuplicateValidator implements Validator, CadsrModuleListener
{
  private ElementsLists elements = ElementsLists.getInstance();
  
  private ValidationItems items = ValidationItems.getInstance();

  private CadsrModule cadsrModule;
  
  public DuplicateValidator()
  {
  }
  
  public void addProgressListener(ProgressListener l) {

  }
  
  public ValidationItems validate() {
    List<ObjectClass> ocs = elements.getElements(DomainObjectFactory.newObjectClass());
    Map<String, ObjectClass> listed = new HashMap<String, ObjectClass>();
    Map<String, ObjectClass> prefNameList = new HashMap<String, ObjectClass>();
    Map<String, ObjectClass> deListed = new HashMap<String, ObjectClass>();
    
    if(ocs != null) {
      for(ObjectClass oc : ocs) {  
        if(oc.getPublicId() != null) {
          if(listed.containsKey(oc.getPublicId()))
            items.addItem(new ValidationError
                          (PropertyAccessor.getProperty
                           ("class.same.mapping", oc.getLongName(),(listed.get(oc.getPublicId())).getLongName()),oc));
          else {
            listed.put(oc.getPublicId(), oc);

            // we also need to add the concept lists so it can be validated against OC that are mapped to concepts
            List<Concept> concepts = cadsrModule.getConcepts(oc);
            prefNameList.put(ConceptUtil.preferredNameFromConcepts(concepts), oc);
          }
        }
        else if(!StringUtil.isEmpty(oc.getPreferredName())) {
          if(prefNameList.containsKey(oc.getPreferredName()))
            items.addItem(new ValidationError
                          (PropertyAccessor.getProperty
                           ("class.same.mapping", oc.getLongName(),(prefNameList.get(oc.getPreferredName())).getLongName()),oc));
          else
            prefNameList.put(oc.getPreferredName(), oc);
        }
      } 
    }
    
    
    List<DataElement> des = elements.getElements(DomainObjectFactory.newDataElement());
    if(des != null && ocs != null) {
      for(ObjectClass oc : ocs) {
        Map<String, DataElement> deList = new HashMap<String, DataElement>();
        for(DataElement de : des) {
          if(de.getDataElementConcept().getObjectClass() == oc) {
            String conceptConcat = null;
            if(!StringUtil.isEmpty(de.getPublicId())) {
              List<Concept> concepts = cadsrModule.getConcepts(de.getDataElementConcept().getProperty());
              conceptConcat = ConceptUtil.preferredNameFromConcepts(concepts);
            } else {
              conceptConcat = de.getDataElementConcept().getProperty().getPreferredName();
            }
            if(deList.containsKey(conceptConcat)) {
              ValidationError item = new  ValidationError
                            (PropertyAccessor.getProperty
                             ("de.same.mapping", de.getDataElementConcept().getLongName(),
                              (deList.get(conceptConcat)).getDataElementConcept().getLongName()),de);
              item.setIncludeInInherited(true);
              items.addItem(item);
            } else if (!StringUtil.isEmpty(de.getDataElementConcept().getProperty().getPreferredName())){
              deList.put(de.getDataElementConcept().getProperty().getPreferredName(), de);
            }
          }
        }
      }
    }
    return items;
  }

  public void setCadsrModule(CadsrModule module) {
    this.cadsrModule = module;
  }


}