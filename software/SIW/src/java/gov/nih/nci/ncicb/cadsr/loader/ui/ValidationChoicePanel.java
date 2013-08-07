/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ui;


import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.BeansAccessor;
import gov.nih.nci.ncicb.cadsr.loader.validator.UMLValidator;

import gov.nih.nci.ncicb.cadsr.loader.validator.Validator;

import java.awt.GridBagLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.swing.*;

public class ValidationChoicePanel extends JPanel {
  
  private List<JCheckBox> checkboxes = new ArrayList<JCheckBox>();

  public ValidationChoicePanel() {
    setLayout(new GridBagLayout());

    UMLValidator mainValidator = BeansAccessor.getSiwValidator();
    int i=0;

    for(Validator validator : mainValidator.getValidators()) {
      JCheckBox cb = new JCheckBox(validator.getClass().getName());
      UIUtil.insertInBag(this, cb, 0, i++);
      
      checkboxes.add(cb);
    }
    
  }

  public Map<String, Boolean> getValidatorChoices() {
    Map<String, Boolean> result = new HashMap<String, Boolean>();
    for(JCheckBox cb : checkboxes){
      result.put(cb.getText(), cb.isSelected());
    }
    return result;
  }
}
