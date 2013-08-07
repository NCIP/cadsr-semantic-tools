/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.validator;

public class MismatchDefByCodeError extends ValidationError
{
  public MismatchDefByCodeError(String message, Object rootCause) {
    super(message, rootCause);
  }
}