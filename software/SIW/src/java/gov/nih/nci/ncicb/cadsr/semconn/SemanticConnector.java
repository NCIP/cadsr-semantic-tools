/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.semconn;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.parser.ElementWriter;
import gov.nih.nci.ncicb.cadsr.loader.parser.Parser;

import gov.nih.nci.ncicb.cadsr.loader.parser.ParserException;
public class SemanticConnector 
{
  Parser parser;
  ElementWriter writer; 
  
  public SemanticConnector()
  {
  }
  
  public void firstPass(String inputXmi, String outputXmi) throws ParserException
  {
    parser.parse(inputXmi);
    writer.setOutput(outputXmi);
    writer.write(ElementsLists.getInstance());
  }
  
  public void setParser(Parser parser) 
  {
    this.parser = parser;
  }
  
  public void setWriter(ElementWriter writer) 
  {
    this.writer = writer;
  }
  
  public void setProgressListener(ProgressListener listener) 
  {
    parser.addProgressListener(listener);
    writer.setProgressListener(listener);
  }
}