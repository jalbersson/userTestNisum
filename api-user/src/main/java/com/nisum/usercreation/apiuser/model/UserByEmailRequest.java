package com.nisum.usercreation.apiuser.model;

import java.io.Serializable;

public class UserByEmailRequest implements Serializable {
    private String email;

    public UserByEmailRequest() {
    }

    public UserByEmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
