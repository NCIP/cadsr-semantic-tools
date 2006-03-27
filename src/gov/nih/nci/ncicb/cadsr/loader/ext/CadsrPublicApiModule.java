package gov.nih.nci.ncicb.cadsr.loader.ext;

import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;

import java.util.*;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.FetchMode;

import gov.nih.nci.system.applicationservice.ApplicationService;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;

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
//         field = ((Long)field).toString();
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
//     subCriteria.add(Example.create(CadsrTransformer.altNamePrivateToPublic(altName)));
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
  


  public void setServiceURL(String url) {
    this.serviceURL = url;
    service = ApplicationService.getRemoteInstance(serviceURL);
  }

  private void prepareCriteria(DetachedCriteria criteria, Map<String, Object> queryFields, List<String> eager) {
    for(String field : queryFields.keySet()) {
      Object o = queryFields.get(field);
      if(o instanceof String) {
        String s = (String)o;
        s = s.replace('*', '%');
        if(s.indexOf("%") != -1) {
          criteria.add(Expression.like(field, s));
          continue;
        }
      } 
      criteria.add(Expression.eq(field, o));
    }

    if(eager != null) {
      for(String s : eager) {
        criteria.setFetchMode(s, FetchMode.JOIN);
      }
    }

  }

  public static void main(String[] args) {
    CadsrModule testModule = new CadsrPublicApiModule("http://cabio-stage.nci.nih.gov/cacore31/http/remoteService");
    try {

      System.out.println("Test Find CS");
      {
        Map<String, Object> queryFields = new HashMap<String, Object>();
        queryFields.put(CadsrModule.LONG_NAME, "Transcription Annotation Prioritization and Screening System");
        queryFields.put(CadsrModule.VERSION, 1f);

        Collection<gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme> list = testModule.findClassificationScheme(queryFields);
        
        System.out.println(list.size());
        for(gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme o : list) {
          System.out.println(o.getPreferredName());
          System.out.println(o.getPublicId());
        }
        
      }

      System.out.println("Test Find VD");
      {
        Map<String, Object> queryFields = new HashMap<String, Object>();
        queryFields.put(CadsrModule.LONG_NAME, "java.lang.*");

        Collection<gov.nih.nci.ncicb.cadsr.domain.ValueDomain> list = testModule.findValueDomain(queryFields);
        
        System.out.println(list.size());
        for(gov.nih.nci.ncicb.cadsr.domain.ValueDomain o : list) {
          System.out.println(o.getPreferredName());
          System.out.println(o.getPublicId());
        }
        
      }

      System.out.println("Test Find DE");
      {
        Map<String, Object> queryFields = new HashMap<String, Object>();
        queryFields.put(CadsrModule.LONG_NAME, "Patient*");

        Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> list = testModule.findDataElement(queryFields);
        
        System.out.println(list.size());
        for(gov.nih.nci.ncicb.cadsr.domain.DataElement o : list) {
          System.out.println(o.getPreferredName());
          System.out.println(o.getPublicId());
        }
        
      }


    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

}