package gov.nih.nci.ncicb.cadsr.loader.ui;

import javax.swing.*;
import java.awt.*;

import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;


public class UMLElementViewPanel extends JPanel {

  final static int GAP = 20;  

  public UMLElementViewPanel(Concept[] concepts) {
    initUI(concepts);
  }

  public Concept[] getConcepts() {
    return null;
  }

  private void initUI(Concept[] concepts) {
    this.setLayout(new GridLayout(-1, 1));

    ConceptUI[] conceptUIs = new ConceptUI[concepts.length];
    JPanel[] conceptPanels = new JPanel[concepts.length];

    for(int i = 0; i<concepts.length; i++) {
      conceptUIs[i] = new ConceptUI(concepts[i]);

      String title = i == 0?"Primary Concept":"Qualifier Concept";

      conceptPanels[i] = new JPanel();
      conceptPanels[i].setBorder
        (BorderFactory.createTitledBorder(title));

      conceptPanels[i].setLayout(new BorderLayout());

      JPanel leftPanel = new JPanel();
      leftPanel.setLayout(new GridLayout(-1, 1));

      JPanel p = new JPanel();
      p.add(conceptUIs[i].labels[0]);
      leftPanel.add(p);

      p = new JPanel();
      p.add(conceptUIs[i].labels[1]);
      leftPanel.add(p);

      p = new JPanel();
      p.add(conceptUIs[i].labels[2]);
      leftPanel.add(p);

      p = new JPanel();
      p.add(conceptUIs[i].labels[3]);
      leftPanel.add(p);

      JPanel mainPanel = new JPanel(new GridLayout(-1, 1));

      p = new JPanel();
      p.add(conceptUIs[i].code);
      mainPanel.add(p);

      p = new JPanel();
      p.add(conceptUIs[i].name);
      mainPanel.add(p);

//       p = new JPanel();
//       p.add(conceptUIs[i].defScrollPane);
//       mainPanel.add(p);
      mainPanel.add(conceptUIs[i].defScrollPane);

      p = new JPanel();
      p.add(conceptUIs[i].defSource);
      mainPanel.add(p);

      conceptPanels[i].add(leftPanel, BorderLayout.WEST);
      conceptPanels[i].add(mainPanel, BorderLayout.CENTER);
      this.add(conceptPanels[i]);
    }

//     this.add(leftPanel, BorderLayout.WEST);
//     this.add(mainPanel, BorderLayout.CENTER);

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
//     defScrollPane.setPreferredSize(new Dimension(300, 60));

    code.setText(concept.getPreferredName());
    name.setText(concept.getLongName());
    def.setText(concept.getPreferredDefinition());
    defSource.setText(concept.getDefinitionSource());
  }
}