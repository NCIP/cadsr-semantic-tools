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

import java.awt.*;
import javax.swing.*;


/**
 * A base descriptor class used to reference a Component panel for the Wizard, as
 * well as provide general rules as to how the panel should behave.
 */
public class WizardPanelDescriptor {
    
  private static final String DEFAULT_PANEL_IDENTIFIER = "defaultPanelIdentifier";
    
    
  /**
   * Identifier returned by getNextPanelDescriptor() to indicate that this is the
   * last panel and the text of the 'Next' button should change to 'Finish'.
   */    
  public static final FinishIdentifier FINISH = new FinishIdentifier();
    
  private Wizard wizard;
  private Component targetPanel;
  private Object panelIdentifier;

  protected String nextPanelDescriptor;
  protected String backPanelDescriptor;
  
    
    /**
     * Default constructor. The id and the Component panel must be set separately.
     */    
    public WizardPanelDescriptor() {
        panelIdentifier = DEFAULT_PANEL_IDENTIFIER;
        targetPanel = new JPanel();
    }
    
    /**
     * Constructor which accepts both the Object-based identifier and a reference to
     * the Component class which makes up the panel.
     * @param id Object-based identifier
     * @param panel A class which extends java.awt.Component that will be inserted as a
     * panel into the wizard dialog.
     */    
    public WizardPanelDescriptor(Object id, Component panel) {
        panelIdentifier = id;
        targetPanel = panel;
    }
   
    /**
     * Returns to java.awt.Component that serves as the actual panel.
     * @return A reference to the java.awt.Component that serves as the panel
     */    
    public final Component getPanelComponent() {
        return targetPanel;
    }
    
    /**
     * Sets the panel's component as a class that extends java.awt.Component
     * @param panel java.awt.Component which serves as the wizard panel
     */    
    public final void setPanelComponent(Component panel) {
        targetPanel = panel;
    }
    
    /**
     * Returns the unique Object-based identifier for this panel descriptor.
     * @return The Object-based identifier
     */    
    public final Object getPanelDescriptorIdentifier() {
        return panelIdentifier;
    }

    /**
     * Sets the Object-based identifier for this panel. The identifier must be unique
     * from all the other identifiers in the panel.
     * @param id Object-based identifier for this panel.
     */    
    public final void setPanelDescriptorIdentifier(Object id) {
        panelIdentifier = id;
    }
    
    final void setWizard(Wizard w) {
        wizard = w;
    }
    
    /**
     * Returns a reference to the Wizard component.
     * @return The Wizard class hosting this descriptor.
     */    
    public final Wizard getWizard() {
        return wizard;
    }   

    /**
     * Returns a reference to the current WizardModel for this Wizard component.
     * @return The current WizardModel for this Wizard component.
     */    
    public WizardModel getWizardModel() {
        return wizard.getModel();
    }
    
    //  Override this method to provide an Object-based identifier
    //  for the next panel.
    
    /**
     * Override this class to provide the Object-based identifier of the panel that the
     * user should traverse to when the Next button is pressed. Note that this method
     * is only called when the button is actually pressed, so that the panel can change
     * the next panel's identifier dynamically at runtime if necessary. Return null if
     * the button should be disabled. Return FinishIdentfier if the button text
     * should change to 'Finish' and the dialog should end.
     * @return Object-based identifier.
     */    
    public Object getNextPanelDescriptor() {
        return nextPanelDescriptor;
    }
  
  public void setNextPanelDescriptor(String descriptor) {
//     if(descriptor.equals("FINISH"))
    this.nextPanelDescriptor = descriptor;
  }

    //  Override this method to provide an Object-based identifier
    //  for the previous panel.
    
    /**
     * Override this class to provide the Object-based identifier of the panel that the
     * user should traverse to when the Back button is pressed. Note that this method
     * is only called when the button is actually pressed, so that the panel can change
     * the previous panel's identifier dynamically at runtime if necessary. Return null if
     * the button should be disabled.
     * @return Object-based identifier
     */    
    public Object getBackPanelDescriptor() {
        return backPanelDescriptor;
    }
  public void setBackPanelDescriptor(String descriptor) {
    this.backPanelDescriptor = descriptor;
  }
    
    //  Override this method in the subclass if you wish it to be called
    //  just before the panel is displayed.
    
    /**
     * Override this method to provide functionality that will be performed just before
     * the panel is to be displayed.
     */    
    public void aboutToDisplayPanel() {

    }
 
    //  Override this method in the subclass if you wish to do something
    //  while the panel is displaying.
    
    /**
     * Override this method to perform functionality when the panel itself is displayed.
     */    
    public void displayingPanel() {

    }
 
    //  Override this method in the subclass if you wish it to be called
    //  just before the panel is switched to another or finished.
    
    /**
     * Override this method to perform functionality just before the panel is to be
     * hidden.
     */    
    public void aboutToHidePanel() {

    }    
    
    
    
    static class FinishIdentifier {
        public static final String ID = "FINISH";
    }
}
