/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ui;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.ReviewTracker;
import gov.nih.nci.ncicb.cadsr.loader.ReviewTrackerType;
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.SearchEvent;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.SearchListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.RunMode;
import gov.nih.nci.ncicb.cadsr.loader.util.OCRRoleNameBuilder;
import gov.nih.nci.ncicb.cadsr.loader.validator.ConceptValidator;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import gov.nih.nci.ncicb.cadsr.loader.validator.*;

public class ValidateConceptsDialog {

  private ConceptValidator conceptValidator;

  private ValidateConceptsDialog2 dialog = null;

  public void init(JFrame owner) {
    dialog = new ValidateConceptsDialog2(owner);
    dialog.setConceptValidator(conceptValidator);
    dialog.init();
  }

  public void display() {
    dialog.setVisible(true);
  }

  public void setConceptValidator(ConceptValidator conceptValidator) {
    this.conceptValidator = conceptValidator;
  }

  public void addSearchListener(SearchListener l) {
    dialog.addSearchListener(l);
  }

}


// TODO this was not well thought out and will need to be refactored at some point. 
class ValidateConceptsDialog2 extends JDialog 
  implements ListSelectionListener, ProgressListener
{
  private Map<Concept, Concept> errorList = new HashMap<Concept, Concept>();
  private Map<Concept, Concept> errorNameList = new HashMap<Concept, Concept>();

  
  private JSplitPane jSplitPane1 = new JSplitPane();
  private JSplitPane jSplitPaneEvs = new JSplitPane();
  private JSplitPane leftSplitPane = new JSplitPane();
  
  private JEditorPane elementPane = new JEditorPane("text/html", "");
  private JEditorPane evsByCodePane = new JEditorPane("text/html", "");
  private JEditorPane evsByNamePane = new JEditorPane("text/html", "");
    
  private DefaultListModel discrepancyListModel = new DefaultListModel(),
                        willNotLoadListModel = new DefaultListModel();
  private JList discrepancyList, willNotLoadList;
  private JScrollPane discrepancyListScrollPane, willNotLoadListScrollPane;
    
  private List<Concept> highlightDifferentNameByCode = new ArrayList<Concept>();
  private List<Concept> highlightDifferentDefByCode = new ArrayList<Concept> ();
  private List<Concept> highlightDifferentCodeByName = new ArrayList<Concept> ();
  private List<Concept> highlightDifferentDefByName = new ArrayList<Concept> ();
  
  private ProgressPanel progressPanel = new ProgressPanel(100);

  private AcListElementWrapper value;
  
  private JLabel order;
  
  private List<SearchListener> searchListeners = new ArrayList<SearchListener>();
  
  private ReviewTracker reviewTracker;

  private ConceptValidator conceptValidator;

  public ValidateConceptsDialog2(JFrame owner) {
    super(owner, "Validate Concepts");

    RunMode runMode = (RunMode)(UserSelections.getInstance().getProperty("MODE"));
    if(runMode.equals(RunMode.Curator)) {
      reviewTracker = ReviewTracker.getInstance(ReviewTrackerType.Curator);
    } else {
      reviewTracker = ReviewTracker.getInstance(ReviewTrackerType.Owner);
    }
  }

  public void setConceptValidator(ConceptValidator conceptValidator) {
    this.conceptValidator = conceptValidator;
  }

  public void init() {
    
    this.getContentPane().setLayout(new BorderLayout());
    
    SwingWorker worker = new SwingWorker() {
        public Object construct() {    
          List<Concept> concepts = ElementsLists.getInstance().
            getElements(DomainObjectFactory.newConcept());
          
          ProgressEvent event = new ProgressEvent();
          event.setGoal(concepts.size() + 1);
          event.setMessage("Validating Concepts");
          progressPanel.newProgressEvent(event);
          
          ValidationItems.getInstance().clear();
          
          conceptValidator.addProgressListener(progressPanel);
          ValidationItems vItems = conceptValidator.validate();
          
          List<ValidationItem> items = new ArrayList<ValidationItem>(vItems.getErrors());
          items.addAll(vItems.getWarnings());

          List<Concept> willNotLoadConceptList = new ArrayList<Concept>();

          for(ValidationItem vItem : items) {
            if(vItem.getRootCause() instanceof ConceptMismatchWrapper) {
              if(((ConceptMismatchWrapper)vItem.getRootCause()).getType() == 1) {
                highlightDifferentNameByCode.add(((ConceptMismatchWrapper)vItem.getRootCause()).getEvsConcept());
                errorList.put(((ConceptMismatchWrapper)vItem.getRootCause()).getModelConcept(), 
                              ((ConceptMismatchWrapper)vItem.getRootCause()).getEvsConcept());
              }    
              if(((ConceptMismatchWrapper)vItem.getRootCause()).getType() == 2) {
                highlightDifferentDefByCode.add(((ConceptMismatchWrapper)vItem.getRootCause()).getEvsConcept());
                errorList.put(((ConceptMismatchWrapper)vItem.getRootCause()).getModelConcept(), 
                              ((ConceptMismatchWrapper)vItem.getRootCause()).getEvsConcept());
              }    
              if(((ConceptMismatchWrapper)vItem.getRootCause()).getType() == 3) {
                highlightDifferentCodeByName.add(((ConceptMismatchWrapper)vItem.getRootCause()).getEvsConcept());
                errorNameList.put(((ConceptMismatchWrapper)vItem.getRootCause()).getModelConcept(), 
                                  ((ConceptMismatchWrapper)vItem.getRootCause()).getEvsConcept());
              }    
              if(((ConceptMismatchWrapper)vItem.getRootCause()).getType() == 4) {
                highlightDifferentDefByName.add(((ConceptMismatchWrapper)vItem.getRootCause()).getEvsConcept());
                errorNameList.put(((ConceptMismatchWrapper)vItem.getRootCause()).getModelConcept(), 
                                  ((ConceptMismatchWrapper)vItem.getRootCause()).getEvsConcept());
              }
            } else if (vItem.getRootCause() instanceof Concept){
              Concept _con = (Concept)vItem.getRootCause();
              willNotLoadConceptList.add(_con);
            }
          }

          for(Concept concept : errorList.keySet()) {
            List<AcListElementWrapper> l = getAcListElementWrappers(concept);
            for(AcListElementWrapper wrapper : l) {
              discrepancyListModel.addElement(wrapper);
            }
          }

          for(Concept concept : willNotLoadConceptList) {
            List<AcListElementWrapper> l = getAcListElementWrappers(concept);
            for(AcListElementWrapper wrapper : l) {
              willNotLoadListModel.addElement(wrapper);
            }
          }
          
          event = new ProgressEvent();
          event.setMessage("Done ");
          event.setStatus(100);
          event.setGoal(100);
          progressPanel.newProgressEvent(event);
          

          return null;
        }
      };
    worker.start();
    
    final int LIST_SIZE = 390;
    
    discrepancyList = new JList(discrepancyListModel);
    discrepancyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    discrepancyList.setBorder(javax.swing.BorderFactory.createTitledBorder("Discrepancies"));
    discrepancyListScrollPane = new JScrollPane(discrepancyList);        

    willNotLoadList = new JList(willNotLoadListModel);
    willNotLoadList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    willNotLoadList.setBorder(javax.swing.BorderFactory.createTitledBorder("Not in caDSR or EVS"));
    willNotLoadListScrollPane = new JScrollPane(willNotLoadList);        
    
    discrepancyList.addListSelectionListener(this);
    discrepancyList.setCellRenderer(new MyCellRenderer());

    willNotLoadList.addListSelectionListener(new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
          if (e.getValueIsAdjusting() == false) {
            if(willNotLoadList.getSelectedIndex() != 1) {
              value = (AcListElementWrapper)willNotLoadList.getSelectedValue();
              AdminComponent ac = value.getAc();
              
              // TODO: this is slightly ugly, is there a better name way?
              if (ac instanceof ObjectClassRelationship) {
                ObjectClassRelationship ocr = (ObjectClassRelationship)ac;
                OCRRoleNameBuilder nameBuilder = new OCRRoleNameBuilder();
                String displayName = nameBuilder.buildDisplayRoleName(ocr);
                SearchEvent searchEvent = new SearchEvent(displayName, false,false,true);
                searchEvent.setExactMatch(true);
                fireSearchEvent(searchEvent);
                return;
              }
              
              int ind = ac.getLongName().lastIndexOf(".");
              String className = ac.getLongName().substring(ind + 1);
              String split[] = className.split(":");
              if(split.length > 1) {
                className = split[0];
                SearchEvent searchEvent = new SearchEvent(className, false,false,true);
                searchEvent.setExactMatch(true);
                fireSearchEvent(searchEvent);
                className = split[1];
                SearchEvent searchEvent2 = new SearchEvent(className, false,false,false);
                searchEvent2.setExactMatch(true);
                fireSearchEvent(searchEvent2);
              }  
              else {
                SearchEvent searchEvent = new SearchEvent(className, false,false,true);
                searchEvent.setExactMatch(true);
                fireSearchEvent(searchEvent);
              }
            }
          }
        }
      });
    willNotLoadList.setCellRenderer(new MyCellRenderer());
    
    JPanel mainPanel = new JPanel();
    order = new JLabel("");    

    order.setBounds(new Rectangle(110, 0, 220, 20));
    mainPanel.setSize(new Dimension(400, 440));
    mainPanel.setLayout(null);

    leftSplitPane.setBounds(new Rectangle(10, 30, 140, LIST_SIZE));
    //     listScrollPane.setBounds(new Rectangle(10, 30, 140, LIST_SIZE));
    
    jSplitPane1.setBounds(new Rectangle(160, 30, 220, LIST_SIZE));
    
    jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
    
    jSplitPane1.setDividerLocation((int)(LIST_SIZE * 2 / 3));
    jSplitPaneEvs.setOrientation(JSplitPane.VERTICAL_SPLIT);
    
    evsByCodePane.setBorder(javax.swing.BorderFactory.createTitledBorder("EVS Concept By Code"));
    elementPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Element Concept"));
    evsByNamePane.setBorder(javax.swing.BorderFactory.createTitledBorder("EVS Concept By Name"));
    
    elementPane.setEditable(false);
    evsByCodePane.setEditable(false);
    evsByNamePane.setEditable(false);
    
    JScrollPane elementScrollPane = new JScrollPane(elementPane);
    JScrollPane evsByCodeScrollPane = new JScrollPane(evsByCodePane);
    JScrollPane evsByNameScrollPane = new JScrollPane(evsByNamePane);
    
    jSplitPaneEvs.setDividerLocation(LIST_SIZE / 3);
    jSplitPaneEvs.add(evsByCodeScrollPane, JSplitPane.BOTTOM);
    jSplitPaneEvs.add(evsByNameScrollPane, JSplitPane.TOP);
    
    jSplitPane1.add(elementScrollPane, JSplitPane.BOTTOM);
    jSplitPane1.add(jSplitPaneEvs, JSplitPane.TOP);

    leftSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    leftSplitPane.setDividerLocation(230);
    leftSplitPane.add(discrepancyListScrollPane, JSplitPane.TOP);
    leftSplitPane.add(willNotLoadListScrollPane, JSplitPane.BOTTOM);
    
    mainPanel.add(jSplitPane1, null);
    mainPanel.add(leftSplitPane, null);
    mainPanel.add(order);
    
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(mainPanel, BorderLayout.CENTER);
    
    this.getContentPane().add(new JLabel("Select an element from the list to view the concepts' information"));
    this.setSize(400, 510);
    this.getContentPane().add(progressPanel, BorderLayout.SOUTH);
    this.setResizable(false);
    UIUtil.putToCenter(this);

  }

  private List<AcListElementWrapper> getAcListElementWrappers(Concept concept) {
    List<ObjectClass> ocs = ElementsLists.getInstance().
      getElements(DomainObjectFactory.newObjectClass());
    
    List<DataElement> des = ElementsLists.getInstance().
      getElements(DomainObjectFactory.newDataElement());
    
    List<ObjectClassRelationship> ocrs = ElementsLists.getInstance().
      getElements(DomainObjectFactory.newObjectClassRelationship());
    
    List<AcListElementWrapper> result = new ArrayList<AcListElementWrapper>();
    
    for(ObjectClass oc : ocs) {
      String temp = oc.getPreferredName();
      String split[] = temp.split(":");
      for(int i = 0; i < split.length; i++)
        if(split[i].equals(concept.getPreferredName())) 
          result.add(new AcListElementWrapper(oc, concept));
    }
    
    for(DataElement de : des) {
      DataElementConcept dec = de.getDataElementConcept();
      String fullName = null;
      for(AlternateName an : de.getAlternateNames()) {
        if(an.getType().equals(AlternateName.TYPE_FULL_NAME))
          fullName = an.getName();
      }
      Boolean reviewed = reviewTracker.get(fullName);
      if(reviewed != null) {
        String temp = dec.getProperty().getPreferredName();
        String split[] = temp.split(":");
        for(int i = 0; i < split.length; i++)
          if(split[i].equals(concept.getPreferredName()))
            result.add(new AcListElementWrapper(dec, concept));
      }
    }
    
    for(ObjectClass oc : ocs) {
      String temp = oc.getLongName();
      if(temp.equals(concept.getLongName())) 
        result.add(new AcListElementWrapper(oc, concept));
    }
    
    for(DataElement de : des) {
      DataElementConcept dec = de.getDataElementConcept();
      String fullName = null;
      for(AlternateName an : de.getAlternateNames()) {
        if(an.getType().equals(AlternateName.TYPE_FULL_NAME))
          fullName = an.getName();
      }
      Boolean reviewed = reviewTracker.get(fullName);
      if(reviewed != null) {
        String temp = dec.getProperty().getLongName();
        if(temp.equals(concept.getLongName()))
          result.add(new AcListElementWrapper(dec, concept));
      }
    }
          
    for(ObjectClassRelationship ocr : ocrs) {
      ConceptDerivationRule cdr = ocr.getConceptDerivationRule();
      if(cdr != null)
        for(ComponentConcept cc : cdr.getComponentConcepts()) {
          if (cc.getConcept().getPreferredName().equals(
                                                        concept.getPreferredName())) {
            result.add(new AcListElementWrapper(ocr, concept));
          }
        }
      cdr = ocr.getSourceRoleConceptDerivationRule();
      if(cdr != null)
        for(ComponentConcept cc : cdr.getComponentConcepts()) {
          if (cc.getConcept().getPreferredName().equals(
                                                        concept.getPreferredName())) {
            result.add(new AcListElementWrapper(ocr, "Source", concept));
          }
        }
      cdr = ocr.getTargetRoleConceptDerivationRule();
      if(cdr != null)
        for(ComponentConcept cc : cdr.getComponentConcepts()) {
          if (cc.getConcept().getPreferredName().equals(
                                                        concept.getPreferredName())) {
            result.add(new AcListElementWrapper(ocr, "Target", concept));
          }
        }
    }
    return result;
  }
  
  public void newProgressEvent(ProgressEvent evt) 
  {
    evt.getStatus();
  }
  
  public void valueChanged(ListSelectionEvent e) {
    if (e.getValueIsAdjusting() == false) {
      if (discrepancyList.getSelectedIndex() != -1) {
        value = (AcListElementWrapper)discrepancyList.getSelectedValue();
        order.setText(value.getOrder());
        elementPane.setText(getConceptHtml(value.getConcept()));
        elementPane.setCaretPosition(0);
        evsByCodePane.setText(getHighlightConceptHtmlByCode(errorList.get(value.getConcept())));
        evsByCodePane.setCaretPosition(0);
        evsByNamePane.setText(getHighlightConceptHtmlByName(errorNameList.get(value.getConcept())));
        evsByNamePane.setCaretPosition(0);
                
        AdminComponent ac = value.getAc();
        
        // TODO: this is slightly ugly, is there a better name way?
        if (ac instanceof ObjectClassRelationship) {
            ObjectClassRelationship ocr = (ObjectClassRelationship)ac;
            OCRRoleNameBuilder nameBuilder = new OCRRoleNameBuilder();
            String displayName = nameBuilder.buildDisplayRoleName(ocr);
            SearchEvent searchEvent = new SearchEvent(displayName, false,false,true);
            searchEvent.setExactMatch(true);
            fireSearchEvent(searchEvent);
            return;
        }
        
        int ind = ac.getLongName().lastIndexOf(".");
        String className = ac.getLongName().substring(ind + 1);
        String split[] = className.split(":");
        if(split.length > 1) {
          className = split[0];
          SearchEvent searchEvent = new SearchEvent(className, false,false,true);
          searchEvent.setExactMatch(true);
          fireSearchEvent(searchEvent);
          className = split[1];
          SearchEvent searchEvent2 = new SearchEvent(className, false,false,false);
          searchEvent2.setExactMatch(true);
          fireSearchEvent(searchEvent2);
        }  
        else {
          SearchEvent searchEvent = new SearchEvent(className, false,false,true);
          searchEvent.setExactMatch(true);
          fireSearchEvent(searchEvent);
        }
      }
    }
  }
  
  private String getHighlightConceptHtmlByCode(Concept con) 
  {
    StringBuilder sb = new StringBuilder();
    if(con != null) {
      if(con.getPreferredName() == null)
        sb.append("<b>Code: </b>" + "None Provided" + "<br>");
      else
        sb.append("<b>Code: </b>" + con.getPreferredName() + "<br>");
          
      if(highlightDifferentNameByCode.contains(con))
        if(con.getLongName() == null)
          sb.append("<div bgcolor='yellow'>" + "<b>Preferred Name: </b>" + "None Provided" + "</div>" + "<br>");
        else
          sb.append("<div bgcolor='yellow'>" + "<b>Preferred Name: </b>" + con.getLongName() + "</div>" + "<br>");
      else if(con.getLongName() == null)
          sb.append("<b>Preferred Name: </b>" + "None Provided" + "<br>");  
      else    
          sb.append("<b>Preferred Name: </b>" + con.getLongName() + "<br>");
      if(highlightDifferentDefByCode.contains(con))
        if(con.getPreferredDefinition() == null)
          sb.append("<div bgcolor='yellow'>" + "<b>Definition: </b>" + "None Provided" + "</div>" + "<br>");
        else
          sb.append("<div bgcolor='yellow'>" + "<b>Definition: </b>" + con.getPreferredDefinition() + "</div>" + "<br>");
      else if(con.getPreferredDefinition() == null)
          sb.append("<b>Definition: </b>" + "None Provided" + "<br>");
      else
          sb.append("<b>Definition: </b>" + con.getPreferredDefinition() + "<br>");
      
      sb.append("</body></html>");
    }
    return sb.toString();
  }
  
  private String getHighlightConceptHtmlByName(Concept con) 
  {
    StringBuilder sb = new StringBuilder();
    if(con != null) {
      if(highlightDifferentCodeByName.contains(con))
        if(con.getPreferredName() == null)
          sb.append("<div bgcolor='yellow'>" + "<b>Code: </b>" + "None Provided" + "</div>" + "<br>");
        else
          sb.append("<div bgcolor='yellow'>" + "<b>Code: </b>" + con.getPreferredName() + "</div>" + "<br>");
      else if(con.getPreferredName() == null)
        sb.append("<b>Code: </b>" + "None Provided" + "<br>");
      else  
        sb.append("<b>Code: </b>" + con.getPreferredName() + "<br>");
      if(con.getLongName() == null)
        sb.append("<b>Preferred Name: </b>" + "None Provided" + "<br>");
      else
        sb.append("<b>Preferred Name: </b>" + con.getLongName() + "<br>");
      if(highlightDifferentDefByName.contains(con))
        if(con.getPreferredDefinition() == null)
          sb.append("<div bgcolor='yellow'>" + "<b>Definition: </b>" + "None Provided" + "</div>" + "<br>");
        else
          sb.append("<div bgcolor='yellow'>" + "<b>Definition: </b>" + con.getPreferredDefinition() + "</div>" + "<br>");
      else if(con.getPreferredDefinition() == null)
        sb.append("<b>Definition: </b>" + "None Provided" + "<br>");
      else 
        sb.append("<b>Definition: </b>" + con.getPreferredDefinition() + "<br>");
      
      sb.append("</body></html>");
    }
    return sb.toString();
  }
  
  
  private String getConceptHtml(Concept con) 
  {
    StringBuilder sb = new StringBuilder();
    if(con != null) {
      sb.append("<html><body>");
      if(con.getPreferredName() == null)
        sb.append("<b>Code: </b>" + "None Provided" + "<br>");
      else
        sb.append("<b>Code: </b>" + con.getPreferredName() + "<br>");
      if(con.getLongName() == null)
        sb.append("<b>Preferred Name: </b>" + "None Provided" + "<br>");
      else
        sb.append("<b>Preferred Name: </b>" + con.getLongName() + "<br>");
      if(con.getPreferredDefinition() == null)
        sb.append("<b>Definition: </b>" + "None Provided" + "<br>");
      else
        sb.append("<b>Definition: </b>" + con.getPreferredDefinition() + "<br>");
      sb.append("</body></html>");
    }

    return sb.toString();
  }
  
  public void addSearchListener(SearchListener listener) 
  {
    searchListeners.add(listener);
  }
  
  public void fireSearchEvent(SearchEvent event) 
  {
    for(SearchListener l : searchListeners)
      l.search(event);
  }
  
  class AcListElementWrapper<T extends AdminComponent>
{
  private T ac;
  private Concept con;
  private String subComponent;

  AcListElementWrapper(T ac, Concept con) 
  {
    this.ac = ac;
    this.con = con;
    this.subComponent = null;
  }
  AcListElementWrapper(T ac, String subComponent, Concept con) 
  {
    this.ac = ac;
    this.con = con;
    this.subComponent = subComponent;
  }
  T getAc() 
  {
    return ac;
  }
  
  public String getSubComponent() {
    return subComponent;
  }
  
  Concept getConcept() 
  {
    return con;
  }
  
  String getOrder() 
  {
    String order = "";
    int index = -1;
    if(ac instanceof ObjectClass) {
      String prefName = ac.getPreferredName();
      String split[] = prefName.split(":");
  
      for(int i = 0; i < split.length; i++)
        if(split[i].equals(con.getPreferredName()))
          index = i;
    
      if(split.length -1 == index)
        order = "Class Primary Concept";
      else
        order = "Class Qualifier Concept #" + (split.length - 1 - index);
    }
    
    if(ac instanceof DataElementConcept) {
      String prefName = ((DataElementConcept)ac).getProperty().getPreferredName();;
      String split[] = prefName.split(":");

      for(int i = 0; i < split.length; i++)
        if(split[i].equals(con.getPreferredName()))
          index = i;
    
      if(split.length -1 == index)
        order = "Attribute Primary Concept";
      else
        order = "Attribute Qualifier Concept #" + (split.length - 1 - index);
    }
    return order;
  }
  
}
class MyCellRenderer extends JLabel implements ListCellRenderer {
  public MyCellRenderer() {
    setOpaque(true);
  }
  public Component getListCellRendererComponent
    (
     JList list,
     Object value,
     int index,
     boolean isSelected,
     boolean cellHasFocus)
  {
    
    AcListElementWrapper acW = (AcListElementWrapper)value;
    AdminComponent ac = acW.getAc();
    
    // TODO: this is slightly ugly, is there a better name way?
    if (ac instanceof ObjectClassRelationship) {
      ObjectClassRelationship ocr = (ObjectClassRelationship)ac;
      OCRRoleNameBuilder nameBuilder = new OCRRoleNameBuilder();
      String displayName = nameBuilder.buildDisplayRoleName(ocr);
      String subComponent = acW.getSubComponent();
      if (subComponent != null) {
        setText(displayName+" "+subComponent);
      }
      else {
        setText(displayName);
      }
      
    }
    else {
      int ind = ac.getLongName().lastIndexOf(".");
      String className = ac.getLongName().substring(ind + 1);
      setText(className);
    }
    
    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    }
    else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }
    setEnabled(list.isEnabled());
    return this;
  }
}

}

