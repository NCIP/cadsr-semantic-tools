/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.semconn;

import org.apache.log4j.*;

import java.io.*;

import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;


/**
 * <!-- LICENSE_TEXT_START -->
 * <!-- LICENSE_TEXT_END -->
 * Copyright 2001-2004 SAIC. Copyright 2001-2003 SAIC. This software was
 * developed in conjunction with the National Cancer Institute, and so to the
 * extent government employees are co-authors, any rights in such works shall
 * be subject to Title 17 of the United States Code, section 105.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the disclaimer of Article 3, below.
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution. 2. The end-user
 * documentation included with the redistribution, if any, must include the
 * following acknowledgment: "This product includes software developed by the
 * SAIC and the National Cancer Institute." If no such end-user documentation
 * is to be included, this acknowledgment shall appear in the software itself,
 * wherever such third-party acknowledgments normally appear. 3. The names
 * "The National Cancer Institute", "NCI" and "SAIC" must not be used to
 * endorse or promote products derived from this software. 4. This license
 * does not authorize the incorporation of this software into any third party
 * proprietary programs. This license does not authorize the recipient to use
 * any trademarks owned by either NCI or SAIC-Frederick. 5. THIS SOFTWARE IS
 * PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER
 * INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * @author caBIO Team
 * @version 1.0
 */
public class Configuration {
  private static ArrayList columnsList;
  private static ArrayList tagList;
  private static String vocabularyName;
  private static int limit = 5;
  private static String classTag;
  private static String attributeTag;
  private static String sender;
  private static String recipient;
  private static String MailServerHost;
  private static String MailServerPort;
  private static String subject;
  private static String message;
  private static String sendEmail;
  private static String serverURL;

  //don't forget to set these names from the config file.... if there are any changes to the config file..
  private static String conceptCode = "ConceptCode";
  private static String classification = "Classification";
  private static String preferredName = "ConceptPreferredName";
  private static String definition = "ConceptDefinition";
  private static String definitionSource = "ConceptDefinitionSource";
  private static String umlClass = "UMLClass";
  private static String umlEntity = "UMLEntity";
  private static String modifiedDate = "ModifiedDate";
  private static String verifiedFlag = "HumanVerified";
  private static String description = "UMLDescription";
  private static String conceptName = "ConceptName";
  private static Logger log = Logger.getLogger(Configuration.class.getName());

  /*public Configuration(){
     loadProperties();
     }*/
  public static void loadProperties() {
    //System.out.println("Configuration  - loadProperties...");
    columnsList = new ArrayList();

    tagList = new ArrayList();

    try {
      Properties _properties = new Properties();

      System.out.println("Loading properties from -  semantic properties file");

      _properties.load(
        Thread.currentThread().getContextClassLoader().getResourceAsStream(
          "semantic.properties"));

      String columns = (String) _properties.getProperty("REPORTCOLUMNS");

      //System.out.println("columns: "+columns);
      StringTokenizer st = new StringTokenizer(columns, ",");

      while (st.hasMoreTokens()) {
        columnsList.add(st.nextToken());
      }

      //set each column value
      for (int i = 0; i < columnsList.size(); i++) {
        String col = (String) columnsList.get(i);

        if (col.equals(conceptCode)) {
          conceptCode = col;
        }

        else if (col.equals(classification)) {
          classification = col;
        }

        else if (col.equals(description)) {
          description = col;
        }

        else if (col.equals(preferredName)) {
          preferredName = col;
        }

        else if (col.equals(definition)) {
          definition = col;
        }

        else if (col.equals(definitionSource)) {
          definitionSource = col;
        }

        else if (col.equals(umlClass)) {
          umlClass = col;
        }

        else if (col.equals(umlEntity)) {
          umlEntity = col;
        }

        else if (col.equals(modifiedDate)) {
          modifiedDate = col;
        }

        else if (col.equals(verifiedFlag)) {
          verifiedFlag = col;
        }

        else if (col.equals(conceptName)) {
          conceptName = col;
        }
      }

      String tags = (String) _properties.getProperty("TAGNAMES");

      st = new StringTokenizer(tags, ",");

      while (st.hasMoreTokens()) {
        tagList.add(st.nextToken());
      }

      //get classification tags for class and attribute
      classTag = (String) _properties.getProperty("CLASSTAG");

      attributeTag = (String) _properties.getProperty("ATTRIBUTETAG");

      //get vocabularyName
      vocabularyName = (String) _properties.getProperty("VOCABULARYNAME");

      //get limit
      String limitString = (String) _properties.getProperty("LIMIT");

      if (limitString != null) {
        limit = (new Integer(limitString)).intValue();
      }

      //get email properties
      sender = (String) _properties.getProperty("SENDER");

      recipient = (String) _properties.getProperty("RECIPIENT");

      MailServerHost = (String) _properties.getProperty("MAILSERVERHOST");

      MailServerPort = (String) _properties.getProperty("MAILSERVERPORT");

      subject = (String) _properties.getProperty("SUBJECT");

      message = (String) _properties.getProperty("MESSAGE");

      sendEmail = (String) _properties.getProperty("SENDEMAIL");

      serverURL = (String) _properties.getProperty("SERVER_URL");

      //System.out.println("- setting serverURL = "+serverURL);
    }
    catch (IOException e) {
      log.error("IOException: " + e.getMessage());

      System.out.println("IOException occured: " + e.getMessage());
    }

    catch (Exception ex) {
      log.error("Exception: " + ex.getMessage());

      ex.printStackTrace();

      System.out.println("Exception - " + ex.getMessage());
    }
  }

  public void setReportColumns(ArrayList cols) {
    columnsList = cols;
  }

  public void setTagNames(ArrayList tags) {
    tagList = tags;
  }

  public static ArrayList getReportColumns() {
    return columnsList;
  }

  public static ArrayList getTagNames() {
    return tagList;
  }

  public static String getConceptCodeCol() {
    return conceptCode;
  }

  public static String getClassificationCol() {
    return classification;
  }

  public static String getPreferredNameCol() {
    return preferredName;
  }

  public static String getDefinitionCol() {
    return definition;
  }

  public static String getDefinitionSourceCol() {
    return definitionSource;
  }

  public static String getUMLClassCol() {
    return umlClass;
  }

  public static String getUMLEntityCol() {
    return umlEntity;
  }

  public static String getUMLDescriptionCol() {
    return description;
  }

  public static String getVerifiedFlagCol() {
    return verifiedFlag;
  }

  public static String getModifiedDateCol() {
    return modifiedDate;
  }

  public static String getVocabularyName() {
    return vocabularyName;
  }

  public static String getConceptName() {
    return conceptName;
  }

  public static int getLimit() {
    return limit;
  }

  public static String getClassTag() {
    return classTag;
  }

  public static String getAttributeTag() {
    return attributeTag;
  }

  public static String getSender() {
    return sender;
  }

  public static String getRecipient() {
    return recipient;
  }

  public static String getMailServerHost() {
    return MailServerHost;
  }

  public static String getMailServerPort() {
    return MailServerPort;
  }

  public static String getSubject() {
    return subject;
  }

  public static String getMessage() {
    return message;
  }

  public static String getSendEmailFlag() {
    return sendEmail;
  }

  public static String getServerURL() {
    return serverURL;
  }
}
