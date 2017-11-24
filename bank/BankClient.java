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
	
	objRef_ca = ncRef.resolve_str("CA");
	Bank bank_ca = BankHelper.narrow(objRef_ca);


	String src = bank_bnp.createAccount("Bernard");
	String dst = bank_ca.createAccount("Antoine");
	bank_bnp.deposit(50.0f,src);
	bank_bnp.interTransfer(src, "src","CA",30);
	String src_b = Float.toString(bank_bnp.getBalance(src));
	String dst_b = Float.toString(bank_ca.getBalance(dst));
	System.out.println("SRC : " + src_b + " DST : " + dst_b);	
    }
}
