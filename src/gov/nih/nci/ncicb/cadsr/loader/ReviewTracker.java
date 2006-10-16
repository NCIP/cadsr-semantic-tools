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
package gov.nih.nci.ncicb.cadsr.loader;
import gov.nih.nci.ncicb.cadsr.loader.event.ReviewEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ReviewEventType;
import gov.nih.nci.ncicb.cadsr.loader.event.ReviewListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.AbstractUMLNode;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.ReviewableUMLNode;
import java.util.HashMap;

/**
 * ReviewTracker keeps track of objects and their Review Status
 * by storing them in a HashMap
 */
public class ReviewTracker implements ReviewListener
{
  private HashMap<String, Boolean> reviewed = new HashMap(); 

  private static ReviewTracker curatorReviewed, modelerReviewed; 
  private ReviewTrackerType trackerType;

  private ReviewTracker(ReviewTrackerType type) {
    this.trackerType = type;
  }

  public static ReviewTracker getInstance(ReviewTrackerType type) {
    if(type.equals(ReviewTrackerType.Curator)) {
      if(curatorReviewed == null)
        curatorReviewed = new ReviewTracker(type);
      return curatorReviewed;


    } else if(type.equals(ReviewTrackerType.Owner)) {
      if(modelerReviewed == null)
        modelerReviewed = new ReviewTracker(type);
      return modelerReviewed;
    }      

    throw new IllegalArgumentException("Wrong type: " + type.toString());
  }
  
  public Boolean get(String key) 
  {   
    return reviewed.get(key);
  }

  public void put(String key, boolean value) 
  {
    reviewed.put(key, value);
  }
  
  public void reviewChanged(ReviewEvent event) {
    if((event.getType().equals(ReviewEventType.Owner) && trackerType.equals(ReviewTrackerType.Owner))
       ||
       (event.getType().equals(ReviewEventType.Curator) && trackerType.equals(ReviewTrackerType.Curator)
        )) {
      AbstractUMLNode absNode = (AbstractUMLNode) event.getUserObject();
      if(reviewed.get(absNode.getFullPath()) != null) 
        {
          reviewed.remove(absNode.getFullPath());
          this.put(absNode.getFullPath(), event.isReviewed());
        }
      else
        this.put(absNode.getFullPath(), event.isReviewed());
    }
  }
  
}