package org.dobex.sound_crafters.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.Context;
import org.dobex.sound_crafters.dto.UserDTO;
import org.dobex.sound_crafters.dto.UserResponseDTO;
import org.dobex.sound_crafters.entity.Status;
import org.dobex.sound_crafters.entity.User;
import org.dobex.sound_crafters.util.AppUtil;
import org.dobex.sound_crafters.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserService {

    private static final Gson GSON = new Gson();

    public String loginUser(UserDTO userDTO, @Context HttpServletResponse httpResponse) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        JsonObject response = new JsonObject();
        String responseMessage = "";
        boolean status = false;

        if (userDTO.getEmail() == null || userDTO.getEmail().isBlank()) responseMessage = "Your email is required";
        else if (userDTO.getPassword() == null || userDTO.getPassword().isBlank())
            responseMessage = "Your password is required";
        else {
            User user = session.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", userDTO.getEmail())
                    .getSingleResultOrNull();
            if (user == null) responseMessage = "This email account not found! Please register first!";
            else {
                if (!user.getPassword().equals(userDTO.getPassword())) {
                    responseMessage = "Something went wrong! Please check your login credentials!";
                } else {
                    Status verifiedStatus = session.createNamedQuery("Status.findByValue", Status.class)
                            .setParameter("value", Status.Type.VERIFIED.name())
                            .getSingleResult();
                    if (!user.getStatus().equals(verifiedStatus)) {
                        responseMessage = "Your account is not verified. Please verify first!";
                        response.addProperty("isVerified", false);
                    } else {

                        if (userDTO.isStaySignedIn()) {
                            Cookie emailCookie = new Cookie("email", userDTO.getEmail());
                            Cookie passwordCookie = new Cookie("password", userDTO.getPassword());
                            emailCookie.setMaxAge(30 * 24 * 60 * 60);
                            passwordCookie.setMaxAge(30 * 24 * 60 * 60);
                            httpResponse.addCookie(emailCookie);
                            httpResponse.addCookie(passwordCookie);
                        }

                        UserResponseDTO responseUserDTO = new UserResponseDTO(
                                user.getId(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getEmail(),
                                user.getCreatedAt(),
                                user.getUpdatedAt(),
                                user.getStatus()
                        );

                        response.addProperty("user", GSON.toJson(responseUserDTO));
                        status = true;
                        responseMessage = "You are successfully logged in!";
                    }
                }
            }
            session.close();
        }

        response.addProperty("status", status);
        response.addProperty("message", responseMessage);
        return GSON.toJson(response);
    }

    public String registerUser(UserDTO userDTO) {

        JsonObject response = new JsonObject();
        String responseMessage = "";
        boolean status = false;

        if (userDTO.getFirstName() == null) responseMessage = "firstName is required";
        else if (userDTO.getLastName() == null) responseMessage = "lastName is required";
        else if (userDTO.getEmail() == null) responseMessage = "email is required";
        else if (userDTO.getPassword() == null) responseMessage = "password is required";
        else {
            Session session = HibernateUtil.getSessionFactory().openSession();
            User singleUser = session.createNamedQuery("User.findByEmail", User.class).setParameter("email", userDTO.getEmail()).getSingleResultOrNull();

            if (singleUser != null) responseMessage = "This email is already exist! Please use another email";

            else {
                User user = new User();
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                user.setEmail(userDTO.getEmail());
                user.setPassword(userDTO.getPassword());

                String verificationCode = AppUtil.generateCode();
                user.setVerificationCode(verificationCode);
                response.addProperty("vcode", true);

                Status pendingStatus = session.createNamedQuery("Status.findByValue", Status.class).setParameter("value", Status.Type.PENDING.name()).getSingleResult();

                user.setStatus(pendingStatus);
                Transaction transaction = session.beginTransaction();

                try {
                    session.persist(user);
                    transaction.commit();
                    status = true;
                    responseMessage = "Account created successfully! Verification has been sent to your email!";
                } catch (HibernateException e) {
                    transaction.rollback();
                    responseMessage = "Account creation failed! Please try again";
                } finally {
                    session.close();
                }
            }
        }

        response.addProperty("status", status);
        response.addProperty("message", responseMessage);
        return UserService.GSON.toJson(response);
    }
}
