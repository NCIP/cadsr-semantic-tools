/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

/*
 * Copyright 2000-2003 Oracle, Inc. This software was developed in conjunction with the National Cancer Institute, and so to the extent government employees are co-authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105.
 *
 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the disclaimer of Article 3, below. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 2. The end-user documentation included with the redistribution, if any, must include the following acknowledgment:
 *
 * "This product includes software developed by Oracle, Inc. and the National Cancer Institute."
 *
 * If no such end-user documentation is to be included, this acknowledgment shall appear in the software itself, wherever such third-party acknowledgments normally appear.
 *
 * 3. The names "The National Cancer Institute", "NCI" and "Oracle" must not be used to endorse or promote products derived from this software.
 *
 * 4. This license does not authorize the incorporation of this software into any proprietary programs. This license does not authorize the recipient to use any trademarks owned by either NCI or Oracle, Inc.
 *
 * 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, ORACLE, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 */
package gov.nih.nci.ncicb.cadsr.loader.ui;

import java.beans.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * The model for the Wizard component, which tracks the text, icons, and enabled state
 * of each of the buttons, as well as the current panel that is displayed.
 */
public class WizardModel {

    /**
     * Identification string for the current panel.
     */    
    public static final String CURRENT_PANEL_DESCRIPTOR_PROPERTY = "currentPanelDescriptorProperty";
    
    /**
     * Property identification String for the Back button's text
     */    
    public static final String BACK_BUTTON_TEXT_PROPERTY = "backButtonTextProperty";
    /**
     * Property identification String for the Back button's icon
     */    
    public static final String BACK_BUTTON_ICON_PROPERTY = "backButtonIconProperty";
    /**
     * Property identification String for the Back button's enabled state
     */    
    public static final String BACK_BUTTON_ENABLED_PROPERTY = "backButtonEnabledProperty";

    /**
     * Property identification String for the Next button's text
     */    
    public static final String NEXT_BUTTON_TEXT_PROPERTY = "nextButtonTextProperty";
    /**
     * Property identification String for the Next button's icon
     */    
    public static final String NEXT_BUTTON_ICON_PROPERTY = "nextButtonIconProperty";
    /**
     * Property identification String for the Next button's enabled state
     */    
    public static final String NEXT_BUTTON_ENABLED_PROPERTY = "nextButtonEnabledProperty";
    
    /**
     * Property identification String for the Cancel button's text
     */    
    public static final String CANCEL_BUTTON_TEXT_PROPERTY = "cancelButtonTextProperty";
    /**
     * Property identification String for the Cancel button's icon
     */    
    public static final String CANCEL_BUTTON_ICON_PROPERTY = "cancelButtonIconProperty";
    /**
     * Property identification String for the Cancel button's enabled state
     */    
    public static final String CANCEL_BUTTON_ENABLED_PROPERTY = "cancelButtonEnabledProperty";
    
    private WizardPanelDescriptor currentPanel;
    
    private HashMap panelHashmap;
    
    private HashMap buttonTextHashmap;
    private HashMap buttonIconHashmap;
    private HashMap buttonEnabledHashmap;
    
    private PropertyChangeSupport propertyChangeSupport;
    
    
    /**
     * Default constructor.
     */    
    public WizardModel() {
        
        panelHashmap = new HashMap();
        
        buttonTextHashmap = new HashMap();
        buttonIconHashmap = new HashMap();
        buttonEnabledHashmap = new HashMap();
        
        propertyChangeSupport = new PropertyChangeSupport(this);

    }
    
    /**
     * Returns the currently displayed WizardPanelDescriptor.
     * @return The currently displayed WizardPanelDescriptor
     */    
    public WizardPanelDescriptor getCurrentPanelDescriptor() {
        return currentPanel;
    }
    
    /**
     * Registers the WizardPanelDescriptor in the model using the Object-identifier specified.
     * @param id Object-based identifier
     * @param descriptor WizardPanelDescriptor that describes the panel
     */    
    public void registerPanel(Object id, WizardPanelDescriptor descriptor) {
        
        //  Place a reference to it in a hashtable so we can access it later
        //  when it is about to be displayed.
        
        panelHashmap.put(id, descriptor);
        
    }  

  /** 
   * returns a registered panel descriptor based on its id
   */
  public WizardPanelDescriptor getPanelDescriptor(Object id) {
    return (WizardPanelDescriptor)panelHashmap.get(id);
  }
    
    /**
     * Sets the current panel to that identified by the Object passed in.
     * @param id Object-based panel identifier
     * @return boolean indicating success or failure
     */    
    public boolean setCurrentPanel(Object id) {

        //  First, get the hashtable reference to the panel that should
        //  be displayed.
        
        WizardPanelDescriptor nextPanel =
            (WizardPanelDescriptor)panelHashmap.get(id);
        
        //  If we couldn't find the panel that should be displayed, return
        //  false.
        
        if (nextPanel == null)
            return false;   

        WizardPanelDescriptor oldPanel = currentPanel;
        currentPanel = nextPanel;
        
        firePropertyChange(CURRENT_PANEL_DESCRIPTOR_PROPERTY, oldPanel, currentPanel);
        
        return true;
        
    }

    public Object getBackButtonText() {
        return buttonTextHashmap.get(BACK_BUTTON_TEXT_PROPERTY);
    }
    
    public void setBackButtonText(Object newText) {
        
        Object oldText = getBackButtonText();        
        if (!newText.equals(oldText)) {
            buttonTextHashmap.put(BACK_BUTTON_TEXT_PROPERTY, newText);
            firePropertyChange(BACK_BUTTON_TEXT_PROPERTY, oldText, newText);
        }
    }

    public Object getNextButtonText() {
        return buttonTextHashmap.get(NEXT_BUTTON_TEXT_PROPERTY);
    }
    
    public void setNextButtonText(Object newText) {
        
        Object oldText = getNextButtonText();        
        if (!newText.equals(oldText)) {
            buttonTextHashmap.put(NEXT_BUTTON_TEXT_PROPERTY, newText);
            firePropertyChange(NEXT_BUTTON_TEXT_PROPERTY, oldText, newText);
        }
    }

    public Object getCancelButtonText() {
        return buttonTextHashmap.get(CANCEL_BUTTON_TEXT_PROPERTY);
    }
    
    public void setCancelButtonText(Object newText) {
        
        Object oldText = getCancelButtonText();        
        if (!newText.equals(oldText)) {
            buttonTextHashmap.put(CANCEL_BUTTON_TEXT_PROPERTY, newText);
            firePropertyChange(CANCEL_BUTTON_TEXT_PROPERTY, oldText, newText);
        }
    } 
    
    public Icon getBackButtonIcon() {
        return (Icon)buttonIconHashmap.get(BACK_BUTTON_ICON_PROPERTY);
    }
    
    public void setBackButtonIcon(Icon newIcon) {
        
        Object oldIcon = getBackButtonIcon();        
        if (!newIcon.equals(oldIcon)) {
            buttonIconHashmap.put(BACK_BUTTON_ICON_PROPERTY, newIcon);
            firePropertyChange(BACK_BUTTON_ICON_PROPERTY, oldIcon, newIcon);
        }
    }

    public Icon getNextButtonIcon() {
        return (Icon)buttonIconHashmap.get(NEXT_BUTTON_ICON_PROPERTY);
    }
    
    public void setNextButtonIcon(Icon newIcon) {
        
        Object oldIcon = getNextButtonIcon();        
        if (!newIcon.equals(oldIcon)) {
            buttonIconHashmap.put(NEXT_BUTTON_ICON_PROPERTY, newIcon);
            firePropertyChange(NEXT_BUTTON_ICON_PROPERTY, oldIcon, newIcon);
        }
    }

    public Icon getCancelButtonIcon() {
        return (Icon)buttonIconHashmap.get(CANCEL_BUTTON_ICON_PROPERTY);
    }
    
    public void setCancelButtonIcon(Icon newIcon) {
        
        Icon oldIcon = getCancelButtonIcon();        
        if (!newIcon.equals(oldIcon)) {
            buttonIconHashmap.put(CANCEL_BUTTON_ICON_PROPERTY, newIcon);
            firePropertyChange(CANCEL_BUTTON_ICON_PROPERTY, oldIcon, newIcon);
        }
    } 
        
    
    public Boolean getBackButtonEnabled() {
        return (Boolean)buttonEnabledHashmap.get(BACK_BUTTON_ENABLED_PROPERTY);
    }
    
    public void setBackButtonEnabled(Boolean newValue) {
        
        Boolean oldValue = getBackButtonEnabled();        
        if (newValue != oldValue) {
            buttonEnabledHashmap.put(BACK_BUTTON_ENABLED_PROPERTY, newValue);
            firePropertyChange(BACK_BUTTON_ENABLED_PROPERTY, oldValue, newValue);
        }
    }

    public Boolean getNextButtonEnabled() {
        return (Boolean)buttonEnabledHashmap.get(NEXT_BUTTON_ENABLED_PROPERTY);
    }
    
    public void setNextButtonEnabled(Boolean newValue) {
        
        Boolean oldValue = getNextButtonEnabled();        
        if (newValue != oldValue) {
            buttonEnabledHashmap.put(NEXT_BUTTON_ENABLED_PROPERTY, newValue);
            firePropertyChange(NEXT_BUTTON_ENABLED_PROPERTY, oldValue, newValue);
        }
    }
    
    public Boolean getCancelButtonEnabled() {
        return (Boolean)buttonEnabledHashmap.get(CANCEL_BUTTON_ENABLED_PROPERTY);
    }
    
    public void setCancelButtonEnabled(Boolean newValue) {
        
        Boolean oldValue = getCancelButtonEnabled();        
        if (newValue != oldValue) {
            buttonEnabledHashmap.put(CANCEL_BUTTON_ENABLED_PROPERTY, newValue);
            firePropertyChange(CANCEL_BUTTON_ENABLED_PROPERTY, oldValue, newValue);
        }
    }
    
    
    
    public void addPropertyChangeListener(PropertyChangeListener p) {
        propertyChangeSupport.addPropertyChangeListener(p);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener p) {
        propertyChangeSupport.removePropertyChangeListener(p);
    }
    
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
    
}
