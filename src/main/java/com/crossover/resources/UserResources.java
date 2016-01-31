package com.crossover.resources;

import com.crossover.application.UserManager;
import com.crossover.model.User;
import org.glassfish.grizzly.http.server.Request;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;


/**
 * User: anlcan
 * Date: 30/01/16
 * Time: 19:35
 */
@Path("/users")
public class UserResources {

    public static final int SESSION_TIMEOUT = 1000 * 5;

    @Context
    private Request httpRequest;

    /**
     *
     * @return
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response get(){
        return Response.status(200).entity("{}").build();
    }

    private static final Object USER_NOT_FOUND = null;

    /**
     * check user login
     * @param username  email
     * @param password  password
     * @return redirect if successful, 401 if not authorized.
     */
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("email") String username,
                          @FormParam("password") String password ) throws URISyntaxException {
        System.out.println(username);
        System.out.println(password);

        User user = UserManager.checkCredentials(username, password);
        if ( user != USER_NOT_FOUND) {
            return Response.seeOther(new URI("/publisher.html")).
                    cookie(new NewCookie("com.crossover", UUID.randomUUID().toString(), "/", "localhost", null, SESSION_TIMEOUT/*maxAge*/, false)).build();
        } else {
            return   Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("Credentials error")
                    .build();
        }
    }

}

