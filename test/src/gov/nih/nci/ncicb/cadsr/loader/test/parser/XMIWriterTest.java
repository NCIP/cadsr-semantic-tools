package gov.nih.nci.ncicb.cadsr.loader.test.parser;

import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.ChangeTracker;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ElementChangeEvent;
import gov.nih.nci.ncicb.cadsr.loader.parser.ParserException;
import gov.nih.nci.ncicb.cadsr.loader.parser.XMIParser2;
import gov.nih.nci.ncicb.cadsr.loader.parser.XMIWriter2;
import gov.nih.nci.ncicb.cadsr.loader.test.MainTestCase;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.AttributeNode;
import gov.nih.nci.ncicb.cadsr.loader.util.BeansAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.DEMappingUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;
import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;
import gov.nih.nci.ncicb.xmiinout.handler.HandlerEnum;
import gov.nih.nci.ncicb.xmiinout.handler.XmiException;
import gov.nih.nci.ncicb.xmiinout.handler.XmiHandlerFactory;
import gov.nih.nci.ncicb.xmiinout.handler.XmiInOutHandler;
import gov.nih.nci.ncicb.xmiinout.util.ModelUtil;

import java.io.IOException;

import java.util.List;

public class XMIWriterTest extends MainTestCase {
    
  public XMIWriterTest() {
    super("XMIWriter Test Case", XMIWriterTest.class, "");
  }
  
  public void setUp() throws Exception {
  
    UserPreferences prefs = (UserPreferences)BeansAccessor.getBeanByName("usersPreferences");
    prefs.getInstance().setUsePrivateApi(false);
  
  }


  /**
   * LVD saved for inherited attributes
   */
  public void testGF21590() {
    String filename = "/home/ludetc/dev/umlclassdiagramloader/2.0.0/test/data/xmi/GF21590.xmi";
    XmiInOutHandler handler = null;
    handler = XmiHandlerFactory.getXmiHandler(HandlerEnum.EADefault);
    try {
      handler.load(filename);
    }
    catch (IOException e) {  
    }
    catch (XmiException e) {
    }
    
    UMLModel model = handler.getModel();
    UMLClass clazz = ModelUtil.findClass(model, "Logical View.test.B");
    assertNotNull(clazz);
    
    UMLTaggedValue tv = clazz.getTaggedValue("CADSR_Inherited.id.Local Value Domain");
    assertNull(tv);

    XMIParser2 parser = (XMIParser2)BeansAccessor.getBeanByName("xmiParser2");

    try {
      parser.parse(filename);
    } catch (ParserException ex) {
        ex.printStackTrace();
    }

    ElementsLists elements = ElementsLists.getInstance();
    ChangeTracker changeTracker = ChangeTracker.getInstance();

    List<DataElement> des = elements.getElements(DomainObjectFactory.newDataElement());
    assertNotNull(des);

    List<ValueDomain> vds = elements.getElements(DomainObjectFactory.newValueDomain());
    
    assertNotNull(vds);
    assertTrue(vds.size() > 0);
    ValueDomain vd = vds.get(0);

    boolean foundDE = false;

    for(DataElement de : des) {
      
      if(LookupUtil.lookupFullName(de).equals("test.B.id")) {
        foundDE = true;
        de.setValueDomain(vd);
        DEMappingUtil.setMappedToLVD(de, true);

        changeTracker.elementChanged(new ElementChangeEvent(new AttributeNode(de)));
        
      }
    }

    assertTrue(foundDE);
    

    filename = "/home/ludetc/dev/umlclassdiagramloader/2.0.0/test/data/xmi/after/GF21590.xmi";
    XMIWriter2 writer = (XMIWriter2)BeansAccessor.getBeanByName("xmiInOutWriter");
    writer.setOutput(filename);
    try {
        writer.write(elements);
    }
    catch (ParserException e) {
    }

    handler = XmiHandlerFactory.getXmiHandler(HandlerEnum.EADefault);
    try {
        handler.load(filename);
    }
    catch (IOException e) {
    }  catch (XmiException e) {
    }
    
    model = handler.getModel();
    clazz = ModelUtil.findClass(model, "Logical View.test.B");
    assertNotNull(clazz);
    
    tv = clazz.getTaggedValue("CADSR_Inherited.id.Local Value Domain");
    assertNotNull(tv);

    assertEquals(tv.getValue(), "VD1");

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
