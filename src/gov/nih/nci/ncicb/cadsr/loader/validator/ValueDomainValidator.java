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
package gov.nih.nci.ncicb.cadsr.loader.validator;

import java.util.*;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.dao.ValueDomainDAO;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;

import gov.nih.nci.ncicb.cadsr.loader.util.*;

import gov.nih.nci.ncicb.cadsr.loader.ext.*;

import org.apache.log4j.Logger;

/**
 * Validate that Value Domains requested for load validate required rules: <ul>
 * <li>Must not already Exist
 * <ul>
 */
public class ValueDomainValidator implements Validator, CadsrModuleListener {

  private ElementsLists elements = ElementsLists.getInstance();

  private ValidationItems items = ValidationItems.getInstance();

  private ProgressListener progressListener;

  private CadsrModule cadsrModule;

  private Logger logger = Logger.getLogger(ValueDomainValidator.class.getName());

  public ValueDomainValidator() {
  }

  public void addProgressListener(ProgressListener l) {
    progressListener = l;
  }

  /**
   * returns a list of Validation errors.
   */
  public ValidationItems validate() {
    
    List<ValueDomain> vds = elements.getElements(DomainObjectFactory.newValueDomain());
    if(vds != null) {
      ProgressEvent evt = new ProgressEvent();
      evt.setMessage("Validating Value Domains ...");
      evt.setGoal(vds.size());
      evt.setStatus(0);

      for(ValueDomain vd : vds) {
        // Search only by long Name
        ValueDomain newVd = DomainObjectFactory.newValueDomain();
        newVd.setLongName(vd.getLongName());

        if(StringUtil.isEmpty(vd.getPreferredDefinition())) {
          items.addItem
            (new ValidationError
             (PropertyAccessor.getProperty
              ("vd.missing.definition", vd.getLongName()), vd));
        }

        if(StringUtil.isEmpty(vd.getVdType())) {
          items.addItem
            (new ValidationError
             (PropertyAccessor.getProperty
              ("vd.missing.vdtype", vd.getLongName()), vd));
        } else if(!vd.getVdType().equals(ValueDomain.VD_TYPE_ENUMERATED) && !vd.getVdType().equals(ValueDomain.VD_TYPE_NON_ENUMERATED)) {
          items.addItem
            (new ValidationError
             (PropertyAccessor.getProperty
              ("vd.wrong.vdtype", vd.getLongName()), vd));
        }
        
        
        
        if(StringUtil.isEmpty(vd.getDataType())) {
          items.addItem
            (new ValidationError
             (PropertyAccessor.getProperty
              ("vd.missing.datatype", vd.getLongName()), vd));
        } else {
          List<String> datatypes = Arrays.asList(PropertyAccessor.getProperty("vd.datatype").split(","));
          if(!datatypes.contains(vd.getDataType()))
            items.addItem
            (new ValidationError
             (PropertyAccessor.getProperty
              ("vd.invalid.datatype", vd.getLongName()), vd));
        }
        
        if(StringUtil.isEmpty(vd.getConceptualDomain().getPublicId())) {
          items.addItem
            (new ValidationError
             (PropertyAccessor.getProperty
              ("vd.missing.cdId", vd.getLongName()), vd));
        }
        if(vd.getConceptualDomain().getVersion() == null) {
          items.addItem
            (new ValidationError
             (PropertyAccessor.getProperty
              ("vd.missing.cdVersion", vd.getLongName()), vd));
        }

        if(vd.getConceptualDomain().getVersion() != null && 
           vd.getConceptualDomain().getPublicId() != null) {
          
          Map<String, Object> queryFields = 
            new HashMap<String, Object>();
          queryFields.put(CadsrModule.PUBLIC_ID, vd.getConceptualDomain().getPublicId());
          try {
            queryFields.put(CadsrModule.VERSION, vd.getConceptualDomain().getVersion());
            Collection<ConceptualDomain> cds = cadsrModule.findConceptualDomain(queryFields);
            if(cds.size() != 1) {
              items.addItem
                (new ValidationError
                 (PropertyAccessor.getProperty
                  ("vd.cd.match.incorrect", vd.getLongName()), vd));
            } else {

            }
          } catch (NumberFormatException e) {
            items.addItem
              (new ValidationError
               (PropertyAccessor.getProperty
                ("vd.missing.cdId", vd.getLongName()), vd));
          } catch (Exception e){
            logger.error("Cannot query cadsr for CD " + e);
          } // end of try-catch

        }


        Map<String, Object> queryFields = 
          new HashMap<String, Object>();
        queryFields.put(CadsrModule.LONG_NAME, vd.getLongName());
        queryFields.put("context.name", UMLDefaults.getInstance().getContext().getName());

        try {
          Collection<ValueDomain> result =  cadsrModule.findValueDomain(queryFields);
          if(result.size() > 0) {
            items.addItem(new ValidationError(PropertyAccessor.getProperty("vd.already.exist", vd.getLongName()), vd));
          }
        } catch (Exception e){
          logger.error(e);
        } // end of try-catch
      }    
    
    List<DataElement> des = (List<DataElement>)elements.getElements(DomainObjectFactory.newDataElement().getClass());
    boolean match = false;
    
    if(des != null) {
      for(ValueDomain vd : vds) {
        match = false;
        for(DataElement de : des) {
          if(vd.getLongName().equals(de.getValueDomain().getLongName())) {
            match = true;
            break;
          }
        }
        if(!match)
          items.addItem(new ValidationError
                        (PropertyAccessor.getProperty
                          ("vd.not.used", vd.getLongName()), vd));
      }
    }
    
    for(ValueDomain vd : vds) {
      List<ComponentConcept> vdList = vd.getConceptDerivationRule().getComponentConcepts();
      List<Concept> vdConceptList = new ArrayList<Concept>();
      for(ComponentConcept cc : vdList)
            vdConceptList.add(cc.getConcept());
      if(vdConceptList.isEmpty())
        break;
      for(ValueDomain currentVd : vds)     
        if(vd != currentVd) {
         List<ComponentConcept> currentVdList = currentVd.getConceptDerivationRule().getComponentConcepts();
         List<Concept> currentVdConceptList = new ArrayList<Concept>();
         for(ComponentConcept cc : currentVdList)
          currentVdConceptList.add(cc.getConcept());
        if(currentVdConceptList.isEmpty())
          break;
        if(vdConceptList.size() == currentVdConceptList.size()
          && vdConceptList.containsAll(currentVdConceptList)) 
          items.addItem(new ValidationError
                        (PropertyAccessor.getProperty
                        ("vd.same.concepts", vd.getLongName(), currentVd.getLongName()),vd));         
        }
    }
    }
    return items;
  }

  public void setCadsrModule(CadsrModule module) {
    this.cadsrModule = module;
  }

}