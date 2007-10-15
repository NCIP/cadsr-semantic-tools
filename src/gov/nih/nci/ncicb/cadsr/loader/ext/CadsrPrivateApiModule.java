package gov.nih.nci.ncicb.cadsr.loader.ext;

import java.util.*;

import java.lang.reflect.Method;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.util.*;

import org.apache.log4j.Logger;

public class CadsrPrivateApiModule implements CadsrModule
{

  private Logger logger = Logger.getLogger(CadsrPublicApiModule.class.getName());

  public CadsrPrivateApiModule()
  {

  }

  public Collection<gov.nih.nci.ncicb.cadsr.domain.Context> 
    getAllContexts() {
    
    return DAOAccessor.getContextDAO().findAll();

  }

  public Collection<ClassificationScheme>
    findClassificationScheme(Map<String, Object> queryFields) throws Exception  {
    return findClassificationScheme(queryFields, null);
  }


  public Collection<ClassificationScheme>
    findClassificationScheme(Map<String, Object> queryFields, List<String> eager) throws Exception  {

    ClassificationScheme cs = DomainObjectFactory.newClassificationScheme();
    buildExample(cs, queryFields);

    if(eager == null)
      return DAOAccessor.getClassificationSchemeDAO().find(cs);
    else
      return DAOAccessor.getClassificationSchemeDAO().find(cs, eager);

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


  public Collection<Representation>
    findRepresentation(Map<String, Object> queryFields) throws Exception  {

    Representation rep = DomainObjectFactory.newRepresentation();
    buildExample(rep, queryFields);

    return DAOAccessor.getRepresentationDAO().find(rep);

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

    List<DataElement> des = DAOAccessor.getAdminComponentDAO().findByClassifiedAlternateName(altName, DomainObjectFactory.newDataElement(), csCsi, null);

    return des;

  }

  public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> 
    findDataElement(Concept[] ocConcepts, Concept[] propConcepts, 
    String vdLongName) throws Exception {
    
    return DAOAccessor.getDataElementDAO().find(ocConcepts, propConcepts, 
            vdLongName);
  }

  public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> 
    suggestDataElement(String className, String attrName) throws Exception {

      return DAOAccessor.getDataElementDAO().findCandidate(
              className, attrName);
  }
  
  public List<gov.nih.nci.ncicb.cadsr.domain.PermissibleValue> getPermissibleValues(gov.nih.nci.ncicb.cadsr.domain.ValueDomain vd)
    throws Exception 
  {
    return DAOAccessor.getValueDomainDAO().getPermissibleValues(
      vd.getId());
  }

  public List<gov.nih.nci.ncicb.cadsr.domain.Concept> getConcepts(gov.nih.nci.ncicb.cadsr.domain.ObjectClass oc) {
    return null;
  }
  public List<gov.nih.nci.ncicb.cadsr.domain.Concept> getConcepts(gov.nih.nci.ncicb.cadsr.domain.Property prop) {
    return null;
  }


  public boolean matchDEToPropertyConcepts(gov.nih.nci.ncicb.cadsr.domain.DataElement de, String[] conceptCodes) throws Exception {
    
    Map<String, Object> queryFields = new HashMap<String, Object>();
    queryFields.put(CadsrModule.PUBLIC_ID, de.getPublicId());
    queryFields.put(CadsrModule.VERSION, de.getVersion());

    List<DataElement> results = new ArrayList<DataElement>(findDataElement(queryFields));
    
    if(results.size() == 0) {
      logger.error("Can't find CDE : " + de.getPublicId() + " v " + de.getVersion() + "\\n Please contact support");
      return false;
    }
    
    DataElement resultDE = results.get(0);
    Property resultProp = resultDE.getDataElementConcept().getProperty();
    
    // following DAO method reads concepts in reverse order
    String[] revCodes = new String[conceptCodes.length];
    for(int i = 0; i<conceptCodes.length; i++)
      revCodes[i] = conceptCodes[revCodes.length - i - 1];

    List<Property> resultProps = DAOAccessor.getPropertyDAO().findByConceptCodes(revCodes, resultProp.getContext());
    for(Property _prop : resultProps) {
      if(_prop.getVersion().equals(resultProp.getVersion()) && 
         _prop.getPublicId().equals(resultProp.getPublicId()))
        return true;
    }
    return false;
  }
  
  public void setServiceURL(String url){ 
  }

  private void buildExample(Object o, Map<String, Object> queryFields) {
    for(String s : queryFields.keySet()) {
      Object field = queryFields.get(s);

      if(s.equals("publicId")) {
        if(field instanceof Long)
          field = ((Long)field).toString();
      }

      if(field instanceof String) {
        String sField = (String)field;
        field = sField.replace('*','%');
      }

      try {
        if(s.startsWith("context.")) {
          Context context = DomainObjectFactory.newContext();
          context.setName((String)field);
          Method m = o.getClass().getMethod("setContext", Context.class);
          m.invoke(o, context);
        } else {
          Method m = o.getClass().getMethod("set" + StringUtil.upperFirst(s), field.getClass());
          m.invoke(o, field); 
        }
      } catch(Exception e) {
        e.printStackTrace();
      } // end of try-catch
    }
  }

  public static void main(String[] args) {
    CadsrPrivateApiModule testModule = new CadsrPrivateApiModule();
    
    try {
      System.out.println("Test matchDE");
      DataElement de = DomainObjectFactory.newDataElement();
      de.setPublicId("2533339");
      de.setVersion(1.0f);
      String[] conceptCodes = new String[] {"C43821", "C16423"};
      // test that should return true
      if(testModule.matchDEToPropertyConcepts(de, conceptCodes))
        System.out.println("ok");
      else
        System.out.println("no good");
      
      conceptCodes = new String[] {"C16423", "C43821"};
      // test that should return false
      if(!testModule.matchDEToPropertyConcepts(de, conceptCodes))
        System.out.println("ok");
      else
        System.out.println("no good");

    } catch (Exception e) {
    } // end of try-catch

  }      


}