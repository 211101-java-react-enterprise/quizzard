package com.revature.quizzard.auth.dtos.responses;

import com.revature.quizzard.user.AppUser;

public class PrincipalResponse {

    private String id;
    private String username;
    private String role;

    public PrincipalResponse() {
        super();
    }

    public PrincipalResponse(AppUser authUser) {
        this.id = authUser.getId();
        this.username = authUser.getUsername();
        this.role = authUser.getAccountType().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "PrincipalResponse{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}
