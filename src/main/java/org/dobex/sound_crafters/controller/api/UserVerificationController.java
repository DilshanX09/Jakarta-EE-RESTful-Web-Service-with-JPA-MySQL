package org.dobex.sound_crafters.controller.api;

import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.dobex.sound_crafters.dto.UserDTO;
import org.dobex.sound_crafters.service.UserService;

@Path("/users-verification")
public class UserVerificationController {

    private final Gson GSON = new Gson();

    @POST
    @Path("/verify-account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyAccount(String requestBody) {
        System.out.println("Received verification request: " + requestBody);
        UserDTO userDTO = GSON.fromJson(requestBody, UserDTO.class);
        String response = new UserService().verifyUserAccount(userDTO);
        return Response.ok().entity(response).build();
    }
}
