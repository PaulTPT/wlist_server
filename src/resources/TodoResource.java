package resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import database.TodoListUsersDatabase;
import database.TodosDatabase;
import wunderlist.Todo;


public class TodoResource implements Resource {

	@Context
	UriInfo uriInfo;
	@Context
	HttpServletRequest sr;
	int id;

	public TodoResource(UriInfo uriInfo, HttpServletRequest sr, int id) {
		this.uriInfo = uriInfo;
		this.sr = sr;
		this.id = id;
	}

	// Application integration
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Todo getTodo() {
		if (TodoListUsersDatabase.userOwnsTodo(
				(String) sr.getAttribute("logged_user"), id)) {
			return TodosDatabase.retrieveTodo(id);
		} else
			throw new RuntimeException("Get: Todo with " + id
					+ " not found or not owned by you");

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Todo putTodo(JAXBElement<Todo> todo) {
		Todo updatedTodo = todo.getValue();

		if (TodoListUsersDatabase.userOwnsTodo(
				(String) sr.getAttribute("logged_user"), id)) {
			TodosDatabase.updateTodo(updatedTodo);
			return updatedTodo;
		} else
			throw new RuntimeException("Get: Todo with " + id
					+ " not found or not owned by you");
	}

	@DELETE
	public Response deleteTodo() {
		if (TodoListUsersDatabase.userOwnsTodo(
				(String) sr.getAttribute("logged_user"), id)) {

			TodosDatabase.deleteTodo(id);
			TodoListUsersDatabase.removeTodoFromUser(
					(String) sr.getAttribute("logged_user"), id);
			return Response.ok().build();
		} else
			throw new RuntimeException("Get: Todo with " + id
					+ " not found or not owned by you");

	}
}