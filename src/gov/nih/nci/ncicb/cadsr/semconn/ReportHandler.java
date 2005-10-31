package gov.nih.nci.ncicb.cadsr.semconn;

import com.infomata.data.*;

import org.apache.log4j.*;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 * @author MashettS
 *
 * @deprecated 10-Nov-2004 01:44:40 PM
 */
public final class ReportHandler {
  private static Logger log = Logger.getLogger(ReportHandler.class.getName());
  private String reportLocation;
  public ReportValidator m_ReportValidator;
  private DataFile read;
  private DataFile write;
  private File report;
  private EVSImpl evsImpl;
  private boolean sendEmail = false;
  private String fileName;

  /**
   * Constructor
   */
  public ReportHandler() {
    report = new File("EVSReport.csv");

    m_ReportValidator = new ReportValidator();

    evsImpl = new EVSImpl();

    fileName = "";

    //get properties
    //properties = new Configuration();
  }

  /**
   * Constructor
   */
  public ReportHandler(
    String xmiFileName,
    String outputCsv) {
    //fileName = xmiFileName.substring(0, xmiFileName.indexOf("."));
    fileName =
      outputCsv + "/" + "EVSReport_" +
      xmiFileName.substring(
        xmiFileName.lastIndexOf("/") + 1, xmiFileName.lastIndexOf(".")) +
      ".csv";

    report = new File(fileName);

    m_ReportValidator = new ReportValidator();

    evsImpl = new EVSImpl();

    //get properties
    //properties = new Configuration();
  }

  /**
   * check if the report exists or else create new report
   */
  public void createReport() throws Exception {
    //Everytime, rewrite report..
    //if exists,, delete
    if ((report != null) && (report.exists())) {
      report.delete();
    }

    report = new File(fileName);

    write = DataFile.createWriter("8859_2", true);

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
    }
    catch (IOException e) {
      log.error("Exception occured while creating report: " + e.getMessage());

      System.out.println(
        "Exception occured while creating header: " + e.getMessage());

      throw new Exception(
        "Exception occured while creating report: " + e.getMessage());
    }

    finally {
      write.close();
    }
  }

  /**
   * Reads report
   *
   * @return
   *
   * @throws Exception
   */
  public ArrayList readReport() throws Exception {
    //Reads report and puts records in the arraylist
    read = DataFile.createReader("8859_1");

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
    catch (IOException e) {
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
  public void writeReport(ArrayList evsList) throws Exception {
    //System.out.println("Writing report");
    write = DataFile.createWriter("8859_2", true);

    write.setDataFormat(new CSVFormat());

    ArrayList ob = new ArrayList();

    try {
      write.open(report);

      DataRow row = null;

      HashMap map = null;

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
      }
    }
    catch (IOException e) {
      log.error("Exception occured while writing report: " + e.getMessage());

      throw new Exception(
        "Exception occured while writing report: " + e.getMessage());
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
  public ArrayList processHandle(ArrayList modelList) throws Exception {
    ArrayList updateEntityList = null;

    sendEmail = false;

    //check whether the report exists
    if (doesReportExist()) {
      System.out.println("Report exists, reading report and validating..");

      //get entities from the report
      ArrayList reportList = readReport();

      //compare model and report
      m_ReportValidator.compare(modelList, reportList);

      //get updatedList after running m_ReportValidator.compare
      updateEntityList = m_ReportValidator.getUpdatedEntityList();

      //get newEntityList after running m_ReportValidator.compare
      ArrayList newEntityList = m_ReportValidator.getNewEntityList();

      //get newReportList to rewrite report after running m_ReportValidator.compare
      ArrayList newReportList = m_ReportValidator.getNewReportList();

      //get tagged values from EVS... only if there are any new entities
      if ((newEntityList != null) && (newEntityList.size() > 0)) {
        System.out.println("Getting values from EVS......");

        //get values from EVS
        ArrayList evsList = getTaggedValuesFromEvs(newEntityList);

        if (evsList != null) {
          sendEmail = true;
        }

        //update the newReportList with the evsList;
        getUpdatedList(newReportList, evsList);
      }

      //write new values to report
      createReport();

      System.out.println("Writing Report....");

      writeReport(newReportList);

      System.out.println("Writing Report.... COMPLETED");
    }

    else {
      System.out.println(
        "Report does not exists. Creating report.... PLEASE WAIT");

      //first get values from EVS
      System.out.println("Getting values from EVS......");

      ArrayList evsList = getTaggedValuesFromEvs(modelList);

      if (evsList != null) {
        sendEmail = true;
      }

      //debugging
      //testOutput(evsList);
      //create report
      createReport();

      //write report
      writeReport(evsList);

      updateEntityList = null;

      System.out.println("\nCreated Report.");
    }

    try {
      //if there are new entries.. send email to EVS folks
      if (sendEmail) {
        m_ReportValidator.notifyEVS(report.getPath());

        sendEmail = false;
      }
    }
    catch (Exception e) {
      log.error("Exception: " + e.getMessage());
    }

    //debugging
    //testOutput(updateEntityList);
    return updateEntityList;
  }

  /**
   * Returns true if the report exists
   */
  public boolean doesReportExist() {
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
  private ArrayList getTaggedValuesFromEvs(ArrayList entities)
    throws Exception {
    return evsImpl.getTaggedValues(entities);
  }

  /**
   * This method updates the newReportList with the values of evsList
   *
   * @param newReportList
   * @param evsList
   */
  private void getUpdatedList(
    ArrayList newReportList,
    ArrayList evsList) {
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

  /*********************************************************************************/

  ////debugging
  public void testOutput(ArrayList attList) {
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
