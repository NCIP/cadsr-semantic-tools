package gov.nih.nci.ncicb.cadsr.loader;

import gov.nih.nci.ncicb.cadsr.loader.util.BeansAccessor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.*;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;


import javax.swing.JOptionPane;
/**
 * <code>Unclassifier</code> is a convenience script for unclassifying all
 * AC's, alt names and definitions from a previous model load.  
 *
 * @author <a href="mailto:rokickik@mail.nih.gov">Konrad Rokicki</a>
 */
public class Unclassifier {

  private static Logger logger = Logger.getLogger(Unclassifier.class.getName());

  private static final String CS_CHECK = 
      "  select count(*) from" +
      "    classification_schemes cs\n" + 
      "  where cs.preferred_name = ? and cs.Version = ?\n";
  
  private static final String DEL_DEFINITIONS_SQL = 
      "delete from definitions where defin_idseq in (\n" + 
      "  select defin_idseq from definitions ATT \n" + 
      "   join AC_ATT_CSCSI_EXT att_csi on att.defin_idseq = att_csi.att_idseq\n" + 
      "   join CS_CSI csCsi on att_csi.cs_csi_idseq = csCsi.cs_csi_idseq\n" + 
      "   join classification_schemes cs on cs.cs_idseq = csCsi.cs_idseq\n" + 
      "  where cs.preferred_name = ? and cs.Version = ?\n" + 
      "  and 1 = \n" + 
      "  (\n" + 
      "    select count(distinct p_cs.CS_IDSEQ) \n" + 
      "    from AC_ATT_CSCSI_EXT p_att_csi\n" + 
      "    join CS_CSI p_csCsi on p_att_csi.cs_csi_idseq = p_csCsi.cs_csi_idseq\n" + 
      "    join classification_schemes p_cs on p_cs.cs_idseq = p_csCsi.cs_idseq\n" + 
      "    where p_att_csi.ATT_IDSEQ = ATT.defin_idseq\n" + 
      "  )\n" + 
      ")";
  
  private static final String DEL_ALTNAMES_SQL = 
      "delete from designations where desig_idseq in (\n" + 
      "  select desig_idseq from designations ATT \n" + 
      "   join AC_ATT_CSCSI_EXT att_csi on att.desig_idseq = att_csi.att_idseq\n" + 
      "   join CS_CSI csCsi on att_csi.cs_csi_idseq = csCsi.cs_csi_idseq\n" + 
      "   join classification_schemes cs on cs.cs_idseq = csCsi.cs_idseq\n" + 
      "  where cs.preferred_name = ? and cs.Version = ?\n" + 
      "  and 1 = \n" + 
      "  (\n" + 
      "    select count(distinct p_cs.CS_IDSEQ) \n" + 
      "    from AC_ATT_CSCSI_EXT p_att_csi\n" + 
      "    join CS_CSI p_csCsi on p_att_csi.cs_csi_idseq = p_csCsi.cs_csi_idseq\n" + 
      "    join classification_schemes p_cs on p_cs.cs_idseq = p_csCsi.cs_idseq\n" + 
      "    where p_att_csi.ATT_IDSEQ = ATT.desig_idseq\n" + 
      "  )\n" + 
      ")";

  private static final String DEL_ATTRS_SQL = 
      "delete from AC_ATT_CSCSI_EXT where CS_CSI_IDSEQ in ( " + 
      "  select CS_CSI_IDSEQ from CS_CSI where CS_IDSEQ in ( " + 
      "   select CS_IDSEQ from Classification_schemes " +
      "   where preferred_name = ? and Version = ? " + 
      "  ) " + 
      ")";
  
  private static final String DEL_ACS_SQL = 
      "delete from AC_CSI where CS_CSI_IDSEQ in ( " + 
      "  select CS_CSI_IDSEQ from CS_CSI where CS_IDSEQ in ( " + 
      "   select CS_IDSEQ from Classification_schemes " +
      "   where preferred_name = ? and Version = ? " + 
      "  ) " + 
      ")";

  private DataSource dataSource;
  
  /**
   *
   * @param args a <code>String[]</code> value
   * @exception Exception if an error occurs
   */
  public static void main(String[] args) throws Exception {

    if(args.length != 2) {
      logger.error("unclassify [CS name] [CS version]");
      System.exit(1);
    }

    Float projectVersion = null;
    try {
      projectVersion = new Float(args[1]);
    } catch (NumberFormatException ex) {
      logger.error("Parameter projectVersion must be a number");
      System.exit(1);
    }

    Unclassifier loader = BeansAccessor.getUnclassifier();
    loader.run(args[0], projectVersion);
  }

  private void run(String csName, Float csVersion) throws Exception {

    // Refresh the views
    Connection conn = null;
    PreparedStatement stmt = null;
    try {
        logger.debug("Starting unclassification of --" + csName + "--" + csVersion);
      
        conn = dataSource.getConnection();
        conn.setAutoCommit(false);

        logger.debug("Checking for CS");
        stmt = conn.prepareStatement(CS_CHECK);
        stmt.setString(1, csName);
        stmt.setFloat(2, csVersion);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        logger.debug("  "+rs.getInt(1)+" CS found");
        
        logger.debug("Deleting definitions");
        stmt = conn.prepareStatement(DEL_DEFINITIONS_SQL);
        stmt.setString(1, csName);
        stmt.setFloat(2, csVersion);
        int definitionsDeleted = stmt.executeUpdate();
        logger.debug("  "+definitionsDeleted+" deleted");

        logger.debug("Deleting alt names");
        stmt = conn.prepareStatement(DEL_ALTNAMES_SQL);
        stmt.setString(1, csName);
        stmt.setFloat(2, csVersion);
        int altNamesDeleted = stmt.executeUpdate();
        logger.debug("  "+altNamesDeleted+" deleted");
        
        logger.debug("Deleting attribute classifications");
        stmt = conn.prepareStatement(DEL_ATTRS_SQL);
        stmt.setString(1, csName);
        stmt.setFloat(2, csVersion);
        int attrDeleted = stmt.executeUpdate();
        logger.debug("  "+attrDeleted+" deleted");

        logger.debug("Deleting AC classifications");
        stmt = conn.prepareStatement(DEL_ACS_SQL);
        stmt.setString(1, csName);
        stmt.setFloat(2, csVersion);
        int acDeleted = stmt.executeUpdate();
        logger.debug("  "+acDeleted+" deleted");
        
        String answ = JOptionPane.showInputDialog(PropertyAccessor.getProperty("unclassify.commit"));
        if(answ == null || !answ.equals("y")) {
          conn.rollback();
          logger.debug("user rolled back");
        } else {
          conn.commit();
          logger.debug("Unclassification complete");
        }
    }
    catch (SQLException e) {
        if (conn != null) {
          System.err.println("Error encountered, rolling back changes.");
          conn.rollback();
        }
        e.printStackTrace();
        System.exit(1);
    }
    finally {
        if (conn != null) {
            conn.close();
        }
    }
    
    System.exit(0);
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }
}
