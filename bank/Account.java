<<<<<<< HEAD
import BankPackage.InsufficientFunds;

public class Account {
    private float amount;
    
    public Account(String id, float amount){
	this.id = id;
	this.amount = amount;
    }
    
    public String getId(){
	return this.id;
    }
    public void deposit(float amount){
	this.amount += amount;
    }

    public void withdrawal(float amount) throws InsufficientFunds{
	if(this.amount < amount)
	    throw InsufficientFunds("Fonds insuffisants");
	else{
	    this.amount -= amount;
	}
    }

    public float getBalance(){
	return this.amount;
    }
    
    public boolean equals(Account other){
	return this.id == other.id;
    }
}
