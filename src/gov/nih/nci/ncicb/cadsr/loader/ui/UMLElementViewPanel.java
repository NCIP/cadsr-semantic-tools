package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.event.ReviewEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ReviewListener;

import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationEvent;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.NavigationListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.*;

import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class UMLElementViewPanel extends JPanel
  implements ActionListener, KeyListener
             , ItemListener, CaretListener {

  private Concept[] concepts;
  private ConceptUI[] conceptUIs;

  private boolean unsavedChanges = false;

  private JPanel _this = this;

  private UMLNode node;

  private static final String ADD = "ADD",
    DELETE = "DELETE",
    SAVE = "SAVE", 
    PREVIOUS = "PREVIOUS",
    NEXT = "NEXT";

  private JButton addButton, deleteButton, saveButton;
  private JButton previousButton, nextButton;
  private JCheckBox reviewButton;
  private List<ReviewListener> reviewListeners = new ArrayList();
  private List<NavigationListener> navigationListeners = new ArrayList();
  
  
  public UMLElementViewPanel(UMLNode node) 
  {
    this.node = node;
    initConcepts();
    initUI();
  }

  public void updateNode(UMLNode node) 
  {
    this.node = node;
    initConcepts();
    updateConcepts(concepts);
  }

  private void initConcepts() 
  {
    concepts = NodeUtil.getConceptsFromNode(node);
  }

  private void updateConcepts(Concept[] concepts) {
    this.concepts = concepts;
    this.removeAll();
    initUI();
    this.updateUI();
  }

  public Concept[] getConcepts() {
    return null;
  }

  public boolean haveUnsavedChanges() {
    return unsavedChanges;
  }

  private void initUI() {
    this.setLayout(new BorderLayout());

    JPanel gridPanel = new JPanel(new GridLayout(-1, 1));
    JScrollPane scrollPane = new JScrollPane(gridPanel);

    conceptUIs = new ConceptUI[concepts.length];
    JPanel[] conceptPanels = new JPanel[concepts.length];

    

    for(int i = 0; i<concepts.length; i++) {
      conceptUIs[i] = new ConceptUI(concepts[i]);

      String title = i == 0?"Primary Concept":"Qualifier Concept";

      conceptPanels[i] = new JPanel();
      conceptPanels[i].setBorder
        (BorderFactory.createTitledBorder(title));

      conceptPanels[i].setLayout(new BorderLayout());

      JPanel mainPanel = new JPanel(new GridBagLayout());

      insertInBag(mainPanel, conceptUIs[i].labels[0], 0, 0);
      insertInBag(mainPanel, conceptUIs[i].labels[1], 0, 1);
      insertInBag(mainPanel, conceptUIs[i].labels[2], 0, 2);
      insertInBag(mainPanel, conceptUIs[i].labels[3], 0, 3);

      insertInBag(mainPanel, conceptUIs[i].code, 1, 0, 2, 1);
      insertInBag(mainPanel, conceptUIs[i].name, 1, 1, 2, 1);
      insertInBag(mainPanel, conceptUIs[i].defScrollPane, 1, 2, 2, 1);
      insertInBag(mainPanel, conceptUIs[i].defSource, 1, 3,1, 1);

      JButton evsButton = new JButton("Evs Link");
      insertInBag(mainPanel, evsButton, 2, 3);
      
      JButton upButton = new JButton(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("up-arrow.gif")));
      JButton downButton = new JButton(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("down-arrow.gif")));

      upButton.setPreferredSize(new Dimension(28, 35));
      downButton.setPreferredSize(new Dimension(28, 35));

      JPanel arrowPanel = new JPanel(new GridBagLayout());
      insertInBag(arrowPanel, upButton, 0, 0);
      insertInBag(arrowPanel, downButton, 0, 4);
//       arrowPanel.add(upButton);
//       arrowPanel.add(downButton);
      
      conceptPanels[i].add(mainPanel, BorderLayout.CENTER);
      conceptPanels[i].add(arrowPanel, BorderLayout.EAST);
      gridPanel.add(conceptPanels[i]);

      conceptUIs[i].code.addKeyListener(this);
      conceptUIs[i].name.addKeyListener(this);
      conceptUIs[i].defSource.addKeyListener(this);

      conceptUIs[i].code.addCaretListener(this);
      conceptUIs[i].name.addCaretListener(this);
      conceptUIs[i].defSource.addCaretListener(this);
      conceptUIs[i].def.addCaretListener(this);
      
      final int index = i;
      if(index == 0)
        upButton.setVisible(false);
      if(index == concepts.length-1)
        downButton.setVisible(false);
      
      upButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          Concept temp = concepts[index-1];
          concepts[index-1] = concepts[index];
          concepts[index] = temp;
          updateConcepts(concepts);
              
          }
        });
      
      downButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          Concept temp = concepts[index];
          concepts[index] = concepts[index+1];
          concepts[index+1] = temp;
          updateConcepts(concepts);
              
          }
        });
    
      
    }

    addButton = new JButton("Add");
    deleteButton = new JButton("Remove");
    saveButton = new JButton("Apply");
    reviewButton = new JCheckBox("Reviewed");
    previousButton = new JButton("Previous");
    nextButton = new JButton("Next");
    
    reviewButton.setSelected(((ReviewableUMLNode)node).isReviewed());

    addButton.setActionCommand(ADD);
    deleteButton.setActionCommand(DELETE);
    saveButton.setActionCommand(SAVE);
    previousButton.setActionCommand(PREVIOUS);
    nextButton.setActionCommand(NEXT);

    addButton.addActionListener(this);
    deleteButton.addActionListener(this);
    saveButton.addActionListener(this);
    reviewButton.addItemListener(this);
    previousButton.addActionListener(this);
    nextButton.addActionListener(this);
    

    if(concepts.length < 2)
      deleteButton.setEnabled(false);

    saveButton.setEnabled(false);

    setButtonState(reviewButton);
    JPanel buttonPanel = new JPanel();

    buttonPanel.add(addButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(reviewButton);
    buttonPanel.add(previousButton);
    buttonPanel.add(nextButton);


    this.add(scrollPane, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.SOUTH);

  }
  
  private void setButtonState(AbstractButton button)  {
    for(int i=0; i < conceptUIs.length; i++) {
      if(conceptUIs[i].code.getText().trim().equals("")
         | conceptUIs[i].name.getText().trim().equals("")
         | conceptUIs[i].defSource.getText().trim().equals("")
         | conceptUIs[i].def.getText().trim().equals("")) {
        button.setEnabled(false);
        return;
      }
      
      else
        button.setEnabled(true);
    }
  }

  public void caretUpdate(CaretEvent evt) {
    setButtonState(reviewButton);
  }
  
  public void keyTyped(KeyEvent evt) {
    saveButton.setEnabled(true);
  }



  public void keyPressed(KeyEvent evt) {
    
  }
  public void keyReleased(KeyEvent evt) {
    
  }

  
  
  public void actionPerformed(ActionEvent evt) {
    JButton button = (JButton)evt.getSource();
    if(button.getActionCommand().equals(SAVE)) {
      
    } else if(button.getActionCommand().equals(ADD)) {
      Concept[] newConcepts = new Concept[concepts.length + 1];
      for(int i = 0; i<concepts.length; i++) {
        newConcepts[i] = concepts[i];
      }
      Concept concept = DomainObjectFactory.newConcept();
      concept.setPreferredName("");
      concept.setLongName("");
      concept.setDefinitionSource("");
      concept.setPreferredDefinition("");
      newConcepts[newConcepts.length - 1] = concept;
      concepts = newConcepts;

      _this.removeAll();
      initUI();
    } else if(button.getActionCommand().equals(DELETE)) {
      Concept[] newConcepts = new Concept[concepts.length - 1];
      for(int i = 0; i<newConcepts.length; i++) {
        newConcepts[i] = concepts[i];
      }
      concepts = newConcepts;

      _this.removeAll();
      initUI();
    } else if(button.getActionCommand().equals(SAVE)) {
      saveButton.setEnabled(false);
    }
      else if(button.getActionCommand().equals(PREVIOUS)) {
        NavigationEvent event = new NavigationEvent(NavigationEvent.NAVIGATE_PREVIOUS);
        fireNavigationEvent(event);
      }
      else if(button.getActionCommand().equals(NEXT)) {
        NavigationEvent event = new NavigationEvent(NavigationEvent.NAVIGATE_NEXT);
        fireNavigationEvent(event);
      }
  }
  
  public void fireNavigationEvent(NavigationEvent event) 
  {
    for(NavigationListener l : navigationListeners)
      l.navigate(event);
  }
  
  public void addNavigationListener(NavigationListener listener) 
  {
    navigationListeners.add(listener);
  }
  
  public void itemStateChanged(ItemEvent e) {
    if(e.getStateChange() == ItemEvent.SELECTED
       || e.getStateChange() == ItemEvent.DESELECTED
       ) {
      ReviewEvent event = new ReviewEvent();
      event.setUserObject(node);
      
      event.setReviewed(ItemEvent.SELECTED == e.getStateChange());
      
      fireReviewEvent(event);
      
      //if item is reviewed go to next item in the tree
      if(e.getStateChange() == ItemEvent.SELECTED) 
      {
        NavigationEvent goToNext = new NavigationEvent(NavigationEvent.NAVIGATE_NEXT);
        fireNavigationEvent(goToNext);
      }
        
    }
  }
  
  public void fireReviewEvent(ReviewEvent event) {
    for(ReviewListener l : reviewListeners)
      l.reviewChanged(event);
  }
  

  public void addReviewListener(ReviewListener listener) {
    reviewListeners.add(listener);
  }
  
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setLayout(new BorderLayout());

    Concept con = DomainObjectFactory.newConcept();
    con.setPreferredName("C12345");
    con.setPreferredDefinition("A definition of this concept");
    con.setDefinitionSource("NCI_GLOSS");
    con.setLongName("NewConceptName");

    Concept[] concepts = new Concept[1];
    concepts[0] = con;

    ObjectClass oc = DomainObjectFactory.newObjectClass();
    oc.setPreferredName(con.getPreferredName());
    oc.setLongName("com.anwar.ATrivialObject");
    
    ClassNode node = new ClassNode(oc);
    frame.add(new UMLElementViewPanel(node));
    
    frame.setSize(500, 400);

    frame.show();

  }

  private void insertInBag(JPanel bagComp, Component comp, int x, int y) {

    insertInBag(bagComp, comp, x, y, 1, 1);

  }

  private void insertInBag(JPanel bagComp, Component comp, int x, int y, int width, int height) {
    JPanel p = new JPanel();
    p.add(comp);

    bagComp.add(p, new GridBagConstraints(x, y, width, height, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }

}

class ConceptUI {
  JLabel[] labels = new JLabel[] {
    new JLabel("Concept Code"),
    new JLabel("Concept Preferred Name"),
    new JLabel("Concept Definition"),
    new JLabel("Concept Definition Source")
  };

  JTextField code = new JTextField(10);
  JTextField name = new JTextField(20);
  JTextArea def = new JTextArea();
  JTextField defSource = new JTextField(10);

  JScrollPane defScrollPane;

  public ConceptUI(Concept concept) {
    initUI(concept);
  }

  private void initUI(Concept concept) {
    def.setFont(new Font("Serif", Font.ITALIC, 16));
    def.setLineWrap(true);
    def.setWrapStyleWord(true);
    defScrollPane = new JScrollPane(def);
    defScrollPane
      .setVerticalScrollBarPolicy
      (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    defScrollPane.setPreferredSize(new Dimension(400, 100));

    code.setText(concept.getPreferredName());
    name.setText(concept.getLongName());
    def.setText(concept.getPreferredDefinition());
    defSource.setText(concept.getDefinitionSource());
    
  }

}