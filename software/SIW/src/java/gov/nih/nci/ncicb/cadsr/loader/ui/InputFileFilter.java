/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ui;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class InputFileFilter extends FileFilter {
  String extension = "";
  InputFileFilter(String extension) {
    this.extension = extension;
  }
  public boolean accept(File f) {
    if (f.isDirectory()) {
      return true;
    }                
    return f.getName().endsWith("." + extension);
  }
  public String getDescription() {
    return extension.toUpperCase() + " Files";
  }
};
