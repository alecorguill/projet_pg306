import project.*;

public class Transaction {
    private float amount;
    private String id_account;
    private String bank_id;

    public Transaction(String id_account, String bank_id,float amount){
	this.amount = amount;
	this.id_account = id_account;
	this.bank_id = bank_id;
    }

    public String toString(){
	return "BANK : " + this.bank_id + ", ACCOUNT : " + this.id_account + ", AMOUNT : " + this.amount;
    }
}
