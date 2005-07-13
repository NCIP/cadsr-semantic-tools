package gov.nih.nci.ncicb.cadsr.evs;
import java.util.List;

public class EVSConcept  {
  private String preferredName;
  private String code;
  private List synonyms;
  private List definitions;
  
  public EVSConcept() {
  }


  public void setPreferredName(String preferredName) {
    this.preferredName = preferredName;
  }


  public String getPreferredName() {
    return preferredName;
  }


  public void setCode(String code) {
    this.code = code;
  }


  public String getCode() {
    return code;
  }


  public void setSynonyms(List synonyms) {
    this.synonyms = synonyms;
  }


  public List getSynonyms() {
    return synonyms;
  }


  public void setDefinitions(List definitions) {
    this.definitions = definitions;
  }


  public List getDefinitions() {
    return definitions;
  }
}