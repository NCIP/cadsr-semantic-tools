package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.validator.*;

import java.util.*;

public class TreeBuilder {

  private ElementsLists elements;
  private UMLDefaults defaults = UMLDefaults.getInstance();

  private static UMLNode rootNode;

  public UMLNode buildTree(ElementsLists elements) {

    this.elements = elements;

    rootNode = new RootNode();
    doPackages(rootNode);

    return rootNode;
  }

  public static UMLNode getRootNode() {
    return rootNode;
  }

  private List<ValidationItem> findValidationItems(Object o) {
    List<ValidationItem> result = new ArrayList<ValidationItem>();

    ValidationItems items = ValidationItems.getInstance();

    Set<ValidationError> errors = items.getErrors();
    for(ValidationError error : errors) {
      if(error.getRootCause() == o)
        result.add(error);
    }

    Set<ValidationWarning> warnings = items.getWarnings();
    for(ValidationWarning warning : warnings) {
      if(warning.getRootCause() == o)
        result.add(warning);
    }
    return result;
  }

  private void doPackages(UMLNode parentNode) {
    
    ClassificationSchemeItem pkg = DomainObjectFactory.newClassificationSchemeItem();
    List<ClassificationSchemeItem> packages = (List<ClassificationSchemeItem>) elements.getElements(pkg.getClass());
    
    for(ClassificationSchemeItem pack : packages) {
      System.out.println("1 package");
      System.out.println(pack.getName());
      System.out.println(pack.getComments());
      System.out.println(pack.getType());

      String alias = defaults.getPackageDisplay(pack.getName()); 
     
      System.out.println("alias: " + alias);

//       if(alias.equals(pack.getName())) {
        UMLNode node = new PackageNode(pack.getName(), alias);
        parentNode.addChild(node);

        System.out.println("added " + node.getDisplay());
        
        doClasses(node);
        System.out.println("--- " + node.getDisplay());
//       }
    }
  }

  private void doClasses(UMLNode parentNode) {
    // Find all classes which are in this package
    String packageName = parentNode.getFullPath();

    ObjectClass oc = DomainObjectFactory.newObjectClass();
    List<ObjectClass> ocs = (List<ObjectClass>) elements.getElements(oc.getClass());

    for(ObjectClass o : ocs) {
      String className = o.getLongName();
      int ind = className.lastIndexOf(".");
      packageName = className.substring(0, ind);
      if(packageName.equals(parentNode.getFullPath())) {
        UMLNode node = new ClassNode(o);
        parentNode.addChild(node);
        System.out.println("--- " + node.getDisplay());
        doAttributes(node);

        List<ValidationItem> items = findValidationItems(o);
        for(ValidationItem item : items) {
          UMLNode vNode = new ValidationNode(item);
          node.addChild(vNode);
        }
      }
    }

  }

  private void doAttributes(UMLNode parentNode) {
    // Find all DEs that have this OC.
    DataElement o = DomainObjectFactory.newDataElement();
    List<DataElement> des = (List<DataElement>) elements.getElements(o.getClass());

    for(DataElement de : des) {
      if(de.getDataElementConcept().getObjectClass().getLongName()
         .equals(parentNode.getFullPath())) {
        UMLNode node = new AttributeNode(de);
        System.out.println("--- --- " + node.getDisplay());
        parentNode.addChild(node);
      }
    }

  }

}