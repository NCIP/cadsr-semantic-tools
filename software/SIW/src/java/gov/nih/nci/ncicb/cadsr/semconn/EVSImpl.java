package gov.nih.nci.ncicb.cadsr.semconn;

import gov.nih.nci.ncicb.cadsr.evs.EVSConcept;
import gov.nih.nci.ncicb.cadsr.evs.LexEVSQueryService;
import gov.nih.nci.ncicb.cadsr.evs.LexEVSQueryServiceImpl;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.LexGrid.commonTypes.Source;
import org.LexGrid.concepts.Definition;
import org.apache.log4j.Logger;


public class EVSImpl extends SubjectClass{
  private static String vocabularyName;
  private static Logger log = Logger.getLogger(EVSImpl.class.getName());
  private ArrayList evsValues;
  private String serverURL;
  private ArrayList possibleOptions;
  private ArrayList separateWords;
  private LexEVSQueryService evsQueryService;

  //private Configuration properties;
  public EVSImpl() {
    vocabularyName = Configuration.getVocabularyName();
    serverURL = Configuration.getServerURL();
//     appService = ApplicationService.getRemoteInstance(serverURL);
    try {
    	evsQueryService = new LexEVSQueryServiceImpl();
    } catch (Exception e) {
      log.error("Can't get cadsr publicAPI, contact support");
      e.printStackTrace();
      throw new RuntimeException(e);
    } // end of try-catch
  }

  /**
   * get tagged values
   *
   * @param umlEntities
   *
   * @return
   */
  public List getTaggedValues(List umlEntities)
    throws Exception {

    evsValues = new ArrayList();
    possibleOptions = new ArrayList();
    separateWords = new ArrayList();
    
    try {
      HashMap map = null;

      ProgressEvent event = new ProgressEvent();
      event.setMessage("Searching EVS...");
      event.setGoal(umlEntities.size());
      for (int i = 0; i < umlEntities.size(); i++) {
        event.setStatus(i);
        notify(event);
        map = (HashMap) umlEntities.get(i);

        String name = (String) map.get(Configuration.getUMLEntityCol());

        //System.out.println("\noriginal string before call evaluateString: " + name + " \n");
        //evaluate string to insert underscore between  words..(if the name has multiple words)
        evaluateString(name);

        //System.out.println("name: "+name);
        //populate modifieddate, verified fields
        map.put(Configuration.getModifiedDateCol(), null);

        map.put(Configuration.getVerifiedFlagCol(), "0");

        //get evs values
        //getEVSValues(name, map);
        List ret = getEVSValues(map);

        //add the return result to the list. If no result returned, must add
        //the original map.
        if (ret!=null && (!ret.isEmpty())){
            evsValues.addAll(ret);
        }else{
            evsValues.add(map);   
        }

        possibleOptions.clear();

        separateWords.clear();
      }
      
      notifyEventDone("Searching EVS finished");

      //debug
      //testOutput(evsValues);
    }

    catch (Exception e) {
      log.error("Exception in getTaggedValues: " + e.getMessage());
      e.printStackTrace();

      throw new Exception("Exception in getTaggedValues: " + e.getMessage());
    }

    return evsValues;
  }

  /**
   * copy values to new hash
   *
   * @param entitiesMap
   * @param newMap
   */
  private void copyHash(
    HashMap entitiesMap,
    HashMap newMap) {
    for (Iterator iter = entitiesMap.keySet().iterator(); iter.hasNext();) {
      String key = (String) iter.next();

      newMap.put(key, entitiesMap.get(key));
    }
  }

  /**
   * Gets concept code for the given name
   *
   * @param name
   *
   * @return
   */
  private String getConceptCode(String name) throws Exception {
    String code = null;

    try {
      List<String> conceptNames = new ArrayList<String>();
      conceptNames.add(name);
      List<EVSConcept> evsConcepts = evsQueryService.findConceptDetailsByName(conceptNames, false, vocabularyName);

      if (evsConcepts != null && evsConcepts.size() > 0) {
    	  EVSConcept evsConcept = evsConcepts.get(0);
    	  
    	  code =  evsConcept.getCode();
      }
    }
    catch (Exception e) {
      log.error(
        "Exception occured while getting concept code: " + e.getMessage());

      throw new Exception(
        "Exception occured while getting concept code: " + e.getMessage());
    }

    return code;
  }
  

  /**
   * Gets evs values
   *
   * @param entitiesMap
   *
   * @throws Exception
   */
   private List getEVSValues(HashMap entitiesMap) throws Exception {
     //System.out.print(".");
     //System.out.println("getEVSValues....");
     //get uml class and uml entity
     String umlClass = (String) entitiesMap.get(Configuration.getUMLClassCol());

     String umlEntity =
       (String) entitiesMap.get(Configuration.getUMLEntityCol());

     String classification = null;

     //if the umlclass and uml entity are equal, then it is class.. else it is attribute
     if (umlClass.equalsIgnoreCase(umlEntity)) {
       classification = Configuration.getClassTag();
     }
     else
     {
       classification = Configuration.getAttributeTag();
     }

     HashMap newlyFound = new HashMap();
     
     // go through loops of possible options
     String name = "";

     boolean search = false; // only want to search individual term once

     //System.out.println("possibleOptions size = " + possibleOptions.size());
     for (int i = 0; i < possibleOptions.size(); i++) {
       name = (String) possibleOptions.get(i);
       //System.out.println("possibleOptions: " + name);
        if (newlyFound.containsKey(name)){
            continue; //loop to next option.
        }
        
       String conceptCode = getConceptCode(name);
       //System.out.println("conceptCode = " + conceptCode);
       if (conceptCode != null) {
         HashMap found = new HashMap();
         copyHash(entitiesMap, found);
         found.put(Configuration.getConceptCodeCol(), conceptCode);
         found.put(Configuration.getConceptName(), name);
         found.put(Configuration.getClassificationCol(), classification);
         //System.out.println("calling getProperties...."+ conceptCode);
         getProperties(name, found);
         newlyFound.put(name, found);
         //Future: multiple code enhancement..
         //Assuming that we get the secondary concept codes from the properties
       }
       else {
         //System.out.println("\n11111calling get synonyms... when conceptcode is null");
         //concept is null.... get synonyms
         String[] concepts = getSynonyms(name);

         boolean first = true;

         HashMap newMap = null;

         //System.out.println("concepts length = " + concepts.length);
         //add each concept (synonym) as a new record
         if ((concepts.length == 0) && !search) {
           search = true;

           //System.out.println("search individual word when concept code synonyms is null");
           for (int j = 0; j < separateWords.size(); j++) {
             name = separateWords.get(j).toString();

             //System.out.println("separate word: " + name);
             if (newlyFound.containsKey(name)){
                 continue; //loop to next seperate words.
             }
             conceptCode = getConceptCode(name);

             //System.out.println("concept code = " + conceptCode);
             if (conceptCode != null) {
               HashMap found = new HashMap();
               copyHash(entitiesMap, found);                 
               found.put(Configuration.getConceptCodeCol(), conceptCode);
               found.put(Configuration.getConceptName(), name);
               found.put(Configuration.getClassificationCol(), classification);
               //System.out.println("calling getProperties...."+ conceptCode);
               getProperties(name, found);
               newlyFound.put(name, found);
             }
             else {
               String[] concepts1 = getSynonyms(name);
               boolean first1 = true;
               HashMap newMap1 = null;

               //System.out.println("concepts1 length = " + concepts1.length);
               for (int k = 0; k < concepts1.length; k++) {
                 name = concepts1[k];

                 //get conceptcode for each concept
                 //conceptCode = getConceptCode(name);
                 //System.out.println("calling getConcept code...(for synonyms)... name: " + name );
                  if (newlyFound.containsKey(name)){
                      continue; //loop to next seperate words.
                  }
                 conceptCode = getConceptCode(name);

                 //System.out.println("\n22222concept code = " + conceptCode);
                 if (conceptCode != null) {
                   //System.out.println("Concept code = "+ conceptCode+ "\tname= "+ name);
                   if (first1) {
                     //System.out.println("first1.. block adding to entitiesMap");
                     //add first one to the existing map
                     HashMap found = new HashMap();
                     copyHash(entitiesMap, found);
                     found.put(
                       Configuration.getConceptCodeCol(), conceptCode);
                     found.put(Configuration.getConceptName(), name);
                     found.put(
                       Configuration.getClassificationCol(), classification);
                     //System.out.println("getProperties...");
                     getProperties(name, found);
                     newlyFound.put(name, found);
                     first1 = false;
                   }

                   else {
                     //since, multiple synonyms, create new map and add to it
                     //create new HashMap
                     HashMap found = new HashMap();
                     //copy all other values from the above entitiesMap
                     copyHash(entitiesMap, found);

                     //System.out.println("adding EVS values....");
                     found.put(Configuration.getConceptCodeCol(), conceptCode);
                     found.put(Configuration.getConceptName(), name);
                     found.put(
                       Configuration.getClassificationCol(), classification);

                     //getProperties
                     //System.out.println("getProperties...");
                     getProperties(name, found);
                     //add this new map to the list
                      newlyFound.put(name, found);
                   }
                 }
               }
             }
           }
         }

         else {
           for (int k = 0; k < concepts.length; k++) {
             name = concepts[k];

             //get conceptcode for each concept
              if (newlyFound.containsKey(name)){
                  continue; //loop to next seperate words.
              }
             conceptCode = getConceptCode(name);

             //System.out.println("\n22222concept code = " + conceptCode);
             if (conceptCode != null) {
               //System.out.println("Concept code = "+ conceptCode+ "\tname= "+ name);
               if (first) {
                 //add first one to the existing map
                 HashMap found = new HashMap();
                 copyHash(entitiesMap, found);
                 found.put(Configuration.getConceptCodeCol(), conceptCode);
                 found.put(Configuration.getConceptName(), name);
                 found.put(
                   Configuration.getClassificationCol(), classification);
                 //System.out.println("getProperties...");
                 getProperties(name, found);
                 newlyFound.put(name, found);
                 first = false;
               }

               else {
                 //since, multiple synonyms, create new map and add to it
                 //create new HashMap
                 HashMap found = new HashMap();
                 //copy all other values from the above entitiesMap
                 copyHash(entitiesMap, found);

                 //System.out.println("adding EVS values....");
                 found.put(Configuration.getConceptCodeCol(), conceptCode);
                 found.put(Configuration.getConceptName(), name);
                 found.put(
                   Configuration.getClassificationCol(), classification);

                 //getProperties
                 //System.out.println("getProperties...");
                 getProperties(name, found);
                 //add this new map to the list
                  newlyFound.put(name, found);
               }
             }

             else // go through loop of individual words
              {
               //System.out.println("search individual word when concept code is null");
               for (int j = 0; j < separateWords.size(); j++) {
                 name = separateWords.get(j).toString();

                 //System.out.println("separate word: " + name);
                  if (newlyFound.containsKey(name)){
                      continue; //loop to next seperate words.
                  }
                 conceptCode = getConceptCode(name);

                 if (conceptCode != null) {
                   HashMap found = new HashMap();
                   copyHash(entitiesMap, found);
                   found.put(
                     Configuration.getConceptCodeCol(), conceptCode);
                   found.put(Configuration.getConceptName(), name);
                   found.put(
                     Configuration.getClassificationCol(), classification);
                   //System.out.println("calling getProperties...."+ conceptCode);
                   getProperties(name, found);
                   newlyFound.put(name, found);
                 }
               }
             }
           }
         }
       }
     } // end for loop
      //for testing
     if (newlyFound==null || newlyFound.isEmpty()){
         return null;
     }else{
        return new ArrayList(newlyFound.values());
     }
   }     


  private String[] getConceptCodes(String name) throws Exception {
    //System.out.println("EVSImpl - getConceptCodes...");
    String[] codes = null;

    try {
      List<String> conceptNames = new ArrayList<String>();
      conceptNames.add(name);
      
      List<EVSConcept> evsConcepts = evsQueryService.findConceptDetailsByName(conceptNames, false, vocabularyName);
      if (evsConcepts != null) {
    	  codes = new String[evsConcepts.size()];
    	  for (int i=0;i<evsConcepts.size();i++) {
    		  codes[i] = evsConcepts.get(i).getCode();
    	  }
      }
    }

    catch (Exception e) {
      log.error(
        "Exception occured while getting concept code: " + e.getMessage());

      throw new Exception(
        "Exception occured while getting concept code: " + e.getMessage());
    }

    return codes;
  }

  /**
   * get synonyms
   *
   * @param name
   *
   * @return
   */
  private String[] getSynonyms(String name) throws Exception {
    String[] concepts = null;

    try {
      List<EVSConcept> evsConcepts = evsQueryService.findConceptsBySynonym(name, false, 0, vocabularyName);
      if (evsConcepts != null) {
    	  concepts = new String[evsConcepts.size()];
    	  for (int i=0;i<evsConcepts.size();i++) {
    		  concepts[i] = evsConcepts.get(i).getCode();
    	  }
      }
    }

    catch (Exception e) {
      log.error("Exception occured while getting synonyms: " + e.getMessage());

      throw new Exception(
        "Exception occured while getting synonyms: " + e.getMessage());
    }

    return concepts;
  }

  /**
   * Gets properties for the given name, and parse the properties.
   *
   * @param name
   *
   * @throws Exception
   */
  private void getProperties(
    String name,
    HashMap entitiesMap) throws Exception {
    try {    	
    	List<String> conceptNames = new ArrayList<String>();
    	conceptNames.add(name);
    	
    	List<EVSConcept> evsConcepts = evsQueryService.findConceptDetailsByName(conceptNames, false, vocabularyName);
    	if (evsConcepts != null && evsConcepts.size() > 0) {
    		EVSConcept evsConcept = evsConcepts.get(0);
    		String preferredName = evsConcept.getPreferredName();
    		
    		entitiesMap.put(Configuration.getPreferredNameCol(), preferredName);
    		
    		List<Definition> definitions = evsConcept.getDefinitions();
    		String definition = null;
    		String definitionSource = null;
    		Definition nciDefinition = getNCIDefinition(definitions);
    		if (nciDefinition != null) {
    			definition = nciDefinition.getValue().getContent();
    			definitionSource = "NCI";
    		}
    		else {
    			if (definitions != null && definitions.size() > 0) {
    				Definition defaultDef = definitions.get(definitions.size()-1);
    				
    				definition = defaultDef.getValue().getContent();
    				Source[] defaultSources = defaultDef.getSource();
    				if (defaultSources != null && defaultSources.length > 0) {
    					definitionSource = defaultSources[defaultSources.length-1].getContent();
    				}
    			}
    		}
    		
    		entitiesMap.put(Configuration.getDefinitionSourceCol(), definitionSource);
			entitiesMap.put(Configuration.getDefinitionCol(), definition);
    	}
    }
    catch (Exception e) {
      log.error(
        "Exception occured while getting properties: " + e.getMessage());

      throw new Exception(
        "Exception occured while getting properties: " + e.getMessage());
    }
  }
  
  private Definition getNCIDefinition(List<Definition> definitions) {
	  if (definitions != null) {
		  for (Definition def: definitions) {
			if (isNCIDefinition(def)) {
				return def;
			}
		}
	  }
	  return null;
  }
  
  private boolean isNCIDefinition(Definition definition) {
	  Source[] sources = definition.getSource();
		if (sources != null && sources.length > 0) {
			for (Source defSource: sources) {
				if (defSource.getContent().equalsIgnoreCase("NCI")) {
					return true;
				}
			}
		}
		
		return false;
  }

  /**
   * Parse the value
   *
   * @param value
   *
   * @return
   */
  private Vector getPropertyElements(String value) {
    Vector vector = new Vector();

    String s1 = value;

    for (int i = s1.indexOf("</"); i != -1; i = s1.indexOf("</")) {
      s1 = s1.substring(i + 1);

      int j = s1.indexOf(">");

      String s2 = s1.substring(1, j);

      vector.add(s2.trim());
    }

    return vector;
  }

  /**
   * Parse the element value
   *
   * @param s
   * @param s1
   *
   * @return
   */
  private String getPropertyElementValue(
    String s,
    String s1) {
    Vector vector;

    Vector vector1;

    s = s.trim();

    vector = getPropertyElements(s1);

    vector1 = parseXML(s1);

    if (vector1.size() != vector.size()) {

      return "";
    }

    int i;

    i = -1;

    int j = 0;

    do {
      if (j >= vector.size()) {

        break;
      }

      if (s.equalsIgnoreCase((String) vector.elementAt(j))) {
        i = j;

        break;
      }

      j++;
    }
    while (true);

    if (i == -1) {

      return "";
    }

    return (String) vector1.elementAt(i);
  }

  /**
   * Parse the string (string is in xml format)
   *
   * @param s
   *
   * @return
   */
  private Vector parseXML(String s) {
    Vector vector = new Vector();

    StringTokenizer stringtokenizer = new StringTokenizer(s, "<");

    boolean flag = true;

    String s2;

    String s1 = s2 = "";

    try {
      while (stringtokenizer.hasMoreTokens()) {
        String s3 = stringtokenizer.nextToken();

        if (flag) {
          int i = s3.indexOf(">");

          s2 = s3.substring(0, i);

          s1 = s1 + s3.substring(i + 1);

          String s4 = stringtokenizer.nextToken();

          if (s4.startsWith("/")) {
            String s6 = s3.substring(i + 1);

            vector.add(s6);
          }
          else {
            s1 = s1 + "<" + s4;

            flag = false;
          }
        }
        else
         if (!s3.equalsIgnoreCase("/" + s2 + ">")) {
          String s5 = stringtokenizer.nextToken();

          s1 = s1 + "<" + s3;

          if (!s5.equalsIgnoreCase("/" + s2 + ">")) {
            s1 = s1 + "<" + s5;
          }
          else {
            vector.add(s1);

            flag = true;
          }
        }
        else {
          vector.add(s1);

          flag = true;
        }
      }
    }

    catch (Exception exception) {
      log.error("Exception: " + exception.getMessage());

      exception.printStackTrace();

      return null;
    }

    return vector;
  }

  /**
   * This method adds underscore in between the words (eg: converts GeneHomolog
   * to Gene_Homolog)
   *
   * @param name
   */
  private void evaluateString(String name) {
    //remove package name if the name is a class name
    if (name!=null){
        int index = name.lastIndexOf(".");
        name = name.substring(index+1);
    }    
    Set optionSet = new HashSet();
    optionSet.add(name);

    char firstChar = name.charAt(0);

    firstChar = Character.toUpperCase(firstChar);

    if (name.indexOf("_") > 0) {
      String temp = Character.toString(firstChar) + name.substring(1);
      optionSet.add(temp);
    }
    String temp = firstChar + name.substring(1).toLowerCase();
    optionSet.add(temp);

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

          separateWords.add(tempSeparateWord);

          tempSeparateWord = null;

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

    optionSet.add(sb.toString());
    optionSet.add(wholeWords.toString());

    if (separateWords.size() > 0) {
      /*
         StringBuffer tmp = (StringBuffer)separateWords.get(0);
         char c2 = Character.toUpperCase(tmp.charAt(0));
      
         tmp.deleteCharAt(0);
         tmp.insert(0, c2);
      
         separateWords.remove(0);
         separateWords.add(0, tmp);
       */
      String temp2 = separateWords.get(separateWords.size() - 1).toString();

      if (tempSeparateWord != null) {
        temp = tempSeparateWord.toString();

        if (temp2.compareToIgnoreCase(temp) != 0) {
          separateWords.add(temp);
        }
      }
    }
    possibleOptions = new ArrayList(optionSet);
    optionSet = null;//garbage collection ready
    
    //testing
     for (int i=0; i<possibleOptions.size();i++){
         System.out.println("options["+i+"]=" + possibleOptions.get(i));
     }
     for (int i=0; i<separateWords.size();i++){
         System.out.println("separateWords["+i+"]=" + separateWords.get(i));
       }     
    return;
 }

  /////////////////testing////////////////////
  private void testOutput(ArrayList attList) {
    for (int i = 0; i < attList.size(); i++) {
      HashMap map = (HashMap) attList.get(i);

      //System.out.println("******** List of values***********");
      for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
        String key = (String) iter.next();

        String value = (String) map.get(key);

        //System.out.println(key+" = "+value);
      }
    }
  }

 
}
