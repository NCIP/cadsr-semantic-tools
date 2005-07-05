package gov.nih.nci.ncicb.cadsr.loader.ui.event;

import java.util.EventListener;

public interface NavigationListener extends EventListener {

  public void navigate(NavigationEvent event);

}
