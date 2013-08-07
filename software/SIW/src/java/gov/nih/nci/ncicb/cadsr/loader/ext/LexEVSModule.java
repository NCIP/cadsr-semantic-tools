/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ext;

import java.util.Collection;

public interface LexEVSModule {

	public EvsResult findByConceptCode(String code, boolean includeRetired);
	public Collection<EvsResult> findByPreferredName(String s, boolean includeRetired);
	public Collection<EvsResult> findBySynonym(String s, boolean includeRetired);
	
}
