package gov.nih.nci.ncicb.cadsr.loader.event;

public class NewConceptualEvent implements LoaderEvent {

  /**
   * Tagged Value name for Concept Code
   */
  public static final String TV_CONCEPT_CODE = "ConceptCode";

  /**
   * Tagged Value name for Concept Source
   */
//   public static final String TV_CONCEPT_SOURCE = "ConceptSource";

  /**
   * Tagged Value name for NCI Concept Code
   */
//   public static final String TV_NCI_CONCEPT_CODE = "NCIConceptCode";

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
   * Tagged Value name for NCI Concept Definition
   */
//   public static final String TV_NCI_CONCEPT_DEFINITION = "NCIConceptDefinition";

  private String code;
//   private String nciCode;
//   private String source;
  private String definition;
  private String preferredName;
  private String definitionSource;
//   private String nciDefinition;

//   /**
//    * Get the NciDefinition value.
//    * @return the NciDefinition value.
//    */
//   public String getNciConceptDefinition() {
//     return nciDefinition;
//   }

//   /**
//    * Set the NciDefinition value.
//    * @param newNciDefinition The new NciDefinition value.
//    */
//   public void setNciConceptDefinition(String newNciDefinition) {
//     this.nciDefinition = newNciDefinition;
//   }

  
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