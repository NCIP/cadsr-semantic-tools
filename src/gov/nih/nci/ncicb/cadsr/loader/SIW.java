package gov.nih.nci.ncicb.cadsr.loader;

import gov.nih.nci.ncicb.cadsr.loader.util.BeansAccessor;

import javax.swing.UIManager;

public class SIW {

  /**
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

    
    UMLLoaderGUI gui = BeansAccessor.getUMLLoaderGUI();
    
    gui.start();

  }
}