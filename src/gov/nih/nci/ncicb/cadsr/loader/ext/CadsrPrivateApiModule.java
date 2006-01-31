package gov.nih.nci.ncicb.cadsr.loader.ext;

import java.util.*;

import java.lang.reflect.Method;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.util.*;


public class CadsrPrivateApiModule implements CadsrModule
{
  public CadsrPrivateApiModule()
  {

  }


  public Collection<ClassificationScheme>
    findClassificationScheme(Map<String, Object> queryFields) throws Exception  {
    return null;
  }


  public Collection<ClassificationScheme>
    findClassificationScheme(Map<String, Object> queryFields, List<String> eager) throws Exception  {

    return null;
  }


  public Collection<ObjectClass>
    findObjectClass(Map<String, Object> queryFields) throws Exception  {
    return null;

  }


  public Collection<ValueDomain>
    findValueDomain(Map<String, Object> queryFields) throws Exception  {


    ValueDomain vd = DomainObjectFactory.newValueDomain();
    buildExample(vd, queryFields);

    return DAOAccessor.getValueDomainDAO().find(vd);

  }


  public Collection<Property>
    findProperty(Map<String, Object> queryFields) throws Exception  {
    return null;

  }


  public Collection<ConceptualDomain>
    findConceptualDomain(Map<String, Object> queryFields) throws Exception  {

    ConceptualDomain cd = DomainObjectFactory.newConceptualDomain();
    buildExample(cd, queryFields);

    return DAOAccessor.getConceptualDomainDAO().find(cd);

  }


  
  public Collection<DataElement>
    findDataElement(Map<String, Object> queryFields) throws Exception  {

    DataElement de = DomainObjectFactory.newDataElement();
    buildExample(de, queryFields);

    return DAOAccessor.getDataElementDAO().find(de);
  }

  
//   public DataElement 
//     findDataElementByPublicId(String id, Float version) throws Exception  {
//     return null;

//   }


  public Collection<DataElement> 
    findDEByClassifiedAltName(AlternateName altName, ClassSchemeClassSchemeItem csCsi) throws Exception  {
    return null;

  }



  public void setServiceURL(String url){ 
  }

  private void buildExample(Object o, Map<String, Object> queryFields) {
    for(String s : queryFields.keySet()) {
      Object field = queryFields.get(s);

      if(field instanceof String) {
        String sField = (String)field;
        field = sField.replace('*','%');
      }

      try {
        Method m = o.getClass().getMethod("set" + s.substring(0, 1).toUpperCase() + s.substring(1), field.getClass());
        m.invoke(o, field);
      } catch(Exception e) {
        e.printStackTrace();
      } // end of try-catch
    }

  }


}