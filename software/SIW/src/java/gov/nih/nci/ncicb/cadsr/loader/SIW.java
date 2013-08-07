/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

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

    
    UMLLoaderGUI gui = BeansAccessor.getSiw();
    
    gui.start();

  }
}