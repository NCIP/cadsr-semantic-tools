package gov.nih.nci.ncicb.cadsr.loader.util;

import java.util.*;

import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;

public class InheritedAttributeList 
{

  private static InheritedAttributeList instance = new InheritedAttributeList();

  private Map<DataElement, DataElement> deMap = new HashMap<DataElement, DataElement>();
  private Map<ObjectClass, ObjectClass> ocMap = new HashMap<ObjectClass, ObjectClass>();

  private Map<ObjectClass, String> exclusionReasonMap = new HashMap<ObjectClass, String>();

  private Set<ObjectClass> excludedFromSemanticInheritance = new HashSet<ObjectClass>();

  private InheritedAttributeList() {}

  public static InheritedAttributeList getInstance() 
  {
    return instance;
  }

  public void add(DataElement de, DataElement parentDe) 
  {  
    deMap.put(de, parentDe);
  }

  public void add(ObjectClass oc, ObjectClass parentOc) 
  {  
    ocMap.put(oc, parentOc);
  }
  
  
  public boolean isInherited(DataElement de) 
  {
    return deMap.containsKey(de);
  }

  public DataElement getParent(DataElement de) {
    return deMap.get(de);
  }

  public ObjectClass getParentOc(ObjectClass oc) {
    return ocMap.get(oc);
  }

  public void excludeFromSemanticInheritance(ObjectClass oc) {
    excludedFromSemanticInheritance.add(oc);
  }
  
  public boolean isExcludedFromSemanticInheritance(ObjectClass oc) {
    return excludedFromSemanticInheritance.contains(oc);
  }

  public String getSemanticExclusionReason(ObjectClass oc) {
    return exclusionReasonMap.get(oc);
  }

  public void addReasonForSemanticExclusion(ObjectClass oc, String reason) {
    exclusionReasonMap.put(oc, reason);
  }
  
  public List<ObjectClass> getChildrenOc(ObjectClass parentOc) {
    List<ObjectClass> childrenOc = new ArrayList<ObjectClass>();
    for(ObjectClass keyOc : ocMap.keySet()) {
      if(ocMap.get(keyOc) == parentOc)
        childrenOc.add(keyOc);
    }
    return childrenOc;
  }

  public Collection<DataElement> getAllInherited() {
    return deMap.keySet();
  }

  public DataElement getTopParent(DataElement de) {
    DataElement parent = deMap.get(de);
    while (parent != null) {
      DataElement _de = deMap.get(parent);
      if(_de == null)
        return parent;
      else 
        parent = _de;
    }
    return de;
  }
  
}
