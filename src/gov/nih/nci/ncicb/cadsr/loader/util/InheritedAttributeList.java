package gov.nih.nci.ncicb.cadsr.loader.util;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gov.nih.nci.ncicb.cadsr.domain.DataElement;

public class InheritedAttributeList 
{

  private static InheritedAttributeList instance = new InheritedAttributeList();

  private Map<DataElement, DataElement> map = new HashMap<DataElement, DataElement>();

  private InheritedAttributeList() {}

  public static InheritedAttributeList getInstance() 
  {
    return instance;
  }

  public void add(DataElement de, DataElement parentDe) 
  {  
    map.put(de, parentDe);
  }
  
  public boolean isInherited(DataElement de) 
  {
    return map.containsKey(de);
  }

  public DataElement getParent(DataElement de) {
    return map.get(de);
  }
  
}
