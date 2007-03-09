package gov.nih.nci.ncicb.cadsr.loader.util;

import java.util.List;
import java.util.ArrayList;

import gov.nih.nci.ncicb.cadsr.domain.DataElement;

public class InheritedAttributeList 
{

  private static InheritedAttributeList instance = new InheritedAttributeList();

  private List<DataElement> list = new ArrayList<DataElement>();

  private InheritedAttributeList() 
  {

  }

  public static InheritedAttributeList getInstance() 
  {
    return instance;
  }

  public void add(DataElement de) 
  {  
    list.add(de);
  }
  
  public boolean isInherited(DataElement de) 
  {
    return list.contains(de);
  }

}
