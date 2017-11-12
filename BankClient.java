import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

public class  BankClient
{
    public static void main(String args[]) throws Exception
    {
	org.omg.CORBA.Object objRef;
	ORB orb = ORB.init(args, null);  
	// create and initialize the ORB
	// get the naming service
	objRef = orb.resolve_initial_references("NameService");
	NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
	// resolve the object reference from the naming service
	objRef = ncRef.resolve_str("bank");
	// convert the CORBA object reference into Bank reference
	Bank bank = BankHelper.narrow(objRef);
	// remote method invocation

	String response = bank.createAccount();
	float balance = bank.getBalance("21515");
	System.out.println(response + " " + Float.toString(balance));
    }
}
