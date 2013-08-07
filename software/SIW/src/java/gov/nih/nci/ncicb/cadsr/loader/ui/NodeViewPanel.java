/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.ui.event.*;
import gov.nih.nci.ncicb.cadsr.loader.event.*;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public interface NodeViewPanel extends NavigationListener {

  public void addReviewListener(ReviewListener listener);

  public void addNavigationListener(NavigationListener listener);
  
  public void addElementChangeListener(ElementChangeListener listener);

  public void addPropertyChangeListener(PropertyChangeListener l);

  public String getName();
  public void setName(String name);

  public void updateNode(UMLNode node);

}