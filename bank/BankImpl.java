import java.util.ArrayList;
import java.util.Arrays;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;
import javax.xml.bind.annotation.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import java.lang.*;
import project.*;

@XmlRootElement(name="bank")
class BankImpl extends project.BankPOA
{
    @XmlElement(name="id")
    private String id;
    private ArrayList<Account> portfolio;
    private InterBank interbank;

    public InterBank connectInterBank(String args[], String name)
    {
	ORB orb = ORB.init(args, null);  
	org.omg.CORBA.Object objRef;
	// create and initialize the ORB
	// get the naming service	
	try 
	    {
		objRef = orb.resolve_initial_references("NameService");
		NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
		// resolve the object reference from the naming service
		System.out.println(name);
		objRef = ncRef.resolve_str(name);
		// convert the CORBA object reference into Bank reference
		this.interbank = InterBankHelper.narrow(objRef);
	    }
	catch (Exception e)
	    { 
		System.out.println("Exception: " + name + e.getMessage()); 
		e.printStackTrace();
		System.exit(1);
	    }
	this.interbank.registerBank(this.id);
	return interbank;

    }

    public BankImpl(String args[]) throws Exception{
	this.id = args[0];
	this.portfolio = new ArrayList<Account>();
	/* Connection à l'interbank */
    }

    /* Retourne l'indice du compte avec le numero id_account
     * Leve une exception si compte inconnu
     * 
     */
    Account getAccount(String id_account) throws UnknownAccount
    {
	for(int i=0; i<this.portfolio.size(); ++i){
	    if(id_account.equals(this.portfolio.get(i).getId()))
		return this.portfolio.get(i);
	}
	System.out.println("Compte inconnu");	 
	throw new UnknownAccount();
    }	
    
    public String getId()
    {
	return this.id;
    }

    public String createAccount(String id_client)
    {
	String new_id = Integer.toString((this.portfolio.size()+1));
	Account new_account = new Account(new_id,id_client,0.0f);
	portfolio.add(new_account);
	System.out.println("New Account : " + new_id);
	return new_id;
    }
    public void deposit(float amount, String id_account) throws UnknownAccount
    {
	Account a = getAccount(id_account);
	a.deposit(amount);
	return;
    }
    public void withdrawal(float amount, String id_account) throws UnknownAccount, InsufficientFunds
    {
	Account a = getAccount(id_account);
	a.withdrawal(amount);
	return;
    }

    public String[] getAllAccounts(String id_client){
	
	ArrayList<String> list_ids = new ArrayList<String>();
	for(int i=0; i<this.portfolio.size(); ++i){
	    if(id_client.equals(this.portfolio.get(i).getIdClient()))
		list_ids.add(this.portfolio.get(i).getIdClient());
	}
	return list_ids.toArray(new String[list_ids.size()]);
	
    }

    public float getBalance(String id_account) throws UnknownAccount
    {
	wakeUp();
	Account a = getAccount(id_account);
	return a.getBalance();
    }

    public void intraTransfer(String id_src, String id_dst, float amount) throws UnknownAccount, InsufficientFunds 
    {
	Account src = getAccount(id_src);
	Account dst = getAccount(id_dst);
	src.withdrawal(amount);
	dst.deposit(amount);
	return;
    }
    public void interTransfer(String id_src, String id_dst, String bank_id, float amount) throws UnknownBank, UnknownAccount, InsufficientFunds
    {
	this.interbank.transfer(id_src,id_dst,this.id,bank_id,amount);
	return;
    }

    public void processJobs(ArrayList<Event> jobs){
	for(int i=0; i<jobs.size(); ++i){
	    Event evt = jobs.get(i);
	    Event_t type = evt.e;
	    try{
		if ((type).equals(Event_t.withdraw)){
		    withdrawal(evt.amount,evt.id_account_src);
		}
		
		else if ((type).equals(Event_t.deposit)){
		    deposit(evt.amount,evt.id_account_src);
		}
		else
		    throw new Exception();
	    }
	    catch (Exception e){
		this.interbank.handleException(evt);
	    }
	}
    }

    public void wakeUp(){
	System.out.println("JE SUIS REVEILLE " + this.id);
	ArrayList<Event> jobs = new ArrayList<Event>(Arrays.asList(this.interbank.getJobs(this.id).mails));
	processJobs(jobs);
    }
    
}
