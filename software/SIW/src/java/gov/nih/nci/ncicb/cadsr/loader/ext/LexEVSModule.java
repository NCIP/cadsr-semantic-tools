package gov.nih.nci.ncicb.cadsr.loader.ext;

import java.util.Collection;

public interface LexEVSModule {

	public EvsResult findByConceptCode(String code, boolean includeRetired);
	public Collection<EvsResult> findByPreferredName(String s, boolean includeRetired);
	public Collection<EvsResult> findBySynonym(String s, boolean includeRetired);
	
}
