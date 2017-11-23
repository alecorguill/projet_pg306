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
    public static void 	main(String args[]) throws Exception
    {
	ORB orb = ORB.init(args, null);  
	// create and initialize the ORB
	// get reference to rootpoa & activate the POAManager
	org.omg.CORBA.Object objRef = orb.resolve_initial_references("RootPOA");
	POA rootpoa = POAHelper.narrow(objRef);
	Policy[] persistentPolicy = new Policy[1];
	persistentPolicy[0] = rootpoa.create_lifespan_policy(LifespanPolicyValue.PERSISTENT);
	POA persistentpoa = rootpoa.create_POA("childPOA",null,persistentPolicy);
	persistentpoa.the_POAManager().activate();
	// get the naming service
	try 
	    {
		objRef = orb.resolve_initial_references("NameService");
	}
	catch (Exception e)
	    { 
		System.out.println("Exception: " + e.getMessage()); 
		e.printStackTrace(); 
	    }
	NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
	// instanciate the servant
	BankImpl BankImpl = new BankImpl(args);
	// get object reference from servant
	objRef = persistentpoa.servant_to_reference(BankImpl);
	// convert the generic CORBA object reference into typed Bank reference
	Bank bankRef = BankHelper.narrow(objRef);
	// bind the object reference in the naming service
	NameComponent path[ ] = ncRef.to_name(args[0]); 
	// id.kind
	ncRef.rebind(path, bankRef);
	String corba_args[] = Arrays.copyOfRange(args, 1, args.length);
	bankRef.connectInterBank(corba_args,"interbank");
	orb.run();
	orb.shutdown(false);	    
	// start server...
    }
}
