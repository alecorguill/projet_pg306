import java.util.ArrayList;
import java.util.Arrays;
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
	for(int i=0; i<this.banks.size(); ++i){
	    if(id_bank.equals(this.banks.get(i).getId()))
		return this.banks.get(i);
	}
	System.out.println("Banque inconnue");	 
	throw new UnknownBank();
    }	

    public void registerBank(String bank_name)
    {
	System.out.println("NEW BANK IN INTERBANK : " + bank_name);
	try {
	    org.omg.CORBA.Object objRef = this.nc.resolve_str(bank_name);
	    // convert the CORBA object reference into Bank reference
	    this.banks.add(BankHelper.narrow(objRef));
	    this.bankMails.add(new MailBox(bank_name,new Event[0]));
	}
	catch(Exception e){
	    System.out.println("Exception: " + e.getMessage()); 
	    e.printStackTrace(); 
	}
	return;
    }
    
    private void addJob(Event event, String id_bank){
	for(int i=0; i<this.bankMails.size(); ++i){
	    
	    if(id_bank.equals(this.bankMails.get(i).id_bank)){
		Event mails[] = this.bankMails.get(i).mails;
		ArrayList<Event> tmp = new ArrayList<Event>(Arrays.asList(mails)); 
		tmp.add(event);
		this.bankMails.get(i).mails = tmp.toArray(new Event[tmp.size()]);
		return;
	    }
	}
    }
    
    
    public void handleException(Event event){
	Event w, d;
	Event_t evt = event.e;
	String id_src = event.id_account_src;
	String id_dst = event.id_account_dst;
	String bank_src = event.bank_id_src;
	String bank_dst = event.bank_id_dst;	
	float amount = event.amount;
	
	if (evt.equals(Event_t.withdraw))
	    {
		w = new Event(id_dst,id_src,bank_dst,bank_src,Event_t.exception_t,amount);
		d = new Event(id_src,id_dst,bank_src,bank_dst,Event_t.exception_t,-amount);
		this.addJob(w,id_dst);
	    }
	else
	    {
		d = new Event(id_dst,id_src,bank_dst,bank_src,Event_t.exception_t,amount);
		w = new Event(id_src,id_dst,bank_src,bank_dst,Event_t.exception_t,-amount);
		this.addJob(d,id_dst);
	    }	  
	logs.add(w);
	logs.add(d);	
    }
    

    public MailBox getJobs(String id_bank){
	for(int i=0; i<this.bankMails.size(); ++i){
	    if(id_bank.equals(this.bankMails.get(i).id_bank)){
		return bankMails.get(i);
	    }
	}
	return new MailBox();
    }
    
    public void clearOut(String id_bank){
	for(int i=0; i<this.bankMails.size(); ++i){
	    if(id_bank.equals(this.bankMails.get(i).id_bank)){
		this.bankMails.get(i).mails = new Event[0];
		return;
	    }
	}
    }
    public void transfer(String id_src, String id_dst, String bank_src, String bank_dst, float amount){
	Event w = new Event(id_src,id_dst,bank_src,bank_dst,Event_t.withdraw,amount); 
	Event d = new Event(id_src,id_dst,bank_src,bank_dst,Event_t.deposit,amount);
	this.addJob(w,bank_src);
	this.addJob(d,bank_dst);
	logs.add(w);
	logs.add(d);	
	return;
    }
    
    public String[] getAllBanks()
    {
	ArrayList<String> res = new ArrayList<String>();
	for(Bank tmp : this.banks)
	    res.add(tmp.getId());
	return res.toArray(new String[res.size()]);
    }

    public String[] getAllEvents()
    {
	ArrayList<String> res = new ArrayList<String>();
	for(Event tmp : this.logs)
	    res.add("BANK : " + tmp.bank_id_dst + ", ACCOUNT : " + tmp.id_account_dst + ", AMOUNT : " + tmp.amount);
	return res.toArray(new String[res.size()]);
    }

}
