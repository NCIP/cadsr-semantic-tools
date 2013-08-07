/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.event;

import org.apache.log4j.spi.LoggingEvent;


public interface LogListener {
  public void append(LoggingEvent loggingEvent);
}