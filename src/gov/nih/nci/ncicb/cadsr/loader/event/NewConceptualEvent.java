package gov.nih.nci.ncicb.cadsr.loader.event;

/**
 * Events with a concept should extend this event
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class NewConceptualEvent implements LoaderEvent {

  /**
   * Tagged Value name for Concept Code
   */
  public static final String TV_CONCEPT_CODE = "ConceptCode";

  /**
   * Tagged Value name for Concept Preferred Name
   */
  public static final String TV_CONCEPT_PREFERRED_NAME = "ConceptPreferredName";

  /**
   * Tagged Value name for Concept Definition
   */
  public static final String TV_CONCEPT_DEFINITION = "ConceptDefinition";

  /**
   * Tagged Value name for Concept Definition Source
   */
  public static final String TV_CONCEPT_DEFINITION_SOURCE = "ConceptDefinitionSource";

  /**
   * Tagged Value name for Concept Definition Source
   */
  public static final String TV_DOCUMENTATION = "documentation";

  private String code;
  private String definition;
  private String preferredName;
  private String definitionSource;

  private String description;
  
  /**
   * Get the DefinitionSource value.
   * @return the DefinitionSource value.
   */
  public String getConceptDefinitionSource() {
    return definitionSource;
  }

  /**
   * Set the DefinitionSource value.
   * @param newDefinitionSource The new DefinitionSource value.
   */
  public void setConceptDefinitionSource(String newDefinitionSource) {
    this.definitionSource = newDefinitionSource;
  }

  

  /**
   * Get the PreferredName value.
   * @return the PreferredName value.
   */
  public String getConceptPreferredName() {
    return preferredName;
  }

  /**
   * Set the PreferredName value.
   * @param newPreferredName The new PreferredName value.
   */
  public void setConceptPreferredName(String newPreferredName) {
    this.preferredName = newPreferredName;
  }

  
  /**
   * Get the Definition value.
   * @return the Definition value.
   */
  public String getConceptDefinition() {
    return definition;
  }

  /**
   * Get the Description value.
   * @return the Description value.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Set the Description value.
   * @param newDescription The new Description value.
   */
  public void setDescription(String newDescription) {
    this.description = newDescription;
  }

  /**
   * Set the Definition value.
   * @param newDefinition The new Definition value.
   */
  public void setConceptDefinition(String newDefinition) {
    this.definition = newDefinition;
  }

  
//   /**
//    * Get the Source value.
//    * @return the Source value.
//    */
//   public String getConceptSource() {
//     return source;
//   }

//   /**
//    * Set the Source value.
//    * @param newSource The new Source value.
//    */
//   public void setConceptSource(String newSource) {
//     this.source = newSource;
//   }

  
//   /**
//    * Get the NciCode value.
//    * @return the NciCode value.
//    */
//   public String getNciConceptCode() {
//     return nciCode;
//   }

//   /**
//    * Set the NciCode value.
//    * @param newNciCode The new NciCode value.
//    */
//   public void setNciConceptCode(String newNciCode) {
//     this.nciCode = newNciCode;
//   }

  
  /**
   * Get the Code value.
   * @return the Code value.
   */
  public String getConceptCode() {
    return code;
  }

  /**
   * Set the Code value.
   * @param newCode The new Code value.
   */
  public void setConceptCode(String newCode) {
    this.code = newCode;
  }

  

}