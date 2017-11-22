import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

import project.*;

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

	String src = bank.createAccount();
	String dst = bank.createAccount();
	bank.deposit(50.0f,src);
	bank.intraTransfer(src, dst, 30);
	String src_b = Float.toString(bank.getBalance(src));
	String dst_b = Float.toString(bank.getBalance(dst));
	System.out.println("SRC : " + src_b + " DST : " + dst_b);
    }
}
