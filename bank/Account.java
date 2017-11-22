import project.*;

public class Account {
    private float amount;
    private String id;
    private String id_client;

    public Account(String id, String id_client, float amount){
	this.id = id;
	this.id_client = id_client;
	this.amount = amount;
    }
    
    public String getId(){
	return this.id;
    }
    
    
    public String getIdClient(){
	return this.id_client;
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
	res += "CLIENT : "+ this.id_client +'\n';
	res += "BALANCE : "+Float.toString(this.amount)+'\n';
	return res;
    }
}
