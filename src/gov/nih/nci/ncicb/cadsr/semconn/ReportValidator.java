package gov.nih.nci.ncicb.cadsr.semconn;


import gov.nih.nci.semantic.util.Configuration;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Iterator;
import org.apache.log4j.*;
/**
  * <!-- LICENSE_TEXT_START -->
* Copyright 2001-2004 SAIC. Copyright 2001-2003 SAIC. This software was developed in conjunction with the National Cancer Institute, 
* and so to the extent government employees are co-authors, any rights in such works shall be subject to Title 17 of the United States Code, section 105. 
* Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: 
* 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the disclaimer of Article 3, below. Redistributions 
* in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other 
* materials provided with the distribution. 
* 2. The end-user documentation included with the redistribution, if any, must include the following acknowledgment:
* "This product includes software developed by the SAIC and the National Cancer Institute."
* If no such end-user documentation is to be included, this acknowledgment shall appear in the software itself, 
* wherever such third-party acknowledgments normally appear. 
* 3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or promote products derived from this software. 
* 4. This license does not authorize the incorporation of this software into any third party proprietary programs. This license does not authorize 
* the recipient to use any trademarks owned by either NCI or SAIC-Frederick. 
* 5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
* MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, 
* SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
* WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
  * <!-- LICENSE_TEXT_END -->
  */

/**
  * ReportValidator validates the report
  * @author caBIO Team 
  * @version 1.0
 */

public final class ReportValidator {

  private String reportLocation;
  private EVSImpl evsImpl;
	private ArrayList newEntityList;
	private ArrayList updateEntityList;
	private ArrayList newReportList;

	private String conceptCode = null;
	private String preferredName = null;
	private String definition = null;
	private String definitionSource = null;
	private String umlClass = null;
	private String umlEntity = null;
	private String modifiedDate = null;
	private String verified = null;
	private String description = null;
	private String classification = null;
	private static Logger log= Logger.getLogger(ReportValidator.class.getName());
	/**
	 * Constructor for ReportValidator
	 */
	public ReportValidator(){
		evsImpl = new EVSImpl();
		//properties = new Configuration();

		//get all configuration properties
		conceptCode = Configuration.getConceptCodeCol();
		preferredName = Configuration.getPreferredNameCol();
		description = Configuration.getUMLDescriptionCol();
		definition = Configuration.getDefinitionCol();
		definitionSource = Configuration.getDefinitionSourceCol();
		umlClass = Configuration.getUMLClassCol();
		umlEntity = Configuration.getUMLEntityCol();
		modifiedDate = Configuration.getModifiedDateCol();
		verified = Configuration.getVerifiedFlagCol();
		classification = Configuration.getClassificationCol();

	}

	/**
	 * @param cui
	 *
	 */
	public int checkEVSApprovalStatus(String cui){
		return 0;
	}

	public boolean isReportComplete(){
		return false;
	}

	public boolean isReportCompleteAndVerified(){
		return false;
	}


	/**
	 * checks the entity in the report
	 * @param name    name
	 *
	 */
	public boolean checkTaggedValue(String name){
		return false;
	}


	/**
	 * Gets entity values(tag values) from cabio(evs) and creates TaggedValue object.
	 */
	public void getTaggedValuesFromEvs(){

	}


	/**
	 * This method does the comparison of model entities and report list
	 * @param modelList
	 * @param reportList
	 */
	public void compare(ArrayList modelList, ArrayList reportList) throws Exception
	{

		newEntityList = new ArrayList(); 
		updateEntityList = new ArrayList(); 
		newReportList = new ArrayList();

		HashMap modelElementsMap = null; 


		for(int index=0; index<modelList.size(); index++)
		{
			modelElementsMap = (HashMap)modelList.get(index);
			compareWithReport(modelElementsMap, reportList);
		}

	}


	/**
	 * This method compares model element with the reportlist
	 * @param modelElementsMap
	 * @param reportList
	 */
	private void compareWithReport(HashMap modelElementsMap, ArrayList reportList) throws Exception
	{

		HashMap reportValuesMap = null; 
		boolean newEntry = true;
		HashMap newMap = new HashMap();


		try
		{
            
			for(int index=0; index<reportList.size(); index++)
			{
			   reportValuesMap = (HashMap)reportList.get(index);


			  if(((reportValuesMap.get(umlClass)).equals(modelElementsMap.get(umlClass))) &&
				   ((reportValuesMap.get(umlEntity)).equals(modelElementsMap.get(umlEntity))))
			  {

				newEntry = false;


				if(reportValuesMap.get(verified).equals("1"))
				{

				   String classification1 = (String)reportValuesMap.get(classification);
				   String id = "";


				   if((classification1.equalsIgnoreCase(Configuration.getClassTag())) ||
				   		(classification1.equalsIgnoreCase(Configuration.getAttributeTag())))
				   {
				   	   id = "";
				   }
				   else
				   {

				      id = classification1.substring(classification1.length()-1, classification1.length());
				      classification1 = classification1.substring(0, classification1.length()-1);
				   }


				   String tag = classification1+Configuration.getConceptCodeCol()+id;



				   if(modelElementsMap.containsKey(tag))
				   {

 					  if((!((reportValuesMap.get(conceptCode)).equals(modelElementsMap.get(classification1+conceptCode+id)))) ||
							(!((reportValuesMap.get(preferredName)).equals(modelElementsMap.get(classification1+preferredName+id)))) ||
							(!((reportValuesMap.get(definition)).equals(modelElementsMap.get(classification1+definition+id)))) ||
							(!((reportValuesMap.get(definitionSource)).equals(modelElementsMap.get(classification1+definitionSource+id)))))
 					  {
 					  	 addToUpdateList(reportValuesMap);
 					  }
				   }
				   else
				   {

				   	  addToUpdateList(reportValuesMap);
				   }

				}

				  newReportList.add(reportValuesMap);

			  }

			}

		 }
		 catch(Exception e)
		 {
		     log.error("Exception occured in "+getClass().getName()+": "+e.getMessage());
			throw new Exception("Exception occured in "+getClass().getName()+": "+e.getMessage());
		 }


		 if(newEntry)
		 {


			newEntityList.add(modelElementsMap);


			newReportList.add(modelElementsMap);
		}
    }


	/**
	 * This method adds record to the update list to update model.
	 * @param reportValuesMap
	 */
	private void addToUpdateList(HashMap reportValuesMap)
	{


		HashMap map = new HashMap();

		String classification1 = (String)reportValuesMap.get(classification);
		String id = "";
		if((classification1.equalsIgnoreCase(Configuration.getClassTag())) ||
	 	   (classification1.equalsIgnoreCase(Configuration.getAttributeTag())))
		{
		   id = "";
		}
		else
		{

		   id = classification1.substring(classification1.length()-1, classification1.length());
		   classification1 = classification1.substring(0, classification1.length()-1);
		}


		for(Iterator iter=reportValuesMap.keySet().iterator(); iter.hasNext();)
		{
			String key = (String)iter.next();
			if((key.equalsIgnoreCase(conceptCode)) || (key.equalsIgnoreCase(preferredName)) ||
               (key.equalsIgnoreCase(definition)) ||  (key.equalsIgnoreCase(definitionSource)))
			{
				map.put(classification1+key+id, reportValuesMap.get(key));
			}
			else
				map.put(key, reportValuesMap.get(key));

		}


		updateEntityList.add(map);
	}



	/**
	 * Copy contents of one hashmap to other hashmap
	 * @param newMap
	 * @param reportValuesMap
	 */
	private void copy(HashMap newMap, HashMap reportValuesMap)
	{

		for(Iterator iter=reportValuesMap.keySet().iterator(); iter.hasNext();)
		{
			String key = (String)iter.next();
			newMap.put(key, reportValuesMap.get(key));
		}
	}



	/**
	 * Gets newEntityList, which has all the new entities from the model that doesn't exist in the report.
	 * First, compareModelAndReport() has to be called, to get this newEntitylist..
	 * @return
	 */
	public ArrayList getNewEntityList(){ return newEntityList; }

	/**
	 * Gets updateEntityList, which has the entities from the report that has to updated in the model.
	 * First, compareModelAndReport() has to be called, to get this updateEntityList..
	 * @return
	 */
	public ArrayList getUpdatedEntityList(){ return updateEntityList; }

	/**
	 * Gets newReportList, which has all the entities of model..(this is to remove values from report that
	 * doesn't exist in report)
	 * @return
	 */
	public ArrayList getNewReportList(){return newReportList; }


	/**
	 * Sends email to EVS team
	 *
	 */
	public void notifyEVS(String report)
	{
		
		try{
			if(Configuration.getSendEmailFlag().equalsIgnoreCase("TRUE"))
			{
				EmailReport er = new EmailReport(report);
				er.sendEmail();
			}
		}catch(Exception e)
		{
		    log.error("Exception : "+e.getMessage());
		}
	}


}
