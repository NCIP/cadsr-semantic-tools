package gov.nih.nci.cadsr.common;

import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrPrivateApiModule;

//import org.apache.log4j.Logger;

/** Logger wrapper class */
public class Logger {

	//private Logger logger = Logger.getLogger(CadsrPrivateApiModule.class.getName());

	public static void error(String msg) {
		System.out.println(msg);	//SIW-1
		//logger.error(msg);
	}

}
