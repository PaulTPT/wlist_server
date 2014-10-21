package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import database.UserDatabase;
import wunderlist.Listw;

@Path("register")
public class RegisterResource {

	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response register(@FormParam("email") String mail, @FormParam("password") String password){
		if(UserDatabase.addUser(mail, password)){
			return Response.ok().build();
		}else {
			throw new RuntimeException("Impossible to register user. Probably a user with the same email already exist...");
		}
		
		
	}
	
}
