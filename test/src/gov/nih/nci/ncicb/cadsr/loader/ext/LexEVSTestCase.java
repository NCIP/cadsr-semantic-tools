package gov.nih.nci.ncicb.cadsr.loader.ext;

import gov.nih.nci.ncicb.cadsr.evs.EVSConcept;
import gov.nih.nci.ncicb.cadsr.evs.LexEVSQueryService;
import gov.nih.nci.ncicb.cadsr.evs.LexEVSQueryServiceImpl;

import java.util.List;

import junit.framework.TestCase;

import org.LexGrid.LexBIG.DataModel.Collections.AssociatedConceptList;
import org.LexGrid.LexBIG.DataModel.Collections.AssociationList;
import org.LexGrid.LexBIG.DataModel.Core.AssociatedConcept;
import org.LexGrid.LexBIG.DataModel.Core.Association;
import org.LexGrid.commonTypes.Source;
import org.LexGrid.concepts.Definition;


public class LexEVSTestCase extends TestCase{

	public void testGetByConceptCode() {
		try {
			LexEVSQueryService evsModule = new LexEVSQueryServiceImpl();
			List<EVSConcept> evsConcepts = evsModule.findConceptsByCode("C80736", false, 100, "NCI Thesaurus");
			for (EVSConcept evsConcept: evsConcepts) {
				System.out.println("Code: "+evsConcept.getCode()+", Name: "+evsConcept.getPreferredName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testGetBySynonym() {
		try {
			LexEVSQueryService evsModule = new LexEVSQueryServiceImpl();
			List<EVSConcept> evsConcepts = evsModule.findConceptsBySynonym("*clinical*", true, 0, "NCI Thesaurus");
			for (EVSConcept evsConcept: evsConcepts) {
				System.out.println("Code: "+evsConcept.getCode()+", Name: "+evsConcept.getPreferredName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testGetByPreferredName() {
		try {
			LexEVSQueryService evsModule = new LexEVSQueryServiceImpl();
			List<EVSConcept> evsConcepts = evsModule.findConceptsByPreferredName("blood", false,  "NCI Thesaurus");
			for (EVSConcept evsConcept: evsConcepts) {
				System.out.println("Code: "+evsConcept.getCode()+", Name: "+evsConcept.getPreferredName());
				List<Definition> defs = evsConcept.getDefinitions();
				assertNotNull(defs);
				for (Definition def: defs) {
					Source[] sources = def.getSource();
					for (Source source: sources) {
						System.out.println("Source: "+source.getContent());
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
