/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.evs;

public class EVSException extends Exception {

	public EVSException() {
		super();
	}
	
	public EVSException(Exception e) {
		super(e);
	}
	
	public EVSException(String message, Exception e) {
		super(message, e);
	}
}
