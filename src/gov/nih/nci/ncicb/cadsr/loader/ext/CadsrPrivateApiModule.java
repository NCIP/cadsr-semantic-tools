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
  
  public Collection<String> getAllDatatypes() {
      
      return DAOAccessor.getValueDomainDAO().getAllDatatypes();
  }

  public Collection<ClassificationScheme>
    findClassificationScheme(Map<String, Object> queryFields) throws Exception  {
    return findClassificationScheme(queryFields, null);
  }


  public Collection<ClassificationScheme>
    findClassificationScheme(Map<String, Object> queryFields, List<String> eager) throws Exception  {

    ClassificationScheme cs = DomainObjectFactory.newClassificationScheme();
    buildExample(cs, queryFields);

    // look at all WF status
    cs.setWorkflowStatus(AdminComponent.WF_STATUS_ALL);

    // set to something else than YES. Should better better solution for this
    cs.setLatestVersionIndicator("..");

    return new HashSet(DAOAccessor.getClassificationSchemeDAO().find(cs, eager));

  }


  public Collection<ObjectClass>
    findObjectClass(Map<String, Object> queryFields) throws Exception  {
    return null;

  }


  public Collection<ValueDomain>
    findValueDomain(Map<String, Object> queryFields) throws Exception  {


    ValueDomain vd = DomainObjectFactory.newValueDomain();
    buildExample(vd, queryFields);

    List<String> eager = new ArrayList<String>();
    eager.add("conceptualDomain");
    eager.add("representation");

    return DAOAccessor.getValueDomainDAO().find(vd, eager);

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

    List<Concept> concepts = 
      DAOAccessor.getObjectClassDAO().getConcepts(oc);
    
    return concepts;
    
  }
  public List<gov.nih.nci.ncicb.cadsr.domain.Concept> getConcepts(gov.nih.nci.ncicb.cadsr.domain.Property prop) {
    List<Concept> concepts = 
      DAOAccessor.getPropertyDAO().getConcepts(prop);
    
    return concepts;
  }


  public Collection<gov.nih.nci.ncicb.cadsr.domain.Representation> findPreferredRepTerms() {
    
    return null;
    
  }

  public List<gov.nih.nci.ncicb.cadsr.domain.DataElement> findDEByOCConcept(gov.nih.nci.ncicb.cadsr.domain.Concept concept) {
    
    List<DataElement> result = DAOAccessor.getDataElementDAO().findByOCConcept(concept);
    return result;

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
//       System.out.println("Test matchDE");
//       DataElement de = DomainObjectFactory.newDataElement();
//       de.setPublicId("2533339");
//       de.setVersion(1.0f);
//       String[] conceptCodes = new String[] {"C43821", "C16423"};
//       // test that should return true
//       if(testModule.matchDEToPropertyConcepts(de, conceptCodes))
//         System.out.println("ok");
//       else
//         System.out.println("no good");
      
//       conceptCodes = new String[] {"C16423", "C43821"};
//       // test that should return false
//       if(!testModule.matchDEToPropertyConcepts(de, conceptCodes))
//         System.out.println("ok");
//       else
//         System.out.println("no good");

//      System.out.println("Test find Prop Concepts");
//      gov.nih.nci.ncicb.cadsr.domain.Property prop = DomainObjectFactory.newProperty();
//      prop.setPublicId("2207095");
////       oc.setPublicId("2557779");
//      prop.setVersion(1f);
//      List<gov.nih.nci.ncicb.cadsr.domain.Concept> concepts = testModule.getConcepts(prop);
//      for(gov.nih.nci.ncicb.cadsr.domain.Concept con : concepts) {
//        System.out.println(con.getLongName());
//      }
//
//
//        Collection<String> tmp = testModule.getAllDatatypes();
//        for(String s: tmp){
//            System.out.println(" ---- "+s);
//        }

      gov.nih.nci.ncicb.cadsr.domain.Concept concept = DomainObjectFactory.newConcept();
      concept.setPreferredName("C16612");
      
      Collection<DataElement> des = testModule.findDEByOCConcept(concept);
      for(DataElement de : des) {
        System.out.println(de.getLongName());
      }



    } catch (Exception e) {
      e.printStackTrace();
   } // end of try-catch

  }


    public List<DataElement> findDEByOCConcepts(Concept[] concepts) {
        return null;
    }

    public List<DataElement> findDEByConcepts(Concept ocPrimaryConcept,
                                              Concept[] propConcepts) {
        return null;
    }

    public List<DataElement> findDEByConcepts(Concept[] ocConcepts,
                                              Concept[] propConcepts) {
        return null;
    }
}
