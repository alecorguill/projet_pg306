import javax.ws.rs.*;
import javax.ws.rs.core.*;
    
@Path("/bank")
public class BankRessource{
    
    public String createAccount();
    public void deposit(float amount, String id_account);
    public void withdrawal(float amount, String id_account);
    public float getBalance(String id_account);
    public void intraTransfer(String id_src, String id_dst, float amount);
    public void interTransfer(String id_src, String id_dst, String bank_id, float amount);

}
