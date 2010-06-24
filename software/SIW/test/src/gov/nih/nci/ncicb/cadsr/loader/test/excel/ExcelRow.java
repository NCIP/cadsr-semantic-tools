package gov.nih.nci.ncicb.cadsr.loader.test.excel;

public class ExcelRow
{
    private Object[] data;

    public ExcelRow(Object data[])
    {
	    this.data = data;
    }

    public Object[] getData()
    {
        return data;
    }

    public Object getData(int index)
    {
        if (data != null && data.length > index)
        {
            return data[index];
        }
        return null;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        if (data != null)
        {
            for (int i = 0; i < data.length; i++)
            {
                sb.append(data[i] + "\t");
            }
        }
        return sb.toString() + "\n";
    }

}
