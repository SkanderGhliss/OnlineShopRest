package de.home.vs.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("test")
public class DemoRessource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTestRessource() {
        
        return "This is my first web service resource \n";
    }
}