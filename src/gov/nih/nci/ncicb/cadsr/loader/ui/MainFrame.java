package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.*;
import gov.nih.nci.ncicb.cadsr.loader.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.BorderLayout;

import java.util.*;

import gov.nih.nci.ncicb.cadsr.domain.*;


public class MainFrame extends JFrame 
                               implements ViewChangeListener
{

  private JMenuBar jMenuBar1 = new JMenuBar();
  private JMenu fileMenu = new JMenu();
  private JMenuItem saveMenuItem = new JMenuItem();
  private JMenuItem saveAsMenuItem = new JMenuItem();
  private JMenuItem exportErrorsMenuItem = new JMenuItem();
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
  private JTabbedPane viewTabbedPane = new JTabbedPane();
  private JPanel jPanel1 = new JPanel();
  private JPanel mainViewPanel = new JPanel();

  private UmlDefaultsPanel defaultsPanel = new UmlDefaultsPanel();
  private NavigationPanel navigationPanel = new NavigationPanel();

  private MainFrame _this = this;

  private List<UMLElementViewPanel> viewPanels = new ArrayList<UMLElementViewPanel>();

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
    this.setSize(new Dimension(800, 650));
    this.setJMenuBar(jMenuBar1);
    this.setTitle("UML Loader");
    fileMenu.setText("File");
    saveMenuItem.setText("Save");
    saveAsMenuItem.setText("Save As");
    exportErrorsMenuItem.setText("Export");
    semanticConnectorMenuItem.setText("Semantic Connector");
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
    fileMenu.add(exportErrorsMenuItem);
    fileMenu.addSeparator();
    fileMenu.add(exitMenuItem);
    jMenuBar1.add(fileMenu);

    runMenu.add(jMenuItem4);
    runMenu.add(jMenuItem5);
    runMenu.addSeparator();
    runMenu.add(defaultsMenuItem);
    jMenuBar1.add(runMenu);

    helpMenu.add(jMenuItem8);
    helpMenu.addSeparator();
    helpMenu.add(jMenuItem6);
    jMenuBar1.add(helpMenu);

    jTabbedPane1.addTab("Errors", new ErrorPanel(TreeBuilder.getRootNode()));
//     logTabbedPane.addTab("jPanel1", jPanel1);
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
    
    mainViewPanel.setLayout(new BorderLayout());
  }

  public void viewChanged(ViewChangeEvent event) {
    if(event.getType() == ViewChangeEvent.VIEW_CONCEPTS) {
      UMLNode node = (UMLNode)event.getViewObject();
      if(node instanceof ClassNode) {
        ObjectClass oc = (ObjectClass)node.getUserObject();

        String[] conceptCodes = oc.getPreferredName().split("-");
        Concept[] concepts = new Concept[conceptCodes.length];
        for(int i=0; i<concepts.length; 
            concepts[i] = LookupUtil.lookupConcept(conceptCodes[i++])
            );
        
        UMLElementViewPanel viewPanel = new UMLElementViewPanel(concepts);
        viewTabbedPane.addTab(node.getDisplay(), viewPanel);
        viewTabbedPane.setSelectedComponent(viewPanel);
      }
    }
   
  }

}