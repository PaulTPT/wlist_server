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

import database.TodoListUsersDatabase;
import database.TodosDatabase;
import wunderlist.Todo;

// Will map the resource to the URL todos
@Path("me/tasks")
public class TodosResource {

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
	public List<Todo> getTodos() {
		List<Todo> todos = new ArrayList<Todo>();
		todos = TodoListUsersDatabase.getTodos((String) sr
				.getAttribute("logged_user"));
		return todos;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Todo newTodo(@FormParam("title") String title,@FormParam("list_id") int list_id) {
		Todo newTodo = TodosDatabase.storeTodo(new Todo(title,list_id));
		if (newTodo != null) {
			TodoListUsersDatabase.addTodoToUser(
					(String) sr.getAttribute("logged_user"), newTodo.getId());
			return newTodo;
		} else {
			throw new RuntimeException("Error... Task not stored.");
		}

	}

	

}