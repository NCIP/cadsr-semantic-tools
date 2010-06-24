package gov.nih.nci.ncicb.cadsr.loader.validator;
import gov.nih.nci.ncicb.cadsr.domain.Concept;

public class ConceptMismatchWrapper 
{
  static final int NAME_BY_CODE = 1,
    DEF_BY_CODE = 2,
    CODE_BY_NAME = 3,
    DEF_BY_NAME = 4;
  
  private int type;
  private Concept evsConcept;
  private Concept modelConcept;

  public ConceptMismatchWrapper(int type, Concept evsConcept, Concept modelConcept)
  {
    this.type = type;
    this.evsConcept = evsConcept;
    this.modelConcept = modelConcept;
  }
  
  public int getType() {
    return type;
  }
  
  public Concept getEvsConcept() {
    return evsConcept;
  }
  
  public Concept getModelConcept() {
    return modelConcept;
  }
}