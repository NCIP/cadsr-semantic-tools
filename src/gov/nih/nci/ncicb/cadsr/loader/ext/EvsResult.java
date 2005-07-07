package gov.nih.nci.ncicb.cadsr.loader.ext;
import gov.nih.nci.ncicb.cadsr.domain.Concept;

public class EvsResult {
  
  private Concept concept;
  private String[] synonyms;

  public EvsResult(Concept concept, String[] synonyms) {
    this.concept = concept;
    this.synonyms = synonyms;
  }

  public Concept getConcept() {
    return concept;
  }
  public String[] getSynonyms() {
    return synonyms;
  }

}