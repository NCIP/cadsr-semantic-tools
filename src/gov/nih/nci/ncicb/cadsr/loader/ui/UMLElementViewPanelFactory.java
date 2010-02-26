package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModuleListener;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.UMLNode;

import java.util.ArrayList;
import java.util.List;

// class managed by Spring
public class UMLElementViewPanelFactory implements CadsrModuleListener {

    private List<UMLElementViewPanel> umlVPList = new ArrayList<UMLElementViewPanel>();
    private List<InheritedAttributeViewPanel> inheritedVPList = new ArrayList<InheritedAttributeViewPanel>();

    private CadsrModule cadsrModule;

    private java.io.PrintWriter printWriter = null;

    private void log(String _message)
    {
	try
	{
	    this.printWriter.println(_message);
	}
	catch(Throwable t)
	{
	    throw new RuntimeException(t);
	}
	finally
	{
	    if (this.printWriter != null) { this.printWriter.flush(); }
	}
    }

    public UMLElementViewPanelFactory() {
	try
	{
	    java.io.File file = new java.io.File("/home/georgebn/UMLElementViewPanelFactory.log");
	    if (file.exists() == true) { file.delete(); }
	    this.printWriter = new java.io.PrintWriter(file);
	}
	catch (Throwable t)
	{
	    throw new RuntimeException(t);
	}

    }


    public UMLElementViewPanel createUMLElementViewPanel(UMLNode node) {
        log("Creating UMLElementViewPanel");
	UMLElementViewPanel vp = new UMLElementViewPanel(node);
	log("Created UMLElementViewPanel");
	vp.setCadsrModule(cadsrModule);
	log("Set Cadsr module");
	umlVPList.add(vp);
	log("Added to list");
        return vp;
        
    }

    public InheritedAttributeViewPanel createInheritedAttributeViewPanel(UMLNode node) {
       
        InheritedAttributeViewPanel vp = new InheritedAttributeViewPanel(node);
        vp.setCadsrModule(cadsrModule);
        inheritedVPList.add(vp);
        return vp;
        
    }

    public void removeFromList(InheritedAttributeViewPanel vp) {
        inheritedVPList.remove(vp);
    }

    public void removeFromList(UMLElementViewPanel vp) {
        umlVPList.remove(vp);
    }
    
    public void setCadsrModule(CadsrModule cadsrModule) {
      this.cadsrModule = cadsrModule;
      for(UMLElementViewPanel vp : umlVPList)
        vp.setCadsrModule(cadsrModule);
      
      for(InheritedAttributeViewPanel vp : inheritedVPList)
        vp.setCadsrModule(cadsrModule);
    }
}
