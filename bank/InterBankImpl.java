import java.util.ArrayList;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;

import project.*;

class InterBankImpl extends InterBankPOA
{
    private ArrayList<Bank> banks;
    private NamingContextExt nc;

    public InterBankImpl(String args[]) throws Exception
    {
	org.omg.CORBA.Object objRef;
	ORB orb = ORB.init(args, null);  
	objRef = orb.resolve_initial_references("NameService");
	this.nc = NamingContextExtHelper.narrow(objRef);	
	this.banks = new ArrayList<Bank>();
    }

    Bank getBank(String id_bank) throws UnknownBank
    {
	for(int i=0; i<this.banks.size(); ++i){
	    if(id_bank.equals(this.banks.get(i).getId()))
		return this.banks.get(i);
	}
	System.out.println("Banque inconnue");	 
	throw new UnknownBank();
    }	

    public void registerBank(String bank_name)
    {
	System.out.println(bank_name);
	try {
	    org.omg.CORBA.Object objRef = this.nc.resolve_str(bank_name);
	    // convert the CORBA object reference into Bank reference
	    this.banks.add(BankHelper.narrow(objRef));
	
	}
	catch(Exception e){
	    System.out.println("Exception: " + e.getMessage()); 
	    e.printStackTrace(); 
	}
	return;
    }
    public void transfer(String id_src, String id_dst, String bank_src, String bank_dst, float amount) throws UnknownAccount, UnknownBank, InsufficientFunds{
	Bank src = getBank(bank_src);
	Bank dst = getBank(bank_dst);
	src.withdrawal(amount,id_src);
	dst.deposit(amount,id_dst);
	return;
    }
}
