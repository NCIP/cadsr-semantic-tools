package gov.nih.nci.ncicb.cadsr.loader.test.cadsrapi;

import java.util.*;
import java.io.*;


public class TestCaseProperties {

  private static Properties testData = new Properties();
  private static Properties classMapping = new Properties();
  protected static HashMap methodCount = new HashMap();

  private HashMap vars = new HashMap();

  static  {
    try {
      testData.load(ClassLoader.getSystemResource("testData.properties").openStream());
      classMapping.load(ClassLoader.getSystemResource("classMapping.properties").openStream());

      TestCaseProperties tcp = new TestCaseProperties();
      tcp.generateData();
      

    } catch (IOException e){
      System.out.println("Can't open properties File");
      System.exit(1);
    } // end of try-catch
  }

  public static String getProperty(String name) {
    return testData.getProperty(name);
  }

  public static String getTestData(String name) {
    StackTraceElement st = new Exception().getStackTrace()[1]; 
    String className = st.getClassName().substring(st.getClassName().lastIndexOf(".") + 1);
    String prefix = className + "."; // + st.getMethodName();

    String countStr = className + "." + st.getMethodName();

    Integer i = (Integer)methodCount.get(countStr);
    int count = 0;
    if(i != null)
      count = i.intValue();
    
    String property = null;
    while(count != -1) {
//       System.out.println("Looking for property: " + prefix + name + "." + count);
      property = testData.getProperty(prefix + name + "." + count);
      count--;
      if(property != null) 
	return property;
    }
    if(property == null)
      property = testData.getProperty(prefix + name);

    return property;
    
  }

  public static void addCount(String cName, String mName) {
    String className = cName.substring(cName.lastIndexOf(".") + 1);
    String prefix = className + "." + mName;

    Integer i = (Integer)methodCount.get(prefix);

    if(i == null)
      methodCount.put(prefix, new Integer(0));
    else 
      methodCount.put(prefix, new Integer(i.intValue() + 1));

  }

  public static String getFullClassName(String className) {
    return classMapping.getProperty(className) + "." + className;
  }

  public static String[] getTestList() {
    ArrayList list = new ArrayList();

    try {

      BufferedReader br = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResource("testData.properties").openStream()));

      String lastTest = "";
      int lastCount = -1;
      String line = null;
      while((line = br.readLine()) != null) {
	if(line.trim().length() == 0)
	  continue;
	if(line.startsWith("#"))
	  continue;

	String propName = line.substring(0, line.indexOf("=")).trim();
	
	int ind = propName.indexOf(".");
	ind = propName.indexOf(".", ind+1);
	String testName = propName.substring(0, ind);
	String tc = propName.substring(propName.lastIndexOf(".") + 1);
	
	int testCount = 0;
	try {
	  testCount = Integer.parseInt(tc);
	} catch (NumberFormatException e){ // do nothing, no count means 0
	} 
	
	// is this new test?
	if(!testName.equals(lastTest) || testCount != lastCount) {
	  list.add(testName);
	}

	lastTest = testName;
	lastCount = testCount;
      }
    } catch (IOException e){
      e.printStackTrace();
    } 

    String[] result = new String[list.size()];
    list.toArray(result);
    return result;
  }

  private void generateData() {
    // 1st pass: defines
    Enumeration propNames = testData.propertyNames();
    while(propNames.hasMoreElements()) {
      String propName = (String)propNames.nextElement();
      
      try {
	String value = testData.getProperty(propName);
	int index1, index2;
	if((index1 = value.indexOf("@")) != -1) {
	  if((index2 = value.indexOf("@", index1 + 1)) != -1) {
	    String perc = value.substring(index1 + 1, index2);
	    if(perc.startsWith("def,")) {
	      perc = perc.substring(perc.indexOf(",") + 1);
	      String varName = perc.substring(0, perc.indexOf(","));
	      String val = perc.substring(perc.indexOf(",") + 1);
	      vars.put(varName, val);
	      value = value.substring(0, index1) + val;
	      testData.setProperty(propName, value);
	    }
	  }
	}      
      } catch (Throwable e) { 
	System.out.println("----------WARNING: BAD Variable");
      } 
    }

    // 2nd Pass, generated Data
    propNames = testData.propertyNames();
    while(propNames.hasMoreElements()) {
      String propName = (String)propNames.nextElement();
      
      String value = testData.getProperty(propName);
      int index1, index2;
      if((index1 = value.indexOf("@")) != -1) {
	if((index2 = value.indexOf("@", index1 + 1)) != -1) {
	  // We found the %....% string. Extract it.
	  String perc = value.substring(index1 + 1, index2);
	  String[] args = perc.split(",");

	  try {
	    int min = Integer.parseInt(args[1]);
	    int max = Integer.parseInt(args[2]);
	    String name = null;
	    if(args.length > 3)
	      name = args[3];

	    String gen = null;

	    if(args[0].equals("alpha")) {
	      gen = generateAlpha(min, max);
	    } else if(args[0].equals("num")) {
	      gen = generateNum(min, max);
	    } else { // unrecognized, don't do anything.
	      continue;
	    }

	    String val = value.substring(index2+1);
	    value = value.substring(0, index1) + gen + val;
	    testData.setProperty(propName, value);

	    if(name != null) {
	      if(vars.containsKey(name)) {
		System.out.println("----- WARNING, duplicate Variable Definition ---");
	      } else {
		vars.put(name, value);
	      }
	    }

	  } catch (NumberFormatException e){
	    continue;
	  } // end of try-catch
	}
      }
    }

    // 3rd pass, replace Vars
    propNames = testData.propertyNames();
    while(propNames.hasMoreElements()) {
      String propName = (String)propNames.nextElement();
      String value = testData.getProperty(propName);
      int index1, index2;
      if((index1 = value.indexOf("@")) != -1) {
	if((index2 = value.indexOf("@", index1 + 1)) != -1) {
	  String perc = value.substring(index1 + 1, index2);
	  if(perc.startsWith("var,")) {
	    String varName = perc.substring(perc.indexOf(",") + 1);
	    String val = (String)vars.get(varName);
	    val = value.substring(0, index1) + val;
	    testData.setProperty(propName, val);
	  }
	} 
      }
    }
  }

  private String alpha = "abcdefghijklmnopqrstuvwxyz,./;'!@#$&*()-+?:\"";
  private String num = "0123456789";

  private String generateAlpha(int min, int max) {
    String letters = alpha + num;
    int aSize = letters.length();
    int diff = max - min;

    Random random = new Random(new Date().getTime());
    int len = random.nextInt(diff) + min;
    
    StringBuffer sb = new StringBuffer();
    for(int i=0; i<len; i++) {
      int curs = random.nextInt(aSize);
      sb.append(letters.charAt(curs));
    }

    return sb.toString();

  }

  private String generateNum(int min, int max) {
    int aSize = num.length();
    int diff = max - min;

    Random random = new Random(new Date().getTime());
    int len = random.nextInt(diff) + min;
    
    StringBuffer sb = new StringBuffer();
    for(int i=0; i<len; i++) {
      int curs = random.nextInt(aSize);
      sb.append(num.charAt(curs));
    }

    return sb.toString();
    
  }


}