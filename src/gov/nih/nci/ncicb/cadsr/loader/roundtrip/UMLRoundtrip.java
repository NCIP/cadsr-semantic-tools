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
package gov.nih.nci.ncicb.cadsr.loader.roundtrip;

import gov.nih.nci.ncicb.cadsr.dao.*;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.spring.*;

import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.util.*;

import org.apache.log4j.Logger;

import java.util.*;


/**
 * This class will call the other UML Related Persisters
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 */
public class UMLRoundtrip implements Roundtrip {

  private static Logger logger = Logger.getLogger(UMLRoundtrip.class.getName());

  protected static AdminComponentDAO adminComponentDAO = DAOAccessor.getAdminComponentDAO();
  protected static DataElementDAO dataElementDAO = DAOAccessor.getDataElementDAO();
  protected static DataElementConceptDAO dataElementConceptDAO = DAOAccessor.getDataElementConceptDAO();
  protected static ValueDomainDAO valueDomainDAO = DAOAccessor.getValueDomainDAO();
  protected static PropertyDAO propertyDAO = DAOAccessor.getPropertyDAO();
  protected static ObjectClassDAO objectClassDAO = DAOAccessor.getObjectClassDAO();
  protected static ObjectClassRelationshipDAO objectClassRelationshipDAO = DAOAccessor.getObjectClassRelationshipDAO();
  protected static ClassificationSchemeDAO classificationSchemeDAO = DAOAccessor.getClassificationSchemeDAO();
  protected static ClassificationSchemeItemDAO classificationSchemeItemDAO = DAOAccessor.getClassificationSchemeItemDAO();
  protected static ClassSchemeClassSchemeItemDAO classSchemeClassSchemeItemDAO = DAOAccessor.getClassSchemeClassSchemeItemDAO();
  protected static ConceptDAO conceptDAO = DAOAccessor.getConceptDAO();

  protected ElementsLists elements = ElementsLists.getInstance();

  private Map<String, ValueDomain> valueDomains = new HashMap<String, ValueDomain>();

  protected UMLDefaults defaults = UMLDefaults.getInstance();

  private String lastProjectName;
  private Float lastProjectVersion;

  private ClassificationScheme projectCs = null;


  public UMLRoundtrip(String lastProjectName, Float lastProjectVersion) throws RoundtripException {
    this.lastProjectName = lastProjectName;
    this.lastProjectVersion = lastProjectVersion;
    
    initCs();
  }
  
  private void initCs() throws RoundtripException {
    projectCs = createCs(lastProjectName, lastProjectVersion);
    List eager = new ArrayList();
    eager.add("csCsis");
    
    List<ClassificationScheme> results = DAOAccessor.getClassificationSchemeDAO().find(projectCs, eager);

    if(results.size() == 0)
      throw new RoundtripException(PropertyAccessor.getProperty("last.project.not.found", new String[]{lastProjectName, lastProjectVersion.toString()}));
      
    projectCs = results.get(0);

  }


  public void roundtrip() {
    List<DataElement> des = elements.getElements(DomainObjectFactory.newDataElement());
    
    // cache package / csCsi
    Map<String, ClassSchemeClassSchemeItem> csCsiCache = 
      new HashMap<String, ClassSchemeClassSchemeItem>();


    for(DataElement de : des) {
      ObjectClass oc = de.getDataElementConcept().getObjectClass();
      Property prop = de.getDataElementConcept().getProperty();
      
      String className = oc.getLongName();
      int ind = className.lastIndexOf(".");
      String packageName = className.substring(0, ind);
      className = className.substring(ind + 1);

      ClassSchemeClassSchemeItem csCsi = csCsiCache.get(packageName);
      if(csCsi == null) {
        csCsi = lookupCsCsi(packageName);
      }
      
      if(csCsi != null) {  
        if(oc.getPublicId() == null) {
          ObjectClass newOc = findObjectClass(csCsi, className);
          if(newOc != null) {
            oc.setPublicId(newOc.getPublicId());
            System.out.println("found matching OC!" + className);
          } else
            System.out.println("no OC match " + className);
        }


        AlternateName altName = DomainObjectFactory.newAlternateName();
        altName.setName(de.getDataElementConcept().getLongName());
        altName.setType(AlternateName.TYPE_UML_DE);
        
        List<String> eager = new ArrayList<String>();
        List<DataElement> l = DAOAccessor.getAdminComponentDAO()
          .findByClassifiedAlternateName(
            altName,
            DomainObjectFactory.newDataElement(),
            csCsi, eager
            );
        
        DataElement newDe = null;
        if(l.size() > 0)
          newDe = l.get(0);
        
        if(newDe != null) {
          System.out.println("Found Mathcing DE " + altName.getName());
          de.setValueDomain(newDe.getValueDomain());
          de.getDataElementConcept().getProperty().setPublicId(newDe.getDataElementConcept().getProperty().getPublicId());
        } else
          System.out.println("NO DE MATCH " + altName.getName());
      }
    }
  }

  private ClassSchemeClassSchemeItem lookupCsCsi(String packageName) {
    List csCsis = projectCs.getCsCsis();
    ClassSchemeClassSchemeItem packageCsCsi = null;
    for(Iterator it = csCsis.iterator(); it.hasNext(); ) {
      ClassSchemeClassSchemeItem csCsi = 
        (ClassSchemeClassSchemeItem)it.next();
      try {
        if(csCsi.getCsi().getName().equals(packageName)
           || csCsi.getCsi().getComments().equals(packageName)
           )
          packageCsCsi = csCsi;
      } catch (NullPointerException e){
      } // end of try-catch
    }
    
    return packageCsCsi;

  }

  private ClassificationScheme createCs(String projectName, float projectVersion) {
    ClassificationScheme cs = DomainObjectFactory.newClassificationScheme();
    cs.setPreferredName(projectName);
    cs.setVersion(new Float(projectVersion));
    return cs;
  }

  private ObjectClass findObjectClass(
    ClassSchemeClassSchemeItem packageCsCsi,
    String className
    ) 
  {
    
    AlternateName altName = DomainObjectFactory.newAlternateName();
    altName.setName(className);
    altName.setType(AlternateName.TYPE_UML_CLASS);
    
    List eager = new ArrayList();
    eager.add("acCsCsis");    
    List<ObjectClass> l = DAOAccessor.getAdminComponentDAO()
      .findByClassifiedAlternateName(
        altName, DomainObjectFactory.newObjectClass(),
        packageCsCsi, eager
        );
    
    ObjectClass oc = null;
    if(l.size() > 0)
      oc = l.get(0);

    return oc;
  }


}
