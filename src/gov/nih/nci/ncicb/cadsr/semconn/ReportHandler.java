package gov.nih.nci.ncicb.cadsr.semconn;

import com.infomata.data.CSVFormat;
import com.infomata.data.DataFile;
import com.infomata.data.DataRow;

import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author MashettS
 *
 * @deprecated
 */
public final class ReportHandler extends SubjectClass{
  private static Logger log = Logger.getLogger(ReportHandler.class.getName());

  private ReportValidator reportValidor;
  private EVSImpl evsImpl = null;

  private boolean sendEmail = false;

  /**
   * Constructor
   */
  public ReportHandler() {
  }


  public void addProgressListener(ProgressListener pl){
      super.addProgressListener(pl);
      if (reportValidor!=null){
          reportValidor.addProgressListener(pl);
      }
  }
    public void removeProgressListener(ProgressListener pl){
        super.removeProgressListener(pl);
        if (reportValidor!=null){
            reportValidor.removeProgressListener(pl);
        }
    }

  /**
   * check if the report exists or else create new report
   */
  private File createReport(String fileName, boolean override) 
  throws FileAlreadyExistsException, SemanticConnectorException {
    File report = new File(fileName);
    if ((report != null) && (report.exists()) && !override) {
      throw new FileAlreadyExistsException("Report " + fileName + " already exists.");
    }

    if ((report != null) && (report.exists()) && override) {
      report.delete();
    }

    DataFile write = DataFile.createWriter("8859_2", true);
    write.setDataFormat(new CSVFormat());

    //first line is column header
    write.containsHeader(true);

    try {
      write.open(report);
      DataRow row = write.next();
      //add header
      ArrayList columns = Configuration.getReportColumns();

      for (int i = 0; i < columns.size(); i++) {
        row.add((String) columns.get(i));
      }
    }//TODO - exception handling here
    catch (IOException e) {
      log.error("Exception occured while creating report: " + e.getMessage());

      System.out.println(
        "Exception occured while creating header: " + e.getMessage());

      throw new SemanticConnectorException("Could not create report." , e);
    }
    finally {
      write.close();
    }
    return report;
  }

  /**
   * Reads report
   *
   * @return
   *
   * @throws Exception
   */
  private List readReport(String csvReportFileName) throws Exception {
    //Reads report and puts records in the arraylist
    File report = new File(csvReportFileName);
    DataFile read = DataFile.createReader("8859_1");
    read.setDataFormat(new CSVFormat());
    ArrayList evsValues = new ArrayList();

    try {
      read.open(report);
      HashMap map = null;
      ArrayList columns = Configuration.getReportColumns();

    //read first row, to get out the header
      DataRow row = read.next();

      for (row = read.next(); row != null; row = read.next()) {
        map = new HashMap();

        for (int i = 0; i < columns.size(); i++) {
          String column = (String) columns.get(i);
          String value = row.getString(i);
          map.put(column, value);
        }
        //add map to the arraylist
        evsValues.add(map);
      }
    }
    catch (Exception e) {
      log.error("Exception occured while reading report: " + e.getMessage());
      throw new Exception(
        "Exception occured while reading report: " + e.getMessage());
    }
    finally {
      read.close();
    }
    return evsValues;
  }

  /**
   * Writes report
   *
   * @param evsList
   *
   * @throws Exception
   */
  private void writeReport(List evsList, String csvReportFileName, boolean override) 
  throws SemanticConnectorException {
  
    System.out.println("Creating report");
    File report = createReport(csvReportFileName, override);
    
    System.out.println("Writing report");
    DataFile write = DataFile.createWriter("8859_2", true);
    write.setDataFormat(new CSVFormat());
    ArrayList ob = new ArrayList();

    try {
      DataRow row = null;
      HashMap map = null;
      
      write.open(report);

      //Iterate through the list
      for (int i = 0; i < evsList.size(); i++) {
        map = (HashMap) evsList.get(i);

        row = write.next();
        String UMLClass =
          getValue((String) map.get(Configuration.getUMLClassCol()));
        row.add(UMLClass);
        row.add(getValue((String) map.get(Configuration.getUMLEntityCol())));
        row.add(
          getValue((String) map.get(Configuration.getUMLDescriptionCol())));
        row.add(getValue((String) map.get(Configuration.getConceptName())));
        row.add(
          getValue((String) map.get(Configuration.getPreferredNameCol())));
        row.add(
          getValue((String) map.get(Configuration.getClassificationCol())));
        row.add(getValue((String) map.get(Configuration.getConceptCodeCol())));
        row.add(getValue((String) map.get(Configuration.getDefinitionCol())));
        row.add(
          getValue((String) map.get(Configuration.getDefinitionSourceCol())));
        row.add(getValue((String) map.get(Configuration.getModifiedDateCol())));
        row.add(getValue((String) map.get(Configuration.getVerifiedFlagCol())));
      }//end of for
    }
    catch (IOException e) {
      log.error("Exception occured while writing report: " + e.getMessage());
      throw new SemanticConnectorException("Could not write report.", e);
    }
    finally {
      write.close();
    }
  }

  /**
   * Start point of the flow...
   *
   * @param modelList
   *
   * @return
   */
  public void generateEVSReport(List modelList, String reportFileName, boolean override) 
  throws FileAlreadyExistsException, SemanticConnectorException {

    //sanity check
    if (reportFileName == null || reportFileName.length()==0){
        log.warn("File name is empty. No report is generated");
        return;
    }
    if (modelList == null || modelList.size()==0){
        log.warn("Model does not exist. No report is generated.");
        System.out.println("Model does not exist. No report is generated.");
        return;
    }
    
    //verify report already exists before moving to expensive EVS search.
    File report = new File(reportFileName);
    if (report.exists() && !override){
        throw new FileAlreadyExistsException("Report " + reportFileName + " already exists.");
    }
    
    //init
    sendEmail = false;

    try{
      //first get values from EVS
      log.info("Getting values from EVS......");

      List evsList = getTaggedValuesFromEvs(modelList);

      if (evsList != null) {
        sendEmail = true;
      }

      //debugging
      //testOutput(evsList);
      //create report
      //write report
      writeReport(evsList, reportFileName, override);

      System.out.println("\nReport named " + reportFileName + " is generated.");
      log.info("Report named " + reportFileName + " is generated.");

        //send email if needed.
        //when should we notify EVS? - TODO
        //if there are new entries.. send email to EVS folks
          if (sendEmail) {
            getReportValidator().notifyEVS(report.getPath());
    
            sendEmail = false;
          }
    }catch (SemanticConnectorException e){
        throw e;
    }
    catch (Exception e){
        throw new SemanticConnectorException(" Could not generate EVS report.", e);
    }

    //debugging
    //testOutput(updateEntityList);
    return;
  }

    /**
     * @param modelList
     * @return
     */
     public List getUpdatedEntities(List modelList, String reportFileName) 
     throws FileDoesNotExistException, Exception{
      //sanity check
      if (modelList==null || modelList.size()==0){
          log.warn("Model does not exist. No annotation is needed.");
          throw new Exception("Model does not exist. No annotation is needed.");
      }
      
      if (reportFileName==null || reportFileName.length()==0){
          log.warn("Report file name is empty. ");
          throw new FileDoesNotExistException("Report file name is empty. ");
      }
      
      List updateEntityList = null;
      sendEmail = false;
      
      //check if report file already exists;
      File report = new File(reportFileName);      
      if (!doesReportExist(report)){
          log.warn("Report file does not exist. ");
          throw new FileDoesNotExistException("Report file does not exist. ");
      }

      //report exists
      System.out.println("Report exists, reading report and validating..");
      log.info("Report exists, reading report and validating..");

      //get entities from the report
      List reportList = readReport(reportFileName);

      //compare model and report 
      ReportValidator reportValidator = getReportValidator();
      reportValidator.addProgressListeners(getProgressListeners());
      reportValidator.compare(modelList, reportList);
      //not human verified items...
      

      //get updatedList after running reportValidator.compare
      updateEntityList = reportValidator.getUpdatedEntityList();

      /* write report is not neccessary
       //get newEntityList after running reportValidator.compare
      List newEntityList = reportValidator.getNewEntityList();

      //get newReportList to rewrite report after running reportValidator.compare
      List newReportList = reportValidator.getNewReportList();

      //get tagged values from EVS... only if there are any new entities
      if ((newEntityList != null) && (newEntityList.size() > 0)) {
          System.out.println("Getting values from EVS......");

          //get values from EVS
          List evsList = getTaggedValuesFromEvs(newEntityList);

          if (evsList != null) {
            sendEmail = true;
          }

          //update the newReportList with the evsList;
          getUpdatedList(newReportList, evsList);
      }//end of new entiy

      System.out.println("Writing Report....");
      log.info("Writing Report....");

      writeReport(newReportList,reportFileName);

      System.out.println("Writing Report.... COMPLETED");
      log.info("Writing Report.....COMPLETED");
      */

      //send email if needed.
      try {
        //if there are new entries.. send email to EVS folks
        if (sendEmail) {
          getReportValidator().notifyEVS(report.getPath());
          sendEmail = false;
        }
      }
      catch (Exception e) {
        log.error("Exception: " + e.getMessage());
        throw e;
      }

      //debugging
      //testOutput(updateEntityList);
      return updateEntityList;
    }




  /**
   * Returns true if the report exists
   */
  private boolean doesReportExist(File report) {
    if ((report != null) && (report.exists())) {
      return true;
    }
    else
    {
      return false;
    }
  }

  /**
   * checks the value provided, if it is null, then returns empty string
   *
   * @param value
   *
   * @return
   */
  private String getValue(String value) {
    String emptyString = "";

    String returnString = null;

    if (value != null) {
      if (value.equalsIgnoreCase("null")) {
        returnString = emptyString;
      }

      else
      {
        returnString = value;
      }
    }

    else
    {
      returnString = emptyString;
    }

    return returnString;
  }

  /**
   * Gets entity values(tag values) from cabio(evs)
   *
   * @param entities
   *
   * @return
   */
  private List getTaggedValuesFromEvs(List entities)
    throws Exception {
    return getEvsImpl().getTaggedValues(entities);
  }

  /**
   * This method updates the newReportList with the values of evsList
   *
   * @param newReportList
   * @param evsList
   */
  private void getUpdatedList(
    List newReportList,
    List evsList) {
    HashMap map = null;

    HashMap evsMap = null;

    //Iterate through the newReportList
    for (int i = 0; i < newReportList.size(); i++) {
      map = (HashMap) newReportList.get(i);

      //Iterate through the evsList
      for (int j = 0; j < evsList.size(); j++) {
        evsMap = (HashMap) evsList.get(j);

        if (
          ((map.get(Configuration.getUMLClassCol())).equals(
                evsMap.get(Configuration.getUMLClassCol()))) &&
              ((map.get(Configuration.getUMLEntityCol())).equals(
                evsMap.get(Configuration.getUMLEntityCol())))) {
          //Found the record
          map.put(
            Configuration.getClassificationCol(),
            evsMap.get(Configuration.getClassificationCol()));

          map.put(
            Configuration.getConceptCodeCol(),
            evsMap.get(Configuration.getConceptCodeCol()));

          map.put(
            Configuration.getDefinitionCol(),
            evsMap.get(Configuration.getDefinitionCol()));

          map.put(
            Configuration.getDefinitionSourceCol(),
            evsMap.get(Configuration.getDefinitionSourceCol()));

          map.put(
            Configuration.getPreferredNameCol(),
            evsMap.get(Configuration.getPreferredNameCol()));
        }
      }
    }
  }
  
  
  private ReportValidator getReportValidator(){
      if (reportValidor == null){
          ReportValidator rv = new ReportValidator();
          rv.addProgressListeners(getProgressListeners());
          return rv;
      }else{
          return reportValidor;
      }
  }
  
  
  private EVSImpl getEvsImpl(){
      if (evsImpl == null){
          EVSImpl evs = new EVSImpl();
          evs.addProgressListeners(getProgressListeners());
          return evs;
      }
      else{
          return evsImpl;
      }
  }

  /*********************************************************************************/

  ////debugging
  private void testOutput(ArrayList attList) {
    for (int i = 0; i < attList.size(); i++) {
      HashMap map = (HashMap) attList.get(i);

      System.out.println("******** List of values***********");

      for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
        String key = (String) iter.next();

        String value = (String) map.get(key);

        System.out.println(key + " = " + value);
      }
    }
  }
}
