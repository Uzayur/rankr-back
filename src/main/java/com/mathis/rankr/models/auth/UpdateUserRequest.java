package com.mathis.rankr.models.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateUserRequest {

    @NotBlank
    @Size(min = 3, max = 30)
    private String username;

    @NotBlank
    @Size(min = 1, max = 50)
    private String firstname;

    @NotBlank
    @Size(min = 1, max = 50)
    private String lastname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 255)
    private String password;

    private String bio;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public @NotBlank @Size(min = 6, max = 255) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 6, max = 255) String password) {
        this.password = password;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotBlank @Size(min = 1, max = 50) String getFirstname() {
        return firstname;
    }

    public void setFirstname(@NotBlank @Size(min = 1, max = 50) String firstname) {
        this.firstname = firstname;
    }

    public @NotBlank @Size(min = 3, max = 30) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 3, max = 30) String username) {
        this.username = username;
    }

    public @NotBlank @Size(min = 1, max = 50) String getLastname() {
        return lastname;
    }

    public void setLastname(@NotBlank @Size(min = 1, max = 50) String lastname) {
        this.lastname = lastname;
    }
}
