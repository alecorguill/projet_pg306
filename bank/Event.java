import project.*;

public class Event {
    private String id_account_src;
    private String id_account_dst;
    private String bank_id_src;
    private String bank_id_dst;
    private Event_t event;
    private float amount;
    
    public Event(String id_account_src, String id_account_dst, 
		 String bank_id_src, String bank_id_dst, 
		 float amount, Event_t event){
	this.id_account_src = id_account_src;
	this.bank_id_src = bank_id_src;
	this.id_account_dst = id_account_dst;
	this.bank_id_dst = bank_id_dst;
	this.event = event;
	this.amount = amount;
    }
    
    public Event_t getEvent(){
	return this.event;
    }
  
    public String getAccountSrc(){
	return this.id_account_src; 
    }
  
    public String getAccountDst(){
	return this.id_account_dst; 
    } 
  
    public String getBankIdSrc(){
	return this.bank_id_src; 
    }
  
    public String getBankIdDst(){
	return this.bank_id_dst; 
    } 

    public float getAmount(){
        return this.amount;
    }
}
