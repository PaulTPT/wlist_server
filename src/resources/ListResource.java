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

import wunderlist.Listw;
import database.ListDatabase;
import database.TodoListUsersDatabase;
import database.TodosDatabase;

public class ListResource implements Resource {
	@Context
	UriInfo uriInfo;
	@Context
	HttpServletRequest sr;
	int id;

	public ListResource(UriInfo uriInfo, HttpServletRequest sr, int id) {
		this.uriInfo = uriInfo;
		this.sr = sr;
		this.id = id;
	}

	// Application integration
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Listw getList() {
		if (TodoListUsersDatabase.userOwnsList(
				(String) sr.getAttribute("logged_user"), id)) {
			return ListDatabase.retrieveList(id);
		} else
			throw new RuntimeException("Get: Todo with " + id
					+ " not found or not owned by you");

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Listw putList(JAXBElement<Listw> list) {
		Listw updatedList = list.getValue();

		if (TodoListUsersDatabase.userOwnsList(
				(String) sr.getAttribute("logged_user"), id)) {
			ListDatabase.updateList(updatedList);
			return updatedList;
		} else
			throw new RuntimeException("Get: Todo with " + id
					+ " not found or not owned by you");
	}

	@DELETE
	public Response deleteList() {
		if (TodoListUsersDatabase.userOwnsList(
				(String) sr.getAttribute("logged_user"), id)) {

			ListDatabase.deleteList(id);
			TodoListUsersDatabase.removeListFromUser(
					(String) sr.getAttribute("logged_user"), id);
			return Response.ok().build();
		} else
			throw new RuntimeException("Get: Todo with " + id
					+ " not found or not owned by you");

	
}

}