/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */


package gov.nih.nci.ncicb.cadsr.semconn;


public class FileDoesNotExistException extends SemanticConnectorException{
    public FileDoesNotExistException(String msg){
        super(new Exception(msg));
    }
}
