package gov.nih.nci.ncicb.cadsr.loader.parser;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

import org.apache.log4j.Logger;

import com.infomata.data.*;

import java.util.*;
import java.io.File;
import java.io.IOException;

public class CsvParser implements Parser {

  private ProgressListener progressListener = null;
  
  private UMLHandler listener;

  private Logger logger = Logger.getLogger(CsvParser.class.getName());


  public void setEventHandler(LoaderHandler handler) {
    this.listener = (UMLHandler) handler;
  }

  /**
   * main parse method.
   *
   * @param filename a <code>String</code> value
   */
  public void parse(String filename) {
    //Reads report and puts records in the arraylist
    DataFile reader = DataFile.createReader("8859_1");
    reader.setDataFormat(new CSVFormat());
    List evsValues = new ArrayList();
    
    try {
      File inputFile = new File(filename);

      reader.open(inputFile);

      //read first row, to get out the header

      NewClassEvent classEvent = null;
      NewAttributeEvent attributeEvent = null;
      
      DataRow row = reader.next();
      while( (row = reader.next()) != null ) {
        if(row.getString(COL_CLASS).equals(row.getString(COL_ENTITY))) {
          NewClassEvent newClassEvent = createClassEvent(row);

          // is this row refering to the same object as the previous row?
          if(classEvent != null) {
            if(newClassEvent.getName().equals(classEvent.getName())) {
              classEvent.addConcept(newClassEvent.getConcepts().get(0));
            } else {
              // flush previous line
              listener.newClass(classEvent);
              // store new line
              classEvent = newClassEvent;
            }
            
          } else {
            classEvent = newClassEvent;
          }

        } else {
          // Flush class, if not already done
          if(classEvent != null) {
            listener.newClass(classEvent);
            classEvent = null;
          }
          
          NewAttributeEvent newAttributeEvent = createAttributeEvent(row);
          if(attributeEvent != null) {
            if(newAttributeEvent.getName().equals(attributeEvent.getName()) && newAttributeEvent.getClassName().equals(attributeEvent.getClassName())) {
              attributeEvent.addConcept
                (newAttributeEvent.getConcepts().get(0));
            } else {
              listener.newAttribute(attributeEvent);
              attributeEvent = newAttributeEvent;
            }
          } else {
            attributeEvent = newAttributeEvent;
          }
        }
        
      }
      // Flush last line
      if(classEvent != null)
        listener.newClass(classEvent);
      if(attributeEvent != null)
        listener.newAttribute(attributeEvent);
      

    } catch(IOException e) {
      System.exit(1);
    } finally {
      reader.close();
    }


  }

  private static String DEFAULT_PACKAGE_NAME = "Classes";

  private static int COL_CLASS = 0;
  private static int COL_ENTITY = 1;
  private static int COL_DESCRIPTION = 2;
//   private static int COL_CONCEPT_NAME = 4;
  private static int COL_CONCEPT_PREF_NAME = 4;
  private static int COL_CONCEPT_CODE = 6;
  private static int COL_CONCEPT_DEF = 7;
  private static int COL_CONCEPT_DEF_SOURCE = 8;
  
  

  public void addProgressListener(ProgressListener listener) {
    progressListener = listener;
  }

  private NewClassEvent createClassEvent(DataRow row) {
    NewClassEvent evt = new NewClassEvent(DEFAULT_PACKAGE_NAME + "." + row.getString(COL_CLASS));
    evt.setPackageName(DEFAULT_PACKAGE_NAME);
    evt.addConcept(createConceptEvent(row));
    evt.setDescription(row.getString(COL_DESCRIPTION));
    return evt;
  }
  private NewAttributeEvent createAttributeEvent(DataRow row) {
    NewAttributeEvent evt = new NewAttributeEvent(row.getString(COL_ENTITY));
    evt.setClassName(DEFAULT_PACKAGE_NAME + "." + row.getString(COL_CLASS));
    
    evt.addConcept(createConceptEvent(row));
    evt.setDescription(row.getString(COL_DESCRIPTION));
    return evt;
  }

  private NewConceptEvent createConceptEvent(DataRow row) {
    NewConceptEvent conceptEvt = new NewConceptEvent();
    conceptEvt.setConceptCode(row.getString(COL_CONCEPT_CODE));
    conceptEvt.setConceptDefinition(row.getString(COL_CONCEPT_DEF));
    conceptEvt.setConceptPreferredName(row.getString(COL_CONCEPT_PREF_NAME));
    conceptEvt.setConceptDefinitionSource(row.getString(COL_CONCEPT_DEF_SOURCE));
    return conceptEvt;
  }

}
