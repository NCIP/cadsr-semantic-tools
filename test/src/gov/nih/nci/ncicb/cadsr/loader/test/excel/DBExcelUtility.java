package gov.nih.nci.ncicb.cadsr.loader.test.excel;

import gov.nih.nci.ncicb.cadsr.loader.test.reflection.ReflectiveUtility;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DBExcelUtility {

	private static int tablename_row = 2;
    private static int columnname_row = 7;
    private static int datastart_row = 10;
    private static int MAXCOLS = 50;
    private static int MAXROWS = 5000;
    private static short dataStart_column = 1;
    private static Log log = LogFactory.getLog("test.junit");
    private ArrayList tblsList = new ArrayList();
    public static boolean CLEAN_ALL = true;


    public static void debugResultSet(ResultSet rs)
        throws Exception
    {
        int maxcol = 20;
        StringBuffer sb = new StringBuffer();
        ResultSetMetaData mdata = rs.getMetaData();

        sb.append("\n");
        maxcol = mdata.getColumnCount();
        System.out.println("Total Columns : " + maxcol);

        for (int i = 1; i <= maxcol; i++)
        {
            sb.append(mdata.getColumnName(i) + "\t");
        }

        int rowcount = 0;

        while (rs.next())
        {
            sb.append("\n");
            ++rowcount;
            for (int i = 1; i <= maxcol; i++)
            {
                try
                {
                    sb.append(rs.getObject(i) + "\t");
                }
                catch (Exception e)
                {
                    System.out.println("Exception " + e);
                }
            }
        }
        System.out.println(sb.toString());
        System.out.println("Total Rows : " + rowcount);
    }

    public static void displayResultSetRow(ResultSet rs)
        throws Exception
    {
        int maxcol = 20;
        StringBuffer sb = new StringBuffer();
        ResultSetMetaData mdata = rs.getMetaData();

        sb.append("\n");
        maxcol = mdata.getColumnCount();

        for (int i = 1; i <= maxcol; i++)
        {
            sb.append(mdata.getColumnName(i) + "\t");
        }

        int rowcount = 0;

        sb.append("\n");
        for (int i = 1; i <= maxcol; i++)
        {
            try
            {
                sb.append(rs.getObject(i) + "\t");
            }
            catch (Exception e)
            {
                System.out.println("Exception " + e);
            }
        }
        System.out.println(sb.toString());
    }


    public String getExcelDataSet(String filename, ArrayList allTablesList)
        throws Exception
    {
        FileInputStream fis = new FileInputStream(filename);

        return getExcelDataSet(fis, allTablesList);
    }

    public String getExcelDataSet(URL filename, ArrayList allTablesList)
        throws Exception
    {
        InputStream is = filename.openStream();

        return getExcelDataSet(is, allTablesList);
    }

     public String getExcelDataSet(InputStream is, String tableName)
        throws Exception
    {
        StringBuffer sb = new StringBuffer();
        HSSFWorkbook wb = ExcelUtility.createWorkbook(is);
        int cnt = wb.getNumberOfSheets();

        sb.append("<dataset>\n");

        for (int i = 0; i < cnt; i++)
        {
            String name = wb.getSheetName(i);

            if (name.indexOf(tableName) != -1)
            {
                sb.append(loadSheetXML(wb, name));
            }
        }
        sb.append("</dataset>\n");
        return sb.toString();
    }

    public String getExcelDataSet(InputStream is, ArrayList allTablesList)
        throws Exception
    {
        StringBuffer sb = new StringBuffer();
        HSSFWorkbook wb = ExcelUtility.createWorkbook(is);
        int cnt = wb.getNumberOfSheets();

        sb.append("<dataset>\n");
        tblsList = new ArrayList();

        for (int i = 0; i < cnt; i++)
        {
            String name = wb.getSheetName(i);

            if (name.startsWith("tbl_") || name.startsWith("TBL_"))
            {
                sb.append(loadSheetXML(wb, name));
            }
        }
        sb.append(appendOtherTables(allTablesList));
        sb.append("</dataset>\n");
        //System.out.println(sb.toString());
        return sb.toString();
    }

    /////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
    public ExcelResultSet getExcelResultSet(String filename, String resultname)
        throws Exception
    {
        FileInputStream fis = new FileInputStream(filename);

        return getExcelResultSet(fis, resultname);
    }

    public ExcelResultSet getExcelResultSet(URL filename, String resultname)
        throws Exception
    {
        InputStream is = filename.openStream();

        return getExcelResultSet(is, resultname);
    }

    public ExcelResultSet getExcelResultSet(InputStream is, String resultname)
        throws Exception
    {
        ExcelResultSet excelResultSet = null;
        HSSFWorkbook wb = ExcelUtility.createWorkbook(is);
        int cnt = wb.getNumberOfSheets();
        int row = 2;
        int tblnamerow = 2;
        short col = 1;
        short tblnamecol = 1;

        for (int i = 0; i < cnt; i++)
        {
            String name = wb.getSheetName(i);

            if (name.startsWith("exp_") || name.startsWith("EXP_"))
            {
                HSSFSheet hssfsheet = ExcelUtility.createHSSFSheet(wb, name);

                String rsname = ExcelUtility.getString(hssfsheet, tblnamerow, tblnamecol);

                if (rsname.equalsIgnoreCase(resultname))
                {
                    excelResultSet = createExcelResultSet(hssfsheet);
                    break;
                }
            }
        }
        return excelResultSet;
    }


    public String[] getExcelDataAsString(HSSFSheet hssfsheet, int row, short col, int colcount)
        throws Exception
    {
        String ret[] = new String[colcount];
        String first = ExcelUtility.getString(hssfsheet, row, col);

        if (first == null)
        {
            return null;
        }

        for (int i = 0; i < colcount; i++)
        {
            ret[i] = ExcelUtility.getString(hssfsheet, row, (short) (col + i));
        }
        return ret;
    }

    public Object[] getExcelDataAsObjects(HSSFSheet hssfsheet, int row, short col, int colcount, String coltype[])
        throws Exception
    {
        Object ret[] = new Object[colcount];
        Object first = ExcelUtility.getObject(hssfsheet, row, col);

        if (first == null)
        {
            return null;
        }

        for (int i = 0; i < colcount; i++)
        {
            ret[i] = null;

            Object cdata = ExcelUtility.getObject(hssfsheet, row, (short) (col + i));

            if (cdata != null)
            {
                if (coltype[i].equalsIgnoreCase(DataType.TYPE_STRING))
                {
                    ret[i] = ExcelUtility.getString(hssfsheet, row, (short) (col + i));
                }
                else if (coltype[i].equalsIgnoreCase(DataType.TYPE_INTEGER))
                {
                    ret[i] = ExcelUtility.getInteger(hssfsheet, row, (short) (col + i));
                }
                else if (coltype[i].equalsIgnoreCase(DataType.TYPE_DOUBLE))
                {
                    ret[i] = ExcelUtility.getDouble(hssfsheet, row, (short) (col + i));
                }
                else if (coltype[i].equalsIgnoreCase(DataType.TYPE_BOOLEAN))
                {
                    ret[i] = ExcelUtility.getBoolean(hssfsheet, row, (short) (col + i));
                }
                else if (coltype[i].equalsIgnoreCase(DataType.TYPE_DATE))
                {
                    ret[i] = ExcelUtility.getDate(hssfsheet, row, (short) (col + i));
                }
                else if (coltype[i].equalsIgnoreCase(DataType.TYPE_LONG))
                {
                    ret[i] = ExcelUtility.getLong(hssfsheet, row, (short) (col + i));
                }
            }
        }
        return ret;
    }


    ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    public ArrayList createDataObject(String filename, String resultname)
        throws Exception
    {
        FileInputStream fis = new FileInputStream(filename);

        return createDataObject(fis, resultname);
    }

    public ArrayList createDataObject(URL filename, String resultname)
        throws Exception
    {
        InputStream is = filename.openStream();

        return createDataObject(is, resultname);
    }

    public ArrayList createDataObject(InputStream is, String resultname)
        throws Exception
    {
        ArrayList datalist = new ArrayList();
        ExcelResultSet excelResultSet = null;
        HSSFWorkbook wb = ExcelUtility.createWorkbook(is);
        int cnt = wb.getNumberOfSheets();
        int row = 2;
        int tblnamerow = 2;
        short col = 1;
        short tblnamecol = 1;

        for (int i = 0; i < cnt; i++)
        {
            String name = wb.getSheetName(i);

            if (name.startsWith("event_") || name.startsWith("EVENT_"))
            {
                HSSFSheet hssfsheet = ExcelUtility.createHSSFSheet(wb, name);
                String rsname = ExcelUtility.getString(hssfsheet, tblnamerow, tblnamecol);

                //System.out.println(rsname + "," + resultname);
                if (rsname.equalsIgnoreCase(resultname))
                {
                    excelResultSet = createExcelResultSet(hssfsheet);
                    if (excelResultSet != null)
                    {
                        ArrayList rows = excelResultSet.getRows();
                        String[] colnames = excelResultSet.getColumns();
                        String[] coltypes = excelResultSet.getType();
                        String[] convertedtypes = convertDataTypes(coltypes);
                        String classname = excelResultSet.getClassname();
                        int rowcnt = rows.size();

                        for (int x = 0; x < rowcnt; x++)
                        {
                            ExcelRow ro = (ExcelRow) (rows.get(x));
                            Object object = excelRowToObject(ro, classname, colnames, convertedtypes);

                            if (object != null)
                            {
                                datalist.add(object);
                            }
                        }
                    }
                }
            }
        }
        return datalist;
    }

    public String[] updateColTypes(String coltype[])
    {
        String ret[] = new String[coltype.length];

        for (int i = 0; i < coltype.length; i++)
        {
            ret[i] = coltype[i];
            if (coltype[i].equalsIgnoreCase(DataType.TYPE_CHAR) ||
                coltype[i].equalsIgnoreCase(DataType.TYPE_VARCHAR) ||
                coltype[i].equalsIgnoreCase(DataType.TYPE_VARCHAR2))
            {
                ret[i] = "String";
            }
            if (coltype[i].equalsIgnoreCase(DataType.TYPE_NUMERIC))
            {
                ret[i] = "Double";
            }
        }
        return ret;
    }

    public String loadSheetXML(HSSFWorkbook wb, String sheetname)
        throws Exception
    {
        StringBuffer sb = new StringBuffer();
        HSSFSheet hssfsheet = ExcelUtility.createHSSFSheet(wb, sheetname);

        int row = tablename_row;
        short col = 1;
        String tablename = ExcelUtility.getString(hssfsheet, tablename_row, col);

        //row++

        sb.append("<table name=\"" + tablename + "\">\n");
        tblsList.add(tablename);
        row = columnname_row;

        //contains column names

        int colcount = getColumnCount(hssfsheet, columnname_row);

        //Header
        String icoltypes[] = getExcelDataAsString(hssfsheet, columnname_row + 1, col, colcount);
        String coltypes[] = updateColTypes(icoltypes);

        sb.append(getStringRowXMLData(hssfsheet, row, colcount, "column") + "\n");

        row = datastart_row;
        //data starts at row=10
        col = 1;
        for (int i = 0; i < MAXROWS; i++)
        {
            //check the first column. If it is null, consider it as end of loop
            Object cdata = ExcelUtility.getObject(hssfsheet, row + i, (short) (dataStart_column));

            if (cdata == null)
            {
                break;
            }
            sb.append("<row>\n");
            sb.append(getRowXMLData(hssfsheet, sheetname, row + i, colcount, "value", coltypes) + "\n");
            sb.append("</row>\n");
        }
        sb.append("</table> \n");
        return sb.toString();
    }

    public boolean compareResultSet(ResultSet rs,
        ExcelResultSet ers)
        throws Exception
    {
        ArrayList data = null;
        String cols[] = ers.getColumns();
        String types[] = ers.getType();
        int rscount = 0;
        int excount = 0;

        if (ers.getRows() != null)
        {
            excount = ers.getRows().size();
        }

        while (rs.next())
        {
            data = getRowData(rs, cols, types);
            if (!compareToAllExcelRows(data, ers, cols, types))
            {
                System.out.println("Comparision failed for the following Resultset row :: ");
                displayResultSetRow(rs);
                return false;
            }
            ++rscount;
        }

        if (excount != rscount)
        {
            log.error("Row count Mismatch : Resultset has " + rscount + " rows. Excel Resultset has " + excount + " rows");
            return false;
        }
        return true;
    }

    public boolean compareExcelRow(ExcelRow erow, ArrayList cmp, String coltype[])
        throws Exception
    {
        int numberOfColumns = coltype.length;
        short col = 0;
        boolean nodata = true;
        Object data[] = erow.getData();

        for (int i = 0; i < numberOfColumns; i++)
        {
            Object odata = (Object) cmp.get(i);

            if (data[i] == null && odata == null)
            {
                continue;
            }
            if ((data[i] == null && odata != null) ||
                (data[i] != null && odata == null))
            {
                return false;
            }

            if (coltype[i].equalsIgnoreCase(DataType.TYPE_STRING))
            {
                String cdata = (String) cmp.get(i);

                if (!cdata.equalsIgnoreCase((String) data[i]))
                {
                    return false;
                }
            }
            else if (coltype[i].equalsIgnoreCase(DataType.TYPE_INTEGER))
            {
                Integer idata = (Integer) cmp.get(i);
                Integer cidata = (Integer) data[i];

                if (idata.compareTo(cidata) != 0)
                {
                    return false;
                }
            }
            else if (coltype[i].equalsIgnoreCase(DataType.TYPE_DOUBLE))
            {
                Double ddata = (Double) cmp.get(i);
                Double cddata = (Double) data[i];

                if (ddata.compareTo(cddata) != 0)
                {
                    return false;
                }
            }
            else if (coltype[i].equalsIgnoreCase(DataType.TYPE_BOOLEAN))
            {
                Boolean bdata = (Boolean) cmp.get(i);
                Boolean cbdata = (Boolean) data[i];

                if (bdata.booleanValue() != cbdata.booleanValue())
                {
                    return false;
                }
            }
            else if (coltype[i].equalsIgnoreCase(DataType.TYPE_DATE))
            {
                Date dtval = (Date) cmp.get(i);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //Date cdtval = sdf.parse(data[i]);
                Date cdtval = (Date) data[i];

                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();

                cal1.setTime(dtval);
                cal2.setTime(cdtval);
                if (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR) ||
                    cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH) ||
                    cal1.get(Calendar.DATE) != cal2.get(Calendar.DATE))
                {
                    return false;
                }
            }
        }
        return true;
    }


    //Return Data as a String
    private String getStringRowXMLData(HSSFSheet hssfsheet,
        int row,
        int colcount,
        String prefix)
    {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < colcount; i++)
        {
            Object cdata = ExcelUtility.getObject(hssfsheet, row, (short) (dataStart_column + i));

            if (cdata != null)
            {
                sb.append("<" + prefix + ">" + cdata + "</" + prefix + ">");
            }
        }
        return sb.toString();
    }

    //Return Data as a String
    private String getRowXMLData(HSSFSheet hssfsheet, String sheetName,
        int row, int colcount, String prefix,
        String coltype[])
    {
        StringBuffer sb = new StringBuffer();
        Object cdata = null;
        int column = 0;

        try
        {

            for (int i = 0; i < colcount; i++)
            {
                column = (short) (dataStart_column + i);
                cdata = ExcelUtility.getObject(hssfsheet, row, (short) column);

				//sb.append("(AV=" + cdata + "," + cdata.toString().length() + ")");
                if (cdata != null && cdata.toString().length()!=0)
                {
                    if (coltype[i].equalsIgnoreCase(DataType.TYPE_STRING))
                    {
                        sb.append("<" + prefix + ">" + (String) cdata + "</" + prefix + ">");
                    }
                    else if (coltype[i].equalsIgnoreCase(DataType.TYPE_INTEGER))
                    {
                        Integer idata = ExcelUtility.getInteger(hssfsheet, row, (short) (dataStart_column + i));

                        sb.append("<" + prefix + ">" + idata + "</" + prefix + ">");
                    }
                    else if (coltype[i].equalsIgnoreCase(DataType.TYPE_DOUBLE))
                    {
                        Double ddata = ExcelUtility.getDouble(hssfsheet, row, (short) (dataStart_column + i));

                        sb.append("<" + prefix + ">" + ddata + "</" + prefix + ">");
                    }
                    else if (coltype[i].equalsIgnoreCase(DataType.TYPE_BOOLEAN))
                    {
                        Boolean bdata = ExcelUtility.getBoolean(hssfsheet, row, (short) (dataStart_column + i));

                        sb.append("<" + prefix + ">" + bdata + "</" + prefix + ">");
                    }
                    else if (coltype[i].equalsIgnoreCase(DataType.TYPE_DATE))
                    {
                        Date dtval = ExcelUtility.getDate(hssfsheet, row, (short) (dataStart_column + i));
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        StringBuffer dtString = new StringBuffer();
                        String dt = sdf.format(dtval);

                        //System.out.println("dtval = " + dtval);
                        //System.out.println("dt = " + dtString.toString());
                        sb.append("<" + prefix + ">" + dt + "</" + prefix + ">");
                    }
                }
                else
                {
                    sb.append("<none/>");
                }
            }
            return sb.toString();
        }
        catch (ClassCastException ce)
        {
            ce.printStackTrace();
            throw new ClassCastException("There is a type problem in the excel worksheet on Sheet='" +
                sheetName + "', and Cell(" + (row + 1) + "," + (column + 1) + ")" + "\n" + ce.getMessage());
        }
    }

    //returns a count of Non Null columns
    //Use this function for the Column names row only !!
    private int getColumnCount(HSSFSheet hssfsheet, int row)
    {
        short dataStart_column = 1;

        for (int i = 0; i < MAXCOLS; i++)
        {
            String cdata = ExcelUtility.getString(hssfsheet, row, (short) (dataStart_column + i));

            if (cdata == null)
            {
                return i;
            }
        }
        return MAXCOLS;
    }

    //read resultset and construct an array of resultset data
    private ArrayList getRowData(ResultSet rs, String colnames[], String coltype[])
        throws Exception
    {
        ArrayList data = new ArrayList();
        int numberOfColumns = coltype.length;
        short col = 0;
        boolean nodata = true;

        for (int i = 0; i < numberOfColumns; i++)
        {
            String dat = "";

            if (coltype[i].equalsIgnoreCase(DataType.TYPE_STRING))
            {
                String cdata = rs.getString(colnames[i]);

                data.add(cdata);
            }
            else if (coltype[i].equalsIgnoreCase(DataType.TYPE_INTEGER))
            {
                Integer idata = new Integer(rs.getInt(colnames[i]));

                data.add(idata);
            }
            else if (coltype[i].equalsIgnoreCase(DataType.TYPE_DOUBLE))
            {
                Double ddata = new Double(rs.getDouble(colnames[i]));

                data.add(ddata);
            }
            else if (coltype[i].equalsIgnoreCase(DataType.TYPE_BOOLEAN))
            {
                Boolean bdata = new Boolean(rs.getBoolean(colnames[i]));

                data.add(bdata);
            }
            else if (coltype[i].equalsIgnoreCase(DataType.TYPE_DATE))
            {
                Date dtval = rs.getDate(colnames[i]);

                data.add(dtval);
            }
        }
        return data;
    }

    private ExcelResultSet createExcelResultSet(HSSFSheet hssfsheet)
        throws Exception
    {
        int row = 2;
        int tblnamerow = 2;
        short col = 1;
        short tblnamecol = 1;

        ExcelResultSet excelResultSet = null;

        row = columnname_row;

        //contains column names
        int colcount = getColumnCount(hssfsheet, columnname_row);
        ExcelRow header = new ExcelRow(getExcelDataAsString(hssfsheet, columnname_row, (short) 1, colcount));
        String coltypes[] = getExcelDataAsString(hssfsheet, columnname_row + 1, (short) 1, colcount);

        String classname = ExcelUtility.getString(hssfsheet, tblnamerow + 1, tblnamecol);
        String updatedColtypes[] = updateColTypes(coltypes);
        ExcelRow types = new ExcelRow(updatedColtypes);

        excelResultSet = new ExcelResultSet(header, types);
        excelResultSet.setClassname(classname);
        row = datastart_row;
        for (int r = 0; r < MAXROWS; r++)
        {
            Object rowdata[] = getExcelDataAsObjects(hssfsheet, row + r, (short) 1, colcount, updatedColtypes);

            if (isEmptyRowData(rowdata))
            {
                break;
            }

            ExcelRow excelRow = new ExcelRow(rowdata);

            excelResultSet.addRow(excelRow);
        }
        return excelResultSet;
    }

    /**
     * Returns true is the rowdata object represents an empty excel row of data.
     * It inspects the array that represents row data checking for valid data in every column.
     * If every column contains spaces or null, it determines that the row is empty.
     * 
     * 
     * @param rowdata
     * @return
     */
    private boolean isEmptyRowData(Object[] rowdata)
    {
        boolean isEnd = true;
        
        if(rowdata !=null)
        {
            for(int i = 0;i<rowdata.length;i++)
            {
                Object colData = rowdata[i];
                
                //If valid data in column, then row contains data
                if(colData!=null && !colData.toString().trim().equals(""))
                {
                    isEnd = false;
                    break;
                }
            }
        }
        
        return isEnd;
    }
    
    private boolean compareToAllExcelRows(ArrayList data,
        ExcelResultSet ers,
        String colnames[],
        String coltype[])
    {
        boolean result = false;
        int numberOfColumns = coltype.length;
        ArrayList rows = ers.getRows();

        for (int i = 0; i < rows.size(); i++)
        {
            ExcelRow row = (ExcelRow) rows.get(i);

            try
            {
                if (compareExcelRow(row, data, coltype))
                {
                    return true;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }


    private Object excelRowToObject(ExcelRow ro, String classname, String[] colnames, String[] coltypes)
        throws Exception
    {
        //System.out.println(classname);
        Object newobject = Class.forName(classname).newInstance();

        for (int i = 0; i < colnames.length; i++)
        {
            if (colnames[i] == null)
            {
                throw new NullPointerException("One of the class column names in excel is null");
            }

            String colname = colnames[i];

            colname = colname.substring(0, 1).toUpperCase() + colname.substring(1, colname.length());

            String methodname = "set" + colname;
            String methodtype = coltypes[i];

            //System.out.println("classname = " + newobject.getClass().getName());
            ReflectiveUtility.setObject(newobject, ro.getData(i), methodname, methodtype);
        }
        return newobject;
    }

    private String[] convertDataTypes(String coltypes[])
    {
        String retColtypes[];

        if (coltypes == null)
        {
            return null;
        }
        retColtypes = new String[coltypes.length];
        for (int i = 0; i < coltypes.length; i++)
        {
            retColtypes[i] = coltypes[i];
            if (coltypes[i] != null)
            {
                if (coltypes[i].equalsIgnoreCase(DataType.TYPE_DATE))
                {
                    retColtypes[i] = "java.util.Date";
                }
                else
                {
                    retColtypes[i] = "java.lang." + coltypes[i];
                }
            }
        }
        return retColtypes;
    }

    private String appendOtherTables(ArrayList allTablesList)
    {
        if (allTablesList == null)
        {
            return "";
        }

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < allTablesList.size(); i++)
        {
            String tbl = (String) allTablesList.get(i);

            if (!checkIfTableAdded(tbl))
            {
                sb.append("<table name=\"" + tbl + "\" />" + "\n");
            }
        }
        return sb.toString();
    }

    private boolean checkIfTableAdded(String table)
    {
        if (table.startsWith("STGG_"))
        {
            return true;
        }

        for (int i = 0; i < tblsList.size(); i++)
        {
            String arg = (String) tblsList.get(i);

            if (arg.equalsIgnoreCase(table))
            {
                //System.out.println("Matched : " + table);
                return true;
            }
        }
        return false;
    }
}
