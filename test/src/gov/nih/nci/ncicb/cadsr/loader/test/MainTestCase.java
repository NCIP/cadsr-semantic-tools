package gov.nih.nci.ncicb.cadsr.loader.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.NamingManager;
import javax.sql.DataSource;

import junit.framework.TestCase;
import oracle.jdbc.pool.OracleDataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.filter.SequenceTableFilter;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

public abstract class MainTestCase extends TestCase
{
	protected static final String WORKING_IN_DIR = "/tmp";
	protected static final String WORKING_OUT_DIR = WORKING_IN_DIR+"/out";
	
    static ITableFilter filter;
    static List allDeleteCmds;
    static PropertyManager propertyManager;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    static String propertyLocation = "/datasource.properties";
    static String ddlLocation = "/cadsr.sql";
    static List tableNames;
    protected String schema;
    protected URL dataURL;
    protected DatabaseTestWrapper databaseWrapper;
    protected String dataSourceJNDIName;
    protected DataSource explicitDataSource;
    protected String explicitDataSourceSchema;
    protected Log log;
    private static Pattern createTablePattern = Pattern.compile("CREATE\\s+TABLE");
    protected boolean runInRealContainer;
    

    static
    {
        propertyManager = new PropertyManager(getProperties());
        
        if (!NamingManager.hasInitialContextFactoryBuilder())
        {
        	try {
        		SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
				builder.activate();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //NamingManager.setInitialContextFactoryBuilder(builder);
        }
        
    }

    /**
     * uses the default unit test schema and unit test settings
     *
     * @param name
     */
    public MainTestCase(String name)
    {
        super(name);
        log = LogFactory.getLog("test.junit");
    }

    public MainTestCase(String name, Boolean runInContainer)
    {
        this(name);
        runInRealContainer = runInContainer.booleanValue();
    }

    /**
     * Loads the data into the unit test database
     *
     * @param name
     * @param clazz
     * @param dataURLLocation
     */
    public MainTestCase(String name, Class clazz, String dataURLLocation)
    {
        this(name);
        this.dataURL = getResource(clazz, dataURLLocation);
    }

    /**
     * use the container category settings including schema and jndi
     * settings for the data source
     *
     * @param name
     * @param clazz
     * @param dataURLLocation
     * @param containerCategory
     */
    public MainTestCase(String name, Class clazz, String dataURLLocation, String containerCategory)
    {
        this(name);
        this.dataURL = getResource(clazz, dataURLLocation);
        schema = getPropertyManager().getContainerDataSourceSchema(containerCategory);
        dataSourceJNDIName = getPropertyManager().getContainerDataSourceJNDI(containerCategory);
    }


    /**
     * Explicitly define the data source.
     * Explicitly defining the data source bypasses the use of the db that is normally configured
     * for use in Unit Tests.
     */
    public MainTestCase(String name, Class clazz, String dataURLLocation, DataSource dataSource, String schema)
    {
        this(name);

        this.dataURL = dataURLLocation == null ? null : getResource(clazz, dataURLLocation);
        this.schema = schema;
        this.explicitDataSource = dataSource;
    }

    public MainTestCase(String name, Class clazz, String dataURLLocation, String containerCategory, Boolean runInContainer)
    {
        this(name, clazz, dataURLLocation, containerCategory);
        runInRealContainer = runInContainer.booleanValue();
    }
    
	protected String formatDate(Date date) {
		String returnValue = null;
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		
		returnValue = formatter.format(date);
		
		return returnValue;
	}
	
	protected String formatDate(Calendar calendar) {
		String returnValue = null;
		Date date = calendar.getTime();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		
		returnValue = formatter.format(date);
		
		return returnValue;
	}
	
    /*public void begin(WebRequest request)
    {
        request.addParameter("runInRealContainer", String.valueOf(runInRealContainer),
                WebRequest.POST_METHOD);
    }*/


    protected static PropertyManager getPropertyManager()
    {
        return propertyManager;
    }

    protected boolean doCleanInsert()
    {
        return true;
    }

    protected void enableImmediateSync() throws Exception
    {
        /* if (!runInRealContainer())
         {
             Connection con =
                     databaseWrapper.getConnection().getConnection();
             if (!isOracle(con))
             {
                 Statement stmt =
                         con.createStatement();
                 stmt.executeUpdate("SET WRITE_DELAY FALSE");
             }
         }*/
    }


    protected void disableImmediateSync() throws Exception
    {
        if (!runInRealContainer())
        {
            Connection con =
                    databaseWrapper.getConnection().getConnection();
            if (!isOracle(con))
            {
                Statement stmt =
                        con.createStatement();
                stmt.executeUpdate("SET WRITE_DELAY 5000");
            }
        }
    }

    protected void closeInMemoryConnection()
            throws Exception
    {
        if (databaseWrapper != null)
        {
            databaseWrapper.closeInMemoryConnection();
        }
    }

    static List getTableNames() throws Exception
    {
        if (tableNames != null)
        {
            return tableNames;
        }
        else
        {
            tableNames = new ArrayList();
        }

        String ddl = getDDL();

        if (ddl != null)
        {

            BufferedReader reader = new BufferedReader(new StringReader(ddl));
            String line = null;

            while ((line = reader.readLine()) != null)
            {
                Matcher m = createTablePattern.matcher(line);
                if (m.find())
                {
                    int start = m.end();
                    int end = line.length() - 1;
                    String tableName = line.substring(start, end).trim();


                    tableNames.add(tableName);
                }
            }
        }

        /*Map classMetaData = ((EntityManagerFactoryImpl)emf).getSessionFactory().getAllClassMetadata();
        Iterator tableClassesIter = classMetaData.keySet().iterator();
        while (tableClassesIter.hasNext()) {
        	String className = (String)tableClassesIter.next();
        	String tableName = className.substring(className.lastIndexOf(".")+1);
        	tableNames.add(tableName);
        }*/
        return tableNames;
    }

    static String getDDL()
    {
        String ddl = null;

        try
        {
            URL url = MainTestCase.class.getResource(ddlLocation);

            if (url != null)
            {
                ddl = FileUtil.getFileContent(url.openStream());
            }
            else
            {
                System.err.println("Unable to find DDL location '" + ddlLocation + "'");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return ddl;
    }

    static Properties getProperties()
    {
        Properties properties = new Properties();

        try
        {
            URL url = MainTestCase.class.getResource(propertyLocation);

            if (url != null)
            {
                properties.load(url.openStream());
            }
            else
            {
                System.err.println("Unable to find unit test property location '" + propertyLocation + "'");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return properties;
    }
    
    protected static Properties getDBProperties() {
    	Properties properties = new Properties();
    	
    	properties.put("db.url", getPropertyManager().getUnitDataSourceURL());
    	properties.put("db.username", getPropertyManager().getUnitDataSourceUser());
    	properties.put("db.password", getPropertyManager().getUnitDataSourcePassword());
    	
    	return properties;
    }

    protected DataSource getDataSource()
    {
        return databaseWrapper.getDataSource();
    }

    protected IDatabaseConnection getConnection()
            throws Exception
    {
        return databaseWrapper.getConnection();
    }

    protected boolean isCactusMode()
    {
        return runInRealContainer();
    }

    protected String getDefaultDataSchema()
    {
        return propertyManager.getUnitDataSourceSchema();
    }

    protected URL getResource(Class clazz, String urlPath)
    {
        URL url = clazz.getResource(urlPath);

        if (url == null)
        {
            throw new NullPointerException("URL Location = '" + urlPath + "' not found.");
        }

        return url;
    }

    protected ITable queryTable(String resultName, String query)
            throws Exception
    {
        return databaseWrapper.createQueryTable(getConnection(), resultName, query);
    }

    protected ITable getCustomTable(String tableName) throws Exception
    {
        return databaseWrapper.createCustomTable(tableName);
    }

    protected ITable getCustomTable(String sheetName, String tablename) throws Exception
    {
        return databaseWrapper.createCustomTable(sheetName, tablename);
    }

    protected ResultSet getExpectedResultSet(String resultsetName)
            throws Exception
    {
        return databaseWrapper.getExpectedResultSet(resultsetName);
    }

    /*protected List getQueueMessages(JMSManager manager, JMSQueueConfiguration queueConfig)
            throws JMSException, NamingException
    {
        QueueReceiver receiver = manager.createReceiver(queueConfig, false, Session.AUTO_ACKNOWLEDGE);

        return getQueueMessages(receiver);
    }*/

    /*protected List getQueueMessages(QueueReceiver receiver)
            throws JMSException
    {
        List list = new ArrayList();
        ObjectMessage message = null;

        do
        {
            message = (ObjectMessage) receiver.receive(1000);
            if (message != null)
            {
                list.add(message.getObject());
            }
        }
        while (message != null);

        return list;
    }*/

    protected void setUp()
            throws Exception
    {
        /*String param = (request != null) ? request.getParameter("runInRealContainer") : null;

        if (param != null && param.trim().equalsIgnoreCase("true"))
        {
            runInRealContainer = true;
        }*/

/*        if (!runInRealContainer())
        {
            if (!NamingManager.hasInitialContextFactoryBuilder())
            {
            	SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
            	builder.activate();
                //NamingManager.setInitialContextFactoryBuilder(builder);
            }
        }*/

        if (requiresDatabase())
        {
            DataSource dataSource = null;

            if (runInRealContainer())
            {
                if (dataSourceJNDIName == null)
                {
                    throw new NullPointerException("The test requires a JNDI name for the data source");
                }

                dataSource = createJNDIDataSource(dataSourceJNDIName);

            }
            else
            {
                if (explicitDataSource != null)
                {
                    dataSource = explicitDataSource;
                }
                else
                {
                    schema = getDefaultDataSchema();
                    dataSource = createDefaultDataSource();
                }
            }

            if (dataURL != null) {

            	databaseWrapper = new DatabaseTestWrapper(dataSource, dataURL, schema, runInRealContainer());
                databaseWrapper.setUp();
                
                databaseWrapper = new DatabaseTestWrapper(dataSource, dataURL, schema, runInRealContainer());
                databaseWrapper.setUpdate(true);
                databaseWrapper.setUp();
            }
        }
    }


    protected abstract boolean runInRealContainer();

    protected abstract boolean requiresDatabase();

    protected abstract void containerSetUp()
            throws Exception;

    protected ArrayList createDataObject(String resultname)
            throws Exception
    {
        return databaseWrapper.createDataObject(resultname);
    }

    protected boolean compareResultSet(ResultSet rs, String resultName)
            throws Exception
    {
        return databaseWrapper.compareResultSet(rs, resultName);
    }


    protected DataSource createJNDIDataSource(String jndiName)
            throws Exception
    {
        InitialContext ctx = new InitialContext();
        Object dataSourceObject = (DataSource) ctx.lookup(jndiName);

        if (dataSourceObject == null)
        {
            throw new NullPointerException("JNDI Data Source Lookup for '" + jndiName + "' not found.");
        }

        if (!DataSource.class.isAssignableFrom(dataSourceObject.getClass()))
        {
            throw new Exception("The JNDI Lookup '" + jndiName +
                    "' is of type '" + dataSourceObject.getClass() + "' and not a DataSource");
        }

        return (DataSource) dataSourceObject;
    }

    protected DataSource createDefaultDataSource()
            throws Exception
    {
        OracleDataSource unitDataSource = new OracleDataSource();

        String url = propertyManager.getUnitDataSourceURL();
        String user = propertyManager.getUnitDataSourceUser();
        String password = propertyManager.getUnitDataSourcePassword();

        unitDataSource.setURL(url);
        unitDataSource.setUser(user);
        unitDataSource.setPassword(password);

        return unitDataSource;
    }

    protected void bindToJNDI(String envJNDIName, String realJNDITree)
            throws Exception
    {
        InitialContext ctx = new InitialContext();
        Object dataSourceObject = (DataSource) jndiLookup(envJNDIName);

        if (dataSourceObject == null)
        {
            dataSourceObject = (DataSource) jndiLookup(realJNDITree);

            if (dataSourceObject == null)
            {
                throw new NullPointerException("The env JNDI='" + envJNDIName + "' and real JNDI='" + realJNDITree + "' are both empty.");
            }

            ctx.bind(envJNDIName, dataSourceObject);
        }
    }

    protected Object jndiLookup(String jndiName)
            throws Exception
    {
        InitialContext ctx = new InitialContext();
        Object ret = null;

        try
        {
            ret = ctx.lookup(jndiName);
        }
        catch (NamingException ne)
        {
            //ne.printStackTrace();
        }

        return ret;
    }

    protected void debugResultSet(ResultSet resultSet)
            throws Exception
    {
        databaseWrapper.debugResultSet(resultSet);
    }

    protected boolean compareResultSet(ResultSet rs, ResultSet expected)
            throws Exception
    {
        return databaseWrapper.compareResultSet(rs, expected);
    }

    protected Date createDate(String date)
    {
        return dateFormat.parse(date, new ParsePosition(0));
    }

    protected void tearDown()
            throws Exception
    {
        //MockContextFactory.revertSetAsInitial();
        if (databaseWrapper != null)
        {
            databaseWrapper.tearDown();
        }
    }

    /*protected void jmsBind(JMSQueueConfiguration queueConfig)
            throws NamingException
    {
        String jndiQueueName = queueConfig.getQueueName();
        String jndiQueueConnectionFactory = queueConfig.getQueueConnectionFactory();

        Queue mockPriceAdjustmentQueue = new org.mockejb.jms.MockQueue(jndiQueueName);
        QueueConnectionFactory mockQueueConnectionfactory = new org.mockejb.jms.QueueConnectionFactoryImpl();

        //manage the queue bindings
        InitialContext ctx = new InitialContext();

        ctx.rebind(jndiQueueConnectionFactory, mockQueueConnectionfactory);
        ctx.rebind(jndiQueueName, mockPriceAdjustmentQueue);
    }*/

    /*protected void jmsBind(JMSTopicConfiguration topicConfig)
            throws NamingException
    {
        String jndiTopicName = topicConfig.getTopicName();
        String jndiTopicConnectionFactory = topicConfig.getTopicConnectionFactory();

        MockTopic topic = new MockTopic(jndiTopicName);
        TopicConnectionFactoryImpl mockTopicConnectionfactory = new org.mockejb.jms.TopicConnectionFactoryImpl();

        //manage the topic bindings
        InitialContext ctx = new InitialContext();

        ctx.rebind(jndiTopicConnectionFactory, mockTopicConnectionfactory);
        ctx.rebind(jndiTopicName, topic);
    }*/

    /**
     * @created June 24, 2005
     */
    public static class PropertyManager
    {
        Properties properties;

        public PropertyManager(Properties properties)
        {
            this.properties = properties;
        }

        public String getContainerDataSourceSchema(String category)
        {
            String property = category + ".container.schema";

            return properties.getProperty(property);
        }

        public String getContainerDataSourceJNDI(String category)
        {
            String property = category + ".container.datasource.jndi";

            return properties.getProperty(property);
        }

        public String getUnitDataSourceSchema()
        {
            String property = "common.unit.schema";

            return properties.getProperty(property);
        }

        public String getUnitDataSourceJNDI()
        {
            String property = "common.unit.jndi";

            return properties.getProperty(property);
        }

        public String getUnitDataSourceURL()
        {
            String property = "common.unit.datasource.url";

            return properties.getProperty(property);
        }

        public String getUnitDataSourceUser()
        {
            String property = "common.unit.datasource.user";

            return properties.getProperty(property);
        }

        public String getUnitDataSourcePassword()
        {
            String property = "common.unit.datasource.password";

            return properties.getProperty(property);
        }
    }

    /**
     * @created July 5, 2005
     */
    public class MockContextFactoryBuilder implements javax.naming.spi.InitialContextFactoryBuilder
    {
        public InitialContextFactory createInitialContextFactory(Hashtable environment)
                throws NamingException
        {
        //    return new MockContextFactory();
        	return null;
        }
    }


    /**
     * @created June 24, 2005
     */
    protected class DBUnitWrapperException extends Exception
    {

        private Exception causeException;

        public DBUnitWrapperException()
        {
            causeException = null;
        }

        public DBUnitWrapperException(String message)
        {
            super(message);
            causeException = null;
        }

        public DBUnitWrapperException(Exception ex)
        {
            causeException = null;
            causeException = ex;
        }

        public DBUnitWrapperException(String message, Exception ex)
        {
            super(message);
            causeException = null;
            causeException = ex;
        }

        public Exception getCausedByException()
        {
            return causeException;
        }

        public String getMessage()
        {
            String msg = super.getMessage();

            if (causeException == null)
            {
                return msg;
            }
            if (msg == null)
            {
                return "nested exception is: " + causeException.toString();
            }
            else
            {
                return msg + "; nested exception is: " + causeException.toString();
            }
        }

        public void printStackTrace(PrintStream ps)
        {
            if (causeException == null)
            {
                super.printStackTrace(ps);
            }
            else
            {
                synchronized (ps)
                {
                    ps.println(this);
                    causeException.printStackTrace(ps);
                    super.printStackTrace(ps);
                }
            }
        }

        public void printStackTrace()
        {
            printStackTrace(System.err);
        }

        public void printStackTrace(PrintWriter pw)
        {
            if (causeException == null)
            {
                super.printStackTrace(pw);
            }
            else
            {
                synchronized (pw)
                {
                    pw.println(this);
                    causeException.printStackTrace(pw);
                    super.printStackTrace(pw);
                }
            }
        }
    }

    
    class DatabaseTestWrapper extends DBTestCase
    {
        protected DataSource dataSource;
        protected URL dataURL;
        protected String schema;
        protected Connection conn = null;
        protected DatabaseConnection dbconnection = null;
        protected boolean inContainer;
        protected IDataSet dataSetInUse;
        private boolean update;
        
        


        public boolean isUpdate() {
			return update;
		}

		public void setUpdate(boolean update) {
			this.update = update;
		}

		public DatabaseTestWrapper(DataSource dataSource, URL dataURL, String schema, boolean inContainer)
                throws Exception
        {
            super(dataURL);
            this.dataSource = dataSource;
            this.dataURL = dataURL;
            this.schema = schema;
            this.inContainer = inContainer;

            conn = dataSource.getConnection();

            if (!inContainer && !isOracle(conn))
            {
             /*   Statement stmt = conn.createStatement();

                stmt.execute("SET write_delay 5000");*/
            }

            if (conn == null)
            {
                throw new NullPointerException("Data source '" +
                        dataSource.toString() + "' resulted in a null connection");
            }

            if (schema == null || schema.trim().length() == 0)
            {
                dbconnection = new DatabaseConnection(conn);
            }
            else
            {
                dbconnection = new DatabaseConnection(conn, schema);
            }


            try
            {
                DatabaseConfig config = dbconnection.getConfig();

                if (inContainer)
                {
                    //String id2 = "http://www.dbunit.org/features/qualifiedTableNames";
                    //config.setFeature(id2, true);
                }

                String id = "http://www.dbunit.org/features/batchedStatements";
                config.setFeature(id, false);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public ArrayList createDataObject(String resultname)
                throws Exception
        {
            return createDataObject(dataURL, resultname);
        }

        public void generateExcelSchema()
                throws Exception
        {
            if (!inContainer)
            {
                String excelFile = "schema.xls";

                if (!(new File(excelFile)).exists())
                {
              //      ExcelSchemaGenerator.generateSchema(excelFile, conn, schema);
                }
            }
        }

        protected IDatabaseConnection getConnection()
                throws Exception
        {
            if (dbconnection.getConnection().isClosed())
            {
                conn = dataSource.getConnection();

                if (schema == null || schema.trim().length() == 0)
                {
                    dbconnection = new DatabaseConnection(conn);
                }
                else
                {
                    dbconnection = new DatabaseConnection(conn, schema);
                }

                // dbconnection = new DatabaseConnection(conn);
            }

            return dbconnection;
        }

        protected IDataSet getDataSet()
                throws Exception
        {
            try
            {
                ArrayList alltables = (ArrayList) getTableNames();//TestCaseUtility.getAllTableList(conn);

                IDataSet dataset = super.createCustomDataSet(alltables);
                dataSetInUse = getFilteredDataset(dataset);
                return dataSetInUse;
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        protected DataSource getDataSource()
        {
            return dataSource;
        }

        protected DatabaseOperation getSetUpOperation()
                throws Exception
        {
        	if (!update) {
        		if (doCleanInsert())
                {
                    return DatabaseOperation.CLEAN_INSERT;
                }

                return DatabaseOperation.REFRESH;
        	}
            return DatabaseOperation.UPDATE;
        }

        protected IDataSet getFilteredDataset(IDataSet dataset)
                throws Exception
        {
            IDatabaseConnection conn = null;

            try
            {
                conn = getConnection();

                long start = System.currentTimeMillis();
                if (filter == null)
                {
                    filter = new SequenceTableFilter(sortedTableNames); //new DatabaseSequenceFilter(conn);
                }
                long end = System.currentTimeMillis();

                //System.err.println("TOOK:" + (end - start));

                IDataSet newds = new FilteredDataSet(filter, dataset);

                return newds;
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        protected void cleanUpAll() throws SQLException
        {
            if (doCleanInsert())
            {
                Statement stmt = null;
                ResultSet tables = null;

                try
                {
                    stmt = conn.createStatement();
                    conn.setAutoCommit(false);
                    String deleteCmd = "DELETE FROM ";
                    if (isOracle(conn))
                    {
                        try
                        {
                            stmt.executeQuery("SET CONSTRAINTS ALL DEFERRED");
                        }
                        catch (SQLException e)
                        {
                        }

                        //deleteCmd = "TRUNCATE TABLE ";
                    }
                    else
                    {
                       /* try
                        {
                            stmt.executeQuery("SET REFERENTIAL_INTEGRITY FALSE");
                        }
                        catch (SQLException e)
                        {
                            log.error(e, e);
                            e.printStackTrace(System.err);
                            throw e;
                        }*/
                    }

                    if (allDeleteCmds == null)
                    {
                        allDeleteCmds = new LinkedList();
                        tables = conn.getMetaData().getTables(null, schema, null, null);

                        while (tables.next())
                        {
                            String tableName = tables.getString("TABLE_SCHEM") + "." + tables.getString("TABLE_NAME");
                            String deleteSQL = deleteCmd + tableName;
                            allDeleteCmds.add(deleteSQL);
                        }
                    }

                    try
                    {
                        Iterator iter = allDeleteCmds.iterator();
                        while (iter.hasNext())
                        {
                            String deleteSQL = (String) iter.next();
                            stmt.executeUpdate(deleteSQL);
                        }
                    }
                    catch (SQLException sqe)
                    {

                    }

                    conn.commit();

                    conn.setAutoCommit(true);
                }
                catch (SQLException e)
                {
                    log.warn(e.getMessage() + ": Failed to delete tables properly", e);
                }
                finally
                {
                    if (stmt != null)
                    {
                        stmt.close();
                    }
                    if (tables != null)
                    {
                        tables.close();
                    }
                }
            }
        }

        protected void setUp()
                throws Exception
        {
            try
            {
                if (doCleanInsert())
                {
                    cleanUpAll();
                }

                super.setUp();
                closeConnection(conn);
            }
            catch (Exception e)
            {
                String message = "Error in data file processing filename = '" + dataURL.toString() + "'";

                System.out.println(message + "," + e.getMessage() + "\n" + super.dataSetString);

                throw new DBUnitWrapperException(message, e);
            }
        }

        protected void closeConnection(Connection conn)
                throws SQLException
        {
            if (conn != null)
            {
                conn.close();
                conn = null;
            }
        }

        protected void closeInMemoryConnection()
                throws SQLException
        {
            if (!inContainer)
            {
                /* if (!isOracle(dataSourceProd))
                 {
                     try
                     {
                         Connection inMemoryConn = dataSourceProd.getConnection();
                         Statement st = inMemoryConn.createStatement();
                         st.execute("SHUTDOWN");
                         inMemoryConn.close();
                     }
                     catch (SQLException e)
                     {
                         log.error(e, e);
                     }
                 }*/
            }
            else
            {
                //Connection con = dataSourceProd.getConnection();
                if (conn != null && !conn.isClosed())
                {
                    conn.close();
                    conn = null;
                }
            }
        }

        protected void tearDown()
                throws Exception
        {
            closeInMemoryConnection();
        }

    }

    private boolean isOracle(DataSource ds)
    {
        Connection con = null;
        try
        {
            con = ds.getConnection();
            String productName = con.getMetaData().getDatabaseProductName();
            return (productName != null && productName.equalsIgnoreCase("Oracle"));
        }
        catch (SQLException e)
        {
            return false;
        }
        finally
        {
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException exc)
                {

                }
            }
        }
    }

    private boolean isOracle(Connection con)
    {
        try
        {
            String productName = con.getMetaData().getDatabaseProductName();
            return (productName != null && productName.equalsIgnoreCase("Oracle"));
        }
        catch (SQLException e)
        {
            return false;
        }
    }
    
    private static String[] sortedTableNames = new String[]{
					    		"ACTIONS_LOV",
					    		"AC_STATUS_LOV",
					    		"BUSINESS_ROLES_LOV",
					    		"CM_STATES_LOV",
					    		"SECURITY_CONTEXTS_LOV",
					    		"AC_ACTIONS_MATRIX",
					    		"AC_CI_BU",
					    		"AC_TYPES_LOV",
					    		"LIFECYCLES_LOV",
					    		"PROGRAM_AREAS_LOV",
					    		"CONTEXTS",
					    		"ORGANIZATIONS",
					    		"STEWARDS",
					    		"ADMINISTERED_COMPONENTS",
					    		"REGISTRARS",
					    		"REG_STATUS_LOV",
					    		"SUBMITTERS",
					    		"AC_REGISTRATIONS",
					    		"COMPLEX_REP_TYPE_LOV",
					    		"CON_DERIVATION_RULES_EXT",
					    		"CSI_TYPES_LOV",
					    		"CS_ITEMS",
					    		"CS_TYPES_LOV",
					    		"CLASSIFICATION_SCHEMES",
					    		"CS_CSI",
					    		"PERSONS",
					    		"CONTACT_ROLES_EXT",
					    		"AC_CONTACTS",
					    		"AC_CSI",
					    		"AC_CSI_BACKUP",
					    		"AC_CSI_CAT_BU",
					    		"AC_CSI_DISEASE",
					    		"AC_CSI_STAGING",
					    		"AC_HISTORIES",
					    		"AC_RECS",
					    		"AC_SUBJECTS",
					    		"AC_WF_RULES",
					    		"AC_WF_BUSINESS_ROLES",
					    		"ADDR_TYPES_LOV",
					    		"ADVANCE_RPT_LOV",
					    		"APP_COMPONENT_TYPES_LOV",
					    		"APP_OBJECTS_LOV",
					    		"APP_VERSIONS",
					    		"APP_OBJECTS",
					    		"APP_PRIV_LOV",
					    		"APP_ROLES_LOV",
					    		"APP_GRANTS",
					    		"CONCEPTUAL_DOMAINS",
					    		"VALUE_MEANINGS",
					    		"CD_VMS",
					    		"CHARACTER_SET_LOV",
					    		"CLASSIFICATION_SCHEMES_BACKUP",
					    		"CLASS_SCHEME_ITEMS_BACKUP",
					    		"COMM_TYPES_LOV",
					    		"DATATYPES_LOV",
					    		"OBJECT_CLASSES_LOV",
					    		"PROPERTIES_LOV",
					    		"OBJECT_CLASSES_EXT",
					    		"PROPERTIES_EXT",
					    		"CONCEPTS_EXT",
					    		"QUALIFIER_LOV_EXT",
					    		"DATA_ELEMENT_CONCEPTS",
					    		"FORMATS_LOV",
					    		"UNIT_OF_MEASURES_LOV",
					    		"REPRESENTATIONS_EXT",
					    		"VALUE_DOMAINS",
					    		"DATA_ELEMENTS",
					    		"COMPLEX_DATA_ELEMENTS",
					    		"RULE_FUNCTIONS_EXT",
					    		"COMPLEX_DE_RELATIONSHIPS",
					    		"CONCEPTS_STG",
					    		"CONTACT_ADDRESSES",
					    		"CONTACT_COMMS",
					    		"RELATIONSHIPS_LOV",
					    		"CSI_RECS",
					    		"CSI_STAGING",
					    		"CSI_TYPES_LOV_BACKUP",
					    		"CS_CSI_BACKUP",
					    		"CS_CSI_BU",
					    		"CS_RECS",
					    		"CS_TYPES_LOV_BACKUP",
					    		"DATA_ELEMENTS_BU",
					    		"DEC_RECS",
					    		"LANGUAGES_LOV",
					    		"DEFINITION_TYPES_LOV_EXT",
					    		"DEFINITIONS",
					    		"DESIGNATION_TYPES_LOV",
					    		"DESIGNATIONS",
					    		"DESIGNATIONS_BACKUP",
					    		"DESIGNATIONS_BACKUP_NEW",
					    		"DESIG_BACKUP",
					    		"DE_BACKUP",
					    		"DE_RECS",
					    		"DOCUMENT_TYPES_LOV",
					    		"FAILED_LOG",
					    		"GROUPS",
					    		"GROUP_RECS",
					    		"SC_GROUPS",
					    		"GRP_BUSINESS_ROLES",
					    		"LOOKUP_LOV",
					    		"META_TEXT",
					    		"META_UTIL_STATUSES",
					    		"MLOG$_AC_CSI",
					    		"MLOG$_CLASSIFICATION_SCHEM",
					    		"MLOG$_CS_CSI",
					    		"MLOG$_CS_ITEMS",
					    		"MLOG$_DATA_ELEMENTS",
					    		"MLOG$_DATA_ELEMENT_CONCEPT",
					    		"MLOG$_DEFINITIONS",
					    		"MLOG$_DESIGNATIONS",
					    		"MLOG$_VALUE_DOMAINS",
					    		"OC_CADSR",
					    		"OC_COMPRESULT",
					    		"OC_COMPRESULT2",
					    		"OC_VD",
					    		"PERMISSIBLE_VALUES",
					    		"PROGRAMS",
					    		"REFERENCE_DOCUMENTS",
					    		"REFERENCE_BLOBS",
					    		"REFERENCE_FORMATS_LOV",
					    		"REL_USAGE_LOV",
					    		"RL_RUL",
					    		"RULES_LOV",
					    		"RUPD$_AC_CSI",
					    		"RUPD$_CLASSIFICATION_SCHEM",
					    		"RUPD$_CS_CSI",
					    		"RUPD$_CS_ITEMS",
					    		"RUPD$_DATA_ELEMENTS",
					    		"RUPD$_DATA_ELEMENT_CONCEPT",
					    		"RUPD$_DEFINITIONS",
					    		"RUPD$_DESIGNATIONS",
					    		"RUPD$_VALUE_DOMAINS",
					    		"SC_CONTEXTS",
					    		"USER_ACCOUNTS",
					    		"SC_USER_ACCOUNTS",
					    		"SUBJECTS",
					    		"SUCCESS_LOG",
					    		"S_COMPLIANCE_STATUS_LOV",
					    		"S_STANDARDS_LOV",
					    		"S_AC_STANDARDS",
					    		"UI_AC_TYPES_LOV",
					    		"S_AC_STD_APPLICABILITIES",
					    		"S_CMR_META_MODELS",
					    		"S_MANDATORY_TYPES_LOV",
					    		"S_STANDARD_ATTRIBUTES",
					    		"S_CMM_SA_MAP",
					    		"UA_BUSINESS_ROLES",
					    		"UI_ACTIVITIES_LOV",
					    		"UI_TYPES_LOV",
					    		"UI_ELEMENTS",
					    		"UI_ITEMS",
					    		"UI_CONSTRAINTS",
					    		"UI_ELEMENTS_ITEMS",
					    		"UI_FRAMESETS",
					    		"UI_HIERARCHIES",
					    		"UI_LINKS",
					    		"UI_HIER_LINK_RECS",
					    		"UI_IMAGES",
					    		"UI_IMAGE_TYPES_LOV",
					    		"UI_ITEM_GENERATORS",
					    		"UI_ITEM_HIERARCHIES",
					    		"UI_ITEM_IMAGES",
					    		"UI_ITEM_LINK_RECS",
					    		"UI_LINK_FRAMESET_RECS",
					    		"UI_LINK_LINK_RECS",
					    		"UI_LINK_PARAMS",
					    		"UI_METADATA",
					    		"UI_REFERENCE",
					    		"USER_GROUPS",
					    		"VD_PVS",
					    		"VD_PV_RECS",
					    		"VD_RECS",
					    		"WSGSR_SESSIONS",
					    		"WSGSR_USERDATA",
					    		"AC_ATT_TYPES_LOV_EXT",
					    		"AC_ATT_CSCSI_EXT",
					    		"AC_CHANGE_HISTORY_EXT",
					    		"AC_CLASS_SCHEMES_STAGING",
					    		"AC_SOURCES_EXT",
					    		"AC_SOURCES_HST",
					    		"AC_SUBMITTERS_STAGING",
					    		"ADMINISTERED_COMPONENTS_HST",
					    		"ADMIN_COMPONENTS_STAGING",
					    		"ADMIN_COMPONENTS_STAGING_BKUP",
					    		"ASL_ACTL_EXT",
					    		"CDE_CART_ITEMS",
					    		"SOURCE_DATA_LOADS",
					    		"CLASS_SCHEMES_STAGING",
					    		"CLASS_SCHEME_ITEMS_STAGING",
					    		"COMPONENT_LEVELS_EXT",
					    		"COMPONENT_CONCEPTS_EXT",
					    		"CONCEPTS_STAGING",
					    		"CONCEPTUAL_DOMAINS_STAGING",
					    		"CONCEPT_SOURCES_LOV_EXT",
					    		"QUESTION_CONDITIONS_EXT",
					    		"QUEST_CONTENTS_EXT",
					    		"VALID_VALUES_ATT_EXT",
					    		"CONDITION_COMPONENTS_EXT",
					    		"MESSAGE_TYPES_EXT",
					    		"CONDITION_MESSAGE_EXT",
					    		"CREATE$JAVA$LOB$TABLE",
					    		"CRF_TOOL_PARAMETER_EXT",
					    		"DATA_ELEMENTS_HST",
					    		"DATA_ELEMENTS_STAGING",
					    		"DATA_ELEMENT_CONCEPTS_STAGING",
					    		"DEC_STAGING",
					    		"DECR_STAGING",
					    		"DEC_RELATIONSHIPS",
					    		"DEFINITIONS_STAGING",
					    		"DESIGNATIONS_STAGING",
					    		"ECLASSES_STAGING",
					    		"EATTRIBUTES_STAGING",
					    		"EREFERENCES_STAGING",
					    		"ERRORS_EXT",
					    		"ERROR_LOG",
					    		"ESUPERTYPES_STAGING",
					    		"GS_TABLES_LOV",
					    		"GS_COMPOSITE",
					    		"GS_TOKENS",
					    		"GUEST_LOG",
					    		"ICD",
					    		"JAVA$CLASS$MD5$TABLE",
					    		"LOADER_DEFAULTS",
					    		"MATCH_RESULTS_EXT",
					    		"MLOG$_AC_ATT_CSCSI_EXT",
					    		"MLOG$_COMPONENT_CONCEPTS_E",
					    		"MLOG$_COMPONENT_LEVELS_EXT",
					    		"MLOG$_CONCEPTS_EXT",
					    		"MLOG$_CON_DERIVATION_RULES",
					    		"MLOG$_OBJECT_CLASSES_EXT",
					    		"MLOG$_OC_RECS_EXT",
					    		"OBJECT_CLASSES_STAGING",
					    		"OC_RECS_EXT",
					    		"PCOLL_CONTROL",
					    		"PERMISSIBLE_VALUES_HST",
					    		"PERMISSIBLE_VALUES_STAGING",
					    		"PLAN_TABLE",
					    		"PROPERTIES_STAGING",
					    		"PROTOCOLS_EXT",
					    		"PROTOCOL_QC_EXT",
					    		"PS_TXN",
					    		"PV_STAGING_BKUP",
					    		"QC_DISPLAY_LOV_EXT",
					    		"QC_RECS_EXT",
					    		"QC_RECS_HST",
					    		"QC_TYPE_LOV_EXT",
					    		"QUAL_MAP",
					    		"QUAL_STG",
					    		"QUEST_ATTRIBUTES_EXT",
					    		"QUEST_CONTENTS_HST",
					    		"QUEST_VV_EXT",
					    		"REF_DOCS_STAGING",
					    		"REPRESENTATIONS_STAGING",
					    		"REPRESENTATION_LOV_EXT",
					    		"REVIEWER_FEEDBACK_LOV_EXT",
					    		"RUPD$_AC_ATT_CSCSI_EXT",
					    		"RUPD$_COMPONENT_CONCEPTS_E",
					    		"RUPD$_COMPONENT_LEVELS_EXT",
					    		"RUPD$_CONCEPTS_EXT",
					    		"RUPD$_CON_DERIVATION_RULES",
					    		"RUPD$_OBJECT_CLASSES_EXT",
					    		"RUPD$_OC_RECS_EXT",
					    		"SN_ALERT_EXT",
					    		"SN_QUERY_EXT",
					    		"SN_REPORT_EXT",
					    		"SN_RECIPIENT_EXT",
					    		"SN_REP_CONTENTS_EXT",
					    		"SOURCES_EXT",
					    		"STAGE_LOAD_PDF",
					    		"SUBSTITUTIONS_EXT",
					    		"TRIGGERED_ACTIONS_EXT",
					    		"TA_PROTO_CSI_EXT",
					    		"TEXT_STRINGS_EXT",
					    		"TMP_TAB",
					    		"TOOL_OPTIONS_EXT",
					    		"TOOL_PROPERTIES_EXT",
					    		"TS_TYPE_LOV_EXT",
					    		"UI_MENU_TREE_EXT",
					    		"UML_LOADER_DEFAULTS",
					    		"UP_ASSOCIATIONS_METADATA_MVW",
					    		"UP_ATTRIBUTE_METADATA_MVW",
					    		"UP_ATTRIBUTE_METADATA_MVW_TEMP",
					    		"UP_ATTRIBUTE_TYPE_METADATA_MVW",
					    		"UP_CADSR_PROJECT_MVW",
					    		"UP_CLASS_METADATA_MVW",
					    		"UP_CLASS_METADATA_MVW_TEMP",
					    		"UP_GEN_METADATA_MVW",
					    		"UP_PACKAGES_MVW",
					    		"UP_PACKAGES_MVW_TEMP",
					    		"UP_SEMANTIC_METADATA_MVW",
					    		"UP_SUB_PROJECTS_MVW",
					    		"UP_TYPE_ENUMERATION_MVW",
					    		"USERS_LOCKOUT",
					    		"VALUE_DOMAINS_HST",
					    		"VALUE_DOMAINS_STAGING",
					    		"VD_PVS_HST",
					    		"VD_PVS_SOURCES_EXT",
					    		"VD_PVS_SOURCES_HST",
					    		"VD_STAGING_BKUP",
					    		"VM_BACKUP",
					    		"XML_LOADER_ERRORS",
					    		"AUDIT_ACTIONS",
					    		"AW$AWCREATE",
					    		"AW$AWCREATE10G",
					    		"AW$AWMD",
					    		"AW$AWREPORT",
					    		"AW$AWXML",
					    		"AW$EXPRESS",
					    		"DUAL",
					    		"IMPDP_STATS",
					    		"KU$NOEXP_TAB",
					    		"ODCI_SECOBJ$",
					    		"ODCI_WARNINGS$",
					    		"OLAPI_HISTORY",
					    		"OLAPI_IFACE_OBJECT_HISTORY",
					    		"OLAPI_IFACE_OP_HISTORY",
					    		"OLAPI_MEMORY_HEAP_HISTORY",
					    		"OLAPI_MEMORY_OP_HISTORY",
					    		"OLAPI_SESSION_HISTORY",
					    		"OLAPTABLEVELS",
					    		"OLAPTABLEVELTUPLES",
					    		"OLAP_OLEDB_FUNCTIONS_PVT",
					    		"OLAP_OLEDB_KEYWORDS",
					    		"OLAP_OLEDB_MDPROPVALS",
					    		"OLAP_OLEDB_MDPROPS",
					    		"PLAN_TABLE$",
					    		"PSTUBTBL",
					    		"STMT_AUDIT_OPTION_MAP",
					    		"SYSTEM_PRIVILEGE_MAP",
					    		"TABLE_PRIVILEGE_MAP",
					    		"WRI$_ADV_ASA_RECO_DATA",
					    		"DEF$_TEMP$LOB",
					    		"HELP",
					    		"MVIEW$_ADV_INDEX",
					    		"MVIEW$_ADV_OWB",
					    		"MVIEW$_ADV_PARTITION",
					    		"OL$",
					    		"OL$HINTS",
					    		"OL$NODES",
					    		"WM$NEXTVER_TABLE",
					    		"WM$VERSION_HIERARCHY_TABLE",
					    		"WM$VERSION_TABLE",
					    		"WM$WORKSPACES_TABLE",
					    		"XDB$ACL",
					    		"XDB$ALL_MODEL",
					    		"XDB$ANY",
					    		"XDB$ANYATTR",
					    		"XDB$ATTRGROUP_DEF",
					    		"XDB$ATTRGROUP_REF",
					    		"XDB$ATTRIBUTE",
					    		"XDB$CHOICE_MODEL",
					    		"XDB$COMPLEX_TYPE",
					    		"XDB$ELEMENT",
					    		"XDB$GROUP_DEF",
					    		"XDB$GROUP_REF",
					    		"XDB$SCHEMA",
					    		"XDB$SEQUENCE_MODEL",
					    		"XDB$SIMPLE_TYPE"
    };

}