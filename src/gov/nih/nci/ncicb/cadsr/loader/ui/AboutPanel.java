package gov.nih.nci.ncicb.cadsr.loader.ui;

import javax.swing.JOptionPane;

import java.io.*;
import java.awt.Frame;
import java.nio.charset.Charset;

public class AboutPanel extends JOptionPane {

  public AboutPanel() {
    try {
      StringBuilder sw = new StringBuilder();
      InputStreamReader in = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("about.html"), Charset.forName("US-ASCII"));
            
      int c = -1;
      while((c = in.read()) != -1 )
        sw.append((char)c);
            
      JOptionPane.showMessageDialog
        ((Frame)null, 
         sw.toString(),
         "About this tool", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e){
      e.printStackTrace();
    } // end of try-catch
          
  }

  public static void main(String[] args) {
    new AboutPanel();
    System.exit(0);
  }

}