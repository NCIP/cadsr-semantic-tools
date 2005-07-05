package gov.nih.nci.ncicb.cadsr.loader.util;

public enum RunMode {
  GenerateReport, 
    Curator, 
    Reviewer;


  public String toString() {
    return name();
  }

}