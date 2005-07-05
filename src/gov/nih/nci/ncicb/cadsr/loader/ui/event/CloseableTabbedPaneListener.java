package gov.nih.nci.ncicb.cadsr.loader.ui.event;

import java.util.EventListener;

public interface CloseableTabbedPaneListener extends EventListener {

  public boolean closeTab(int i);

}