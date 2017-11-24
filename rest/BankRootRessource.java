import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.Arrays;
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
    public Response createAccount(@PathParam("id_client") String id_client)
    {	
	System.out.println("Creating account");
	String id;
	try {
	    config();
	    resolve(bankname);
	    id = bank.createAccount(id_client);
	}
	catch (Exception e){
	    return Response.ok().build();
	}
	return Response.ok(id).build();

    }
    
    @PUT
    @Path("/{id}/{amount}")
    public Response deposit(@PathParam("amount") float amount, @PathParam("id") String id_account) {
	
    	System.out.println("Deposit account");
	
	try{
	    config();
	    resolve(bankname);
	    bank.deposit(amount,id_account);   
	}
	catch (Exception e){}
	return Response.ok().build();
    }
    
    @PUT
    @Path("/{id}/{amount}")
    public Response withdrawal(@PathParam("amount") float amount, @PathParam("id") String id_account){
    	System.out.println("Withdraw account");
	try{
	    
	    config();
	    resolve(bankname);
	    bank.withdrawal(amount,id_account);
    	}
	catch (Exception e){}
	return Response.ok().build();
	
    }
    
    @GET
    @Path("/accounts/{id_client}")
    @Produces("text/plain")
    public Response getAllAccounts(@PathParam("id_client") String id_client){
	System.out.println("Get all accounts of a client");
	String[] ids;
	try{
	    config();
	    resolve(bankname);
	    ids = bank.getAllAccounts(id_client);
	}
	catch (Exception e)
	    {
		return Response.ok().build();
	    }
	return Response.ok(Arrays.toString(ids)).build();
    }

    
    
    @GET
    @Path("/{id}")
    @Produces("text/plain")
    public Response getBalance(@PathParam("id") String id_account){
    	System.out.println("Get balance");
	float balance;
	try{
	    config();
	    resolve(bankname);
	    balance = bank.getBalance(id_account);
	}
	catch (Exception e)
	    {
		return Response.ok().build();
	    }
    	return Response.ok(Float.toString(balance)).build();
    }
    
    @PUT
    @Path("/{id_src}/{id_dst}/{amount}")
    public Response intraTransfer(@PathParam("id_src") String id_src, @PathParam("id_dst") String id_dst, @PathParam("amount") float amount){
    	System.out.println("intraTransfer");
	try{
	    
	    config();
	    resolve(bankname);
	    bank.intraTransfer(id_src,id_dst,amount);
    	}
	catch (Exception e){}
	return Response.ok().build();
	
    }

    @PUT
    @Path("/{id_src}/{bank_dst}/{id_dst}/{amount}")
    public Response interTransfer(@PathParam("id_src") String id_src, @PathParam("bank_dst") String bank_dst, @PathParam("id_dst") String id_dst, @PathParam("amount") float amount) throws Exception{
    	System.out.println("interTransfer");
	try{	    
	    config();
	    resolve(bankname);
	    bank.interTransfer(id_src,id_dst,bank_dst,amount);
    	}
	catch (Exception e){}

	return Response.ok().build();

    }

}
