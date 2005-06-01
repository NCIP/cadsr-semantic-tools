package gov.nih.nci.ncicb.cadsr.loader.parser;

public interface ElementWriter {

  public void write(ElementsList elements);

  public void setOutput(java.io.Writer writer);

}