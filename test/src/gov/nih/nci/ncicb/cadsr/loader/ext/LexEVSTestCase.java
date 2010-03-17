package gov.nih.nci.ncicb.cadsr.loader.ext;

import gov.nih.nci.ncicb.cadsr.evs.EVSConcept;
import gov.nih.nci.ncicb.cadsr.evs.LexEVSQueryService;
import gov.nih.nci.ncicb.cadsr.evs.LexEVSQueryServiceImpl;

import java.util.List;

import junit.framework.TestCase;

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
			List<EVSConcept> evsConcepts = evsModule.findConceptsBySynonym("*clin*", true, 0, "NCI Thesaurus");
			
			for (EVSConcept evsConcept: evsConcepts) {
				System.out.println("Code: "+evsConcept.getCode()+", Name: "+evsConcept.getPreferredName());
			}
			
			System.out.println("Size:" + evsConcepts.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public void testGetSynonymThreaded() {
		String[] strs = new String[] {"*gene*","*ene*","*clin*","*al*"};
		ThreadRunner[] runners = new ThreadRunner[strs.length];
		
		int count = 0;
		while (count < 5) {
			for (int i=0;i<strs.length;i++) {
				runners[i] = new ThreadRunner(strs[i]);
				runners[i].start();
			}
			
			boolean done = false;
			try { 
				while (!done) {
					done = true;
					for (ThreadRunner runner: runners) {
						if (!runner.isDone()) {
							done = false;
							Thread.sleep(10000L);
							break;
						}
					}
				}
				Thread.sleep(10000L);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				count ++;
			}
		}
	}*/
	
	private class ThreadRunner extends Thread {

		private final String searchStr;
		private boolean done = false;
		
		public ThreadRunner(String _searchStr) {
			searchStr = _searchStr;
		}
		
		public boolean isDone() {
			return done;
		}
		
		@Override
		public void run() {
			try {
				LexEVSQueryService evsModule = new LexEVSQueryServiceImpl();
				List<EVSConcept> evsConcepts = evsModule.findConceptsBySynonym(searchStr, false, 0, "NCI Thesaurus");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				done = true;
			}
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
