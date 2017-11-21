import java.util.ArrayList;
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
	
    }

    /* Retourne l'indice du compte avec le numero id_account
     * -1 sinon
     * 
     */
    int getAccountIndex(String id_account){
	Account a = new Account(id_account, 0.0f);
	int ind = portfolio.indexOf(a);
	if(ind == -1){
	    System.out.println("Compte inconnu");
	}	
	else{
	    return ind;
	}
	return -1;
    }
    
    public String createAccount(){
	String new_id = Integer.toString((this.portfolio.size()+1));
	Account new_account = new Account(new_id, 0.0f);
	portfolio.add(new_account);
	System.out.println("New Account : " + new_id);
	return new_id;
    }
    public void deposit(float amount, String id_account){
	int i = getAccountIndex(id_account);
	portfolio.get(i).deposit(amount);
	return;
    }
    public void withdrawal(float amount, String id_account){
	int i = getAccountIndex(id_account);
	portfolio.get(i).withdrawal(amount);
	return;
    }
    public float getBalance(String id_account){	
	int i = getAccountIndex(id_account);
	return 	portfolio.get(i).getBalance();
    }
    public void intraTransfer(String id_src, String id_dst, float amount){
	
    }
    public void interTransfer(String id_src, String id_dst, String bank_id, float amount){

    }
    
}
