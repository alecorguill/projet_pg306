import java.util.ArrayList;
import java.util.Arrays;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;
import javax.xml.bind.annotation.*;



@XmlRootElement(name="bank")
class BankImpl extends BankPOA
{
    @XmlElement(name="id")
    private String id;
    private ArrayList<Account> portfolio;
    
    public BankImpl(){
	this.id = "ENSEIRB";
	this.portfolio = new ArrayList<Account>();
	/* Connection à l'interbank */
	
    }

    /* Retourne l'indice du compte avec le numero id_account
     * Leve une exception si compte inconnu
     * 
     */
    Account getAccount(String id_account) throws BankPackage.UnknownAccount
    {
	for(int i=0; i<this.portfolio.size(); ++i){
	    if(id_account.equals(this.portfolio.get(i).getId()))
		return this.portfolio.get(i);
	}
	System.out.println("Compte inconnu");	 
	throw new BankPackage.UnknownAccount();
    }	
    
    public String createAccount()
    {
	String new_id = Integer.toString((this.portfolio.size()+1));
	Account new_account = new Account(new_id, 0.0f);
	portfolio.add(new_account);
	System.out.println("New Account : " + new_id);
	return new_id;
    }
    public void deposit(float amount, String id_account) throws BankPackage.UnknownAccount
    {
	Account a = getAccount(id_account);
	a.deposit(amount);
	return;
    }
    public void withdrawal(float amount, String id_account) throws BankPackage.UnknownAccount, BankPackage.InsufficientFunds
    {
	Account a = getAccount(id_account);
	a.withdrawal(amount);
	return;
    }
    public float getBalance(String id_account) throws BankPackage.UnknownAccount
    {
	Account a = getAccount(id_account);
	return a.getBalance();
    }

    public void intraTransfer(String id_src, String id_dst, float amount) throws BankPackage.UnknownAccount, BankPackage.InsufficientFunds 
    {
	Account src = getAccount(id_src);
	Account dst = getAccount(id_dst);
	src.withdrawal(amount);
	dst.deposit(amount);
	return;
    }
    public void interTransfer(String id_src, String id_dst, String bank_id, float amount) throws BankPackage.UnknownAccount
    {
	
    }
    
}
