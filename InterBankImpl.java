import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;

class InterBankImpl extends InterBankPOA
{
    private ArrayList<Bank> banks;
    public InterBankImpl();
    void registerBank(String bank_name);
}
