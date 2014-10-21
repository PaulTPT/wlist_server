package resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import database.TodoListUsersDatabase;

@Path("{id}: [0-9]*")
public class IdResource {
	
	@Context
	HttpServletRequest sr;

	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	// Defines that the next path parameter after todos is
		// treated as a parameter and passed to the TodoResources
		// Allows to type http://localhost:8080/wunderlist/me/1
		// 1 will be treaded as parameter todo and passed to TodoResource
		
		public Resource getTodo(@PathParam("todo") int id) {

			if (TodoListUsersDatabase.isTodo(
					(String) sr.getAttribute("logged_user"), id)) {
				return new TodoResource(uriInfo, sr, id);
			} else {
				return new ListResource(uriInfo, sr, id);
			}
		}

}
