
public class Account {
    private String id;
    private float amount;
    
    public Account(String id, float amount){
	this.id = id;
	this amount = amount;
    }

    public void deposit(float amount){
	this.amount += amount;
    }

    public void withdrawal(float amount){
	this.amount -= amount;
    }

    public float getBalance(){
	return this.amount;
    }
}
