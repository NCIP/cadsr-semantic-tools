package gov.nih.nci.ncicb.cadsr.loader.ui.event;

public class ViewChangeEvent {

  public static final int VIEW_CONCEPTS = 1;
  public static final int VIEW_ASSOCIATION = 2;

  private int type;
  private Object viewObject;
  private boolean inNewTab = false;


  public ViewChangeEvent(int type) {
    this.type = type;
  }

  public int getType() {
    return type;
  }

  public void setViewObject(Object viewObject) {
    this.viewObject = viewObject;
  }

  public Object getViewObject() {
    return viewObject;
  }

  public boolean getInNewTab() {
    return inNewTab;
  }

  public void setInNewTab(boolean b) {
    inNewTab = b;
  }

}
