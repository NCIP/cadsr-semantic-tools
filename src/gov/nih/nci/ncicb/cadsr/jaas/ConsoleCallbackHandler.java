package gov.nih.nci.ncicb.cadsr.jaas;

import java.io.*;

import javax.security.auth.callback.*;

/**
 * Simple CallbackHandler that queries the standard input. This is appropriate for console mode only.
 *
 * @author <a href="mailto:ludetc@mail.nih.gov">Christophe Ludet</a>
 */
public class ConsoleCallbackHandler implements CallbackHandler {

  public ConsoleCallbackHandler() {
  }

  public void handle(Callback[] callbacks) 
    throws java.io.IOException, UnsupportedCallbackException {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    for (int i = 0; i < callbacks.length; i++) {

      if (callbacks[i] instanceof NameCallback) {
        System.out.print(((NameCallback)callbacks[i]).getPrompt());
        String user=br.readLine();
        ((NameCallback)callbacks[i]).setName(user);
        
      } else if (callbacks[i] instanceof PasswordCallback) {
        ConsoleEraser consoleEraser = new ConsoleEraser();
        System.out.print(((PasswordCallback)callbacks[i]).getPrompt());
        consoleEraser.start();
        String pass=br.readLine();
        consoleEraser.halt();
        ((PasswordCallback)callbacks[i]).setPassword(pass.toCharArray());
      } else {
        throw(new UnsupportedCallbackException(
                callbacks[i], "Callback class not supported"));
      }
    }
  }

  class ConsoleEraser extends Thread {
    private boolean running = true;
    public void run() {
      while (running) {
        System.out.print("\b ");
      }
    }
    public synchronized void halt() {
      running = false;
    }  
  }
}

