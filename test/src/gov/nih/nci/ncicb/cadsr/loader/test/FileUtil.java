package gov.nih.nci.ncicb.cadsr.loader.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil
{
	/**
	 * There is a much cleaner implementation of doing this, but good
	 * enough since it is only used for testing
	 */
	public static String getFileContent(InputStream is)
			throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		StringBuffer buff = new StringBuffer();
		
		while((line = br.readLine()) !=null)
		{
			buff.append(line + "\n");
		}

		return buff.toString();
	}
}
