package gov.nih.nci.ncicb.cadsr.loader.ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.BorderLayout;

public class MainFrame extends JFrame 
{

  private JMenuBar jMenuBar1 = new JMenuBar();
  private JMenu jMenu1 = new JMenu();
  private JMenuItem saveMenuItem = new JMenuItem();
  private JMenuItem saveAsMenuItem = new JMenuItem();
  private JMenu jMenu2 = new JMenu();
  private JMenuItem jMenuItem4 = new JMenuItem();
  private JMenuItem jMenuItem5 = new JMenuItem();
  private JMenu jMenu3 = new JMenu();
  private JMenuItem jMenuItem6 = new JMenuItem();
  private JMenuItem exitMenuItem = new JMenuItem();
  private JMenuItem jMenuItem8 = new JMenuItem();
  private JMenuItem defaultsMenuItem = new JMenuItem();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JSplitPane jSplitPane1 = new JSplitPane();
  private JSplitPane jSplitPane2 = new JSplitPane();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel jPanel1 = new JPanel();
  private JPanel mainViewPanel = new JPanel();

  private UmlDefaultsPanel defaultsPanel = new UmlDefaultsPanel();
  private NavigationPanel navigationPanel = new NavigationPanel();

  private MainFrame _this = this;

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
    this.setSize(new Dimension(700, 450));
    this.setJMenuBar(jMenuBar1);
    this.setTitle("UML Loader");
    jMenu1.setText("File");
    saveMenuItem.setText("Save");
    saveAsMenuItem.setText("Save As");
    jMenu2.setText("Model");
    jMenuItem4.setText("Validate");
    jMenuItem5.setText("Upload");
    jMenu3.setText("Help");
    jMenuItem6.setText("About");
    exitMenuItem.setText("Exit");
    jMenuItem8.setText("Index");
    defaultsMenuItem.setText("Defaults");
    jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jSplitPane1.setDividerLocation(120);
    jSplitPane2.setDividerLocation(170);
    jMenu1.add(saveMenuItem);
    jMenu1.add(saveAsMenuItem);
    jMenu1.addSeparator();
    jMenu1.add(exitMenuItem);
    jMenuBar1.add(jMenu1);
    jMenu2.add(jMenuItem4);
    jMenu2.add(jMenuItem5);
    jMenu2.addSeparator();
    jMenu2.add(defaultsMenuItem);
    jMenuBar1.add(jMenu2);
    jMenu3.add(jMenuItem8);
    jMenu3.addSeparator();
    jMenu3.add(jMenuItem6);
    jMenuBar1.add(jMenu3);

    jTabbedPane1.addTab("Errors", new ErrorPanel(DummyErrorPanelData.getData()));
//     logTabbedPane.addTab("jPanel1", jPanel1);
    jTabbedPane1.addTab("Log", new JPanel());
    jSplitPane2.add(jTabbedPane1, JSplitPane.BOTTOM);
    jSplitPane2.add(mainViewPanel, JSplitPane.TOP);
    jSplitPane1.add(jSplitPane2, JSplitPane.RIGHT);
    
    jSplitPane1.add(navigationPanel, JSplitPane.LEFT);
    
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
}