/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.ui;

import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.CadsrModuleListener;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.AttributeNode;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.NodeUtil;
import gov.nih.nci.ncicb.cadsr.loader.ui.tree.UMLNode;
import gov.nih.nci.ncicb.cadsr.loader.ui.util.UIUtil;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Shows a preview of what CDE's will be reused for the current element 
 * if the current concept and value domain mappings are accepted.
 * 
 * @author <a href="mailto:rokickik@mail.nih.gov">Konrad Rokicki</a>
 */
public class DEReuseDialog extends JDialog implements CadsrModuleListener {

    private CadsrModule cadsrModule;
    
    private JLabel deLongNameTitleLabel = new JLabel("Data Element Long Name");
    private JLabel deLongNameValueLabel = new JLabel();
    private JLabel deIdTitleLabel = new JLabel("Public ID / Version");
    private JLabel deIdValueLabel = new JLabel();
    private JLabel deContextNameTitleLabel = new JLabel("Data Element Context");
    private JLabel deContextNameValueLabel = new JLabel();
    private JLabel vdLongNameTitleLabel = new JLabel("Value Domain Long Name");
    private JLabel vdLongNameValueLabel = new JLabel();
    
    private JButton okButton = new JButton("OK");
    
    public DEReuseDialog() {
        super((JFrame)null, "Preview of DataElement reuse", true);

        this.getContentPane().setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        UIUtil.insertInBag(mainPanel, deLongNameTitleLabel, 0, 1);
        UIUtil.insertInBag(mainPanel, deLongNameValueLabel, 1, 1);
        UIUtil.insertInBag(mainPanel, deIdTitleLabel, 0, 2);
        UIUtil.insertInBag(mainPanel, deIdValueLabel, 1, 2);
        UIUtil.insertInBag(mainPanel, deContextNameTitleLabel, 0, 3);
        UIUtil.insertInBag(mainPanel, deContextNameValueLabel, 1, 3);
        UIUtil.insertInBag(mainPanel, vdLongNameTitleLabel, 0, 4);
        UIUtil.insertInBag(mainPanel, vdLongNameValueLabel, 1, 4);
        
        Border margin = new EmptyBorder(10, 10, 5, 10); 
        mainPanel.setBorder(margin);
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        
        getContentPane().add(mainPanel, BorderLayout.NORTH); 
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }
    
    /**
     * This method should be called whenever the dialog is displayed
     * for a given node. It kicks off the actual search, which will update
     * the display.
     * @param node the current attribute node
     */
    public void init(UMLNode node) {

        deLongNameValueLabel.setText("");
        deIdValueLabel.setText("");
        deContextNameValueLabel.setText("");
        vdLongNameValueLabel.setText("");
        
        if (node instanceof AttributeNode) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            SwingUtilities.invokeLater(new ReuseSearch(node));
            this.setCursor(Cursor.getDefaultCursor());
        }
        else {
            System.err.println(
                    "ERROR: cannot preview DE reuse for node of type "
                    +node.getClass().getName());
        }
    }
    
    /**
     * The search for DE reuse that is run whenever the dialog is updated.
     */
    private class ReuseSearch implements Runnable {
        
        private final UMLNode node;
        
        ReuseSearch(UMLNode node)  {
            this.node = node;
        }
        
        public void run() {
            Concept[] propConcepts = NodeUtil.getConceptsFromNode(node);
            Concept[] ocConcepts = NodeUtil.getConceptsFromNode(node.getParent());
            ValueDomain vd = ((DataElement)node.getUserObject()).getValueDomain();
            
            // the concepts must be reversed for searching, because that's the
            // way they're stored in the database, and order matters
            Collections.reverse(Arrays.asList(propConcepts));
            Collections.reverse(Arrays.asList(ocConcepts));

            deLongNameValueLabel.setText("None");
            deIdValueLabel.setText("None");
            deContextNameValueLabel.setText("None");
            vdLongNameValueLabel.setText("None");
            
//            try {
//                Collection<DataElement> results = cadsrModule.findDataElement(
//                        ocConcepts, propConcepts, vd.getLongName());
//                for(DataElement de : results) {         
//                    deLongNameValueLabel.setText(
//                            de.getLongName());
//                    
//                    deIdValueLabel.setText(
//                            de.getPublicId() + " v" + de.getVersion());
//                    
//                    deContextNameValueLabel.setText(
//                            de.getContext().getName());
//                    
//                    vdLongNameValueLabel.setText(
//                            de.getValueDomain().getLongName());
//                }
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
            
            pack();
        }
    }

    /**
     * The CadsrModule dependency should be injected into this class by 
     * the caller, or a framework like Spring.
     */
    public void setCadsrModule(CadsrModule cadsrModule) {
        this.cadsrModule = cadsrModule;
    }

    /**
     * UI Test hardness.
     */
    public static void main(String args[]) {
        DEReuseDialog dialog = new DEReuseDialog();
        dialog.setVisible(true);
    }
}
