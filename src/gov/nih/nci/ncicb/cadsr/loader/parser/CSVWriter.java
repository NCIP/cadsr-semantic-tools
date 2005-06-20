package gov.nih.nci.ncicb.cadsr.loader.parser;
import com.infomata.data.CSVFormat;
import com.infomata.data.DataFile;
import com.infomata.data.DataRow;
import gov.nih.nci.ncicb.cadsr.domain.*;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class CSVWriter implements ElementWriter
{
  private DataFile write;
  private File report;
  //private ObjectClass oc = ;
 
  public void setOutput(String url) 
  {
    report = new File(url);
  }
  
//  public static void main(String [] args) 
//  {
//    CSVWriter w = new CSVWriter();
//    w.write(ElementsLists.getInstance());
//  }
  
  public void write(ElementsLists elements) 
  {
    write = DataFile.createWriter("8859_2", true);
    write.setDataFormat(new CSVFormat());
    //List altNames = oc.getAlternateNames();
    DataRow row = null;
    if((report!=null) && (report.exists()))
		{
		    report.delete();
		}
    //for(ObjectClass c : (List<ObjectClass>elements.getElements(oc.getClass())))
    //  System.out.println(c);
    //elements.getElements(oc.getClass());
    //elements = ElementsLists.getInstance();
    List ocs =elements.getElements(DomainObjectFactory.newObjectClass().getClass());
    //for(Iterator i = ocs.iterator(); 
    //    i.hasNext(); i.next()) {
    try
      {
        write.open(report);
        row = write.next();
        row.add("UMLClass");
        row.add("UMLEntity");
        row.add("UMLDescription");
        row.add("ConceptName");
        row.add("ConceptPreferredName");
        row.add("Classification");
        row.add("ConceptCode");
        row.add("ConceptDefinition");
        row.add("ConceptDefinitionSource");
        row.add("ModifiedDate");
        row.add("HumanVerified");
        
        for(ObjectClass oc : (List<ObjectClass>) ocs) {
    //for (ObjectClass c : ((List elements.getElements(DomainObjectFactory.newObjectClass().getClass()))))
    //{
      
          //row = write.next();
          //List altNames = oc.getAlternateNames();
          //for(AlternateName a : (List<AlternateName>) altNames)
          //  row.add(a.getType());
          String [] conceptCodes = oc.getPreferredName().split(":");
          for(int i = conceptCodes.length-1; i > -1; i--) {
          System.out.println("Length of conceptCodes " + conceptCodes.length);
          String temp[] = oc.getLongName().split("domain.");
          row = write.next();
          
          row.add(temp[1]);
          row.add(temp[1]);
          row.add(oc.getPreferredDefinition());
          //String [] conceptCodes = oc.getPreferredName().split(":");
          Concept con = LookupUtil.lookupConcept(conceptCodes[i]);
          if(con != null) {
          row.add(con.getLongName());
          row.add(con.getLongName());
          row.add("ObjectClass");
          row.add(con.getPreferredName());
          row.add(con.getPreferredDefinition());
          row.add(con.getDefinitionSource());
          row.addEmpty();
          row.add(0);
          }
          }
        
          DataElementConcept o = DomainObjectFactory.newDataElementConcept();
          List<DataElementConcept> decs = (List<DataElementConcept>) elements.getElements(o.getClass());

          for(DataElementConcept dec : decs) {
            if(dec.getObjectClass().getLongName()
              .equals(oc.getLongName())) {
            
            String [] propConCodes = dec.getProperty().getPreferredName().split(":");
            for(int i = propConCodes.length-1; i > -1; i--)
            {
              String temp[] = oc.getLongName().split("domain.");
              //Concept con = LookupUtil.lookupConcept(conceptCodes[i]);
              row = write.next();
              row.add(temp[1]);
              row.add(dec.getProperty().getLongName());
              row.addEmpty();
              
              Concept con = LookupUtil.lookupConcept(propConCodes[i]);
              row.add(con.getLongName());
              row.add(con.getLongName());
              if(i == propConCodes.length-1)
                row.add("Property");
              else
                row.add("PropertyQualifier" + ((propConCodes.length-1) - i));
        
              row.add(con.getPreferredName());
              row.add(con.getPreferredDefinition());
              row.add(con.getDefinitionSource());
              row.addEmpty();
              row.add(0);
            
            }
           }
          }          
          
          //ObjectClass temp = (ObjectClass) i;
          //String pd = oc.getPreferredDefinition();
          //row.add(pd);
        //row.add(((ObjectClass) i).getPreferredDefinition());
        //row.add(((ObjectClass) i).getLongName());
        //for ( : elements.getElements(new DataElementConcept().getClass()))
        //{
        //}
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      finally 
      {
        write.close();
      }
    }
  
}

