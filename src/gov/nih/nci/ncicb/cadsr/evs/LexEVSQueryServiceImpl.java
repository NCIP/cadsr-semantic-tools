package gov.nih.nci.ncicb.cadsr.evs;

import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.LexGrid.LexBIG.DataModel.Collections.ConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.ConceptReference;
import org.LexGrid.LexBIG.DataModel.Core.NameAndValue;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.Extensions.Generic.LexBIGServiceConvenienceMethods;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.LBConstants.MatchAlgorithms;
import org.LexGrid.commonTypes.Property;
import org.LexGrid.concepts.Definition;
import org.LexGrid.concepts.Entity;
import org.LexGrid.concepts.Presentation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LexEVSQueryServiceImpl implements LexEVSQueryService {

	private static LexBIGService service;
	private static final String NCIT_SCHEME_NAME = "NCI_Thesaurus";
	private ResolvedConceptReference retiredRootCon = null;
	private LexBIGServiceConvenienceMethods  conMthds = null;
	
	private static final int maxReturn = 5000;
	
	private static Log log = LogFactory.getLog(LexEVSQueryServiceImpl.class);
	private static List<String> retiredStatuses = null;
	
	static {
		try {
			service = (LexBIGService)ApplicationServiceProvider.getApplicationService("EvsServiceInfo");
			
			retiredStatuses = new ArrayList<String>();
			retiredStatuses.add("Retired");
			retiredStatuses.add("retired");
			retiredStatuses.add("Deprecated");
			retiredStatuses.add("deprecated");
			retiredStatuses.add("Removed");
			retiredStatuses.add("removed");
		} catch (Exception e) {
			log.error("Error accessing EVS Service", e);
			throw new EVSRuntimeException("Error connecting to EVS. Please check the configuration and try again",e);
		}
	}
	
	public List findConceptsByCode(String conceptCode, boolean includeRetiredConcepts, int rowCount, String vocabName)
			throws EVSException {
		List<EVSConcept> evsConcepts = new ArrayList<EVSConcept>();
		try {
			CodedNodeSet cns = service.getNodeSet(vocabName, null, null);
			cns = cns.restrictToMatchingProperties(
							Constructors.createLocalNameList("conceptCode"), 
							null, 
							conceptCode, 
							MatchAlgorithms.exactMatch.name(), 
							null
						);
			
			ResolvedConceptReferenceList results = cns.resolveToList(null, null, null, -1);
			evsConcepts = getEVSConcepts(results, includeRetiredConcepts);
		} catch (Exception e) {
			log.error("Error finding concept for code ["+conceptCode+"]", e);
			throw new EVSException("Error finding concept for code ["+conceptCode+"]", e);
		}
		return evsConcepts;
	}
	
	public List<EVSConcept> findConceptDetailsByName(List<String> conceptNames, boolean includeRetiredConcepts) throws EVSException {
		
		return findConceptDetailsByName(conceptNames, includeRetiredConcepts, NCIT_SCHEME_NAME);
	}

	public List<EVSConcept> findConceptDetailsByName(List<String> conceptNames, boolean includeRetiredConcepts, String vocabName) throws EVSException {
		List<EVSConcept> evsConcepts = new ArrayList<EVSConcept>();
		
		if (conceptNames != null) {
			for (String conceptName: conceptNames) {
				List<EVSConcept> evsConceptsChunk = (List<EVSConcept>)findConceptsByCode(conceptName, includeRetiredConcepts, 0, vocabName);
				evsConcepts.addAll(evsConceptsChunk);
			}
		}
		
		return evsConcepts;
	}

	public List findConceptsByCode(String conceptCode, boolean includeRetiredConcepts, int rowCount) throws EVSException {
		return findConceptsByCode(conceptCode, includeRetiredConcepts, rowCount, NCIT_SCHEME_NAME);
	}
	
	private List<EVSConcept> getEVSConcepts(ResolvedConceptReferenceList rcRefList, boolean includeRetiredConcepts) throws Exception {
		List<EVSConcept> evsConcepts = new ArrayList<EVSConcept>();
		if (rcRefList != null) {
			Iterator<ResolvedConceptReference> iter = rcRefList.iterateResolvedConceptReference();
			while (iter.hasNext()) {
				ResolvedConceptReference conceptRef = iter.next();
				if (doIncludeConcept(conceptRef, includeRetiredConcepts)) {
					EVSConcept evsConcept = getEVSConcept(conceptRef);
					evsConcepts.add(evsConcept);
				}
			}
		}
		return evsConcepts;
	}
	
	private boolean doIncludeConcept(ResolvedConceptReference conceptRef, boolean includeRetiredConcepts) throws Exception {
		if (includeRetiredConcepts) {
			return true;
		}
		else {
			Entity entity = conceptRef.getEntity();
			if (entity.isIsActive() 
					&& !isRetiredStatus(entity) 
					&& !isRetiredConcept(conceptRef)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isRetiredStatus(Entity entity) {
		
		if (((entity.getStatus() != null 
				&& retiredStatuses.contains(entity.getStatus()))
				||
				hasConceptStatusRetiredProperty(entity))) {
			return true;
		}
		
		return false;
	}
	
	private boolean hasConceptStatusRetiredProperty(Entity entity) {
		Property[] props = entity.getAllProperties();
		for (Property prop: props) {
			if (prop.getPropertyName().equalsIgnoreCase("Concept_Status")) {
				String statusVal = prop.getValue().getContent();
				if (statusVal != null && retiredStatuses.contains(statusVal)) {
					return true;
				}
			}
		}
		return false;
	}

	private EVSConcept getEVSConcept(ResolvedConceptReference rcRef) {
		EVSConcept evsConcept = new EVSConcept();
		evsConcept.setCode(rcRef.getCode());
		
		Entity entity = rcRef.getEntity();
		evsConcept.setDefinitions(getEntityDefinitions(entity));
		evsConcept = setProperties(evsConcept, entity);
		
		return evsConcept;
	}
	
	private List<Definition> getEntityDefinitions(Entity entity) {
		List<Definition> definitions = new ArrayList<Definition>();
		
		if (entity != null) {
			Definition[] defs = entity.getDefinition();
			for (Definition def: defs) {
				definitions.add(def);
			}
		}
		
		return definitions;
	}
	
	private EVSConcept setProperties(EVSConcept evsConcept, Entity entity) {
		if (entity != null) {
			List<String> synonyms = new ArrayList<String>();
			
			Property[] entityProps = entity.getAllProperties();
			for (Property entityProp: entityProps) {
				String propName = entityProp.getPropertyName();
				String propValue = entityProp.getValue().getContent();
				
				if (propName.equalsIgnoreCase("FULL_SYN")) {
					synonyms.add(propValue);
				}
				else if (propName.equalsIgnoreCase("Preferred_Name")) {
					evsConcept.setPreferredName(propValue);
					evsConcept.setName(propValue);
				}
			}
			evsConcept.setSynonyms(synonyms);
		}
		
		return evsConcept;
	}
	
	public List<EVSConcept> findConceptsByPreferredName(String searchTerm, boolean includeRetiredConcepts) throws EVSException {
		return findConceptsByPreferredName(searchTerm, includeRetiredConcepts, NCIT_SCHEME_NAME);
	}

	public List<EVSConcept> findConceptsByPreferredName(String searchTerm, boolean includeRetiredConcepts, String vocabName) throws EVSException {
		List<EVSConcept> evsConcepts = new ArrayList<EVSConcept>();
		try {
			CodedNodeSet cns = service.getNodeSet(vocabName, null, null);
			cns = cns.restrictToMatchingDesignations(
					searchTerm, 
					CodedNodeSet.SearchDesignationOption.PREFERRED_ONLY, 
					MatchAlgorithms.exactMatch.name(), 
					null);
			ResolvedConceptReferenceList results = cns.resolveToList(null, null, null, -1);
			evsConcepts = getEVSConcepts(results, includeRetiredConcepts);
		} catch (Exception e) {
			log.error("Error finding concepts for synonym ["+searchTerm+"]", e);
			throw new EVSException("Error finding concepts for synonym ["+searchTerm+"]", e);
		}
		return evsConcepts;
	}
	
	public List<EVSConcept> findConceptsBySynonym(String searchTerm,
			boolean includeRetiredConcepts, int rowCount) throws EVSException {
		return findConceptsBySynonym(searchTerm, includeRetiredConcepts, rowCount, NCIT_SCHEME_NAME);
	}

	public List<EVSConcept> findConceptsBySynonym(String searchTerm,
		boolean includeRetiredConcepts, int rowCount, String vocabName)
			throws EVSException {
		List<EVSConcept> evsConcepts = new ArrayList<EVSConcept>();
		try {
			CodedNodeSet cns = service.getNodeSet(vocabName, null, null);

			String[][] termAndMatchAlgorithmName = getTermAndMatchAlgorithmName(searchTerm);
			cns = cns.restrictToMatchingDesignations(
					termAndMatchAlgorithmName[0][0], 
					SearchDesignationOption.ALL, 
					termAndMatchAlgorithmName[0][1],
					null
				);
			ResolvedConceptReferenceList results = cns.resolveToList(null, null, null, -1);
			evsConcepts = getEVSConcepts(results, includeRetiredConcepts);
		} catch (Exception e) {
			log.error("Error finding concepts for synonym ["+searchTerm+"]", e);
			throw new EVSException("Error finding concepts for synonym ["+searchTerm+"]", e);
		}
		return evsConcepts;
	}
	
	public boolean isRetiredConcept(ConceptReference conRef) throws Exception {
		ConceptReference retiredCon = getRetiredRootConcept();
		
		if (retiredCon != null) {
			CodedNodeGraph cng = service.getNodeGraph(NCIT_SCHEME_NAME, null, null);
			ConceptReferenceList refList = cng.listCodeRelationships(conRef, retiredCon, false);
			for (int i=0;i<refList.getConceptReferenceCount();i++) {
				if(refList.getConceptReference(i).getConceptCode().equalsIgnoreCase("subClassOf")) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private ConceptReference getRetiredRootConcept() throws Exception {
		if (retiredRootCon == null) {
			ResolvedConceptReferenceList rootConcepts = getRootConcepts();
			for (int i=0;i<rootConcepts.getResolvedConceptReferenceCount();i++) {
				ResolvedConceptReference resConRef = rootConcepts.getResolvedConceptReference(i);
				Presentation[] presentations = resConRef.getEntity().getPresentation();
				for (Presentation pres: presentations) {
					if (pres.getIsPreferred() && pres.getValue().getContent().contains("Retired")) {
						retiredRootCon = resConRef;
						break;
					}
				}
				
				if (retiredRootCon != null) break;
			}
		}
		
		return retiredRootCon;
	}
	
	private ResolvedConceptReferenceList getRootConcepts() throws Exception {
		LexBIGServiceConvenienceMethods  conMthds = getConvenienceMethods();
		
		String[] hirearchyIds = conMthds.getHierarchyIDs(NCIT_SCHEME_NAME, null);
		ResolvedConceptReferenceList rootConcepts = conMthds.getHierarchyRoots(NCIT_SCHEME_NAME, null, hirearchyIds[0]);
		
		return rootConcepts;
	}
	
	private LexBIGServiceConvenienceMethods getConvenienceMethods() throws Exception {
		
		if(conMthds == null) {
			conMthds = (LexBIGServiceConvenienceMethods)service.getGenericExtension("LexBIGServiceConvenienceMethods");
			conMthds.setLexBIGService(service);
		}
		
		return conMthds;
	}
	
	private String[][] getTermAndMatchAlgorithmName(String searchTerm) {
		String[][] termAndMatchAlgorithm = new String[1][2];
		if (searchTerm.startsWith("*")) {
			termAndMatchAlgorithm[0][0] = searchTerm.substring(1);
			termAndMatchAlgorithm[0][1] = MatchAlgorithms.contains.name();
		}
		else if (searchTerm.endsWith("*")) {
			termAndMatchAlgorithm[0][0] = searchTerm.substring(0, searchTerm.length()-1);
			termAndMatchAlgorithm[0][1] = MatchAlgorithms.startsWith.name();
		}
		else if (searchTerm.contains("*")) {
			termAndMatchAlgorithm[0][0] = searchTerm;
			termAndMatchAlgorithm[0][1] = MatchAlgorithms.LuceneQuery.name();
		}
		else {
			termAndMatchAlgorithm[0][0] = searchTerm;
			termAndMatchAlgorithm[0][1] = MatchAlgorithms.exactMatch.name();
		}
		
		return termAndMatchAlgorithm;
	}
	
}
