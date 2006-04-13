package gov.nih.nci.ncicb.cadsr.loader.ui;

/**
 * Thrown by an Applyable Panel if, for some reason, it could not apply.
 */
public class ApplyException extends Exception {

  public ApplyException(String msg) {
    super(msg);
  }
  
  public ApplyException() {
    super();
  }

}

