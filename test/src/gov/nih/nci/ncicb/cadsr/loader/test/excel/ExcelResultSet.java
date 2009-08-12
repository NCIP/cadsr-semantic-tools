package gov.nih.nci.ncicb.cadsr.loader.test.excel;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ExcelResultSet
{
	String classname;
    ExcelRow colnames;
    ExcelRow type;
    ArrayList rows = new ArrayList(); 

    public ExcelResultSet(ExcelRow colnames, ExcelRow type)
    {
        this.colnames = colnames;
        this.type = type;
    }
    
    public void addRow(ExcelRow row)
    {
       rows.add(row);
    }

    public ArrayList getRows()
    {
       return rows;
    }
    
    public int compare(ResultSet rs)
    {
       return 0;
    }
    
	public String getClassname()
	{
		return classname;
	}

	public void setClassname(String classname)
	{
		this.classname= classname;
	}

	public String [] getColumns()
    {
        return (String [])(colnames.getData());
    }
    
    public String [] getType()
    {
        return (String [])(type.getData());
    }
    
    public String getType(int index)
    {
        String [] dtype = (String [])type.getData();
        if(dtype != null && dtype.length <= index)
           return (String)(dtype[index]);
        return null;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        if(colnames != null)
       	   sb.append(colnames.toString());
        if(type != null)
           sb.append(type.toString());
        for(int i=0; i<rows.size();i++)
        {
        	ExcelRow row = (ExcelRow)rows.get(i);
        	sb.append(row.toString());
        }
        return sb.toString();   	   
    }
    
}
