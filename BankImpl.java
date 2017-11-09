import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;
class BankImpl extends BankPOA
{
    // public String echoString(String msg) 
    // {
    // 	System.out.println("msg: " + msg);
    // 	return msg;
    // }
    public long echoString(long msg) 
    {
	System.out.println("msg: " + msg.toString());
	return msg;
    }
}
