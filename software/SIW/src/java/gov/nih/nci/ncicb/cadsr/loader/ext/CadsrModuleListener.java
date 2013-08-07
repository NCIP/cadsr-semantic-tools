/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ext;

  /**
   * implement if you want your class to use plug-in cadsrModule
   */
public interface CadsrModuleListener {

  /**
   * IoC setter
   */
  public void setCadsrModule(CadsrModule module);

}