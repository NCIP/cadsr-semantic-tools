package gov.nih.nci.ncicb.cadsr.evs;

public class EVSException extends Exception {

	public EVSException() {
		super();
	}
	
	public EVSException(Exception e) {
		super(e);
	}
	
	public EVSException(String message, Exception e) {
		super(message, e);
	}
}
