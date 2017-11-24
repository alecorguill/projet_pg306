import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

import project.*;

public class Observer
{
    public static void main(String args[]) throws Exception
    {
	ORB orb = ORB.init(args, null);  
	org.omg.CORBA.Object objRef;
	InterBank ib;
	// create and initialize the ORB
	// get the naming service	
	objRef = orb.resolve_initial_references("NameService");
	NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
	// resolve the object reference from the naming service
	objRef = ncRef.resolve_str("interbank");
	// convert the CORBA object reference into Bank reference
	ib = InterBankHelper.narrow(objRef);

	System.out.println("########### STATE OF THE INTERBANK MARKET ###########");
	System.out.println("########### BANKS CONNECTED ###########");
	String banks[] = ib.getAllBanks();
	for(int i=0; i<banks.length; ++i)
	    System.out.println(banks[i]);
	System.out.println("########### TRANSACTIONS HISTORY ###########");
	String logs[] = ib.getAllTransactions();
	for(int i=0; i<logs.length; ++i)
	    System.out.println(logs[i]);		
    }
}
