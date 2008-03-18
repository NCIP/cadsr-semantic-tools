package gov.nih.nci.ncicb.cadsr.loader.ext;

import gov.nih.nci.cadsr.domain.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;

/**
 * Layer to the EVS external API.
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class CadsrPublicApiModule implements CadsrModule {

  private static String serviceURL = null;

  private static ApplicationService service = null;

  private Logger logger = Logger.getLogger(CadsrPrivateApiModule.class.getName());

  public CadsrPublicApiModule() {

  }

  public CadsrPublicApiModule(String serviceURL) {
    if(serviceURL == null) {
      logger.error("caDSR Public API not initialized, please initialize it first.");
    }
    service = ApplicationService.getRemoteInstance(serviceURL);
  }

  public Collection<gov.nih.nci.ncicb.cadsr.domain.Context> 
    getAllContexts() {
    
    try {
      gov.nih.nci.cadsr.domain.Context searchContext  = new gov.nih.nci.cadsr.domain.Context();
      List listResult = new ArrayList(new HashSet(service.search(gov.nih.nci.cadsr.domain.Context.class.getName(), searchContext)));
      
      return CadsrTransformer.contextListPublicToPrivate(listResult);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } // end of try-catch

  }
  
    public Collection<String> getAllDatatypes()  {
        DetachedCriteria datatypeCriteria = DetachedCriteria.forClass(gov.nih.nci.cadsr.domain.ValueDomain.class, "vd");
        datatypeCriteria.setProjection( Projections.distinct(Projections.projectionList().add( Projections.property("vd.datatypeName"), "datatypeName" )));
        try{
            List<String> listResult = service.query(datatypeCriteria, gov.nih.nci.cadsr.domain.ValueDomain.class.getName());
            return listResult;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } // end of try-catch
    }

  public Collection<gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme>
    findClassificationScheme(Map<String, Object> queryFields) throws Exception {
    return findClassificationScheme(queryFields, null);
  }


  public Collection<gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme>
    findClassificationScheme(Map<String, Object> queryFields, List<String> eager) throws Exception {

    gov.nih.nci.cadsr.domain.ClassificationScheme searchCS = new gov.nih.nci.cadsr.domain.ClassificationScheme();

    buildExample(searchCS, queryFields);

    List listResult = new ArrayList(new HashSet(service.search(gov.nih.nci.cadsr.domain.ClassificationScheme.class.getName(), searchCS)));

    return CadsrTransformer.csListPublicToPrivate(listResult);
  }

  public Collection<gov.nih.nci.ncicb.cadsr.domain.ObjectClass>
    findObjectClass(Map<String, Object> queryFields) throws Exception {

    gov.nih.nci.cadsr.domain.ObjectClass searchOC = new gov.nih.nci.cadsr.domain.ObjectClass();

    buildExample(searchOC, queryFields);

    List listResult = new ArrayList(new HashSet(service.search(gov.nih.nci.cadsr.domain.ObjectClass.class.getName(), searchOC)));

    return CadsrTransformer.ocListPublicToPrivate(listResult);
  }

  public Collection<gov.nih.nci.ncicb.cadsr.domain.ValueDomain>
    findValueDomain(Map<String, Object> queryFields) throws Exception {

    gov.nih.nci.cadsr.domain.ValueDomain vd = new gov.nih.nci.cadsr.domain.ValueDomain();

    buildExample(vd, queryFields);

    List listResult = new ArrayList(new HashSet(service.search(gov.nih.nci.cadsr.domain.ValueDomain.class.getName(), vd)));

    return CadsrTransformer.vdListPublicToPrivate(listResult);
  }

  public Collection<gov.nih.nci.ncicb.cadsr.domain.Property>
    findProperty(Map<String, Object> queryFields) throws Exception {


    gov.nih.nci.cadsr.domain.Property searchProp = new gov.nih.nci.cadsr.domain.Property();

    buildExample(searchProp, queryFields);

    List listResult = new ArrayList(new HashSet(service.search(gov.nih.nci.cadsr.domain.Property.class.getName(), searchProp)));

    return CadsrTransformer.propListPublicToPrivate(listResult);
  }


  public Collection<gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain>
    findConceptualDomain(Map<String, Object> queryFields) throws Exception {
    
    gov.nih.nci.cadsr.domain.ConceptualDomain searchCD = new gov.nih.nci.cadsr.domain.ConceptualDomain();
    
    buildExample(searchCD, queryFields);

    List listResult = new ArrayList(new HashSet(service.search(gov.nih.nci.cadsr.domain.ConceptualDomain.class, searchCD)));
    
    return CadsrTransformer.cdListPublicToPrivate(listResult);
  }

  public Collection<gov.nih.nci.ncicb.cadsr.domain.Representation>
    findRepresentation(Map<String, Object> queryFields) throws Exception {
    
    gov.nih.nci.cadsr.domain.Representation searchRep = new gov.nih.nci.cadsr.domain.Representation();
    
    buildExample(searchRep, queryFields);

    List listResult = new ArrayList(new HashSet(service.search(gov.nih.nci.cadsr.domain.Representation.class, searchRep)));
    
    return CadsrTransformer.repListPublicToPrivate(listResult);
  }

  
  public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement>
    findDataElement(Map<String, Object> queryFields) throws Exception {
    
    gov.nih.nci.cadsr.domain.DataElement searchDE = new gov.nih.nci.cadsr.domain.DataElement();
    
    buildExample(searchDE, queryFields);

    List listResult = new ArrayList(new HashSet(service.search(gov.nih.nci.cadsr.domain.DataElement.class, searchDE)));
    
    return CadsrTransformer.deListPublicToPrivate(listResult);
  }
  
  private void buildExample(Object o, Map<String, Object> queryFields) {

    for(String s : queryFields.keySet()) {
      Object field = queryFields.get(s);
      if(s.equals("publicId")) {
        s = "publicID";
        if(field instanceof String)
          field = new Long((String)field);
      } else if(s.equals("workflowStatus")) {
          s = "workflowStatusName";
      }
      if(field instanceof String) {
        String sField = (String)field;
        field = sField.replace('%','*');
      }

      try {
        if(s.startsWith("context.")) {
          gov.nih.nci.cadsr.domain.Context context = new gov.nih.nci.cadsr.domain.Context();
          context.setName((String)field);
          Method m = o.getClass().getMethod("setContext", gov.nih.nci.cadsr.domain.Context.class);
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

 

  public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> 
    findDEByClassifiedAltName(gov.nih.nci.ncicb.cadsr.domain.AlternateName altName, gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem csCsi) throws Exception {
    
    DetachedCriteria deCriteria = DetachedCriteria.forClass(gov.nih.nci.cadsr.domain.DataElement.class, "de");
    
    DetachedCriteria subCriteria = deCriteria.createCriteria("designationCollection");
    subCriteria.add(Expression.eq("name", altName.getName()));
    subCriteria.add(Expression.eq("type", altName.getType()));


    DetachedCriteria csCsiCriteria = subCriteria.createCriteria("designationClassSchemeItemCollection")
      .createCriteria("classSchemeClassSchemeItem")
      .add(Expression.eq("id", csCsi.getId()));

//     csCsiCriteria.createCriteria("classificationScheme")
//       .add(Expression.eq("id", csCsi.getCs().getId()));

//     csCsiCriteria.createCriteria("classificationSchemeItem")
//       .add(Expression.eq("type", csCsi.getCsi().getType()))
//       .add(Expression.eq("name", csCsi.getCsi().getName()));
    
    List listResult = service.query(deCriteria, gov.nih.nci.cadsr.domain.DataElement.class.getName());
    
    if(listResult.size() > 0) {
      return CadsrTransformer.deListPublicToPrivate(listResult);
    } else
      return new ArrayList();
  }

  /**
   * Returns a collection containing the DataElement that has the ObjectClass
   * and Property specified by the given concepts, and the Value Domain
   * specified by the given long name. 
   */
  public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> 
    findDataElement(Concept[] ocConcepts, Concept[] propConcepts, 
    String vdLongName) throws Exception {

    int i = 0;
    StringBuffer ocNames = new StringBuffer();
    for(Concept concept : ocConcepts) {
        if (i++ > 0)  ocNames.append(":");
        ocNames.append(concept.getPreferredName());
    }

    int j = 0;
    StringBuffer propNames = new StringBuffer();
    for(Concept concept : propConcepts) {
        if (j++ > 0)  propNames.append(":");
        propNames.append(concept.getPreferredName());
    }
    
    DetachedCriteria criteria = DetachedCriteria.forClass(
            gov.nih.nci.cadsr.domain.DataElement.class, "de");
      
    DetachedCriteria deCriteria = criteria.createCriteria("dataElementConcept");
    DetachedCriteria vdCriteria = criteria.createCriteria("valueDomain").
            add(Expression.eq("longName", vdLongName));
      
    DetachedCriteria ocCriteria = deCriteria.createCriteria("objectClass")
            .createCriteria("conceptDerivationRule")
            .add(Expression.eq("name", ocNames.toString()));
      
    DetachedCriteria propCriteria = deCriteria.createCriteria("property")
            .createCriteria("conceptDerivationRule")
            .add(Expression.eq("name", propNames.toString()));

    Collection<gov.nih.nci.cadsr.domain.DataElement> results = 
        service.query(deCriteria, gov.nih.nci.cadsr.domain.DataElement.class.getName());

    return CadsrTransformer.deListPublicToPrivate(results);
  }

  /**
   * Returns a list containing DataElements that are good candidates for the
   * given class/attribute names. Currently, it searches for DataElements
   * which have "class:attribute" as an alternate name (designation).
   */
  public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> 
    suggestDataElement(String className, String attrName) throws Exception {

      // for now, just search on this, but we can do more searches in the future
      final String altName = className+":"+attrName;

      DetachedCriteria criteria = DetachedCriteria.forClass(
              gov.nih.nci.cadsr.domain.DataElement.class, "de");
      DetachedCriteria deCriteria = criteria.createCriteria("designationCollection").
              add(Expression.eq("name", altName));

      Collection<gov.nih.nci.cadsr.domain.DataElement> results =  
          service.query(criteria, gov.nih.nci.cadsr.domain.DataElement.class.getName());
      return CadsrTransformer.deListPublicToPrivate(results);
  }

  public List<gov.nih.nci.ncicb.cadsr.domain.PermissibleValue> getPermissibleValues(gov.nih.nci.ncicb.cadsr.domain.ValueDomain vd)
    throws Exception 
  {

    List<gov.nih.nci.cadsr.domain.PermissibleValue> result = new ArrayList<gov.nih.nci.cadsr.domain.PermissibleValue>();
    
    DetachedCriteria criteria = DetachedCriteria.forClass(
      gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue.class, "vdPv");
    
    criteria.createCriteria("enumeratedValueDomain")
      .add(Expression.eq("id", vd.getId()));
    
    Collection<gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue> vdPvs = service.query(criteria, gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue.class.getName());
    for(gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue vdPv : vdPvs) {
      result.add(vdPv.getPermissibleValue());
    }

    return new ArrayList(CadsrTransformer.pvListPublicToPrivate(result));
  }

  public List<gov.nih.nci.ncicb.cadsr.domain.Concept> getConcepts(gov.nih.nci.ncicb.cadsr.domain.ObjectClass oc) {
    ArrayList<gov.nih.nci.ncicb.cadsr.domain.Concept> result = 
      new ArrayList<gov.nih.nci.ncicb.cadsr.domain.Concept>();

    if(StringUtil.isEmpty(oc.getPublicId()))
      return result;

    try {
      gov.nih.nci.cadsr.domain.ObjectClass searchOC = new gov.nih.nci.cadsr.domain.ObjectClass();
      searchOC.setPublicID(new Long(oc.getPublicId()));
      searchOC.setVersion(oc.getVersion());
      
      List<gov.nih.nci.cadsr.domain.ObjectClass> ocs =  
        service.search(gov.nih.nci.cadsr.domain.ObjectClass.class.getName(), searchOC);

      if(ocs.size() != 1)
        return result;

      gov.nih.nci.cadsr.domain.ObjectClass resOc = ocs.iterator().next();
      Collection<gov.nih.nci.cadsr.domain.ComponentConcept> comps = resOc.getConceptDerivationRule().getComponentConceptCollection();

      for(gov.nih.nci.cadsr.domain.ComponentConcept comp : comps) {
        gov.nih.nci.cadsr.domain.Concept conc = comp.getConcept();
        gov.nih.nci.ncicb.cadsr.domain.Concept concept = DomainObjectFactory.newConcept();
        CadsrTransformer.acPublicToPrivate(concept, conc);
        result.add(0, concept);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } // end of try-catch

    return result;

    
  }
  public List<gov.nih.nci.ncicb.cadsr.domain.Concept> getConcepts(gov.nih.nci.ncicb.cadsr.domain.Property prop) {
    ArrayList<gov.nih.nci.ncicb.cadsr.domain.Concept> result = 
      new ArrayList<gov.nih.nci.ncicb.cadsr.domain.Concept>();

    if(StringUtil.isEmpty(prop.getPublicId()))
      return result;

    try {
      gov.nih.nci.cadsr.domain.Property searchProp = new gov.nih.nci.cadsr.domain.Property();
      searchProp.setPublicID(new Long(prop.getPublicId()));
      searchProp.setVersion(prop.getVersion());
      
      List<gov.nih.nci.cadsr.domain.Property> props =  
        service.search(gov.nih.nci.cadsr.domain.Property.class.getName(), searchProp);

      if(props.size() != 1)
        return result;

      gov.nih.nci.cadsr.domain.Property resProp = props.iterator().next();
      Collection<gov.nih.nci.cadsr.domain.ComponentConcept> comps = resProp.getConceptDerivationRule().getComponentConceptCollection();

      for(gov.nih.nci.cadsr.domain.ComponentConcept comp : comps) {
        gov.nih.nci.cadsr.domain.Concept conc = comp.getConcept();
        gov.nih.nci.ncicb.cadsr.domain.Concept concept = DomainObjectFactory.newConcept();
        CadsrTransformer.acPublicToPrivate(concept, conc);
        result.add(0, concept);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } // end of try-catch

    return result;
  }


  public boolean matchDEToPropertyConcepts(gov.nih.nci.ncicb.cadsr.domain.DataElement de, String[] conceptCodes) throws Exception {

    if(StringUtil.isEmpty(de.getPublicId()))
      return false;

    gov.nih.nci.cadsr.domain.DataElement searchDE = new gov.nih.nci.cadsr.domain.DataElement();
    searchDE.setPublicID(new Long(de.getPublicId()));
    searchDE.setVersion(de.getVersion());
    
    List<gov.nih.nci.cadsr.domain.DataElement> results =  
      service.search(gov.nih.nci.cadsr.domain.DataElement.class.getName(), searchDE);

    if(results.size() == 0) {
      logger.error("Can't find CDE : " + de.getPublicId() + " v " + de.getVersion() + "\\n Please contact support");
      return false;
    }

    gov.nih.nci.cadsr.domain.DataElement resultDE = results.get(0);

    gov.nih.nci.cadsr.domain.ConceptDerivationRule conDR = 
      resultDE.getDataElementConcept().getProperty().getConceptDerivationRule();
    
    Collection compConcepts = conDR.getComponentConceptCollection();
    if(compConcepts.size() != conceptCodes.length)
        return false;
    
    Iterator it = compConcepts.iterator();
    while(it.hasNext()) {
        ComponentConcept comp = (ComponentConcept)it.next();
        if(!conceptCodes[comp.getDisplayOrder()].equals(comp.getConcept().getPreferredName()))
            return false;
    }
        
    return true;

  }

  
  public void setServiceURL(String url) {
    this.serviceURL = url;
    service = ApplicationService.getRemoteInstance(serviceURL);
  }

//  private void prepareCriteria(DetachedCriteria criteria, Map<String, Object> queryFields, List<String> eager) {
//    for(String field : queryFields.keySet()) {
//      Object o = queryFields.get(field);
//      if(o instanceof String) {
//        String s = (String)o;
//        s = s.replace('*', '%');
//        if(s.indexOf("%") != -1) {
//          criteria.add(Expression.like(field, s));
//          continue;
//        }
//      } 
//      criteria.add(Expression.eq(field, o));
//    }
//
//    if(eager != null) {
//      for(String s : eager) {
//        criteria.setFetchMode(s, FetchMode.JOIN);
//      }
//    }
//
//  }

  public static void main(String[] args) {
    CadsrModule testModule = new CadsrPublicApiModule("http://cabio.nci.nih.gov/cacore32/http/remoteService");
    try {

//       System.out.println("Test Find CS");
//       {
//         Map<String, Object> queryFields = new HashMap<String, Object>();
//         queryFields.put(CadsrModule.LONG_NAME, "Transcription Annotation Prioritization and Screening System");
//         queryFields.put(CadsrModule.VERSION, 1f);

//         Collection<gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme> list = testModule.findClassificationScheme(queryFields);
        
//         System.out.println(list.size());
//         for(gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme o : list) {
//           System.out.println(o.getPreferredName());
//           System.out.println(o.getPublicId());
//         }
        
//       }

//       System.out.println("Test Find VD");
//       {
//         Map<String, Object> queryFields = new HashMap<String, Object>();
//         queryFields.put(CadsrModule.LONG_NAME, "java.lang.*");

//         Collection<gov.nih.nci.ncicb.cadsr.domain.ValueDomain> list = testModule.findValueDomain(queryFields);
        
//         System.out.println(list.size());
//         for(gov.nih.nci.ncicb.cadsr.domain.ValueDomain o : list) {
//           System.out.println(o.getPreferredName());
//           System.out.println(o.getPublicId());
//         }
        
//       }

//       System.out.println("Test Find DE");
//       {
//         Map<String, Object> queryFields = new HashMap<String, Object>();
//         queryFields.put(CadsrModule.LONG_NAME, "Patient*");

//         Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> list = testModule.findDataElement(queryFields);
        
//         System.out.println(list.size());
//         for(gov.nih.nci.ncicb.cadsr.domain.DataElement o : list) {
//           System.out.println(o.getPreferredName());
//           System.out.println(o.getPublicId());
//         }
        
//       }

//       System.out.println("Test matchDE");
//       gov.nih.nci.ncicb.cadsr.domain.DataElement de = DomainObjectFactory.newDataElement();
//       de.setPublicId("2533339");
//       String[] conceptCodes = new String[] {"C43821", "C16423"};
//       // test that should return true
//       if(testModule.matchDEToPropertyConcepts(de, conceptCodes))
//         System.out.println("ok");
//
//       conceptCodes = new String[] {"C16423", "C43821"};
//       // test that should return false
//       if(!testModule.matchDEToPropertyConcepts(de, conceptCodes))
//         System.out.println("ok");
//      

//      System.out.println("Test find OC Concepts");
//      gov.nih.nci.ncicb.cadsr.domain.ObjectClass oc = DomainObjectFactory.newObjectClass();
//      oc.setPublicId("2241624");
////       oc.setPublicId("2557779");
//      oc.setVersion(1f);
//      List<gov.nih.nci.ncicb.cadsr.domain.Concept> concepts = testModule.getConcepts(oc);
//      for(gov.nih.nci.ncicb.cadsr.domain.Concept con : concepts) {
//        System.out.println(con.getLongName());
//      }

    testModule.getAllDatatypes();

    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }


}