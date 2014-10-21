package resources;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;

import com.sun.jersey.api.json.JSONConfiguration;

import database.ListDatabase;
import database.TodoListUsersDatabase;
import database.TodosDatabase;
import wunderlist.Listw;
import wunderlist.Todo;

// Will map the resource to the URL todos
@Path("me/lists")
public class ListsResource {

	@Context
	HttpServletRequest sr;

	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// Return the list of todos for applications
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Listw> getLists() {
		List<Listw> lists = new ArrayList<Listw>();
		lists = TodoListUsersDatabase.getLists((String) sr
				.getAttribute("logged_user"));
		return lists;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Listw newList(@FormParam("title") String title) {
		Listw newList = ListDatabase.storeList(new Listw(title));
		if (newList != null) {
			if (TodoListUsersDatabase.addListToUser(
					(String) sr.getAttribute("logged_user"), newList.getId())) {
				return newList;
			} else {
				throw new RuntimeException("Error... Task not stored.");
			}
		} else {
			throw new RuntimeException("Error... Task not stored.");
		}

	}

}