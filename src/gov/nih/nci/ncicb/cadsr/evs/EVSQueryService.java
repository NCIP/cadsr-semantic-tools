/*
 * Copyright 2000-2005 Oracle, Inc. This software was developed in conjunction with the National Cancer Institute, and so to the extent government employees are co-authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105.
 *
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the disclaimer of Article 3, below. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 2. The end-user documentation included with the redistribution, if any, must include the following acknowledgment:
 *
 * "This product includes software developed by Oracle, Inc. and the National Cancer Institute."
 *
 * If no such end-user documentation is to be included, this acknowledgment shall appear in the software itself, wherever such third-party acknowledgments normally appear.
 *
 * 3. The names "The National Cancer Institute", "NCI" and "Oracle" must not be used to endorse or promote products derived from this software.
 *
 * 4. This license does not authorize the incorporation of this software into any proprietary programs. This license does not authorize the recipient to use any trademarks owned by either NCI or Oracle, Inc.
 *
 * 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, ORACLE, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 */
package gov.nih.nci.ncicb.cadsr.evs;

import gov.nih.nci.evs.domain.*;
import gov.nih.nci.evs.query.*;
import gov.nih.nci.ncicb.cadsr.evs.EVSConcept;
import gov.nih.nci.system.applicationservice.*;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.*;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.io.IOException;

import java.util.*;


public class EVSQueryService {
  private static String NCI_THESAURUS_VOCAB_NAME = "NCI_Thesaurus";
  private static String SYNONYM_PROPERTY_NAME = "Synonym";
  private static String PREFERRED_NAME_PROP = "PREFERRED_NAME";
  private static String DEFINITION_PROPERTY_NAME = "DEFINITION";
  private static String ALT_DEFINITION_PROPERTY_NAME = "ALT_DEFINITION";
  private EVSApplicationService evsService;

  public EVSQueryService() {
    try {
      evsService = (EVSApplicationService)ApplicationServiceProvider.getApplicationService("EvsServiceInfo");
    } catch (Exception e) {
      System.err.println("Unable to get EVSApplicationService. Contact Support");
    }
  }

  /**
   * returns by list of concepts by preferredName
   * <br> Usually, it means one concept, not always...
   */
  public List<EVSConcept> findConceptsByPreferredName(
    String searchTerm,
    boolean includeRetiredConcepts) throws Exception {

    return findConceptsByPreferredName(searchTerm, includeRetiredConcepts, NCI_THESAURUS_VOCAB_NAME);

  }

  /* CL: Redo. No time atm. */
  /**
   * returns by list of concepts by preferredName
   * <br> Usually, it means one concept, not always...
   */
  public List<EVSConcept> findConceptsByPreferredName(
    String searchTerm,
    boolean includeRetiredConcepts,
    String vocabName) throws Exception {
    
    // CL: this is bad. replace by true query. who guaranties the result is in the 1st 500 rows? 
    List<EVSConcept> consBySyn = findConceptsBySynonym(searchTerm, includeRetiredConcepts, 500, vocabName);
    
    List<EVSConcept> results = new ArrayList<EVSConcept>();
    for(EVSConcept con : consBySyn) {
      if(con.getPreferredName().equalsIgnoreCase(searchTerm))
        results.add(con);
    }

    return results;
  }

  public List<EVSConcept> findConceptsBySynonym(
    String searchTerm,
    boolean includeRetiredConcepts,
    int rowCount) throws Exception {
    
    return findConceptsBySynonym(searchTerm, includeRetiredConcepts, rowCount, NCI_THESAURUS_VOCAB_NAME);

  }

  public List<EVSConcept> findConceptsBySynonym(
    String searchTerm,
    boolean includeRetiredConcepts,
    int rowCount, 
    String vocabName) throws Exception {

    EVSQuery query = new EVSQueryImpl();
    
    // 2 means "by property"
    // 1 means ? (some kind of index)
    query.searchDescLogicConcepts
      (vocabName, searchTerm, rowCount, 2, SYNONYM_PROPERTY_NAME, 1);


    List conceptNames = evsService.evsSearch(query);

    return this.descConceptToEVSConcept(conceptNames, includeRetiredConcepts);

  }

  public List<EVSConcept> findConceptDetailsByName(
    List conceptNames,
    boolean includeRetiredConcepts) throws Exception {

    return findConceptDetailsByName(conceptNames, includeRetiredConcepts, NCI_THESAURUS_VOCAB_NAME);

  }

  public List<EVSConcept> descConceptToEVSConcept(
    List<DescLogicConcept> concepts,
    boolean includeRetiredConcepts) {
    
    List<EVSConcept> results = new ArrayList<EVSConcept>();
    
    for (DescLogicConcept concept : concepts) {
      if (
        (includeRetiredConcepts) ||
        (!includeRetiredConcepts && !concept.getIsRetired().booleanValue())) {
        List synonyms = this.retrieveSynonyms(concept);
        List defs = this.retrieveDefinitions(concept);
        
        EVSConcept c = new EVSConcept();
        c.setCode(concept.getCode());
        c.setPreferredName(retrievePreferredName(concept));
        c.setName(concept.getName());
        c.setDefinitions(defs);
        c.setSynonyms(synonyms);
        
        results.add(c);
       }
    }
    
    return results; 
    
  }

  public List<EVSConcept> findConceptDetailsByName(
    List<String> conceptNames,
    boolean includeRetiredConcepts,
    String vocabName) throws Exception {
    List<EVSConcept> results = new ArrayList<EVSConcept>();

    for (String conceptName : conceptNames) {
      DescLogicConcept concept =
        this.findDescLogicConceptByName(vocabName, conceptName);

      if (
        (includeRetiredConcepts) ||
            (!includeRetiredConcepts && !concept.getIsRetired().booleanValue())) {
        List synonyms = this.retrieveSynonyms(concept);
        List defs = this.retrieveDefinitions(concept);

        EVSConcept c = new EVSConcept();
        c.setCode(concept.getCode());
        c.setPreferredName(retrievePreferredName(concept));
        c.setName(conceptName);
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

    return findConceptsByCode(conceptCode, includeRetiredConcepts, rowCount, NCI_THESAURUS_VOCAB_NAME);
  }

  public List findConceptsByCode(
    String conceptCode,
    boolean includeRetiredConcepts,
    int rowCount, 
    String vocabName) throws Exception {

    List results = new ArrayList();
    EVSQuery query = new EVSQueryImpl();

    boolean inputFlag = true; // is a code, not name
    query.getDescLogicConcept(vocabName, conceptCode, inputFlag);
    List evsResults = evsService.evsSearch(query);

    if (evsResults != null) {
      return descConceptToEVSConcept(evsResults, includeRetiredConcepts);
    }
    
    return null;

//     query.getDescLogicConceptNameByCode(vocabName, conceptCode);

//     List conceptNames = evsService.evsSearch(query);

//     return this.descConceptToEVSConcept(conceptNames, includeRetiredConcepts);

    
//     query.getConceptNameByCode(vocabName, conceptCode);
//     List conceptNames = evsService.evsSearch(query);

//     results =
//       this.findConceptDetailsByName(conceptNames, includeRetiredConcepts, vocabName);

//     return results;

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
      }
    }

    return synonyms;
  }

  private String retrievePreferredName(DescLogicConcept dlc) {
    Collection propVect = dlc.getPropertyCollection();
    for (Iterator it = propVect.iterator(); it.hasNext();) {
      Property p = (Property) it.next();
      if (p.getName().equalsIgnoreCase(PREFERRED_NAME_PROP)) {
        return p.getValue();
      }
    }
    return null;
  }

  private List retrieveDefinitions(DescLogicConcept dlc) {
    List definitions = new ArrayList();
    Vector propVect = dlc.getPropertyCollection();
    for (int x = 0; x < propVect.size(); x++) {
      Property p = (Property) propVect.get(x);
      if (p.getName().equalsIgnoreCase(DEFINITION_PROPERTY_NAME)) {
        //definitions.add(p.getValue());
        String definition = this.retrieveDefinitionValue(p.getValue());
        String definitionSource = this.retrieveDefinitionSource(p);
        Definition def = new Definition();
        Source src = new Source();
        def.setDefinition(definition);
        src.setAbbreviation(definitionSource);
        def.setSource(src);
        definitions.add(def);
      }
    }
    if(definitions.size() == 0) {
      for (int x = 0; x < propVect.size(); x++) {
        Property p = (Property) propVect.get(x);
        if (p.getName().equalsIgnoreCase(ALT_DEFINITION_PROPERTY_NAME)) {
          String definition = this.retrieveDefinitionValue(p.getValue());
          String definitionSource = this.retrieveDefinitionSource(p);
          Definition def = new Definition();
          Source src = new Source();
          def.setDefinition(definition);
          src.setAbbreviation(definitionSource);
          def.setSource(src);
          definitions.add(def);
        }
      }
    }

    return definitions;
  }

  private String retrieveDefinitionSource(Property property) {
    List qList = property.getQualifierCollection();
    for(int q=0;q<qList.size(); q++){
      Qualifier qualifier = (Qualifier)qList.get(q);
      if(qualifier.getName().equals("Source"))
        return qualifier.getValue();
    }
    return "";
  }

//   private String retrieveDefinitionSource(String termStr) {
//     return termStr;
//   }

  private String retrieveDefinitionValue(String termStr) {
    return termStr;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    EVSQueryService testAction = new EVSQueryService();
    try {

//      EVSApplicationService service = (EVSApplicationService)ApplicationServiceProvider.getApplicationService("EvsServiceInfo");
////       String genUrl = "http://evsapi-dev.nci.nih.gov:19080/evsapi41";
////       EVSApplicationService service = (EVSApplicationService) ApplicationServiceProvider.getApplicationServiceFromUrl(genUrl);
//
//      EVSQuery query = new EVSQueryImpl();
//
//      query.searchDescLogicConcepts
//        ("NCI_Thesaurus", name, rowCount, 2, "Synonym", 1);
//
////       query.getConceptWithPropertyMatching(
////         "NCI_Thesaurus", "Synonym", "name", 100);
//      
//      List concepts = service.evsSearch(query);
//
//      for(Object o : concepts) {
//        DescLogicConcept desc = (DescLogicConcept)o;
//        System.out.println(desc.getName());
//        System.out.println(desc.getIsRetired());
//      }
//
//      

    testAction.findConceptsBySynonym("name", false, 5);

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
