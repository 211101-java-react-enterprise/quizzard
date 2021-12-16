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

}
