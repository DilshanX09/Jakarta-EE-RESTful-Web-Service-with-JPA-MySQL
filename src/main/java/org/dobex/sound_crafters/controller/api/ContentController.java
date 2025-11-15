package org.dobex.sound_crafters.controller.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.dobex.sound_crafters.service.LocationService;

@Path("/data")
public class ContentController {

    @GET
    @Path("/cities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCities() {
        String response = new LocationService().getAllCities();
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/provinces")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProvinces() {
        String response = new LocationService().getAllProvinces();
        return Response.ok().entity(response).build();
    }
}
