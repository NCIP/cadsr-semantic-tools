package gov.nih.nci.ncicb.cadsr.loader.ext;

import gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CadsrModule
{

  public static final String LONG_NAME = "longName";
  public static final String PUBLIC_ID = "publicId";
  public static final String VERSION = "version";
  public static final String CONTEXT = "context";
  public static final String WORKFLOW_STATUS = "workflowStatus";


  public Collection<gov.nih.nci.ncicb.cadsr.domain.Context> 
    getAllContexts();
  public Collection<String> 
    getAllDatatypes();
    
  public Collection<gov.nih.nci.ncicb.cadsr.domain.Representation>
    findPreferredRepTerms() throws Exception;

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

  public Collection<gov.nih.nci.ncicb.cadsr.domain.Representation>
    findRepresentation(Map<String, Object> queryFields) throws Exception;
  
  public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement>
    findDataElement(Map<String, Object> queryFields) throws Exception;
  
//   public gov.nih.nci.ncicb.cadsr.domain.DataElement 
//     findDataElementByPublicId(String id, Float version) throws Exception;

  public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> 
    findDEByClassifiedAltName(gov.nih.nci.ncicb.cadsr.domain.AlternateName altName, gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem csCsi) throws Exception;

  public Collection<gov.nih.nci.ncicb.cadsr.domain.ObjectClass> 
    findOCByClassifiedAltName(gov.nih.nci.ncicb.cadsr.domain.AlternateName altName, gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem csCsi) throws Exception;

//  public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> 
//    findDataElement(Concept[] ocConcepts, Concept[] propConcepts, 
//    String vdLongName) throws Exception;

  public Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> 
    suggestDataElement(String className, String attrName) throws Exception;

  public List<gov.nih.nci.ncicb.cadsr.domain.PermissibleValue> getPermissibleValues(gov.nih.nci.ncicb.cadsr.domain.ValueDomain vd)
    throws Exception;

  /**
   * @param oc ObjectClass to look for. PubID must be set.
   * @return ordered list of concept (primary first)
   */
  public List<gov.nih.nci.ncicb.cadsr.domain.Concept> getConcepts(gov.nih.nci.ncicb.cadsr.domain.ObjectClass oc);

  /**
   * @param prop Property to look for. PubID must be set.
   * @return ordered list of concept (primary first)
   */
  public List<gov.nih.nci.ncicb.cadsr.domain.Concept> getConcepts(gov.nih.nci.ncicb.cadsr.domain.Property prop);


  /**
   * Verify that this CDE's property has the provided concepts
   * DE must have publicID and version
   */
  public boolean matchDEToPropertyConcepts(gov.nih.nci.ncicb.cadsr.domain.DataElement de, String[] conceptCodes) throws Exception;

  public List<gov.nih.nci.ncicb.cadsr.domain.DataElement> findDEByOCConcept(gov.nih.nci.ncicb.cadsr.domain.Concept concept);

  public List<gov.nih.nci.ncicb.cadsr.domain.DataElement> findDEByOCConcepts(gov.nih.nci.ncicb.cadsr.domain.Concept[] concepts);

  public List<gov.nih.nci.ncicb.cadsr.domain.AlternateName> getAlternateNames(gov.nih.nci.ncicb.cadsr.domain.AdminComponent ac);

  public List<ObjectClassRelationship> findOCR(ObjectClassRelationship ocr);

  public gov.nih.nci.ncicb.cadsr.domain.Concept findConceptByCode(String conceptCode);
}
