package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.Representation;
import gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.event.ElementChangeEvent;
import gov.nih.nci.ncicb.cadsr.loader.event.ElementChangeListener;
import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModuleListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.UMLNode;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.ValueDomainNode;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.ConventionUtil;

import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ValueDomainViewPanel extends JPanel 
    implements Editable, CadsrModuleListener, ItemListener, DocumentListener
{
  private JLabel vdPrefDefTitleLabel = new JLabel("VD Preferred Definition"),
    vdDatatypeTitleLabel = new JLabel("VD Datatype"),
    vdTypeTitleLabel = new JLabel("VD Type"),
    vdCdIdTitleLabel = new JLabel("VD CD PublicId / Version"),
    vdRepIdTitleLabel = new JLabel("Representation PublicId / Version"),
    vdCdLongNameTitleLabel = new JLabel("VD CD Long Name"),
    vdUnitOfMeasureTitleLabel = new JLabel("Unit Of Measure"),
    vdDisplayFormatTitleLabel = new JLabel("Display Format"),
    vdMinimumLengthTitleLabel = new JLabel("Minimum Length"),
    vdMaximumLengthTitleLabel = new JLabel("Maximum Length"),
    vdDecimalPlaceTitleLabel = new JLabel("Decimal Place"),
    vdHighValueTitleLabel = new JLabel("High Value"),
    vdLowValueTitleLabel = new JLabel("Low Value");
  
  
  // !! TODO REFACTOR THESE PUBLIC FIELDS. GURU MEDITATION.
  public JTextArea vdPrefDefValueTextField = new JTextArea();
  private CadsrDialog cadsrCDDialog;
  private CadsrDialog cadsrREPDialog;

  public JComboBox vdDatatypeValueCombobox = null;
  private JRadioButton vdTypeERadioButton = new JRadioButton("E");
  private JRadioButton vdTypeNRadioButton = new JRadioButton("N");
  public JRadioButton tmpInvisible = new JRadioButton("dummy");
  private ButtonGroup vdTypeRadioButtonGroup = new ButtonGroup();

  private JPanel optionalPanel;
  
  public JLabel vdCDPublicIdJLabel = null;
  public JLabel vdCdLongNameValueJLabel = null;
  private JButton cdSearchButton = new JButton("Search");
  private JButton repSearchButton = new JButton("Search");
  public JLabel vdRepIdValueJLabel = null;
  
  private JComboBox vdUnitOfMeasureValueCombobox = new JComboBox();
  private JComboBox vdDisplayFormatValueCombobox =  new JComboBox();
  private JTextField vdMinimumLengthValueTextField;
  private JTextField vdMaximumLengthValueTextField;
  private JTextField vdDecimalPlaceValueTextField;
  private JTextField vdHighValueTextField;
  private JTextField vdLowValueTextField;

  private JScrollPane scrollPane;
  private List datatypeList = null,
  uomList = null, displayFormatList = null;
  
  private JLabel explainLabel;
  private ValueDomain vd, tempVD;
  private ApplyButtonPanel applyButtonPanel;
  private UMLNode umlNode;
  private CadsrModule cadsrModule;

  private ReferenceDocumentsPanel refDocPanel = null;

  private boolean isInitialized = false;
  private List<ElementChangeListener> changeListeners = new ArrayList<ElementChangeListener>();
  private List<PropertyChangeListener> propChangeListeners = new ArrayList<PropertyChangeListener>();  

  private ReferenceDocumentsPanel refDocDialog;
  
  public void addPropertyChangeListener(PropertyChangeListener l) {
      super.addPropertyChangeListener(l);;
      propChangeListeners.add(l);
  }

  public ValueDomainViewPanel() {
  }
  
  public void update(ValueDomain vd, UMLNode umlNode) 
  {
    this.vd = vd;
    this.umlNode = umlNode;

    tempVD = DomainObjectFactory.newValueDomain();
    tempVD.setConceptualDomain(vd.getConceptualDomain());
    tempVD.setPreferredDefinition(vd.getPreferredDefinition());
    tempVD.setRepresentation(vd.getRepresentation());
    tempVD.setDataType(vd.getDataType());
    tempVD.setVdType(vd.getVdType());
    tempVD.setUOMName(vd.getUOMName());
    tempVD.setFormatName(vd.getFormatName());
    tempVD.setMaximumLength(vd.getMaximumLength());
    tempVD.setMinimumLength(vd.getMinimumLength());
    tempVD.setDecimalPlace(vd.getDecimalPlace());
    tempVD.setHighValue(vd.getHighValue());
    tempVD.setLowValue(vd.getLowValue());

    tempVD.setReferenceDocuments(vd.getReferenceDocuments());
    
    vd = null;

    if(!isInitialized)
      initUI();

    applyButtonPanel.update();
    applyButtonPanel.propertyChange(new PropertyChangeEvent(this, ButtonPanel.SETUP, null, true));

    initValues();
  }
  
  private void initUI() 
  {
    isInitialized = true;

    this.setLayout(new BorderLayout());
    JPanel mainPanel = new JPanel(new GridBagLayout());
    optionalPanel = new JPanel(new GridBagLayout());
    optionalPanel.setBorder(BorderFactory.createTitledBorder("Optional"));
    applyButtonPanel = new ApplyButtonPanel(this, (ValueDomainNode)umlNode);
    addPropertyChangeListener(applyButtonPanel);

    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    populateDatatypeCombobox();
    this.setCursor(Cursor.getDefaultCursor());
 
    JPanel vdTypeRadioButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    vdTypeRadioButtonGroup.add(vdTypeERadioButton);
    vdTypeRadioButtonGroup.add(vdTypeNRadioButton);
    vdTypeRadioButtonGroup.add(tmpInvisible);
    vdTypeRadioButtonPanel.add(vdTypeERadioButton);
    vdTypeRadioButtonPanel.add(vdTypeNRadioButton);
    vdTypeRadioButtonPanel.add(tmpInvisible);
    tmpInvisible.setVisible(false);
      
    JPanel vdCDIdVersionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    vdCDPublicIdJLabel = new JLabel();
    vdCDIdVersionPanel.add(vdCDPublicIdJLabel);
    vdCDIdVersionPanel.add(cdSearchButton);
    cdSearchButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            cadsrCDDialog.setAlwaysOnTop(true);
            cadsrCDDialog.setVisible(true);
            ConceptualDomain cd = (ConceptualDomain)cadsrCDDialog.getAdminComponent();
            if(cd == null) return;
            tempVD.setConceptualDomain(cd);
            //initValues();
            setVdCdSearchedValues();
            firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, true));
          }});
    
    repSearchButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          cadsrREPDialog.setAlwaysOnTop(true);
          cadsrREPDialog.startSearchPreferredRepTerms();
          cadsrREPDialog.setVisible(true);
          Representation rep = (Representation)cadsrREPDialog.getAdminComponent();
          if(rep == null) return;
          tempVD.setRepresentation(rep);
          //initValues();
          setRepSearchedValues();
          firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, true));
        }});
    vdCdLongNameValueJLabel = new JLabel();

    JPanel vdCDRepPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    vdRepIdValueJLabel = new JLabel();
    vdCDRepPanel.add(vdRepIdValueJLabel);
    vdCDRepPanel.add(repSearchButton);
    
    vdPrefDefValueTextField.setLineWrap(true);
    vdPrefDefValueTextField.setWrapStyleWord(true);
    scrollPane = new JScrollPane(vdPrefDefValueTextField);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    scrollPane = new JScrollPane(vdPrefDefValueTextField);
    scrollPane.setPreferredSize(new Dimension(200, 100));
    
    UIUtil.insertInBag(mainPanel, vdPrefDefTitleLabel, 0, 1);
    UIUtil.insertInBag(mainPanel, scrollPane, 1, 1, 3, 1); 
    
    UIUtil.insertInBag(mainPanel, vdDatatypeTitleLabel, 0, 3);
    UIUtil.insertInBag(mainPanel, vdDatatypeValueCombobox, 1, 3);
    
    UIUtil.insertInBag(mainPanel, vdTypeTitleLabel, 0, 4);
    UIUtil.insertInBag(mainPanel, vdTypeRadioButtonPanel, 1, 4);
    
    UIUtil.insertInBag(mainPanel, vdCdIdTitleLabel, 0, 5);
    UIUtil.insertInBag(mainPanel, vdCDIdVersionPanel, 1, 5);
    
    UIUtil.insertInBag(mainPanel, vdCdLongNameTitleLabel, 0, 6);
    UIUtil.insertInBag(mainPanel, vdCdLongNameValueJLabel, 1, 6);

    UIUtil.insertInBag(mainPanel, vdRepIdTitleLabel, 0, 7);
    UIUtil.insertInBag(mainPanel, vdCDRepPanel, 1, 7);


    // START: Data population for optional panel fields goes here
    populateUOMCombobox();
    populateDFCombobox();

//     vdHighValuevalueTextArea.setLineWrap(true);
//     vdHighValuevalueTextArea.setWrapStyleWord(true);
//     vdHighValueScrollPane = new JScrollPane(vdHighValuevalueTextArea);
//     vdHighValueScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//     vdHighValueScrollPane.setPreferredSize(new Dimension(200, 100));
    
//     vdLowValuevalueTextArea.setLineWrap(true);
//     vdLowValuevalueTextArea.setWrapStyleWord(true);
//     vdLowValueScrollPane = new JScrollPane(vdLowValuevalueTextArea);
//     vdLowValueScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//     vdLowValueScrollPane.setPreferredSize(new Dimension(200, 100));
    
    vdLowValueTextField = new JFormattedTextField(new RegexFormatter("\\d*\\.?\\d*", 255));
    vdHighValueTextField = new JFormattedTextField(new RegexFormatter("\\d*\\.?\\d*", 255));

    vdLowValueTextField.setColumns(10);
    vdHighValueTextField.setColumns(10);
    
    vdMinimumLengthValueTextField = new JFormattedTextField(new RegexFormatter("\\d{0,8}"));
    vdMaximumLengthValueTextField = new JFormattedTextField(new RegexFormatter("\\d{0,8}"));
    vdDecimalPlaceValueTextField = new JFormattedTextField(new RegexFormatter("\\d{0,2}"));
    
    vdMinimumLengthValueTextField.setColumns(8);
    vdMaximumLengthValueTextField.setColumns(8);
    vdDecimalPlaceValueTextField.setColumns(8);
    
    explainLabel = new JLabel("<html><u color=BLUE>Explain this</u></html>");
    ToolTipManager.sharedInstance().registerComponent(explainLabel);
    ToolTipManager.sharedInstance().setDismissDelay(3600000);

    JPanel explainPanel = new JPanel();
    explainPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    explainPanel.add(explainLabel);
    explainLabel.setToolTipText(PropertyAccessor.getProperty("value.domain.view.explain.this"));

    UIUtil.insertInBag(optionalPanel, vdUnitOfMeasureTitleLabel, 0, 2);
    UIUtil.insertInBag(optionalPanel, vdUnitOfMeasureValueCombobox, 1, 2); 
    UIUtil.insertInBag(optionalPanel, explainPanel, 2, 2); 
    
    UIUtil.insertInBag(optionalPanel, vdDisplayFormatTitleLabel, 0, 3);
    UIUtil.insertInBag(optionalPanel, vdDisplayFormatValueCombobox, 1, 3); 
    
    UIUtil.insertInBag(optionalPanel, vdMinimumLengthTitleLabel, 0, 4);
    UIUtil.insertInBag(optionalPanel, vdMinimumLengthValueTextField, 1, 4);
    
    UIUtil.insertInBag(optionalPanel, vdMaximumLengthTitleLabel, 0, 5);
    UIUtil.insertInBag(optionalPanel, vdMaximumLengthValueTextField, 1, 5);

    UIUtil.insertInBag(optionalPanel, vdDecimalPlaceTitleLabel, 0, 6);
    UIUtil.insertInBag(optionalPanel, vdDecimalPlaceValueTextField, 1, 6);
    
    UIUtil.insertInBag(optionalPanel, vdHighValueTitleLabel, 0, 7);
    UIUtil.insertInBag(optionalPanel, vdHighValueTextField, 1, 7, 3, 1);
    
    UIUtil.insertInBag(optionalPanel, vdLowValueTitleLabel, 0, 9);
    UIUtil.insertInBag(optionalPanel, vdLowValueTextField, 1, 9, 3, 1);

    JButton refDocButton = new JButton("Reference Documents");
    refDocButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          refDocDialog.setVisible(true);
          if(refDocDialog.getCloseStatus() == ReferenceDocumentsPanel.CLOSE_STATUS_OK) {
            tempVD.setReferenceDocuments(refDocDialog.getResult());
          } else {
            refDocDialog.setReferenceDocuments(tempVD.getReferenceDocuments());
          }
        }
      });
//     UIUtil.insertInBag(optionalPanel, refDocButton, 0, 10, 2, 1);

    // END: Data population for optional panel fields goes here

    JScrollPane scrollPane = new JScrollPane(mainPanel);
    scrollPane.getVerticalScrollBar().setUnitIncrement(30);
    JScrollPane optionalPanelScrollPane = new JScrollPane(optionalPanel);
    optionalPanelScrollPane.getVerticalScrollBar().setUnitIncrement(30);
    
    this.add(mainPanel, BorderLayout.NORTH);
    this.add(optionalPanel, BorderLayout.SOUTH);
    
  }

  public void setExpanded(boolean b) {
    optionalPanel.setVisible(b);
  }

  private void populateUOMCombobox(){
      uomList = new ArrayList();
      String[] collUOM = PropertyAccessor.getProperty("vd.unit.of.measures").split(",");
      vdUnitOfMeasureValueCombobox = new JComboBox();
      vdUnitOfMeasureValueCombobox.addItem("NOT SPECIFIED");
      uomList.add("NOT SPECIFIED");
      for (String uomValue : collUOM){
          uomList.add(uomValue);
          vdUnitOfMeasureValueCombobox.addItem(uomValue);
      }
  }
  private void populateDFCombobox(){
      displayFormatList = new ArrayList();
      String[] collDisplayFormat = PropertyAccessor.getProperty("vd.display.format").split(",");
      vdDisplayFormatValueCombobox = new JComboBox();
      vdDisplayFormatValueCombobox.addItem("NOT SPECIFIED");
      displayFormatList.add("NOT SPECIFIED");
      for (String dfValue : collDisplayFormat){
          displayFormatList.add(dfValue);
          vdDisplayFormatValueCombobox.addItem(dfValue);
      }
  }
  
  private void initValues() 
  {
    vdPrefDefValueTextField.setText(tempVD.getPreferredDefinition());
    
    // Set Datatype
    {
      int index = tempVD.getDataType() == null ? -1 : getSelectedIndex(tempVD.getDataType(), datatypeList);
      vdDatatypeValueCombobox.setSelectedIndex(index == -1 ? 0 : index);
      
      if(vd.getVdType() != null && tempVD != null && tempVD.getVdType() != null){
        if(!vd.getVdType().equals("null")){
          if(!vd.getVdType().equals(null)){
            if(tempVD.getVdType().equals("E")){
              vdTypeERadioButton.setSelected(true);
              vdTypeNRadioButton.setSelected(false);
            }
            else if(tempVD.getVdType().equals("N")){
              vdTypeNRadioButton.setSelected(true);
              vdTypeERadioButton.setSelected(false);
            }
          }else{
            tmpInvisible.setSelected(true);
            vdTypeERadioButton.setSelected(false);
            vdTypeNRadioButton.setSelected(false);
          }
        }else{
          tmpInvisible.setSelected(true);
          vdTypeERadioButton.setSelected(false);
          vdTypeNRadioButton.setSelected(false);
        }
      }else{
        tmpInvisible.setSelected(true);
        vdTypeERadioButton.setSelected(false);
        vdTypeNRadioButton.setSelected(false);
      }
    }

    // Set Conceptual Domain
    {
      vdCDPublicIdJLabel.setText(ConventionUtil.publicIdVersion(tempVD.getConceptualDomain()));
      
      if(tempVD != null && tempVD.getConceptualDomain() != null && tempVD.getConceptualDomain().getLongName() != null
         && !tempVD.getConceptualDomain().getLongName().equals(""))
        vdCdLongNameValueJLabel.setText(tempVD.getConceptualDomain().getLongName());
      else
        vdCdLongNameValueJLabel.setText("Unable to lookup CD Long Name");
    }

    // Set Rep Term
    {
      vdRepIdValueJLabel.setText(ConventionUtil.publicIdVersion(tempVD.getRepresentation()));
    }

    // Set UOM
    {
      int index = tempVD.getUOMName() == null ? -1 : getSelectedIndex(tempVD.getUOMName(), uomList);
      vdUnitOfMeasureValueCombobox.setSelectedIndex(index == -1 ? 0 : index);
    }

    // Set Format
    {
      int index = tempVD.getFormatName() == null ? -1 : getSelectedIndex(tempVD.getFormatName(), displayFormatList);
      vdDisplayFormatValueCombobox.setSelectedIndex(index == -1 ? 0 : index);
    }

    if(tempVD.getMinimumLength() != null)
      vdMinimumLengthValueTextField.setText(tempVD.getMinimumLength().toString());
   else 
     vdMinimumLengthValueTextField.setText("");

    if(tempVD.getMaximumLength() != null)
      vdMaximumLengthValueTextField.setText(tempVD.getMaximumLength().toString());
   else 
     vdMaximumLengthValueTextField.setText("");

    if(tempVD.getDecimalPlace() != null)
      vdDecimalPlaceValueTextField.setText(tempVD.getDecimalPlace().toString());
   else 
     vdDecimalPlaceValueTextField.setText("");

    vdHighValueTextField.setText(tempVD.getHighValue());
    vdLowValueTextField.setText(tempVD.getLowValue());

    
    vdMinimumLengthValueTextField.setActionCommand("MIN_LENGTH");
    vdMaximumLengthValueTextField.setActionCommand("MAX_LENGTH");
    vdDecimalPlaceValueTextField.setActionCommand("DECIMAL_PLACE");

    vdPrefDefValueTextField.getDocument().addDocumentListener(this);
    vdMinimumLengthValueTextField.getDocument().addDocumentListener(this);
    vdMaximumLengthValueTextField.getDocument().addDocumentListener(this);
    vdDecimalPlaceValueTextField.getDocument().addDocumentListener(this);
    vdHighValueTextField.getDocument().addDocumentListener(this);
    vdLowValueTextField.getDocument().addDocumentListener(this);
    vdTypeERadioButton.addItemListener(this);
    vdTypeNRadioButton.addItemListener(this);
    vdDatatypeValueCombobox.addItemListener(this);
    vdUnitOfMeasureValueCombobox.addItemListener(this);
    vdDisplayFormatValueCombobox.addItemListener(this);

    // Set ReferenceDocuments
    refDocDialog = new ReferenceDocumentsPanel();
    refDocDialog.setReferenceDocuments(vd.getReferenceDocuments());
    
    firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, false));
  }
  
  private void setVdCdSearchedValues(){
      vdCDPublicIdJLabel.setText(ConventionUtil.publicIdVersion(tempVD.getConceptualDomain()));

      if(tempVD.getConceptualDomain().getLongName() != null
          && !tempVD.getConceptualDomain().getLongName().equals(""))
        vdCdLongNameValueJLabel.setText(tempVD.getConceptualDomain().getLongName());
      else
        vdCdLongNameValueJLabel.setText("Unable to lookup CD Long Name");
  }
  
  private void setRepSearchedValues(){
      vdRepIdValueJLabel.setText(ConventionUtil.publicIdVersion(tempVD.getRepresentation()));
  }
  
  public static void main(String args[]) 
  {
//    JFrame frame = new JFrame();
//    ValueDomainViewPanel vdPanel = new ValueDomainViewPanel();
//    vdPanel.setVisible(true);
//    frame.add(vdPanel);
//    frame.setVisible(true);
//    frame.setSize(450, 350);
  }

  public void applyPressed() {   
    vd.setPublicId(null);
    vd.setConceptualDomain(tempVD.getConceptualDomain());
    vd.setRepresentation(tempVD.getRepresentation());
    if(vdDatatypeValueCombobox.getSelectedIndex() != 0)
      vd.setDataType(String.valueOf(vdDatatypeValueCombobox.getSelectedItem()));
    if(!checkForNullOrZero(vdPrefDefValueTextField.getText()))
      vd.setPreferredDefinition(vdPrefDefValueTextField.getText());
    if(vdTypeERadioButton.isSelected())
      vd.setVdType("E");
    if(vdTypeNRadioButton.isSelected())
      vd.setVdType("N");
    vdTypeERadioButton.addItemListener(this);
    vdTypeNRadioButton.addItemListener(this);
    
    if(!vdUnitOfMeasureValueCombobox.getSelectedItem().toString().equals("NOT SPECIFIED"))
      vd.setUOMName(vdUnitOfMeasureValueCombobox.getSelectedItem().toString());

    if(!vdDisplayFormatValueCombobox.getSelectedItem().toString().equals("NOT SPECIFIED"))
      vd.setFormatName(vdDisplayFormatValueCombobox.getSelectedItem().toString());

    if(!checkForNullOrZero(vdMinimumLengthValueTextField.getText()))
      vd.setMinimumLength(Integer.parseInt(vdMinimumLengthValueTextField.getText()));
    else
      vd.setMinimumLength(null);
    if(!checkForNullOrZero(vdMaximumLengthValueTextField.getText()))
      vd.setMaximumLength(Integer.parseInt(vdMaximumLengthValueTextField.getText()));
    else
      vd.setMaximumLength(null);
    if(!checkForNullOrZero(vdDecimalPlaceValueTextField.getText()))
      vd.setDecimalPlace(Integer.parseInt(vdDecimalPlaceValueTextField.getText()));
    else
      vd.setDecimalPlace(null);
    vd.setHighValue(vdHighValueTextField.getText());
    vd.setLowValue(vdLowValueTextField.getText());
    
    firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, false));
    fireElementChangeEvent(new ElementChangeEvent((Object)umlNode));
  }

    public void setCadsrCDDialog(CadsrDialog cadsrCDDialog) {
      this.cadsrCDDialog = cadsrCDDialog;
    }

    public void setCadsrREPDialog(CadsrDialog cadsrREPDialog) {
      this.cadsrREPDialog = cadsrREPDialog;
    }

    public void setCadsrModule(CadsrModule cadsrModule){
      this.cadsrModule = cadsrModule;
    }
    
//  private String setTextFieldValue(int value){
//    
//    return value == 0 ? "" : Integer.toString(value);
//    
//  }
  
  /**
   * Returns true if the value is null.
   */

  private boolean checkForNullOrZero(String value){
    if(value == null || value.equals("null") || value.length() == 0)
      return true;
    else
      return false;
  }
  
  private void populateDatatypeCombobox(){
    datatypeList = new ArrayList();
    Collection<String> collDatatypes = cadsrModule.getAllDatatypes();
    vdDatatypeValueCombobox = new JComboBox();
    vdDatatypeValueCombobox.addItem("Please Select The Datatype");
    datatypeList.add("Please Select The Datatype");
    Iterator itr = collDatatypes.iterator();
    while(itr.hasNext()){
      String datatypeValue = (String) itr.next();
      datatypeList.add(datatypeValue);
      vdDatatypeValueCombobox.addItem(datatypeValue);
    }
  }
  
    private int getSelectedIndex(String selectedString, List lst){
        for(int i=0; i<lst.size(); i++)
            if(lst.get(i)!= null 
                && lst.get(i).toString().trim().equals(selectedString.trim()))
                return i;
        return -1;
    }
    
    public void addElementChangeListener(ElementChangeListener listener){
        changeListeners.add(listener);
    }
    private void fireElementChangeEvent(ElementChangeEvent event) {
      for(ElementChangeListener l : changeListeners)
        l.elementChanged(event);
    }
    private void firePropertyChangeEvent(PropertyChangeEvent evt) {
      for(PropertyChangeListener l : propChangeListeners) 
        l.propertyChange(evt);
    }

    public void itemStateChanged(ItemEvent e) {
        firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, true));
    }

    public void insertUpdate(DocumentEvent e) {
        firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, true));
    }

    public void removeUpdate(DocumentEvent e) {
        firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, true));
    }

    public void changedUpdate(DocumentEvent e) {
        firePropertyChangeEvent(new PropertyChangeEvent(this, ApplyButtonPanel.SAVE, null, true));
    }
    public UMLNode getNode() 
    {
      return umlNode;
    }
    public String getPubId() {
        return tempVD.getPublicId();
    }
  
  public void setReferenceDocumentsPanel(ReferenceDocumentsPanel p) {
    this.refDocPanel = p;
  }


}
