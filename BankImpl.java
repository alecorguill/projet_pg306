import java.util.ArrayList;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;


class BankImpl extends BankPOA
{
    private String id;
    private ArrayList<Account> portfolio;
    
    public BankImpl(){
	
    }

    /* Retourne l'indice du compte avec le numero id_account
     * lance une exception si le compte n'existe pas 
     * 
     */
    Account getAccountIndex(String id_account) throws UnknownAccount{
	Account a = new Account(id_account, 0.0f);
	int ind = portfolio.indexOf(a);
	if(ind == -1)
	    throw UnknownAccount();
	else{
	    return ind;
	    }
    }

    public String createAccount(){
	String new_id = (this.portfolio.size()+1).toString();
	Account new_account = Account(new_id, 0.0f);
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
	float res = 0.2f;
	return res;
    }
    public void intraTransfer(String id_src, String id_dst, float amount){

    }
    public void interTransfer(String id_src, String id_dst, String bank_id, float amount){

    }
    
}
