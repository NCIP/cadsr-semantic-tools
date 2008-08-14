package gov.nih.nci.ncicb.cadsr.loader.ext;

import gov.nih.nci.ncicb.cadsr.loader.util.DiskCache;

import org.aopalliance.intercept.*;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

import org.apache.log4j.Logger;

import java.net.URL;

public class CadsrModuleInterceptor 
  implements MethodInterceptor
{

  private Logger logger = Logger.getLogger(CadsrModuleInterceptor.class.getName());

  private JCS cache = null;
  
  public CadsrModuleInterceptor() {
//     try
//       {
//         cache =  JCS.getInstance( "test" );
//       }
//     catch ( CacheException e )
//       {
//         logger.error( "Problem initializing cache for region name [test]",e);
//       }

//     System.out.println("************* CACHE INIT OK ****************");
    
  }

  // interceptor. Use this later for caching queries
  public Object invoke(MethodInvocation invocation) throws Throwable {
    


    return invocation.proceed();

  }


  
}