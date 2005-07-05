package gov.nih.nci.ncicb.cadsr.loader.parser;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

public interface ElementWriter {

  public void write(ElementsLists elements);

//  public void setOutput(java.io.Writer writer);
//  
  public void setOutput(String url);

}
