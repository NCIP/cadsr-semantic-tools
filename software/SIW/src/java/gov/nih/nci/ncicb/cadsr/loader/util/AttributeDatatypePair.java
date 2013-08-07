/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.util;

public class AttributeDatatypePair {

  private String attributeName, datatype;

  public AttributeDatatypePair(String attributeName, String datatype) {
    this.attributeName = attributeName;
    this.datatype = datatype;
  }

  public String getAttributeName() {
    return attributeName;
  }
  public String getDatatype() {
    return datatype;
  }

}