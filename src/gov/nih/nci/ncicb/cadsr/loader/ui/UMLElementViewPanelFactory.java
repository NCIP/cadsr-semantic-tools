package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModuleListener;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.UMLNode;

import java.util.ArrayList;
import java.util.List;

// class managed by Spring
public class UMLElementViewPanelFactory implements CadsrModuleListener {

    private List<UMLElementViewPanel> umlVPList = new ArrayList<UMLElementViewPanel>();

    private CadsrModule cadsrModule;

    public UMLElementViewPanelFactory() {
    }

    public UMLElementViewPanel createUMLElementViewPanel(UMLNode node) {
       
        UMLElementViewPanel vp = new UMLElementViewPanel(node);
        
        vp.setCadsrModule(cadsrModule);
    
        umlVPList.add(vp);

        return vp;
        
    }

    public void removeFromList(UMLElementViewPanel vp) {
        umlVPList.remove(vp);
    }
    
    public void setCadsrModule(CadsrModule cadsrModule) {
      this.cadsrModule = cadsrModule;
      for(UMLElementViewPanel vp : umlVPList)
        vp.setCadsrModule(cadsrModule);
    }
}
