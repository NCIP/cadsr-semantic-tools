package gov.nih.nci.ncicb.cadsr.loader.test.generic;

import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.PermissibleValue;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;
import gov.nih.nci.ncicb.cadsr.loader.ChangeTracker;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ElementChangeEvent;
import gov.nih.nci.ncicb.cadsr.loader.parser.ParserException;
import gov.nih.nci.ncicb.cadsr.loader.parser.XMIParser2;
import gov.nih.nci.ncicb.cadsr.loader.parser.XMIWriter2;
import gov.nih.nci.ncicb.cadsr.loader.persister.ConceptPersister;
import gov.nih.nci.ncicb.cadsr.loader.persister.PersisterException;
import gov.nih.nci.ncicb.cadsr.loader.persister.PersisterUtil;
import gov.nih.nci.ncicb.cadsr.loader.test.MainTestCase;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.ValueMeaningNode;
import gov.nih.nci.ncicb.cadsr.loader.util.BeansAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.ConceptUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;
import gov.nih.nci.ncicb.xmiinout.handler.HandlerEnum;
import gov.nih.nci.ncicb.xmiinout.handler.XmiException;
import gov.nih.nci.ncicb.xmiinout.handler.XmiHandlerFactory;
import gov.nih.nci.ncicb.xmiinout.handler.XmiInOutHandler;
import gov.nih.nci.ncicb.xmiinout.util.ModelUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class GenericTestCase extends MainTestCase {
    
  public GenericTestCase() {
    super("Generic Test Case", GenericTestCase.class, "");
  }
  
  public void setUp() throws Exception {
  
    
    UserPreferences prefs = (UserPreferences)BeansAccessor.getBeanByName("usersPreferences");
    prefs.getInstance().setUsePrivateApi(false);
  
  }


  public void testGF21590() {
    
  }
  
 
  public void testGF21871() {
  
    String filename = "/home/ludetc/dev/umlclassdiagramloader/2.0.0/test/data/xmi/GF21871.xmi";
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
    UMLAttribute att = ModelUtil.findAttribute(model, "Logical View.test.VD1.pv1");
    assertNotNull(att);
    
    UMLTaggedValue tv = att.getTaggedValue("ValueMeaningConceptCode");
    assertNull(tv);

    XMIParser2 parser = (XMIParser2)BeansAccessor.getBeanByName("xmiParser2");

    try {
      parser.parse(filename);
    } catch (ParserException ex) {
        ex.printStackTrace();
    }

    ElementsLists elements = ElementsLists.getInstance();

    ChangeTracker changeTracker = ChangeTracker.getInstance();

    List<ValueDomain> vds = elements.getElements(DomainObjectFactory.newValueDomain());
    assertNotNull(vds);

    for(ValueDomain vd : vds) {
    
      for(PermissibleValue pv : vd.getPermissibleValues()) {
      List<Concept> concepts = new ArrayList<Concept>();

      Concept concept = DomainObjectFactory.newConcept();
      concept.setPreferredName("W_00001");
      concept.setLongName("Not a real concept");
      concept.setPreferredDefinition("bad concept");
      concept.setDefinitionSource("NCI");
      
      elements.addElement(concept);
      concepts.add(concept);

      pv.getValueMeaning().setConceptDerivationRule(ConceptUtil.createConceptDerivationRule(concepts, true));

      changeTracker.elementChanged(new ElementChangeEvent(new ValueMeaningNode(pv.getValueMeaning(), LookupUtil.lookupFullName(vd))));
      }
    }
    

    filename = "/home/ludetc/dev/umlclassdiagramloader/2.0.0/test/data/xmi/after/GF21871.xmi";
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
    att = ModelUtil.findAttribute(model, "Logical View.test.VD1.pv1");
    assertNotNull(att);
    
    tv = att.getTaggedValue("ValueMeaningConceptCode");
    assertNotNull(tv);

    assertEquals(tv.getValue(), "W_00001");
    
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
