package gov.nih.nci.ncicb.cadsr.loader.util;

public class AttributeDatatypePair {

  private String attributeName, datatype;

  public AttributeDatatypePair(String attributeName, String datatype) {
    this.attributeName = attributeName;
    this.datatype = datatype;
  }

  public String getAttributeName() {
    return attributeName;
  }
  public String getDatatype() {
    return datatype;
  }

}