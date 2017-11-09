import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;

class BankImpl extends BankPOA
{
    private String id;
    private ArrayList<String> portfolio;
    
    public BankImpl();
    public String createAccount();
    public void deposit(float amount, String id_account);
    public void withdrawal(float amount, String id_account);
    public float getBalance(String id_account);
    public void intraTransfer(String id_src, String id_dst, float amount);
    public void interTransfer(String id_src, String id_dst, String bank_id, float amount);
    
}
