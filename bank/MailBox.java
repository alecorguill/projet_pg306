import project.*;
import java.util.ArrayList;


public class MailBox {
    public String id_bank;
    private ArrayList<Event> mails;

    public MailBox(String id_bank){
	this.id_bank = id_bank;
	this.mails = new ArrayList<Event>();
    }
    
    public void add(Event event){
	this.mails.add(event);
    }

    public String getId(){
	return this.id_bank;
    }
	
}
