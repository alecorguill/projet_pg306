import java.util.ArrayList;
import java.util.Arrays;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

import project.*;

class TestBank {
    
    Bank getBank(String bank_name, ORB orb) throws Exception{
	org.omg.CORBA.Object objRef, objRefB;
	// create and initialize the ORB
	// get the naming service
	objRef = orb.resolve_initial_references("NameService");
	NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
	objRefB = objRef;
	
	objRefB = ncRef.resolve_str(bank_name);      
	return BankHelper.narrow(objRefB);
    }
    
    public void testGetId(ORB orb) throws Exception{
	System.out.print("Test GetID..");
	
	String bank_name = "BNP";
	Bank a = getBank(bank_name, orb);
	String res = a.getId();
    	assert res.equals(bank_name) : "ERREUR : getId invalid";
	System.out.print("ok\n");    
    }

    
    public void testCreateAccount(ORB orb) throws Exception{
	System.out.print("Test CreateAccount..");
	
	String bank_name = "BNP";
	String id_client = "Arthur";
	Bank a = getBank(bank_name, orb);
	String c0 = a.createAccount(id_client);
	String c1 = a.createAccount(id_client);	
	ArrayList<String> accounts = new ArrayList<String>(Arrays.asList(a.getAllAccounts(id_client)));
   	assert accounts.size() == 2 : "ERREUR : Invalid number of accounts";
   	assert accounts.contains(c0);
   	assert accounts.contains(c1);
	System.out.print("ok\n");    
    }


    public void testDeposit(ORB orb) throws Exception{
	System.out.print("Test Deposit..");
	
	String bank_name = "BNP";
	String id_client = "Arthur";
	Bank b = getBank(bank_name, orb);
	String c = b.createAccount(id_client);
	b.deposit(50.0f, c);
	b.deposit(20.0f, c);
	Float balance = b.getBalance(c);
	assert balance == 70.0f;
	System.out.print("ok\n");    
    }


    public void testWithdrawal(ORB orb) throws Exception{
	System.out.print("Test Withdrawal..");
	
	String bank_name = "BNP";
	String id_client = "Arthur";
	Bank b = getBank(bank_name, orb);
	String c = b.createAccount(id_client);
	b.deposit(50.0f, c);
	b.withdrawal(20.0f, c);
	Float balance = b.getBalance(c);
	assert balance == 30.0f;
	System.out.print("ok\n");    
    }


    public void testIntraTransfer(ORB orb) throws Exception{
	System.out.print("Test IntraTransfer..");
	
	String bank_name = "BNP";
	String client1 = "Arthur";
	String client2 = "Ahcene";
	Bank b = getBank(bank_name, orb);
	String c1 = b.createAccount(client1);
	String c2 = b.createAccount(client2);
	b.deposit(50.0f, c1);
	b.intraTransfer(c1,c2,20.0f);
	Float balance1 = b.getBalance(c1);
	Float balance2 = b.getBalance(c2);
	assert balance1 == 30.0f;
	assert balance2 == 20.0f;
	System.out.print("ok\n");    
    }

    public void testInterTransfer(ORB orb) throws Exception{
	System.out.print("Test InterTransfer..");
	
	String bank_name1 = "BNP";
	String bank_name2 = "CA";
	String client1 = "Arthur";
	String client2 = "Ahcene";
	Bank b1 = getBank(bank_name1, orb);
	Bank b2 = getBank(bank_name2, orb);
	String c1 = b1.createAccount(client1);
	String c2 = b2.createAccount(client2);
	b1.deposit(50.0f, c1);
	b1.interTransfer(c1,c2,"CA",20.0f);
	Float balance1 = b1.getBalance(c1);
	Float balance2 = b2.getBalance(c2);
	assert balance1 == 30.0f;
	assert balance2 == 20.0f;
	System.out.print("ok\n");    
    }
     public static void main (String[] args) throws Exception {

	 /*rappel pour ne pas oublier -ea */
	 boolean estMisAssertion = false; assert estMisAssertion = true; if (!estMisAssertion) { System.out.println("Execution impossible sans l'option -ea"); return; }

	 ORB orb = ORB.init(args, null);  
		

	 System.out.println(".");
	 TestBank t1 = new TestBank();
	 t1.testGetId(orb);
	 
	 System.out.println(".");
	 TestBank t2 = new TestBank();
	 t2.testCreateAccount(orb);

	 System.out.println(".");
	 TestBank t3 = new TestBank();
	 t3.testDeposit(orb);

	 System.out.println(".");
	 TestBank t4 = new TestBank();
	 t4.testWithdrawal(orb);
	 

	 System.out.println(".");
	 TestBank t5 = new TestBank();
	 t5.testIntraTransfer(orb);


	 System.out.println(".");
	 TestBank t6 = new TestBank();
	 t6.testInterTransfer(orb);

     }
}
