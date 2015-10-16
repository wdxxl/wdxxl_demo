package com.wdxxl.jersey;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestResource {

    @Inject
    private JustOne justOne;

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/func1/{input}")
    public Response func1(@PathParam("input") int input) {
        justOne.bumpSecretNumber();
        String responseData =
                String.format("{ \"result\": %s }", input + justOne.getSecretNumber());
        return Response.ok(responseData).build();
    }
}