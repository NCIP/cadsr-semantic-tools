package gov.nih.nci.ncicb.cadsr.loader.ext;
import gov.nih.nci.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import java.util.*;

/**
 * Transforms cadsr public API to private API and vice versa
 * <br>
 *
 * THIS IS NOT A COMPLETE TRANSFORMER. Currently only meant to support UML Loader / SIW.
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class CadsrTransformer {

  /**
   * Transforms a DE from public API to private API
   */
  public static gov.nih.nci.ncicb.cadsr.domain.DataElement dePublicToPrivate(gov.nih.nci.cadsr.domain.DataElement inDE) {

    gov.nih.nci.ncicb.cadsr.domain.DataElement outDE = DomainObjectFactory.newDataElement();    

    acPublicToPrivate(outDE, inDE);
    
    gov.nih.nci.cadsr.domain.DataElementConcept dec = inDE.getDataElementConcept();

    outDE.setDataElementConcept(decPublicToPrivate(dec));
    
    outDE.setValueDomain(vdPublicToPrivate(inDE.getValueDomain()));
    
    

    return outDE;
  }

  public static gov.nih.nci.ncicb.cadsr.domain.Concept conceptPublicToPrivate(gov.nih.nci.cadsr.domain.Concept inConcept) {
    
    gov.nih.nci.ncicb.cadsr.domain.Concept outConcept = DomainObjectFactory.newConcept();    
    acPublicToPrivate(outConcept, inConcept);
    
    return outConcept;
  }

  /**
   * Transforms a DEC from public API to private API
   */
  public static gov.nih.nci.ncicb.cadsr.domain.DataElementConcept decPublicToPrivate(gov.nih.nci.cadsr.domain.DataElementConcept inDEC) {

    gov.nih.nci.ncicb.cadsr.domain.DataElementConcept outDEC = DomainObjectFactory.newDataElementConcept();    

    acPublicToPrivate(outDEC, inDEC);
    
    if(inDEC.getObjectClass() != null)
      outDEC.setObjectClass(ocPublicToPrivate(inDEC.getObjectClass()));
    if(inDEC.getProperty() != null)
      outDEC.setProperty(propPublicToPrivate(inDEC.getProperty()));

    if(inDEC.getConceptualDomain() != null)
      outDEC.setConceptualDomain(cdPublicToPrivate(inDEC.getConceptualDomain()));

    return outDEC;
  }


  /**
   * Transforms an OC from public API to private API
   */
  public static gov.nih.nci.ncicb.cadsr.domain.ObjectClass ocPublicToPrivate(gov.nih.nci.cadsr.domain.ObjectClass inOC) {

    gov.nih.nci.ncicb.cadsr.domain.ObjectClass outOC = DomainObjectFactory.newObjectClass();    
    
    acPublicToPrivate(outOC, inOC);

    return outOC;
  }


  /**
   * Transforms a Prop from public API to private API
   */
  public static gov.nih.nci.ncicb.cadsr.domain.Property propPublicToPrivate(gov.nih.nci.cadsr.domain.Property inProp) {

    gov.nih.nci.ncicb.cadsr.domain.Property outProp = DomainObjectFactory.newProperty();    
    acPublicToPrivate(outProp, inProp);

    return outProp;
  }
  
  /**
   * Transforms a Value Domain from public API to private API
   */
  public static gov.nih.nci.ncicb.cadsr.domain.ValueDomain vdPublicToPrivate(gov.nih.nci.cadsr.domain.ValueDomain inVD) {

    gov.nih.nci.ncicb.cadsr.domain.ValueDomain outVD = DomainObjectFactory.newValueDomain();    

    acPublicToPrivate(outVD, inVD);
    
    if(inVD.getConceptualDomain() != null)
      outVD.setConceptualDomain(cdPublicToPrivate(inVD.getConceptualDomain()));
    if(inVD.getRepresention() != null)
      outVD.setRepresentation(repPublicToPrivate(inVD.getRepresention()));
    
    outVD.setDataType(inVD.getDatatypeName());
    
    return outVD;

  }

  public static gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain cdPublicToPrivate(gov.nih.nci.cadsr.domain.ConceptualDomain inCD) {

    gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain outCD = DomainObjectFactory.newConceptualDomain();    

    acPublicToPrivate(outCD, inCD);

    return outCD;
  }

  public static gov.nih.nci.ncicb.cadsr.domain.Representation repPublicToPrivate(gov.nih.nci.cadsr.domain.Representation inRep) {

    gov.nih.nci.ncicb.cadsr.domain.Representation outRep = DomainObjectFactory.newRepresentation();    

    acPublicToPrivate(outRep, inRep);

    return outRep;
  }

  public static List<gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain> cdListPublicToPrivate(List<gov.nih.nci.cadsr.domain.ConceptualDomain> inCDs) {

    List<gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain> result = 
      new ArrayList<gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain>();

   // for(gov.nih.nci.cadsr.domain.ConceptualDomain inCD : inCDs) {
   //   result.add(cdPublicToPrivate(inCD));
   // }
    for(int i=0;i<inCDs.size();i++){
    	result.add(cdPublicToPrivate(inCDs.get(i)));
    }
    return result;
  }

  public static List<gov.nih.nci.ncicb.cadsr.domain.Representation> repListPublicToPrivate(List<gov.nih.nci.cadsr.domain.Representation> inReps) {

    List<gov.nih.nci.ncicb.cadsr.domain.Representation> result = 
      new ArrayList<gov.nih.nci.ncicb.cadsr.domain.Representation>();

 //   for(gov.nih.nci.cadsr.domain.Representation inRep : inReps) {
   //   result.add(repPublicToPrivate(inRep));
   // }
    for(int i=0;i<inReps.size();i++){
    	result.add(repPublicToPrivate(inReps.get(i)));
    }
    return result;
  }

  

  /**
   * Copies values from inAc to outAc
   */
  public static void acPublicToPrivate(gov.nih.nci.ncicb.cadsr.domain.AdminComponent outAc, gov.nih.nci.cadsr.domain.AdministeredComponent inAc) {

    gov.nih.nci.ncicb.cadsr.domain.Lifecycle lc = DomainObjectFactory.newLifecycle();
    gov.nih.nci.ncicb.cadsr.domain.Audit audit = DomainObjectFactory.newAudit();
    outAc.setLifecycle(lc);
    outAc.setAudit(audit);
    
    outAc.setId(inAc.getId());
    outAc.setPreferredName(inAc.getPreferredName());
    outAc.setPreferredDefinition(inAc.getPreferredDefinition());
    outAc.setLongName(inAc.getLongName());
    outAc.setVersion(inAc.getVersion());
    outAc.setWorkflowStatus(inAc.getWorkflowStatusName());
    outAc.setLatestVersionIndicator(inAc.getLatestVersionIndicator());
    outAc.getLifecycle().setBeginDate(inAc.getBeginDate());
    outAc.getLifecycle().setEndDate(inAc.getEndDate());
    outAc.setDeletedIndicator(inAc.getDeletedIndicator());
    outAc.setChangeNote(inAc.getChangeNote());
    outAc.setOrigin(inAc.getOrigin());
    outAc.getAudit().setCreationDate(inAc.getDateCreated());
    outAc.getAudit().setCreatedBy(inAc.getCreatedBy());
    outAc.getAudit().setModificationDate(inAc.getDateModified());
    outAc.getAudit().setModifiedBy(inAc.getModifiedBy());

    if(inAc.getPublicID() != null)
      outAc.setPublicId(inAc.getPublicID().toString());
    outAc.setContext(contextPublicToPrivate(inAc.getContext()));
    
  }

  /**
   * Transforms a Context from public API to private API
   */
  public static gov.nih.nci.ncicb.cadsr.domain.Context contextPublicToPrivate(gov.nih.nci.cadsr.domain.Context inContext) {

    gov.nih.nci.ncicb.cadsr.domain.Context outContext = DomainObjectFactory.newContext();    
    
    outContext.setName(inContext.getName());
    outContext.setId(inContext.getId());

    return outContext;

  }

  /**
   * Transforms a Context List from public API to private API
   */
  public static Collection<gov.nih.nci.ncicb.cadsr.domain.Context> contextListPublicToPrivate(List<gov.nih.nci.cadsr.domain.Context> inContexts) {

    List<gov.nih.nci.ncicb.cadsr.domain.Context> outContexts = 
      new ArrayList<gov.nih.nci.ncicb.cadsr.domain.Context>();

  //  for(gov.nih.nci.cadsr.domain.Context _con : inContexts) {
  //    outContexts.add(contextPublicToPrivate(_con));
  //  }
    for(int i=0;i<inContexts.size();i++){
    	outContexts.add(contextPublicToPrivate(inContexts.get(i)));
    }

    return outContexts;

  }

  /**
   * Transforms a DE List from public API to private API
   */
  public static Collection<gov.nih.nci.ncicb.cadsr.domain.DataElement> deListPublicToPrivate(List<gov.nih.nci.cadsr.domain.DataElement> inDEs) {

    List<gov.nih.nci.ncicb.cadsr.domain.DataElement> outDEs = 
      new ArrayList<gov.nih.nci.ncicb.cadsr.domain.DataElement>();

   /* for(gov.nih.nci.cadsr.domain.DataElement privateDe : inDEs) {
      outDEs.add(dePublicToPrivate(privateDe));
    } */
    
    for(int i=0;i<inDEs.size();i++){
    	outDEs.add(dePublicToPrivate(inDEs.get(i)));
    }
    return outDEs;

  }


  public static gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme csPublicToPrivate(gov.nih.nci.cadsr.domain.ClassificationScheme inCs) {
    
    gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme outCs = DomainObjectFactory.newClassificationScheme();

    acPublicToPrivate(outCs, inCs);

    try {
      for(Iterator it = inCs.getClassSchemeClassSchemeItemCollection().iterator(); it.hasNext(); ) {
        gov.nih.nci.cadsr.domain.ClassSchemeClassSchemeItem csCsi = (gov.nih.nci.cadsr.domain.ClassSchemeClassSchemeItem)it.next();
        outCs.addCsCsi(csCsiPublicToPrivate(csCsi));

      }
    } catch (org.hibernate.LazyInitializationException e){
      // TODO
      // these used to be one of those OK exceptions that SDK would throw. Need to check if still true. 
    } 

    return outCs;

  }

  /**
   * Transforms a CS List from public API to private API
   */
  public static Collection<gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme> csListPublicToPrivate(List<gov.nih.nci.cadsr.domain.ClassificationScheme> inCSs) {

    List<gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme> outCSs = 
      new ArrayList<gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme>();

 //   for(gov.nih.nci.cadsr.domain.ClassificationScheme privateCs : inCSs) {
 //     outCSs.add(csPublicToPrivate(privateCs));
 //   }
    for(int i=0;i<inCSs.size();i++){
    	outCSs.add(csPublicToPrivate(inCSs.get(i)));
    }
    return outCSs;

  }

  /**
   * Transforms an OC List from public API to private API
   */
  public static Collection<gov.nih.nci.ncicb.cadsr.domain.ObjectClass> ocListPublicToPrivate(List<gov.nih.nci.cadsr.domain.ObjectClass> inOCs) {

    List<gov.nih.nci.ncicb.cadsr.domain.ObjectClass> outOCs = 
      new ArrayList<gov.nih.nci.ncicb.cadsr.domain.ObjectClass>();

 //   for(gov.nih.nci.cadsr.domain.ObjectClass privateOC : inOCs) {
 //     outOCs.add(ocPublicToPrivate(privateOC));
 //   }
    for(int i=0;i<inOCs.size();i++){
    	outOCs.add(ocPublicToPrivate(inOCs.get(i)));
    }
    return outOCs;

  }

  /**
   * Transforms an Property List from public API to private API
   */
  public static Collection<gov.nih.nci.ncicb.cadsr.domain.Property> propListPublicToPrivate(List<gov.nih.nci.cadsr.domain.Property> inProps) {

    List<gov.nih.nci.ncicb.cadsr.domain.Property> outProps = 
      new ArrayList<gov.nih.nci.ncicb.cadsr.domain.Property>();

 //   for(gov.nih.nci.cadsr.domain.Property privateProp : inProps) {
 //     outProps.add(propPublicToPrivate(privateProp));
 //   }
    for(int i=0;i<inProps.size();i++){
    	outProps.add(propPublicToPrivate(inProps.get(i)));
    }
    return outProps;

  }
  
  /**
   * Transforms a Value Domain List from public API to private API
   */
  public static Collection<gov.nih.nci.ncicb.cadsr.domain.ValueDomain> vdListPublicToPrivate(List<gov.nih.nci.cadsr.domain.ValueDomain> inVDs) {

    List<gov.nih.nci.ncicb.cadsr.domain.ValueDomain> outVDs = 
      new ArrayList<gov.nih.nci.ncicb.cadsr.domain.ValueDomain>();
    gov.nih.nci.cadsr.domain.ValueDomain vd=null;

 /*   for(gov.nih.nci.cadsr.domain.ValueDomain privateVD : inVDs) {
      outVDs.add(vdPublicToPrivate(privateVD));
    } */
    if(!inVDs.isEmpty()){
    for(int i=0;i<inVDs.size();i++){
        outVDs.add(vdPublicToPrivate(inVDs.get(i)));
    }
    }
    	    
    return outVDs;

  }

  public static gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem acCsCsiPublicToPrivate(gov.nih.nci.cadsr.domain.AdministeredComponentClassSchemeItem inAcCsCsi) {

    gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem outAcCsCsi = DomainObjectFactory.newAdminComponentClassSchemeClassSchemeItem();

    outAcCsCsi.setId(inAcCsCsi.getId());

    outAcCsCsi.setCsCsi(csCsiPublicToPrivate(inAcCsCsi.getClassSchemeClassSchemeItem()));
    
    // TODO: can't convert AcId because the public API does not expose it
    //outAcCsCsi.setAcId()
    
    return outAcCsCsi;

  }

    
  private static gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem csCsiPublicToPrivate(gov.nih.nci.cadsr.domain.ClassSchemeClassSchemeItem inCsCsi) {

    gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem outCsCsi = DomainObjectFactory.newClassSchemeClassSchemeItem();

    outCsCsi.setId(inCsCsi.getId());

    outCsCsi.setCsi(csiPublicToPrivate(inCsCsi.getClassificationSchemeItem()));

    return outCsCsi;

  }

  
  private static gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem csiPublicToPrivate(gov.nih.nci.cadsr.domain.ClassificationSchemeItem inCsi) {
    gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem outCsi = DomainObjectFactory.newClassificationSchemeItem();
    outCsi.setLongName(inCsi.getLongName());
    outCsi.setType(inCsi.getType());
    outCsi.setId(inCsi.getId());

    return outCsi;

  }

  public static gov.nih.nci.ncicb.cadsr.domain.PermissibleValue pvPublicToPrivate(gov.nih.nci.cadsr.domain.PermissibleValue inPv) {
    gov.nih.nci.ncicb.cadsr.domain.PermissibleValue outPv = DomainObjectFactory.newPermissibleValue();
    
    outPv.setValue(inPv.getValue());
    outPv.setValueMeaning(vmPublicToPrivate(inPv.getValueMeaning()));

    return outPv;
  }


  public static gov.nih.nci.ncicb.cadsr.domain.ValueMeaning vmPublicToPrivate(gov.nih.nci.cadsr.domain.ValueMeaning inVm) {
    gov.nih.nci.ncicb.cadsr.domain.ValueMeaning outVm = DomainObjectFactory.newValueMeaning();
    
//    outVm.setValue(inVm.getShortMeaning());

    return outVm;
  }

  public static Collection<gov.nih.nci.ncicb.cadsr.domain.PermissibleValue> pvListPublicToPrivate(List<gov.nih.nci.cadsr.domain.PermissibleValue> inPvs) {

    List<gov.nih.nci.ncicb.cadsr.domain.PermissibleValue> outPvs = 
      new ArrayList<gov.nih.nci.ncicb.cadsr.domain.PermissibleValue>();

   /* for(gov.nih.nci.cadsr.domain.PermissibleValue privatePv : inPvs) {
      outPvs.add(pvPublicToPrivate(privatePv));
    }  */
    
    for(int i=0;i<inPvs.size();i++){
    	outPvs.add(pvPublicToPrivate(inPvs.get(i)));
    }
    return outPvs;

  }

  public static List<gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship> ocrListPublicToPrivate(List<gov.nih.nci.cadsr.domain.ObjectClassRelationship> inOCRs) {
  
    List<gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship> outOCRs = 
      new ArrayList<gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship>();
    
    
 /*   for(gov.nih.nci.cadsr.domain.ObjectClassRelationship inOCR : inOCRs) {
      outOCRs.add(ocrPublicToPrivate(inOCR));
    }  */
    for(int i=0;i<inOCRs.size();i++){
    	outOCRs.add(ocrPublicToPrivate(inOCRs.get(i)));
    }

    return outOCRs;
    
  }

  /**
   * Transforms a OCR from public API to private API
   */
  public static gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship ocrPublicToPrivate(gov.nih.nci.cadsr.domain.ObjectClassRelationship inOCR) {

    gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship outOCR = DomainObjectFactory.newObjectClassRelationship();    

    acPublicToPrivate(outOCR, inOCR);
    
    outOCR.setSourceRole(inOCR.getSourceRole());
    outOCR.setTargetRole(inOCR.getTargetRole());
    

    outOCR.setSourceHighCardinality(inOCR.getSourceHighMultiplicity());
    outOCR.setSourceLowCardinality(inOCR.getSourceLowMultiplicity());

    outOCR.setTargetHighCardinality(inOCR.getTargetHighMultiplicity());
    outOCR.setTargetLowCardinality(inOCR.getTargetLowMultiplicity());
        
    return outOCR;

  }
  

  public static List<gov.nih.nci.ncicb.cadsr.domain.AlternateName> anListPublicToPrivate(Collection<gov.nih.nci.cadsr.domain.Designation> inANs) {
    List<gov.nih.nci.ncicb.cadsr.domain.AlternateName> outANs = 
      new ArrayList<gov.nih.nci.ncicb.cadsr.domain.AlternateName>();

    
/*    for(gov.nih.nci.cadsr.domain.Designation inAn : inANs) {
      outANs.add(anPublicToPrivate(inAn));
    } */
    for(int i=0;i<inANs.size();i++){
    	outANs.add(anPublicToPrivate(inANs.iterator().next()));
    }

    return outANs;
  }

  public static gov.nih.nci.ncicb.cadsr.domain.AlternateName anPublicToPrivate(gov.nih.nci.cadsr.domain.Designation inAn) {
  
      gov.nih.nci.ncicb.cadsr.domain.AlternateName outAn = DomainObjectFactory.newAlternateName();
      
      outAn.setName(inAn.getName());
      outAn.setType(inAn.getType());

      for(gov.nih.nci.cadsr.domain.DesignationClassSchemeItem desCsi : inAn.getDesignationClassSchemeItemCollection()) {
        outAn.addCsCsi(csCsiPublicToPrivate(desCsi.getClassSchemeClassSchemeItem()));
      }
      
      return outAn;

  }
}