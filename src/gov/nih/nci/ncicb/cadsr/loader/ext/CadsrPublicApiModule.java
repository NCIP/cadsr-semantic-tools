package gov.nih.nci.ncicb.cadsr.loader.ext;

import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import java.util.*;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.FetchMode;

import gov.nih.nci.system.applicationservice.ApplicationService;

import org.apache.log4j.Logger;



/**
 * Layer to the EVS external API.
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class CadsrPublicApiModule {

  private static String serviceURL = null;

  private static ApplicationService service = null;

  private Logger logger = Logger.getLogger(CadsrPublicApiModule.class.getName());


//   public static List<gov.nih.nci.ncicb.cadsr.domain.DataElement>
//     findDataElements(gov.nih.nci.ncicb.cadsr.domain.DataElement de) {

//     return null;

//   }

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

    DetachedCriteria criteria = DetachedCriteria.forClass(gov.nih.nci.cadsr.domain.impl.ClassificationSchemeImpl.class, "cs");

    prepareCriteria(criteria, queryFields, eager);

    List listResult = new ArrayList(new HashSet(service.query(criteria, gov.nih.nci.cadsr.domain.impl.ClassificationSchemeImpl.class.getName())));

    return CadsrTransformer.csListPublicToPrivate(listResult);
  }

  public Collection<gov.nih.nci.ncicb.cadsr.domain.ObjectClass>
    findObjectClass(Map<String, Object> queryFields) throws Exception {

    DetachedCriteria criteria = DetachedCriteria.forClass(gov.nih.nci.cadsr.domain.impl.ObjectClassImpl.class, "oc");

    prepareCriteria(criteria, queryFields, null);

    List listResult = new ArrayList(new HashSet(service.query(criteria, gov.nih.nci.cadsr.domain.impl.ObjectClassImpl.class.getName())));

    return CadsrTransformer.ocListPublicToPrivate(listResult);
  }

  public Collection<gov.nih.nci.ncicb.cadsr.domain.ValueDomain>
    findValueDomain(Map<String, Object> queryFields) throws Exception {

    DetachedCriteria criteria = DetachedCriteria.forClass(gov.nih.nci.cadsr.domain.impl.ValueDomainImpl.class, "vd");

    prepareCriteria(criteria, queryFields, null);

    List listResult = new ArrayList(new HashSet(service.query(criteria, gov.nih.nci.cadsr.domain.impl.ValueDomainImpl.class.getName())));

    return CadsrTransformer.vdListPublicToPrivate(listResult);
  }

  public Collection<gov.nih.nci.ncicb.cadsr.domain.Property>
    findProperty(Map<String, Object> queryFields) throws Exception {

    DetachedCriteria criteria = DetachedCriteria.forClass(gov.nih.nci.cadsr.domain.impl.PropertyImpl.class, "prop");

    prepareCriteria(criteria, queryFields, null);

    List listResult = new ArrayList(new HashSet(service.query(criteria, gov.nih.nci.cadsr.domain.impl.PropertyImpl.class.getName())));

    return CadsrTransformer.propListPublicToPrivate(listResult);
  }
  
  public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement>
    findDataElement(Map<String, Object> queryFields) throws Exception {

    DetachedCriteria criteria = DetachedCriteria.forClass(gov.nih.nci.cadsr.domain.impl.DataElementImpl.class, "de");

    prepareCriteria(criteria, queryFields, null);

System.out.println("run");

    List listResult = new ArrayList(new HashSet(service.query(criteria, gov.nih.nci.cadsr.domain.impl.DataElementImpl.class.getName())));

  System.out.println("transform");

    return CadsrTransformer.deListPublicToPrivate(listResult);
  }

  public gov.nih.nci.ncicb.cadsr.domain.DataElement 
    findDataElementByPublicId(String id, Float version) throws Exception {
    
    DetachedCriteria deCriteria = DetachedCriteria.forClass(gov.nih.nci.cadsr.domain.impl.DataElementImpl.class, "de");
    
    deCriteria.add(Expression.eq("publicID", new Long(id)));
    deCriteria.add(Expression.eq("version", version));
    
    List listResult = service.query(deCriteria, gov.nih.nci.cadsr.domain.impl.DataElementImpl.class.getName());
    
    if(listResult.size() > 0) {
      gov.nih.nci.cadsr.domain.DataElement qResult = (gov.nih.nci.cadsr.domain.DataElement)listResult.get(0);
      return CadsrTransformer.dePublicToPrivate(qResult);
    } else
      return null;
  }

  public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> 
    findDEByClassifiedAltName(gov.nih.nci.ncicb.cadsr.domain.AlternateName altName, gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem csCsi) throws Exception {
    
    DetachedCriteria deCriteria = DetachedCriteria.forClass(gov.nih.nci.cadsr.domain.impl.DataElementImpl.class, "de");
    
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
    
    List listResult = service.query(deCriteria, gov.nih.nci.cadsr.domain.impl.DataElementImpl.class.getName());
    
    if(listResult.size() > 0) {
//       gov.nih.nci.cadsr.domain.DataElement qResult = (gov.nih.nci.cadsr.domain.DataElement)listResult.get(0);
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
    CadsrPublicApiModule testModule = new CadsrPublicApiModule("http://cabio.nci.nih.gov/cacore30/server/HTTPServer");
    try {
//       gov.nih.nci.ncicb.cadsr.domain.DataElement de = testModule.findDataElementByPublicId("2223838", 3f);
//       if(de == null)
//         System.out.println("no result found");
//       else
//         System.out.println("result : " + de.getLongName());
      
      Map<String, Object> queryFields = new HashMap<String, Object>();
      queryFields.put("longName", "Person Name Prefix Text");
//      queryFields.put("version", new Float(3f));
      
      List<String> eager = new ArrayList<String>();
//       eager.add("classSchemeClassSchemeItemCollection");

      System.out.println("searching");

      Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> list = testModule.findDataElement(queryFields);
      
      System.out.println(list.size());
      for(gov.nih.nci.ncicb.cadsr.domain.DataElement o : list) {
        System.out.println(o.getPreferredName());
        System.out.println(o.getPublicId());
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

}