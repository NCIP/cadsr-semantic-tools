package gov.nih.nci.ncicb.cadsr.loader.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;


public class UMLElementViewPanel extends JPanel
  implements ActionListener, KeyListener {

  private Concept[] concepts;

  private boolean unsavedChanges = false;

  private static final String ADD = "ADD",
    DELETE = "DELETE",
    SAVE = "SAVE";

  private JButton addButton, deleteButton, saveButton;

  public UMLElementViewPanel(Concept[] concepts) {
    this.concepts = concepts;
    initUI();
  }

  public void updateConcepts(Concept[] concepts) {
    this.concepts = concepts;
    this.removeAll();
    initUI();
  }

  public Concept[] getConcepts() {
    return null;
  }

  public boolean haveUnsavedChanges() {
    return unsavedChanges;
  }

  private void initUI() {
    this.setLayout(new BorderLayout());

//     JPanel scrollPanel = new JPanel();

    JPanel gridPanel = new JPanel(new GridLayout(-1, 1));
    JScrollPane scrollPane = new JScrollPane(gridPanel);

    ConceptUI[] conceptUIs = new ConceptUI[concepts.length];
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

      insertInBag(mainPanel, conceptUIs[i].code, 1, 0);
      insertInBag(mainPanel, conceptUIs[i].name, 1, 1);
      insertInBag(mainPanel, conceptUIs[i].defScrollPane, 1, 2);
      insertInBag(mainPanel, conceptUIs[i].defSource, 1, 3);

      conceptPanels[i].add(mainPanel, BorderLayout.CENTER);
      gridPanel.add(conceptPanels[i]);

      conceptUIs[i].code.addKeyListener(this);
      conceptUIs[i].name.addKeyListener(this);
      conceptUIs[i].defSource.addKeyListener(this);
    }


    addButton = new JButton("Add");
    deleteButton = new JButton("Remove");
    saveButton = new JButton("Apply");

    addButton.setActionCommand(ADD);
    deleteButton.setActionCommand(DELETE);
    saveButton.setActionCommand(SAVE);

    addButton.addActionListener(this);
    deleteButton.addActionListener(this);
    saveButton.addActionListener(this);

    if(concepts.length < 2)
      deleteButton.setEnabled(false);

    saveButton.setEnabled(false);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(saveButton);

//     scrollPanel.add(buttonPanel, BorderLayout.SOUTH);

//     scrollPanel.add(gridPanel);

//     scrollPane.setViewportView(gridPanel);

    this.add(scrollPane, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.SOUTH);

  }

  public void keyTyped(KeyEvent evt) {
    System.out.println("typed");
    saveButton.setEnabled(true);
  }
  public void keyPressed(KeyEvent evt) {
    System.out.println("pressed");
  }
  public void keyReleased(KeyEvent evt) {
    System.out.println("released");
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
      initUI();
    } else if(button.getActionCommand().equals(DELETE)) {
      Concept[] newConcepts = new Concept[concepts.length - 1];
      for(int i = 0; i<newConcepts.length; i++) {
        newConcepts[i] = concepts[i];
      }
      concepts = newConcepts;
      initUI();
    } else if(button.getActionCommand().equals(SAVE)) {
      saveButton.setEnabled(false);
    } 
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

    frame.add(new UMLElementViewPanel(concepts));
    
    frame.setSize(500, 400);

    frame.show();

  }

  private void insertInBag(JPanel bagComp, Component comp, int x, int y) {
    JPanel p = new JPanel();
    p.add(comp);

    bagComp.add(p, new GridBagConstraints(x, y, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
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