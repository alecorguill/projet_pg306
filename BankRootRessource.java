import javax.ws.rs.*;
import javax.ws.rs.core.*;

    
@Path("/bank")
public class BankRootRessource
{
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount()
    {	
	System.out.println("Creating account");
	String id = BankImpl.createAccount();
	return Response.ok(id).build();
    }
    
    // @PUT
    // @Path("{id}")
    // public void deposit(float amount, @PathParam("id") String id_account);
    
    // @PUT
    // @Path("{id}")
    // public void withdrawal(float amount, @PathParam("id") String id_account);
    
    // @GET
    // @Path("{id}")
    // public float getBalance(@PathParam("id") String id_account);
    
    
    // public void intraTransfer(String id_src, String id_dst, float amount);
    // public void interTransfer(String id_src, String id_dst, String bank_id, float amount);

}
