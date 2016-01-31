package com.crossover.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * User: anlcan
 * Date: 31/01/16
 * Time: 00:33
 */
public class RequestFilter implements ContainerRequestFilter {


    private static final  String LOGIN_URI = "users/login";

    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException {

        if ( requestContext.getUriInfo().getPath().endsWith(LOGIN_URI))
            return;

        for ( Cookie c : requestContext.getCookies().values()){
            if ( c.getName().equalsIgnoreCase("com.crossover")){
                // we are just checking if cookie is set
                // in a production environment, we could setup a memcache/redis
                // with cookie values set with ttl
                return;
            }
        }

        requestContext.abortWith(Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("User cannot access the resource.")
                .build());


    }
}
