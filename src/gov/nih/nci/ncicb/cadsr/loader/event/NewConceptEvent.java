package gov.nih.nci.ncicb.cadsr.loader.event;

/**
 * Wrapper for Concept Info
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class NewConceptEvent implements LoaderEvent {

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
   * Qualifier Tagged Value prepender. 
   */
  public static final String TV_QUALIFIER = "Qualifier";

  /**
   * Qualifier Tagged Value prepender. 
   */
  public static final String TYPE_CLASS = "ObjectClass";

  /**
   * Qualifier Tagged Value prepender. 
   */
  public static final String TYPE_PROPERTY = "Property";
  

  private String code;
  private String definition;
  private String preferredName;
  private String definitionSource;
  private int order;

  
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
   * Get the Order value.
   * @return the Order value.
   */
  public int getOrder() {
    return order;
  }

  /**
   * Set the Order value.
   * @param newOrder The new Order value.
   */
  public void setOrder(int newOrder) {
    this.order = newOrder;
  }

  

  /**
   * Set the Definition value.
   * @param newDefinition The new Definition value.
   */
  public void setConceptDefinition(String newDefinition) {
    this.definition = newDefinition;
  }

  
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