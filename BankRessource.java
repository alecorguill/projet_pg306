import javax.ws.rs.*;
import javax.ws.rs.core.*;
    
@Path("/bank")
public class BankRessource
{
    
    @POST
    @Path("/clients")
    @Produces("application/xml")
    public String createAccount()
    {
	System.out.println("Create account");
	return "Success";
    }
    
    @PUT
    public void deposit(float amount, String id_account);
    
    @PUT
    public void withdrawal(float amount, String id_account);
    
    @GET
    @Path("/clients/{id}")
    public float getBalance(@PathParam("id") String id_account);
    public void intraTransfer(String id_src, String id_dst, float amount);
    public void interTransfer(String id_src, String id_dst, String bank_id, float amount);

}
