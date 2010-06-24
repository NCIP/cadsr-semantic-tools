package gov.nih.nci.ncicb.cadsr.loader.test.persister;

import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.domain.bean.DataElementBean;
import gov.nih.nci.ncicb.cadsr.domain.bean.DataElementConceptBean;
import gov.nih.nci.ncicb.cadsr.domain.bean.ReferenceDocumentBean;
import gov.nih.nci.ncicb.cadsr.domain.bean.ValueDomainBean;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrPrivateApiModule;
import gov.nih.nci.ncicb.cadsr.loader.persister.DEPersister;
import gov.nih.nci.ncicb.cadsr.loader.persister.PersisterUtil;
import gov.nih.nci.ncicb.cadsr.loader.test.MainTestCase;
import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DEPersisterTest extends MainTestCase {
    
    public DEPersisterTest() {
      super("DEPersisterTest", DEPersisterTest.class, "/gov/nih/nci/ncicb/cadsr/loader/test/persister/gf22793.xls");
    }
    
    public void setUp() throws Exception {
       super.setUp();
    }
    
    public boolean runInRealContainer() {
        return false;
    }
    
    public boolean requiresDatabase() {
        return true;
    }
    
    public void containerSetUp() {
        
    }
    
    public void testGF22793() {
    	
    	ClassPathXmlApplicationContext cpCtx = new ClassPathXmlApplicationContext(new String[]{"beans.xml", "spring-datasources.xml", "loader-spring.xml"});
    	
    	
    	DataElementBean de = new DataElementBean();
    	de.setPublicId("1111");
    	de.setVersion(new Float(1.0));
    	
    	DataElementConcept dec = new DataElementConceptBean();
    	dec.setId("DEC1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    	
    	ValueDomain vd = new ValueDomainBean();
    	vd.setId("VD1xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    	vd.setPublicId("1111");
    	vd.setVersion(new Float(1.0));
    	
    	de.setDataElementConcept(dec);
    	de.setValueDomain(vd);
    	
    	ReferenceDocument rd = new ReferenceDocumentBean();
    	rd.setType("Preferred Question Text");
    	rd.setName("Inserted RD Name");
    	rd.setText("Inserted RD Name");
    	
    	List<ReferenceDocument> rds = new ArrayList<ReferenceDocument>();
    	rds.add(rd);
    	
    	UMLDefaults.getInstance().initParams("SIW_TEST", new Float(1.0), "MATHURA");
    	UMLDefaults.getInstance().initWithDB();
    	
    	de.setReferenceDocuments(rds);
    	
    	ElementsLists.getInstance().addElement(de);
    	CadsrPrivateApiModule cadsrModule = new CadsrPrivateApiModule();
    	new LookupUtil().setCadsrModule(cadsrModule);
    	
    	DEPersister dePersister = new DEPersister();
    	dePersister.setPersisterUtil(new PersisterUtil());
    	dePersister.persist();
    	
    	try {
			Connection con = super.getDataSource().getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from REFERENCE_DOCUMENTS");
			
			boolean comp = super.compareResultSet(rs, "REFERENCE_DOCUMENTS");
			assertTrue(comp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
