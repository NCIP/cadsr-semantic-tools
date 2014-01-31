package gov.nih.nci.ncicb.cadsr.loader.validator;

public class MismatchDefByCodeError extends ValidationError
{
  public MismatchDefByCodeError(String message, Object rootCause) {
    super(message, rootCause);
  }
}