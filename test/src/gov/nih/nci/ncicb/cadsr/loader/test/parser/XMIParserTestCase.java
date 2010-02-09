package gov.nih.nci.ncicb.cadsr.loader.test.parser;

import gov.nih.nci.ncicb.cadsr.domain.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.domain.PermissibleValue;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;
import gov.nih.nci.ncicb.cadsr.domain.bean.ValueDomainBean;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.UMLDefaultHandler;
import gov.nih.nci.ncicb.cadsr.loader.parser.Parser;
import gov.nih.nci.ncicb.cadsr.loader.parser.XMIParser2;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;

public class XMIParserTestCase extends TestCase {

	Parser parser = new XMIParser2();
	UMLDefaultHandler handler = new UMLDefaultHandler();

	String xmiFileName = "gov/nih/nci/ncicb/cadsr/loader/test/cadsrapi/GF25444_41_6.xmi";
	
	public void testParseXMI() {
		try {
			parser.setEventHandler(handler);
			
			String path = XMIParserTestCase.class.getClassLoader().getResource(xmiFileName).getPath();
			File f = new File(path);
			
			parser.parse(f.getAbsolutePath().replace("\\", "/"));
			
			ElementsLists elementsList = ElementsLists.getInstance();
			List<ValueDomainBean> valueDomains = elementsList.getElements(new ValueDomainBean());
			assertNotNull(valueDomains);
			assertEquals(new Integer(valueDomains.size()), new Integer(1));
			
			List<PermissibleValue> pvs = valueDomains.get(0).getPermissibleValues();
			assertNotNull(pvs);
			assertEquals(new Integer(pvs.size()), new Integer(3));
			for (PermissibleValue pv: pvs) {
				assertNotNull(pv.getValue());
				
				ValueMeaning vm = pv.getValueMeaning();
				assertNotNull(vm);
				
				ConceptDerivationRule cdr = vm.getConceptDerivationRule();
				assertNotNull(cdr);
				
				List<ComponentConcept> compCons = cdr.getComponentConcepts();
				assertNotNull(compCons);
				assertTrue(compCons.size()>0);
				for (ComponentConcept compCon: compCons) {
					assertNotNull(compCon.getConcept());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
