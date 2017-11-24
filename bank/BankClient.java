import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

import project.*;

public class  BankClient
{
    public static void main(String args[]) throws Exception
    {
	org.omg.CORBA.Object objRef, objRef_bnp,objRef_ca;
	ORB orb = ORB.init(args, null);  
	// create and initialize the ORB
	// get the naming service
	objRef = orb.resolve_initial_references("NameService");
	NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
	// resolve the object reference from the naming service
	objRef_bnp = objRef;
	objRef_ca = objRef;

	objRef_bnp = ncRef.resolve_str("BNP");
	Bank bank_bnp = BankHelper.narrow(objRef_bnp);
	String src = bank_bnp.createAccount("Bernard");
	bank_bnp.createAccount("Bernard");
	bank_bnp.deposit(50.0f,src);
	String res[] = bank_bnp.getAllAccounts("Bernard");
	for(int i =0; i<res.length; ++i)
	    System.out.println("ID ACCOUNT : " + res[i]);
    }
}
