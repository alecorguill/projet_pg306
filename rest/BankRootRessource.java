import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

import project.*;
    
@Path("/bank/{bankname}")
public class BankRootRessource{
    @PathParam("bankname") private String bankname;
    private Bank bank;
    private NamingContextExt ncRef;
    
    public void config() throws Exception
    {
	if (ncRef != null)
	    return;	
	org.omg.CORBA.Object objRef;
	String[] args = {"-ORBInitRef","NameService=corbaloc::localhost:2810/NameService"};
	ORB orb = ORB.init(args, null);
	objRef = orb.resolve_initial_references("NameService");
	System.out.println("L'erreur est plus haut laaaaaaaaaaaaaa");
    	this.ncRef = NamingContextExtHelper.narrow(objRef);
    }
    
    public void resolve(String bankname) throws Exception
    {
	
	org.omg.CORBA.Object objRef;
	objRef = this.ncRef.resolve_str(bankname);
    	this.bank = BankHelper.narrow(objRef); 
    }
    
    @POST
    @Path("/{id_client}")
    @Produces("text/plain")
    public Response createAccount(@PathParam("id_client") String id_client) throws Exception
    {	
	System.out.println("Creating account");
	config();
	resolve(bankname);
	String id = bank.createAccount(id_client);
	return Response.ok(id).build();
    }
    
    // @PUT
    // @Path("/{id}")
    // public Response deposit(float amount, @PathParam("id") String id_account) throws Exception {
	
    // 	System.out.println("Deposit account");
    // 	bank.deposit(amount,id_account);
    // 	return Response.ok().build();
    // }
    
    // @PUT
    // @Path("/{id}")
    // public Response withdrawal(float amount, @PathParam("id") String id_account) throws Exception {
    // 	System.out.println("Withdraw account");
    // 	bank.withdrawal(amount,id_account);
    // 	return Response.ok().build();
	
    // }
    
    // @GET
    // @Path("/{id_client}")
    // public Response getAllAccounts(@PathParam("id") String id_client) throws Exception {
    // 	System.out.println("Get all accounts of a client");
    // 	bank.getAllAccounts(id_client);
    // 	return Response.ok().build();
    // }

    
    
    // @GET
    // @Path("/{id}")
    // public Response getBalance(@PathParam("id") String id_account) throws Exception {
    // 	System.out.println("Get balance");
    // 	float balance = bank.getBalance(id_account);
    // 	return Response.ok(balance).build();
    // }
    
    // @PUT
    // @Path("/{id}")
    // public Response intraTransfer(String id_src, String id_dst, float amount) throws Exception{
    // 	System.out.println("intraTransfer");
    // 	System.out.println("Deposit account");
    // 	bank.deposit(amount,id_dst);
    // 	System.out.println("Withdraw account");
    // 	bank.withdrawal(amount,id_src);
    // 	return Response.ok().build();
    // }
    // @PUT
    // @Path("/{id}")
    // public Response interTransfer(String id_src, String id_dst, String bank_id, float amount) throws Exception{
    // 	System.out.println("interTransfer");
    // 	bank.interTransfer(id_src,id_dst,bank_id,amount);
    // 	return Response.ok().build();
    // }

}
