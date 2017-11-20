import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.ext.jaxrs.JaxRsApplication;

public class BankRestServer
{
  public static void main(String[] args) throws Exception
  {
    // create Component (as ever for Restlet)
    Component comp = new Component();
    Server server = comp.getServers().add(Protocol.HTTP, 8182);
    
    // create JAX-RS runtime environment
    JaxRsApplication application = new JaxRsApplication(comp.getContext());
    
    // attach Application
    application.add(new BankApplication());
    
    // Attach the application to the component and start it
    comp.getDefaultHost().attach(application);
    comp.start();
    
    System.out.println("Server started on port " + server.getPort());
  }
}

