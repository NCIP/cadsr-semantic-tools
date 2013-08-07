/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.event;

public class IdVersionPair {

  private String id;
  private Float version;

  public IdVersionPair(String id, Float version) {
    this.id = id;
    this.version = version;
  }

  public String getId() {
    return id;
  }

  public Float getVersion() {
    return version;
  }

}