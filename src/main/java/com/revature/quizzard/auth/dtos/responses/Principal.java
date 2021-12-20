package com.revature.quizzard.auth.dtos.responses;

import com.revature.quizzard.user.AppUser;

public class Principal {

    private String id;
    private String username;
    private String role;

    public Principal() {
        super();
    }

    public Principal(AppUser authUser) {
        this.id = authUser.getId();
        this.username = authUser.getUsername();
        this.role = authUser.getAccountType().toString();
    }

    public Principal(String id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
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

    public AppUser extractUser() {
        return new AppUser(id, username, role);
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
