package gov.nih.nci.ncicb.cadsr.loader.ui.event;

import java.util.EventListener;

public interface ViewChangeListener extends EventListener {

  public void viewChanged(ViewChangeEvent evt);

}

