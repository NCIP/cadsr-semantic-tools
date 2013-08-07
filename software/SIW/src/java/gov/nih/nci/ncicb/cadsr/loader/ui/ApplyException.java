/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ui;

/**
 * Thrown by an Applyable Panel if, for some reason, it could not apply.
 */
public class ApplyException extends Exception {

  public ApplyException(String msg) {
    super(msg);
  }
  
  public ApplyException() {
    super();
  }

}

