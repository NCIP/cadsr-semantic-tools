package gov.nih.nci.ncicb.cadsr.loader.ui.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Component;

public class UIUtil {

  private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

  public static void putToCenter(Component comp) {
    comp.setLocation((screenSize.width - comp.getSize().width) / 2, (screenSize.height - comp.getSize().height) / 2);
  }


}