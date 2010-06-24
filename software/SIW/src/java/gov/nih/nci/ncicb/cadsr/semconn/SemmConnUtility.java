package gov.nih.nci.ncicb.cadsr.semconn;
import java.util.ArrayList;
import java.util.List;

public class SemmConnUtility 
{
  /**
   * This method adds underscore in between the words (eg: converts GeneHomolog
   * to Gene_Homolog)
   *
   * @param name
   */
  public static void evaluateString(String name, List<String> options, List<String> words) {
    if(options == null || words == null)
      throw new IllegalArgumentException("Options or Words is not initialized");
      
    //remove package name if the name is a class name
    if (name!=null){
        int index = name.lastIndexOf(".");
        name = name.substring(index+1);
    }    
    //Set optionSet = new HashSet();
    options.add(name);

    char firstChar = name.charAt(0);

    firstChar = Character.toUpperCase(firstChar);

    if (name.indexOf("_") > 0) {
      String temp = Character.toString(firstChar) + name.substring(1);
      options.add(temp);
    }
    String temp = firstChar + name.substring(1).toLowerCase();
    options.add(temp);

    String evaluatedString = null;;

    StringBuffer wholeWords = new StringBuffer();

    StringBuffer tempSeparateWord = new StringBuffer();

    char[] chars = name.toCharArray();

    StringBuffer sb = new StringBuffer();

    boolean first = true;

    int index = 0;

    for (int i = 0; i < chars.length; i++) {
      //Character c = new Character(chars[i]);
      //System.out.println("inside loop i = " +i);
      if (Character.isUpperCase(chars[i])) {
        if ((i > 1) && ((i - index) > 1)) {
          //System.out.println("Inside capital if");
          first = false;

          sb.append("_").append(chars[i]);

          words.add(tempSeparateWord.toString());

          tempSeparateWord = new StringBuffer();

          tempSeparateWord.append(chars[i]);

          wholeWords.append(" ").append(chars[i]);
        }

        else {
          wholeWords.append(chars[i]);

          tempSeparateWord.append(chars[i]);

          sb.append(chars[i]);
        }

        index = i;
      }

      else {
        if (chars[i] != '_') {
          sb.append(chars[i]);

          wholeWords.append(chars[i]);

          tempSeparateWord.append(chars[i]);
        }
      }
    }

    //System.out.println("Converted string: "+sb.toString());
    //if the string contains "_", then make the first character uppercase
    if (!first) {
      char c = Character.toUpperCase(sb.charAt(0));

      sb.deleteCharAt(0);

      sb.insert(0, c);

      char c1 = Character.toUpperCase(wholeWords.charAt(0));

      wholeWords.deleteCharAt(0);

      wholeWords.insert(0, c1);
    }

    options.add(sb.toString());
    options.add(wholeWords.toString());

    if (words.size() > 0) {
      /*
         StringBuffer tmp = (StringBuffer)separateWords.get(0);
         char c2 = Character.toUpperCase(tmp.charAt(0));
      
         tmp.deleteCharAt(0);
         tmp.insert(0, c2);
      
         separateWords.remove(0);
         separateWords.add(0, tmp);
       */
      String temp2 = words.get(words.size() - 1).toString();

      if (tempSeparateWord != null) {
        temp = tempSeparateWord.toString();

        if (temp2.compareToIgnoreCase(temp) != 0) {
          words.add(temp);
        }
      }
    }
    List possibleOptions = new ArrayList(options);
    options = null;//garbage collection ready
    
    //testing
     for (int i=0; i<possibleOptions.size();i++){
         System.out.println("options["+i+"]=" + possibleOptions.get(i));
     }
     for (int i=0; i<words.size();i++){
         System.out.println("separateWords["+i+"]=" + words.get(i));
       }     
    return;
 }


  public static void main(String[] args)
  {
    String name = "BoBiceRocks";
    String name2 = "Bo Bice Rocks";
    String name3 = "Bo_Bice_Rocks";
    
    List options = new ArrayList();
    List words = new ArrayList();
    
    SemmConnUtility.evaluateString(name,options,words);
    //SemmConnUtility.evaluateString(name2,options,words);
//    for(String s : words)
//      System.out.println(s);
    
    //SemmConnUtility semmConnUtility = new SemmConnUtility();
  }
}