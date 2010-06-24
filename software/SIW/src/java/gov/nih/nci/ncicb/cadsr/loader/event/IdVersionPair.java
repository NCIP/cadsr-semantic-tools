package gov.nih.nci.ncicb.cadsr.loader.event;

public class IdVersionPair {

  private String id;
  private Float version;

  public IdVersionPair(String id, Float version) {
    this.id = id;
    this.version = version;
  }

  public String getId() {
    return id;
  }

  public Float getVersion() {
    return version;
  }

}