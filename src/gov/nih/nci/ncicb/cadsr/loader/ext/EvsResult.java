package gov.nih.nci.ncicb.cadsr.loader.ext;
import gov.nih.nci.ncicb.cadsr.domain.Concept;

public class EvsResult {
  
  private Concept concept;
  private String[] synonyms;
  private String conceptName;

  public EvsResult(Concept concept, String conceptName, String[] synonyms) {
    this.concept = concept;
    this.conceptName = conceptName;
    this.synonyms = synonyms;
  }

  public Concept getConcept() {
    return concept;
  }
  public String[] getSynonyms() {
    return synonyms;
  }

  public String getConceptName() {
    return conceptName;
  }

}