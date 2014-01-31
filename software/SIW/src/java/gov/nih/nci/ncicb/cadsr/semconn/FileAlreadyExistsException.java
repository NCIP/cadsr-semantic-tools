

package gov.nih.nci.ncicb.cadsr.semconn;


public class FileAlreadyExistsException extends SemanticConnectorException{
    public FileAlreadyExistsException(String msg){
        super(msg, null);
    }
}
