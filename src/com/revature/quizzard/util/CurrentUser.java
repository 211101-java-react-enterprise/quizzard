package com.revature.quizzard.util;

import com.revature.quizzard.models.AppUser;

public class CurrentUser {

    private AppUser user;

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
