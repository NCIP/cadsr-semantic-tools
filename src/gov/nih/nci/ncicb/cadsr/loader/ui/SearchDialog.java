package gov.nih.nci.ncicb.cadsr.loader.ui;
import gov.nih.nci.ncicb.cadsr.loader.ui.event.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class SearchDialog extends JDialog implements ActionListener
{
  private JTextField searchField = new JTextField(10);
  private JButton findButton = new JButton("Find Next");
  private List<SearchListener> searchListeners = new ArrayList();
  private static final String FINDNEXT = "FINDNEXT";
  
  public SearchDialog(JFrame owner)
  {
    super(owner, "Find");
    this.getContentPane().setLayout(new FlowLayout());
    this.getContentPane().add(searchField);
    this.getContentPane().add(findButton);
    this.setSize(200,100);
    findButton.setActionCommand(FINDNEXT);    
    findButton.addActionListener(this);
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
  
  public void actionPerformed(ActionEvent event) 
  {
    JButton button = (JButton)event.getSource();
    if(button.getActionCommand().equals(FINDNEXT)) 
    {
      String s = searchField.getText();
      SearchEvent searchEvent = new SearchEvent(s);
      fireSearchEvent(searchEvent);
      //this.setVisible(false);
    }
  }
  
  
}