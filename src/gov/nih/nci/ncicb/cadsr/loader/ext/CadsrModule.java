package gov.nih.nci.ncicb.cadsr.loader.ext;

import java.util.Collection;
import java.util.Map;
import java.util.List;

public interface CadsrModule
{

  public static final String LONG_NAME = "longName";
  public static final String PUBLIC_ID = "publicId";
  public static final String VERSION = "version";
  public static final String CONTEXT = "context";
  
  public Collection<gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme>
    findClassificationScheme(Map<String, Object> queryFields) throws Exception;

  public Collection<gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme>
    findClassificationScheme(Map<String, Object> queryFields, List<String> eager) throws Exception;

  public Collection<gov.nih.nci.ncicb.cadsr.domain.ObjectClass>
    findObjectClass(Map<String, Object> queryFields) throws Exception;

  public Collection<gov.nih.nci.ncicb.cadsr.domain.ValueDomain>
    findValueDomain(Map<String, Object> queryFields) throws Exception;

  public Collection<gov.nih.nci.ncicb.cadsr.domain.Property>
    findProperty(Map<String, Object> queryFields) throws Exception;

  public Collection<gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain>
    findConceptualDomain(Map<String, Object> queryFields) throws Exception;

  
  public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement>
    findDataElement(Map<String, Object> queryFields) throws Exception;
  
//   public gov.nih.nci.ncicb.cadsr.domain.DataElement 
//     findDataElementByPublicId(String id, Float version) throws Exception;

  public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> 
    findDEByClassifiedAltName(gov.nih.nci.ncicb.cadsr.domain.AlternateName altName, gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem csCsi) throws Exception;

  public void setServiceURL(String url);

}
