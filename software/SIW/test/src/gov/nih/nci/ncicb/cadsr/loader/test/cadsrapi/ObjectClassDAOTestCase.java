/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.test.cadsrapi;

import gov.nih.nci.ncicb.cadsr.dao.ObjectClassDAO;
import gov.nih.nci.ncicb.cadsr.dao.PropertyDAO;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.loader.test.MainTestCase;
import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;

import java.util.ArrayList;
import java.util.List;

public class ObjectClassDAOTestCase extends MainTestCase {

	public ObjectClassDAOTestCase() {
		super("ObjectClassDAOTestCase", ObjectClassDAOTestCase.class, "/gov/nih/nci/ncicb/cadsr/loader/test/cadsrapi/gf6757.xls");
	}
	
	@Override
	protected void containerSetUp() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean requiresDatabase() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean runInRealContainer() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setUp() throws Exception{
		super.setUp();
	}
	
	public void testGF6757() {
		ObjectClassDAO ocDAO = (ObjectClassDAO)DAOAccessor.getObjectClassDAO();
		List<ObjectClass> ocList = ocDAO.findByConceptCodes(new String[]{"C111", "C000"}, null);
		assertNotNull(ocList);
		assertTrue (ocList.size() == 0);
	}
	
	/*public void testGF6757_2() {
		PropertyDAO propDAO = (PropertyDAO)DAOAccessor.getPropertyDAO();
		List<Property> propList = propDAO.findByConceptCodes(new String[]{"Cabc","Cxyz"}, new ArrayList<String>());
		assertNotNull(propList);
		assertTrue (propList.size() == 0);
	}*/

}
