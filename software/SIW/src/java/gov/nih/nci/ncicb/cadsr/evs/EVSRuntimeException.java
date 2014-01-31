package gov.nih.nci.ncicb.cadsr.evs;

public class EVSRuntimeException extends RuntimeException {

	public EVSRuntimeException() {
		super();
	}
	
	public EVSRuntimeException(Exception e) {
		super(e);
	}
	
	public EVSRuntimeException(String message, Exception e) {
		super(message, e);
	}
}
