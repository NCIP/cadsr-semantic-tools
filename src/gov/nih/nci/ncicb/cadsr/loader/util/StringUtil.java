package gov.nih.nci.ncicb.cadsr.loader.util;

public class StringUtil {

  /**
   * @return the same String with the first char is uppercase.
   */
  public static String upperFirst(String s) {
    String first = s.substring(0, 1).toUpperCase();
    return first + s.substring(1);
  }

  /**
   * @return true if s is either null or empty.
   */
  public static boolean isEmpty(String s) {
    return (s == null) || (s.length() == 0); 
  }
}