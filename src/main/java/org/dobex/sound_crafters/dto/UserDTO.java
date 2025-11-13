package org.dobex.sound_crafters.dto;

import org.dobex.sound_crafters.entity.Status;

import java.util.Date;

public class UserDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String verificationCode;
    private Date createdAt;
    private Date updatedAt;
    private Status status;
    private boolean staySignedIn;

    public UserDTO(long id, String firstName, String lastName, String email, Date createdAt, Date updatedAt, Status status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean isStaySignedIn() {
        return staySignedIn;
    }

    public void setStaySignedIn(boolean staySignedIn) {
        this.staySignedIn = staySignedIn;
    }
}
