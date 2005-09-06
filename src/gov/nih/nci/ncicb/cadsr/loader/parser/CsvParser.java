/*
 * Copyright 2000-2003 Oracle, Inc. This software was developed in conjunction with the National Cancer Institute, and so to the extent government employees are co-authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105.
 *
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the disclaimer of Article 3, below. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 2. The end-user documentation included with the redistribution, if any, must include the following acknowledgment:
 *
 * "This product includes software developed by Oracle, Inc. and the National Cancer Institute."
 *
 * If no such end-user documentation is to be included, this acknowledgment shall appear in the software itself, wherever such third-party acknowledgments normally appear.
 *
 * 3. The names "The National Cancer Institute", "NCI" and "Oracle" must not be used to endorse or promote products derived from this software.
 *
 * 4. This license does not authorize the incorporation of this software into any proprietary programs. This license does not authorize the recipient to use any trademarks owned by either NCI or Oracle, Inc.
 *
 * 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, ORACLE, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 */
package gov.nih.nci.ncicb.cadsr.loader.parser;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

import gov.nih.nci.ncicb.cadsr.loader.ReviewTracker;
import org.apache.log4j.Logger;

import com.infomata.data.*;

import java.util.*;
import java.io.File;
import java.io.IOException;

/**
 * A parser for CSV files 
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
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

      // skip first line
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

        }
      }
      // Flush last line
      if(classEvent != null)
        listener.newClass(classEvent);
      classEvent = null;
      
      // second pass. We're interested in attributes now. 
      // for lack of a .first() method, we reload the file.
      reader.open(inputFile);
      row = reader.next();
      while( (row = reader.next()) != null ) {
        // condition for an attribute
        if(!row.getString(COL_CLASS).equals(row.getString(COL_ENTITY))) {
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
      // flush last line
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
  private static int COL_HUMAN_VERIFIED = 10;
  

  public void addProgressListener(ProgressListener listener) {
    progressListener = listener;
  }

  private NewClassEvent createClassEvent(DataRow row) {
    NewClassEvent evt = new NewClassEvent(DEFAULT_PACKAGE_NAME + "." + row.getString(COL_CLASS));
    evt.setPackageName(DEFAULT_PACKAGE_NAME);
    evt.addConcept(createConceptEvent(row));
    evt.setDescription(row.getString(COL_DESCRIPTION));
    
    evt.setReviewed(row.getString(COL_HUMAN_VERIFIED).equals("1")?true:false);
    
    return evt;
  }
  private NewAttributeEvent createAttributeEvent(DataRow row) {
    NewAttributeEvent evt = new NewAttributeEvent(row.getString(COL_ENTITY));
    evt.setClassName(DEFAULT_PACKAGE_NAME + "." + row.getString(COL_CLASS));
    
    evt.addConcept(createConceptEvent(row));
    evt.setDescription(row.getString(COL_DESCRIPTION));
    
    evt.setType("");
    
    evt.setReviewed(row.getString(COL_HUMAN_VERIFIED).equals("1")?true:false);
  
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
