import project.*;

public class Account {
    private float amount;
    private String id;

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
	if(this.amount < amount){
	    throw new InsufficientFunds();
	}
	else{
	    this.amount -= amount;
	}
    }

    public float getBalance(){
	return this.amount;
    }
    
    public String toString(){
	String res = "ID : "+this.id+"\n";
	res += "BALANCE : "+Float.toString(this.amount)+'\n';
	return res;
    }
}
