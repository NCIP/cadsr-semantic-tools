package mypackage1;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class NavigationPanel extends JPanel 
{
  private JTree tree;;

  private Object[] treeValues = 
  {
    "caCore 3.0", 
    new Object[] {
      "CSM", 
      new Object[] {
        "Group",
        new Object[] {
          "groupName", "groupDescription"
        },
        "User",
        new Object[] {
          "userName", "userDescription"
        }
      },
      "caDSR",
      new Object[] {
        "DataElementConcept", 
        new Object[] {
          "oc", "property"
        },
        "DataElement",
        new Object[] {
          "valueDomain", "dec"
        }
      }
    }
  };

  public NavigationPanel()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  {
    DefaultMutableTreeNode top = buildTree();
      
    tree = new JTree(top);

    this.setLayout(new BorderLayout());
    this.add(tree, BorderLayout.CENTER);
  }

  private DefaultMutableTreeNode buildTree() {
    
    DefaultMutableTreeNode node = new DefaultMutableTreeNode((String)treeValues[0]);

    return doNode(node, (Object[])treeValues[1]);

  }

  private DefaultMutableTreeNode doNode(DefaultMutableTreeNode node, Object[] values) {
    
    DefaultMutableTreeNode newNode = null;
    for(int i = 0; i < values.length; i++) {
      Object o = values[i];
      if (o instanceof String) {
        newNode = new DefaultMutableTreeNode((String)o);
        node.add(newNode);
      } else {
        doNode(newNode, (Object[])o);
      }
    }

    return node;
    
  }

}