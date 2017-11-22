import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
    
@Path("/bank/{bankname}")
public class BankRootRessource{
    @PathParam("bankname") private String bankname;
    private Bank bank;
   
    
    public BankRootRessource(String args[]) throws Exception{
	org.omg.CORBA.Object objRef;
	ORB orb = ORB.init(args, null);  
	objRef = orb.resolve_initial_references("NameService");
	NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
	objRef = ncRef.resolve_str(bankname);
	bank = BankHelper.narrow(objRef); 
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_XML)
    public Response createAccount()
    {	
	System.out.println("Creating account");
	String id = bank.createAccount();
	return Response.ok(id).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response deposit(float amount, @PathParam("id") String id_account) throws Exception {
	
	System.out.println("Deposit account");
	bank.deposit(amount,id_account);
	return Response.ok().build();
    }
    
    @PUT
    @Path("/{id}")
    public Response withdrawal(float amount, @PathParam("id") String id_account) throws Exception {
	System.out.println("Withdraw account");
	bank.withdrawal(amount,id_account);
	return Response.ok().build();
	
    }
    
    @GET
    @Path("/{id}")
    public Response getBalance(@PathParam("id") String id_account) throws Exception {
	System.out.println("Get balance");
	float balance = bank.getBalance(id_account);
	return Response.ok(balance).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response intraTransfer(String id_src, String id_dst, float amount) throws Exception{
	System.out.println("intraTransfer");
	System.out.println("Deposit account");
	bank.deposit(amount,id_dst);
	System.out.println("Withdraw account");
	bank.withdrawal(amount,id_src);
	return Response.ok().build();
    }
    @PUT
    @Path("/{id}")
    public Response interTransfer(String id_src, String id_dst, String bank_id, float amount) throws Exception{
	System.out.println("interTransfer");
	bank.interTransfer(id_src,id_dst,bank_id,amount);
	return Response.ok().build();
    }

}
