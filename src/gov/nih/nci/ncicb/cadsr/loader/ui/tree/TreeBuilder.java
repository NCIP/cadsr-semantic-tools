package gov.nih.nci.ncicb.cadsr.loader.ui.tree;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.validator.*;
import gov.nih.nci.ncicb.cadsr.loader.util.*;

import java.util.*;

public class TreeBuilder {

  private ElementsLists elements;
  private UMLDefaults defaults = UMLDefaults.getInstance();

  private static UMLNode rootNode;

  public UMLNode buildTree(ElementsLists elements) {

    this.elements = elements;

    rootNode = new RootNode();
    doPackages(rootNode);

    doAssociations(rootNode);

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
      String alias = defaults.getPackageDisplay(pack.getName()); 
        UMLNode node = new PackageNode(pack.getName(), alias);
        parentNode.addChild(node);

        doClasses(node);
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
        doAttributes(node);

        List<ValidationItem> items = findValidationItems(o);
        for(ValidationItem item : items) {
          ValidationNode vNode = new ValidationNode(item);
          node.addValidationNode(vNode);
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
        parentNode.addChild(node);
      }
    }


  }

  private void doAssociations(UMLNode parentNode) {
    UMLNode assocNode = new PackageNode("Associations", "Associations");

    ObjectClassRelationship o = DomainObjectFactory.newObjectClassRelationship();
    List<ObjectClassRelationship> ocrs = 
      (List<ObjectClassRelationship>) 
      elements.getElements(o.getClass());

    for(ObjectClassRelationship ocr : ocrs) {
      UMLNode node = new AssociationNode(ocr);
      assocNode.addChild(node);
    }

    parentNode.addChild(assocNode);

  }

}