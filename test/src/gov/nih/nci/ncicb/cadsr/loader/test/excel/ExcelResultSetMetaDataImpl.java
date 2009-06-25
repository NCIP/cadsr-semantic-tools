package gov.nih.nci.ncicb.cadsr.loader.test.excel;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ExcelResultSetMetaDataImpl implements ResultSetMetaData
{
	private String columnNames[];
	private String coltypes[];
	
	public ExcelResultSetMetaDataImpl(String[] columnNames, String[] coltypes)
	{
		this.columnNames = columnNames;
		this.coltypes = coltypes;
	}
	public int getColumnCount() 
			throws SQLException
	{
		if(columnNames == null)
		   return 0;
		return columnNames.length;
	}
	public String getColumnName(int column) 
			throws SQLException
	{
		if(columnNames == null || column > columnNames.length)
			throw new SQLException("No Such Column : index=" + column);
		return columnNames[column-1];
	}

	public String getColumnTypeName(int column) 
			throws SQLException
	{
		if(coltypes == null || column > coltypes.length)
			throw new SQLException("No Such Column : index=" + column);
		return coltypes[column-1];
	}

	/////////////////////////////////////////////////////////////
	/////NOT IMPLEMENTED
	/////////////////////////////////////////////////////////////
	
	public String getCatalogName(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public String getColumnClassName(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public int getColumnDisplaySize(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public String getColumnLabel(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public int getColumnType(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public int getPrecision(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public int getScale(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public String getSchemaName(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public String getTableName(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public boolean isAutoIncrement(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public boolean isCaseSensitive(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public boolean isCurrency(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public boolean isDefinitelyWritable(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public int isNullable(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public boolean isReadOnly(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public boolean isSearchable(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public boolean isSigned(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public boolean isWritable(int column) 
			throws SQLException
	{
		throw new SQLException("Function Not Implemented");
	}
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}
}
