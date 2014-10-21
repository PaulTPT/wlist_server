package wunderlist;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import database.UserDatabase;


public class AuthFilter implements ContainerRequestFilter {
	
	@Context HttpServletRequest sr;
	
    /**
     * Apply the filter : check input request, validate or not with user auth
     * @param containerRequest The request from Tomcat server
     */
    @Override
    public ContainerRequest filter(ContainerRequest containerRequest) throws WebApplicationException {
        //GET, POST, PUT, DELETE, ...
        String method = containerRequest.getMethod();
        // myresource/get/56bCA for example
        String path = containerRequest.getPath(true);
 
        System.out.println(path);
        //We do allow this
        if(path.equals("login") ){
        	
            return containerRequest;
        }
 
        //Get the authentification passed in HTTP headers parameters
        String auth = containerRequest.getHeaderValue("authorization");
        System.out.println(auth);
 
        //If the user does not have the right (does not provide any HTTP Basic Auth)
       if(auth == null){
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }
 

        //If login or password fail
       if(UserDatabase.userFromToken(auth)==null){
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }
 
 
        sr.setAttribute("logged_user", UserDatabase.userFromToken(auth));
        
 
        return containerRequest;
    }
}
