package gov.nih.nci.ncicb.cadsr.loader.test.validator;

import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.parser.ParserException;
import gov.nih.nci.ncicb.cadsr.loader.parser.XMIParser2;
import gov.nih.nci.ncicb.cadsr.loader.test.MainTestCase;

import gov.nih.nci.ncicb.cadsr.loader.util.BeansAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.RunMode;
import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;

import gov.nih.nci.ncicb.cadsr.loader.validator.DuplicateClassValidator;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;

import gov.nih.nci.ncicb.xmiinout.handler.HandlerEnum;
import gov.nih.nci.ncicb.xmiinout.handler.XmiException;
import gov.nih.nci.ncicb.xmiinout.handler.XmiHandlerFactory;
import gov.nih.nci.ncicb.xmiinout.handler.XmiInOutHandler;

import java.io.IOException;

import java.util.Set;

public class DuplicateClassValidatorTest extends MainTestCase {

  public DuplicateClassValidatorTest() {
    super("DuplicateClassValidator Test Case", DuplicateClassValidatorTest.class, "");
  }
  
  public void setUp() throws Exception {
    UserPreferences prefs =
      (UserPreferences)BeansAccessor.getBeanByName("usersPreferences");
    prefs.getInstance().setUsePrivateApi(false);
    
    UserSelections.getInstance().setProperty("MODE", RunMode.Reviewer);
  }
  
  
  /**
   * If the attributes datatype is the same as a LVD name, still throw an error, unless attribute is specifically mapped to LVD
   */
  public void testGF20498() {
    ElementsLists.getInstance().clear();
    UserSelections.getInstance().setProperty("XMI_HANDLER", null);
    ValidationItems.getInstance().clear();
    
    String filename =
      "/home/ludetc/dev/umlclassdiagramloader/2.0.0/test/data/xmi/GF20498.xmi";
    XmiInOutHandler handler = null;
    handler = XmiHandlerFactory.getXmiHandler(HandlerEnum.EADefault);
    try {
      handler.load(filename);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (XmiException e) {
      e.printStackTrace();
    }
    
    XMIParser2 parser =
      (XMIParser2)BeansAccessor.getBeanByName("xmiParser2");
    
    try {
      parser.parse(filename);
    } catch (ParserException ex) {
      ex.printStackTrace();
    }
    
    DuplicateClassValidator duplicateClassValidator = new DuplicateClassValidator();
    ValidationItems items = duplicateClassValidator.validate();
    
    Set<ValidationError> errors = items.getErrors();
    assertNotNull(errors);
    
    boolean errorFound = false;
    for(ValidationError error : errors) {
      if(error.getMessage().startsWith("Class myPackage.domain.ElectricSystem already exists")) {
        errorFound = true;
      }
    }
    assertTrue(errorFound);
  }

  public boolean runInRealContainer() {
    return false;
  }
  
  public boolean requiresDatabase() {
    return true;
  }
  
  public void containerSetUp() {
  }

}