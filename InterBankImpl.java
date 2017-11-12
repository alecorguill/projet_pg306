import java.util.ArrayList;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;

class InterBankImpl extends InterBankPOA
{
    private ArrayList<Bank> banks;
    public InterBankImpl(){

    }
    public void registerBank(String bank_name){
	
    }
    public void Transfer(String id_src, String id_dst, String bank_id, float amount){
	
    }
}
