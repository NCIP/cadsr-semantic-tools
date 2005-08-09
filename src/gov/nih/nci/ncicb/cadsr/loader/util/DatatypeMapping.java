package gov.nih.nci.ncicb.cadsr.loader.util;

import java.util.Set;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

public class DatatypeMapping {

  private static Map<String, String> vdMapping = new HashMap<String, String>();

  public static Map<String, String> getMapping() { return vdMapping; }

  public static Set<String> getKeys() {return vdMapping.keySet();}

  public static Collection<String> getValues() {return vdMapping.values();}

  static {
    vdMapping.put("int", "java.lang.Integer");
    vdMapping.put("float", "java.lang.Float");
    vdMapping.put("boolean", "java.lang.Boolean");
    vdMapping.put("Integer", "java.lang.Integer");
    vdMapping.put("Float", "java.lang.Float");
    vdMapping.put("Boolean", "java.lang.Boolean");
    vdMapping.put("Short", "java.lang.Short");
    vdMapping.put("Double", "java.lang.Double");
    vdMapping.put("Char", "java.lang.Char");
    vdMapping.put("Byte", "java.lang.Byte");
    vdMapping.put("Long", "java.lang.Long");

    vdMapping.put("String", "java.lang.String");

    vdMapping.put("Date", "java.util.Date");
    vdMapping.put("DateTime", "java.util.Date");

    vdMapping.put("any", "java.lang.Object");
    vdMapping.put("valueany", "java.lang.Object");
    vdMapping.put("any[][][]", "java.lang.Object");
  }


}