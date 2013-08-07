/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader;

import gov.nih.nci.ncicb.cadsr.loader.parser.*;
import gov.nih.nci.ncicb.cadsr.loader.util.*;

import gov.nih.nci.ncicb.cadsr.loader.event.*;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;

import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModule;

import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModuleListener;

import java.util.*;

public class ConceptInheritanceAction implements CadsrModuleListener {
  
  private Parser parser;
  private ElementWriter writer;
  
  protected ElementsLists elements = ElementsLists.getInstance();
  
  private ProgressListener progressListener = null;
  
  private CadsrModule cadsrModule;

  public void performConceptInheritance(String input, String output) {

    ChangeTracker changeTracker = ChangeTracker.getInstance();

    ProgressEvent pEvt = new ProgressEvent();
    pEvt.setGoal(0);
    pEvt.setMessage("Parsing ...");
    pEvt.setStatus(0);
    progressListener.newProgressEvent(pEvt);

    try {
      parser.parse(input);
    } catch (ParserException e) {
      throw new RuntimeException(e);
    } 

    InheritedAttributeList inheritedList =  InheritedAttributeList.getInstance();
    

    List<ObjectClass> ocs = new ArrayList<ObjectClass>(elements.getElements(DomainObjectFactory.newObjectClass()));
    int listIndex = 0;

    while(ocs.size() > 0) {
      // we need to do the tree from the bottom up (so we don't dup concepts that were not input by user
      if(listIndex >= ocs.size()) {
        listIndex = 0;
      }
      
      ObjectClass oc = ocs.get(listIndex);
      
      // oc is mapped to a public ID, nothing we can do.
      // or oc is marked as excluded from this rule. 
      // either way, we skip
      if(!StringUtil.isEmpty(oc.getPublicId()) || inheritedList.isExcludedFromSemanticInheritance(oc)) {
        ocs.remove(oc);
      } else {
        // See if this one has children still in the list
        boolean found = false;
        List<ObjectClass> childrenOcs = inheritedList.getChildrenOc(oc);
        for(ObjectClass c_oc : childrenOcs) {
          if(ocs.contains(c_oc))
            found = true;
        }
        
        if(found == true) { // leave this one for later
          listIndex++;
        } else {
          
          // start by storing a list of conDR for the Oc hierarchy.
          List<ConceptDerivationRule> condrList = new ArrayList<ConceptDerivationRule>();
          ConceptDerivationRule condr = ConceptUtil.findConceptDerivationRule(oc);
          condrList.add(condr);
          ObjectClass parentOc = inheritedList.getParentOc(oc);
          while (parentOc != null) {
            if(!inheritedList.isExcludedFromSemanticInheritance(parentOc))
              condrList.add(ConceptUtil.findConceptDerivationRule(parentOc));
            parentOc = inheritedList.getParentOc(parentOc);
          }
          
          // now trickle concepts down. 
          for(int parentIndex = 1; parentIndex < condrList.size(); parentIndex++) {
            // 1. add one to each component concept order.
            for(ComponentConcept compCon : condr.getComponentConcepts()) {
              compCon.setOrder(compCon.getOrder() + condrList.get(parentIndex).getComponentConcepts().size());
            }
            
            // add the concepts from each parent
            condr.getComponentConcepts().addAll(0, condrList.get(parentIndex).getComponentConcepts());
          }
          
          // update preferredName as conceptList. (refactor this in future)
          oc.setPreferredName(ConceptUtil.preferredNameFromConceptDerivationRule(condr));
          

          String className = LookupUtil.lookupFullName(oc);
          changeTracker.put(className, true);
          ocs.remove(oc);
        }
        // this oc is done so remove it from the list.
      }
    }


    pEvt.setGoal(50);
    pEvt.setMessage("Writing ...");
    pEvt.setStatus(100);
    progressListener.newProgressEvent(pEvt);

    try {
      writer.setOutput(output);
      writer.write(elements);
    } catch (ParserException e) {
      throw new RuntimeException(e);
    } 
    
    pEvt.setGoal(100);
    pEvt.setMessage("Done");
    pEvt.setStatus(100);
    pEvt.setCompleted(true);
    progressListener.newProgressEvent(pEvt);

  }


  public void setParser(Parser parser) {
    this.parser = parser;
  }

  public void setWriter(ElementWriter writer) {
    this.writer = writer;
  }

  public void addProgressListener(ProgressListener l) {
    progressListener = l;
  }

  public void setCadsrModule(CadsrModule module) {
    this.cadsrModule = module;
  }

}