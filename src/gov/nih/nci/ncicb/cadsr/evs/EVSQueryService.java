package gov.nih.nci.ncicb.cadsr.evs;

import gov.nih.nci.evs.domain.*;
import gov.nih.nci.evs.query.*;
import gov.nih.nci.ncicb.cadsr.evs.EVSConcept;
import gov.nih.nci.system.applicationservice.*;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.io.IOException;
import java.io.Serializable;

import java.util.*;


public class EVSQueryService {
  public static String NCI_THESAURUS_VOCAB_NAME = "NCI_Thesaurus";
  public static String SYNONYM_PROPERTY_NAME = "Synonym";
  public static String DEFINITION_PROPERTY_NAME = "DEFINITION";
  private ApplicationService evsService;
  private String cacoreServiceURL;

  public EVSQueryService() {
    this.cacoreServiceURL =
      "http://cabio-stage.nci.nih.gov/cacore30/server/HTTPServer";
    evsService = ApplicationService.getRemoteInstance(cacoreServiceURL);
  }

  public EVSQueryService(String cacoreServiceURL) {
    this.cacoreServiceURL = cacoreServiceURL;
    evsService = ApplicationService.getRemoteInstance(cacoreServiceURL);
  }

  public void setCacoreServiceURL(String cacoreServiceURL) {
    this.cacoreServiceURL = cacoreServiceURL;
  }

  public String getCacoreServiceURL() {
    return cacoreServiceURL;
  }

  public List findConceptsBySynonym(
    String searchTerm,
    boolean includeRetiredConcepts,
    int rowCount) throws Exception {
    if (cacoreServiceURL == null) {
      throw new Exception("Please specify a valid caCORE Service URL");
    }

    List results = new ArrayList();
    EVSQuery query = new EVSQueryImpl();
    query.getConceptWithPropertyMatching(
      NCI_THESAURUS_VOCAB_NAME, SYNONYM_PROPERTY_NAME, searchTerm, rowCount);

    List conceptNames = evsService.evsSearch(query);

    System.out.println("Concepts:" + conceptNames.size());

    results =
      this.findConceptDetaisByName(conceptNames, includeRetiredConcepts);

    /*for (Iterator it = conceptNames.iterator(); it.hasNext();) {
       String conceptName = (String) it.next();
       System.out.println("Element: " + conceptName);
       DescLogicConcept concept =
         this.findConceptByName(NCI_THESAURUS_VOCAB_NAME, conceptName);
       System.out.println("Concept code: " + concept.getCode());
       List synonyms = this.retrieveSynonyms(concept);
       List defs = this.retrieveDefinitions(concept);
    
       EVSConcept c = new EVSConcept();
       c.setCode(concept.getCode());
       c.setPreferredName(conceptName);
       c.setDefinitions(defs);
       c.setSynonyms(synonyms);
    
       results.add(c);
       }*/
    return results;
  }

  public List findConceptDetaisByName(
    List conceptNames,
    boolean includeRetiredConcepts) throws Exception {
    List results = new ArrayList();
    for (Iterator it = conceptNames.iterator(); it.hasNext();) {
      String conceptName = (String) it.next();
      System.out.println("Element: " + conceptName);
      DescLogicConcept concept =
        this.findDescLogicConceptByName(NCI_THESAURUS_VOCAB_NAME, conceptName);

      if (
        (includeRetiredConcepts) ||
            (!includeRetiredConcepts && !concept.isRetired().booleanValue())) {
        System.out.println("Concept code: " + concept.getCode());
        List synonyms = this.retrieveSynonyms(concept);
        List defs = this.retrieveDefinitions(concept);

        EVSConcept c = new EVSConcept();
        c.setCode(concept.getCode());
        c.setPreferredName(conceptName);
        c.setDefinitions(defs);
        c.setSynonyms(synonyms);

        results.add(c);
      }
    }

    return results;
  }

  public List findConceptsByCode(
    String conceptCode,
    boolean includeRetiredConcepts,
    int rowCount) throws Exception {
    if (cacoreServiceURL == null) {
      throw new Exception("Please specify a valid caCORE Service URL");
    }

    List results = new ArrayList();
    EVSQuery query = new EVSQueryImpl();
    query.getConceptNameByCode(NCI_THESAURUS_VOCAB_NAME, conceptCode);
    List conceptNames = evsService.evsSearch(query);

    System.out.println("Concepts:" + conceptNames.size());

    results =
      this.findConceptDetaisByName(conceptNames, includeRetiredConcepts);

    return results;
  }

  private DescLogicConcept findDescLogicConceptByName(
    String vocabName,
    String conceptName) throws Exception {
    EVSQuery query = new EVSQueryImpl();
    query.getConceptByName(vocabName, conceptName);

    List results = evsService.evsSearch(query);

    return (DescLogicConcept) results.get(0);
  }

  private List retrieveSynonyms(DescLogicConcept dlc) {
    List synonyms = new ArrayList();
    Vector propVect = dlc.getPropertyCollection();
    for (int x = 0; x < propVect.size(); x++) {
      Property p = (Property) propVect.get(x);
      if (p.getName().equalsIgnoreCase(SYNONYM_PROPERTY_NAME)) {
        synonyms.add(p.getValue());
        System.out.println("Synonym: " + p.getValue());
      }
    }

    return synonyms;
  }

  private List retrieveDefinitions(DescLogicConcept dlc) {
    List definitions = new ArrayList();
    Vector propVect = dlc.getPropertyCollection();
    for (int x = 0; x < propVect.size(); x++) {
      Property p = (Property) propVect.get(x);
      if (p.getName().equalsIgnoreCase(DEFINITION_PROPERTY_NAME)) {
        //definitions.add(p.getValue());
        System.out.println("Definition: " + p.getValue());
        String definition = this.retrieveDefinitionValue(p.getValue());
        String definitionSource = this.retrieveDefinitionSource(p.getValue());
        Definition def = new Definition();
        Source src = new Source();
        def.setDefinition(definition);
        src.setAbbreviation(definitionSource);
        def.setSource(src);
        definitions.add(def);
      }
    }

    return definitions;
  }

  private String retrieveDefinitionSource(String termStr) {
    String source = "";

    int length = 0; //<def-source>,  <def-definition>
    length = termStr.length();
    System.out.println(termStr);
    int iStartDefSource = 0;
    int iEndDefSource = 0;

    if (length > 0) {
      iStartDefSource = termStr.lastIndexOf("<def-source>");
      iStartDefSource = iStartDefSource + ("<def-source>").length();
      iEndDefSource = termStr.indexOf("</def-source>");
      if ((iStartDefSource > 1) && (iEndDefSource > 1)) {
        source = termStr.substring(iStartDefSource, iEndDefSource);
      }
    }
    System.out.println("Definition source: " + source);

    return source;
  }

  private String retrieveDefinitionValue(String termStr) {
    String definition = "";

    int length = 0; //<def-source>,  <def-definition>
    length = termStr.length();
    int iStartDef = 0;
    int iEndDef = 0;

    if (length > 0) {
      iStartDef = termStr.lastIndexOf("<def-definition>");
      iStartDef = iStartDef + ("<def-definition>").length();
      iEndDef = termStr.indexOf("</def-definition>");

      if ((iStartDef > 1) && (iEndDef > 1)) {
        definition = termStr.substring(iStartDef, iEndDef);
      }
    }
    System.out.println("Definition value: " + definition);

    return definition;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    EVSQueryService testAction = new EVSQueryService();
    try {
      //testAction.findConceptsBySynonym("gene", 100);
      testAction.findConceptsByCode("C41095", true, 100);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
