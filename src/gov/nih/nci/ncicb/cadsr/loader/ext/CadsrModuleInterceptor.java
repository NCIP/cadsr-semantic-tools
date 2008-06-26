package gov.nih.nci.ncicb.cadsr.loader.ext;

import org.aopalliance.intercept.*;

public class CadsrModuleInterceptor 
   implements MethodInterceptor
{


  // interceptor. Use this later for caching queries
  public Object invoke(MethodInvocation invocation) throws Throwable {
    
    return invocation.proceed();

  }
  
  
  
}