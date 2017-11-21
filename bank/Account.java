
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

    public void withdrawal(float amount){
	if(this.amount < amount){
	    System.out.println("Fonds insuffisants");
	    return;
	}
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
