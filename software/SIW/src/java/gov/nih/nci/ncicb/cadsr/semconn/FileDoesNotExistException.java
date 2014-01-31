

package gov.nih.nci.ncicb.cadsr.semconn;


public class FileDoesNotExistException extends SemanticConnectorException{
    public FileDoesNotExistException(String msg){
        super(new Exception(msg));
    }
}
