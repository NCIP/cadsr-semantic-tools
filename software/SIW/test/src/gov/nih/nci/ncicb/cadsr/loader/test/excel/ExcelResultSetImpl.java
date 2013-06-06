package gov.nih.nci.ncicb.cadsr.loader.test.excel;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.RowId;
import java.sql.SQLXML;
//import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
//import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
//import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class ExcelResultSetImpl implements ResultSet
{
    String          colnames[];
    String          coltypes[];
    private ExcelResultSet excelResultSet;
    private ResultSetMetaData rsmetadata = null;

    private ArrayList excelRowArray;
    private int     totalRows;
    private int     currentRow;
    private int     columnCount;
    private boolean lastColumnReadIsNull = false;

    public ExcelResultSetImpl(InputStream is, String resultname)
        throws Exception
    {
        DBExcelUtility util = new DBExcelUtility();

        this.excelResultSet = util.getExcelResultSet(is, resultname);
        if (excelResultSet == null)
        {
            throw new Exception("Could not create the Excel Result set Object");
        }

        excelRowArray = excelResultSet.getRows();
        totalRows = excelResultSet.getRows().size();
        //System.out.println("count = " + totalRows);
        currentRow = -1;
        colnames = excelResultSet.getColumns();
        coltypes = excelResultSet.getType();
        rsmetadata = new ExcelResultSetMetaDataImpl(colnames, coltypes);

        columnCount = colnames.length;
        /*
         * for(int i=0; i <excelRowArray.size(); i++)
         * {
         * ExcelRow ro = (ExcelRow)(excelRowArray.get(i));
         * System.out.println(ro);
         * }
         */
    }

    public Array getArray(int i)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public Array getArray(String columnName)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public InputStream getAsciiStream(int columnIndex)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public InputStream getAsciiStream(String columnName)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public BigDecimal getBigDecimal(int columnIndex)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public BigDecimal getBigDecimal(int columnIndex, int scale)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public BigDecimal getBigDecimal(String columnName)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public BigDecimal getBigDecimal(String columnName, int scale)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public InputStream getBinaryStream(int columnIndex)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public InputStream getBinaryStream(String columnName)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public Blob getBlob(int i)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public Blob getBlob(String columnName)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public int getType()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public InputStream getUnicodeStream(int columnIndex)
        throws SQLException
    {
        return null;
    }

    public InputStream getUnicodeStream(String columnName)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public URL getURL(int columnIndex)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public URL getURL(String columnName)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public SQLWarning getWarnings()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    //////////////////////////////////////////////////////////////////////////////////////
    ///////////////////// ONLY THESE METHODS ARE IMPLEMENTED /////////////////////////////
    ///////////////////// ///////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////


    public ResultSetMetaData getMetaData()
        throws SQLException
    {
        return rsmetadata;
    }

    public int getRow()
    {
        return currentRow;
    }

    public boolean getBoolean(String columnName)
        throws SQLException
    {
        return getBoolean(findColumn(columnName));
    }

    public byte getByte(String columnName)
        throws SQLException
    {
        return getByte(findColumn(columnName));
    }

    public byte[] getBytes(String columnName)
        throws SQLException
    {
        return getBytes(findColumn(columnName));
    }

    public Reader getCharacterStream(String columnName)
        throws SQLException
    {
        return getCharacterStream(findColumn(columnName));
    }

    public Clob getClob(String columnName)
        throws SQLException
    {
        return getClob(findColumn(columnName));
    }

    public int getConcurrency()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public String getCursorName()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public java.sql.Date getDate(String columnName)
        throws SQLException
    {
        return getDate(findColumn(columnName));
    }

    public java.sql.Date getDate(String columnName, Calendar cal)
        throws SQLException
    {
        return getDate(findColumn(columnName), cal);
    }

    public double getDouble(String columnName)
        throws SQLException
    {
        return getDouble(findColumn(columnName));
    }

    public int getFetchDirection()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public int getFetchSize()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public float getFloat(String columnName)
        throws SQLException
    {
        return getFloat(findColumn(columnName));
    }

    public int getInt(String columnName)
        throws SQLException
    {
        return getInt(findColumn(columnName));
    }

    public long getLong(String columnName)
        throws SQLException
    {
        return getLong(findColumn(columnName));
    }

    public Object getObject(String columnName)
        throws SQLException
    {
        return getObject(findColumn(columnName));
    }

//    public Object getObject(String columnName, Map map)
//        throws SQLException
//    {
//        return getObject(findColumn(columnName), map);
//    }

    public Ref getRef(String columnName)
        throws SQLException
    {
        return getRef(findColumn(columnName));
    }

    public short getShort(String columnName)
        throws SQLException
    {
        return getShort(findColumn(columnName));
    }

    public Statement getStatement()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public String getString(String columnName)
        throws SQLException
    {
        return getString(findColumn(columnName));
    }

    public Time getTime(String columnName)
        throws SQLException
    {
        return getTime(findColumn(columnName));
    }

    public Time getTime(String columnName, Calendar cal)
        throws SQLException
    {
        return getTime(findColumn(columnName), cal);
    }

    public Timestamp getTimestamp(String columnName)
        throws SQLException
    {
        return getTimestamp(findColumn(columnName));
    }

    public Timestamp getTimestamp(String columnName, Calendar cal)
        throws SQLException
    {
        return getTimestamp(findColumn(columnName), cal);
    }

    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////

    public boolean isAfterLast()
    {
        return false;
    }

    public boolean isBeforeFirst()
    {
        return false;
    }

    public boolean isFirst()
    {
        if (totalRows == 0)
        {
            return false;
        }
        if (currentRow == 0)
        {
            return false;
        }
        return true;
    }

    public boolean isLast()
    {
        if (totalRows == 0)
        {
            return false;
        }
        if (currentRow == totalRows - 1)
        {
            return false;
        }
        return true;
    }

    public boolean getBoolean(int columnIndex)
        throws SQLException
    {
        checkColumnIndex(columnIndex);

        Object val = getCellValue(currentRow, columnIndex - 1);

        setWasNull(val);
        if (val == null)
        {
            return false;
        }
        return ((Boolean) val).booleanValue();
    }

    public java.sql.Date getDate(int columnIndex)
        throws SQLException
    {
        checkColumnIndex(columnIndex);

        Object val = getCellValue(currentRow, columnIndex - 1);

        setWasNull(val);
        if (val == null)
        {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try
        {
//                    java.util.Date udate = sdf.parse((String)val);
            java.util.Date udate = (Date) val;
            java.sql.Date cdtval = new java.sql.Date(udate.getTime());

            return cdtval;
        }
        catch (Exception e)
        {
            throw new SQLException("Parse Exception : " + e);
        }
    }

    public double getDouble(int columnIndex)
        throws SQLException
    {
        checkColumnIndex(columnIndex);

        Object val = getCellValue(currentRow, columnIndex - 1);

        setWasNull(val);
        if (val == null)
        {
            return 0;
        }
        return ((Double) val).doubleValue();
    }

    public float getFloat(int columnIndex)
        throws SQLException
    {
        checkColumnIndex(columnIndex);

        Object val = getCellValue(currentRow, columnIndex - 1);

        setWasNull(val);
        if (val == null)
        {
            return 0;
        }
        return ((Double) val).floatValue();
    }

    public int getInt(int columnIndex)
        throws SQLException
    {
        checkColumnIndex(columnIndex);

        Object val = getCellValue(currentRow, columnIndex - 1);

        setWasNull(val);
        if (val == null)
        {
            return 0;
        }
        return ((Integer) val).intValue();
    }

    public short getShort(int columnIndex)
        throws SQLException
    {
        checkColumnIndex(columnIndex);

        Object val = getCellValue(currentRow, columnIndex - 1);

        setWasNull(val);
        if (val == null)
        {
            return 0;
        }
        return ((Integer) val).shortValue();
    }

    public long getLong(int columnIndex)
        throws SQLException
    {
        checkColumnIndex(columnIndex);

        Object val = getCellValue(currentRow, columnIndex - 1);

        setWasNull(val);
        if (val == null)
        {
            return 0;
        }
        return ((Integer) val).longValue();
    }

    public String getString(int columnIndex)
        throws SQLException
    {
        checkColumnIndex(columnIndex);

        Object val = getCellValue(currentRow, columnIndex - 1);

        setWasNull(val);
        if (val == null)
        {
            return null;
        }
        return (String) val;
    }

    public Object getObject(int columnIndex)
        throws SQLException
    {
        checkColumnIndex(columnIndex);

        Object val = ((ExcelRow) excelRowArray.get(currentRow)).getData(columnIndex - 1);

        setWasNull(val);
        return val;
    }

    public Timestamp getTimestamp(int columnIndex)
        throws SQLException
    {
        checkColumnIndex(columnIndex);

        Object val = getCellValue(currentRow, columnIndex - 1);

        setWasNull(val);
        if (val == null)
        {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

        try
        {
            java.util.Date udate = sdf.parse((String) val);
            //java.util.Date udate = (Date)val;
            java.sql.Timestamp cdtval = new java.sql.Timestamp(udate.getTime());

            return cdtval;
        }
        catch (Exception e)
        {
            throw new SQLException("Parse Exception : " + e);
        }
    }

    public Time getTime(int columnIndex)
        throws SQLException
    {
        checkColumnIndex(columnIndex);

        Object val = getCellValue(currentRow, columnIndex - 1);

        setWasNull(val);
        if (val == null)
        {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

        try
        {
            java.util.Date udate = sdf.parse((String) val);
            //java.util.Date udate = (Date)val;
            java.sql.Time cdtval = new java.sql.Time(udate.getTime());

            return cdtval;
        }
        catch (Exception e)
        {
            throw new SQLException("Parse Exception : " + e);
        }
    }


    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    public byte getByte(int columnIndex)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public byte[] getBytes(int columnIndex)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public Reader getCharacterStream(int columnIndex)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public java.sql.Date getDate(int columnIndex, Calendar cal)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public Timestamp getTimestamp(int columnIndex, Calendar cal)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }


    public Time getTime(int columnIndex, Calendar cal)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public Clob getClob(int i)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public Ref getRef(int i)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public Object getCellValue(int ro, int col)
    {
        ExcelRow row = (ExcelRow) (excelRowArray.get(ro));

        return row.getData(col);
    }

    public void setFetchDirection(int direction)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void setFetchSize(int rows)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public boolean absolute(int row)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void afterLast()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void beforeFirst()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void cancelRowUpdates()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void clearWarnings()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void close()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void deleteRow()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void refreshRow()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public boolean relative(int rows)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public boolean rowDeleted()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public boolean rowInserted()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public boolean rowUpdated()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateArray(int columnIndex, Array x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateArray(String columnName, Array x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateAsciiStream(int columnIndex, InputStream x, int length)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateAsciiStream(String columnName, InputStream x, int length)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateBigDecimal(int columnIndex, BigDecimal x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateBigDecimal(String columnName, BigDecimal x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateBinaryStream(int columnIndex, InputStream x, int length)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateBinaryStream(String columnName, InputStream x, int length)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateBlob(int columnIndex, Blob x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateBlob(String columnName, Blob x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateBoolean(int columnIndex, boolean x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateBoolean(String columnName, boolean x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateByte(int columnIndex, byte x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateByte(String columnName, byte x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateBytes(int columnIndex, byte[] x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateBytes(String columnName, byte[] x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateCharacterStream(int columnIndex, Reader x, int length)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateCharacterStream(String columnName, Reader reader, int length)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateClob(int columnIndex, Clob x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateClob(String columnName, Clob x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateDate(int columnIndex, java.sql.Date x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateDate(String columnName, java.sql.Date x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateDouble(int columnIndex, double x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateDouble(String columnName, double x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateFloat(int columnIndex, float x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateFloat(String columnName, float x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateInt(int columnIndex, int x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateInt(String columnName, int x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateLong(int columnIndex, long x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateLong(String columnName, long x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateNull(int columnIndex)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateNull(String columnName)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateObject(int columnIndex, Object x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateObject(int columnIndex, Object x, int scale)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateObject(String columnName, Object x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateObject(String columnName, Object x, int scale)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateRef(int columnIndex, Ref x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateRef(String columnName, Ref x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateRow()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateShort(int columnIndex, short x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateShort(String columnName, short x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateString(int columnIndex, String x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateString(String columnName, String x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateTime(int columnIndex, Time x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateTime(String columnName, Time x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateTimestamp(int columnIndex, Timestamp x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void updateTimestamp(String columnName, Timestamp x)
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public void insertRow()
        throws SQLException
    {
        throw new SQLException("Function Not Implemented");
    }

    public boolean wasNull()
        throws SQLException
    {
        return lastColumnReadIsNull;
    }

    public int findColumn(String columnName) throws SQLException
    {
        int index = -1;
        
        for (int i = 0; i < colnames.length; i++)
        {
            if (colnames[i].equalsIgnoreCase(columnName))
            {
                index= i + 1;
                break;
            }
        }
        
        if(index<0)
            throw new SQLException("Column ["+columnName+"] not found!");
        
        return index;
    }

    public boolean first()
    {
        if (totalRows == 0)
        {
            return false;
        }
        currentRow = 0;
        return true;
    }

    public boolean last()
    {
        if (totalRows == 0)
        {
            return false;
        }
        currentRow = totalRows - 1;
        return true;
    }

    public boolean next()
    {
        if (totalRows == 0 || currentRow == (totalRows - 1))
        {
            return false;
        }
        ++currentRow;
        return true;
    }

    public boolean previous()
    {
        if (totalRows == 0 && currentRow == 0)
        {
            return false;
        }
        --currentRow;
        return true;
    }

    public void moveToCurrentRow() { }

    public void moveToInsertRow() { }

    private void setWasNull(Object data)
    {
        if (data == null)
        {
            lastColumnReadIsNull = true;
        }
        else
        {
            lastColumnReadIsNull = false;
        }
    }

    private void checkColumnIndex(int columnIndex)
        throws SQLException
    {
        if (columnIndex < 0 || columnIndex > columnCount)
        {
            throw new SQLException("Invalid Column at index = " + columnIndex);
        }
    }
    
    public void updateCharacterStream(int i, Reader r) {
    	
    }
    
    
	public Object getObject(int arg0, Map<String, Class<?>> arg1) throws SQLException {
		return null;
	}
    
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Reader getNCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getNCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(String arg0, Map<String, Class<?>> arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRowId(int arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRowId(String arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

//	public NClob getNClob(int arg0) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public NClob getNClob(String arg0) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public String getNString(int arg0) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public String getNString(String arg0) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public RowId getRowId(int arg0) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public RowId getRowId(String arg0) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public SQLXML getSQLXML(int arg0) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public SQLXML getSQLXML(String arg0) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public boolean isClosed() throws SQLException {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void updateAsciiStream(int arg0, InputStream arg1, long arg2)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateAsciiStream(int arg0, InputStream arg1)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateAsciiStream(String arg0, InputStream arg1, long arg2)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateAsciiStream(String arg0, InputStream arg1)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateBinaryStream(int arg0, InputStream arg1, long arg2)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateBinaryStream(int arg0, InputStream arg1)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateBinaryStream(String arg0, InputStream arg1, long arg2)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void updateBinaryStream(String arg0, InputStream arg1)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateBlob(int arg0, InputStream arg1, long arg2)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateBlob(int arg0, InputStream arg1) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateBlob(String arg0, InputStream arg1, long arg2)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateBlob(String arg0, InputStream arg1) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateCharacterStream(int arg0, Reader arg1, long arg2)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateCharacterStream(String arg0, Reader arg1, long arg2)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateCharacterStream(String arg0, Reader arg1)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateClob(int arg0, Reader arg1, long arg2)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void updateClob(int arg0, Reader arg1) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateClob(String arg0, Reader arg1, long arg2)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateClob(String arg0, Reader arg1) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateNCharacterStream(int arg0, Reader arg1, long arg2)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateNCharacterStream(int arg0, Reader arg1)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateNCharacterStream(String arg0, Reader arg1, long arg2)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateNCharacterStream(String arg0, Reader arg1)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void updateNClob(int arg0, NClob arg1) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateNClob(int arg0, Reader arg1, long arg2)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateNClob(int arg0, Reader arg1) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateNClob(String arg0, NClob arg1) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateNClob(String arg0, Reader arg1, long arg2)
//			throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateNClob(String arg0, Reader arg1) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateNString(int arg0, String arg1) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateNString(String arg0, String arg1) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateRowId(int arg0, RowId arg1) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateRowId(String arg0, RowId arg1) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
//		// TODO Auto-generated method stub
//		
//	}
}
