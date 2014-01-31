package gov.nih.nci.ncicb.cadsr.loader.ext;

import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.Collection;
import java.util.Iterator;

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Exceptions.LBException;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.ActiveOption;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.LBConstants.MatchAlgorithms;
import org.LexGrid.commonTypes.Property;
import org.LexGrid.concepts.Entity;

public class LexEVSModuleImpl implements LexEVSModule {

	public EvsResult findByConceptCode(String code, boolean includeRetired) {
		/*LexBIGService lbService = getEVSService();
		try {
			CodedNodeSet cns = lbService.getCodingSchemeConcepts("NCI_Thesaurus", null);
			cns = cns.restrictToMatchingProperties(
							Constructors.createLocalNameList("conceptCode"), 
							null, 
							code, 
							MatchAlgorithms.exactMatch.name(), 
							null
						);
			cns = cns.restrictToStatus(ActiveOption.ALL, new String[]{"Retired_Concept"});
			
			ResolvedConceptReferenceList results = cns.resolveToList(null, null, null, -1);
			Iterator<ResolvedConceptReference> iter = results.iterateResolvedConceptReference();
			while (iter.hasNext()) {
				ResolvedConceptReference conceptRef = iter.next();
				Entity entity = conceptRef.getEntity();
				Property[] entityProps = entity.getAllProperties();
				String resolvedCode = conceptRef.getConceptCode();
				System.out.println(resolvedCode+":"+entity.getStatus()+":"+entity.isIsActive());
				for (int i=0;i<entityProps.length; i++) {
					System.out.println(entityProps[i].getPropertyName()+": "+entityProps[i].getValue().getContent());
				}
			}
		} catch (LBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return null;
	}

	public Collection<EvsResult> findByPreferredName(String s,
			boolean includeRetired) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<EvsResult> findBySynonym(String s, boolean includeRetired) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static LexBIGService getEVSService() {
		/*try {
			LexBIGService service = (LexBIGService)ApplicationServiceProvider.getApplicationServiceFromUrl("http://lexevsapi.nci.nih.gov/lexevsapi50/", "EvsServiceInfo");
			
			return service;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return null;
	}

}
