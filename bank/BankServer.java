import java.util.Arrays;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import java.util.Properties;
import java.util.ArrayList;
import org.omg.CORBA.Policy;
import org.omg.PortableServer.*;
import project.*;

public class BankServer {
    
    public static void main(String args[])
    {
	try {
	    Properties properties = System.getProperties();
	    properties.put( "org.omg.CORBA.ORBInitialHost",
			    "localhost" );
	    properties.put( "org.omg.CORBA.ORBInitialPort",
			    "2810" );
	    ORB orb = ORB.init(args, properties);  
	    // create and initialize the ORB
	    BankImpl BankImpl = new BankImpl(args);
	    // get reference to rootpoa & activate the POAManager	
	    POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
	    Policy[] persistentPolicy = new Policy[1];
	    persistentPolicy[0] = rootpoa.create_lifespan_policy(LifespanPolicyValue.PERSISTENT);
	    POA persistentpoa = rootpoa.create_POA("childPOA",null,persistentPolicy);
	    persistentpoa.the_POAManager().activate();
	    persistentpoa.activate_object( BankImpl );
	    // get the naming service
	    org.omg.CORBA.Object objRef, bankref;	
	    objRef = orb.resolve_initial_references("NameService");
	    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
	    // instanciate the servant
	    // get object reference from servant	
	    // convert the generic CORBA object reference into typed Bank reference
	    //Bank bankRef = BankHelper.narrow(objRef);
	    // bind the object reference in the naming service
	    bankref = persistentpoa.servant_to_reference(BankImpl); 
	    Bank b = BankHelper.narrow(bankref);
	    NameComponent path[ ] = ncRef.to_name(args[0]); 
	    // id.kind
	    ncRef.rebind(path, bankref);
	    String corba_args[] = Arrays.copyOfRange(args, 1, args.length);
	    b.connectInterBank(corba_args,"interbank");
	    orb.run();
	    Thread.sleep(60000);
	    //orb.shutdown(false);
	}
	catch ( Exception e ) {
            System.err.println( "Exception in Persistent Server Startup " + e );
        }
	// start server...
    }
}
