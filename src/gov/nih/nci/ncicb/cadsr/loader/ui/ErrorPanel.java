package gov.nih.nci.ncicb.cadsr.loader.ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;
import gov.nih.nci.ncicb.cadsr.loader.validator.*;
import java.util.*;
import javax.swing.tree.DefaultTreeCellRenderer;

public class ErrorPanel extends JPanel {

  private JTree tree;
  private DefaultMutableTreeNode rootNode;

  public ErrorPanel(java.util.List list) {
//     initUI(list);
  }

//   private void initUI(java.util.List<ValidationError> list) {
//     rootNode = new DefaultMutableTreeNode("Root");
    
//     buildTree(list);
    
//     //create tree and make root not visible
//     tree = new JTree(rootNode);
//     tree.setRootVisible(false);
//     tree.setShowsRootHandles(true);
    
//     ImageIcon leafIcon = new ImageIcon(this.getClass().getResource("/red-dot.jpg"));
    
//     if (leafIcon != null) {
//       DefaultTreeCellRenderer renderer = 
// 	new DefaultTreeCellRenderer();
//       renderer.setLeafIcon(leafIcon);
//       tree.setCellRenderer(renderer);
//     }

//     this.setLayout(new BorderLayout());

//     JScrollPane scrollPane = new JScrollPane(tree);
//     this.setPreferredSize(new Dimension(450, 110));
//     this.add(scrollPane, BorderLayout.CENTER);

//   }
  
//   public void buildTree(java.util.List<ValidationError> errorList) {
//     DefaultMutableTreeNode errorNode = 
//       new DefaultMutableTreeNode("Error");
//     DefaultMutableTreeNode warningNode = 
//       new DefaultMutableTreeNode("Warning");
   
//     rootNode.add(errorNode);
//     rootNode.add(warningNode);
   
//     for(ValidationError err : errorList) {
//       if(err.getSeverity() == errorNode.toString()) {
// 	DefaultMutableTreeNode node = new DefaultMutableTreeNode(err.getMessage());
// 	errorNode.add(node);
//       }
//       if(err.getSeverity() == warningNode.toString()) {
// 	DefaultMutableTreeNode node = new DefaultMutableTreeNode(err.getMessage());
// 	warningNode.add(node);
//       }
//     }
   }

//   public static void main(String[] args) {
//     JFrame runFrame = new JFrame("Test JFrame FOR ErrorPanel");
//     runFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//     runFrame.setSize(100,100);

//     JPanel errorPanel = new ErrorPanel(DummyErrorPanelData.getData());

//     runFrame.getContentPane().add(BorderLayout.CENTER, errorPanel);
//     //errorPanel.setLayout(new BorderLayout());
//     //runFrame.add(errorPanel);

//     runFrame.show();
//   }

// }

class DummyErrorPanelData {
  static java.util.List<ValidationError> getData() {

    return new ArrayList();

//     ValidationError error1 = new ValidationError("Error", "Concept Not Found");
//     ValidationError error2 = new ValidationError("Error", "Code not Found");
//     ValidationError error3 = new ValidationError("Error", "Element not Found");
//     ValidationError error4 = new ValidationError("Error", "File not Found");
      
//     ValidationError warning1 = 
//       new ValidationError("Warning", "Incorrect type ");
//     ValidationError warning2 = 
//       new ValidationError("Warning", "File not of proper type ");
//     ValidationError warning3 = new ValidationError("Warning", "Unexpected type ");
      
//     java.util.List listofErrors = new ArrayList();
//     listofErrors.add(error1);
//     listofErrors.add(error2);
//     listofErrors.add(error3);
//     listofErrors.add(error4);
      
//     listofErrors.add(warning1);
//     listofErrors.add(warning2);
//     listofErrors.add(warning3);
      
//     return listofErrors;
  }
}      
