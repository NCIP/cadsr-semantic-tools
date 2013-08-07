/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.test;

import gov.nih.nci.ncicb.cadsr.loader.test.excel.DBExcelUtility;
import gov.nih.nci.ncicb.cadsr.loader.test.excel.ExcelResultSet;
import gov.nih.nci.ncicb.cadsr.loader.test.excel.ExcelResultSetImpl;

import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.xml.XmlDataSet;

public class DBTestCase extends DatabaseTestCase {

	protected Log log;
    protected DBExcelUtility dbExcelUtility = new DBExcelUtility();
    protected DataSource dataSource;
    protected URL dataURL;
    protected String dataSetString;

    public DBTestCase(URL dataURL)
    {
        log = LogFactory.getLog("test.junit");
        this.dataURL = dataURL;
    }
    
    @Override
	protected IDatabaseConnection getConnection() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return createExcelDataSet();
	}

    public ArrayList createDataObject(URL url, String resultname)
            throws Exception
    {
        return dbExcelUtility.createDataObject(url, resultname);
    }

    protected URL getDataURL()
    {
        if (dataURL == null)
        {
            throw new NullPointerException("No dataURL set for the database test class.");
        }

        return dataURL;
    }

    protected ExcelResultSet getExcelResultSet(String resultname)
            throws Exception
    {
        return dbExcelUtility.getExcelResultSet(getDataURL(), resultname);
    }

    public ResultSet getExpectedResultSet(String resultsetName)
            throws Exception
    {
        InputStream is = getDataURL().openStream();
        ResultSet resultSet = new ExcelResultSetImpl(is, resultsetName);

        return resultSet;
    }

    protected void setUp()
            throws Exception
    {
        super.setUp();
    }

    /**
     * converts to to a DBUnit type data set
     *
     * @param alltables
     * @return
     * @throws Exception
     */
    public IDataSet createCustomDataSet(ArrayList alltables)
            throws Exception
    {
        dataSetString = dbExcelUtility.getExcelDataSet(getDataURL(), alltables);

        StringReader reader = new StringReader(dataSetString);
        return new XmlDataSet(reader);
    }

    public IDataSet createCustomDataSet(String tableName)
            throws Exception
    {
        InputStream is = getDataURL().openStream();
        dataSetString = dbExcelUtility.getExcelDataSet(is, tableName);
        StringReader reader = new StringReader(dataSetString);
        return new XmlDataSet(reader);
    }


    public ITable createQueryTable(IDatabaseConnection conn, String resultName, String query)
            throws Exception
    {
        return conn.createQueryTable(resultName, query);
    }

    public ITable createCustomTable(String tableName)
            throws Exception
    {
        IDataSet ds = createCustomDataSet(tableName);
        return ds.getTable(tableName);
    }

    public ITable createCustomTable(String sheetName, String tableName)
    throws Exception
    {
        IDataSet ds = createCustomDataSet(sheetName);
        return ds.getTable(tableName);
    }

    //Caution : this function does consider the All Other tables in
    //the database (to verify Database Integrity criteria
    protected IDataSet createExcelDataSet()
            throws Exception
    {
        String data = dbExcelUtility.getExcelDataSet(getDataURL(), null);
        StringReader reader = new StringReader(data);

        return new XmlDataSet(reader);
    }

    protected boolean compareResultSet(ResultSet rs, ExcelResultSet ers)
            throws Exception
    {
        return dbExcelUtility.compareResultSet(rs, ers);
    }

    public void debugResultSet(ResultSet resultSet)
            throws Exception
    {
        DBExcelUtility.debugResultSet(resultSet);
    }

    public boolean compareResultSet(ResultSet rs, ResultSet expected)
            throws Exception
    {
        /**
         * RK working on this
         */
        return false;
    }

    public boolean compareResultSet(ResultSet rs, String resultname)
            throws Exception
    {
        return dbExcelUtility.compareResultSet(rs, dbExcelUtility.getExcelResultSet(getDataURL(), resultname));
    }

}
