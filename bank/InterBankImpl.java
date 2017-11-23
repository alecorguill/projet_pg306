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
    private ArrayList<Event> logs;
    private ArrayList<MailBox> bankMails; 
    private NamingContextExt nc;

    public InterBankImpl(String args[]) throws Exception
    {
	org.omg.CORBA.Object objRef;
	ORB orb = ORB.init(args, null);  
	objRef = orb.resolve_initial_references("NameService");
	this.nc = NamingContextExtHelper.narrow(objRef);	
	this.banks = new ArrayList<Bank>();
	this.logs =  new ArrayList<Event>();
	this.bankMails =  new ArrayList<MailBox>();

    }

    Bank getBank(String id_bank) throws UnknownBank
    {
	System.out.println("Nb comptes" + this.banks.size());
	System.out.println("Id bank " + id_bank);
	for(int i=0; i<this.banks.size(); ++i){
	    System.out.println(this.banks.get(i));
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
    
    private void addJob(Event event, String id_bank){
	for(int i=0; i<this.bankMails.size(); ++i){
	    if(id_bank.equals(this.bankMails.get(i).getId())){
		this.bankMails.get(i).add(event);
		return;
    }
	}
    }
    
    
    public void handleException(Event event){
	Event w, d;
	Event_t evt = event.getEvent();
	String id_src = event.getAccountSrc();
	String id_dst = event.getAccountDst();
	String bank_src = event.getBankIdSrc();
	String bank_dst = event.getBankIdDst();	
	float amount = event.getAmount();
	
	if (evt.equals(Event_t.withdraw))
	    {
		w = new Event(id_dst,id_src,bank_dst,bank_src,amount,Event_t.exception_t);
		d = new Event(id_src,id_dst,bank_src,bank_dst,-amount,Event_t.exception_t);
		this.addJob(w,id_dst);
	    }
	else
	    {
		d = new Event(id_dst,id_src,bank_dst,bank_src,amount,Event_t.exception_t);
		w = new Event(id_src,id_dst,bank_src,bank_dst,-amount,Event_t.exception_t);
		this.addJob(d,id_dst);
	    }	  
	logs.add(w);
	logs.add(d);	
    }
    

    public MailBox getJobs(String id_bank){
	for(int i=0; i<this.bankMails.size(); ++i){
	    if(id_bank.equals(this.bankMails.get(i).getId())){
		return bankMails.get(i);
	    }
	}
    }
    
    public void transfer(String id_src, String id_dst, String bank_src, String bank_dst, float amount){
	Event w = new Event(id_src,id_dst,bank_src,bank_dst,amount,Event_t.withdraw); 
	Event d = new Event(id_src,id_dst,bank_src,bank_dst,amount,Event_t.deposit);
	this.addJob(w,id_src);
	this.addJob(d,id_dst);
	logs.add(w);
	logs.add(d);	
	return;
    }
}
