/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

/*
 * Copyright 2000-2005 Oracle, Inc. This software was developed in conjunction with the National Cancer Institute, and so to the extent government employees are co-authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105.
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

package gov.nih.nci.ncicb.cadsr.loader.defaults;

import gov.nih.nci.ncicb.cadsr.domain.*;

import org.apache.log4j.Logger;

import java.util.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

/**
 * Defaults Loader if defaults are stored in an adjacent file. 
 * <br>
 * If XMI file is named a.xmi, it looks for a.properties
 *
 * @author <a href="mailto:chris.ludet@oracle.com">Christophe Ludet</a>
 *  
 */
public class AttachedFileDefaultsLoader  {

  private static Logger logger = Logger.getLogger(AttachedFileDefaultsLoader.class.getName());

  public LoaderDefault loadDefaults(String modelFileName) {

    try {
      LoaderDefault loaderDefault = DomainObjectFactory.newLoaderDefault();
      Properties props = new Properties();
      File f = new File(getPropFilenameAsURI(modelFileName));
      if(!f.exists())
        return null;

      props.load(new FileInputStream(f));

      logger.debug("loaded");

      loaderDefault.setProjectName(props.getProperty("projectName"));
      loaderDefault.setProjectVersion(new Float(props.getProperty("projectVersion")));
      loaderDefault.setContextName(props.getProperty("contextName"));
      loaderDefault.setVersion(new Float(props.getProperty("version")));
      loaderDefault.setWorkflowStatus(props.getProperty("workflowStatus"));
      loaderDefault.setProjectLongName(props.getProperty("projectLongName"));
      loaderDefault.setProjectDescription(props.getProperty("projectDescription"));
      loaderDefault.setCdName(props.getProperty("cdName"));
      loaderDefault.setCdContextName(props.getProperty("cdContextName"));

      loaderDefault.setPackageFilter(props.getProperty("packageFilter"));

      return loaderDefault;
    } catch (Exception e){
      logger.error(e);
      return null;
    } // end of try-catch

  }

  
  public void saveDefaults(LoaderDefault loaderDefault, String modelFileName) {
    try {

      Properties props = new MyProperties();

      System.out.println("modelFileName as URI: " + getPropFilenameAsURI(modelFileName));
      File f = new File(getPropFilenameAsURI(modelFileName));

      if(f.exists())
        props.load(new FileInputStream(f));
      else 
        f.createNewFile();
      

      props.setProperty("projectName", loaderDefault.getProjectName());
      props.setProperty("projectVersion", loaderDefault.getProjectVersion().toString());
      props.setProperty("contextName", loaderDefault.getContextName());
      props.setProperty("version", loaderDefault.getVersion().toString());
      props.setProperty("workflowStatus", loaderDefault.getWorkflowStatus());
      props.setProperty("projectLongName", loaderDefault.getProjectLongName());
      props.setProperty("projectDescription", loaderDefault.getProjectDescription());
      props.setProperty("cdName", loaderDefault.getCdName());
      props.setProperty("cdContextName", loaderDefault.getCdContextName());
      props.setProperty("packageFilter", loaderDefault.getPackageFilter());

      props.store(new FileOutputStream(f), "Generated By the Semantic Integration Workbench.");
      

    } catch (Exception e){
      e.printStackTrace();
      logger.error(e);
      return;
    } // end of try-catch

  }

  private URI getPropFilenameAsURI(String filename) {

    int ind = filename.lastIndexOf(".");
    String propsFileName = filename.substring(0, ind);
    propsFileName = propsFileName + ".properties";
    
    propsFileName = propsFileName.replaceAll("\\ ", "%20");

    if(!propsFileName.startsWith("/"))
      propsFileName = "/" + propsFileName;    

    try {
      return new URI("file://" + propsFileName);
    } catch (URISyntaxException e){
      return null;
    } // end of try-catch
    
  }

  // The default behaviour of throwing a unchecked exception
  // upon loading a null string is inadequate here. 
  private class MyProperties extends Properties {
    public Object setProperty(String key, String value) {
      if (value == null) {
        return super.setProperty(key, "");
      } else {
        return super.setProperty(key, value);
      }
    }
  }
}