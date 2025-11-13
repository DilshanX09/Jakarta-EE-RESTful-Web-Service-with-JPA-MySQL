package org.dobex.sound_crafters.controller.api;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.dobex.sound_crafters.dto.UserDTO;
import org.dobex.sound_crafters.service.UserService;

@Path("/users")
public class UserController {

    private static final Gson GSON = new Gson();

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String requestBody) {
        UserDTO userDTO = GSON.fromJson(requestBody, UserDTO.class);
        String response = new UserService().registerUser(userDTO);
        return Response.ok().entity(response).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String requestBody, @Context HttpServletResponse httpResponse) {
        UserDTO userDTO = GSON.fromJson(requestBody, UserDTO.class);
        String response = new UserService().loginUser(userDTO, httpResponse);
        return Response.ok().entity(response).build();
    }
}
