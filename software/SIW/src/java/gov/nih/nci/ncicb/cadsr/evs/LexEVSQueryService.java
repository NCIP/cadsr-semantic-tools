/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.evs;


import java.util.List;

public interface LexEVSQueryService {

	public List<EVSConcept> findConceptDetailsByName(List<String> conceptNames, boolean includeRetiredConcepts) throws EVSException;
	public List<EVSConcept> findConceptDetailsByName(List<String> conceptNames, boolean includeRetiredConcepts, String vocabName) throws EVSException;
	public List findConceptsByCode(String conceptCode, boolean includeRetiredConcepts, int rowCount) throws EVSException;
	public List findConceptsByCode(String conceptCode, boolean includeRetiredConcepts, int rowCount, String vocabName) throws EVSException;
	public List<EVSConcept> findConceptsByPreferredName(String searchTerm, boolean includeRetiredConcepts) throws EVSException;
	public List<EVSConcept> findConceptsByPreferredName(String searchTerm, boolean includeRetiredConcepts, String vocabName) throws EVSException;
	public List<EVSConcept> findConceptsBySynonym(String searchTerm, boolean includeRetiredConcepts, int rowCount) throws EVSException;
	public List<EVSConcept> findConceptsBySynonym(String searchTerm, boolean includeRetiredConcepts, int rowCount, String vocabName) throws EVSException;
	
}
