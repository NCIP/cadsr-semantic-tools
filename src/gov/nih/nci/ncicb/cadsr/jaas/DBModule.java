package gov.nih.nci.ncicb.cadsr.jaas;

import java.io.*;
import java.util.*;

import javax.sql.DataSource;
import java.sql.Connection;

import java.security.*;
import javax.security.auth.spi.LoginModule;
import javax.security.auth.login.LoginException;
import javax.security.auth.Subject;
import javax.security.auth.callback.*;

import org.apache.log4j.Logger;

public class DBModule implements LoginModule {
  private CallbackHandler callbackHandler;
  private Subject  subject;
  private Map      sharedState;
  private Map      options;
  
  // temporary state
  private List tempCredentials = new ArrayList();
  private List tempPrincipals = new ArrayList();
  
  // the authentication status
  private boolean  success;

  private static Logger logger = Logger.getLogger(DBModule.class.getName());
  
  public void initialize(Subject subject, CallbackHandler callbackHandler,
			 Map sharedState, Map options) {
    
    // save the initial state
    this.callbackHandler = callbackHandler;
    this.subject     = subject;
    this.sharedState = sharedState;
    this.options     = options;
    
  }
  

  public boolean login() throws LoginException {

    LoaderPrincipal  p = null;
    Properties creds = new Properties();
    
    Callback[] callbacks = new Callback[] {
      new NameCallback("Username: "),
      new PasswordCallback("Password: ", false)
    };
    
    try {
      callbackHandler.handle(callbacks);
    } catch (Exception e){
      logger.error("Can't query user for password.");
      logger.error(e.getMessage());
    } // end of try-catch
    
    String username = ((NameCallback)callbacks[0]).getName();
    String password = new String(((PasswordCallback)callbacks[1]).getPassword());
    ((PasswordCallback)callbacks[1]).clearPassword();
    
    creds.setProperty("load", "load");
    this.tempCredentials.add(creds);
    this.tempPrincipals.add(new LoaderPrincipal(username));
    
    success = false;
    try
      {
        success = dbValidate(username, password);
      }
    catch(Exception e)
      {
        e.printStackTrace();
      }
    if(!success)
      throw new LoginException("Authentication failed: Password does not match");
    else
      return true;
    
  }

  public boolean logout() throws javax.security.auth.login.LoginException {

    tempPrincipals.clear();
    tempCredentials.clear();
	
    // remove the principals the login module added
    Iterator it = subject.getPrincipals(LoaderPrincipal.class).iterator();
    while (it.hasNext()) {
      LoaderPrincipal p = (LoaderPrincipal)it.next();
      subject.getPrincipals().remove(p);
    }

    // remove the credentials the login module added
    it = subject.getPublicCredentials(Properties.class).iterator();
    while (it.hasNext()) {
      Properties creds = (Properties)it.next();
      subject.getPrincipals().remove(creds);
    }
    
    return(true);
  }
 
  public boolean abort() throws javax.security.auth.login.LoginException {
    
    // Clean out state
    success = false;
    
    tempPrincipals.clear();
    tempCredentials.clear();
    
    logout();
    
    return(true);
  }

  public boolean commit() throws LoginException {

    if (success) {
      if (subject.isReadOnly()) {
	throw new LoginException ("Subject is Readonly");
      }
      
      try {
// 	Iterator it = tempPrincipals.iterator();
	
	subject.getPrincipals().addAll(tempPrincipals);
	subject.getPublicCredentials().addAll(tempCredentials);
	
	tempPrincipals.clear();
	tempCredentials.clear();
	
	
	return(true);
      } catch (Exception ex) {
	ex.printStackTrace(System.out);
	throw new LoginException(ex.getMessage());
      }
    } else {
      tempPrincipals.clear();
      tempCredentials.clear();
      return(true);
    }
  }

  private boolean dbValidate(String username, String password)
    throws Exception
  {
    DataSourceProvider dsp = (DataSourceProvider)Class.forName((String)options.get("dataSourceProvider")).newInstance();
    DataSource ds = dsp.getDataSource((String)options.get("dataSource"));
    Connection conn = ds.getConnection(username, password);
    conn.getMetaData();
    return true;
  }
    

}