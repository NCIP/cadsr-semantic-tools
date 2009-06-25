package gov.nih.nci.ncicb.cadsr.loader.test.persister;

import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.persister.ConceptPersister;
import gov.nih.nci.ncicb.cadsr.loader.persister.PersisterException;
import gov.nih.nci.ncicb.cadsr.loader.persister.PersisterUtil;
import gov.nih.nci.ncicb.cadsr.loader.test.MainTestCase;

public class ConceptPersisterTest extends MainTestCase {
    
    public ConceptPersisterTest() {
      super("Concept Persister", ConceptPersisterTest.class, "/test-data-setup.xls");
    }
    
    public void setUp() throws Exception {
//       super.setUp();
    }
    
    public void testConceptNotInCadsrNotInEVS() {
        ElementsLists elements = ElementsLists.getInstance();
        
        Concept concept = DomainObjectFactory.newConcept();
        concept.setPreferredName("W_00001");
        concept.setLongName("Not a real concept");
        concept.setPreferredDefinition("bad concept");
        concept.setDefinitionSource("NCI");
        
        elements.addElement(concept);

        ObjectClass oc = DomainObjectFactory.newObjectClass();
        oc.setPreferredName("W_00001");
        elements.addElement(oc);
        
        ConceptPersister persister = new ConceptPersister();
        persister.setPersisterUtil(new PersisterUtil());

        boolean exceptionCaught = false;
        try {
          persister.persist();
        } catch (PersisterException ex) {
          exceptionCaught = true;    
        }
        
        assertTrue(exceptionCaught);
        
    }
    
    
    public boolean runInRealContainer() {
        return false;
    }
    
    public boolean requiresDatabase() {
        return true;
    }
    
    public void containerSetUp() {
        
    }
}
