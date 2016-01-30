package com.crossover.resources;

import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User: anlcan
 * Date: 30/01/16
 * Time: 19:35
 */
@Path("/users")
public class UserResources {
    /**
     *
     * @return
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response get(){
        return Response.status(200).build();
    }

    /**
     * check user login
     * @param username  email
     * @param password  password
     * @return redirect if successful, 401 if not authorized.
     */
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormDataParam("email") String username,
                          @FormDataParam("password") String password ){
        System.out.println(username);
        System.out.println(password);
        return Response.status(200).build();
    }

}

