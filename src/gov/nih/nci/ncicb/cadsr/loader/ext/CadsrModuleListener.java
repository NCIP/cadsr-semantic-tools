package gov.nih.nci.ncicb.cadsr.loader.ext;

  /**
   * implement if you want your class to use plug-in cadsrModule
   */
public interface CadsrModuleListener {

  /**
   * IoC setter
   */
  public void setCadsrModule(CadsrModule module);

}