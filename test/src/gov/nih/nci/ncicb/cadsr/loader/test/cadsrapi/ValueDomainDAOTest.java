package gov.nih.nci.ncicb.cadsr.loader.test.cadsrapi;


import gov.nih.nci.ncicb.cadsr.domain.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain;
import gov.nih.nci.ncicb.cadsr.domain.Context;
import gov.nih.nci.ncicb.cadsr.domain.PermissibleValue;
import gov.nih.nci.ncicb.cadsr.domain.Representation;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;
import gov.nih.nci.ncicb.cadsr.domain.bean.ComponentConceptBean;
import gov.nih.nci.ncicb.cadsr.domain.bean.ConceptBean;
import gov.nih.nci.ncicb.cadsr.domain.bean.ConceptDerivationRuleBean;
import gov.nih.nci.ncicb.cadsr.domain.bean.ConceptualDomainBean;
import gov.nih.nci.ncicb.cadsr.domain.bean.ContextBean;
import gov.nih.nci.ncicb.cadsr.domain.bean.PermissibleValueBean;
import gov.nih.nci.ncicb.cadsr.domain.bean.RepresentationBean;
import gov.nih.nci.ncicb.cadsr.domain.bean.ValueDomainBean;
import gov.nih.nci.ncicb.cadsr.domain.bean.ValueMeaningBean;
import gov.nih.nci.ncicb.cadsr.loader.test.MainTestCase;
import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ValueDomainDAOTest extends MainTestCase
{
	private static String dataURL = "/gov/nih/nci/ncicb/cadsr/loader/test/cadsrapi/GF25444_41_6.xls";
	protected static Log log = LogFactory.getLog(ValueDomainDAOTest.class.getName());
  
  public ValueDomainDAOTest()
  {
	  super("ValueDomainDAOTest", ValueDomainDAOTest.class, dataURL);
  }
  
  	@Override
	protected void containerSetUp() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean requiresDatabase() {
		return true;
	}

	@Override
	protected boolean runInRealContainer() {
		return false;
	}
  
  public void setUp() {
	  try {
		super.setUp();
	} catch (Exception e) {
		e.printStackTrace();
	}
  }
  
  public void testFind()
    throws Exception
  {
    /*ValueDomain vd = DomainObjectFactory.newValueDomain();
    vd.setLongName(TestCaseProperties.getTestData("testFind.name"));

    System.out.println("Searching for VD: " + vd.getLongName());
    List list = DAOAccessor.getValueDomainDAO().find(vd);

    assertTrue("Result size incorrect for VD:  " + vd.getLongName(), list.size() == Integer.parseInt(TestCaseProperties.getTestData("testFind.result")));
*/
//     ValueDomain valueDomain = (ValueDomain)list.get(0);

//     assertTrue("Unexpected Representation: " + valueDomain.getRepresentation().getLongName(), TestCaseProperties.getTestData("testFind.result.representation.name").equals(valueDomain.getRepresentation().getLongName()));

  }

  public void testFindPermissibleValues() 
    throws Exception { 
    
    /*String name = TestCaseProperties.getTestData("testFindPermissibleValues.vd.name");
    System.out.println("Searching for VD: " + name);
    ValueDomain valueDomain = DAOAccessor.getValueDomainDAO().findByName(name);
    assertNotNull("ValueDomain " + name + " does not Exist", valueDomain);
    
    List pvs = DAOAccessor.getValueDomainDAO().getPermissibleValues(valueDomain.getId());
    assertTrue("ValueDomain has too few PVs: " + pvs.size(), pvs.size() > (new Integer(TestCaseProperties.getTestData("testFindPermissibleValues.result")).intValue()));
*/
  }
  
  public void testSave() {
	  ValueDomain vd = new ValueDomainBean();
	  ConceptualDomain cd = new ConceptualDomainBean();
	  cd.setPublicId("1111");
	  cd.setVersion(new Float(1.0));
	  
	  vd.setConceptualDomain(cd);
	  
	  Representation rep = new RepresentationBean();
	  rep.setPublicId("2186371");
	  rep.setVersion(new Float(1.0));
	  vd.setRepresentation(rep);
	  
	  vd.setVersion(new Float(1.0));
	  
	  vd.setDataType("CHARACTER");
	  
	  vd.setContext(getContext());
	  
	  vd.setPreferredDefinition("TESTVD_GF25441_41");
	  
	  vd.setVdType("E");
	  vd.setLongName("TestVD_GF25444_41_Jan19_6");
	  
	  List<PermissibleValue> pvs = new ArrayList<PermissibleValue>();
	  
	  pvs.add(getPV("Doxorubicin/Filgrastim", new String[][]{}));
	  pvs.add(getPV("Moderate Adverse Event", new String[][]{{"C41339","Moderate Adverse Event"}, {"C25284","Type"}}));
	  pvs.add(getPV("Short", new String[][]{{"C73939", "Short"}}));
	  pvs.add(getPV("Other", new String[][]{}));
	  
	  vd.setPermissibleValues(pvs);
	  
	  try {
		ValueDomain createdVD = DAOAccessor.getValueDomainDAO().create(vd);
		assertNotNull(createdVD);
		
		Connection con = getDataSource().getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from VALUE_MEANINGS where upper(short_meaning) in ('SHORT', 'OTHER')");
		
		assertTrue(compareResultSet(rs, "VALUE_MEANINGS"));
		
		List<PermissibleValue> createdPVs = DAOAccessor.getValueDomainDAO().getPermissibleValues(createdVD.getId());
		
		assertNotNull(createdPVs);
		assertEquals(new Integer(createdPVs.size()), new Integer(4));
		boolean containsShort = false;
		for (PermissibleValue pv: createdPVs) {
			if (pv.getValue().equals("Short")) {
				containsShort = true;
				ValueMeaning shortVM = pv.getValueMeaning();
				assertNotNull(shortVM);
				ConceptDerivationRule shortCDR = shortVM.getConceptDerivationRule();
				assertNotNull(shortCDR);
				List<ComponentConcept> shortCCs = shortCDR.getComponentConcepts();
				assertNotNull(shortCCs);
				assertTrue(shortCCs.size() == 1);
				Concept shortCon = shortCCs.get(0).getConcept();
				assertNotNull(shortCon);
				assertEquals(shortCon.getPreferredName(), "C73939");
			}
			else if (pv.getValue().equals("Doxorubicin/Filgrastim")) {
				assertNull(pv.getValueMeaning().getConceptDerivationRule());
			}
		}
		
		assertTrue(containsShort);
	} catch (Exception e) {
		e.printStackTrace();
	}
  }
  
  public void testValueDomainSaveGF25444_41_122_1()
  {
	  try
	  {
		  gov.nih.nci.ncicb.cadsr.loader.parser.XMIParser2 parser = new gov.nih.nci.ncicb.cadsr.loader.parser.XMIParser2();
		  gov.nih.nci.ncicb.cadsr.loader.event.UMLDefaultHandler handler = new gov.nih.nci.ncicb.cadsr.loader.event.UMLDefaultHandler();

		  parser.setEventHandler(handler);

		  String xmiFileName = "gov/nih/nci/ncicb/cadsr/loader/test/cadsrapi/GF25444_41_112_1.xmi";
		  String path = getClass().getClassLoader().getResource(xmiFileName).getPath();
		  java.io.File file = new java.io.File(path);

		  parser.parse(file.getAbsolutePath().replace("\\", "/"));

		  gov.nih.nci.ncicb.cadsr.loader.ElementsLists elementsList = gov.nih.nci.ncicb.cadsr.loader.ElementsLists.getInstance();
		  java.util.List<ValueDomainBean> valueDomainList = elementsList.getElements(new ValueDomainBean());
		  assertNotNull(valueDomainList);
		  assertEquals(new Integer(valueDomainList.size()), new Integer(1));

		  ValueDomain valueDomain = valueDomainList.get(0);
		  valueDomain.setVersion(new Float(1.0));
		  valueDomain.setContext(getContext());
		  ConceptualDomain cd = new ConceptualDomainBean();
		  cd.setPublicId("1111");
		  cd.setVersion(new Float(1.0));
		  
		  valueDomain.setConceptualDomain(cd);
		  
		  for (PermissibleValue pv: valueDomain.getPermissibleValues()) {
			  pv.getValueMeaning().setContext(getContext());
		  }
		  
		  ValueDomain createdVD = DAOAccessor.getValueDomainDAO().create(valueDomain);
		  assertNotNull(createdVD);
		  
		  List<PermissibleValue> createdPVs = DAOAccessor.getValueDomainDAO().getPermissibleValues(createdVD.getId());
		  for (PermissibleValue pv: createdPVs) {
			  if (pv.getValue().equalsIgnoreCase("Other")) {
				  assertNotNull(pv.getValueMeaning());
				  assertNotNull(pv.getValueMeaning().getId());
				  assertNull(pv.getValueMeaning().getConceptDerivationRule());
			  }
		  }

		  Connection con = getDataSource().getConnection();
		  Statement st = con.createStatement();
		  ResultSet resultSet = st.executeQuery("SELECT COUNT(*) FROM VALUE_MEANINGS WHERE short_meaning = 'Doxorubicin/Filgrastim' AND CONDR_IDSEQ IS NULL");

		  int count = 0;

		  while (resultSet.next() == true)
		  {
			  count++;
		  }

		  assertTrue((count == 1));

		  st = con.createStatement();
		  resultSet = st.executeQuery("SELECT COUNT(*) FROM VALUE_MEANINGS WHERE short_meaning = 'Other'");

		  count = 0;

		  while (resultSet.next() == true)
		  {
			  count = resultSet.getInt(1);
		  }

		  assertTrue((count == 2));
	  }	  
	  catch(Throwable t)
	  {
		  t.printStackTrace();
		  fail(t.getMessage());
	  }
  }
  
  private Context getContext() {
	  Context ctx = new ContextBean();
	  ctx.setId("6BF1D8AD-29FA-6CF3-E040-A8C0955834A9");
	  ctx.setName("caBIG");
	  
	  return ctx;
  }

  private PermissibleValue getPV(String value, String[][] concepts) {
	  PermissibleValue pv = new PermissibleValueBean();
	  pv.setValue(value);
	  pv.setValueMeaning(getVM(value, concepts));
	  return pv;
  }
  
  private ValueMeaning getVM(String value, String[][] concepts) {
	  ValueMeaning vm = new ValueMeaningBean();
	  vm.setLongName(value);
	  vm.setConceptDerivationRule(getCDR(concepts));
	  vm.setContext(getContext());
	  return vm;
  }
  
  private ConceptDerivationRule getCDR(String[][] concepts) {
	  ConceptDerivationRule cdr = new ConceptDerivationRuleBean();
	  cdr.setComponentConcepts(getCompCon(concepts, cdr));
	  
	  return cdr;
  }
  
  private List<ComponentConcept> getCompCon(String[][] concepts, ConceptDerivationRule cdr) {
	  List<ComponentConcept> compCons = new ArrayList<ComponentConcept>();
	  
	  for (int i=0;i<concepts.length;i++) {
		  ComponentConcept compCon = new ComponentConceptBean();
		  Concept c = new ConceptBean();
		  c.setPreferredName(concepts[i][0]);
		  c.setLongName(concepts[i][1]);
		  
		  compCon.setConcept(c);
		  compCon.setOrder(i);
		  compCon.setConceptDerivationRule(cdr);
		  
		  compCons.add(compCon);
	  }
	  
	  return compCons;
  }

  public static Test suite()
  {
    TestSuite suite = new TestSuite(ValueDomainDAOTest.class);
    return suite;
  }

  public static void main(String[] args) {
    TestRunner.run(ValueDomainDAOTest.class);
  }


}