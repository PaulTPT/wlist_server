package wunderlist;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import database.UserDatabase;

@Path("/login")
public class LoginResource {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Token login(@FormParam("email") String name,
			@FormParam("password") String password) throws IOException {
		if (UserDatabase.validateUser(name, password)) {
			String token = UserDatabase.addTokenToUser(name);
			return new Token(name, token);
		} else {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

	}

}
