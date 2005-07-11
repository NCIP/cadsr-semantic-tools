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
    con.setPreferredDefinition("But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?");
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
      con.setPreferredDefinition("But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?");
      con.setDefinitionSource("NCI");
      result.add(new EvsResult(con, new String[] {
                                 "Gene", "Gene2", "Gene3"
                               }));
    }
    
    return result;
  }
}
