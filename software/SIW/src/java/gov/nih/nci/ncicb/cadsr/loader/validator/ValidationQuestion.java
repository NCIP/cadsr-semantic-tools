package gov.nih.nci.ncicb.cadsr.loader.validator;

/**
 *
 */
public class ValidationQuestion extends ValidationItem {

  private String[] options;

  private int selection;

  /**
   * Get the Selection value.
   * @return the Selection value.
   */
  public int getSelection() {
    return selection;
  }

  /**
   * Set the Selection value.
   * @param newSelection The new Selection value.
   */
  public void setSelection(int newSelection) {
    this.selection = newSelection;
  }

  public ValidationQuestion(String message, Object rootCause, String[] options) {
    super(message, rootCause);
    
    this.options = options;
  }

  public String[] getOptions() {
    return options;
  }

}