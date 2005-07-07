package gov.nih.nci.ncicb.cadsr.loader.ext;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import java.util.ArrayList;
import java.util.Collection;

public class EvsModule 
{
  public EvsModule()
  {
  }
  
  public EvsResult findByConceptCode(String code) 
  {
    Concept con = DomainObjectFactory.newConcept();
    con.setPreferredName("C16642");
    con.setLongName("Gene");
    con.setPreferredDefinition("Gene Definition");
    con.setDefinitionSource("NCI");

    return new EvsResult(con, new String[] {
                           "Gene", "Gene2", "Gene3"
                         });
  }
  
  public Collection<EvsResult> findBySynonym(String s) 
  {
    Collection<EvsResult> result = new ArrayList();
    int n = (int)(Math.random() * 10 + 5);
    
    for(int i = 0; i<n; i++) {
      Concept con = DomainObjectFactory.newConcept();
      con.setPreferredName("C16642");
      con.setLongName("Gene");
      con.setPreferredDefinition("Gene Definition");
      con.setDefinitionSource("NCI");
      result.add(new EvsResult(con, new String[] {
                                 "Gene", "Gene2", "Gene3"
                               }));
    }
    
    return result;
  }
}
