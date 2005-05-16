package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.event.ReviewEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ReviewListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.*;
import gov.nih.nci.ncicb.cadsr.loader.util.*;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.*;


import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.*;

import gov.nih.nci.ncicb.cadsr.domain.*;
import javax.swing.tree.DefaultMutableTreeNode;


public class MainFrame extends JFrame 
  implements ViewChangeListener, CloseableTabbedPaneListener
{

  private JMenuBar jMenuBar1 = new JMenuBar();
  private JMenu fileMenu = new JMenu();
  private JMenuItem saveMenuItem = new JMenuItem();
  private JMenuItem saveAsMenuItem = new JMenuItem();
  private JMenuItem exportErrorsMenuItem = new JMenuItem();
  private JMenu editMenu = new JMenu();
  private JMenuItem findMenuItem = new JMenuItem();
  private JMenuItem prefMenuItem = new JMenuItem();
  private JMenu runMenu = new JMenu();
  private JMenuItem jMenuItem4 = new JMenuItem();
  private JMenuItem jMenuItem5 = new JMenuItem();
  private JMenu helpMenu = new JMenu();
  private JMenuItem jMenuItem6 = new JMenuItem();
  private JMenuItem exitMenuItem = new JMenuItem();
  private JMenuItem jMenuItem8 = new JMenuItem();
  private JMenuItem semanticConnectorMenuItem = new JMenuItem();
  private JMenuItem defaultsMenuItem = new JMenuItem();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JSplitPane jSplitPane1 = new JSplitPane();
  private JSplitPane jSplitPane2 = new JSplitPane();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private CloseableTabbedPane viewTabbedPane = new CloseableTabbedPane();
  private JPanel jPanel1 = new JPanel();
  private JPanel mainViewPanel = new JPanel();

  private UmlDefaultsPanel defaultsPanel = new UmlDefaultsPanel();
  private NavigationPanel navigationPanel = new NavigationPanel();

  private MainFrame _this = this;

  private Map<String, UMLElementViewPanel> viewPanels = new HashMap();
  private AssociationViewPanel associationViewPanel = null;

  

  public MainFrame()
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
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(new Dimension(830, 650));
    this.setJMenuBar(jMenuBar1);
    this.setTitle("UML Loader");
    fileMenu.setText("File");
    saveMenuItem.setText("Save");
    saveAsMenuItem.setText("Save As");
    exportErrorsMenuItem.setText("Export");
    semanticConnectorMenuItem.setText("Semantic Connector");
    editMenu.setText("Edit");
    findMenuItem.setText("Find");
    prefMenuItem.setText("Preferences");
    runMenu.setText("Run");
    jMenuItem4.setText("Validate");
    jMenuItem5.setText("Upload");
    helpMenu.setText("Help");
    jMenuItem6.setText("About");
    exitMenuItem.setText("Exit");
    jMenuItem8.setText("Index");
    defaultsMenuItem.setText("Defaults");
    jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jSplitPane1.setDividerLocation(160);
    jSplitPane2.setDividerLocation(400);

    

    fileMenu.add(saveMenuItem);
    fileMenu.add(saveAsMenuItem);
    fileMenu.addSeparator();
    fileMenu.add(findMenuItem);
    fileMenu.add(exportErrorsMenuItem);
    fileMenu.addSeparator();
    fileMenu.add(exitMenuItem);
    jMenuBar1.add(fileMenu);

    editMenu.add(findMenuItem);
    editMenu.add(prefMenuItem);
    jMenuBar1.add(editMenu);
    

    runMenu.add(jMenuItem4);
    runMenu.add(jMenuItem5);
    runMenu.addSeparator();
    runMenu.add(defaultsMenuItem);
    jMenuBar1.add(runMenu);

    helpMenu.add(jMenuItem8);
    helpMenu.addSeparator();
    helpMenu.add(jMenuItem6);
    jMenuBar1.add(helpMenu);

    jTabbedPane1.addTab("Errors", new ErrorPanel(BeansAccessor.getTreeBuilder().getRootNode()));
//     logTabbedPane.addTab("jPanel1", jPanel1);

    Icon closeIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("close-tab.gif"));
    viewTabbedPane.setCloseIcons(closeIcon, closeIcon, closeIcon);
    viewTabbedPane.addCloseableTabbedPaneListener(this);

    jTabbedPane1.addTab("Log", new JPanel());
    jSplitPane2.add(jTabbedPane1, JSplitPane.BOTTOM);
    jSplitPane2.add(viewTabbedPane, JSplitPane.TOP);
    jSplitPane1.add(jSplitPane2, JSplitPane.RIGHT);
    
    jSplitPane1.add(navigationPanel, JSplitPane.LEFT);
    
    navigationPanel.addViewChangeListener(this);

    this.getContentPane().add(jSplitPane1, BorderLayout.CENTER);
    
    exitMenuItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          System.exit(0);
        }
    });  

    defaultsMenuItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          JDialog dialog = new JDialog(_this, "UML Loader Defaults", true);
          dialog.getContentPane().setLayout(new BorderLayout());
          dialog.getContentPane().add(defaultsPanel, BorderLayout.CENTER);
          dialog.setSize(300, 150);
          dialog.show();
        }
    });
    
    findMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        SearchDialog sd = new SearchDialog(_this);
        UIUtil.putToCenter(sd);
        sd.addSearchListener(navigationPanel);
        sd.setVisible(true);

      }
    });
    
    final PreferenceDialog pd = new PreferenceDialog(_this);
    prefMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        UIUtil.putToCenter(pd);
        pd.setVisible(true);
      }
    });
    
    mainViewPanel.setLayout(new BorderLayout());
  }

  public void viewChanged(ViewChangeEvent event) {
    if(event.getType() == ViewChangeEvent.VIEW_CONCEPTS) {
      UMLNode node = (UMLNode)event.getViewObject();

      // If concept is already showing, just bring it up front
      if(viewPanels.containsKey(node.getFullPath())) {
        UMLElementViewPanel pa = viewPanels.get(node.getFullPath());
        viewTabbedPane.setSelectedComponent(pa);
        return;
      }


      if((event.getInNewTab() == true) || (viewPanels.size() == 0)) {
        UMLElementViewPanel viewPanel = new UMLElementViewPanel(node);
        
        viewPanel.addReviewListener(navigationPanel);
        viewPanel.addNavigationListener(navigationPanel);
        
        viewTabbedPane.addTab(node.getDisplay(), viewPanel);
        viewTabbedPane.setSelectedComponent(viewPanel);

        viewPanel.setName(node.getFullPath());
        viewPanels.put(viewPanel.getName(), viewPanel);
      } else {
        UMLElementViewPanel viewPanel = (UMLElementViewPanel)
          viewTabbedPane.getSelectedComponent();
        viewPanels.remove(viewPanel.getName());

        viewTabbedPane.setTitleAt(viewTabbedPane.getSelectedIndex(), node.getDisplay());

        viewPanel.setName(node.getFullPath());
        viewPanel.updateNode(node);
        viewPanels.put(viewPanel.getName(), viewPanel);
      }

    } else if(event.getType() == ViewChangeEvent.VIEW_ASSOCIATION) {
      UMLNode node = (UMLNode)event.getViewObject();

      if(associationViewPanel == null) {
        associationViewPanel = new AssociationViewPanel((ObjectClassRelationship)node.getUserObject());
        viewTabbedPane.addTab("Association", associationViewPanel);
        associationViewPanel.setName("Association");
      } else
        associationViewPanel.update((ObjectClassRelationship)node.getUserObject());

      viewTabbedPane.setSelectedComponent(associationViewPanel);

    }
   
  }

  public boolean closeTab(int index) {

    Component c = viewTabbedPane.getComponentAt(index);
    viewPanels.remove(c.getName());

    return true;
  }
  
}